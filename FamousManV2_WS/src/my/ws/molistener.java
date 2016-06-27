package my.ws;

import java.util.Calendar;

import db.Moqueue;
import pro.Server.*;
import uti.*;

public class molistener
{
	public enum Result
	{
		/*
		 * 0 Đẩy tin nhắn vào queue thành công 1 Đẩy tin nhắn vào queue thất bại
		 * 200 Sai tham số 201 Sai tên đăng nhập hoặc mật khẩu 202 Sai định dạng
		 * dữ liệu 203 Sai tham số request 400 Server bận
		 */

		Success(0), Fail(1),
		/**
		 * Riêng enum này dùng để phục vụ cho hàm check invalid para input
		 */
		CheckParaIsOK(2), InputInvalid(200), AccountInvalid(201), InputFormatIncorrect(202), InpurtRequestInvalid(
				203),
		// Lỗi hệ thống
		SystemError(400), ;

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

	String LogFormat = "MORequest -->username:%s|password:%s|source:%s|dest:%s|content:%s|result:%s";

	class MoListenerRequest
	{
		public String username;
		public String password;
		public String source;
		public String dest;
		public String content;
		
		public String BuildResult(Result mResult)
		{
			return mResult.GetValue().toString();
		}

		public Result mResult = Result.Fail;
		public Calendar mCal_Current = Calendar.getInstance();

		public Moqueue moQueueObj = new Moqueue();
		public String PhoneNumber = "";
		public Result CheckPara()
		{
			try
			{
				if (username == null || username.equals("") || password == null || password.equals("") || source == null
						|| source.equals("") || dest == null || dest.equals("") || content == null || content.equals(""))
				{
					return Result.InputInvalid;
				}

				if (!Program.checkAccount(username, password))
				{
					return Result.AccountInvalid;
				}

				if (!dest.equalsIgnoreCase(LocalConfig.SHORT_CODE))
				{
					return Result.InpurtRequestInvalid;
				}

				PhoneNumber = MyCheck.CheckPhoneNumber(source);
				if (PhoneNumber.equals(""))
				{
					return Result.InputFormatIncorrect;
				}
				if (MyCheck.GetTelco(PhoneNumber) != MyConfig.Telco.VIETTEL)
				{
					return Result.InputFormatIncorrect;
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
			moQueueObj.setMo(content);
			moQueueObj.setMoinsertDate(MyDate.Date2Timestamp(Calendar.getInstance()));
			moQueueObj.setPhoneNumber(PhoneNumber);
			moQueueObj.setReceiveDate(MyDate.Date2Timestamp(mCal_Current));
			moQueueObj.setRequestId(MySeccurity.GenUniqueueID());
			moQueueObj.setShortCode(dest);
			moQueueObj.setTelcoId(((Integer) MyConfig.Telco.VIETTEL.GetValue()).shortValue());
		}
		
		public boolean SaveMOQueue()
		{
			try
			{
				return moQueueObj.Save();
			}
			catch (Exception ex)
			{
				mLog.log.error(ex);
			}
			return false;
		}
	}
	public String moRequest(String username, String password, String source, String dest, String content)
	{

		MoListenerRequest mRequest = new MoListenerRequest();
		try
		{
			mRequest.username = username;
			mRequest.password = password;
			mRequest.source = source;
			mRequest.dest = dest;
			mRequest.content = content;

			mRequest.mResult = mRequest.CheckPara();
			
			if (mRequest.mResult != Result.CheckParaIsOK)
				return mRequest.BuildResult(mRequest.mResult);
			
			mRequest.CreateMOQueue();
			
			if (mRequest.SaveMOQueue())
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
			mLog.log.info(String.format(LogFormat, username, password, source, dest, content, mRequest.mResult.GetValue()));
		}
	}
}
