package db;

import java.sql.Timestamp;
import java.util.Calendar;

import uti.MyDate;

/**
 * Mtlog entity. @author MyEclipse Persistence Tools
 */

public class Mtlog extends DAOBase implements java.io.Serializable {

	// Fields

	private MtlogId id;
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
	private Timestamp logDate;

	// Constructors

	/** default constructor */
	public Mtlog() {
	}

	/** minimal constructor */
	public Mtlog(MtlogId id) {
		this.id = id;
	}
	
	public Mtlog(Mtqueue mtqueueObj)
	{
		
		MtlogId mID = new MtlogId();
		mID.setPhoneNumber(mtqueueObj.getPhoneNumber());
		mID.setPid(mtqueueObj.getPid());
		mID.setServiceId(mtqueueObj.getServiceId());
		this.setId(mID);
		this.shoreCode = mtqueueObj.getShoreCode();
		this.telcoId = mtqueueObj.getTelcoId();
		this.keyword = mtqueueObj.getKeyword();
		this.mo = mtqueueObj.getMo();
		this.channelId = mtqueueObj.getChannelId();
		this.requestId = mtqueueObj.getRequestId();
		this.moinsertDate = mtqueueObj.getMoinsertDate();
		this.receiveDate = mtqueueObj.getReceiveDate();
		this.mt = mtqueueObj.getMt();
		this.contentTypeId = mtqueueObj.getContentTypeId();
		this.mttypeId = mtqueueObj.getMttypeId();
		this.totalSegment = mtqueueObj.getTotalSegment();
		this.mtinsertDate = mtqueueObj.getMtinsertDate();
		this.sendDate = mtqueueObj.getSendDate();
		this.doneDate = mtqueueObj.getDoneDate();
		this.retryCount = mtqueueObj.getRetryCount();
		this.statusId = mtqueueObj.getStatusId();
		this.sendTypeId = mtqueueObj.getSendTypeId();
		this.pushTime = mtqueueObj.getPushTime();
		this.logDate = MyDate.Date2Timestamp(Calendar.getInstance());
	}


	/** full constructor */
	public Mtlog(MtlogId id, String shoreCode, Short telcoId, String keyword,
			String mo, Short channelId, String requestId,
			Timestamp moinsertDate, Timestamp receiveDate, String mt,
			Short contentTypeId, Integer mttypeId, Short totalSegment,
			Timestamp mtinsertDate, Timestamp sendDate, Timestamp doneDate,
			Short retryCount, Short statusId, Short sendTypeId,
			Timestamp pushTime, Timestamp logDate) {
		this.id = id;
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
		this.logDate = logDate;
	}

	// Property accessors

	public MtlogId getId() {
		return this.id;
	}

	public void setId(MtlogId id) {
		this.id = id;
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

	public Timestamp getLogDate() {
		return this.logDate;
	}

	public void setLogDate(Timestamp logDate) {
		this.logDate = logDate;
	}

}