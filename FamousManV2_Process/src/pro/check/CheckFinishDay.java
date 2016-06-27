package pro.check;

import java.util.Calendar;

import pro.server.LocalConfig;
import pro.server.Program;
import uti.MyLogger;

public class CheckFinishDay extends Thread
{
	MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath, this.getClass().toString());

	public CheckFinishDay()
	{

	}

	public void run()
	{
		while (Program.processData)
		{
			mLog.log.debug("---------------BAT DAU CHECK FINISH DAY --------------------");

			try
			{

				for (String PushTime : LocalConfig.FINISH_DAY_LIST_TIME)
				{
					Calendar mCal_Current = Calendar.getInstance();
					if (mCal_Current.get(Calendar.HOUR_OF_DAY) != Integer.parseInt(PushTime))
					{
						continue;
					}

					// Chạy thread Push tin
					RunThread();
				}

				mLog.log.debug("CHECK FINISH DAY SE DELAY " + LocalConfig.FINISH_DAY_TIME_DELAY + " PHUT.");
				mLog.log.debug("---------------KET THUC CHECK FINISH DAY--------------------");
				sleep(LocalConfig.FINISH_DAY_TIME_DELAY * 60 * 1000);
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
			for (int j = 0; j < LocalConfig.FINISH_DAY_PROCESS_NUMBER; j++)
			{
				FinishDay mFinishDay = new FinishDay((short) 0, LocalConfig.FINISH_DAY_PROCESS_NUMBER, j, 0,
						LocalConfig.FINISH_DAY_ROWCOUNT.intValue(), Calendar.getInstance().getTime());
				mFinishDay.setPriority(Thread.MAX_PRIORITY);
				mFinishDay.start();
			}

		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
	}
}
