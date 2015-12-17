package pro.server;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import uti.utility.MyConfig;
import uti.utility.MyDate;

import dat.content.News;
import dat.content.NewsObject;
import dat.content.Service;
import dat.content.Service.ServiceType;
import dat.content.ServiceObject;
import dat.content.Zodiac;
import dat.content.ZodiacObject;
import dat.history.ChargeLog;
import dat.history.MOLog;
import dat.sub.SubNews;
import dat.sub.SubNewsObject;
import dat.sub.Subscriber;
import db.define.MyTableModel;

/**
 * Chứa các đối tượng đã table mẫu ở Database Các đối tượng này sẽ được load khi
 * chương trình bắt đầu chạy
 * 
 * @author Administrator
 * 
 */
public class CurrentData
{
	/**
	 * Key được GEN ra để insert vào table SubNews và gửi về cho KH để nhắn tin
	 * Confirm download nội dung
	 */
	private static SubNewsObject mLastSubNewsObj = new SubNewsObject();

	public static synchronized String GetSubNewsKey() throws Exception
	{

		if (!mLastSubNewsObj.IsNull())
		{
			if (MyDate.IsToday(mLastSubNewsObj.RequestDate))
			{
				int TempKey = 1;
				if (!mLastSubNewsObj.NewsKey.equalsIgnoreCase(""))
				{
					TempKey = Integer.parseInt(mLastSubNewsObj.NewsKey);
				}

				TempKey++;
				mLastSubNewsObj.NewsKey = Integer.toString(TempKey);

				return mLastSubNewsObj.NewsKey;
			}
		}

		// lấy 1 record mới nhất
		SubNews mSubNews = new SubNews(LocalConfig.mDBConfig_MSSQL);
		MyTableModel mTable = mSubNews.Select(5);
		if (mTable != null && mTable.GetRowCount() > 0)
		{
			Vector<SubNewsObject> mList = SubNewsObject.ConvertToList(mTable);

			if (mList.size() > 0)
				mLastSubNewsObj = mList.get(0);
		}

		if (mLastSubNewsObj.IsNull() || !MyDate.IsToday(mLastSubNewsObj.RequestDate))
		{
			mLastSubNewsObj = new SubNewsObject();
			int TempKey = 1;
			mLastSubNewsObj.NewsKey = Integer.toString(TempKey);
			mLastSubNewsObj.RequestDate = Calendar.getInstance().getTime();
			return mLastSubNewsObj.NewsKey;
		}
		else
		{
			int TempKey = 1;
			if (!mLastSubNewsObj.NewsKey.equalsIgnoreCase(""))
			{
				TempKey = Integer.parseInt(mLastSubNewsObj.NewsKey);
			}

			TempKey++;
			mLastSubNewsObj.NewsKey = Integer.toString(TempKey);

			return mLastSubNewsObj.NewsKey;
		}
	}

	public static MyTableModel mTable_MOLog = null;

	public static synchronized MyTableModel GetTable_MOLog() throws Exception
	{

		MOLog mMOLog = new MOLog(LocalConfig.mDBConfig_MSSQL);
		mTable_MOLog = mMOLog.Select(0);
		return (MyTableModel) mTable_MOLog.clone();

	}

	public static MyTableModel mTable_Sub = null;

	public static synchronized MyTableModel GetTable_Sub() throws Exception
	{

		Subscriber mSub = new Subscriber(LocalConfig.mDBConfig_MSSQL);
		mTable_Sub = mSub.Select(0);
		return (MyTableModel) mTable_Sub.clone();

	}

	public static MyTableModel mTable_SubNews = null;

	public static synchronized MyTableModel GetTable_SubNews() throws Exception
	{
		SubNews mSubNews = new SubNews(LocalConfig.mDBConfig_MSSQL);
		mTable_SubNews = mSubNews.Select(0);
		return (MyTableModel) mTable_SubNews.clone();
	}

	public static MyTableModel mTable_ChargeLog = null;

	public static synchronized MyTableModel GetTable_ChargeLog() throws Exception
	{
		ChargeLog mChargeLog = new ChargeLog(LocalConfig.mDBConfig_MSSQL);
		mTable_ChargeLog = mChargeLog.Select(0);
		return (MyTableModel) mTable_ChargeLog.clone();
	}

	private static Vector<ServiceObject> Current_ServiceObj = new Vector<ServiceObject>();
	/**
	 * Lấy danh sách dịch vụ hiện tại
	 * 
	 * @return
	 * @throws Exception
	 */
	public static synchronized Vector<ServiceObject> Get_Current_ServiceObj() throws Exception
	{
		if (Current_ServiceObj != null && Current_ServiceObj.size() > 0
				&& !Current_ServiceObj.get(0).IsReload(LocalConfig.TIME_DELAY_RELOAD_SERVICE))
		{
			return Current_ServiceObj;
		}

		if (Current_ServiceObj != null)
		{
			Current_ServiceObj.clear();
		}

		Service mService = new Service(LocalConfig.mDBConfig_MSSQL);
		MyTableModel mTable = mService.Select(3);
		if (mTable != null && mTable.GetRowCount() > 1)
		{
			Current_ServiceObj = ServiceObject.ConvertToList(mTable);
		}

		return Current_ServiceObj;
	}

