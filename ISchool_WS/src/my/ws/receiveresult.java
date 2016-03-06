package my.ws;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import db.ChargeLog;
import db.ChargeLogId;
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

	String LogFormat = "ChargeLog -->username:%s|password:%s|serviceid:%s|msisdn:%s|chargetime:%s|params:%s|mode:%s|amount:%s|detail:%s|Chargecode:%s|nextRenewalTime:%s|transid:%s|result:%s";

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
		public String detail;
		public String Chargecode;
		public String nextRenewalTime;
		public String transid;

		public String PhoneNumber = "";
		public ParaType mParaType = ParaType.Nothing;

		/**
		 * Cho biết mode là real thật
		 */
		public Mode mMode = Mode.Nothing;

		/**
		 * Chuyển từ chargetime sang
		 */
		
		public Calendar calChargeTime = Calendar.getInstance();
		public Calendar calNextRenewalTime = Calendar.getInstance();
		public Calendar calCurrent = Calendar.getInstance();
		public Calendar calExpire = Calendar.getInstance();

		public String MTContent = "";

		public Short PID = 0;

		public String LogBeforeSub = "";
		public Subscriber subObj = new Subscriber();
		public ChargeLog mChargeLogObj = new ChargeLog();

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

				try
				{
					// Kiểm tra định dạng ngày tháng nhập vào
					Date TempTime = MyConfig.Get_DateFormat_yyyymmddhhmmss().parse(chargetime);
					calChargeTime.setTime(TempTime);

					if(nextRenewalTime != null)
					{
						// Kiểm tra định dạng ngày tháng nhập vào
						Date TempTime_2 = MyConfig.Get_DateFormat_yyyymmddhhmmss().parse(nextRenewalTime);
						calNextRenewalTime.setTime(TempTime_2);
					}
					else
					{
						calNextRenewalTime = null;
					}
					
					
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
			subObj = mSubscriber.GetSub(PID, PhoneNumber);

			calCurrent.set(Calendar.MILLISECOND, 0);
			calCurrent.set(calChargeTime.get(Calendar.YEAR), calChargeTime.get(Calendar.MONTH),
					calChargeTime.get(Calendar.DATE), 23, 59, 59);

		}

		public void CreateChargeLog()
		{
			ChargeLogId mID = new ChargeLogId();
			mID.setPid(subObj.getId().getPid());
			mID.setPhoneNumber(subObj.getId().getPhoneNumber());
			mChargeLogObj.setId(mID);

			mChargeLogObj.setChannelId(MyConfig.ChannelType.SMS.GetValue().shortValue());
			mChargeLogObj.setChargeDate(MyDate.Date2Timestamp(calChargeTime));
			mChargeLogObj.setChargeTypeId(ChargeLog.ChargeType.Renew.GetValue());
			mChargeLogObj.setLogDate(MyDate.Date2Timestamp(Calendar.getInstance()));
			mChargeLogObj.setPartnerId(subObj.getPartnerId());
			mChargeLogObj.setPrice((float) amount);
			

			mChargeLogObj.setDetail(detail);
			mChargeLogObj.setChargeCode(Chargecode);
			mChargeLogObj.setNextRenewalTime(MyDate.Date2Timestamp(calNextRenewalTime));
			mChargeLogObj.setTransId(transid);
			
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
			else return subObj.Update();
		}

		public boolean Insert_ChargeLog() throws Exception
		{
			if (this.mMode == Mode.Check)
				return true;
			else return mChargeLogObj.Save();

		}
	}

	public String resultRequest(String username, String password, String serviceid, String msisdn, String chargetime,
			String params, String mode, int amount, 
			String detail,
			String Chargecode,
			String nextRenewalTime,
			String transid)
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
		mRequest.detail = detail;
		mRequest.Chargecode = Chargecode;
		mRequest.nextRenewalTime = nextRenewalTime;
		mRequest.transid = transid;
		

		try
		{
			// kiểm tra đầu vào
			mRequest.mResult = mRequest.CheckPara();
			if (mRequest.mResult != Result.CheckParaIsOK)
				return mRequest.BuildResult(mRequest.mResult);

			mRequest.mResult = Result.Fail;

			mRequest.GetSub();
			if (mRequest.subObj == null)
			{
				mRequest.mResult = Result.Fail;
				return mRequest.BuildResult(mRequest.mResult);
			}

			mRequest.LogBeforeSub = MyLogger.GetLog("BEFORE_SUB:",mRequest.subObj);
			
			if (mRequest.mParaType == ParaType.ChargeSuccess)
			{
				mRequest.subObj.setStatusId(Subscriber.Status.Active.GetValue());

				mRequest.subObj.setExpiryDate(MyDate.Date2Timestamp(mRequest.calCurrent));
				mRequest.subObj.setRenewChargeDate(MyDate.Date2Timestamp(mRequest.calChargeTime));

				mRequest.subObj.setRetryChargeCount(0);

				mRequest.subObj.setChargeMark(LocalConfig.RenewMark);
			}
			else
			{
				mRequest.subObj.setStatusId(Subscriber.Status.Pending.GetValue());
				mRequest.subObj.setRetryChargeDate(MyDate.Date2Timestamp(mRequest.calChargeTime));
				mRequest.subObj.setRetryChargeCount(mRequest.subObj.getRetryChargeCount() + 1);
				mRequest.subObj.setChargeMark(0);
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
					amount,detail,Chargecode,nextRenewalTime,transid, mRequest.mResult.GetValue()));
			mLog.log.debug(mRequest.LogBeforeSub);
			mLog.log.debug( MyLogger.GetLog("AFTER_SUB:",mRequest.subObj));
		}
	}
}
