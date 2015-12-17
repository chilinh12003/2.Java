package db;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import uti.MyConfig;
import antlr.collections.impl.Vector;

public class Play extends DAOBase implements java.io.Serializable
{
	public enum PlayType
	{
		Nothing(0), BuySuggest(1), Answer(2),
		/**
		 * Khi kết thúc 1 ngày thì lưu thông tin của Sub vào play
		 */
		FinishDay(3);

		private int value;

		private PlayType(int value)
		{
			this.value = value;
		}

		public Short GetValue()
		{
			return (Short) ((Integer) this.value).shortValue();
		}

		public static PlayType FromValue(Short iValue)
		{
			for (PlayType type : PlayType.values())
			{
				if (type.GetValue().equals(iValue))
					return type;
			}
			return Nothing;
		}
	}

	/**
	 * Tình trạng cho biết, khách hàng mua dữ kiện thành công. hoặc
	 * trả lời thành công...
	 * 
	 * @author Administrator
	 * 
	 */
	public enum Status
	{
		Nothing(0), CorrectAnswer(1), IncorrectAnswer(2), BuySuggest(3), ;

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

	// Fields

	private PlayId id;
	private Short pid;
	private Integer questionId;
	private Timestamp playDate;

	private Integer suggestId;
	private Short playTypeId;
	private Timestamp receiveDate;
	private Integer orderNumber;
	private String userAnswer;
	private Short statusId;

	private Integer weekMark = 0;
	private Integer dayMark = 0;
	private Integer addMark = 0;
	private Integer chargeMark = 0;
	private Integer buyMark = 0;
	private Integer answerMark = 0;

	private Timestamp insertDate;

	// Constructors

	/** default constructor */
	public Play()
	{
	}

	/** minimal constructor */
	public Play(PlayId id)
	{
		this.id = id;
	}

	/** full constructor */
	public Play(PlayId id, Short pid, Integer questionId, Timestamp playDate, Integer suggestId, Short playTypeId,
			Timestamp receiveDate, Integer orderNumber, String userAnswer, Short statusId, Integer weekMark,
			Integer dayMark, Integer addMark, Integer chargeMark, Integer buyMark, Integer answerMark,
			Timestamp insertDate)
	{
		super();
		this.id = id;
		this.pid = pid;
		this.questionId = questionId;
		this.playDate = playDate;
		this.suggestId = suggestId;
		this.playTypeId = playTypeId;
		this.receiveDate = receiveDate;
		this.orderNumber = orderNumber;
		this.userAnswer = userAnswer;
		this.statusId = statusId;
		this.weekMark = weekMark;
		this.dayMark = dayMark;
		this.addMark = addMark;
		this.chargeMark = chargeMark;
		this.buyMark = buyMark;
		this.answerMark = answerMark;
		this.insertDate = insertDate;
	}

	// Property accessors

	public PlayId getId()
	{
		return this.id;
	}

	public void setId(PlayId id)
	{
		this.id = id;
	}

	public Short getPid()
	{
		return this.pid;
	}

	public void setPid(Short pid)
	{
		this.pid = pid;
	}

	public Integer getQuestionId()
	{
		return this.questionId;
	}

	public void setQuestionId(Integer questionId)
	{
		this.questionId = questionId;
	}

	public Timestamp getPlayDate()
	{
		return this.playDate;
	}

	public void setPlayDate(Timestamp playDate)
	{
		this.playDate = playDate;
	}

	public Integer getSuggestId()
	{
		return this.suggestId;
	}

	public void setSuggestId(Integer suggestId)
	{
		this.suggestId = suggestId;
	}

	public Short getPlayTypeId()
	{
		return this.playTypeId;
	}

	public void setPlayTypeId(Short playTypeId)
	{
		this.playTypeId = playTypeId;
	}

	public Timestamp getReceiveDate()
	{
		return this.receiveDate;
	}

	public void setReceiveDate(Timestamp receiveDate)
	{
		this.receiveDate = receiveDate;
	}

	public Integer getOrderNumber()
	{
		return this.orderNumber;
	}

	public void setOrderNumber(Integer orderNumber)
	{
		this.orderNumber = orderNumber;
	}

	public String getUserAnswer()
	{
		return this.userAnswer;
	}

	public void setUserAnswer(String userAnswer)
	{
		this.userAnswer = userAnswer;
	}

	public Short getStatusId()
	{
		return this.statusId;
	}

	public void setStatusId(Short statusId)
	{
		this.statusId = statusId;
	}

	public Integer getWeekMark()
	{
		return weekMark;
	}

	public void setWeekMark(Integer weekMark)
	{
		this.weekMark = weekMark;
	}

	public Integer getDayMark()
	{
		return dayMark;
	}

	public void setDayMark(Integer dayMark)
	{
		this.dayMark = dayMark;
	}

	public Integer getAddMark()
	{
		return addMark;
	}

	public void setAddMark(Integer addMark)
	{
		this.addMark = addMark;
	}

	public Integer getChargeMark()
	{
		return chargeMark;
	}

	public void setChargeMark(Integer chargeMark)
	{
		this.chargeMark = chargeMark;
	}

	public Integer getBuyMark()
	{
		return buyMark;
	}

	public void setBuyMark(Integer buyMark)
	{
		this.buyMark = buyMark;
	}

	public Integer getAnswerMark()
	{
		return answerMark;
	}

	public void setAnswerMark(Integer answerMark)
	{
		this.answerMark = answerMark;
	}

	public Timestamp getInsertDate()
	{
		return insertDate;
	}

	public void setInsertDate(Timestamp insertDate)
	{
		this.insertDate = insertDate;
	}

	/**
	 * lấy tổng số thời gian đã trả lời đúng trong 1 tuần của thuê bao
	 * 
	 * @param PhoneNumber
	 * @return
	 */
	public Long getTotalTime(String PhoneNumber, Calendar calBegin, Calendar calEnd) throws Exception
	{
		String strSQL = "SELECT 	SUM(HOUR(ReceiveDate)*60*60*1000 + MINUTE(ReceiveDate)*60*1000 + "
				+ "	SECOND(ReceiveDate)*1000 + MICROSECOND(ReceiveDate)) as TotalTime " + "FROM 	Play "
				+ "WHERE PhoneNumber='" + PhoneNumber + "' AND	PlayTypeID = " + PlayType.Answer.GetValue().toString()
				+ " AND StatusID = " + Status.CorrectAnswer.GetValue().toString() + " AND PlayDate BETWEEN '"
				+ MyConfig.Get_DateFormat_InsertDB().format(calBegin.getTime()) + "' AND '"
				+ MyConfig.Get_DateFormat_InsertDB().format(calEnd.getTime()) + "' " + "GROUP BY PhoneNumber ";
		Long TotalTime = (Long) this.GetUnique(strSQL);
		return TotalTime;
	}

	@SuppressWarnings("unchecked")
	public List<Play> GetList(int rowCount) throws Exception
	{
		String strSQL = "FROM Play ORDER BY LogID ASC ";
		return (List<Play>) Get(strSQL, rowCount);
	}
}