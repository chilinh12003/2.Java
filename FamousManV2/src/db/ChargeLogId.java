package db;

/**
 * ChargeLogId entity. @author MyEclipse Persistence Tools
 */

public class ChargeLogId extends DAOBase implements java.io.Serializable {

	// Fields

	private Integer logId;
	private String phoneNumber;
	private Short pid;
	private Integer serviceId;

	// Constructors

	/** default constructor */
	public ChargeLogId() {
	}

	/** full constructor */
	public ChargeLogId(Integer logId, String phoneNumber, Short pid,
			Integer serviceId) {
		this.logId = logId;
		this.phoneNumber = phoneNumber;
		this.pid = pid;
		this.serviceId = serviceId;
	}

	// Property accessors

	public Integer getLogId() {
		return this.logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Short getPid() {
		return this.pid;
	}

	public void setPid(Short pid) {
		this.pid = pid;
	}

	public Integer getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ChargeLogId))
			return false;
		ChargeLogId castOther = (ChargeLogId) other;

		return ((this.getLogId() == castOther.getLogId()) || (this.getLogId() != null
				&& castOther.getLogId() != null && this.getLogId().equals(
				castOther.getLogId())))
				&& ((this.getPhoneNumber() == castOther.getPhoneNumber()) || (this
						.getPhoneNumber() != null
						&& castOther.getPhoneNumber() != null && this
						.getPhoneNumber().equals(castOther.getPhoneNumber())))
				&& ((this.getPid() == castOther.getPid()) || (this.getPid() != null
						&& castOther.getPid() != null && this.getPid().equals(
						castOther.getPid())))
				&& ((this.getServiceId() == castOther.getServiceId()) || (this
						.getServiceId() != null
						&& castOther.getServiceId() != null && this
						.getServiceId().equals(castOther.getServiceId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getLogId() == null ? 0 : this.getLogId().hashCode());
		result = 37
				* result
				+ (getPhoneNumber() == null ? 0 : this.getPhoneNumber()
						.hashCode());
		result = 37 * result
				+ (getPid() == null ? 0 : this.getPid().hashCode());
		result = 37 * result
				+ (getServiceId() == null ? 0 : this.getServiceId().hashCode());
		return result;
	}

}