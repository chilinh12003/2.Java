package dat.content;

import java.util.Calendar;
import java.util.Date;

import uti.utility.MyConfig;
import dat.content.News.NewsType;
import dat.content.News.Status;
import db.define.MyDataRow;
import db.define.MyTableModel;

public class NewsObject implements java.io.Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int NewsID = 0;
	public String NewsName = "";
	/**
	 * Nội dung của bản tin
	 */
	public String MT = "";

	public News.Status mStatus = Status.NoThing;
	public News.NewsType mNewsType = NewsType.NoThing;

	/**
	 * Thời gian push tin cho khách hàng
	 */
	public Date PushTime = null;

	public Date CreateDate = Calendar.getInstance().getTime();
	
	public int QuestionID = 0;

	public int Priority = 0;
	public boolean IsNull()
	{
		if (MT == "") return true;
		else return false;
	}

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

	public static NewsObject Convert(MyTableModel mTable) throws Exception
	{
		try
		{
			NewsObject mNewsObject = new NewsObject();

			if (mTable.IsEmpty()) return mNewsObject;

			mNewsObject.NewsID = Integer.parseInt(mTable.GetValueAt(0, "NewsID").toString());
			mNewsObject.MT = mTable.GetValueAt(0, "MT").toString();

			if (mTable.GetValueAt(0, "StatusID") != null)
				mNewsObject.mStatus = Status.FromInt(Integer.parseInt(mTable.GetValueAt(0, "StatusID").toString()));

			if (mTable.GetValueAt(0, "NewsTypeID") != null)
				mNewsObject.mNewsType = NewsType.FromInt(Integer
						.parseInt(mTable.GetValueAt(0, "NewsTypeID").toString()));

			if (mTable.GetValueAt(0, "QuestionID") != null)
				mNewsObject.QuestionID = Integer.parseInt(mTable.GetValueAt(0, "QuestionID").toString());

			if (mTable.GetValueAt(0, "PushTime") != null)
				mNewsObject.PushTime = MyConfig.Get_DateFormat_InsertDB().parse(
						mTable.GetValueAt(0, "PushTime").toString());

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
		
		mRow.SetValueCell("NewsName",NewsName);
		mRow.SetValueCell("MT",MT);
		mRow.SetValueCell("StatusID",mStatus.GetValue());
		mRow.SetValueCell("StatusName",mStatus.toString());
		mRow.SetValueCell("NewsTypeID",mNewsType.GetValue());
		mRow.SetValueCell("NewsTypeName",mNewsType.toString());
		mRow.SetValueCell("PushTime",MyConfig.Get_DateFormat_InsertDB().format(PushTime.getTime()));
		mRow.SetValueCell("QuestionID",QuestionID);
		mRow.SetValueCell("CreateDate",MyConfig.Get_DateFormat_InsertDB().format(CreateDate.getTime()));
		mRow.SetValueCell("Priority",Priority);

		mTable.AddNewRow(mRow);
		return mTable;
	}

}
