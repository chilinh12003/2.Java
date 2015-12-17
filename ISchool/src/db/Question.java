package db;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import uti.MyConfig;

/**
 * Question entity. @author MyEclipse Persistence Tools
 */

public class Question extends DAOBase implements java.io.Serializable {

	public enum Status
	{
		Nothing(0),
		
		Active(1),
		
		Deactive(2),;

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

	private Integer questionId =0;
	private Integer sessionId;
	private Timestamp createDate;
	private String rightAnswer;
	private Integer orderNumber;
	private String mt;

	// Constructors

	/** default constructor */
	public Question() {
	}

	/** full constructor */
	public Question(Integer sessionId, Timestamp createDate, String rightAnswer, Integer orderNumber, String mt) {
		this.sessionId = sessionId;
		this.createDate = createDate;
		this.rightAnswer = rightAnswer;
		this.orderNumber = orderNumber;
		this.mt = mt;
	}

	// Property accessors

	public Integer getQuestionId() {
		return this.questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public Integer getSessionId() {
		return this.sessionId;
	}

	public void setSessionId(Integer sessionId) {
		this.sessionId = sessionId;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getRightAnswer() {
		return this.rightAnswer;
	}

	public void setRightAnswer(String rightAnswer) {
		this.rightAnswer = rightAnswer;
	}	

	public Integer getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getMt() {
		return this.mt;
	}

	public void setMt(String mt) {
		this.mt = mt;
	}

	public List<Question> getBySession(Integer SessionID) throws Exception 
	{
		List<?> mList = null;
		try
		{
			String strSQL = "FROM Question WHERE SessionID = '" +SessionID.toString() +"' "
					+ " ORDER BY OrderNumber ASC ";
			
			mList = (List<?>) Get(strSQL);
			if (mList.size() > 0)
			{
				return (List<Question>) mList;
			}
			return null;
		}
		catch (Exception ex)
		{
			throw ex;
		}
		
	}

}
