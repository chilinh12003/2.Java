package pro.mo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pro.server.LocalConfig;
import pro.server.ProcessMOAbstract;
import pro.server.Program;
import uti.MyConvert;
import uti.MyLogger;
import db.DefineMt;
import db.Keyword;
import db.Moqueue;
import db.Mtqueue;
import db.Subscriber;
import db.DefineMt.MTType;

public class RequestMark extends ProcessMOAbstract
{

	MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath, this.getClass().toString());
	List<Mtqueue> listMTQueue = new ArrayList<Mtqueue>();

	Moqueue moQueueObj = null;
	Subscriber mSubObj = new Subscriber();

	Calendar mCal_Current = Calendar.getInstance();

	DefineMt.MTType mMTType = DefineMt.MTType.RegFail;

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
				if (mMTType == MTType.RequestMarkSuccess)
				{
					Integer WeekMark = mSubObj.getWeekMark() + mSubObj.getAddMark() + mSubObj.getAnswerMark()
							+ mSubObj.getChargeMark() + mSubObj.getBuyMark();
					MTContent = MTContent.replace("[WeekMark]", WeekMark.toString());
				}
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
				mMTType = MTType.RequestMarkNotReg;
				return AddToList();
			}

			mMTType = MTType.RequestMarkSuccess;
			return AddToList();
		}
		catch (Exception ex)
		{
			mLog.log.error(MyLogger.GetLog(moQueueObj));
			mLog.log.error(MyLogger.GetLog(mSubObj), ex);
			mMTType = MTType.RequestMarkFail;
			return AddToList();
		}
		finally
		{
			mLog.log.debug(MyLogger.GetLog(moQueueObj));
		}
	}

}
