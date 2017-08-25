package pro.check;

import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import dat.content.News;
import dat.content.NewsObject;
import dat.content.DefineMT.MTType;
import dat.content.News.NewsType;
import dat.content.News.Status;
import dat.history.Play;
import dat.history.PlayLog;
import dat.history.WinnerWeek;
import dat.history.WinnerWeekObject;
import dat.sub.Subscriber;
import dat.sub.SubscriberObject;
import db.*;
import db.define.MyTableModel;
import pro.server.Common;
import pro.server.CurrentData;
import pro.server.LocalConfig;
import pro.server.Program;
import uti.utility.*;

/**
 * Thead được chạy vào khoảng 1h ngày thứ 2 hàng tuần, để tìm ra người chiến
 * thắng tuần. Thread dựa vào table subscriber và Play để lấy ra người chiến
 * thằng. vì vậy chần phải chạy trước thread FishDay để thông tin của Sub ko bị
 * reset.
 * 
 * 
 * 
 * @author chilinh
 * 
 */
public class FinishSession extends Thread
{
	MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath, this.getClass().toString());

	Subscriber subDB = null;
	Play playDB = null;
	PlayLog playLogDB = null;
	WinnerWeek winnerWeekDB = null;

	// Ngày đầu tháng
	Calendar calFirstDayOfMonth = Calendar.getInstance();
	// Ngày cuối tháng
	Calendar calLastDayOfMonth = Calendar.getInstance();

	MyTableModel mTable_SubUpdate = null;
	MyTableModel mTable_Play = null;

	public void run()
	{

		if (Program.processData)
		{
			try
			{
				init();

				SubscriberObject subObjWinner = null;

				// lấy danh sách người có nhiều điểm nhất
				MyTableModel mTable_Sub = subDB.Select(10,
						MyConfig.Get_DateFormat_InsertDB().format(calFirstDayOfMonth.getTime()),
						MyConfig.Get_DateFormat_InsertDB().format(calLastDayOfMonth.getTime()));

				List<SubscriberObject> listTopWinner = SubscriberObject.ConvertToList(mTable_Sub, false);

				subObjWinner = findWinner(listTopWinner);

				if (subObjWinner == null)
				{
					mLog.log.warn("Khong tim thay nguoi chien thang");
					return;
				}
					
				mLog.log.info("Nguoi chien thang: " + MyLogger.GetLog(subObjWinner));
				
				if (!insertWinnerWeek(subObjWinner))
				{
					mLog.log.warn("Khong insert vao duoc table WinnerWeek: " + MyLogger.GetLog(subObjWinner));
					return;
				}

				// Insert xuống table News để thông báo cho người chiến thắng
				insertWinnerNews(subObjWinner);
			}
			catch (Exception ex)
			{
				mLog.log.error(ex);
			}
		}
	}

	void init() throws Exception
	{
		// Thường là ngày đầu tháng, mà ta đang tính cho tháng trướng --> sẽ trừ
		// đi 5 ngày để lấy ngày của tháng trước
		Calendar calPrevMonth = Calendar.getInstance();
		calPrevMonth.add(Calendar.DATE, -5);
		
		calPrevMonth.set(Calendar.HOUR_OF_DAY, 0);
		calPrevMonth.set(Calendar.MINUTE, 0);
		calPrevMonth.set(Calendar.SECOND, 0);
		calPrevMonth.set(Calendar.MILLISECOND, 0);
		
		calFirstDayOfMonth = MyDate.GetFirstDayOfMonth(calPrevMonth);
		calLastDayOfMonth = MyDate.GetLastDayOfMonth(calPrevMonth);

		subDB = new Subscriber(LocalConfig.mDBConfig_MSSQL);
		playDB = new Play(LocalConfig.mDBConfig_MSSQL);
		playLogDB = new PlayLog(LocalConfig.mDBConfig_MSSQL);
		winnerWeekDB = new WinnerWeek(LocalConfig.mDBConfig_MSSQL);

		mTable_SubUpdate = subDB.Select(0);
		mTable_SubUpdate.Clear();
		mTable_Play = playDB.Select(0);
		mTable_Play.Clear();

	}
	
	SubscriberObject findWinner(List<SubscriberObject> listTopWinner)
	{
		try
		{
			if (listTopWinner == null || listTopWinner.size() < 1)
				return null;

			if (listTopWinner.size() == 1)
			{
				return listTopWinner.get(0);
			}

			// Danh sách có lần trả lời đúng nhiều nhất
			List<SubscriberObject> listTopAnswer = new Vector<SubscriberObject>();

			// Tìm điểm trả lời cao nhất (nghĩa là số lần trả lời đúng nhiều
			// nhất)
			int MaxAnswerMark = 0;
			for (SubscriberObject item : listTopWinner)
			{
				if (item.AnswerMark > MaxAnswerMark)
					MaxAnswerMark = item.AnswerMark;
			}

			for (SubscriberObject item : listTopWinner)
			{
				if (item.AnswerMark == MaxAnswerMark)
					listTopAnswer.add(item);
			}
			// Nếu dành sách còn 1 thì lấy ngay
			if (listTopAnswer.size() == 1)
			{
				return listTopAnswer.get(0);
			}

			// Tìm danh sách có số lần mua nhiều nhất - nghĩa là điểm mua lớn
			// nhất
			List<SubscriberObject> listTopBuy = new Vector<SubscriberObject>();

			int MaxBuyMark = 0;
			for (SubscriberObject item : listTopAnswer)
			{
				if (item.BuyMark > MaxBuyMark)
					MaxBuyMark = item.BuyMark;
			}

			for (SubscriberObject item : listTopAnswer)
			{
				if (item.BuyMark == MaxBuyMark)
					listTopBuy.add(item);
			}

			// Nếu dành sách còn 1 thì lấy ngay
			if (listTopBuy.size() == 1)
			{
				return listTopBuy.get(0);
			}

			return null;
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
			return null;
		}
	}

	void insertWinnerNews(SubscriberObject subObjWinner)
	{
		try
		{
			String MT = Common.GetDefineMT_Message(MTType.NotifyWinner);
			String MSISDN = MyCheck.ValidPhoneNumber(subObjWinner.MSISDN, "0");
			MSISDN = MSISDN.substring(0, MSISDN.length() - 2) + "xx";
			MT = MT.replace("[Winner]", MSISDN);

			NewsObject mNewsObj = new NewsObject();
			mNewsObj.NewsName = "Push Tin Nguoi Chien thang thang: "
					+ MyConfig.Get_DateFormat_VNShort().format(calFirstDayOfMonth.getTime());
			mNewsObj.mNewsType = NewsType.Winner;
			mNewsObj.mStatus = Status.Waiting;
			mNewsObj.MT = MT;

			Calendar mCal_PushTime = Calendar.getInstance();
			mCal_PushTime.set(Calendar.HOUR_OF_DAY, 14);
			mCal_PushTime.set(Calendar.MINUTE, 15);
			mCal_PushTime.set(Calendar.MILLISECOND, 0);

			mNewsObj.PushTime = mCal_PushTime.getTime();
			mNewsObj.QuestionID = 0;
			mNewsObj.CreateDate = Calendar.getInstance().getTime();
			mNewsObj.Priority = 0;

			News mNews = new News(LocalConfig.mDBConfig_MSSQL);

			MyTableModel mTable_News = mNews.Select(0);
			mTable_News = mNewsObj.AddNewRow(mTable_News);

			if (mNews.Insert(0, mTable_News.GetXML()))
			{
				mLog.log.info("Luu tin thanh cong cho push tin winner:" + MyLogger.GetLog(mNewsObj));
			}
			else
			{
				mLog.log.warn("khong the luu tin xuong table News cho push tin winner");
			}
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
	}


	boolean insertWinnerWeek(SubscriberObject subObj)
	{
		try
		{
			MyTableModel mTable_WeekWinner = winnerWeekDB.Select(0);

			WinnerWeekObject mObj = new WinnerWeekObject();
			mObj.MSISDN = subObj.MSISDN;
			mObj.BeginSession = calFirstDayOfMonth.getTime();
			mObj.EndSession = calLastDayOfMonth.getTime();
			mObj.WinnerCount = subObj.WeekMark;

			mObj.AddNewRow(mTable_WeekWinner);

			return winnerWeekDB.Insert(0, mTable_WeekWinner.GetXML());
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
			mLog.log.info(MyLogger.GetLog(subObj));
		}
		return false;
	}

	

}
