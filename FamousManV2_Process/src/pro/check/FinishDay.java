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
import db.Subscriber;

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

	int questionId = 0;
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

			try
			{
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
					this.MaxOrderID = 0;
					
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

							// khởi tạo thông tin của thuê bao cho ngày mới
							resetSub(subObj, isMonday);
						}					

						if (listSub.size() > 0 && !subDB.Update(listSub))
						{
							mLog.log.warn("FinishDay Update xuong Subscriber khong thanh cong.");
						}

						listSub = subDB.GetSub(CurrentPID, this.MaxOrderID, this.RowCount, this.ThreadNumber,
								this.ThreadIndex);
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
	
	void resetSub(Subscriber subObj, boolean isMonday) throws Exception
	{
		// Chỉ chuyển thành pedding nếu thuê bao này đang Active.
		// Nếu charge thành công thì tình trạng sẽ chuyển lại Active
		if (subObj.getStatusId().shortValue() == Subscriber.Status.Active.GetValue().shortValue())
		{
			subObj.setStatusId(Subscriber.Status.Pending.GetValue());
			
		}
	}
}
