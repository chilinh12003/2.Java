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
	private Integer lastSuggestId;
	private Integer suggestByDay;
	private Integer totalSuggest;
	private Timestamp lastSuggestDate;
	private Integer answerForSuggestId;
	private String lastAnswer;
	private Short answerStatusId;
	private Integer answerByDay;
	private Timestamp lastAnswerDate;
	private Timestamp deregDate;
	private Integer partnerId;
	private Integer weekMark;
	private Integer dayMark;
	private Integer addMark;
	private Integer chargeMark;
	private Integer buyMark;
	private Integer answerMark;
	private Timestamp logDate;
	private Short isReg;

	// Constructors

	/** default constructor */
	public SubLog()
	{
	}


	public SubLog(Subscriber mSubObj, Short isReg)
	{
		SubLogId mID = new SubLogId();

		mID.setPhoneNumber(mSubObj.getId().getPhoneNumber());
		mID.setPid(mSubObj.getId().getPid());

		this.setId(mID);

		this.setFirstDate(mSubObj.getFirstDate());

		this.setResetDate(mSubObj.getResetDate());
		this.setEffectiveDate(mSubObj.getEffectiveDate());
		this.setExpiryDate(mSubObj.getExpiryDate());
		this.setRetryChargeDate(mSubObj.getRetryChargeDate());
		this.setRetryChargeCount(mSubObj.getRetryChargeCount());
		this.setRenewChargeDate(mSubObj.getRenewChargeDate());
		this.setChannelId(mSubObj.getChannelId());
		this.setStatusId(mSubObj.getStatusId());
		this.setOrderId(mSubObj.getOrderId());
		this.setLastSuggestId(mSubObj.getLastSuggestId());
		this.setSuggestByDay(mSubObj.getSuggestByDay());
		this.setTotalSuggest(mSubObj.getTotalSuggest());
		this.setLastSuggestDate(mSubObj.getLastSuggestDate());
		this.setAnswerForSuggestId(mSubObj.getAnswerForSuggestId());
		this.setLastAnswer(mSubObj.getLastAnswer());
		this.setAnswerStatusId(mSubObj.getAnswerStatusId());
		this.setAnswerByDay(mSubObj.getAnswerByDay());
		this.setLastAnswerDate(mSubObj.getLastAnswerDate());
		this.setDeregDate(mSubObj.getDeregDate());
		this.setPartnerId(mSubObj.getPartnerId());
		this.setLogDate(MyDate.Date2Timestamp(Calendar.getInstance()));
		this.setIsReg(isReg);
	}

	
	
	/** minimal constructor */
	public SubLog(SubLogId id, Integer orderId)
	{
		this.id = id;
		this.orderId = orderId;
	}

	/** full constructor */
	public SubLog(SubLogId id, Timestamp firstDate, Timestamp resetDate, Timestamp effectiveDate, Timestamp expiryDate,
			Timestamp retryChargeDate, Integer retryChargeCount, Timestamp renewChargeDate, Short channelId,
			Short statusId, Integer orderId, Integer lastSuggestId, Integer suggestByDay, Integer totalSuggest,
			Timestamp lastSuggestDate, Integer answerForSuggestId, String lastAnswer, Short answerStatusId,
			Integer answerByDay, Timestamp lastAnswerDate, Timestamp deregDate, Integer partnerId, Integer weekMark,
			Integer dayMark, Integer addMark, Integer chargeMark, Integer buyMark, Integer answerMark,
			Timestamp logDate, Short isReg)
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
		this.lastSuggestId = lastSuggestId;
		this.suggestByDay = suggestByDay;
		this.totalSuggest = totalSuggest;
		this.lastSuggestDate = lastSuggestDate;
		this.answerForSuggestId = answerForSuggestId;
		this.lastAnswer = lastAnswer;
		this.answerStatusId = answerStatusId;
		this.answerByDay = answerByDay;
		this.lastAnswerDate = lastAnswerDate;
		this.deregDate = deregDate;
		this.partnerId = partnerId;
		this.weekMark = weekMark;
		this.dayMark = dayMark;
		this.addMark = addMark;
		this.chargeMark = chargeMark;
		this.buyMark = buyMark;
		this.answerMark = answerMark;
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
		return this.retryChargeCount;
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

	public Integer getLastSuggestId()
	{
		return this.lastSuggestId;
	}

	public void setLastSuggestId(Integer lastSuggestId)
	{
		this.lastSuggestId = lastSuggestId;
	}

	public Integer getSuggestByDay()
	{
		return this.suggestByDay;
	}

	public void setSuggestByDay(Integer suggestByDay)
	{
		this.suggestByDay = suggestByDay;
	}

	public Integer getTotalSuggest()
	{
		return this.totalSuggest;
	}

	public void setTotalSuggest(Integer totalSuggest)
	{
		this.totalSuggest = totalSuggest;
	}

	public Timestamp getLastSuggestDate()
	{
		return this.lastSuggestDate;
	}

	public void setLastSuggestDate(Timestamp lastSuggestDate)
	{
		this.lastSuggestDate = lastSuggestDate;
	}

	public Integer getAnswerForSuggestId()
	{
		return this.answerForSuggestId;
	}

	public void setAnswerForSuggestId(Integer answerForSuggestId)
	{
		this.answerForSuggestId = answerForSuggestId;
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
		return this.answerStatusId;
	}

	public void setAnswerStatusId(Short answerStatusId)
	{
		this.answerStatusId = answerStatusId;
	}

	public Integer getAnswerByDay()
	{
		return this.answerByDay;
	}

	public void setAnswerByDay(Integer answerByDay)
	{
		this.answerByDay = answerByDay;
	}

	public Timestamp getLastAnswerDate()
	{
		return this.lastAnswerDate;
	}

	public void setLastAnswerDate(Timestamp lastAnswerDate)
	{
		this.lastAnswerDate = lastAnswerDate;
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
		return this.weekMark;
	}

	public void setWeekMark(Integer weekMark)
	{
		this.weekMark = weekMark;
	}

	public Integer getDayMark()
	{
		return this.dayMark;
	}

	public void setDayMark(Integer dayMark)
	{
		this.dayMark = dayMark;
	}

	public Integer getAddMark()
	{
		return this.addMark;
	}

	public void setAddMark(Integer addMark)
	{
		this.addMark = addMark;
	}

	public Integer getChargeMark()
	{
		return this.chargeMark;
	}

	public void setChargeMark(Integer chargeMark)
	{
		this.chargeMark = chargeMark;
	}

	public Integer getBuyMark()
	{
		return this.buyMark;
	}

	public void setBuyMark(Integer buyMark)
	{
		this.buyMark = buyMark;
	}

	public Integer getAnswerMark()
	{
		return this.answerMark;
	}

	public void setAnswerMark(Integer answerMark)
	{
		this.answerMark = answerMark;
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