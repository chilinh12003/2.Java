package pro.server;
import java.util.List;
import db.Moqueue;
import uti.MyLogger;
import uti.MyQueue;

public class LoadMO extends Thread
{

	MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath, LoadMO.class.toString());

	Moqueue moqueueDB = new Moqueue();
	MyQueue queueMO = null;
	int threadNumber = 1;
	int threadIndex = 1;

	// private Logger logger = null;
	static int TIME_DELAY_LOAD_MO = 100;

	public LoadMO()
	{
	}

	public LoadMO(MyQueue queueMO, int threadNumber, int threadIndex)
	{
		try
		{
			this.queueMO = queueMO;
			this.threadNumber = threadNumber;
			this.threadIndex = threadIndex;
		}
		catch (Exception ex)
		{
			mLog.log.error("Error Contructor LoadMO(MsgQueue,int,int,int)", ex);
		}
	}

	public void run()
	{

		while (Program.getData)
		{
			try
			{
				List<Moqueue> mList = moqueueDB.GetByThread(threadNumber, threadIndex);
				for (Moqueue item : mList)
				{
					queueMO.add(item);
					mLog.log.info(MyLogger.GetLog("LoadMO AddToQueue", item));
				}

				if (mList.size() > 0)
					moqueueDB.Delete(mList);
			}
			catch (Exception ex)
			{
				mLog.log.error(ex);
			}

			try
			{
				sleep(TIME_DELAY_LOAD_MO);
			}
			catch (InterruptedException ex)
			{
				mLog.log.error(ex);
			}
		}

	}

}
