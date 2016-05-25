package MyUtility;

import java.util.Vector;

public abstract class MyThread extends Thread
{
	public static Vector<Long> ActiveThreasID = new Vector<Long>();

	public MyThread()
	{

	}

	@Override
	public final void run()
	{
		try
		{
			ActiveThreasID.add(this.getId());
			doRun();
		}
		finally
		{
			if (ActiveThreasID.contains(this.getId()))
				ActiveThreasID.remove(this.getId());
		}
	}

	public abstract void doRun();

}
