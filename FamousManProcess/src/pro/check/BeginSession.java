package pro.check;

import java.util.Calendar;

import dat.content.DefineMT.MTType;
import dat.content.News.NewsType;
import dat.content.News.Status;
import dat.content.News;
import dat.content.NewsObject;
import dat.content.Suggest;
import dat.content.SuggestObject;
import db.define.MyTableModel;
import pro.server.Common;
import pro.server.CurrentData;
import pro.server.LocalConfig;
import pro.server.Program;
import uti.utility.*;

/**
 * Chạy trước 8h để tạo 1 bản tin push về cho tập khách hàng lúc 8h
 * @author Administrator
 *
 */
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
				if (mCal_Current.get(Calendar.HOUR_OF_DAY) == 6 && mCal_Current.get(Calendar.MINUTE) == 0 && !isPushing)
				{
					isPushing = true;					

					String MT = Common.GetDefineMT_Message(MTType.NotifyNewSession);
					SuggestObject mSuggestObj = CurrentData.Get_SuggestObj(1);

					if (!mSuggestObj.IsNull())
					{
						MT = MT.replace("[SuggestMT]", mSuggestObj.MT);

						NewsObject mNewsObj = new NewsObject();
						mNewsObj.NewsName = "Push Tin hang ngay: "
								+ MyConfig.Get_DateFormat_LongFormat().format(Calendar.getInstance().getTime());
						mNewsObj.mNewsType = NewsType.Reminder;
						mNewsObj.mStatus = Status.New;
						mNewsObj.MT = MT;

						Calendar mCal_PushTime = Calendar.getInstance();
						mCal_PushTime.set(Calendar.HOUR_OF_DAY, 8);
						mCal_PushTime.set(Calendar.MINUTE, 0);
						mCal_PushTime.set(Calendar.MILLISECOND, 0);

						mNewsObj.PushTime = mCal_PushTime.getTime();
						
						mNewsObj.CreateDate = Calendar.getInstance().getTime();
						mNewsObj.Priority = 0;

						News mNews = new News(LocalConfig.mDBConfig_MSSQL);

						MyTableModel mTable_News = mNews.Select(0);
						mTable_News = mNewsObj.AddNewRow(mTable_News);

						boolean result= mNews.Insert(0, mTable_News.GetXML());

						if (result)
						{
							mLog.log.info("Luu tin thanh cong cho push tin hang ngay luc 8h:"
									+ MyLogger.GetLog(mNewsObj));
							
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