	/**
	 * Lấy dịch vụ theo ServiceID
	 * 
	 * @param ServiceID
	 * @return
	 * @throws Exception
	 */
	public static synchronized ServiceObject Get_ServiceObj(int ServiceID) throws Exception
	{
		ServiceObject mServiceObj = new ServiceObject();

		for (ServiceObject mObject : Get_Current_ServiceObj())
		{
			if (mObject.ServiceID == ServiceID)
			{
				mServiceObj = mObject;
				break;
			}
		}

		if (mServiceObj.IsNull())
		{
			Service mService = new Service(LocalConfig.mDBConfig_MSSQL);

			MyTableModel mTable = mService.Select(1, Integer.toString(ServiceID));

			if (mTable != null && mTable.GetRowCount() > 0)
			{
				mServiceObj = ServiceObject.Convert(mTable.GetRow(0));
				Get_Current_ServiceObj().add(mServiceObj);
			}
		}
		return mServiceObj;
	}

	/**
	 * Lấy dịch vụ theo keyword
	 * 
	 * @param Keyword
	 * @return
	 * @throws Exception
	 */
	public static synchronized ServiceObject Get_ServiceObj(String Keyword) throws Exception
	{
		ServiceObject mServiceObj = new ServiceObject();

		Keyword = "\"" + Keyword + "\"";
		Keyword = Keyword.toUpperCase();
		for (ServiceObject mObject : Get_Current_ServiceObj())
		{
			if (mObject.RegKeyword.contains(Keyword))
				return mObject;
		}

		return mServiceObj;
	}

	private static Vector<ZodiacObject> Current_ZodiacObj = new Vector<ZodiacObject>();

	/**
	 * Lấy danh sách Cung Hoàng Đạo hiện tại
	 * 
	 * @return
	 * @throws Exception
	 */
	public static synchronized Vector<ZodiacObject> Get_Current_ZodiacObj() throws Exception
	{
		if (Current_ZodiacObj != null && Current_ZodiacObj.size() > 0)
		{
			return Current_ZodiacObj;
		}

		if (Current_ZodiacObj != null)
		{
			Current_ZodiacObj.clear();
		}

		Zodiac mZodiac = new Zodiac(LocalConfig.mDBConfig_MSSQL);
		MyTableModel mTable = mZodiac.Select(2);

		if (mTable != null && mTable.GetRowCount() > 1)
		{
			Current_ZodiacObj = ZodiacObject.ConvertToList(mTable);
		}

		return Current_ZodiacObj;
	}

	public static synchronized ZodiacObject Get_ZodiacObj(String Keyword) throws Exception
	{
		ZodiacObject mZodiacObj = new ZodiacObject();

		Keyword = "\"" + Keyword + "\"";
		Keyword = Keyword.toUpperCase();

		for (ZodiacObject mObject : Get_Current_ZodiacObj())
		{
			if (mObject.Keyword.contains(Keyword))
				return mObject;
		}

		return mZodiacObj;
	}

	/**
	 * Lấy danh sách tin đã trả trong ngày của 1 thuê bao
	 * 
	 * @param PID
	 * @param MSISDN
	 * @param ServiceID
	 */
	public static Vector<SubNewsObject> GetSendNews_ByDay(int PID, String MSISDN, int ServiceID) throws Exception
	{
		SubNews mSubNews = new SubNews(LocalConfig.mDBConfig_MSSQL);

		MyTableModel mTable = mSubNews.Select(3, Integer.toString(PID), MSISDN, Integer.toString(ServiceID));

		return SubNewsObject.ConvertToList(mTable);
	}

	/**
	 * Lấy tin tức theo NewsID
	 * 
	 * @param NewsID
	 * @return
	 * @throws Exception
	 */
	public static NewsObject GetNews(int NewsID) throws Exception
	{
		NewsObject mObject = new NewsObject();
		News mNews = new News(LocalConfig.mDBConfig_MSSQL);

		MyTableModel mTable = mNews
				.Select(7, Integer.toString(NewsID), Integer.toString(News.Status.Active.GetValue()));

		if (mTable != null && mTable.GetRowCount() > 0)
			mObject = NewsObject.Convert(mTable.GetRow(0));

		return mObject;
	}

