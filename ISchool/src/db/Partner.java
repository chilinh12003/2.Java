package db;

/**
 * Partner entity. @author MyEclipse Persistence Tools
 */

public class Partner extends DAOBase implements java.io.Serializable {

	// Fields

	private Integer partnerId;
	private String validString;
	private String partnerName;
	private String phone;
	private String email;
	private String address;
	private Short isActive;
	private String website;
	private Short partnerTypeId;
	private Integer parentId;

	// Constructors

	/** default constructor */
	public Partner() {
	}

	/** minimal constructor */
	public Partner(Short isActive) {
		this.isActive = isActive;
	}

	/** full constructor */
	public Partner(String validString, String partnerName, String phone,
			String email, String address, Short isActive, String website,
			Short partnerTypeId, Integer parentId) {
		this.validString = validString;
		this.partnerName = partnerName;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.isActive = isActive;
		this.website = website;
		this.partnerTypeId = partnerTypeId;
		this.parentId = parentId;
	}

	// Property accessors

	public Integer getPartnerId() {
		return this.partnerId;
	}

	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}

	public String getValidString() {
		return this.validString;
	}

	public void setValidString(String validString) {
		this.validString = validString;
	}

	public String getPartnerName() {
		return this.partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Short getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Short isActive) {
		this.isActive = isActive;
	}

	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Short getPartnerTypeId() {
		return this.partnerTypeId;
	}

	public void setPartnerTypeId(Short partnerTypeId) {
		this.partnerTypeId = partnerTypeId;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

}