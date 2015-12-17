package db;

import java.sql.Timestamp;

/**
 * PlayLog entity. @author MyEclipse Persistence Tools
 */

public class PlayLog extends DAOBase implements java.io.Serializable
{

	// Fields

	private PlayLogId id;
	private Integer questionId;
	private Timestamp playDate;
	private Integer suggestId;
	private Short playTypeId;
	private Timestamp receiveDate;
	private Integer orderNumber;
	private String userAnswer;
	private Short statusId;
	private Integer weekMark;
	private Integer dayMark;
	private Integer addMark;
	private Integer chargeMark;
	private Integer buyMark;
	private Integer answerMark;
	private Timestamp insertDate;

	// Constructors

	/** default constructor */
	public PlayLog()
	{
	}

	/** minimal constructor */
	public PlayLog(PlayLogId id)
	{
		this.id = id;
	}
	
	public PlayLog(Play playObj)
	{
		PlayLogId mID =new  PlayLogId();
		mID.setPid(playObj.getPid());
		mID.setPhoneNumber(playObj.getId().getPhoneNumber());
		mID.setLogId(playObj.getId().getLogId());
		this.setId(mID);
		
		this.questionId = playObj.getQuestionId();
		this.playDate = playObj.getPlayDate();
		this.suggestId = playObj.getSuggestId();
		this.playTypeId = playObj.getPlayTypeId();
		this.receiveDate = playObj.getReceiveDate();
		this.orderNumber = playObj.getOrderNumber();
		this.userAnswer = playObj.getUserAnswer();
		this.statusId = playObj.getStatusId();
		this.weekMark = playObj.getWeekMark();
		this.dayMark = playObj.getDayMark();
		this.addMark = playObj.getAddMark();
		this.chargeMark = playObj.getChargeMark();
		this.buyMark = playObj.getBuyMark();
		this.answerMark = playObj.getAnswerMark();
		this.insertDate = playObj.getInsertDate();
	}

	/** full constructor */
	public PlayLog(PlayLogId id, Integer questionId, Timestamp playDate, Integer suggestId, Short playTypeId,
			Timestamp receiveDate, Integer orderNumber, String userAnswer, Short statusId, Integer weekMark,
			Integer dayMark, Integer addMark, Integer chargeMark, Integer buyMark, Integer answerMark,
			Timestamp insertDate)
	{
		this.id = id;
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

	public PlayLogId getId()
	{
		return this.id;
	}

	public void setId(PlayLogId id)
	{
		this.id = id;
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

	public Timestamp getInsertDate()
	{
		return this.insertDate;
	}

	public void setInsertDate(Timestamp insertDate)
	{
		this.insertDate = insertDate;
	}

}