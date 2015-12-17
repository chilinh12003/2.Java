package db;

import java.sql.Timestamp;

/**
 * MemberLog entity. @author MyEclipse Persistence Tools
 */

public class MemberLog extends DAOBase implements java.io.Serializable
{

	// Fields

	private Integer logId;
	private Integer memberId;
	private String memberName;
	private String tableName;
	private Integer actionId;
	private String actionName;
	private String oldData;
	private String newData;
	private Boolean isSuccess;
	private String requestIp;
	private Timestamp createDate;

	// Constructors

	/** default constructor */
	public MemberLog()
	{
	}

	/** minimal constructor */
	public MemberLog(Boolean isSuccess, Timestamp createDate)
	{
		this.isSuccess = isSuccess;
		this.createDate = createDate;
	}

	/** full constructor */
	public MemberLog(Integer memberId, String memberName, String tableName, Integer actionId, String actionName,
			String oldData, String newData, Boolean isSuccess, String requestIp, Timestamp createDate)
	{
		this.memberId = memberId;
		this.memberName = memberName;
		this.tableName = tableName;
		this.actionId = actionId;
		this.actionName = actionName;
		this.oldData = oldData;
		this.newData = newData;
		this.isSuccess = isSuccess;
		this.requestIp = requestIp;
		this.createDate = createDate;
	}

	// Property accessors

	public Integer getLogId()
	{
		return this.logId;
	}

	public void setLogId(Integer logId)
	{
		this.logId = logId;
	}

	public Integer getMemberId()
	{
		return this.memberId;
	}

	public void setMemberId(Integer memberId)
	{
		this.memberId = memberId;
	}

	public String getMemberName()
	{
		return this.memberName;
	}

	public void setMemberName(String memberName)
	{
		this.memberName = memberName;
	}

	public String getTableName()
	{
		return this.tableName;
	}

	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}

	public Integer getActionId()
	{
		return this.actionId;
	}

	public void setActionId(Integer actionId)
	{
		this.actionId = actionId;
	}

	public String getActionName()
	{
		return this.actionName;
	}

	public void setActionName(String actionName)
	{
		this.actionName = actionName;
	}

	public String getOldData()
	{
		return this.oldData;
	}

	public void setOldData(String oldData)
	{
		this.oldData = oldData;
	}

	public String getNewData()
	{
		return this.newData;
	}

	public void setNewData(String newData)
	{
		this.newData = newData;
	}

	public Boolean getIsSuccess()
	{
		return this.isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess)
	{
		this.isSuccess = isSuccess;
	}

	public String getRequestIp()
	{
		return this.requestIp;
	}

	public void setRequestIp(String requestIp)
	{
		this.requestIp = requestIp;
	}

	public Timestamp getCreateDate()
	{
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate)
	{
		this.createDate = createDate;
	}

}