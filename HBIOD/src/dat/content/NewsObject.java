package dat.content;

import java.util.Calendar;
import java.util.Date;

import uti.utility.MyConfig;
import dat.content.News.NewsType;
import dat.content.News.Status;
import dat.content.Zodiac.ZodiacList;
import db.define.MyDataRow;
import db.define.MyTableModel;

public class NewsObject
{
	

	public int NewsID = 0;
	public String NewsName = "";
	/**
	 * Nội dung của bản tin
	 */
	public String MT = "";

	public News.Status mStatus = Status.Nothing;
	public News.NewsType mNewsType = NewsType.Nothing;
	public ZodiacList mZodiac = ZodiacList.Nothing;

	/**
	 * Thời gian push tin cho khách hàng
	 */
	public Date PushTime = null;

	public Date CreateDate = Calendar.getInstance().getTime();
	
	public int Priority = 0;
	public boolean IsNull()
	{
		if (MT == "") return true;
		else return false;
	}

	public int ServiceID = 0;
	public String ServiceName = "";
	
	/**
	 * Lấy số MT bắn xuống cho khách hàng
	 * 
	 * @return
	 */
	public Integer MTCount()
	{
		if (MT == null) return 0;
		Integer MTLength = MT.length();
		Integer Count = MTLength / 160;
		if (MTLength % 160 != 0) Count++;

		return Count;
	}

	public NewsObject()
	{

	}

	public static NewsObject Convert(MyDataRow mRow) throws Exception
	{
		try
		{
			NewsObject mNewsObject = new NewsObject();

			if (mRow== null) return mNewsObject;

			mNewsObject.NewsID = Integer.parseInt(mRow.GetValueCell("NewsID").toString());
			if(mRow.GetValueCell("NewsName") != null)
			{
				mNewsObject.NewsName = mRow.GetValueCell("NewsName").toString();
			}
			mNewsObject.MT = mRow.GetValueCell("MT").toString();

			if (mRow.GetValueCell("StatusID") != null)
				mNewsObject.mStatus = Status.FromInt(Integer.parseInt(mRow.GetValueCell("StatusID").toString()));

			if (mRow.GetValueCell("NewsTypeID") != null)
				mNewsObject.mNewsType = NewsType.FromInt(Integer
						.parseInt(mRow.GetValueCell("NewsTypeID").toString()));
		
			if (mRow.GetValueCell("ZodiacID") != null)
				mNewsObject.mZodiac = ZodiacList.FromInt(Integer
						.parseInt(mRow.GetValueCell("ZodiacID").toString()));
			
			if (mRow.GetValueCell("PushTime") != null)
				mNewsObject.PushTime = MyConfig.Get_DateFormat_InsertDB().parse(
						mRow.GetValueCell("PushTime").toString());
			
			if (mRow.GetValueCell("CreateDate") != null)
				mNewsObject.CreateDate = MyConfig.Get_DateFormat_InsertDB().parse(
						mRow.GetValueCell("CreateDate").toString());
			
			if(mRow.GetValueCell("ServiceID") != null)
			{
				mNewsObject.ServiceID = Integer.parseInt(mRow.GetValueCell("ServiceID").toString());
				mNewsObject.ServiceName = mRow.GetValueCell("ServiceName").toString();
			}
			
			mNewsObject.Priority = Integer.parseInt(mRow.GetValueCell("Priority").toString());
			return mNewsObject;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public MyTableModel AddNewRow(MyTableModel mTable) throws Exception
	{
		if (mTable == null) return null;

		if (IsNull()) return mTable;

		MyDataRow mRow = mTable.CreateNewRow();
		mRow.SetValueCell("ServiceID",ServiceID);
		mRow.SetValueCell("ServiceName",ServiceName);
		mRow.SetValueCell("NewsName",NewsName);
		mRow.SetValueCell("MT",MT);
		mRow.SetValueCell("StatusID",mStatus.GetValue());
		mRow.SetValueCell("StatusName",mStatus.toString());
		mRow.SetValueCell("NewsTypeID",mNewsType.GetValue());
		mRow.SetValueCell("NewsTypeName",mNewsType.toString());
		mRow.SetValueCell("PushTime",MyConfig.Get_DateFormat_InsertDB().format(PushTime.getTime()));
		mRow.SetValueCell("CreateDate",MyConfig.Get_DateFormat_InsertDB().format(CreateDate.getTime()));
		mRow.SetValueCell("Priority",Priority);

		mTable.AddNewRow(mRow);
		return mTable;
	}

}
