package MyConn;

import java.util.Iterator;

import MyUtility.MyLogger;
import MyUtility.MyThread;

public class CheckConnection extends MyThread
{

	public CheckConnection()
	{

	}

	public void doRun()
	{
		while (MyConnection.AllowCheckConnection)
		{
			MyConnection.CheckConnectionRunning = true;
			try
			{
				for (Iterator<ObjectConnection> mItem = MyConnection.ListConnection.listIterator(); mItem.hasNext();)
				{
					ObjectConnection mObjectConn = mItem.next();
					if(!mObjectConn.CheckLiveTime() && !mObjectConn.IsExecuting)
					{
						mObjectConn.RealClose();
						mItem.remove();
					}
				}
			}
			catch (Exception ex)
			{
				System.out.println(ex.getMessage());
			}
			try
			{
				System.out.println("CHECK CONNECTION SE Delay " + MyConnection.DelayCheckConnection + " Giay.");
				System.out.println("---------------KET THUC CHECK CONNECTION --------------------");
				sleep(MyConnection.DelayCheckConnection * 1000);
			}
			catch (InterruptedException ex)
			{
				System.out.println("Error Sleep thread:"+ex.getMessage());
			}
			MyConnection.CheckConnectionRunning = false;
		}
	}

}
