package db;

/**
 * Category entity. @author MyEclipse Persistence Tools
 */

public class Category extends DAOBase implements java.io.Serializable {

	// Fields

	private Integer cateId;
	private String cateName;
	private String prefixCode;
	private String description;
	private Integer parentId;
	private Integer level;
	private Integer priority;
	private Short isActive;

	// Constructors

	/** default constructor */
	public Category() {
	}

	/** minimal constructor */
	public Category(Integer priority, Short isActive) {
		this.priority = priority;
		this.isActive = isActive;
	}

	/** full constructor */
	public Category(String cateName, String prefixCode, String description,
			Integer parentId, Integer level, Integer priority, Short isActive) {
		this.cateName = cateName;
		this.prefixCode = prefixCode;
		this.description = description;
		this.parentId = parentId;
		this.level = level;
		this.priority = priority;
		this.isActive = isActive;
	}

	// Property accessors

	public Integer getCateId() {
		return this.cateId;
	}

	public void setCateId(Integer cateId) {
		this.cateId = cateId;
	}

	public String getCateName() {
		return this.cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public String getPrefixCode() {
		return this.prefixCode;
	}

	public void setPrefixCode(String prefixCode) {
		this.prefixCode = prefixCode;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Short getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Short isActive) {
		this.isActive = isActive;
	}

}