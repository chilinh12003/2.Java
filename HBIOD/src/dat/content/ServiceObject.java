package dat.content;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import uti.utility.MyDate;
import uti.utility.MyDate.TimeType;
import uti.utility.MyText;
import dat.content.Service.PushType;
import dat.content.Service.ServiceType;
import db.define.MyDataRow;
import db.define.MyTableModel;


public class ServiceObject 
{

	public Integer ServiceID = 0;
	public String ServiceName = "";
	public String RegKeyword = "";
	public String DeregKeyword = "";
	public String PushTime = "";
	public String PackageName = "";

	public Service.ServiceType mServiceType = ServiceType.NoThing;

	public Service.PushType mPushType = PushType.NoThing;
	/**
	 * Thời gian load lên từ DB
	 */
	public Date LoadDate = null;
	public ServiceObject()
	{

	}

	/**
	 * Lấy danh sách trả tin trong ngày hôm nay của dịch vụ
	 * 
	 * @return
	 * @throws Exception
	 */
	public Vector<Date> GetPushTime() throws Exception
	{
		try
		{
			Vector<Date> mListPushTime = new Vector<Date>();

			if (PushTime == "")
				return null;

			String[] Arr = PushTime.split("\\|");

			if (Arr.length < 1)
				return null;

			for (String item : Arr)
			{
				String[] arr_time = item.split("\\:");
				if (arr_time.length != 2)
					continue;

				Integer HourPush = Integer.parseInt(arr_time[0]);
				Integer MinutePush = Integer.parseInt(arr_time[1]);

				Calendar mCalPush = Calendar.getInstance();
				mCalPush.set(Calendar.HOUR_OF_DAY, HourPush);
				mCalPush.set(Calendar.MINUTE, MinutePush);
				mCalPush.set(Calendar.SECOND, 0);
				mCalPush.set(Calendar.MILLISECOND, 0);

				mListPushTime.add(mCalPush.getTime());

			}

			return mListPushTime;
		}
		catch (Exception ex)
		{

			throw ex;
		}
	}

	/**
	 * Khung giờ hiện tại đang push tin
	 */
	public Date CurrentPushTime = null;

	public boolean IsNull()
	{
		if (ServiceID == 0)
			return true;
		else
			return false;
	}

	/**
	 * Kiểm tra xem service có cần phải load lại không.
	 * @param TimeDelayLoad
	 * @return
	 * @throws Exception
	 */
	public boolean IsReload(long TimeDelayLoad) throws Exception
	{
		if (LoadDate == null)
			return false;
		Calendar mCal_Current = Calendar.getInstance();
		Calendar mCal_LoadDate = Calendar.getInstance();

		mCal_LoadDate.setTime(LoadDate);
		long MinuteLoad = MyDate.diffTime(TimeType.Minute, mCal_LoadDate, mCal_Current);
		if(MinuteLoad > TimeDelayLoad)
		{
			return true;
		}
		else
			return false;
	}
	
	public static ServiceObject Convert(MyDataRow mRow) throws Exception
	{
		try
		{
			ServiceObject mObject = new ServiceObject();

			if (mRow == null) return mObject;

			mObject.ServiceID = Integer.parseInt(mRow.GetValueCell( "ServiceID").toString());
			mObject.ServiceName = MyText.RemoveSignVietnameseString((String) mRow.GetValueCell( "ServiceName"));
			mObject.PushTime = mRow.GetValueCell( "PushTime").toString();
			mObject.RegKeyword = mRow.GetValueCell( "RegKeyword").toString().toUpperCase();
			mObject.DeregKeyword = mRow.GetValueCell( "DeregKeyword").toString().toUpperCase();
			mObject.PackageName =mRow.GetValueCell( "PackageName").toString().toUpperCase();
			
			if (mRow.GetValueCell( "ServiceTypeID") != null)
				mObject.mServiceType = ServiceType.FromInt(Integer.parseInt(mRow.GetValueCell( "ServiceTypeID").toString()));

			if (mRow.GetValueCell( "PushTypeID") != null)
				mObject.mPushType = PushType.FromInt(Integer.parseInt(mRow.GetValueCell( "PushTypeID").toString()));
			
			mObject.LoadDate = Calendar.getInstance().getTime();
			return mObject;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}
	
	public static Vector<ServiceObject> ConvertToList(MyTableModel mTable) throws Exception
	{
		try
		{
			Vector<ServiceObject> mList = new Vector<ServiceObject>();
			if (mTable.GetRowCount() < 1) return mList;

			for (int i = 0; i < mTable.GetRowCount(); i++)
			{
				ServiceObject mObject = new ServiceObject();

				MyDataRow mRow = mTable.GetRow(i);
				mObject = Convert(mRow);
				mList.add(mObject);
			}
			return mList;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}
	
}