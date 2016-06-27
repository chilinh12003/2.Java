package pro.mo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import db.*;
import db.DefineMt.MTType;
import pro.server.LocalConfig;
import pro.server.ProcessMOAbstract;
import pro.server.Program;
import uti.MyConvert;
import uti.MyLogger;

/**
 * 
 * @author Administrator
 * 
 */
public class InvalidProcess extends ProcessMOAbstract
{
	MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath, this.getClass().toString());
	List<Mtqueue> listMTQueue = new ArrayList<Mtqueue>();

	Moqueue moQueueObj = null;

	Calendar mCal_Current = Calendar.getInstance();

	DefineMt.MTType mMTType = MTType.Invalid;

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
			String MTContent = Program.GetDefineMT_Message(mMTType);
			if (!MTContent.equalsIgnoreCase(""))
			{
				Mtqueue mtQueueObj = new Mtqueue(null,moQueueObj, PID, MTContent, MTType.Invalid,null);
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
			return AddToList();
		}
		catch (Exception ex)
		{
			mLog.log.error(MyLogger.GetLog(moQueueObj), ex);
			mMTType = MTType.SystemError;
			return AddToList();
		}
		finally
		{
			mLog.log.debug(MyLogger.GetLog(moQueueObj));
		}
	}

}