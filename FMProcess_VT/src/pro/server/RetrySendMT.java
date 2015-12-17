package pro.server;

import db.Mtlog;
import db.Mtqueue;
import uti.MyLogger;
import uti.MyQueue;

public class RetrySendMT extends Thread
{

	MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath, RetrySendMT.class.toString());

	Mtqueue mtqueueDB = new Mtqueue();
	Mtlog mtlog = new Mtlog();

	MyQueue queueMTRetry = null;
	MyQueue queueMTRetry_Next = new MyQueue();
	

	int TimeDelay = LocalConfig.TIME_DELAY_RETRY_SEND_MT*1000;
	
	public RetrySendMT()
	{
	}

	public RetrySendMT(MyQueue queueMTRetry)
	{
		try
		{
			this.queueMTRetry = queueMTRetry;			
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
	}

	void Delay()
	{
		try
		{
			sleep(TimeDelay);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	public void run()
	{

		Delay();
		
		while (Program.getData)
		{
			
			try
			{
				// Xử lý các MTQueue
				while (queueMTRetry.getSize() > 0)
				{
					Mtqueue mtqueueObj = (Mtqueue) queueMTRetry.remove();
					// Không gửi được thì add vào queue next
					LoadMT.SendMT(queueMTRetry_Next, mtqueueObj);
				}

				// Add lại quueue từ queue next
				while (queueMTRetry_Next.getSize() > 0)
				{
					Mtqueue mtqueueObj = (Mtqueue) queueMTRetry_Next.remove();
					queueMTRetry.add(mtqueueObj);
				}
			}
			catch (Exception ex)
			{
				mLog.log.error(ex);
			}

			Delay();
		}

	}	
}
