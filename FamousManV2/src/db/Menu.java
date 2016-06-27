package db;

/**
 * Menu entity. @author MyEclipse Persistence Tools
 */

public class Menu extends DAOBase implements java.io.Serializable {

	// Fields

	private Integer menuId;
	private String menuName;
	private String imagePath;
	private Integer pageId;
	private String link;
	private Integer level;
	private Integer parentId;
	private Integer priority;
	private Short isActive;

	// Constructors

	/** default constructor */
	public Menu() {
	}

	/** minimal constructor */
	public Menu(Integer level, Integer priority, Short isActive) {
		this.level = level;
		this.priority = priority;
		this.isActive = isActive;
	}

	/** full constructor */
	public Menu(String menuName, String imagePath, Integer pageId, String link,
			Integer level, Integer parentId, Integer priority, Short isActive) {
		this.menuName = menuName;
		this.imagePath = imagePath;
		this.pageId = pageId;
		this.link = link;
		this.level = level;
		this.parentId = parentId;
		this.priority = priority;
		this.isActive = isActive;
	}

	// Property accessors

	public Integer getMenuId() {
		return this.menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getImagePath() {
		return this.imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Integer getPageId() {
		return this.pageId;
	}

	public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
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