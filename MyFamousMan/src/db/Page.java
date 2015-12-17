package db;

/**
 * Page entity. @author MyEclipse Persistence Tools
 */

public class Page extends DAOBase implements java.io.Serializable
{

	// Fields

	private Integer pageId;
	private String nameSpace;
	private String pageName;
	private Integer pageTypeId;
	private Short isActive;

	// Constructors

	/** default constructor */
	public Page()
	{
	}

	/** minimal constructor */
	public Page(Integer pageTypeId, Short isActive)
	{
		this.pageTypeId = pageTypeId;
		this.isActive = isActive;
	}

	/** full constructor */
	public Page(String nameSpace, String pageName, Integer pageTypeId, Short isActive)
	{
		this.nameSpace = nameSpace;
		this.pageName = pageName;
		this.pageTypeId = pageTypeId;
		this.isActive = isActive;
	}

	// Property accessors

	public Integer getPageId()
	{
		return this.pageId;
	}

	public void setPageId(Integer pageId)
	{
		this.pageId = pageId;
	}

	public String getNameSpace()
	{
		return this.nameSpace;
	}

	public void setNameSpace(String nameSpace)
	{
		this.nameSpace = nameSpace;
	}

	public String getPageName()
	{
		return this.pageName;
	}

	public void setPageName(String pageName)
	{
		this.pageName = pageName;
	}

	public Integer getPageTypeId()
	{
		return this.pageTypeId;
	}

	public void setPageTypeId(Integer pageTypeId)
	{
		this.pageTypeId = pageTypeId;
	}

	public Short getIsActive()
	{
		return this.isActive;
	}

	public void setIsActive(Short isActive)
	{
		this.isActive = isActive;
	}

}