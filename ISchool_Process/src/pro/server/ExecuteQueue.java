package pro.server;

import java.math.BigDecimal;

import uti.*;
import db.*;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class ExecuteQueue extends Thread
{

	MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath, this.getClass().toString());

	int threadID = 0;
	MyQueue queueMO = null;

	BigDecimal AM = new BigDecimal(-1);

	public ExecuteQueue(MyQueue queueMO, int threadID)
	{
		this.queueMO = queueMO;
		this.threadID = threadID;
	}

	public void run()
	{
		Moqueue moQueueObj = null;
		Keyword mKeyword = null;
		
		try
		{
			sleep(1500);
		}
		catch (InterruptedException ex)
		{
			ex.printStackTrace();
		}

		while (Program.processData)
		{
			try
			{
				// Lấy 1 item trong MO Queue và xóa nó đi
				moQueueObj =  (Moqueue) queueMO.remove();

				moQueueObj.setKeyword(moQueueObj.getMo());
				
				//Lấy keyword phù hợp cho MO này
				mKeyword = Program.mLoadKeyword.getKeyword(moQueueObj.getKeyword(), moQueueObj.getShortCode());

				mLog.log.info(MyLogger.GetLog(moQueueObj));

				moQueueObj.setKeyword(mKeyword.getKeyword());
				ProcessMOQueue(moQueueObj, mKeyword);
			}
			catch (Exception ex)
			{
				mLog.log.error(MyLogger.GetLog(moQueueObj), ex);
				queueMO.add(moQueueObj);
			}

		}

	}

	private void ProcessMOQueue(Moqueue moQueueObj, Keyword mKeyword)
	{
		ProcessMOAbstract delegate = null;
		try
		{
			// Ghi log xem class nào xử lý MO này
			mLog.log.info(MyLogger.GetLog(moQueueObj));

			// Khởi tạo đối tượng process xử lý keyword
			Class<?> delegateClass = Class.forName(mKeyword.getClassName());
			Object delegateObject = delegateClass.newInstance();
			delegate = (ProcessMOAbstract) delegateObject;

			delegate.start(moQueueObj, mKeyword);

		}
		catch (Exception e)
		{
			mLog.log.error(MyLogger.GetLog(moQueueObj), e);
		}

	}
}
