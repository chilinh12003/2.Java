package my.ws;

import java.util.Calendar;
import java.util.List;

import pro.Server.*;
import uti.MyCheck;
import uti.MyConfig;
import uti.MyDate;
import uti.MyLogger;
import uti.MySeccurity;
import db.Moqueue;
import db.Mtqueue;
import db.Suggest;
import db.DefineMt.MTType;

public class getcontent
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
		/*
		 * 0: Đăng ký sub 1: Hủy sub 2: Tạm dừng 3: Đăng ký lại
		 */

		Nothing(-1), Register(0), Deregister(1), Pause(300), ReturnReg(301), ;

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

	String LogFormat = "GetContent -->username:%s|password:%s|serviceid:%s|msisdn:%s|params:%s|mode:%s|amount:%s|result:%s";

	class GetContentRequest
	{

		public String username;
		public String password;
		public String serviceid;
		public String msisdn;
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

		public String MTContent = "";

		public Short PID = 0;

		public Suggest mSuggestObj = null;
		public Moqueue moQueueObj = new Moqueue();

		/**
		 * ID của đối tác, khi đăng ký qua các kênh của đối tác
		 */
		public Integer PartnerID = 0;
		public Result mResult = Result.Fail;
		public String Desc = "";
		public MTType mMTType = MTType.Fail;

		public String BuildResult(Result mResult, String MT)
		{
			Desc = MT;
			return mResult.GetValue().toString() + "|" + MT;
		}

		public Result CheckPara()
		{
			try
			{
				if (username == null || username.equals("") || password == null || password.equals("")
						|| serviceid == null || serviceid.equals("") || msisdn == null || msisdn.equals("")
						|| mode == null || mode.equals("") || amount < 0)
				{
					return Result.InputInvalid;
				}

				// Kiểm tra user, pass
				if (!Program.checkAccount(username, password))
				{
					return Result.AccountInvalid;
				}

				// Kiểm tra service ID
				if (!serviceid.equalsIgnoreCase(LocalConfig.SERVICE_ID))
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

				// Kiểm tra para
				/*
				 * int intPara = Integer.parseInt(params.trim()); mParaType =
				 * ParaType.FromValue(intPara);
				 * 
				 * if (mParaType == ParaType.Nothing) { return
				 * Result.InputInvalid; }
				 */

				// Kiêm tra mode
				if (mode.equalsIgnoreCase("Check"))
					this.mMode = Mode.Check;
				else if (mode.equalsIgnoreCase("REAL"))
					this.mMode = Mode.Real;

				if (mMode == Mode.Nothing)
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

		public void CreateMOQueue()
		{
			moQueueObj.setChannelId(MyConfig.ChannelType.SMS.GetValue().shortValue());
			moQueueObj.setMo("MUA");
			moQueueObj.setMoInsertDate(MyDate.Date2Timestamp(Calendar.getInstance()));
			moQueueObj.setPhoneNumber(PhoneNumber);
			moQueueObj.setReceiveDate(MyDate.Date2Timestamp(mCal_ChargeTime));
			moQueueObj.setRequestId(MySeccurity.GenUniqueueID());
			moQueueObj.setShortCode(LocalConfig.SHORT_CODE);
			moQueueObj.setTelcoId(((Integer) MyConfig.Telco.VIETTEL.GetValue()).shortValue());
		}

		public String BuySuggest() throws Exception
		{
			BuySuggest mBuySuggest = new BuySuggest();

			mMTType = mBuySuggest.getMessages(moQueueObj, mMode, amount);

			List<Mtqueue> listMTQueue = mBuySuggest.AddToList();
			if (mMTType == MTType.BuySugSuccess)
			{
				mResult = Result.Success;

				if (mMode == Mode.Real)
				{
					listMTQueue.get(0).Save();
					if (listMTQueue.size() > 1)
						listMTQueue.get(1).Save();
				}
				return BuildResult(mResult, listMTQueue.get(0).getMt());
			}
			else
			{
				mResult = Result.Fail;
				if (mMode == Mode.Real)
					listMTQueue.get(0).Save();
				return BuildResult(mResult, listMTQueue.get(0).getMt());
			}
		}

	}
	public String contentRequest(String username, String password, String serviceid, String msisdn, String params,
			String mode, int amount)
	{
		GetContentRequest mRequest = new GetContentRequest();
		mRequest.username = username;
		mRequest.password = password;
		mRequest.serviceid = serviceid;
		mRequest.msisdn = msisdn;
		mRequest.params = params;
		mRequest.mode = mode;
		mRequest.amount = amount;

		try
		{
			// kiểm tra đầu vào
			mRequest.mResult = mRequest.CheckPara();
			if (mRequest.mResult != Result.CheckParaIsOK)
				return mRequest.BuildResult(mRequest.mResult, mRequest.mResult.toString());

			mRequest.mResult = Result.Fail;

			mRequest.CreateMOQueue();

			return mRequest.BuySuggest();

		}
		catch (Exception ex)
		{
			mLog.log.error(ex);

			mRequest.mResult = Result.SystemError;
			return mRequest.BuildResult(mRequest.mResult, mRequest.mResult.toString());
		}
		finally
		{
			mLog.log.info(String.format(LogFormat, username, password, serviceid, msisdn, params, mode, amount,
					mRequest.mResult.GetValue() + "|" + mRequest.Desc));
		}
	}
}
