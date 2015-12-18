package pro.check;

import java.util.Calendar;

import pro.server.CurrentData;
import pro.server.LocalConfig;
import pro.server.Program;
import uti.MyConfig;
import uti.MyDate;
import uti.MyLogger;
import db.News;
import db.Suggest;
import db.DefineMt.MTType;

public class BeginSession extends Thread
{

	MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath, this.getClass().toString());

	// Cho biết thread đang push tin xuống KH;

	public boolean isPushing = false;
	public BeginSession()
	{
	}
	public void run()
	{

		while (Program.processData)
		{
			try
			{
				Calendar mCal_Current = Calendar.getInstance();
				if (mCal_Current.get(Calendar.HOUR_OF_DAY) == 6 && mCal_Current.get(Calendar.MINUTE) == 00 && !isPushing)
				{
					isPushing = true;					

					String MT = Program.GetDefineMT_Message(MTType.NotifyNewSession);
					Suggest mSuggestObj = CurrentData.Get_SuggestObj(1);

					if (mSuggestObj != null)
					{
						MT = MT.replace("[SuggestMT]", mSuggestObj.getMt());

						News newsObj = new News();

						Calendar calPushTime = Calendar.getInstance();

						calPushTime.set(calPushTime.get(Calendar.YEAR), calPushTime.get(Calendar.MONTH),
								calPushTime.get(Calendar.DATE), 8, 0, 0);

						newsObj.setCreateDate(MyDate.Date2Timestamp(Calendar.getInstance()));
						newsObj.setMt(MT);
						newsObj.setNewsName("Push Tin hàng ngày: "
								+ MyConfig.Get_DateFormat_LongFormat().format(Calendar.getInstance().getTime()));
						newsObj.setNewsTypeId(News.NewsType.Reminder.GetValue());
						newsObj.setOrderId(0);
						newsObj.setPushTime(MyDate.Date2Timestamp(calPushTime));
						newsObj.setQuestionId(null);
						newsObj.setStatusId(News.Status.New.GetValue());

						if (newsObj.Save())
						{
							mLog.log.info("Luu tin thanh cong cho push tin hang ngay luc 8h:"
									+ MyLogger.GetLog(newsObj));
							
							Thread.sleep(10 * 60 * 1000);
						}
						else
						{
							mLog.log.warn("khong the luu tin xuong table News cho push tin hang ngay luc 8h");
						}
					}
					else
					{
						mLog.log.warn("Khong tim thay du kien de push tin hang ngay luc 8h");
					}

				}

				Thread.sleep(60 * 1000);
				isPushing = false;
			}
			catch (Exception ex)
			{
				isPushing = false;
				mLog.log.error(ex);
			}
		}
	}
}
