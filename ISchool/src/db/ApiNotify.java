package db;

import java.sql.Timestamp;

/**
 * ApiNotify entity. @author MyEclipse Persistence Tools
 */

public class ApiNotify extends DAOBase implements java.io.Serializable
{

	// Fields

	private ApiNotifyId id;
	private String cpRequestId;
	private Float price;
	private String responseCode;
	private String cmd;
	private Timestamp logDate;
	private Integer partnerId;

	// Constructors

	/** default constructor */
	public ApiNotify()
	{
	}

	/** minimal constructor */
	public ApiNotify(ApiNotifyId id)
	{
		this.id = id;
	}

	/** full constructor */
	public ApiNotify(ApiNotifyId id, String cpRequestId, Float price, String responseCode, String cmd,
			Timestamp logDate, Integer partnerId)
	{
		this.id = id;
		this.cpRequestId = cpRequestId;
		this.price = price;
		this.responseCode = responseCode;
		this.cmd = cmd;
		this.logDate = logDate;
		this.partnerId = partnerId;
	}

	// Property accessors

	public ApiNotifyId getId()
	{
		return this.id;
	}

	public void setId(ApiNotifyId id)
	{
		this.id = id;
	}

	public String getCpRequestId()
	{
		return this.cpRequestId;
	}

	public void setCpRequestId(String cpRequestId)
	{
		this.cpRequestId = cpRequestId;
	}

	public Float getPrice()
	{
		return this.price;
	}

	public void setPrice(Float price)
	{
		this.price = price;
	}

	public String getResponseCode()
	{
		return this.responseCode;
	}

	public void setResponseCode(String responseCode)
	{
		this.responseCode = responseCode;
	}

	public String getCmd()
	{
		return this.cmd;
	}

	public void setCmd(String cmd)
	{
		this.cmd = cmd;
	}

	public Timestamp getLogDate()
	{
		return this.logDate;
	}

	public void setLogDate(Timestamp logDate)
	{
		this.logDate = logDate;
	}

	public Integer getPartnerId()
	{
		return this.partnerId;
	}

	public void setPartnerId(Integer partnerId)
	{
		this.partnerId = partnerId;
	}

}