package my.ws;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.RestoreAction;

import my.ws.receiveresult.Result;
import javassist.runtime.Desc;
import db.DefineMt;
import db.Moqueue;
import db.Mtqueue;
import db.Service;
import db.DefineMt.MTType;
import pro.Server.*;
import uti.MyCheck;
import uti.MyConfig;
import uti.MyDate;
import uti.MyLogger;
import uti.MySeccurity;

public class subscribe
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

		Nothing(-1), Register(0), Deregister(1), Pause(2), Restore(3), ;

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

	String LogFormat = "Subscriber -->username:%s|password:%s|serviceid:%s|msisdn:%s|chargetime:%s|params:%s|mode:%s|amount:%s|command:%s|result:%s";

	class SubRequest
	{
		public String username;
		public String password;
		public String serviceid;
		public String msisdn;
		public String chargetime;
		public String params;
		public String mode;
		public int amount;
		public String command;
		public Service serviceObj;

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

		public Moqueue moQueueObj = new Moqueue();

		/**
		 * ID của đối tác, khi đăng ký qua các kênh của đối tác
		 */
		public Integer PartnerID = 0;
		public Result mResult = Result.Fail;
		public String Desc = "";
		public MTType mMTType = MTType.Fail;

		public void CreateMOQueue()
		{
			moQueueObj.setChannelId(MyConfig.ChannelType.SMS.GetValue().shortValue());
			moQueueObj.setMo(command);
			moQueueObj.setMoinsertDate(MyDate.Date2Timestamp(Calendar.getInstance()));
			moQueueObj.setPhoneNumber(PhoneNumber);
			moQueueObj.setReceiveDate(MyDate.Date2Timestamp(mCal_ChargeTime));
			moQueueObj.setRequestId(MySeccurity.GenUniqueueID());
			moQueueObj.setShortCode(LocalConfig.SHORT_CODE);
			moQueueObj.setTelcoId(((Integer) MyConfig.Telco.VIETTEL.GetValue()).shortValue());
		}

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
						|| chargetime == null || chargetime.equals("") || params == null || params.equals("")
						|| mode == null || mode.equals("") || amount < 0 || command == null || command.equals(""))
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

		/**
		 * Tiến hành đăng ký
		 * 
		 * @return
		 * @throws Exception
		 */
		public String Register() throws Exception
		{
			Register mReg = new Register();

			mMTType = mReg.getMessages(moQueueObj, mMode, amount, serviceObj);

			List<Mtqueue> listMTQueue = mReg.AddToList();
			if (mMTType == MTType.RegNewSuccess || mMTType == MTType.RegAgainSuccessFree)
			{
				mResult = Result.Success;
				if (listMTQueue.size() == 1)
				{
					if (mMode == Mode.Real)
						listMTQueue.get(0).Save();
					return BuildResult(mResult, listMTQueue.get(0).getMt());
				}
				else
				{
					if (mMode == Mode.Real)
					{
						listMTQueue.get(0).Save();
						if (listMTQueue.size() > 1)
							listMTQueue.get(1).Save();
					}
					return BuildResult(mResult, listMTQueue.get(0).getMt());
				}
			}
			else
			{
				mResult = Result.Fail;
				if (mMode == Mode.Real)
					listMTQueue.get(0).Save();
				return BuildResult(mResult, listMTQueue.get(0).getMt());
			}

		}

		public String Restore() throws Exception
		{
			RestoreRegister mRestore = new RestoreRegister();

			mMTType = mRestore.getMessages(moQueueObj, mMode, amount, serviceObj);

			List<Mtqueue> listMTQueue = mRestore.AddToList();
			if (mMTType == MTType.RestoreSuccess)
			{
				mResult = Result.Success;
				
				if (listMTQueue.size() == 1)
				{
					if (mMode == Mode.Real)
						listMTQueue.get(0).Save();
					return BuildResult(mResult, listMTQueue.get(0).getMt());
				}
				else
				{
					if (mMode == Mode.Real)
					{
						listMTQueue.get(0).Save();
						if (listMTQueue.size() > 1)
							listMTQueue.get(1).Save();
					}
					return BuildResult(mResult, listMTQueue.get(0).getMt());
				}
			}
			else
			{
				mResult = Result.Fail;
				if (mMode == Mode.Real)
					listMTQueue.get(0).Save();
				return BuildResult(mResult, listMTQueue.get(0).getMt());
			}
		}

		public String Deregister() throws Exception
		{
			Deregister mDereg = new Deregister();

			mMTType = mDereg.getMessages(moQueueObj, mMode, serviceObj);

			List<Mtqueue> listMTQueue = mDereg.AddToList();
			if (mMTType == MTType.DeregSuccess)
			{
				mResult = Result.Success;
				listMTQueue.get(0).Save();
				return BuildResult(mResult, listMTQueue.get(0).getMt());
			}
			else
			{
				mResult = Result.Fail;
				listMTQueue.get(0).Save();
				return BuildResult(mResult, listMTQueue.get(0).getMt());
			}
		}
	}

	public String subRequest(String username, String password, String serviceid, String msisdn, String chargetime,
			String params, String mode, int amount, String command)
	{
		SubRequest mReqeust = new SubRequest();
		mReqeust.username = username;
		mReqeust.password = password;
		mReqeust.serviceid = serviceid;
		mReqeust.msisdn = msisdn;
		mReqeust.chargetime = chargetime;
		mReqeust.params = params;
		mReqeust.mode = mode;
		mReqeust.amount = amount;
		mReqeust.command = command;

		try
		{
			// kiểm tra đầu vào
			mReqeust.mResult = mReqeust.CheckPara();
			if (mReqeust.mResult != Result.CheckParaIsOK)
				return mReqeust.BuildResult(mReqeust.mResult, mReqeust.mResult.toString());

			mReqeust.mResult = Result.Fail;

			mReqeust.CreateMOQueue();

			if (mReqeust.mParaType == ParaType.Register)
			{
				return mReqeust.Register();
			}
			else if (mReqeust.mParaType == ParaType.Restore)
			{
				return mReqeust.Restore();
			}
			else if (mReqeust.mParaType == ParaType.Deregister)
			{
				return mReqeust.Deregister();
			}

			return mReqeust.BuildResult(mReqeust.mResult, mReqeust.mResult.toString());

		}
		catch (Exception ex)
		{
			mLog.log.error(ex);

			mReqeust.mResult = Result.SystemError;
			return mReqeust.BuildResult(mReqeust.mResult, mReqeust.mResult.toString());
		}
		finally
		{
			mLog.log.info(String.format(LogFormat, username, password, serviceid, msisdn, chargetime, params, mode,
					amount, command, mReqeust.mResult.GetValue() + "|" + mReqeust.Desc));
		}
	}

}
