package db;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

	/**
	 * Cho biết khách hàng đã mua bộ câu hỏi nào rồi
	 * @author Administrator
	 *
	 */
	public enum BuyQuestionType
	{
		Nothing(0),
		/**
		 * Có 5 câu hỏi free được phép trả lời
		 */
		QuestionFree(1),
		/**
		 * Chỉ mua bộ 3 câu hỏi
		 */
		BuyOneQuestion(2),
		/**
		 * Chỉ mua bộ 7 câu hỏi
		 */
		BuyTwoQuestion(3),

		/**
		 * Mua bộ 3 câu hỏi trước sau đó mua tiếp bộ 7 câu hỏi
		 */
		BuyOneTwoQuestion(4),
		/**
		 * Mua bộ 7 câu hỏi trước sau đó mua tiếp bộ 3 câu hỏi
		 */
		BuyTwoOneQuestion(5);

		private int value;

		private BuyQuestionType(int value)
		{
			this.value = value;
		}

		public Short GetValue()
		{
			return (Short) ((Integer) this.value).shortValue();
		}

		public static BuyQuestionType FromValue(Short iValue)
		{
			for (BuyQuestionType type : BuyQuestionType.values())
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
	private Timestamp lastBuyDate;
	private Short buyType;
	private Integer questionCount;
	private Integer sendCount;
	private Integer answerCount;
	private Integer answerRight;
	private Integer answerRightBuyType;
	private Timestamp lastAnswerDate;
	private Integer lastQuestionId;
	

	private String lastAnswer;
	private Short answerStatusId;
	private Timestamp deregDate;
	private Integer partnerId;
	private Integer weekMark;
	private Integer dayMark;
	private Integer addMark;
	private Integer chargeMark;
	private Integer buyMark;
	private Integer answerMark;
	private Integer promotionMark;

	// Constructors

	/** default constructor */
	public Subscriber() {
	}

	/** minimal constructor */
	public Subscriber(SubscriberId id, Integer orderId) {
		this.id = id;
		this.orderId = orderId;
	}

	public Subscriber(SubLog mSubLog)
	{
		SubscriberId mID = new SubscriberId();

		mID.setPhoneNumber(mSubLog.getId().getPhoneNumber());
		mID.setPid(mSubLog.getId().getPid());
		this.setId(mID);

		this.setFirstDate(mSubLog.getFirstDate());

		this.firstDate =mSubLog.getFirstDate();
		this.resetDate =mSubLog.getResetDate();
		this.effectiveDate =mSubLog.getEffectiveDate();
		this.expiryDate =mSubLog.getExpiryDate();
		this.retryChargeDate =mSubLog.getRetryChargeDate();
		this.retryChargeCount =mSubLog.getRetryChargeCount();
		this.renewChargeDate =mSubLog.getRenewChargeDate();
		this.channelId =mSubLog.getChannelId();
		this.statusId =mSubLog.getStatusId();
		this.orderId =mSubLog.getOrderId();
		this.lastBuyDate =mSubLog.getLastBuyDate();
		this.buyType =mSubLog.getBuyType();
		this.questionCount =mSubLog.getQuestionCount();
		this.sendCount =mSubLog.getSendCount();
		this.answerCount =mSubLog.getAnswerCount();
		this.answerRight =mSubLog.getAnswerRight();
		this.answerRightBuyType = mSubLog.getAnswerRightBuyType();
		this.lastAnswerDate =mSubLog.getLastAnswerDate();
		this.lastQuestionId =mSubLog.getLastQuestionId();
		this.lastAnswer =mSubLog.getLastAnswer();
		this.answerStatusId = mSubLog.getAnswerStatusId();
		this.deregDate =mSubLog.getDeregDate();
		this.partnerId =mSubLog.getPartnerId();
		this.weekMark =mSubLog.getWeekMark();
		this.dayMark =mSubLog.getDayMark();
		this.addMark =mSubLog.getAddMark();
		this.chargeMark =mSubLog.getChargeMark();
		this.buyMark =mSubLog.getBuyMark();
		this.answerMark =mSubLog.getAnswerMark();
		this.promotionMark =mSubLog.getPromotionMark();

	}

	/** full constructor */
	public Subscriber(SubscriberId id, Timestamp firstDate,
			Timestamp resetDate, Timestamp effectiveDate, Timestamp expiryDate,
			Timestamp retryChargeDate, Integer retryChargeCount,
			Timestamp renewChargeDate, Short channelId, Short statusId,
			Integer orderId, Timestamp lastBuyDate, Short buyType,
			Integer questionCount, Integer sendCount, Integer answerCount,
			Integer answerRight, Integer answerRightBuyType, Timestamp lastAnswerDate,
			Integer lastQuestionId, String lastAnswer, Short answerStatusId, Timestamp deregDate,
			Integer partnerId, Integer weekMark, Integer dayMark,
			Integer addMark, Integer chargeMark, Integer buyMark,
			Integer answerMark, Integer promotionMark) {
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
		this.lastBuyDate = lastBuyDate;
		this.buyType = buyType;
		this.questionCount = questionCount;
		this.sendCount = sendCount;
		this.answerCount = answerCount;
		this.answerRight = answerRight;
		this.answerRightBuyType = answerRightBuyType;
		this.lastAnswerDate = lastAnswerDate;
		this.lastQuestionId = lastQuestionId;
		this.lastAnswer = lastAnswer;
		this.answerStatusId = answerStatusId;
		this.deregDate = deregDate;
		this.partnerId = partnerId;
		this.weekMark = weekMark;
		this.dayMark = dayMark;
		this.addMark = addMark;
		this.chargeMark = chargeMark;
		this.buyMark = buyMark;
		this.answerMark = answerMark;
		this.promotionMark = promotionMark;
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
		return this.retryChargeCount == null ? 0 : this.retryChargeCount;
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

	public Timestamp getLastBuyDate() {
		return this.lastBuyDate;
	}

	public void setLastBuyDate(Timestamp lastBuyDate) {
		this.lastBuyDate = lastBuyDate;
	}

	public Short getBuyType() {
		return this.buyType;
	}

	public void setBuyType(Short buyType) {
		this.buyType = buyType;
	}

	public Integer getQuestionCount() {
		return this.questionCount == null ? 0 : this.questionCount;
	}

	public void setQuestionCount(Integer questionCount) {
		this.questionCount = questionCount;
	}

	public Integer getSendCount() {
		return this.sendCount == null ? 0 : this.sendCount;
	}

	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}

	public Integer getAnswerCount() {
		return this.answerCount == null ? 0 : this.answerCount;
	}

	public void setAnswerCount(Integer answerCount) {
		this.answerCount = answerCount;
	}

	public Integer getAnswerRight() {
		return this.answerRight == null ? 0 : this.answerRight;
	}

	public void setAnswerRight(Integer answerRight) {
		this.answerRight = answerRight;
	}

	public Integer getAnswerRightBuyType()
	{
		return answerRightBuyType == null ? 0 : this.answerRightBuyType;
	}

	public void setAnswerRightBuyType(Integer answerRightBuyType)
	{
		this.answerRightBuyType = answerRightBuyType;
	}
	
	public Timestamp getLastAnswerDate() {
		return this.lastAnswerDate;
	}

	public void setLastAnswerDate(Timestamp lastAnswerDate) {
		this.lastAnswerDate = lastAnswerDate;
	}

	public Integer getLastQuestionId() {
		return this.lastQuestionId == null ? 0 : this.lastQuestionId;
	}

	public void setLastQuestionId(Integer lastQuestionId) {
		this.lastQuestionId = lastQuestionId;
	}

	public String getLastAnswer() {
		return this.lastAnswer;
	}

	public void setLastAnswer(String lastAnswer) {
		this.lastAnswer = lastAnswer;
	}

	public Short getAnswerStatusId()
	{
		return answerStatusId;
	}

	public void setAnswerStatusId(Short answerStatusId)
	{
		this.answerStatusId = answerStatusId;
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

	public Integer getWeekMark() {
		return this.weekMark == null ? 0 : this.weekMark;
	}

	public void setWeekMark(Integer weekMark) {
		this.weekMark = weekMark;
	}

	public Integer getDayMark() {
		return this.dayMark == null ? 0 : this.dayMark;
	}

	public void setDayMark(Integer dayMark) {
		this.dayMark = dayMark;
	}

	public Integer getAddMark() {
		return this.addMark == null ? 0 : this.addMark;
	}

	public void setAddMark(Integer addMark) {
		this.addMark = addMark;
	}

	public Integer getChargeMark() {
		return this.chargeMark== null ? 0 : this.chargeMark;
	}

	public void setChargeMark(Integer chargeMark) {
		this.chargeMark = chargeMark;
	}

	public Integer getBuyMark() {
		return this.buyMark== null ? 0 : this.buyMark;
	}

	public void setBuyMark(Integer buyMark) {
		this.buyMark = buyMark;
	}

	public Integer getAnswerMark() {
		return this.answerMark== null ? 0 : this.answerMark;
	}

	public void setAnswerMark(Integer answerMark) {
		this.answerMark = answerMark;
	}

	public Integer getPromotionMark() {
		return this.promotionMark== null ? 0 : this.promotionMark;
	}

	public void setPromotionMark(Integer promotionMark) {
		this.promotionMark = promotionMark;
	}

	
	public Subscriber GetSub(Short PID, String PhoneNumber) throws Exception
	{

		List<?> mList = null;
		try
		{
			String strSQL = "FROM " + this.getClass().getName() + " WHERE pid = " + PID.toString()
					+ " AND phoneNumber ='" + PhoneNumber + "'";

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
 * Kiểm tra xem có phải vừa mới hủy trong ngày hay không
 * @param mCal_Current
 * @return
 * @throws Exception
 */
	public boolean CheckSameDeregDay(Calendar mCal_Current) throws Exception
	{
		if (this.deregDate == null)
			return false;
		Calendar mCal_CheckDate = Calendar.getInstance();

		mCal_CheckDate.setTime(new Date(this.deregDate .getTime()));
		if (mCal_Current.get(Calendar.YEAR) == mCal_CheckDate.get(Calendar.YEAR)
				&& mCal_Current.get(Calendar.MONTH) == mCal_CheckDate.get(Calendar.MONTH)
				&& mCal_Current.get(Calendar.DATE) == mCal_CheckDate.get(Calendar.DATE))
			return true;
		else return false;
	}
	
	/**
	 * Kiểm tra Ngày truyền vào cùng ngày với ngày hủy hay không
	 * @param mCal_Current
	 * @return
	 * @throws Exception
	 */
	
	public boolean IsSameDayWithDereg(Calendar mCal_Current) throws Exception
	{
		if (this.deregDate == null)
			return false;
		Calendar mCal_CheckDate = Calendar.getInstance();

		mCal_CheckDate.setTime(new Date(this.deregDate .getTime()));
		if (mCal_Current.get(Calendar.YEAR) == mCal_CheckDate.get(Calendar.YEAR)
				&& mCal_Current.get(Calendar.MONTH) == mCal_CheckDate.get(Calendar.MONTH)
				&& mCal_Current.get(Calendar.DATE) == mCal_CheckDate.get(Calendar.DATE))
			return true;
		else return false;
	}

	
	public boolean CheckLastBuyDate(Calendar mCal_Current) throws Exception
	{
		if (this.lastBuyDate == null)
			return false;
		Calendar mCal_CheckDate = Calendar.getInstance();

		mCal_CheckDate.setTime(new Date(this.lastBuyDate .getTime()));
		if (mCal_Current.get(Calendar.YEAR) == mCal_CheckDate.get(Calendar.YEAR)
				&& mCal_Current.get(Calendar.MONTH) == mCal_CheckDate.get(Calendar.MONTH)
				&& mCal_Current.get(Calendar.DATE) == mCal_CheckDate.get(Calendar.DATE))
			return true;
		else return false;
	}

	public boolean CheckLastAnswerDate(Calendar mCal_Current) throws Exception
	{
		if (lastAnswerDate == null)
			return false;
		Calendar mCal_CheckDate = Calendar.getInstance();

		mCal_CheckDate.setTime(new Date(lastAnswerDate.getTime()));
		if (mCal_Current.get(Calendar.YEAR) == mCal_CheckDate.get(Calendar.YEAR)
				&& mCal_Current.get(Calendar.MONTH) == mCal_CheckDate.get(Calendar.MONTH)
				&& mCal_Current.get(Calendar.DATE) == mCal_CheckDate.get(Calendar.DATE))
			return true;
		else return false;
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
		if (deregDate != null)
		{
			Calendar mCal_CheckDate = Calendar.getInstance();
			mCal_CheckDate.setTime(new Date(deregDate.getTime()));

			if (MyDate.Compare(mCal_Current, mCal_CheckDate, Calendar.WEEK_OF_YEAR))
				return true;
		}
		
		if (expiryDate != null)
		{
			Calendar mCal_CheckDate = Calendar.getInstance();
			mCal_CheckDate.setTime(new Date(expiryDate.getTime()));

			if (MyDate.Compare(mCal_Current, mCal_CheckDate, Calendar.WEEK_OF_YEAR))
				return true;
		}
		
		return false;
	}

	/**
	 * lấy danh sách người có điểm cao nhất
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Subscriber> getTopWeekMark() throws Exception
	{
		String strSQL = " FROM Subscriber WHERE (WeekMark + AddMark + AnswerMark + ChargeMark + BuyMark + PromotionMark) = (SELECT max(WeekMark + AddMark + AnswerMark + ChargeMark + BuyMark + PromotionMark) FROM Subscriber )  ORDER BY OrderID ASC ";
		return (List<Subscriber>) Get(strSQL);
	}
}