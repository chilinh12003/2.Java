package db;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import uti.MyDate;

/**
 * Subscriber entity. @author MyEclipse Persistence Tools
 */

public class Subscriber extends DAOBase implements java.io.Serializable
{

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
	private Integer lastSuggestId = 0;
	private Integer suggestByDay = 0;
	private Integer totalSuggest = 0;
	private Timestamp lastSuggestDate;
	private Integer answerForSuggestId = 0;
	private String lastAnswer;
	private Short answerStatusId = 0;
	private Integer answerByDay = 0;
	private Timestamp lastAnswerDate;
	private Timestamp deregDate;
	private Integer partnerId = 0;

	private Integer weekMark = 0;
	private Integer dayMark = 0;
	private Integer addMark = 0;
	private Integer chargeMark = 0;
	private Integer buyMark = 0;
	private Integer answerMark = 0;

	// Constructors

	/** default constructor */
	public Subscriber()
	{
	}

	public Subscriber(SubLog mSubLog)
	{
		SubscriberId mID = new SubscriberId();

		mID.setPhoneNumber(mSubLog.getId().getPhoneNumber());
		mID.setPid(mSubLog.getId().getPid());
		this.setId(mID);

		this.setFirstDate(mSubLog.getFirstDate());

		this.setResetDate(mSubLog.getResetDate());
		this.setEffectiveDate(mSubLog.getEffectiveDate());
		this.setExpiryDate(mSubLog.getExpiryDate());
		this.setRetryChargeDate(mSubLog.getRetryChargeDate());
		this.setRetryChargeCount(mSubLog.getRetryChargeCount());
		this.setRenewChargeDate(mSubLog.getRenewChargeDate());
		this.setChannelId(mSubLog.getChannelId());
		this.setStatusId(mSubLog.getStatusId());
		this.setOrderId(mSubLog.getOrderId());
		this.setLastSuggestId(mSubLog.getLastSuggestId());
		this.setSuggestByDay(mSubLog.getSuggestByDay());
		this.setTotalSuggest(mSubLog.getTotalSuggest());
		this.setLastSuggestDate(mSubLog.getLastSuggestDate());
		this.setAnswerForSuggestId(mSubLog.getAnswerForSuggestId());
		this.setLastAnswer(mSubLog.getLastAnswer());
		this.setAnswerStatusId(mSubLog.getAnswerStatusId());
		this.setAnswerByDay(mSubLog.getAnswerByDay());
		this.setLastAnswerDate(mSubLog.getLastAnswerDate());
		this.setDeregDate(mSubLog.getDeregDate());
		this.setPartnerId(mSubLog.getPartnerId());

	}

	/** minimal constructor */
	public Subscriber(SubscriberId id, Integer orderId)
	{
		this.id = id;
		this.orderId = orderId;
	}

	/** full constructor */
	public Subscriber(SubscriberId id, Timestamp firstDate, Timestamp resetDate, Timestamp effectiveDate,
			Timestamp expiryDate, Timestamp retryChargeDate, Integer retryChargeCount, Timestamp renewChargeDate,
			Short channelId, Short statusId, Integer orderId, Integer lastSuggestId, Integer suggestByDay,
			Integer totalSuggest, Timestamp lastSuggestDate, Integer answerForSuggestId, String lastAnswer,
			Short answerStatusId, Integer answerByDay, Timestamp lastAnswerDate, Timestamp deregDate,
			Integer partnerId, Integer weekMark, Integer dayMark, Integer addMark, Integer chargeMark, Integer buyMark,
			Integer answerMark)
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
	}

	// Property accessors

	public SubscriberId getId()
	{
		return this.id;
	}

	public void setId(SubscriberId id)
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

	public boolean CheckLastSuggestDate(Calendar mCal_Current) throws Exception
	{
		if (this.lastSuggestDate == null)
			return false;
		Calendar mCal_CheckDate = Calendar.getInstance();

		mCal_CheckDate.setTime(new Date(lastSuggestDate.getTime()));
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
		if (lastAnswerDate != null)
		{
			Calendar mCal_CheckDate = Calendar.getInstance();
			mCal_CheckDate.setTime(new Date(lastAnswerDate.getTime()));

			if (MyDate.Compare(mCal_Current, mCal_CheckDate, Calendar.WEEK_OF_YEAR))
				return true;
		}

		if (lastSuggestDate != null)
		{
			Calendar mCal_CheckDate = Calendar.getInstance();
			mCal_CheckDate.setTime(new Date(lastSuggestDate.getTime()));

			if (MyDate.Compare(mCal_Current, mCal_CheckDate, Calendar.WEEK_OF_YEAR))
				return true;
		}

		if (renewChargeDate != null)
		{
			Calendar mCal_CheckDate = Calendar.getInstance();
			mCal_CheckDate.setTime(new Date(renewChargeDate.getTime()));

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
		String strSQL = " FROM Subscriber WHERE (WeekMark + AddMark + AnswerMark + ChargeMark + BuyMark) = (SELECT max(WeekMark + AddMark + AnswerMark + ChargeMark + BuyMark) FROM Subscriber )  ORDER BY OrderID ASC ";
		return (List<Subscriber>) Get(strSQL);
	}
}