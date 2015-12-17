package db;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Suggest entity. @author MyEclipse Persistence Tools
 */

public class Suggest extends DAOBase implements java.io.Serializable
{

	// Fields

	private Integer suggestId;
	private Integer questionId;
	private String mt;
	private Integer orderNumber;
	private String notifyMt;
	private Short isActive;
	private Timestamp createDate;

	// Constructors

	/** default constructor */
	public Suggest()
	{
	}

	/** full constructor */
	public Suggest(Integer questionId, String mt, Integer orderNumber, String notifyMt, Short isActive,
			Timestamp createDate)
	{
		this.questionId = questionId;
		this.mt = mt;
		this.orderNumber = orderNumber;
		this.notifyMt = notifyMt;
		this.isActive = isActive;
		this.createDate = createDate;
	}

	// Property accessors

	public Integer getSuggestId()
	{
		return this.suggestId;
	}

	public void setSuggestId(Integer suggestId)
	{
		this.suggestId = suggestId;
	}

	public Integer getQuestionId()
	{
		return this.questionId;
	}

	public void setQuestionId(Integer questionId)
	{
		this.questionId = questionId;
	}

	public String getMt()
	{
		return this.mt;
	}

	public void setMt(String mt)
	{
		this.mt = mt;
	}

	public Integer getOrderNumber()
	{
		return this.orderNumber;
	}

	public void setOrderNumber(Integer orderNumber)
	{
		this.orderNumber = orderNumber;
	}

	public String getNotifyMt()
	{
		return this.notifyMt;
	}

	public void setNotifyMt(String notifyMt)
	{
		this.notifyMt = notifyMt;
	}

	public Short getIsActive()
	{
		return this.isActive;
	}

	public void setIsActive(Short isActive)
	{
		this.isActive = isActive;
	}

	public Timestamp getCreateDate()
	{
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate)
	{
		this.createDate = createDate;
	}
	
	@SuppressWarnings("unchecked")
	public List<Suggest> getByQuestion(Integer QuestionID) throws Exception 
	{
		List<?> mList = null;
		try
		{
			String strSQL = "FROM Suggest WHERE QuestionID = '" +QuestionID.toString() +"' "
					+ " ORDER BY OrderNumber ASC ";
			
			mList = (List<?>) Get(strSQL);
			if (mList.size() > 0)
			{
				return (List<Suggest>) mList;
			}
			return null;
		}
		catch (Exception ex)
		{
			throw ex;
		}
		
	}
}