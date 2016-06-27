package db;

import java.sql.Timestamp;

/**
 * RpSubId entity. @author MyEclipse Persistence Tools
 */

public class RpSubId extends DAOBase implements java.io.Serializable {

	// Fields

	private Timestamp reportDay;
	private Integer serviceId;

	// Constructors

	/** default constructor */
	public RpSubId() {
	}

	/** full constructor */
	public RpSubId(Timestamp reportDay, Integer serviceId) {
		this.reportDay = reportDay;
		this.serviceId = serviceId;
	}

	// Property accessors

	public Timestamp getReportDay() {
		return this.reportDay;
	}

	public void setReportDay(Timestamp reportDay) {
		this.reportDay = reportDay;
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
		if (!(other instanceof RpSubId))
			return false;
		RpSubId castOther = (RpSubId) other;

		return ((this.getReportDay() == castOther.getReportDay()) || (this
				.getReportDay() != null && castOther.getReportDay() != null && this
				.getReportDay().equals(castOther.getReportDay())))
				&& ((this.getServiceId() == castOther.getServiceId()) || (this
						.getServiceId() != null
						&& castOther.getServiceId() != null && this
						.getServiceId().equals(castOther.getServiceId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getReportDay() == null ? 0 : this.getReportDay().hashCode());
		result = 37 * result
				+ (getServiceId() == null ? 0 : this.getServiceId().hashCode());
		return result;
	}

}