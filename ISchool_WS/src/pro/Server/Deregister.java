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
	Subscriber mSubObj = new Subscriber();
	SubLog mSubLog = new SubLog();

	DefineMt.MTType mMTType = MTType.RegFail;

	Calendar mCal_Current = Calendar.getInstance();

	String MTContent = "";

	Short PID = 0;

	Mode mMode = Mode.Nothing;

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
		mSubObj.setChannelId(moQueueObj.getChannelId());
		mSubObj.setDeregDate(MyDate.Date2Timestamp(mCal_Current));

	}

	boolean InsertSubLog() throws Exception
	{
		if (mMode == Mode.Check)
			return true;

		mSubLog = new SubLog(mSubObj, (short) 0);
		mSubLog.setLogDate(MyDate.Date2Timestamp(Calendar.getInstance()));
		if (mSubLog.Save())
		{
			// Xóa trong table sub
			if (mSubObj.Delete())
			{
				return true;
			}
			else return false;
		}
		else return false;
	}

	public MTType getMessages(Moqueue moQueueObj, Mode mMode) throws Exception
	{
		try
		{
			this.mMode = mMode;
			// Khoi tao
			Init(moQueueObj);

			// Lấy thông tin khách hàng đã đăng ký
			mSubObj = mSubObj.GetSub(PID, moQueueObj.getPhoneNumber());

			// Nếu chưa đăng ký dịch vụ
			if (mSubObj == null)
			{
				mMTType = MTType.DeregNotRegister;
				return mMTType;
			}

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
			mLog.log.error(MyLogger.GetLog(mSubObj), ex);
			mMTType = MTType.SystemError;
			return mMTType;
		}
		finally
		{
			mLog.log.debug(MyLogger.GetLog(moQueueObj));
		}
	}

}
