package db;

/**
 * MtlogId entity. @author MyEclipse Persistence Tools
 */

public class MtlogId extends DAOBase implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer logId;
	private Short pid;
	private String phoneNumber;

	// Constructors

	/** default constructor */
	public MtlogId() {
	}

	/** full constructor */
	public MtlogId(Integer logId, Short pid, String phoneNumber) {
		this.logId = logId;
		this.pid = pid;
		this.phoneNumber = phoneNumber;
	}

	// Property accessors

	public Integer getLogId() {
		return this.logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public Short getPid() {
		return this.pid;
	}

	public void setPid(Short pid) {
		this.pid = pid;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof MtlogId))
			return false;
		MtlogId castOther = (MtlogId) other;

		return ((this.getLogId() == castOther.getLogId()) || (this.getLogId() != null
				&& castOther.getLogId() != null && this.getLogId().equals(
				castOther.getLogId())))
				&& ((this.getPid() == castOther.getPid()) || (this.getPid() != null
						&& castOther.getPid() != null && this.getPid().equals(
						castOther.getPid())))
				&& ((this.getPhoneNumber() == castOther.getPhoneNumber()) || (this
						.getPhoneNumber() != null
						&& castOther.getPhoneNumber() != null && this
						.getPhoneNumber().equals(castOther.getPhoneNumber())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getLogId() == null ? 0 : this.getLogId().hashCode());
		result = 37 * result
				+ (getPid() == null ? 0 : this.getPid().hashCode());
		result = 37
				* result
				+ (getPhoneNumber() == null ? 0 : this.getPhoneNumber()
						.hashCode());
		return result;
	}

}