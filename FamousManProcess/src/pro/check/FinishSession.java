package pro.check;

import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import dat.history.Play;
import dat.history.PlayLog;
import dat.history.WinnerWeek;
import dat.history.WinnerWeekObject;
import dat.sub.Subscriber;
import db.*;
import db.define.MyTableModel;
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

	// Thứ 2 của tuần trước
	Calendar calPrevMonday = Calendar.getInstance();
	// Chủ nhật của tuần trước
	Calendar calPrevSunday = Calendar.getInstance();

	// Thứ 2 của tuần hiện tại
	Calendar calMonday = Calendar.getInstance();

	MyTableModel mTable_SubUpdate = null;
	MyTableModel mTable_Play = null;

	
	public void run()
	{

		if (Program.processData)
		{
			try
			{
				init();

				Subscriber subObjWinner = null;

				// lấy danh sách người có nhiều điểm nhất
				List<Subscriber> listTopWinner = subDB.getTopWeekMark();

				// nếu có nhiều người bằng điểm nhau, thì lấy người có thời gian
				// trả lời ít nhất
				if (listTopWinner.size() > 1)
				{
					subObjWinner = getSubMinTotalTime(listTopWinner);
				}
				else if (listTopWinner.size() == 1)
				{
					subObjWinner = listTopWinner.get(0);
				}

				if (subObjWinner == null)
				{
					mLog.log.warn("Khong tim thay nguoi chien thang");
					return;
				}

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

	void insertWinnerNews(Subscriber subObjWinner)
	{
		try
		{
			String MT = Program.GetDefineMT_Message(MTType.NotifyWinner);

			MT = MT.replace("[Winner]", subObjWinner.getId().getPhoneNumber());

			News newsObj = new News();

			Calendar calPushTime = Calendar.getInstance();

			calPushTime.set(calPushTime.get(Calendar.YEAR), calPushTime.get(Calendar.MONTH),
					calPushTime.get(Calendar.DATE), 14, 0, 0);

			newsObj.setCreateDate(MyDate.Date2Timestamp(Calendar.getInstance()));
			newsObj.setMt(MT);
			newsObj.setNewsName("Push Tin Winner: "
					+ MyConfig.Get_DateFormat_LongFormat().format(Calendar.getInstance().getTime()));
			newsObj.setNewsTypeId(News.NewsType.Winner.GetValue());
			newsObj.setOrderId(0);
			newsObj.setPushTime(MyDate.Date2Timestamp(calPushTime));
			newsObj.setQuestionId(null);
			newsObj.setStatusId(News.Status.New.GetValue());
			newsObj.setPhoneNumber(subObjWinner.getId().getPhoneNumber());

			if (newsObj.Save())
			{
				mLog.log.info("Luu tin thanh cong cho push tin winner:" + MyLogger.GetLog(newsObj));
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

	void init() throws Exception
	{
		// Lấy thứ 2 của tuần hiện tại.
		calMonday = MyDate.GetMonday(Calendar.getInstance());

		// lấy thứ 5 của tuần trước
		Calendar calPrevThursday = Calendar.getInstance();
		calPrevThursday.setTime(calMonday.getTime());
		calPrevThursday.add(Calendar.DATE, -4);

		calPrevMonday = MyDate.GetMonday(calPrevThursday);
		calPrevSunday = MyDate.GetSunday(calPrevThursday);
		

		subDB = new Subscriber(LocalConfig.mDBConfig_MSSQL);
		playDB = new Play(LocalConfig.mDBConfig_MSSQL);
		playLogDB = new PlayLog(LocalConfig.mDBConfig_MSSQL);
		winnerWeekDB = new WinnerWeek(LocalConfig.mDBConfig_MSSQL);
		

		mTable_SubUpdate = subDB.Select(0);
		mTable_SubUpdate.Clear();
		mTable_Play = playDB.Select(0);
		mTable_Play.Clear();

	}

	boolean insertWinnerWeek(Subscriber subObj)
	{
		try
		{
			MyTableModel mTable_WeekWinner = winnerWeekDB.Select(0);
			
			
			
			winnerWeekDB.Insert(1, mTable_WeekWinner.GetXML());
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
			mLog.log.info(MyLogger.GetLog(subObj));
		}
		return false;
	}

	/**
	 * Lấy thuê bao có thời gian trả lời là ít nhất
	 * 
	 * @param mList
	 * @return
	 * @throws Exception
	 */
	Subscriber getSubMinTotalTime(List<Subscriber> mList) throws Exception
	{
		long minTotalTime = Long.MAX_VALUE;
		Subscriber winnerObj = null;
		for (Subscriber item : mList)
		{
			Long subTotalTime = playDB.getTotalTime(item.getId().getPhoneNumber(), calPrevMonday, calPrevSunday);
			if (subTotalTime == null)
				continue;

			if (minTotalTime >= subTotalTime.longValue())
			{
				minTotalTime = subTotalTime.longValue();
				winnerObj = item;
			}
		}
		return winnerObj;
	}

}