	/**
	 * Lấy tin tức theo ngày
	 * 
	 * @param mServiceObj
	 * @param PushTime
	 * @return
	 * @throws Exception
	 */
	public static NewsObject GetNews(ServiceObject mServiceObj, Date PushTime) throws Exception
	{
		NewsObject mObject = new NewsObject();
		News mNews = new News(LocalConfig.mDBConfig_MSSQL);

		Calendar mCal_Begin = Calendar.getInstance();
		Calendar mCal_End = Calendar.getInstance();
		mCal_Begin.setTime(PushTime);
		mCal_End.setTime(PushTime);

		mCal_Begin.set(Calendar.HOUR_OF_DAY, 0);
		mCal_Begin.set(Calendar.MINUTE, 0);
		mCal_Begin.set(Calendar.SECOND, 0);
		mCal_Begin.set(Calendar.MILLISECOND, 0);

		mCal_End.set(Calendar.HOUR_OF_DAY, 23);
		mCal_End.set(Calendar.MINUTE, 59);
		mCal_End.set(Calendar.SECOND, 59);
		mCal_End.set(Calendar.MILLISECOND, 0);

		// Lấy 1 tin theo Service và theo ngày tháng (Para_1 = ServiceID, Para_2
		// = StatusID, Para_3 = NewsTypeID, Para_4 = BeginDate, Para_5 = EndDate
		MyTableModel mTable = mNews.Select(2, Integer.toString(mServiceObj.ServiceID), Integer
				.toString(News.Status.Active.GetValue()), Integer.toString(News.NewsType.Content.GetValue()), MyConfig
				.Get_DateFormat_InsertDB().format(mCal_Begin.getTime()),
				MyConfig.Get_DateFormat_InsertDB().format(mCal_End.getTime()));

		if (mTable != null && mTable.GetRowCount() > 0)
			mObject = NewsObject.Convert(mTable.GetRow(0));

		return mObject;
	}

	/**
	 * Lấy tin tức cho sô điện thoại
	 * 
	 * @param PID
	 * @param MSISDN
	 * @param mServiceObj
	 * @param mZodiacObj
	 * @return
	 * @throws Exception
	 */
	public static NewsObject GetNews(int PID, String MSISDN, ServiceObject mServiceObj, ZodiacObject mZodiacObj)
			throws Exception
	{
		NewsObject mObject = new NewsObject();
		News mNews = new News(LocalConfig.mDBConfig_MSSQL);
		Calendar mCal_BeginDate = Calendar.getInstance();
		Calendar mCal_EndDate = Calendar.getInstance();
		mCal_BeginDate.set(Calendar.HOUR_OF_DAY, 0);
		mCal_BeginDate.set(Calendar.MINUTE, 0);
		mCal_BeginDate.set(Calendar.SECOND, 0);
		mCal_BeginDate.set(Calendar.MILLISECOND, 0);

		mCal_EndDate.set(Calendar.HOUR_OF_DAY, 0);
		mCal_EndDate.set(Calendar.MINUTE, 0);
		mCal_EndDate.set(Calendar.SECOND, 0);
		mCal_EndDate.set(Calendar.MILLISECOND, 0);

		mCal_EndDate.add(Calendar.DATE, 1);

		MyTableModel mTable = null;

		// Nếu dịch vụ là cung hoàng đạo thì lấy kiểu khác
		if (mServiceObj.mServiceType == ServiceType.Zodiac)
		{
			mTable = mNews.Select(4, Integer.toString(PID), MSISDN, Integer.toString(mServiceObj.ServiceID), Integer
					.toString(News.Status.Active.GetValue()), Integer.toString(mServiceObj.mPushType.GetValue()),
					MyConfig.Get_DateFormat_InsertDB().format(mCal_BeginDate.getTime()), MyConfig
							.Get_DateFormat_InsertDB().format(mCal_EndDate.getTime()), Integer
							.toString(mZodiacObj.mZodiacList.GetValue()));
		}
		else
		{
			mTable = mNews.Select(3, Integer.toString(PID), MSISDN, Integer.toString(mServiceObj.ServiceID), Integer
					.toString(News.Status.Active.GetValue()), Integer.toString(mServiceObj.mPushType.GetValue()),
					MyConfig.Get_DateFormat_InsertDB().format(mCal_BeginDate.getTime()), MyConfig
							.Get_DateFormat_InsertDB().format(mCal_EndDate.getTime()));
		}

		if (mTable == null || mTable.GetRowCount() < 1)
		{
			// trường hợp hết tin trả thì lấy random 1 bản tin bất kỳ trả về cho
			// khách hàng
			if (mServiceObj.mServiceType == ServiceType.Zodiac)
			{
				mTable = mNews.Select(6, Integer.toString(mServiceObj.ServiceID), Integer.toString(News.Status.Active
						.GetValue()), MyConfig.Get_DateFormat_InsertDB().format(mCal_BeginDate.getTime()), MyConfig
						.Get_DateFormat_InsertDB().format(mCal_EndDate.getTime()), Integer
						.toString(mZodiacObj.mZodiacList.GetValue()));
			}
			else
			{
				mTable = mNews.Select(5, Integer.toString(mServiceObj.ServiceID), Integer.toString(News.Status.Active
						.GetValue()), MyConfig.Get_DateFormat_InsertDB().format(mCal_BeginDate.getTime()), MyConfig
						.Get_DateFormat_InsertDB().format(mCal_EndDate.getTime()));
			}
		}

		mObject = NewsObject.Convert(mTable.GetRow(0));

		return mObject;
	}
}
