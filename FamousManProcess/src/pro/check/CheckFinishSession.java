package pro.check;

import java.util.Calendar;

import pro.server.LocalConfig;
import pro.server.Program;
import uti.utility.*;


public class CheckFinishSession extends Thread
{
	MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath, this.getClass().toString());

	public CheckFinishSession()
	{

	}

	public void run()
	{
		while (Program.processData)
		{
			mLog.log.debug("---------------BAT DAU CHECK FINISH SESSION --------------------");

			try
			{
				//Chỉ chạy vào ngày được cấu hình
				if (Calendar.getInstance().get(Calendar.DATE) == LocalConfig.FINISH_SESSION_DAY_OF_MONTH.intValue())
				{
					for (String PushTime : LocalConfig.FINISH_SESSION_LIST_TIME)
					{
						Calendar mCal_Current = Calendar.getInstance();
						if (mCal_Current.get(Calendar.HOUR_OF_DAY) != Integer.parseInt(PushTime))
						{
							continue;
						}

						// Chạy thread Push tin
						RunThread();
					}
				}

				mLog.log.debug("CHECK FINISH SESSION SE DELAY " + LocalConfig.FINISH_SESSION_TIME_DELAY + " PHUT.");
				mLog.log.debug("---------------KET THUC CHECK FINISH SESSION--------------------");
				sleep(LocalConfig.FINISH_SESSION_TIME_DELAY * 60 * 1000);
			}
			catch (Exception ex)
			{
				mLog.log.error(ex);
			}
		}

	}
	void RunThread()
	{
		try
		{
			mLog.log.debug("-------------------------");
			mLog.log.debug("Bat dau FinishSession");

			FinishSession mFinishSession = new FinishSession();
			mFinishSession.setPriority(Thread.MAX_PRIORITY);
			mFinishSession.start();

		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
	}
}
