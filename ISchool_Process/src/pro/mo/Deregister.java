package pro.mo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import db.*;
import db.DefineMt.MTType;
import pro.server.LocalConfig;
import pro.server.ProcessMOAbstract;
import pro.server.Program;
import uti.MyDate;
import uti.MyConvert;
import uti.MyLogger;

public class Deregister extends ProcessMOAbstract
{
	MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath, this.getClass().toString());
	List<Mtqueue> listMTQueue = new ArrayList<Mtqueue>();

	Moqueue moQueueObj = null;
	Subscriber mSubObj = new Subscriber();
	SubLog mSubLog = new SubLog();

	DefineMt.MTType mMTType = MTType.RegFail;

	Calendar mCal_Current = Calendar.getInstance();

	String MTContent = "";

	Short PID = 0;
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
				Mtqueue mtQueueObj = new Mtqueue(moQueueObj, PID, MTContent, mMTType,null);

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

	protected List<Mtqueue> getMessages(Moqueue moQueueObj, Keyword mKeyword) throws Exception
	{
		try
		{
			// Khoi tao
			Init(moQueueObj, mKeyword);

			// Lấy thông tin khách hàng đã đăng ký
			mSubObj = mSubObj.GetSub(PID, moQueueObj.getPhoneNumber());

			// Nếu chưa đăng ký dịch vụ
			if (mSubObj == null)
			{
				mMTType = MTType.DeregNotRegister;
				return AddToList();
			}

			CreateUnSub();

			mSubLog = new SubLog(mSubObj, (short) 0);
			mSubLog.setLogDate(MyDate.Date2Timestamp(Calendar.getInstance()));
			if (mSubLog.Save())
			{
				// Xóa trong table sub
				if (mSubObj.Delete())
				{
					mMTType = MTType.DeregSuccess;
					return AddToList();
				}
			}

			mMTType = MTType.DeregFail;

			return AddToList();
		}
		catch (Exception ex)
		{
			mLog.log.error(MyLogger.GetLog(moQueueObj));
			mLog.log.error(MyLogger.GetLog(mSubObj), ex);
			mMTType = MTType.DeregFail;
			return AddToList();
		}
		finally
		{
			mLog.log.debug(MyLogger.GetLog(moQueueObj));
		}
	}

}
