package my.ws;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import db.ChargeLog;
import db.ChargeLogId;
import db.Service;
import db.Subscriber;
import db.DefineMt.MTType;
import pro.Server.LocalConfig;
import pro.Server.Program;
import uti.MyCheck;
import uti.MyConfig;
import uti.MyConvert;
import uti.MyDate;
import uti.MyLogger;

public class receiveresult
{
	public enum Result
	{
		/*
		 * 0 Gửi nội dung cho người dùng thành công 1 Gửi nội dung cho người
		 * dùng thất bại 300 Sai tham số 301 Sai tên đăng nhập hoặc mật khẩu 302
		 * Server bận
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

	public enum ParaType
	{
		Nothing(-1), ChargeSuccess(0), ChargeFail(1), ;

		private int value;

		private ParaType(int value)
		{
			this.value = value;
		}

		public Integer GetValue()
		{
			return this.value;
		}

		public static ParaType FromValue(int iValue)
		{
			for (ParaType type : ParaType.values())
			{
				if (type.GetValue() == iValue)
					return type;
			}
			return Nothing;
		}
	}

	public enum Mode
	{
		/*
		 * 0: Đăng ký sub 1: Hủy sub 2: Tạm dừng 3: Đăng ký lại
		 */

		Nothing(-1), Check(0), Real(1);

		private int value;

		private Mode(int value)
		{
			this.value = value;
		}

		public Integer GetValue()
		{
			return this.value;
		}

		public static Mode FromValue(int iValue)
		{
			for (Mode type : Mode.values())
			{
				if (type.GetValue() == iValue)
					return type;
			}
			return Nothing;
		}
	}

	MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath(), this.getClass().toString());

	String LogFormat = "ChargeLog -->username:%s|password:%s|serviceid:%s|msisdn:%s|chargetime:%s|params:%s|mode:%s|amount:%s|result:%s";

	class ReceiveResultRequest
	{
		public String username;
		public String password;
		public String serviceid;
		public String msisdn;
		public String chargetime;
		public String params;
		public String mode;
		public int amount;

		public String PhoneNumber = "";
		public ParaType mParaType = ParaType.Nothing;

		/**
		 * Cho biết mode là real thật
		 */
		public Mode mMode = Mode.Nothing;

		/**
		 * Chuyển từ chargetime sang
		 */
		public Calendar mCal_ChargeTime = Calendar.getInstance();
		public Calendar mCal_Current = Calendar.getInstance();
		public Calendar mCal_Expire = Calendar.getInstance();

		public String MTContent = "";

		public Short PID = 0;

		public Subscriber mSubObj = new Subscriber();
		public ChargeLog mChargeLogObj = new ChargeLog();
		public Service serviceObj = null;
		

		public Result mResult = Result.Fail;
		public MTType mMTType = MTType.Fail;

		public String BuildResult(Result mResult)
		{
			return mResult.GetValue().toString();
		}

		public Result CheckPara()
		{
			try
			{
				if (username == null || username.equals("") || password == null || password.equals("")
						|| serviceid == null || serviceid.equals("") || msisdn == null || msisdn.equals("")
						|| chargetime == null || chargetime.equals("") || params == null || params.equals("")
						|| mode == null || mode.equals("") || amount < 0)
				{
					return Result.InputInvalid;
				}

				// Kiểm tra user, pass
				if (!Program.checkAccount(username, password))
				{
					return Result.AccountInvalid;
				}

				serviceObj = Service.getServiceByViettelID(pro.Server.Program.listService, this.serviceid);
				
				// Kiểm tra service ID
				if (serviceObj == null)
				{
					return Result.InputInvalid;
				}

				// Kiểm tra định dạng của Số điện thoại
				PhoneNumber = MyCheck.CheckPhoneNumber(msisdn);
				if (PhoneNumber.equals(""))
				{
					return Result.InputInvalid;
				}

				// Kiểm tra số điện thoại có phải của viettel hay không
				if (MyCheck.GetTelco(PhoneNumber) != MyConfig.Telco.VIETTEL)
				{
					return Result.InputInvalid;
				}

				try
				{
					// Kiểm tra định dạng ngày tháng nhập vào
					Date TempTime = MyConfig.Get_DateFormat_yyyymmddhhmmss().parse(chargetime);
					mCal_ChargeTime.setTime(TempTime);

					// Kiểm tra para
					int intPara = Integer.parseInt(params.trim());
					mParaType = ParaType.FromValue(intPara);

					if (mParaType == ParaType.Nothing)
					{
						return Result.InputInvalid;
					}

					// Kiêm tra mode
					if (mode.equalsIgnoreCase("Check"))
						this.mMode = Mode.Check;
					else if (mode.equalsIgnoreCase("REAL"))
						this.mMode = Mode.Real;

					if (mMode == Mode.Nothing)
					{
						return Result.InputInvalid;
					}
				}
				catch (ParseException ex)
				{
					mLog.log.error(ex);
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

		
		
		public void GetSub() throws Exception
		{
			PID = ((Integer) MyConvert.GetPIDByMSISDN(PhoneNumber, LocalConfig.MAX_PID)).shortValue();
			Subscriber mSubscriber = new Subscriber();
			mSubObj = mSubscriber.GetSub(PID, PhoneNumber, serviceObj);

			mCal_Expire.set(Calendar.MILLISECOND, 0);
			mCal_Expire.set(mCal_ChargeTime.get(Calendar.YEAR), mCal_ChargeTime.get(Calendar.MONTH),
					mCal_ChargeTime.get(Calendar.DATE), 23, 59, 59);

		}

		public void CreateChargeLog()
		{
			ChargeLogId mID = new ChargeLogId();
			mID.setPid(mSubObj.getId().getPid());
			mID.setPhoneNumber(mSubObj.getId().getPhoneNumber());
			mChargeLogObj.setId(mID);

			mChargeLogObj.setChannelId(MyConfig.ChannelType.SMS.GetValue().shortValue());
			mChargeLogObj.setChargeDate(MyDate.Date2Timestamp(mCal_ChargeTime));
			mChargeLogObj.setChargeTypeId(ChargeLog.ChargeType.Renew.GetValue());
			mChargeLogObj.setLogDate(MyDate.Date2Timestamp(Calendar.getInstance()));
			mChargeLogObj.setPartnerId(mSubObj.getPartnerId());
			mChargeLogObj.setPirce((float) amount);
			if (mParaType == ParaType.ChargeSuccess)
			{
				mChargeLogObj.setStatusId(ChargeLog.Status.ChargeSuccess.GetValue());
			}
			else
			{
				mChargeLogObj.setStatusId(ChargeLog.Status.ChargeFail.GetValue());
			}
		}

		public boolean Update_Sub() throws Exception
		{
			if (this.mMode == Mode.Check)
				return true;
			else return mSubObj.Update();
		}

		public boolean Insert_ChargeLog() throws Exception
		{
			if (this.mMode == Mode.Check)
				return true;
			else return mChargeLogObj.Save();

		}
	}

	public String resultRequest(String username, String password, String serviceid, String msisdn, String chargetime,
			String params, String mode, int amount)
	{
		
		ReceiveResultRequest mRequest = new ReceiveResultRequest();
		
		mRequest.username = username;
		mRequest.password = password;
		mRequest.serviceid = serviceid;
		mRequest.msisdn = msisdn;
		mRequest.chargetime = chargetime;
		mRequest.params = params;
		mRequest.mode = mode;
		mRequest.amount = amount;

		try
		{
			// kiểm tra đầu vào
			mRequest.mResult = mRequest.CheckPara();
			if (mRequest.mResult != Result.CheckParaIsOK)
				return mRequest.BuildResult(mRequest.mResult);

			mRequest.mResult = Result.Fail;		
			
			mRequest.GetSub();
			if (mRequest.mSubObj == null)
			{
				mRequest.mResult = Result.Fail;
				return mRequest.BuildResult(mRequest.mResult);
			}

			if (mRequest.mParaType == ParaType.ChargeSuccess)
			{
				mRequest.mSubObj.setStatusId(Subscriber.Status.Active.GetValue());

				mRequest.mSubObj.setExpiryDate(MyDate.Date2Timestamp(mRequest.mCal_Expire));
				mRequest.mSubObj.setRenewChargeDate(MyDate.Date2Timestamp(mRequest.mCal_ChargeTime));

				mRequest.mSubObj.setRetryChargeCount(0);

			}
			else
			{
				mRequest.mSubObj.setStatusId(Subscriber.Status.Pending.GetValue());
				mRequest.mSubObj.setRetryChargeDate(MyDate.Date2Timestamp(mRequest.mCal_ChargeTime));
				mRequest.mSubObj.setRetryChargeCount(mRequest.mSubObj.getRetryChargeCount() + 1);
			}

			if (!mRequest.Update_Sub())
			{
				mRequest.mResult = Result.Fail;
				return mRequest.BuildResult(mRequest.mResult);
			}

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
			mLog.log.info(String.format(LogFormat, username, password, serviceid, msisdn, chargetime, params, mode,
					amount, mRequest.mResult.GetValue()));
		}
	}
}
