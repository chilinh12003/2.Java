package pro.server;
import java.util.Calendar;
import java.util.List;

import javax.xml.namespace.QName;

import db.Mtlog;
import db.Mtqueue;
import uti.MyDate;
import uti.MyLogger;
import uti.MyQueue;

import java.net.URL;
import pro.callWS.Smsws;
import pro.callWS.SmswsPortType;

public class LoadMT extends Thread
{

	static MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath, LoadMT.class.toString());

	Mtqueue mtqueueDB = new Mtqueue();
	static Mtlog mtlog = new Mtlog();

	MyQueue queueMTRetry = null;
	int threadNumber = 1;
	int threadIndex = 1;

	int maxRowCount = 10;

	public LoadMT()
	{
	}

	public LoadMT(MyQueue queueMTRetry, int threadNumber, int threadIndex, int maxRowCount)
	{
		try
		{
			this.queueMTRetry = queueMTRetry;
			this.threadNumber = threadNumber;
			this.threadIndex = threadIndex;
			this.maxRowCount = maxRowCount;
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
	}

	public void run()
	{

		while (Program.getData)
		{
			try
			{
				List<Mtqueue> mList = mtqueueDB.GetByThread(threadNumber, threadIndex, maxRowCount);
				for (Mtqueue item : mList)
				{
					SendMT(queueMTRetry, item);
				}

				if (mList.size() > 0)
					mtqueueDB.Delete(mList);
			}
			catch (Exception ex)
			{
				mLog.log.error(ex);
			}

			try
			{
				sleep(LocalConfig.TIME_DELAY_LOAD_MT);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}

		}

	}

	public static void SendMT(MyQueue queueMTRetry, Mtqueue mtqueueObj)
	{
		boolean isFinishSendMT = false;
		String Result = "-1";
		try
		{

			mtqueueObj.setSendDate(MyDate.Date2Timestamp(Calendar.getInstance()));
			mtqueueObj.setRetryCount((short) (mtqueueObj.getRetryCount() + (short) 1));

			if (mtqueueObj.getSendTypeID().shortValue() == Mtqueue.SendType.SendToUser.GetValue().shortValue())
			{
				// Tiến hành gửi sang telco
				Result = SendSMS(mtqueueObj.getPhoneNumber(), mtqueueObj.getMt());

				if (Result.equals("0"))
				{
					mtqueueObj.setStatusId(Mtqueue.Status.SendSuccess.GetValue());
				}
				else
				{
					mtqueueObj.setStatusId(Mtqueue.Status.SendMTFail.GetValue());
				}

			}
			else
			{
				//nếu MT là loại không gửi đến KH thì mặc định coi là gửi thanh công
				mtqueueObj.setStatusId(Mtqueue.Status.SendSuccess.GetValue());
			}
				
			mtqueueObj.setDoneDate(MyDate.Date2Timestamp(Calendar.getInstance()));

			// Nếu gửi không thành công và số lần retry còn được phép, thì add
			// to queue retry
			if (mtqueueObj.getStatusId().shortValue() != Mtqueue.Status.SendSuccess.GetValue().shortValue()
					&& mtqueueObj.getRetryCount().shortValue() <= LocalConfig.SENDMT_MAX_RETRY.shortValue())
			{
				mtqueueObj.setStatusId(Mtqueue.Status.RetrySendMT.GetValue());
				queueMTRetry.add(mtqueueObj);
			}
			else
			{
				isFinishSendMT = true;
				// Insert xuống mtlog
				Mtlog mtlogObj = new Mtlog(mtqueueObj);
				if (!mtlog.Save(mtlogObj))
				{
					mLog.log.info("Khong insert xuong MTLog duoc:" + MyLogger.GetLog(mtlogObj));
				}
				else
				{
					mLog.log.info("SEND MT TO TELCO:" + MyLogger.GetLog(mtlogObj));
				}
			}

		}
		catch (Exception ex)
		{
			mLog.log.error("Error send MT:" + MyLogger.GetLog(mtqueueObj), ex);
		}
		finally
		{
			if (isFinishSendMT)
			{
				mLog.log.info("SEND MT TO TELCO:" + MyLogger.GetLog(mtqueueObj) + "|ResultViettel:" + Result);
			}
		}
	}

	static String SendSMS(String PhoneNumber, String MT)
	{
	/*	if(PhoneNumber != "")
			return "0";*/
		
		String Result = "1";
		try
		{
			Smsws mSMSWS = new Smsws(new URL(LocalConfig.SENDMT_LINK), new QName("http://smsws/", "smsws"));

			SmswsPortType mPort = mSMSWS.getSmswsSOAP11PortHttp();

			Result = mPort.smsRequest(LocalConfig.SENDMT_USERNAME, LocalConfig.SENDMT_PASSWORK, PhoneNumber, MT,
					LocalConfig.SHORT_CODE, LocalConfig.SERVICE_NAME, "text");

		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
		return Result;

	}
}
