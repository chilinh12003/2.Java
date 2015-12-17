package db;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import uti.MyConfig;
import uti.MyDate;

/**
 * Mtqueue entity. @author MyEclipse Persistence Tools
 */

public class Mtqueue extends DAOBase implements java.io.Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum SendType
	{
		NoThing(0),

		SendToUser(1), NotSend(2), ;

		private int value;

		private SendType(int value)
		{
			this.value = value;
		}

		public Short GetValue()
		{
			return (Short) ((Integer) this.value).shortValue();
		}

		public static SendType FromValue(Short iValue)
		{
			for (SendType type : SendType.values())
			{
				if (type.GetValue().equals(iValue))
					return type;
			}
			return NoThing;
		}
	}

	public enum ContentType
	{
		NoThing(0),

		LongMessage(1), ShortMessage(2), Wappush(3),

		;

		private int value;

		private ContentType(int value)
		{
			this.value = value;
		}

		public Short GetValue()
		{
			return (Short) ((Integer) this.value).shortValue();
		}

		public static ContentType FromValue(Short iValue)
		{
			for (ContentType type : ContentType.values())
			{
				if (type.GetValue().equals(iValue))
					return type;
			}
			return NoThing;
		}
	}

	/**
	 * Tình trạng xử lý của MO, MT
	 * 
	 * @author Administrator
	 * 
	 */
	public enum Status
	{
		NoThing(0),
		/**
		 * Kích hoạt
		 */

		ProcessMODone(1), WaitingSendMT(2), RetrySendMT(3),
		/**
		 * Gửi MT không thành công
		 */
		SendMTFail(4), SendSuccess(5);

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
			return NoThing;
		}
	}

	// Fields

	private Integer mtid;
	private String phoneNumber;
	private Short pid;
	private String shoreCode;
	private Short telcoId;
	private String keyword;
	private String mo;
	private Short channelId;
	private String requestId;
	private Timestamp moInsertDate;
	private Timestamp receiveDate;
	private String mt;
	private Short contentTypeId;
	private Integer mttypeId;
	private Short totalSegment = 0;
	private Timestamp mtInsertDate;
	private Timestamp sendDate;
	private Timestamp doneDate;
	private Short retryCount = 0;
	private Short statusId;
	private Short sendTypeID;
	private Timestamp pushTime;

	// Constructors

	public Timestamp getPushTime()
	{
		return pushTime;
	}

	public void setPushTime(Timestamp pushTime)
	{
		this.pushTime = pushTime;
	}

	public Short getSendTypeID()
	{
		return sendTypeID;
	}

	public void setSendTypeID(Short sendTypeID)
	{
		this.sendTypeID = sendTypeID;
	}

	/** default constructor */
	public Mtqueue()
	{
	}

	/**
	 * Khởi tạo với thông tin từ MO
	 * 
	 * @param moQueueObj
	 * @param PID
	 */
	public Mtqueue(Moqueue moQueueObj, Short PID, String MT, DefineMt.MTType mMTType, Timestamp PushTime)
	{
		this.phoneNumber = moQueueObj.getPhoneNumber();
		this.pid = PID;
		this.shoreCode = moQueueObj.getShortCode();
		this.telcoId = moQueueObj.getTelcoId();
		this.keyword = moQueueObj.getKeyword();
		this.mo = moQueueObj.getMo();
		this.channelId = moQueueObj.getChannelId();
		this.requestId = moQueueObj.getRequestId();
		this.moInsertDate = moQueueObj.getMoInsertDate();
		this.receiveDate = moQueueObj.getReceiveDate();

		this.setMt(MT);

		this.setContentTypeId(ContentType.LongMessage.GetValue());
		this.setMttypeId(mMTType.GetValue());

		this.setContentTypeId(Mtqueue.ContentType.LongMessage.GetValue());
		this.setMtInsertDate(MyDate.Date2Timestamp(Calendar.getInstance()));
		this.setStatusId(Mtqueue.Status.WaitingSendMT.GetValue());

		Integer TotalSesment = MT.length() / 160;
		if (MT.length() % 160 > 0)
			TotalSesment++;

		this.setTotalSegment(TotalSesment.shortValue());

		this.setSendTypeID(SendType.SendToUser.GetValue());
		this.setPushTime(PushTime);
	}

	/** minimal constructor */
	public Mtqueue(Integer mtid, Short pid)
	{
		this.mtid = mtid;
		this.pid = pid;
	}

	/** full constructor */
	public Mtqueue(Integer mtid, String phoneNumber, Short pid, String shoreCode, Short telcoId, String keyword,
			String mo, Short channelId, String requestId, Timestamp moIinsertDate, Timestamp receiveDate, String mt,
			Short contentTypeId, Integer mttypeId, Short totalSegment, Timestamp mtInsertDate, Timestamp sendDate,
			Timestamp doneDate, Short retryCount, Short statusId, Short sendTypeID, Timestamp pushTime)
	{
		this.mtid = mtid;
		this.phoneNumber = phoneNumber;
		this.pid = pid;
		this.shoreCode = shoreCode;
		this.telcoId = telcoId;
		this.keyword = keyword;
		this.mo = mo;
		this.channelId = channelId;
		this.requestId = requestId;
		this.moInsertDate = moIinsertDate;
		this.receiveDate = receiveDate;
		this.mt = mt;
		this.contentTypeId = contentTypeId;
		this.mttypeId = mttypeId;
		this.totalSegment = totalSegment;
		this.mtInsertDate = mtInsertDate;
		this.sendDate = sendDate;
		this.doneDate = doneDate;
		this.retryCount = retryCount;
		this.statusId = statusId;
		this.sendTypeID = sendTypeID;
		this.pushTime = pushTime;
	}

	// Property accessors

	public Integer getMtid()
	{
		return this.mtid;
	}

	public void setMtid(Integer mtid)
	{
		this.mtid = mtid;
	}

	public String getPhoneNumber()
	{
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public Short getPid()
	{
		return this.pid;
	}

	public void setPid(Short pid)
	{
		this.pid = pid;
	}

	public String getShoreCode()
	{
		return this.shoreCode;
	}

	public void setShoreCode(String shoreCode)
	{
		this.shoreCode = shoreCode;
	}

	public Short getTelcoId()
	{
		return this.telcoId;
	}

	public void setTelcoId(Short telcoId)
	{
		this.telcoId = telcoId;
	}

	public String getKeyword()
	{
		return this.keyword;
	}

	public void setKeyword(String keyword)
	{
		this.keyword = keyword;
	}

	public String getMo()
	{
		return this.mo;
	}

	public void setMo(String mo)
	{
		this.mo = mo;
	}

	public Short getChannelId()
	{
		return this.channelId;
	}

	public void setChannelId(Short channelId)
	{
		this.channelId = channelId;
	}

	public String getRequestId()
	{
		return this.requestId;
	}

	public void setRequestId(String requestId)
	{
		this.requestId = requestId;
	}

	public Timestamp getMoInsertDate()
	{
		return this.moInsertDate;
	}

	public void setMoInsertDate(Timestamp moInsertDate)
	{
		this.moInsertDate = moInsertDate;
	}

	public Timestamp getReceiveDate()
	{
		return this.receiveDate;
	}

	public void setReceiveDate(Timestamp receiveDate)
	{
		this.receiveDate = receiveDate;
	}

	public String getMt()
	{
		return this.mt;
	}

	public void setMt(String mt)
	{
		this.mt = mt;
	}

	public Short getContentTypeId()
	{
		return this.contentTypeId;
	}

	public void setContentTypeId(Short contentTypeId)
	{
		this.contentTypeId = contentTypeId;
	}

	public Integer getMttypeId()
	{
		return this.mttypeId;
	}

	public void setMttypeId(Integer mttypeId)
	{
		this.mttypeId = mttypeId;
	}

	public Short getTotalSegment()
	{
		return this.totalSegment;
	}

	public void setTotalSegment(Short totalSegment)
	{
		this.totalSegment = totalSegment;
	}

	public Timestamp getMtInsertDate()
	{
		return this.mtInsertDate;
	}

	public void setMtInsertDate(Timestamp mtInsertDate)
	{
		this.mtInsertDate = mtInsertDate;
	}

	public Timestamp getSendDate()
	{
		return this.sendDate;
	}

	public void setSendDate(Timestamp sendDate)
	{
		this.sendDate = sendDate;
	}

	public Timestamp getDoneDate()
	{
		return this.doneDate;
	}

	public void setDoneDate(Timestamp doneDate)
	{
		this.doneDate = doneDate;
	}

	public Short getRetryCount()
	{
		return this.retryCount;
	}

	public void setRetryCount(Short retryCount)
	{
		this.retryCount = retryCount;
	}

	public Short getStatusId()
	{
		return this.statusId;
	}

	public void setStatusId(Short statusId)
	{
		this.statusId = statusId;
	}

	@SuppressWarnings("unchecked")
	public List<Mtqueue> GetByThread(Integer threadNumber, Integer threadIndex, int maxResults) throws Exception
	{
		Calendar calCurrent = Calendar.getInstance();

		String Query = "FROM Mtqueue WHERE (PushTime IS NULL OR PushTime <= '"
				+ MyConfig.Get_DateFormat_InsertDB().format(calCurrent.getTime()) + "') AND (mod(mtid," + threadNumber
				+ ")=" + threadIndex + ")";
		return (List<Mtqueue>) Get(Query, maxResults);
	}
}