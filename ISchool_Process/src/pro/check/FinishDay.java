package pro.check;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import pro.server.CurrentData;
import pro.server.LocalConfig;
import pro.server.Program;
import uti.MyDate;
import uti.MyLogger;
import db.Play;
import db.PlayId;
import db.PlayLog;
import db.PlaySession;
import db.Question;
import db.Subscriber;
import db.Play.PlayType;

/**
 * Vào mỗi sáng sớm (khoảng 1h), thread này sẽ chạy để khởi tạo lại các thông số
 * cho các thuê bao, trước khi bắt đầu 1 phiên mới
 * 
 * @author chili
 * 
 */
public class FinishDay extends Thread
{
	MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath, this.getClass().toString());

	Short CurrentPID = 1;

	String PhoneNumber = "";

	/**
	 * Số lượng process Push MT được tạo ra
	 */
	Integer ThreadNumber = 1;

	/**
	 * Thứ tự của 1 process
	 */
	Integer ThreadIndex = 0;

	/**
	 * Số thứ tự (OrderID) trong table Subscriber, process sẽ lấy những record
	 * có OrderID >= MaxOrderID
	 */
	Integer MaxOrderID = 0;

	/**
	 * Tổng số record mỗi lần lấy lên để xử lý
	 */
	Integer RowCount = 10;

	/**
	 * Thời gian bắt đầu chạy thead
	 */
	Date StartDate = null;

	/**
	 * Thời gian kết thúc chạy thead
	 */
	Date FinishDate = null;

	Subscriber subDB = new Subscriber();
	Play playDB = new Play();
	PlayLog playLogDB = new PlayLog();
	public FinishDay(Short currentPID, Integer threadNumber, Integer threadIndex, Integer maxOrderID, Integer rowCount,
			Date startDate)
	{
		super();
		CurrentPID = currentPID;
		ThreadNumber = threadNumber;
		ThreadIndex = threadIndex;
		MaxOrderID = maxOrderID;
		RowCount = rowCount;
		this.StartDate = startDate;
	}

	PlaySession playSessionYesterday = null; 
	public void run()
	{

		if (Program.processData)
		{
			boolean isMonday = false;

			if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY)
			{
				isMonday = true;

			}

			List<Subscriber> listSub = new Vector<Subscriber>();
			List<Play> listPlay = new Vector<Play>();

			try
			{
				playSessionYesterday = CurrentData.getYesterdaySession();

				Integer MinPID = (int) this.CurrentPID;

				for (Integer PID = MinPID; PID <= LocalConfig.MAX_PID; PID++)
				{
					// Nếu bị dừng đột ngột
					if (!Program.processData)
					{
						mLog.log.warn("Bi dung FinishDay: Info:" + MyLogger.GetLog(this));
						return;
					}

					this.CurrentPID = PID.shortValue();

					listSub = subDB.GetSub(CurrentPID, this.MaxOrderID, this.RowCount, this.ThreadNumber,
							this.ThreadIndex);

					while (Program.processData && listSub != null && listSub.size() > 0)
					{
						for (Subscriber subObj : listSub)
						{
							// Nếu bị dừng đột ngột
							if (!Program.processData)
							{
								mLog.log.debug("Bi dung FinisDay: FinisDay Info:" + MyLogger.GetLog(this));
								return;
							}

							this.MaxOrderID = subObj.getOrderId();
							this.PhoneNumber = subObj.getId().getPhoneNumber();

							addPlay(subObj, listPlay);

							// khởi tạo thông tin của thuê bao cho ngày mới
							resetSub(subObj, isMonday);
						}

						if (listPlay.size() >0 && !playDB.Save(listPlay))
						{
							mLog.log.warn("FinishDay Insert xuong Play khong thanh cong.");
						}

						if (listSub.size() >0 && !subDB.Update(listSub))
						{
							mLog.log.warn("FinishDay Update xuong Subscriber khong thanh cong.");
						}

						listSub = subDB.GetSub(CurrentPID, this.MaxOrderID, this.RowCount, this.ThreadNumber,
								this.ThreadIndex);
					}
				}
				
				if(isMonday)
				{
					//Nếu là thứ 2 thì sẽ chuyển tất cả play sang playlog
					if(!movePlayLog())
					{
						mLog.log.warn("Move Play to PlayLog khong thanh cong");
					}
				}
				// Cập nhật thời gian kết thúc bắn tin
				this.FinishDate = Calendar.getInstance().getTime();
			}
			catch (Exception ex)
			{
				mLog.log.error("Loi trong FinisDay cho dich vu", ex);

			}
			finally
			{
				mLog.log.debug("KET THUC FinisDay:" + MyLogger.GetLog(this));
			}

		}
	}

	/**
	 * Chuyển dữ liệu từ play sang PlayLog, và xóa từ play đi
	 * @return
	 */
	boolean movePlayLog()
	{
		try
		{
			List<Play> mList = playDB.GetList(10);

			while (Program.processData && mList != null && mList.size() > 0)
			{
				List<PlayLog> mListPlayLog =new Vector<PlayLog>();
				for (Play item : mList)
				{
					PlayLog playLogObj = new PlayLog(item);
					mListPlayLog.add(playLogObj);
					mLog.log.debug("Move Play to PlayLog:" + MyLogger.GetLog(item));
				}

				playLogDB.Save(mListPlayLog);
				playDB.Delete(mList);
				mList = playDB.GetList(10);
			}
			return true;
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
		return false;
	}
	
	void addPlay(Subscriber subObj, List<Play> listPlay)
	{
		int dayMark = subObj.getChargeMark() + subObj.getAddMark() + subObj.getBuyMark() + subObj.getAnswerMark();
		int weekMark = subObj.getWeekMark() + dayMark;

		subObj.setDayMark(dayMark);
		subObj.setWeekMark(weekMark);

		Play playObj = new Play();
		PlayId id = new PlayId();
		id.setPhoneNumber(subObj.getId().getPhoneNumber());

		playObj.setId(id);
		playObj.setPid(subObj.getId().getPid());

		playObj.setSessionId(playSessionYesterday == null ? null : playSessionYesterday.getSessionId());
		playObj.setQuestionId(null);
		playObj.setSendCount(subObj.getSendCount());
		playObj.setAnswerCount(subObj.getAnswerCount());
		playObj.setAnswerRight(subObj.getAnswerRight());
		playObj.setAnswerRightBuyType(subObj.getAnswerRightBuyType());

		
			playObj.setPlayDate(playSessionYesterday == null ? null : playSessionYesterday.getPlayDate());
		

		playObj.setPlayTypeId(PlayType.Answer.GetValue());
		playObj.setReceiveDate(MyDate.Date2Timestamp(Calendar.getInstance()));
		playObj.setUserAnswer(subObj.getLastAnswer());
		playObj.setStatusId(subObj.getAnswerStatusId());
		playObj.setWeekMark(subObj.getWeekMark());
		playObj.setDayMark(subObj.getDayMark());
		playObj.setAddMark(subObj.getAddMark());
		playObj.setChargeMark(subObj.getChargeMark());
		playObj.setBuyMark(subObj.getBuyMark());
		playObj.setAnswerMark(subObj.getAnswerMark());
		playObj.setPromotionMark(subObj.getPromotionMark());
		playObj.setInsertDate(MyDate.Date2Timestamp(Calendar.getInstance()));

		listPlay.add(playObj);
	}
	void resetSub(Subscriber subObj, boolean isMonday)
	{
		subObj.setBuyType(Subscriber.BuyQuestionType.QuestionFree.GetValue());
		subObj.setQuestionCount(0);
		subObj.setSendCount(0);
		subObj.setAnswerCount(0);
		subObj.setAnswerRight(0);
		subObj.setAnswerRightBuyType(0);
		subObj.setLastAnswer(null);
		subObj.setLastAnswerDate(null);
		subObj.setLastQuestionId(0);
		subObj.setAnswerStatusId(Play.Status.Nothing.GetValue());
		// Nếu là ngày đầu tuần
		if (isMonday)
		{
			subObj.setWeekMark(0);
			subObj.setAddMark(0);
		}
		subObj.setDayMark(0);
		subObj.setChargeMark(0);
		subObj.setBuyMark(0);
		subObj.setAnswerMark(0);
		subObj.setPromotionMark(0);

	
	}
}
