package db;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import uti.MyConfig;
import uti.MyDate;

/**
 * Mtqueue entity. @author MyEclipse Persistence Tools
 */

public class Mtqueue extends DAOBase implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum SendType {
		NoThing(0),

		SendToUser(1), NotSend(2), ;

		private int value;

		private SendType(int value) {
			this.value = value;
		}

		public Short GetValue() {
			return (Short) ((Integer) this.value).shortValue();
		}

		public static SendType FromValue(Short iValue) {
			for (SendType type : SendType.values()) {
				if (type.GetValue().equals(iValue))
					return type;
			}
			return NoThing;
		}
	}

	public enum ContentType {
		NoThing(0),

		LongMessage(1), ShortMessage(2), Wappush(3),

		;

		private int value;

		private ContentType(int value) {
			this.value = value;
		}

		public Short GetValue() {
			return (Short) ((Integer) this.value).shortValue();
		}

		public static ContentType FromValue(Short iValue) {
			for (ContentType type : ContentType.values()) {
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
	public enum Status {
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

		private Status(int value) {
			this.value = value;
		}

		public Short GetValue() {
			return (Short) ((Integer) this.value).shortValue();
		}

		public static Status FromValue(Short iValue) {
			for (Status type : Status.values()) {
				if (type.GetValue().equals(iValue))
					return type;
			}
			return NoThing;
		}
	}

	// Fields

	private Integer mtid;
	private Integer serviceId;
	private String phoneNumber;
	private Short pid;
	private String shoreCode;
	private Short telcoId;
	private String keyword;
	private String mo;
	private Short channelId;
	private String requestId;
	private Timestamp moinsertDate;
	private Timestamp receiveDate;
	private String mt;
	private Short contentTypeId;
	private Integer mttypeId;
	private Short totalSegment;
	private Timestamp mtinsertDate;
	private Timestamp sendDate;
	private Timestamp doneDate;
	private Short retryCount;
	private Short statusId;
	private Short sendTypeId;
	private Timestamp pushTime;

	// Constructors

	/** default constructor */
	public Mtqueue() {
	}

	/** minimal constructor */
	public Mtqueue(Short pid) {
		this.pid = pid;
	}

	/**
	 * Khởi tạo với thông tin từ MO
	 * 
	 * @param moQueueObj
	 * @param PID
	 */
	public Mtqueue(Service serviceObj, Moqueue moQueueObj, Short PID,
			String MT, DefineMt.MTType mMTType, Timestamp PushTime) {
		this.phoneNumber = moQueueObj.getPhoneNumber();
		this.pid = PID;
		if (serviceObj != null)
			this.serviceId = serviceObj.getServiceId();
		else
			this.serviceId = 0;

		this.shoreCode = moQueueObj.getShortCode();
		this.telcoId = moQueueObj.getTelcoId();
		this.keyword = moQueueObj.getKeyword();
		this.mo = moQueueObj.getMo();
		this.channelId = moQueueObj.getChannelId();
		this.requestId = moQueueObj.getRequestId();
		this.moinsertDate = moQueueObj.getMoinsertDate();
		this.receiveDate = moQueueObj.getReceiveDate();

		this.setMt(MT);

		this.setContentTypeId(ContentType.LongMessage.GetValue());
		this.setMttypeId(mMTType.GetValue());

		this.setContentTypeId(Mtqueue.ContentType.LongMessage.GetValue());
		this.setMtinsertDate(MyDate.Date2Timestamp(Calendar.getInstance()));
		this.setStatusId(Mtqueue.Status.WaitingSendMT.GetValue());

		Integer TotalSesment = MT.length() / 160;
		if (MT.length() % 160 > 0)
			TotalSesment++;

		this.setTotalSegment(TotalSesment.shortValue());

		this.setSendTypeId(SendType.SendToUser.GetValue());
		this.setPushTime(PushTime);
	}

	/** full constructor */
	public Mtqueue(Integer serviceId, String phoneNumber, Short pid,
			String shoreCode, Short telcoId, String keyword, String mo,
			Short channelId, String requestId, Timestamp moinsertDate,
			Timestamp receiveDate, String mt, Short contentTypeId,
			Integer mttypeId, Short totalSegment, Timestamp mtinsertDate,
			Timestamp sendDate, Timestamp doneDate, Short retryCount,
			Short statusId, Short sendTypeId, Timestamp pushTime) {
		this.serviceId = serviceId;
		this.phoneNumber = phoneNumber;
		this.pid = pid;
		this.shoreCode = shoreCode;
		this.telcoId = telcoId;
		this.keyword = keyword;
		this.mo = mo;
		this.channelId = channelId;
		this.requestId = requestId;
		this.moinsertDate = moinsertDate;
		this.receiveDate = receiveDate;
		this.mt = mt;
		this.contentTypeId = contentTypeId;
		this.mttypeId = mttypeId;
		this.totalSegment = totalSegment;
		this.mtinsertDate = mtinsertDate;
		this.sendDate = sendDate;
		this.doneDate = doneDate;
		this.retryCount = retryCount;
		this.statusId = statusId;
		this.sendTypeId = sendTypeId;
		this.pushTime = pushTime;
	}

	// Property accessors

	public Integer getMtid() {
		return this.mtid;
	}

	public void setMtid(Integer mtid) {
		this.mtid = mtid;
	}

	public Integer getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Short getPid() {
		return this.pid;
	}

	public void setPid(Short pid) {
		this.pid = pid;
	}

	public String getShoreCode() {
		return this.shoreCode;
	}

	public void setShoreCode(String shoreCode) {
		this.shoreCode = shoreCode;
	}

	public Short getTelcoId() {
		return this.telcoId;
	}

	public void setTelcoId(Short telcoId) {
		this.telcoId = telcoId;
	}

	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getMo() {
		return this.mo;
	}

	public void setMo(String mo) {
		this.mo = mo;
	}

	public Short getChannelId() {
		return this.channelId;
	}

	public void setChannelId(Short channelId) {
		this.channelId = channelId;
	}

	public String getRequestId() {
		return this.requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public Timestamp getMoinsertDate() {
		return this.moinsertDate;
	}

	public void setMoinsertDate(Timestamp moinsertDate) {
		this.moinsertDate = moinsertDate;
	}

	public Timestamp getReceiveDate() {
		return this.receiveDate;
	}

	public void setReceiveDate(Timestamp receiveDate) {
		this.receiveDate = receiveDate;
	}

	public String getMt() {
		return this.mt;
	}

	public void setMt(String mt) {
		this.mt = mt;
	}

	public Short getContentTypeId() {
		return this.contentTypeId;
	}

	public void setContentTypeId(Short contentTypeId) {
		this.contentTypeId = contentTypeId;
	}

	public Integer getMttypeId() {
		return this.mttypeId;
	}

	public void setMttypeId(Integer mttypeId) {
		this.mttypeId = mttypeId;
	}

	public Short getTotalSegment() {
		return this.totalSegment;
	}

	public void setTotalSegment(Short totalSegment) {
		this.totalSegment = totalSegment;
	}

	public Timestamp getMtinsertDate() {
		return this.mtinsertDate;
	}

	public void setMtinsertDate(Timestamp mtinsertDate) {
		this.mtinsertDate = mtinsertDate;
	}

	public Timestamp getSendDate() {
		return this.sendDate;
	}

	public void setSendDate(Timestamp sendDate) {
		this.sendDate = sendDate;
	}

	public Timestamp getDoneDate() {
		return this.doneDate;
	}

	public void setDoneDate(Timestamp doneDate) {
		this.doneDate = doneDate;
	}

	public Short getRetryCount() {
		return this.retryCount;
	}

	public void setRetryCount(Short retryCount) {
		this.retryCount = retryCount;
	}

	public Short getStatusId() {
		return this.statusId;
	}

	public void setStatusId(Short statusId) {
		this.statusId = statusId;
	}

	public Short getSendTypeId() {
		return this.sendTypeId;
	}

	public void setSendTypeId(Short sendTypeId) {
		this.sendTypeId = sendTypeId;
	}

	public Timestamp getPushTime() {
		return this.pushTime;
	}

	public void setPushTime(Timestamp pushTime) {
		this.pushTime = pushTime;
	}

	@SuppressWarnings("unchecked")
	public List<Mtqueue> GetByThread(Integer threadNumber, Integer threadIndex,
			int maxResults) throws Exception {
		Calendar calCurrent = Calendar.getInstance();

		String Query = "FROM Mtqueue WHERE (PushTime IS NULL OR PushTime <= '"
				+ MyConfig.Get_DateFormat_InsertDB().format(
						calCurrent.getTime()) + "') AND (mod(mtid,"
				+ threadNumber + ")=" + threadIndex + ")";
		return (List<Mtqueue>) Get(Query, maxResults);
	}
}