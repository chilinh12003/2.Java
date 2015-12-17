package db;

/**
 * MemberGroup entity. @author MyEclipse Persistence Tools
 */

public class MemberGroup extends DAOBase implements java.io.Serializable {

	// Fields

	private Integer groupId;
	private String groupName;
	private Integer partnerId;
	private String partnerName;
	private Integer parentId;
	private Integer level;
	private Short isActive;

	// Constructors

	/** default constructor */
	public MemberGroup() {
	}

	/** minimal constructor */
	public MemberGroup(Integer level, Short isActive) {
		this.level = level;
		this.isActive = isActive;
	}

	/** full constructor */
	public MemberGroup(String groupName, Integer partnerId, String partnerName,
			Integer parentId, Integer level, Short isActive) {
		this.groupName = groupName;
		this.partnerId = partnerId;
		this.partnerName = partnerName;
		this.parentId = parentId;
		this.level = level;
		this.isActive = isActive;
	}

	// Property accessors

	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getPartnerId() {
		return this.partnerId;
	}

	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}

	public String getPartnerName() {
		return this.partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Short getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Short isActive) {
		this.isActive = isActive;
	}

}