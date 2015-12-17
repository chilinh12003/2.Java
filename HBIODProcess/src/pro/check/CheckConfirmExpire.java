package pro.check;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import pro.server.Common;
import pro.server.CurrentData;
import pro.server.LocalConfig;
import pro.server.Program;
import uti.utility.MyConfig;
import uti.utility.VNPApplication;
import uti.utility.MyLogger;
import uti.utility.MyConfig.ChannelType;
import dat.content.DefineMT;
import dat.content.News;
import dat.content.NewsObject;
import dat.content.DefineMT.MTType;
import dat.content.News.NewsType;
import dat.history.MOLog;
import dat.history.MOObject;
import dat.sub.SubNews;
import dat.sub.SubNewsObject;
import dat.sub.Subscriber;
import dat.sub.SubscriberObject;
import db.define.MyDataRow;
import db.define.MyTableModel;

public class CheckConfirmExpire extends Thread
{
	MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath, this.getClass().toString());

	SubNews mSubNews = null;
	Subscriber mSub = null;
	MOLog mMOLog = null;

	MyTableModel mTable_MOLog = null;
	MyTableModel mTable_Sub = null;
	MyTableModel mTable_SubNews = null;

	String ExpireMTContent = "";
	
	int DelaySendMT = 0;
	
	public CheckConfirmExpire()
	{

	}

	private void Init() throws Exception
	{
		mSub = new Subscriber(LocalConfig.mDBConfig_MSSQL);
		mMOLog = new MOLog(LocalConfig.mDBConfig_MSSQL);
		mSubNews = new SubNews(LocalConfig.mDBConfig_MSSQL);

		mTable_MOLog = CurrentData.GetTable_MOLog();
		mTable_Sub = CurrentData.GetTable_Sub();
		mTable_SubNews = CurrentData.GetTable_SubNews();

		ExpireMTContent = Common.GetDefineMT_Message(DefineMT.MTType.NotifyExpire);
		
		int MTLength = ExpireMTContent.length();
		
		//Số bản tin ngắn của MT được push di
		int ShortMTCount = MTLength / 160;
		if (MTLength % 160 != 0) ShortMTCount++;
		
		if (LocalConfig.PUSHMT_TPS > 0)
		{
			int TPS_Delay = (1000 / LocalConfig.PUSHMT_TPS);
			DelaySendMT = ShortMTCount * TPS_Delay;
		}
	}
	private void Sleep()
	{
		mLog.log.debug("CHECK CONFIRM EXPIRE " + LocalConfig.CHECK_EXPIRE_TIME_DELAY + " PHUT.");
		mLog.log.debug("---------------KET THUC CHECK CONFIRM EXPIRE--------------------");
		try
		{
			sleep(LocalConfig.CHECK_EXPIRE_TIME_DELAY * 60 * 1000);
		}
		catch (InterruptedException e)
		{
			mLog.log.error(e);
		}
	}
	public void run()
	{
		try
		{

			Init();

			while (Program.processData)
			{
				mLog.log.debug("---------------BAT DAU CHECK COMFIRM EXPIRE --------------------");

				try
				{
					Vector<SubNewsObject> mList = GetSubNews_Expire();

					while (mList.size() > 0)
					{
						for (SubNewsObject mSubNewsObj : mList)
						{
							// Nếu bị dừng đột ngột
							if (!Program.processData)
							{
								Insert_MOLog();
								Update_SubNews();
							}

							if (!SendMT(mSubNewsObj))
							{
								mLog.log.info("Pust MT Notify Expire Fail -->MSISDN:" + mSubNewsObj.MSISDN
										+ mSubNewsObj.MSISDN + "|NewsID:" + mSubNewsObj.NewsID + "|ServiceID:"
										+ mSubNewsObj.ServiceID);

								MyLogger.WriteDataLog(LocalConfig.LogDataFolder, "_PushExpireMT_NotSend",
										"PUSH MT FAIL NOTIFY EXPIRE --> MSISDN:" + mSubNewsObj.MSISDN
												+ mSubNewsObj.MSISDN + "|NewsID:" + mSubNewsObj.NewsID + "|ServiceID:"
												+ mSubNewsObj.ServiceID);
							}
							else
							{
								mLog.log.info("Pust MT Notify Expire OK -->MSISDN:" + mSubNewsObj.MSISDN
										+ mSubNewsObj.MSISDN + "|NewsID:" + mSubNewsObj.NewsID + "|ServiceID:"
										+ mSubNewsObj.ServiceID);
							}

							AddTo_SubNews(mSubNewsObj);
							
							if(DelaySendMT > 0)
							{
								mLog.log.info("PushExpireMT Delay: " + Integer.toString(DelaySendMT));
								Thread.sleep(DelaySendMT);
							}
						}
						Insert_MOLog();
						Update_SubNews();
						
						mList.clear();
						mList = GetSubNews_Expire();
					}

				}
				catch (Exception ex)
				{
					mLog.log.error(ex);
				}

				finally
				{
					Insert_MOLog();
					Update_SubNews();
				}
				Sleep();

			}
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
	}

	private boolean SendMT(SubNewsObject mSubNewsObj)
	{
		try
		{
			String REQUEST_ID = Long.toString(System.currentTimeMillis());
			if (Common.SendMT(mSubNewsObj.MSISDN, "", ExpireMTContent, REQUEST_ID))
			{
				AddToMOLog(mSubNewsObj, MTType.NotifyExpire, ExpireMTContent, REQUEST_ID);
				return true;
			}
			return false;
		}
		catch (Exception ex)
		{
			mLog.log.error("Gui MT Notify Expire khong thanh cong: MSISDN:" + mSubNewsObj.MSISDN + "|NewsID:"
					+ mSubNewsObj.NewsID + "|ServiceID:" + mSubNewsObj.ServiceID, ex);
		}
		return false;
	}

	private void AddToMOLog(SubNewsObject mSubNewsObj, MTType mMTType_Current, String MTContent_Current,
			String REQUEST_ID) throws Exception
	{
		try
		{
			MOObject mMOObj = new MOObject(mSubNewsObj.ServiceID, mSubNewsObj.MSISDN, ChannelType.SYSTEM,
					mMTType_Current, "NOTIFY EXPIRE", MTContent_Current, REQUEST_ID, mSubNewsObj.PID, Calendar
							.getInstance().getTime(), Calendar.getInstance().getTime(), new VNPApplication(), "", "",
					0);

			mTable_MOLog = mMOObj.AddNewRow(mTable_MOLog);
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
	}

	private void Insert_MOLog() throws Exception
	{
		try
		{
			if (mTable_MOLog.IsEmpty())
				return;
			mMOLog.Insert(0, mTable_MOLog.GetXML());
			mTable_MOLog.Clear();
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
	}

	private void Update_SubNews() throws Exception
	{
		try
		{
			if (mTable_SubNews.IsEmpty())
				return;

			mSubNews.Update(2, mTable_SubNews.GetXML());
			mTable_SubNews.Clear();
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
	}

	private void AddTo_SubNews(SubNewsObject mSubNewsObj)
	{
		try
		{
			mSubNewsObj.NotifyDate = Calendar.getInstance().getTime();
			mSubNewsObj.mStatus = SubNews.Status.Notify;

			mTable_SubNews = mSubNewsObj.AddNewRow(mTable_SubNews);
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
	}

	/**
	 * Lấy danh sách các SubNews đã quá hạn
	 * 
	 * @return
	 */
	private Vector<SubNewsObject> GetSubNews_Expire() throws Exception
	{
		SubNews mSubNews = new SubNews(LocalConfig.mDBConfig_MSSQL);

		MyTableModel mTable = mSubNews.Select(7, Integer.toString(LocalConfig.CHECK_EXPIRE_ROWCOUNT),
				Integer.toString(SubNews.Status.WaitConfirm.GetValue()),
				MyConfig.Get_DateFormat_InsertDB().format(Calendar.getInstance().getTime()));
		return SubNewsObject.ConvertToList(mTable);
	}

}
