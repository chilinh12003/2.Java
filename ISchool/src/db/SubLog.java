package db;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import uti.MyDate;

/**
 * SubLog entity. @author MyEclipse Persistence Tools
 */

public class SubLog extends DAOBase implements java.io.Serializable
{

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
	private Timestamp logDate;
	private Short isReg;

	// Constructors

	/** default constructor */
	public SubLog()
	{
	}

	/** minimal constructor */
	public SubLog(SubLogId id)
	{
		this.id = id;
	}

	public SubLog(Subscriber mSubObj, Short isReg)
	{
		SubLogId mID = new SubLogId();

		mID.setPhoneNumber(mSubObj.getId().getPhoneNumber());
		mID.setPid(mSubObj.getId().getPid());

		this.setId(mID);

		this.firstDate = mSubObj.getFirstDate();
		this.resetDate = mSubObj.getResetDate();
		this.effectiveDate = mSubObj.getEffectiveDate();
		this.expiryDate = mSubObj.getExpiryDate();
		this.retryChargeDate = mSubObj.getRetryChargeDate();
		this.retryChargeCount = mSubObj.getRetryChargeCount();
		this.renewChargeDate = mSubObj.getRenewChargeDate();
		this.channelId = mSubObj.getChannelId();
		this.statusId = mSubObj.getStatusId();
		this.orderId = mSubObj.getOrderId();
		this.lastBuyDate = mSubObj.getLastBuyDate();
		this.buyType = mSubObj.getBuyType();
		this.questionCount = mSubObj.getQuestionCount();
		this.sendCount = mSubObj.getSendCount();
		this.answerCount = mSubObj.getAnswerCount();
		this.answerRight = mSubObj.getAnswerRight();
		this.answerRightBuyType = mSubObj.getAnswerRightBuyType();
		this.lastAnswerDate = mSubObj.getLastAnswerDate();
		this.lastQuestionId = mSubObj.getLastQuestionId();
		this.lastAnswer = mSubObj.getLastAnswer();
		this.answerStatusId = mSubObj.getAnswerStatusId();
		this.deregDate = mSubObj.getDeregDate();
		this.partnerId = mSubObj.getPartnerId();
		this.weekMark = mSubObj.getWeekMark();
		this.dayMark = mSubObj.getDayMark();
		this.addMark = mSubObj.getAddMark();
		this.chargeMark = mSubObj.getChargeMark();
		this.buyMark = mSubObj.getBuyMark();
		this.answerMark = mSubObj.getAnswerMark();
		this.promotionMark = mSubObj.getPromotionMark();
		this.logDate = MyDate.Date2Timestamp(Calendar.getInstance());
		this.setIsReg(isReg);
	}

	/** full constructor */
	public SubLog(SubLogId id, Timestamp firstDate, Timestamp resetDate, Timestamp effectiveDate, Timestamp expiryDate,
			Timestamp retryChargeDate, Integer retryChargeCount, Timestamp renewChargeDate, Short channelId,
			Short statusId, Integer orderId, Timestamp lastBuyDate, Short buyType, Integer questionCount,
			Integer sendCount, Integer answerCount, Integer answerRight, Integer answerRightBuyType,
			Timestamp lastAnswerDate, Integer lastQuestionId, String lastAnswer, Short answerStatusId,
			Timestamp deregDate, Integer partnerId, Integer weekMark, Integer dayMark, Integer addMark,
			Integer chargeMark, Integer buyMark, Integer answerMark, Integer promotionMark, Timestamp logDate,
			Short isReg)
	{
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
		this.logDate = logDate;
		this.isReg = isReg;
	}

	// Property accessors

	public SubLogId getId()
	{
		return this.id;
	}

	public void setId(SubLogId id)
	{
		this.id = id;
	}

	public Timestamp getFirstDate()
	{
		return this.firstDate;
	}

	public void setFirstDate(Timestamp firstDate)
	{
		this.firstDate = firstDate;
	}

	public Timestamp getResetDate()
	{
		return this.resetDate;
	}

	public void setResetDate(Timestamp resetDate)
	{
		this.resetDate = resetDate;
	}

	public Timestamp getEffectiveDate()
	{
		return this.effectiveDate;
	}

	public void setEffectiveDate(Timestamp effectiveDate)
	{
		this.effectiveDate = effectiveDate;
	}

	public Timestamp getExpiryDate()
	{
		return this.expiryDate;
	}

	public void setExpiryDate(Timestamp expiryDate)
	{
		this.expiryDate = expiryDate;
	}

	public Timestamp getRetryChargeDate()
	{
		return this.retryChargeDate;
	}

	public void setRetryChargeDate(Timestamp retryChargeDate)
	{
		this.retryChargeDate = retryChargeDate;
	}

	public Integer getRetryChargeCount()
	{
		return this.retryChargeCount == null ? 0 : this.retryChargeCount;
	}

	public void setRetryChargeCount(Integer retryChargeCount)
	{
		this.retryChargeCount = retryChargeCount;
	}

