package pro.check;

import java.util.Calendar;
import java.util.List;

import db.*;
import db.News.NewsType;
import pro.server.LocalConfig;
import pro.server.Program;
import uti.*;

/**
 * Kiểm tra liên tục, mỗi phút 1 lần.
 * 
 * @author chili
 * 
 */
public class CheckAndRun extends Thread
{
	MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath, this.getClass().toString());

	public CheckAndRun()
	{
		
	}

	public void run()
	{
		while (Program.processData)
		{
			try
			{
				Calendar calCurrent = Calendar.getInstance();
				
				/**
				 * Nghỉ 1 phút
				 */
				sleep(60 * 1000);
			}
			catch (Exception ex)
			{
				mLog.log.error(ex);
			}
		}
	}
	
	public void runBeginSession()
	{
		
	}
}