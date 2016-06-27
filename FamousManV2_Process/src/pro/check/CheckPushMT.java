package pro.check;

import java.util.Calendar;
import java.util.List;

import db.*;
import db.DefineMt.MTType;
import db.News.NewsType;
import pro.server.LocalConfig;
import pro.server.Program;
import uti.*;

/**
 * Kiểm tra và chạy các thead được hẹn giờ
 * 
 * @author chili
 * 
 */
public class CheckPushMT extends Thread
{
	MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath, this.getClass().toString());

	public CheckPushMT()
	{

	}

	public void run()
	{
		while (Program.processData)
		{
			mLog.log.debug("---------------BAT DAU CHECK PUSH MT --------------------");

			try
			{
				for (String PushTime : LocalConfig.PUSHMT_LIST_TIME_NEWS)
				{
					Calendar mCal_Current = Calendar.getInstance();
					if (mCal_Current.get(Calendar.HOUR_OF_DAY) != Integer.parseInt(PushTime))
					{
						continue;
					}

					News newsObj = getOneNews(News.NewsType.Push);
					if (newsObj == null)
						continue;

					// Chạy thread Push tin
					RunThreadPushMT(newsObj, Subscriber.Status.NoThing,MTType.PushNewsDaily);

					UpdateNewsStatus(newsObj,News.Status.Sending);
				}

				for (String PushTime : LocalConfig.PUSHMT_LIST_TIME_REMINDER)
				{
					Calendar mCal_Current = Calendar.getInstance();
					if (mCal_Current.get(Calendar.HOUR_OF_DAY) != Integer.parseInt(PushTime))
					{
						continue;
					}

					News newsObj = getOneNews(News.NewsType.Reminder);

					if (newsObj == null)
						continue;

					// Chạy thread Push tin
					RunThreadPushMT(newsObj, Subscriber.Status.Active, MTType.Reminder);

					UpdateNewsStatus(newsObj,News.Status.Sending);
				}

				for (String PushTime : LocalConfig.PUSHMT_LIST_TIME_REMINDER)
				{
					Calendar mCal_Current = Calendar.getInstance();
					if (mCal_Current.get(Calendar.HOUR_OF_DAY) != Integer.parseInt(PushTime))
					{
						continue;
					}

					News newsObj = getOneNews(News.NewsType.Winner);

					if (newsObj == null)
						continue;

					//Nếu là ban tin người chiến thắng thì chỉ gửi cho 1 người là người chiến thắng
					if(newsObj.getPhoneNumber() == null || newsObj.getPhoneNumber().equalsIgnoreCase(""))
					{
						mLog.log.warn("Ban tin nguoi chien thang ko push duoc vi ko co so dt trong news:" +MyLogger.GetLog(newsObj));
						continue;
					}
					UpdateNewsStatus(newsObj,News.Status.Sending);

					PushWinnerMT(newsObj);
				}
				
				mLog.log.debug("CHECK PUSHMT SE Delay " + LocalConfig.PUSHMT_TIME_DELAY + " Phut.");
				mLog.log.debug("---------------KET THUC CHECK PUSH MT--------------------");
				sleep(LocalConfig.PUSHMT_TIME_DELAY * 60 * 1000);
			}
			catch (Exception ex)
			{
				mLog.log.error(ex);
			}
		}

	}
	void RunThreadPushMT(News newsObj, Subscriber.Status mStatus, DefineMt.MTType mMTType)
	{
		try
		{
			mLog.log.debug("-------------------------");
			mLog.log.debug("Bat dau PUSH MT cho dich vu");

			int DelaySendMT = 0;

			if (LocalConfig.PUSHMT_TPS > 0)
			{
				int TPS_Delay = (1000 / LocalConfig.PUSHMT_TPS) * LocalConfig.PUSHMT_PROCESS_NUMBER;
				DelaySendMT = TPS_Delay;
			}

			for (int j = 0; j < LocalConfig.PUSHMT_PROCESS_NUMBER; j++)
			{
				PushMT mPushMT = new PushMT(newsObj, (short) 0, LocalConfig.PUSHMT_PROCESS_NUMBER, j, 0,
						LocalConfig.PUSHMT_ROWCOUNT, Calendar.getInstance().getTime(), DelaySendMT,mStatus, mMTType);
				mPushMT.setPriority(Thread.MAX_PRIORITY);
				mPushMT.start();
			}
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
	}
	void UpdateNewsStatus(News newsObj, News.Status mStatus)
	{
		try
		{
			newsObj.setStatusId(mStatus.GetValue());
			newsObj.Update();
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
	}

	/**
	 * Lấy 1 tin mới nhất chưa gửi đi
	 * 
	 * @param newsType
	 * @return
	 */
	News getOneNews(News.NewsType newsType)
	{
		try
		{
			Calendar calBeginDate = Calendar.getInstance();
			Calendar calEndDate = Calendar.getInstance();

			// Tính từ phút hiện tại
			calBeginDate.set(Calendar.SECOND, 0);
			calBeginDate.set(Calendar.MILLISECOND, 0);
			calBeginDate.add(Calendar.MINUTE, -1 * (LocalConfig.PUSHMT_TIME_DELAY + 1));

			// Đến hết phút cuối của khoảng thời gian delay
			calEndDate.add(Calendar.MINUTE, (LocalConfig.PUSHMT_TIME_DELAY + 1));
			calEndDate.set(Calendar.SECOND, 0);
			calEndDate.set(Calendar.MILLISECOND, 0);

			News newsDB = new News();
			List<News> mList = newsDB.GetTopNews(newsType, News.Status.New, calBeginDate, calEndDate);
			if (mList != null && mList.size() > 0)
			{
				return mList.get(0);
			}
			return null;
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
		return null;
	}

	
	void PushWinnerMT(News newsObj)
	{
		try
		{
			short PID = 0;
			PID = ((Integer) MyConvert.GetPIDByMSISDN(newsObj.getPhoneNumber(), LocalConfig.MAX_PID)).shortValue();			
			
			Program.sendMT(MTType.NotifyWinner, PID, newsObj.getPhoneNumber(), newsObj.getMt(), "Notify Winner");
			
			UpdateNewsStatus(newsObj,News.Status.Complete);
		}
		catch(Exception ex)
		{
			mLog.log.error(ex);
		}
	}
}