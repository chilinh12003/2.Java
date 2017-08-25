package pro.check;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import dat.content.QuestionObject;
import dat.content.SuggestObject;
import dat.history.Play;
import dat.history.PlayLog;
import dat.history.PlayObject;
import dat.history.Play.PlayType;
import dat.sub.Subscriber;
import dat.sub.SubscriberObject;
import db.define.MyTableModel;
import pro.server.CurrentData;
import pro.server.LocalConfig;
import pro.server.Program;
import uti.utility.*;

/**
 * Vào mỗi sáng sớm (khoảng 1h), thread này sẽ chạy để khởi tạo lại các thông số
 * cho các thuê bao, trước khi bắt đầu 1 phiên mới Thread này phải chạy trước
 * thời gian charge của VIettel và sau thread FinishSession
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
	long MaxOrderID_Play = 0;

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

	Subscriber subDB = null;
	Play playDB = null;
	PlayLog playLogDB = null;

	MyTableModel mTable_SubUpdate = null;
	MyTableModel mTable_Play = null;

	public FinishDay(Short currentPID, Integer threadNumber, Integer threadIndex, Integer maxOrderID, Integer rowCount,
			Date startDate) throws Exception
	{
		super();
		CurrentPID = currentPID;
		ThreadNumber = threadNumber;
		ThreadIndex = threadIndex;
		MaxOrderID = maxOrderID;
		RowCount = rowCount;
		this.StartDate = startDate;
		try
		{
			subDB = new Subscriber(LocalConfig.mDBConfig_MSSQL);
			playDB = new Play(LocalConfig.mDBConfig_MSSQL);
			playLogDB = new PlayLog(LocalConfig.mDBConfig_MSSQL);

			mTable_SubUpdate = subDB.Select(0);
			mTable_SubUpdate.Clear();
			mTable_Play = playDB.Select(0);
			mTable_Play.Clear();

		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
			throw ex;
		}
	}

	int questionId = 0;
	public void run()
	{

		if (Program.processData)
		{
			boolean isFinishSession = false;

			if (Calendar.getInstance().get(Calendar.DATE) == LocalConfig.FINISH_SESSION_DAY_OF_MONTH)
			{
				isFinishSession = true;
			}

			Vector<SubscriberObject> listSub = new Vector<SubscriberObject>();

			try
			{
				QuestionObject mQuestionYesterday = CurrentData.Get_Yesterday_QuestionObj();
				if (!mQuestionYesterday.IsNull())
					questionId = mQuestionYesterday.QuestionID;

				Integer MinPID = (int) this.CurrentPID;
				MyTableModel mTable = new MyTableModel(null, null);

				for (Integer PID = MinPID; PID <= LocalConfig.MAX_PID; PID++)
				{
					// Nếu bị dừng đột ngột
					if (!Program.processData)
					{
						mLog.log.warn("Bi dung FinishDay: Info:" + MyLogger.GetLog(this));
						return;
					}

					this.CurrentPID = PID.shortValue();
					this.MaxOrderID = 0;

					mTable = GetSubscriber(PID);

					while (!mTable.IsEmpty())
					{
						listSub = SubscriberObject.ConvertToList(mTable, false);

						for (SubscriberObject subObj : listSub)
						{
							// Nếu bị dừng đột ngột
							if (!Program.processData)
							{
								mLog.log.debug("Bi dung FinisDay: FinisDay Info:" + MyLogger.GetLog(this));
								return;
							}

							this.MaxOrderID = subObj.OrderID;
							this.PhoneNumber = subObj.MSISDN;
							
							addPlay(subObj);

							// khởi tạo thông tin của thuê bao cho ngày mới
							resetSub(subObj, isFinishSession);
							mTable_SubUpdate = subObj.AddNewRow(mTable_SubUpdate);
						}

						UpdateSub();
						InsertPlay();

						mTable = GetSubscriber(PID);
					}
				}

				if (isFinishSession)
				{
					// Nếu là ngày đầu tháng thì sẽ chuyển tất cả play sang playlog
					movePlayLog();
					
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

	private void UpdateSub() throws Exception
	{
		String XML = "";
		try
		{
			if (mTable_SubUpdate.IsEmpty())
				return;

			XML = mTable_SubUpdate.GetXML();

			if (!subDB.Update(0, XML))
			{
				MyLogger.WriteDataLog(LocalConfig.LogDataFolder, "_FinishDay_NOT_UpdateSub", "LIST RECORD --> " + XML);
			}

		}
		catch (Exception ex)
		{
			MyLogger.WriteDataLog(LocalConfig.LogDataFolder, "_FinishDay_NOT_UpdateSUb", "LIST RECORD --> " + XML);
			mLog.log.error(ex);
		}
		finally
		{
			mTable_SubUpdate.Clear();
		}
	}
	
	private void InsertPlay() throws Exception
	{
		String XML = "";
		try
		{
			if (mTable_Play.IsEmpty())
				return;

			XML = mTable_Play.GetXML();

			if (!playDB.Insert(0, XML))
			{
				MyLogger.WriteDataLog(LocalConfig.LogDataFolder, "_FinishDay_NOT_InsertPlay", "LIST RECORD --> " + XML);
			}

		}
		catch (Exception ex)
		{
			MyLogger.WriteDataLog(LocalConfig.LogDataFolder, "_FinishDay_NOT_InsertPlay", "LIST RECORD --> " + XML);
			mLog.log.error(ex);
		}
		finally
		{
			mTable_Play.Clear();
		}
	}

	public MyTableModel GetSubscriber(Integer PID) throws Exception
	{
		try
		{
			// Lấy danh sách(Para_1 = RowCount, Para_2 = PID, Para_3 = OrderID,
			// Para_4 = ProcessNumber, Para_5 = ProcessIndex )
			return subDB.Select(5, this.RowCount.toString(), PID.toString(), this.MaxOrderID.toString(),
					this.ThreadNumber.toString(), this.ThreadIndex.toString());
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	/**
	 * Chuyển dữ liệu từ play sang PlayLog, và xóa từ play đi
	 * 
	 * @return
	 * @throws Exception
	 */
	void movePlayLog() throws Exception
	{

		try
		{
			MovePlay mMovePlay = new MovePlay();
			mMovePlay.ProcessIndex = ThreadIndex;
			mMovePlay.ProcessNumber = ThreadNumber;
			mMovePlay.RowCount = RowCount;
			mMovePlay.StartDate = Calendar.getInstance().getTime();
			mMovePlay.setPriority(Thread.MAX_PRIORITY);
			mMovePlay.start();
		}
		catch (Exception ex)
		{
			mLog.log.debug("Loi trong di chuyen du lieu Play --> PlayLog", ex);
			throw ex;
		}

	}

	void addPlay(SubscriberObject subObj) throws Exception
	{
		try
		{
			int dayMark = subObj.ChargeMark + subObj.AddMark + subObj.BuyMark + subObj.AnswerMark;
			int weekMark = subObj.WeekMark + dayMark;

			subObj.DayMark = dayMark;
			subObj.WeekMark = weekMark;

			PlayObject playObj = new PlayObject();

			playObj.MSISDN = subObj.MSISDN;
			playObj.PID = subObj.PID;

			playObj.mPlayType = PlayType.FinishDay;
			playObj.MSISDN = subObj.MSISDN;
			playObj.mStatus = subObj.mLastAnswerStatus;
			playObj.OrderNumber = subObj.SuggestByDay;
			playObj.PID = subObj.PID;
			playObj.QuestionID = questionId;
			playObj.ReceiveDate = MyDate.Date2Timestamp(Calendar.getInstance());
			playObj.SuggestID = subObj.AnswerForSuggestID;
			playObj.UserAnswer = subObj.LastAnswer;

			playObj.WeekMark = subObj.WeekMark;
			playObj.DayMark = subObj.DayMark;
			playObj.AddMark = subObj.AddMark;
			playObj.ChargeMark = subObj.ChargeMark;
			playObj.BuyMark = subObj.BuyMark;
			playObj.AnswerMark = subObj.AnswerMark;
			playObj.AnswerRightCount = subObj.AnswerRightCount;
			playObj.BuySuggestCount = subObj.BuySuggestCount;

			mTable_Play = playObj.AddNewRow(mTable_Play);
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
			throw ex;
		}
	}
	void resetSub(SubscriberObject subObj, boolean isFinishSession) throws Exception
	{
		// Nếu là ngày đầu tiên bắt đầu phiên mới
		if (isFinishSession)
		{
			subObj.WeekMark = 0;
		}
		
		subObj.AddMark = 0;
		subObj.DayMark = 0;
		subObj.ChargeMark = 0;
		subObj.BuyMark = 0;
		subObj.AnswerMark = 0;

		subObj.AnswerByDay = 0;

		subObj.mLastAnswerStatus = dat.history.Play.Status.Nothing;

		subObj.AnswerForSuggestID = 0;
		subObj.LastAnswer = null;

		subObj.LastSuggestrID = 0;
		subObj.SuggestByDay = 0;

		// Chỉ chuyển thành pedding nếu thuê bao này đang Active.
		// Nếu charge thành công thì tình trạng sẽ chuyển lại Active
		if (subObj.mStatus.GetValue() == Subscriber.Status.Active.GetValue())
		{
			SuggestObject mSuggestObj = CurrentData.Get_SuggestObj(1);
			if (!mSuggestObj.IsNull())
			{
				subObj.LastSuggestrID = mSuggestObj.SuggestID;
				subObj.SuggestByDay = 1;
				subObj.LastSuggestDate = MyDate.Date2Timestamp(Calendar.getInstance());
				subObj.TotalSuggest = subObj.TotalSuggest;
			}
		}
	}
}
