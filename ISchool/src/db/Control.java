package db;

/**
 * Control entity. @author MyEclipse Persistence Tools
 */

public class Control extends DAOBase implements java.io.Serializable {

	// Fields

	private Integer controlId;
	private Integer pageId;
	private Integer controlTypeId;
	private String uniqueName;
	private String nameSpace;
	private String controlName;
	private Integer parentId;
	private Integer level;
	private Short isActive;

	// Constructors

	/** default constructor */
	public Control() {
	}

	/** minimal constructor */
	public Control(Integer pageId, Integer controlTypeId, Integer parentId,
			Integer level, Short isActive) {
		this.pageId = pageId;
		this.controlTypeId = controlTypeId;
		this.parentId = parentId;
		this.level = level;
		this.isActive = isActive;
	}

	/** full constructor */
	public Control(Integer pageId, Integer controlTypeId, String uniqueName,
			String nameSpace, String controlName, Integer parentId,
			Integer level, Short isActive) {
		this.pageId = pageId;
		this.controlTypeId = controlTypeId;
		this.uniqueName = uniqueName;
		this.nameSpace = nameSpace;
		this.controlName = controlName;
		this.parentId = parentId;
		this.level = level;
		this.isActive = isActive;
	}

	// Property accessors

	public Integer getControlId() {
		return this.controlId;
	}

	public void setControlId(Integer controlId) {
		this.controlId = controlId;
	}

	public Integer getPageId() {
		return this.pageId;
	}

	public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}

	public Integer getControlTypeId() {
		return this.controlTypeId;
	}

	public void setControlTypeId(Integer controlTypeId) {
		this.controlTypeId = controlTypeId;
	}

	public String getUniqueName() {
		return this.uniqueName;
	}

	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}

	public String getNameSpace() {
		return this.nameSpace;
	}

	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}

	public String getControlName() {
		return this.controlName;
	}

	public void setControlName(String controlName) {
		this.controlName = controlName;
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