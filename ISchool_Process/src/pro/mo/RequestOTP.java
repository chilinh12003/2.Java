package pro.mo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pro.server.LocalConfig;
import pro.server.ProcessMOAbstract;
import pro.server.Program;
import uti.MyConvert;
import uti.MyDate;
import uti.MyLogger;
import uti.MySeccurity;
import db.DefineMt;
import db.Keyword;
import db.Moqueue;
import db.Mtqueue;
import db.Otplog;
import db.Subscriber;
import db.DefineMt.MTType;

public class RequestOTP extends ProcessMOAbstract
{

	MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath, this.getClass().toString());
	List<Mtqueue> listMTQueue = new ArrayList<Mtqueue>();

	Moqueue moQueueObj = null;
	Subscriber mSubObj = new Subscriber();

	Otplog mOtpLog = new Otplog();
	
	Calendar mCal_Current = Calendar.getInstance();

	DefineMt.MTType mMTType = DefineMt.MTType.RegFail;

	
	String MTContent = "";

	Short PID = 0;
	String OTPCode = "";
	private void Init(Moqueue moQueueObj, Keyword mKeyword) throws Exception
	{
		try
		{
			this.moQueueObj = moQueueObj;
			PID = ((Integer) MyConvert.GetPIDByMSISDN(moQueueObj.getPhoneNumber(), LocalConfig.MAX_PID)).shortValue();

		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	private List<Mtqueue> AddToList() throws Exception
	{
		try
		{
			listMTQueue.clear();
			MTContent = Program.GetDefineMT_Message(mMTType);

			if (!MTContent.equalsIgnoreCase(""))
			{
				if (mMTType == MTType.RequestOTPSuccess)
				{
					
					MTContent = MTContent.replace("[OTPCode]", OTPCode);
				}
				Mtqueue mtQueueObj = new Mtqueue(moQueueObj, PID, MTContent, mMTType, null);

				listMTQueue.add(mtQueueObj);
			}

			return listMTQueue;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	protected List<Mtqueue> getMessages(Moqueue moQueueObj, Keyword mKeyword) throws Exception
	{
		try
		{
			// Khoi tao
			Init(moQueueObj, mKeyword);

			// Lấy thông tin khách hàng đã đăng ký
			mSubObj = mSubObj.GetSub(PID, moQueueObj.getPhoneNumber());

			// Chưa đăng ký
			if (mSubObj == null)
			{
				mMTType = MTType.RequestOTPNotReg;
				return AddToList();
			}

			//Lấy thông tin OPT chưa sử dụng của khách hàng này
			mOtpLog = mOtpLog.getOTPLog(mSubObj.getId().getPid(), mSubObj.getId().getPhoneNumber(), Otplog.Status.NotUse);
			//nếu còn mã nào chưa sử dụng, thì không cần tạo nữa mà lấy lại gửi KH
			if(mOtpLog != null)
			{
				OTPCode = mOtpLog.getOtpcode();
			}
			else
			{
				OTPCode = MySeccurity.RandomString(5);
				mOtpLog = new Otplog();
				mOtpLog.setCreateDate(MyDate.Date2Timestamp(Calendar.getInstance()));
				mOtpLog.setLoginDate(null);
				mOtpLog.setOtpcode(OTPCode);
				mOtpLog.setPhoneNumber(mSubObj.getId().getPhoneNumber());
				mOtpLog.setPid(mSubObj.getId().getPid());
				mOtpLog.setStatusId(Otplog.Status.NotUse.GetValue());
				
				if (!mOtpLog.Save())
				{
					mLog.log.warn("OTPLog Save Fail:" + MyLogger.GetLog(mOtpLog));
					mMTType = MTType.RequestOTPFail;
					return AddToList();
				}
			}
			 
			mMTType = MTType.RequestOTPSuccess;
			return AddToList();
		}
		catch (Exception ex)
		{
			mLog.log.error(MyLogger.GetLog(moQueueObj));
			mLog.log.error(MyLogger.GetLog(mSubObj), ex);
			
			mMTType = MTType.RequestOTPFail;
			return AddToList();
		}
		finally
		{
			mLog.log.debug(MyLogger.GetLog(moQueueObj));
			if(mOtpLog != null)
				mLog.log.debug(MyLogger.GetLog(mOtpLog));	
		}
	}

}
