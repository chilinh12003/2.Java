package pro.check;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import db.DefineMt;
import db.Mtqueue;
import db.News;
import db.Subscriber;
import db.Subscriber.Status;
import pro.server.LocalConfig;
import pro.server.Program;
import uti.*;

/**
 * Thread này dùng để lấy các tin tức cần push xuống cho KH
 * 
 * @author chili
 *
 */
public class PushMT extends Thread
{
	News newsObj = new News();

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
	Integer DelaySendMT = 0;

	MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath, this.getClass().toString());

	Subscriber subDB = new Subscriber();

	public Subscriber.Status mStatus = Status.NoThing;
	public PushMT()
	{
	}

	public PushMT(News newsObj, Short currentPID, Integer threadNumber, Integer threadIndex, Integer maxOrderID,
			Integer rowCount, Date startDate, Integer delaySendMT, Subscriber.Status mStatus)
	{
		super();
		this.newsObj = newsObj;
		CurrentPID = currentPID;
		ThreadNumber = threadNumber;
		ThreadIndex = threadIndex;
		MaxOrderID = maxOrderID;
		RowCount = rowCount;
		StartDate = startDate;
		DelaySendMT = delaySendMT;
		this.mStatus = mStatus;
	}

	public void run()
	{
		if (Program.processData)
		{
			try
			{
				PushForEach();
				UpdateNewsStatus();
			}
			catch (Exception ex)
			{
				mLog.log.error("Loi xay ra trong qua trinh PUSH MT, Thead Index:" + this.ThreadIndex, ex);
			}
		}
	}

	private void UpdateNewsStatus()
	{
		try
		{
			newsObj.setStatusId(News.Status.Complete.GetValue());
			if (newsObj != null)
				newsObj.Update();

		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
	}

	private void PushForEach() throws Exception
	{
		List<Subscriber> mList;
		try
		{
			Integer MinPID = (int) this.CurrentPID;

			for (Integer PID = MinPID; PID <= LocalConfig.MAX_PID; PID++)
			{
				// Nếu bị dừng đột ngột
				if (!Program.processData)
				{
					mLog.log.debug("Bi dung PushMT: PushMT Info:" + MyLogger.GetLog(this));
					return;
				}

				this.CurrentPID = PID.shortValue();

				if (mStatus == Status.NoThing)
				{
					mList = (List<Subscriber>) subDB.GetSub(CurrentPID, this.MaxOrderID, this.RowCount,
							this.ThreadNumber, this.ThreadIndex);
				}
				else
				{
					mList = (List<Subscriber>) subDB.GetSub(CurrentPID, this.MaxOrderID, this.mStatus, this.RowCount,
							this.ThreadNumber, this.ThreadIndex);
				}
				while (Program.processData && mList != null && mList.size() > 0)
				{
					for (Subscriber mSubObj : mList)
					{
						// Nếu bị dừng đột ngột
						if (!Program.processData)
						{
							mLog.log.debug("Bi dung PushMT: PushMT Info:" + MyLogger.GetLog(this));
							return;
						}
						this.MaxOrderID = mSubObj.getOrderId();
						this.PhoneNumber = mSubObj.getId().getPhoneNumber();
						SendMT(mSubObj);
						if (this.DelaySendMT > 0)
						{
							mLog.log.info("PushMT Delay: " + Integer.toString(this.DelaySendMT));
							Thread.sleep(this.DelaySendMT);
						}
					}

					mList = (List<Subscriber>) subDB.GetSub(CurrentPID, this.MaxOrderID, this.RowCount,
							this.ThreadNumber, this.ThreadIndex);
				}
			}
			// Cập nhật thời gian kết thúc bắn tin
			this.FinishDate = Calendar.getInstance().getTime();
		}
		catch (Exception ex)
		{
			mLog.log.debug("Loi trong PUSH MT cho dich vu");
			throw ex;
		}
		finally
		{
			// Nếu bị dừng đột ngột và chưa push xong thì cho vào queue
			if (this.FinishDate == null)
			{
				Program.queuePushMT.add(this);
			}
			mLog.log.debug("KET THUC PUSH MT:" + MyLogger.GetLog(this));
		}
	}

	private void SendMT(Subscriber mSubObj)
	{
		Mtqueue mtqueueObj = new Mtqueue();
		try
		{
			mtqueueObj.setChannelId(MyConfig.ChannelType.SYSTEM.GetValue().shortValue());
			mtqueueObj.setContentTypeId(Mtqueue.ContentType.LongMessage.GetValue());
			mtqueueObj.setMt(this.newsObj.getMt());
			mtqueueObj.setMtInsertDate(MyDate.Date2Timestamp(Calendar.getInstance()));
			mtqueueObj.setMttypeId(DefineMt.MTType.PushMT.GetValue());
			mtqueueObj.setPhoneNumber(mSubObj.getId().getPhoneNumber());
			mtqueueObj.setPid(mSubObj.getId().getPid());
			mtqueueObj.setRequestId(MySeccurity.GenUniqueueID());
			mtqueueObj.setRetryCount((short) 0);
			mtqueueObj.setSendTypeID(Mtqueue.SendType.SendToUser.GetValue());
			mtqueueObj.setShoreCode(LocalConfig.SHORT_CODE);
			mtqueueObj.setStatusId(Mtqueue.Status.WaitingSendMT.GetValue());
			mtqueueObj.setTelcoId(MyConfig.Telco.VIETTEL.GetValue().shortValue());

			Integer TotalSesment = newsObj.getMt().length() / 160;
			if (newsObj.getMt().length() % 160 > 0)
				TotalSesment++;

			mtqueueObj.setTotalSegment(TotalSesment.shortValue());

			if (mtqueueObj.Save())
			{
				mLog.log.info("Pust MT OK -->" + MyLogger.GetLog(mtqueueObj));
			}
			else
			{
				mLog.log.info("Pust MT Fail -->" + MyLogger.GetLog(mtqueueObj));
				MyLogger.WriteDataLog(LocalConfig.LogDataFolder, "_PushMT_NotSend",
						"PUSH MT FAIL --> " + MyLogger.GetLog(mtqueueObj));
			}
		}
		catch (Exception ex)
		{
			mLog.log.error("Gui MT khong thanh cong:", ex);
			mLog.log.info(MyLogger.GetLog(mtqueueObj));
			mLog.log.info(MyLogger.GetLog(this));
		}
	}

}
