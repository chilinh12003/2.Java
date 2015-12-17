package db;

import java.sql.Timestamp;
import java.util.Calendar;

import uti.MyDate;

/**
 * PlayLog entity. @author MyEclipse Persistence Tools
 */

public class PlayLog extends DAOBase implements java.io.Serializable
{

	// Fields

	private PlayLogId id;
	private Integer sessionId;
	private Integer questionId;
	private Integer sendCount;
	private Integer answerCount;
	private Integer answerRight;
	private Integer answerRightBuyType;
	private Timestamp playDate;
	private Short playTypeId;
	private Timestamp receiveDate;
	private String userAnswer;
	private Short statusId;
	private Integer weekMark;
	private Integer dayMark;
	private Integer addMark;
	private Integer chargeMark;
	private Integer buyMark;
	private Integer answerMark;
	private Integer promotionMark;
	private Timestamp insertDate;
	private Timestamp logDate;

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
		PlayLogId mID = new PlayLogId();
		mID.setPid(playObj.getPid());
		mID.setPhoneNumber(playObj.getId().getPhoneNumber());
		mID.setLogId(playObj.getId().getLogId());
		this.setId(mID);

		this.sessionId = playObj.getSessionId();
		this.questionId = playObj.getQuestionId();
		this.sendCount = playObj.getSendCount();
		this.answerCount = playObj.getAnswerCount();
		this.answerRight = playObj.getAnswerRight();
		this.answerRightBuyType = playObj.getAnswerRightBuyType();
		this.playDate = playObj.getPlayDate();
		this.playTypeId = playObj.getPlayTypeId();
		this.receiveDate = playObj.getReceiveDate();
		this.userAnswer = playObj.getUserAnswer();
		this.statusId = playObj.getStatusId();
		this.weekMark = playObj.getWeekMark();
		this.dayMark = playObj.getDayMark();
		this.addMark = playObj.getAddMark();
		this.chargeMark = playObj.getChargeMark();
		this.buyMark = playObj.getBuyMark();
		this.answerMark = playObj.getAnswerMark();
		this.promotionMark = playObj.getPromotionMark();
		this.insertDate = playObj.getInsertDate();
		this.logDate = MyDate.Date2Timestamp(Calendar.getInstance());
	}

	/** full constructor */
	public PlayLog(PlayLogId id,Integer sessionId, Integer questionId, Integer sendCount, Integer answerCount, Integer answerRight, Integer answerRightBuyType,
			Timestamp playDate, Short playTypeId, Timestamp receiveDate, String userAnswer, Short statusId,
			Integer weekMark, Integer dayMark, Integer addMark, Integer chargeMark, Integer buyMark,
			Integer answerMark, Integer promotionMark, Timestamp insertDate, Timestamp logDate)
	{
		this.id = id;
		this.sessionId = sessionId;
		this.questionId = questionId;
		this.sendCount = sendCount;
		this.answerCount = answerCount;
		this.answerRight = answerRight;
		this. answerRightBuyType = answerRightBuyType;
		this.playDate = playDate;
		this.playTypeId = playTypeId;
		this.receiveDate = receiveDate;
		this.userAnswer = userAnswer;
		this.statusId = statusId;
		this.weekMark = weekMark;
		this.dayMark = dayMark;
		this.addMark = addMark;
		this.chargeMark = chargeMark;
		this.buyMark = buyMark;
		this.answerMark = answerMark;
		this.promotionMark = promotionMark;
		this.insertDate = insertDate;
		this.logDate = logDate;
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

	public Integer getSessionId()
	{
		return sessionId;
	}

	public void setSessionId(Integer sessionId)
	{
		this.sessionId = sessionId;
	}
	public Integer getQuestionId()
	{
		return this.questionId;
	}

	public void setQuestionId(Integer questionId)
	{
		this.questionId = questionId;
	}

	public Integer getSendCount()
	{
		return this.sendCount;
	}

	public void setSendCount(Integer sendCount)
	{
		this.sendCount = sendCount;
	}

	public Integer getAnswerCount()
	{
		return this.answerCount;
	}

	public void setAnswerCount(Integer answerCount)
	{
		this.answerCount = answerCount;
	}

	public Integer getAnswerRight()
	{
		return this.answerRight;
	}

	public void setAnswerRight(Integer answerRight)
	{
		this.answerRight = answerRight;
	}
	
	public Integer getAnswerRightBuyType()
	{
		return answerRightBuyType;
	}

	public void setAnswerRightBuyType(Integer answerRightBuyType)
	{
		this.answerRightBuyType = answerRightBuyType;
	}
	

	public Timestamp getPlayDate()
	{
		return this.playDate;
	}

	public void setPlayDate(Timestamp playDate)
	{
		this.playDate = playDate;
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

	public Integer getPromotionMark()
	{
		return this.promotionMark;
	}

	public void setPromotionMark(Integer promotionMark)
	{
		this.promotionMark = promotionMark;
	}

	public Timestamp getInsertDate()
	{
		return this.insertDate;
	}

	public void setInsertDate(Timestamp insertDate)
	{
		this.insertDate = insertDate;
	}

	public Timestamp getLogDate()
	{
		return this.logDate;
	}

	public void setLogDate(Timestamp logDate)
	{
		this.logDate = logDate;
	}

}