package my.ws;

import java.util.Calendar;

import pro.Server.LocalConfig;
import pro.Server.Program;
import uti.MyCheck;
import uti.MyConfig;
import uti.MyConvert;
import uti.MyDate;
import uti.MyLogger;
import db.ApiNotify;
import db.ApiNotifyId;
import db.ChargeLog;
import db.ChargeLog.ChargeType;

public class apinotify
{

	public enum Result
	{
		/*
		 * 0 – ghi nhận thành công 1 – ghi nhận không thành công Nội dung
		 * result: Nếu là thanh toán download ứng dụng thì phải là link download
		 * ứng dụng sẽ gửi về cho KH Nếu là các thanh toán khác phải gửi về nội
		 * dung mô tả VD: 0 | http://google.com.vn/ung_dung.apk 1 | Thanh toán
		 * đăng ký dịch vụ không thành công
		 */

		Success(0), Fail(1),
		/**
		 * Riêng enum này dùng để phục vụ cho hàm check invalid para input
		 */
		CheckParaIsOK(2), InputInvalid(300), AccountInvalid(301),
		// Lỗi hệ thống
		SystemError(302), ;

		private int value;

		private Result(int value)
		{
			this.value = value;
		}

		public Integer GetValue()
		{
			return this.value;
		}

		public static Result FromValue(int iValue)
		{
			for (Result type : Result.values())
			{
				if (type.GetValue() == iValue)
					return type;
			}
			return Fail;
		}
	}

	MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath(), this.getClass().toString());

	String LogFormat = "ChargeLog -->userName:%s|password:%s|cpRequestId:%s|mobile:%s|price:%s|cmd:%s|responseCode:%s|result:%s";

	class NotifyRequest
	{
		public String username;
		public String password;
		public String cpRequestId;
		public String mobile;
		public int price;
		public String responseCode;
		public String cmd;

		public String PhoneNumber = "";

		public Calendar mCal_Current = Calendar.getInstance();

		public String MTContent = "";

		public Short PID = 0;

		public ApiNotify mApiNotifyObj = new ApiNotify();

		public Result mResult = Result.Fail;

		ChargeLog.ChargeType mChargeType = ChargeType.Nothing;

		public String BuildResult(Result mResult)
		{
			if (mResult == Result.Success)
			{
				if (mChargeType == ChargeType.Register)
					return "0|Thanh toan dang ky dich vu ISchool thanh cong";
				else
				{
					return "0|Huy dich vu ISchool thanh cong";
				}
			}
			else
			{
				if (mChargeType == ChargeType.Deregister)
					return "1|Thanh toan dang ky dich vu ISchool KHONG thanh cong";
				else
				{
					return "1|Huy dich vu ISchool KHONG thanh cong";
				}
			}

		}

		public Result CheckPara()
		{
			try
			{
				if (username == null || username.equals("") || password == null || password.equals("")
						|| cpRequestId == null || cpRequestId.equals("") || mobile == null || mobile.equals("")
						|| price < 0 || responseCode == null || responseCode.equals("") || cmd == null
						|| cmd.equals(""))
				{
					return Result.InputInvalid;
				}

				// Kiểm tra user, pass
				if (!Program.checkAccount(username, password))
				{
					return Result.AccountInvalid;
				}

				// Kiểm tra định dạng của Số điện thoại
				PhoneNumber = MyCheck.CheckPhoneNumber(mobile);
				if (PhoneNumber.equals(""))
				{
					return Result.InputInvalid;
				}

				PID = ((Integer) MyConvert.GetPIDByMSISDN(PhoneNumber, LocalConfig.MAX_PID)).shortValue();

				// Kiểm tra số điện thoại có phải của viettel hay không
				if (MyCheck.GetTelco(PhoneNumber) != MyConfig.Telco.VIETTEL)
				{
					return Result.InputInvalid;
				}

				if (cmd.equalsIgnoreCase("REGISTER"))
					mChargeType = ChargeType.Register;
				else if (cmd.equalsIgnoreCase("CANCEL"))
					mChargeType = ChargeType.Deregister;
				else
				{
					return Result.InputInvalid;
				}

				return Result.CheckParaIsOK;
			}

			catch (Exception ex)
			{
				mLog.log.error(ex);
				return Result.InputInvalid;
			}
		}

		public void CreateChargeLog()
		{
			ApiNotifyId mID = new ApiNotifyId();
			mID.setPid(PID);
			mID.setPhoneNumber(PhoneNumber);
			mApiNotifyObj.setId(mID);

			mApiNotifyObj.setCpRequestId(cpRequestId);
			mApiNotifyObj.setPrice((float) price);
			mApiNotifyObj.setResponseCode(responseCode);
			mApiNotifyObj.setCmd(cmd);
			mApiNotifyObj.setLogDate(MyDate.Date2Timestamp(Calendar.getInstance()));
			mApiNotifyObj.setPartnerId(0);
		}

		public boolean Insert_ChargeLog() throws Exception
		{
			return mApiNotifyObj.Save();
		}
	}

	public String doNotify(String username, String password, String cpRequestId, String mobile, int price,
			String responseCode, String cmd)
	{
		NotifyRequest mRequest = new NotifyRequest();
		mRequest.username = username;
		mRequest.password = password;
		mRequest.cpRequestId = cpRequestId;
		mRequest.mobile = mobile;
		mRequest.price = price;
		mRequest.responseCode = responseCode;
		mRequest.cmd = cmd;

		try
		{
			// kiểm tra đầu vào
			mRequest.mResult = mRequest.CheckPara();
			if (mRequest.mResult != Result.CheckParaIsOK)
				return mRequest.BuildResult(mRequest.mResult);

			mRequest.mResult = Result.Fail;

			mRequest.CreateChargeLog();

			if (!mRequest.Insert_ChargeLog())
			{
				mRequest.mResult = Result.Fail;
				return mRequest.BuildResult(mRequest.mResult);
			}

			mRequest.mResult = Result.Success;
			return mRequest.BuildResult(mRequest.mResult);

		}
		catch (Exception ex)
		{
			mLog.log.error(ex);

			mRequest.mResult = Result.SystemError;
			return mRequest.BuildResult(mRequest.mResult);
		}
		finally
		{
			mLog.log.info(String.format(LogFormat, username, password, cpRequestId, mobile, price, responseCode, cmd,
					mRequest.mResult.GetValue()));
		}
	}
}
