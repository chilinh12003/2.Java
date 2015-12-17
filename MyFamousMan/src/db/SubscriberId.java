package db;

/**
 * SubscriberId entity. @author MyEclipse Persistence Tools
 */

public class SubscriberId extends DAOBase implements java.io.Serializable
{

	// Fields

	private String phoneNumber;
	private Short pid;

	// Constructors

	/** default constructor */
	public SubscriberId()
	{
	}

	/** full constructor */
	public SubscriberId(String phoneNumber, Short pid)
	{
		this.phoneNumber = phoneNumber;
		this.pid = pid;
	}

	// Property accessors

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
		if (!(other instanceof SubscriberId))
			return false;
		SubscriberId castOther = (SubscriberId) other;

		return ((this.getPhoneNumber() == castOther.getPhoneNumber()) || (this.getPhoneNumber() != null
				&& castOther.getPhoneNumber() != null && this.getPhoneNumber().equals(castOther.getPhoneNumber())))
				&& ((this.getPid() == castOther.getPid()) || (this.getPid() != null && castOther.getPid() != null && this
						.getPid().equals(castOther.getPid())));
	}

	public int hashCode()
	{
		int result = 17;

		result = 37 * result + (getPhoneNumber() == null ? 0 : this.getPhoneNumber().hashCode());
		result = 37 * result + (getPid() == null ? 0 : this.getPid().hashCode());
		return result;
	}

}