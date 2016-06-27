package db;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import uti.MyConfig;
import uti.MyDate;

/**
 * Subscriber entity. @author MyEclipse Persistence Tools
 */

public class Subscriber extends DAOBase implements java.io.Serializable {


	public enum Status
	{
		NoThing(0),
		/**
		 * Kích hoạt
		 */
		Active(1),
		/**
		 * Tạm dừng
		 */
		Pending(2), ;

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

	public enum InitType
	{
		Nothing(0),
		/**
		 * Đăng ký mới
		 */
		NewReg(1),
		/**
		 * Đăng ký nhưng trước đó đã hủy dịch vụ
		 */
		RegAgain(2),
		/**
		 * Đăng ký cho số thuê bao đã bị Hủy từ Vinaphone
		 */
		UndoReg(3),

		/**
		 * Khôi phục
		 */
		Restore(4);

		private int value;

		private InitType(int value)
		{
			this.value = value;
		}

		public Short GetValue()
		{
			return (Short) ((Integer) this.value).shortValue();
		}

		public static InitType FromValue(Short iValue)
		{
			for (InitType type : InitType.values())
			{
				if (type.GetValue().equals(iValue))
					return type;
			}
			return Nothing;
		}
	}

	// Fields

	private SubscriberId id;
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

	// Constructors

	/** default constructor */
	public Subscriber() {
	}

	/** minimal constructor */
	public Subscriber(SubscriberId id, Integer orderId) {
		this.id = id;
		this.orderId = orderId;
	}
	

	public Subscriber(SubLog subLogObj)
	{
		SubscriberId mID = new SubscriberId();

		mID.setPhoneNumber(subLogObj.getId().getPhoneNumber());
		mID.setPid(subLogObj.getId().getPid());
		mID.setServiceId(subLogObj.getId().getServiceId());
		
		this.setId(mID);

		this.setFirstDate(subLogObj.getFirstDate());

		this.setResetDate(subLogObj.getResetDate());
		this.setEffectiveDate(subLogObj.getEffectiveDate());
		this.setExpiryDate(subLogObj.getExpiryDate());
		this.setRetryChargeDate(subLogObj.getRetryChargeDate());
		this.setRetryChargeCount(subLogObj.getRetryChargeCount());
		this.setRenewChargeDate(subLogObj.getRenewChargeDate());
		this.setChannelId(subLogObj.getChannelId());
		this.setStatusId(subLogObj.getStatusId());
		this.setOrderId(subLogObj.getOrderId());
		
		this.setDeregDate(subLogObj.getDeregDate());
		this.setPartnerId(subLogObj.getPartnerId());
	}

	

	/** full constructor */
	public Subscriber(SubscriberId id, Timestamp firstDate,
			Timestamp resetDate, Timestamp effectiveDate, Timestamp expiryDate,
			Timestamp retryChargeDate, Integer retryChargeCount,
			Timestamp renewChargeDate, Short channelId, Short statusId,
			Integer orderId, Timestamp deregDate, Integer partnerId) {
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
	}

	// Property accessors

	public SubscriberId getId() {
		return this.id;
	}

	public void setId(SubscriberId id) {
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

	public Subscriber GetSub(Short PID, String PhoneNumber, Service serviceObj) throws Exception
	{

		List<?> mList = null;
		try
		{
			String strSQL = "FROM " + this.getClass().getName() + " WHERE pid = " + PID.toString()
					+ " AND phoneNumber ='" + PhoneNumber + "' AND ServiceID = "+ serviceObj.getServiceId().toString();

			mList = (List<?>) Get(strSQL);
			if (mList.size() > 0)
			{
				return (Subscriber) mList.get(0);
			}

			return null;

		}
		catch (Exception ex)
		{
			throw ex;
		}

	}

	@SuppressWarnings("unchecked")
	public List<Subscriber> GetSub(Short PID, Integer OrderID, Integer RowCount, Integer threadNumber,
			Integer threadIndex) throws Exception
	{

		List<Subscriber> mList = null;
		try
		{
			String strSQL = "FROM Subscriber WHERE pid = " + PID.toString() + " AND orderId > " + OrderID.toString()
					+ " AND (mod(orderId," + threadNumber + ")=" + threadIndex + ")" + " ORDER BY orderId ASC";

			mList = (List<Subscriber>) Get(strSQL, RowCount);

			return mList;

		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public List<Subscriber> GetSub(Short PID, Integer OrderID, Status mStatus, Integer RowCount, Integer threadNumber,
			Integer threadIndex) throws Exception
	{

		List<Subscriber> mList = null;
		try
		{
			String strSQL = "FROM Subscriber WHERE pid = " + PID.toString() + " AND orderId > " + OrderID.toString()
					+ " AND statusId =" + mStatus.GetValue().toString() + "  AND (mod(orderId," + threadNumber + ")="
					+ threadIndex + ")" + " ORDER BY orderId ASC";

			mList = (List<Subscriber>) Get(strSQL, RowCount);

			return mList;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	/**
	 * Lấy các thuê bao pedding và chưa push notifyRenewFail
	 * 
	 * @param PID
	 * @param OrderID
	 * @param mStatus
	 * @param RowCount
	 * @param calBegin
	 * @param calEnd
	 * @return
	 * @throws Exception
	 */
	public List<Subscriber> getSubPending(Short PID, Integer OrderID, Status mStatus, Integer RowCount,
			Calendar calExpiryDate) throws Exception
	{
		List<Subscriber> mList = null;
		try
		{
			String strSQL = "FROM Subscriber WHERE pid = " + PID.toString() + " AND orderId > " + OrderID.toString()
					+ " AND statusId =" + mStatus.GetValue().toString() + "  AND ExpiryDate = '"
					+ MyConfig.Get_DateFormat_InsertDB().format(calExpiryDate.getTime()) + "' "
					+ " ORDER BY orderId ASC";

			mList = (List<Subscriber>) Get(strSQL, RowCount);

			return mList;

		}
		catch (Exception ex)
		{
			throw ex;
		}
	}


	/**
	 * Kiểm tra xem ngày truyền vào có cùng tuần với các ngày của Sub này không
	 * 
	 * @param mCal_Current
	 * @return
	 * @throws Exception
	 */
	public boolean CheckIsWeek(Calendar mCal_Current) throws Exception
	{	

		if (renewChargeDate != null)
		{
			Calendar mCal_CheckDate = Calendar.getInstance();
			mCal_CheckDate.setTime(new Date(renewChargeDate.getTime()));

			if (MyDate.Compare(mCal_Current, mCal_CheckDate, Calendar.WEEK_OF_YEAR))
				return true;
		}

		if (effectiveDate != null)
		{
			Calendar mCal_CheckDate = Calendar.getInstance();
			mCal_CheckDate.setTime(new Date(effectiveDate.getTime()));

			if (MyDate.Compare(mCal_Current, mCal_CheckDate, Calendar.WEEK_OF_YEAR))
				return true;
		}

		return false;
	}

	public boolean CheckDeregSameDate(Calendar calCurrent) throws Exception
	{
		if(this.deregDate== null)
			return false;
		Calendar calDeregDate = Calendar.getInstance();
		calDeregDate.setTime(new Date(this.deregDate.getTime()));
		return MyDate.Compare(calCurrent, calDeregDate, Calendar.DATE);
	}
	
}