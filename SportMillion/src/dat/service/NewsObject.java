package dat.service;

import java.sql.Timestamp;

import dat.service.News.NewsType;
import dat.service.News.Status;
import db.define.MyTableModel;

public class NewsObject implements java.io.Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int NewsID = 0;
	/**
	 * Nội dung của bản tin
	 */
	public String Content = "";

	public News.Status mStatus = Status.NoThing;
	public News.NewsType mNewsType = NewsType.NoThing;
	
	/**
	 * Thời gian push tin cho khách hàng
	 */
	public Timestamp PushTime = null;

	public boolean IsNull()
	{
		if (NewsID < 1 ||  Content == "")
			return true;
		else
			return false;
	}

	/**
	 * Lấy số MT bắn xuống cho khách hàng
	 * @return
	 */
	public int MTCount()
	{
		if(Content == null)
			return 0;
		int MTLength = Content.length();
		int Count = MTLength / 160;
		if(MTLength % 160 != 0)
			Count++;
		
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

			if (mTable.IsEmpty())
				return mNewsObject;
			
			mNewsObject.NewsID = Integer.parseInt(mTable.GetValueAt(0, "NewsID").toString());
			mNewsObject.Content = mTable.GetValueAt(0, "Content").toString();

			if (mTable.GetValueAt(0, "StatusID") != null)
				mNewsObject.mStatus = Status.FromInt(Integer.parseInt(mTable.GetValueAt(0, "StatusID").toString()));

			if (mTable.GetValueAt(0, "NewsTypeID") != null)
				mNewsObject.mNewsType = NewsType.FromInt(Integer.parseInt(mTable.GetValueAt(0, "NewsTypeID").toString()));
			
			if (mTable.GetValueAt(0, "PushTime") != null)
				mNewsObject.PushTime = (Timestamp) mTable.GetValueAt(0, "PushTime");
			
			return mNewsObject;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}


}
