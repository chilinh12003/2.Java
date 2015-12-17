package db;

/**
 * PlayId entity. @author MyEclipse Persistence Tools
 */

public class PlayId extends DAOBase implements java.io.Serializable {

	// Fields

	private Integer logId;
	private String phoneNumber;

	// Constructors

	/** default constructor */
	public PlayId() {
	}

	/** full constructor */
	public PlayId(Integer logId, String phoneNumber) {
		this.logId = logId;
		this.phoneNumber = phoneNumber;
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

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PlayId))
			return false;
		PlayId castOther = (PlayId) other;

		return ((this.getLogId() == castOther.getLogId()) || (this.getLogId() != null
				&& castOther.getLogId() != null && this.getLogId().equals(
				castOther.getLogId())))
				&& ((this.getPhoneNumber() == castOther.getPhoneNumber()) || (this
						.getPhoneNumber() != null
						&& castOther.getPhoneNumber() != null && this
						.getPhoneNumber().equals(castOther.getPhoneNumber())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getLogId() == null ? 0 : this.getLogId().hashCode());
		result = 37
				* result
				+ (getPhoneNumber() == null ? 0 : this.getPhoneNumber()
						.hashCode());
		return result;
	}

}