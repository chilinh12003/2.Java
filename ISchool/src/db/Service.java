package db;

/**
 * Service entity. @author MyEclipse Persistence Tools
 */

public class Service extends DAOBase implements java.io.Serializable
{

	// Fields

	private Integer serviceId;
	private String serviceName;
	private String regKeyword;
	private String deregKeyword;

	// Constructors

	/** default constructor */
	public Service()
	{
	}

	/** full constructor */
	public Service(String serviceName, String regKeyword, String deregKeyword)
	{
		this.serviceName = serviceName;
		this.regKeyword = regKeyword;
		this.deregKeyword = deregKeyword;
	}

	// Property accessors

	public Integer getServiceId()
	{
		return this.serviceId;
	}

	public void setServiceId(Integer serviceId)
	{
		this.serviceId = serviceId;
	}

	public String getServiceName()
	{
		return this.serviceName;
	}

	public void setServiceName(String serviceName)
	{
		this.serviceName = serviceName;
	}

	public String getRegKeyword()
	{
		return this.regKeyword;
	}

	public void setRegKeyword(String regKeyword)
	{
		this.regKeyword = regKeyword;
	}

	public String getDeregKeyword()
	{
		return this.deregKeyword;
	}

	public void setDeregKeyword(String deregKeyword)
	{
		this.deregKeyword = deregKeyword;
	}

}