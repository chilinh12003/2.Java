package db;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import uti.MyDate;

/**
 * SubLog entity. @author MyEclipse Persistence Tools
 */

public class SubLog extends DAOBase implements java.io.Serializable {

	// Fields

	private SubLogId id;
	private Timestamp firstDate;
	private Timestamp resetDate;
	private Timestamp effectiveDate;
	private Timestamp expiryDate;
	private Timestamp retryChargeDate;
	private Integer retryChargeCount;
	private Timestamp renewChargeDate;
	private Short channelId;
	private Short statusId;
	private Integer orderId;
	private Timestamp deregDate;
	private Integer partnerId;
	private Timestamp logDate;
	private Short isReg;

	// Constructors

	/** default constructor */
	public SubLog() {
	}
	
	/** minimal constructor */
	public SubLog(SubLogId id) {
		this.id = id;
	}

	public SubLog(Subscriber subObj, Short isReg) {
		SubLogId mID = new SubLogId();

		mID.setPhoneNumber(subObj.getId().getPhoneNumber());
		mID.setPid(subObj.getId().getPid());
		mID.setServiceId(subObj.getId().getServiceId());
		this.setId(mID);

		this.setFirstDate(subObj.getFirstDate());

		this.setResetDate(subObj.getResetDate());
		this.setEffectiveDate(subObj.getEffectiveDate());
		this.setExpiryDate(subObj.getExpiryDate());
		this.setRetryChargeDate(subObj.getRetryChargeDate());
		this.setRetryChargeCount(subObj.getRetryChargeCount());
		this.setRenewChargeDate(subObj.getRenewChargeDate());
		this.setChannelId(subObj.getChannelId());
		this.setStatusId(subObj.getStatusId());
		this.setOrderId(subObj.getOrderId());
	
		this.setDeregDate(subObj.getDeregDate());
		this.setPartnerId(subObj.getPartnerId());
	
		this.setLogDate(MyDate.Date2Timestamp(Calendar.getInstance()));
		this.setIsReg(isReg);
	}

	/** full constructor */
	public SubLog(SubLogId id, Timestamp firstDate, Timestamp resetDate,
			Timestamp effectiveDate, Timestamp expiryDate,
			Timestamp retryChargeDate, Integer retryChargeCount,
			Timestamp renewChargeDate, Short channelId, Short statusId,
			Integer orderId, Timestamp deregDate, Integer partnerId,
			Timestamp logDate, Short isReg) {
		this.id = id;
		this.firstDate = firstDate;
		this.resetDate = resetDate;
		this.effectiveDate = effectiveDate;
		this.expiryDate = expiryDate;
		this.retryChargeDate = retryChargeDate;
		this.retryChargeCount = retryChargeCount;
		this.renewChargeDate = renewChargeDate;
		this.channelId = channelId;
		this.statusId = statusId;
		this.orderId = orderId;
		this.deregDate = deregDate;
		this.partnerId = partnerId;
		this.logDate = logDate;
		this.isReg = isReg;
	}

	// Property accessors

	public SubLogId getId() {
		return this.id;
	}

	public void setId(SubLogId id) {
		this.id = id;
	}

	public Timestamp getFirstDate() {
		return this.firstDate;
	}

	public void setFirstDate(Timestamp firstDate) {
		this.firstDate = firstDate;
	}

	public Timestamp getResetDate() {
		return this.resetDate;
	}

	public void setResetDate(Timestamp resetDate) {
		this.resetDate = resetDate;
	}

	public Timestamp getEffectiveDate() {
		return this.effectiveDate;
	}

	public void setEffectiveDate(Timestamp effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Timestamp getExpiryDate() {
		return this.expiryDate;
	}

	public void setExpiryDate(Timestamp expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Timestamp getRetryChargeDate() {
		return this.retryChargeDate;
	}

	public void setRetryChargeDate(Timestamp retryChargeDate) {
		this.retryChargeDate = retryChargeDate;
	}

	public Integer getRetryChargeCount() {
		return this.retryChargeCount;
	}

	public void setRetryChargeCount(Integer retryChargeCount) {
		this.retryChargeCount = retryChargeCount;
	}

	public Timestamp getRenewChargeDate() {
		return this.renewChargeDate;
	}

	public void setRenewChargeDate(Timestamp renewChargeDate) {
		this.renewChargeDate = renewChargeDate;
	}

	public Short getChannelId() {
		return this.channelId;
	}

	public void setChannelId(Short channelId) {
		this.channelId = channelId;
	}

	public Short getStatusId() {
		return this.statusId;
	}

	public void setStatusId(Short statusId) {
		this.statusId = statusId;
	}

	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Timestamp getDeregDate() {
		return this.deregDate;
	}

	public void setDeregDate(Timestamp deregDate) {
		this.deregDate = deregDate;
	}

	public Integer getPartnerId() {
		return this.partnerId;
	}

	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}

	public Timestamp getLogDate() {
		return this.logDate;
	}

	public void setLogDate(Timestamp logDate) {
		this.logDate = logDate;
	}

	public Short getIsReg() {
		return this.isReg;
	}

	public void setIsReg(Short isReg) {
		this.isReg = isReg;
	}

	/**
	 * Lấy lần hủy đăng ký gần nhất
	 * 
	 * @param PID
	 * @param PhoneNumber
	 * @return
	 */
	public SubLog GetSub(Short PID, String PhoneNumber, Service serviceObj) throws Exception
	{

		List<?> mList = null;
		try
		{
			String strSQL = "FROM SubLog where pid = " + PID.toString() + " AND PhoneNumber ='" + PhoneNumber
					+ "' AND ServiceID = "+ serviceObj.getServiceId().toString()+ " ORDER BY OrderID DESC ";

			mList = (List<?>) Get(strSQL, 1);
			if (mList.size() > 0)
			{
				return (SubLog) mList.get(0);
			}
			return null;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}
}