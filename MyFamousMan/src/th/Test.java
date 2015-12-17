package th;

import db.*;
public class Test
{
	public static void main(String[] args)
	{

		try
		{

			for (int i = 1; i < 20; i++)
			{
				PushMT mPushMT = new PushMT();
				mPushMT.setPriority(Thread.MAX_PRIORITY);
				mPushMT.Pindex = i;
				mPushMT.run();
			}

		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
