package pro.Server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import my.ws.subscribe.Mode;
import db.*;
import db.DefineMt.MTType;
import uti.MyDate;
import uti.MyConvert;
import uti.MyLogger;

public class Deregister
{
	MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath(), this.getClass().toString());
	List<Mtqueue> listMTQueue = new ArrayList<Mtqueue>();

	Moqueue moQueueObj = null;
	Subscriber subObj = new Subscriber();
	SubLog mSubLog = new SubLog();

	DefineMt.MTType mMTType = MTType.RegFail;

	Calendar mCal_Current = Calendar.getInstance();

	String MTContent = "";

	Short PID = 0;

	Mode mMode = Mode.Nothing;

	String LogBeforeSub ="";
	private void Init(Moqueue moQueueObj) throws Exception
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

	public List<Mtqueue> AddToList() throws Exception
	{
		try
		{
			listMTQueue.clear();
			MTContent = Program.GetDefineMT_Message(mMTType);

			if (!MTContent.equalsIgnoreCase(""))
			{
				Mtqueue mtQueueObj = new Mtqueue(moQueueObj, PID, MTContent, mMTType,null);
				mtQueueObj.setSendTypeID(Mtqueue.SendType.NotSend.GetValue());
				listMTQueue.add(mtQueueObj);
			}

			return listMTQueue;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	protected void CreateUnSub() throws Exception
	{
		subObj.setChannelId(moQueueObj.getChannelId());
		subObj.setDeregDate(MyDate.Date2Timestamp(mCal_Current));

	}

	boolean InsertSubLog() throws Exception
	{
		if (mMode == Mode.Check)
			return true;

		mSubLog = new SubLog(subObj, (short) 0);
		mSubLog.setLogDate(MyDate.Date2Timestamp(Calendar.getInstance()));
		if (mSubLog.Save())
		{
			// Xóa trong table sub
			if (subObj.Delete())
			{
				return true;
			}
			else return false;
		}
		else return false;
	}

	void InsertChargeLog()
	{
		try
		{
			if (this.mMode != Mode.Real || mMTType != MTType.DeregSuccess)
			{
				return;
			}

			ChargeLog chargeObj = new ChargeLog();

			ChargeLogId mID = new ChargeLogId();
			mID.setPid(subObj.getId().getPid());
			mID.setPhoneNumber(subObj.getId().getPhoneNumber());

			chargeObj.setId(mID);

			chargeObj.setChannelId(moQueueObj.getChannelId());
			chargeObj.setChargeDate(moQueueObj.getReceiveDate());
			chargeObj.setChargeTypeId(ChargeLog.ChargeType.Deregister.GetValue());
			chargeObj.setStatusId(ChargeLog.Status.ChargeSuccess.GetValue());
			chargeObj.setLogDate(MyDate.Date2Timestamp(Calendar.getInstance()));
			chargeObj.setPartnerId(subObj.getPartnerId());
			chargeObj.setPrice((float) 0);

			if (!chargeObj.Save())
			{
				mLog.log.warn("Register Save ChargeLog Fail:" + MyLogger.GetLog(chargeObj));
			}
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
	}
	public MTType getMessages(Moqueue moQueueObj, Mode mMode) throws Exception
	{
		try
		{
			this.mMode = mMode;
			// Khoi tao
			Init(moQueueObj);

			// Lấy thông tin khách hàng đã đăng ký
			subObj = subObj.GetSub(PID, moQueueObj.getPhoneNumber());

			// Nếu chưa đăng ký dịch vụ
			if (subObj == null)
			{
				mMTType = MTType.DeregNotRegister;
				return mMTType;
			}

			LogBeforeSub = MyLogger.GetLog("BEFORE_SUB:",subObj);
			
			CreateUnSub();

			if(InsertSubLog())
			{
				mMTType = MTType.DeregSuccess;
				return mMTType;
			}
			mMTType = MTType.DeregFail;

			return mMTType;
		}
		catch (Exception ex)
		{
			mLog.log.error(MyLogger.GetLog(moQueueObj));
			mLog.log.error(MyLogger.GetLog(subObj), ex);
			mMTType = MTType.SystemError;
			return mMTType;
		}
		finally
		{
			InsertChargeLog();
			mLog.log.debug(MyLogger.GetLog(moQueueObj));
			mLog.log.debug(LogBeforeSub);
			mLog.log.debug( MyLogger.GetLog("AFTER_SUB:",subObj));
		}
	}

}
