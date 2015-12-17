package db;

/**
 * SubLogId entity. @author MyEclipse Persistence Tools
 */

public class SubLogId extends DAOBase implements java.io.Serializable
{

	// Fields

	private Integer logId;
	private String phoneNumber;
	private Short pid;

	// Constructors

	/** default constructor */
	public SubLogId()
	{
	}

	/** full constructor */
	public SubLogId(Integer logId, String phoneNumber, Short pid)
	{
		this.logId = logId;
		this.phoneNumber = phoneNumber;
		this.pid = pid;
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

	public String getPhoneNumber()
	{
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public Short getPid()
	{
		return this.pid;
	}

	public void setPid(Short pid)
	{
		this.pid = pid;
	}

	public boolean equals(Object other)
	{
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SubLogId))
			return false;
		SubLogId castOther = (SubLogId) other;

		return ((this.getLogId() == castOther.getLogId()) || (this.getLogId() != null && castOther.getLogId() != null && this
				.getLogId().equals(castOther.getLogId())))
				&& ((this.getPhoneNumber() == castOther.getPhoneNumber()) || (this.getPhoneNumber() != null
						&& castOther.getPhoneNumber() != null && this.getPhoneNumber().equals(
						castOther.getPhoneNumber())))
				&& ((this.getPid() == castOther.getPid()) || (this.getPid() != null && castOther.getPid() != null && this
						.getPid().equals(castOther.getPid())));
	}

	public int hashCode()
	{
		int result = 17;

		result = 37 * result + (getLogId() == null ? 0 : this.getLogId().hashCode());
		result = 37 * result + (getPhoneNumber() == null ? 0 : this.getPhoneNumber().hashCode());
		result = 37 * result + (getPid() == null ? 0 : this.getPid().hashCode());
		return result;
	}

}