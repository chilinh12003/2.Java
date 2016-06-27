package db;

import java.sql.Timestamp;

/**
 * Member entity. @author MyEclipse Persistence Tools
 */

public class Member extends DAOBase implements java.io.Serializable {

	// Fields

	private Integer memberId;
	private Integer groupId;
	private String memberName;
	private String loginName;
	private String password;
	private String phone;
	private Boolean sex;
	private Timestamp birthday;
	private String email;
	private Timestamp createDate;
	private Short isActive;

	// Constructors

	/** default constructor */
	public Member() {
	}

	/** minimal constructor */
	public Member(Integer groupId, String loginName, String password,
			Timestamp createDate, Short isActive) {
		this.groupId = groupId;
		this.loginName = loginName;
		this.password = password;
		this.createDate = createDate;
		this.isActive = isActive;
	}

	/** full constructor */
	public Member(Integer groupId, String memberName, String loginName,
			String password, String phone, Boolean sex, Timestamp birthday,
			String email, Timestamp createDate, Short isActive) {
		this.groupId = groupId;
		this.memberName = memberName;
		this.loginName = loginName;
		this.password = password;
		this.phone = phone;
		this.sex = sex;
		this.birthday = birthday;
		this.email = email;
		this.createDate = createDate;
		this.isActive = isActive;
	}

	// Property accessors

	public Integer getMemberId() {
		return this.memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getMemberName() {
		return this.memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Boolean getSex() {
		return this.sex;
	}

	public void setSex(Boolean sex) {
		this.sex = sex;
	}

	public Timestamp getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Short getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Short isActive) {
		this.isActive = isActive;
	}

}