	public Timestamp getRenewChargeDate()
	{
		return this.renewChargeDate;
	}

	public void setRenewChargeDate(Timestamp renewChargeDate)
	{
		this.renewChargeDate = renewChargeDate;
	}

	public Short getChannelId()
	{
		return this.channelId;
	}

	public void setChannelId(Short channelId)
	{
		this.channelId = channelId;
	}

	public Short getStatusId()
	{
		return this.statusId;
	}

	public void setStatusId(Short statusId)
	{
		this.statusId = statusId;
	}

	public Integer getOrderId()
	{
		return this.orderId;
	}

	public void setOrderId(Integer orderId)
	{
		this.orderId = orderId;
	}

	public Timestamp getLastBuyDate()
	{
		return this.lastBuyDate;
	}

	public void setLastBuyDate(Timestamp lastBuyDate)
	{
		this.lastBuyDate = lastBuyDate;
	}

	public Short getBuyType()
	{
		return this.buyType;
	}

	public void setBuyType(Short buyType)
	{
		this.buyType = buyType;
	}

	public Integer getQuestionCount()
	{
		return this.questionCount == null ? 0 : this.questionCount;
	}

	public void setQuestionCount(Integer questionCount)
	{
		this.questionCount = questionCount;
	}

	public Integer getSendCount()
	{
		return this.sendCount == null ? 0 : this.sendCount;
	}

	public void setSendCount(Integer sendCount)
	{
		this.sendCount = sendCount;
	}

	public Integer getAnswerCount()
	{
		return this.answerCount == null ? 0 : this.answerCount;
	}

	public void setAnswerCount(Integer answerCount)
	{
		this.answerCount = answerCount;
	}

	public Integer getAnswerRight()
	{
		return this.answerRight == null ? 0 : this.answerRight;
	}

	public void setAnswerRight(Integer answerRight)
	{
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

	public Timestamp getLastAnswerDate()
	{
		return this.lastAnswerDate;
	}

	public void setLastAnswerDate(Timestamp lastAnswerDate)
	{
		this.lastAnswerDate = lastAnswerDate;
	}

	public Integer getLastQuestionId()
	{
		return this.lastQuestionId == null ? 0 : this.lastQuestionId;
	}

	public void setLastQuestionId(Integer lastQuestionId)
	{
		this.lastQuestionId = lastQuestionId;
	}

	public String getLastAnswer()
	{
		return this.lastAnswer;
	}

	public void setLastAnswer(String lastAnswer)
	{
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

	public Timestamp getDeregDate()
	{
		return this.deregDate;
	}

	public void setDeregDate(Timestamp deregDate)
	{
		this.deregDate = deregDate;
	}

	public Integer getPartnerId()
	{
		return this.partnerId;
	}

	public void setPartnerId(Integer partnerId)
	{
		this.partnerId = partnerId;
	}

	public Integer getWeekMark()
	{
		return this.weekMark == null ? 0 : this.weekMark;
	}

	public void setWeekMark(Integer weekMark)
	{
		this.weekMark = weekMark;
	}

	public Integer getDayMark()
	{
		return this.dayMark == null ? 0 : this.dayMark;
	}

	public void setDayMark(Integer dayMark)
	{
		this.dayMark = dayMark;
	}

	public Integer getAddMark()
	{
		return this.addMark == null ? 0 : this.addMark;
	}

	public void setAddMark(Integer addMark)
	{
		this.addMark = addMark;
	}

	public Integer getChargeMark()
	{
		return this.chargeMark == null ? 0 : this.chargeMark;
	}

	public void setChargeMark(Integer chargeMark)
	{
		this.chargeMark = chargeMark;
	}

	public Integer getBuyMark()
	{
		return this.buyMark == null ? 0 : this.buyMark;
	}

	public void setBuyMark(Integer buyMark)
	{
		this.buyMark = buyMark;
	}

	public Integer getAnswerMark()
	{
		return this.answerMark == null ? 0 : this.answerMark;
	}

	public void setAnswerMark(Integer answerMark)
	{
		this.answerMark = answerMark;
	}

	public Integer getPromotionMark()
	{
		return this.promotionMark == null ? 0 : this.promotionMark;
	}

	public void setPromotionMark(Integer promotionMark)
	{
		this.promotionMark = promotionMark;
	}

	public Timestamp getLogDate()
	{
		return this.logDate;
	}

	public void setLogDate(Timestamp logDate)
	{
		this.logDate = logDate;
	}

	public Short getIsReg()
	{
		return this.isReg;
	}

	public void setIsReg(Short isReg)
	{
		this.isReg = isReg;
	}

	/**
	 * Lấy lần hủy đăng ký gần nhất
	 * 
	 * @param PID
	 * @param PhoneNumber
	 * @return
	 */
	public SubLog GetSub(Short PID, String PhoneNumber) throws Exception
	{

		List<?> mList = null;
		try
		{
			String strSQL = "FROM SubLog where pid = " + PID.toString() + " AND PhoneNumber ='" + PhoneNumber
					+ "' ORDER BY OrderID DESC ";

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