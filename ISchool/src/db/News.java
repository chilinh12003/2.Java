package db;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import uti.MyConfig;

/**
 * News entity. @author MyEclipse Persistence Tools
 */

public class News extends DAOBase implements java.io.Serializable
{

	public enum Status
	{
		Nothing(0), New(1), Sending(2), Complete(3),

		/*
		 * Chờ tin phát sinh từ hệ thống và chờ duyện
		 */
		Waiting(4);

		private int value;

		private Status(int value)
		{
			this.value = value;
		}

		public Short GetValue()
		{
			return (Short) ((Integer) this.value).shortValue();
		}

		public static Status FromValue(Short iValue)
		{
			for (Status type : Status.values())
			{
				if (type.GetValue().equals(iValue))
					return type;
			}
			return Nothing;
		}
	}

	public enum NewsType
	{
		Nothing(0), Push(1), Reminder(2), Winner(3);

		private int value;

		private NewsType(int value)
		{
			this.value = value;
		}

		public Short GetValue()
		{
			return (Short) ((Integer) this.value).shortValue();
		}

		public static NewsType FromValue(Short iValue)
		{
			for (NewsType type : NewsType.values())
			{
				if (type.GetValue().equals(iValue))
					return type;
			}
			return Nothing;
		}
	}

	// Fields

	private Integer newsId;
	private String newsName;
	private String mt;
	private Short statusId;
	private Short newsTypeId;
	private Timestamp pushTime;
	private Integer questionId;
	private Timestamp createDate;
	private Integer orderId;
	private String phoneNumber;
	// Constructors

	/** default constructor */
	public News()
	{
	}

	/** full constructor */
	public News(String newsName, String mt, Short statusId, Short newsTypeId, Timestamp pushTime, Integer questionId,
			Timestamp createDate, Integer orderId, String phoneNumber)
	{
		this.newsName = newsName;
		this.mt = mt;
		this.statusId = statusId;
		this.newsTypeId = newsTypeId;
		this.pushTime = pushTime;
		this.questionId = questionId;
		this.createDate = createDate;
		this.orderId = orderId;
		this.phoneNumber = phoneNumber;
	}

	// Property accessors

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public Integer getNewsId()
	{
		return this.newsId;
	}

	public void setNewsId(Integer newsId)
	{
		this.newsId = newsId;
	}

	public String getNewsName()
	{
		return this.newsName;
	}

	public void setNewsName(String newsName)
	{
		this.newsName = newsName;
	}

	public String getMt()
	{
		return this.mt;
	}

	public void setMt(String mt)
	{
		this.mt = mt;
	}

	public Short getStatusId()
	{
		return this.statusId;
	}

	public void setStatusId(Short statusId)
	{
		this.statusId = statusId;
	}

	public Short getNewsTypeId()
	{
		return this.newsTypeId;
	}

	public void setNewsTypeId(Short newsTypeId)
	{
		this.newsTypeId = newsTypeId;
	}

	public Timestamp getPushTime()
	{
		return this.pushTime;
	}

	public void setPushTime(Timestamp pushTime)
	{
		this.pushTime = pushTime;
	}

	public Integer getQuestionId()
	{
		return this.questionId;
	}

	public void setQuestionId(Integer questionId)
	{
		this.questionId = questionId;
	}

	public Timestamp getCreateDate()
	{
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate)
	{
		this.createDate = createDate;
	}

	public Integer getOrderId()
	{
		return this.orderId;
	}

	public void setOrderId(Integer orderId)
	{
		this.orderId = orderId;
	}

	@SuppressWarnings("unchecked")
	public List<News> GetTopNews(NewsType newsType, Status status, Calendar calBeginDate, Calendar calEndDate)
			throws Exception
	{
		String Query = "FROM News WHERE NewsTypeID = " + newsType.GetValue().toString() + " AND StatusID = "
				+ status.GetValue().toString() + " AND PushTime BETWEEN '"
				+ MyConfig.Get_DateFormat_InsertDB().format(calBeginDate.getTime()) + "' AND '"
				+ MyConfig.Get_DateFormat_InsertDB().format(calEndDate.getTime()) + "' ORDER BY PushTime DESC ";
		return (List<News>) Get(Query, 1);
	}
	
	@SuppressWarnings("unchecked")
	public List<News> GetNews(NewsType newsType, Calendar calBeginDate, Calendar calEndDate)
			throws Exception
	{
		String Query = "FROM News WHERE NewsTypeID = " + newsType.GetValue().toString() + " AND PushTime BETWEEN '"
				+ MyConfig.Get_DateFormat_InsertDB().format(calBeginDate.getTime()) + "' AND '"
				+ MyConfig.Get_DateFormat_InsertDB().format(calEndDate.getTime()) + "' ORDER BY PushTime DESC ";
		return (List<News>) Get(Query, 1);
	}
}