package MyProcessServer;

import java.util.Hashtable;

import db.define.MyTableModel;

import uti.utility.MyLogger;
public class LoadBlackList extends Thread
{
	MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath, this.getClass().toString());
	private Hashtable<?, ?> blacklist;
	public boolean isLoaded = false;
	public void run()
	{

		while (Program.processData)
		{
			try
			{
				blacklist = GetFromDB();
				isLoaded = true;
				try
				{
					sleep(1000 * 60);
				}
				catch (InterruptedException ex3)
				{
					mLog.log.error(ex3);
				}
			}
			catch (Exception ex3)
			{
				mLog.log.error("Loi khi doc cau hinh:" + ex3.toString(), ex3);
			}

		}
	}

	
	/**
	 * lấy danh sách block list trong DB
	 * @return
	 */
	private Hashtable<String, String> GetFromDB()
	{
		Hashtable<String, String> mList = new Hashtable<String, String>();

		try
		{
			dat.gateway.blacklist mBlackList = new dat.gateway.blacklist(LocalConfig.mDBConfig_MySQL);

			MyTableModel mTable = mBlackList.Select(0);

			for (int i = 0; i < mTable.GetRowCount(); i++)
			{
				mList.put(mTable.GetValueAt(i, "user_id").toString(), mTable.GetValueAt(i, "user_id").toString());
			}

		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}

		return mList;
	}

	/**
	 * Kiểm tra 1 số điện thoại có nằm trong blacklist hay không
	 * @param MSISDN
	 * @return
	 */
	public boolean CheckMSISDN(String MSISDN)
	{
		try
		{
			if(isLoaded)
			{
				return blacklist.containsKey(MSISDN);
			}
			else
			{
				return false;
			}
		}
		catch(Exception ex)
		{
			mLog.log.error(ex);
			
		}
		return false;
	}
}
