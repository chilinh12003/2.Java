package pro.server;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import db.*;
import uti.MyLogger;

/**
 * Là lớp trừ tượng cho các lớp xử lý MO.
 * 
 * @author Administrator
 *
 */
public abstract class ProcessMOAbstract
{
	static MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath, ProcessMOAbstract.class.toString());

	public void start(Moqueue moQueueObj, Keyword mKeyword) throws Exception
	{
		try
		{
			List<Mtqueue> ListMTQueue = getMessages(moQueueObj, mKeyword);
			if (ListMTQueue != null)
			{
				Iterator<?> iter = ListMTQueue.iterator();
				int i = 1;
				for (; iter.hasNext();)
				{
					Mtqueue mtQueueObj = (Mtqueue) iter.next();
					sendMT(mtQueueObj);

					if (ListMTQueue.size() > 0 && i++ < ListMTQueue.size())
					{
						if(LocalConfig.TIME_DELAY_SEND_MT >0)
						{
							try
							{
								Thread.sleep(LocalConfig.TIME_DELAY_SEND_MT);
							}
							catch (InterruptedException e)
							{
								e.printStackTrace();
							}
						}
						
					}
				}
			}
			else
			{
				mLog.log.info(MyLogger.GetLog("SEND MT: MT tra ve la null",moQueueObj));
			}
		}
		catch (Exception e)
		{
			mLog.log.error(MyLogger.GetLog(moQueueObj));
		}
	}

	protected abstract List<Mtqueue> getMessages(Moqueue moQueueObj, Keyword mKeyword) throws Exception;

	private static int sendMT(Mtqueue mtQueueObj)
	{

		if ("".equalsIgnoreCase(mtQueueObj.getMt().trim()) || mtQueueObj.getMt() == null)
		{
			// Truong hop gui ban tin loi
			mLog.log.debug(MyLogger.GetLog("SEND MT: MT tra ve la null", mtQueueObj));
			return 1;
		}

		try
		{
			boolean Result= mtQueueObj.Save();

			if (!Result)
			{
				mLog.log.debug(MyLogger.GetLog("SEND MT: Khong insert duoc xuong MTQueue", mtQueueObj));
				return -1;
			}
			mLog.log.debug(MyLogger.GetLog("SEND MT: insert xuong MTQueue thanh cong", mtQueueObj));
			return 1;
		}
		catch (SQLException e)
		{
			mLog.log.error(MyLogger.GetLog("SEND MT: Khong insert duoc xuong MTQueue", mtQueueObj), e);
			return -1;
		}
		catch (Exception e)
		{
			mLog.log.error(MyLogger.GetLog("SEND MT: Khong insert duoc xuong MTQueue", mtQueueObj), e);
			return -1;
		}

	}

}
