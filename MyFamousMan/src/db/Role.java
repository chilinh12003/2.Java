package db;

/**
 * Role entity. @author MyEclipse Persistence Tools
 */

public class Role extends DAOBase implements java.io.Serializable
{

	// Fields

	private Integer roleId;
	private Integer groupId;
	private Integer pageId;
	private Integer controlId;
	private Integer statusId;

	// Constructors

	/** default constructor */
	public Role()
	{
	}

	/** full constructor */
	public Role(Integer groupId, Integer pageId, Integer controlId, Integer statusId)
	{
		this.groupId = groupId;
		this.pageId = pageId;
		this.controlId = controlId;
		this.statusId = statusId;
	}

	// Property accessors

	public Integer getRoleId()
	{
		return this.roleId;
	}

	public void setRoleId(Integer roleId)
	{
		this.roleId = roleId;
	}

	public Integer getGroupId()
	{
		return this.groupId;
	}

	public void setGroupId(Integer groupId)
	{
		this.groupId = groupId;
	}

	public Integer getPageId()
	{
		return this.pageId;
	}

	public void setPageId(Integer pageId)
	{
		this.pageId = pageId;
	}

	public Integer getControlId()
	{
		return this.controlId;
	}

	public void setControlId(Integer controlId)
	{
		this.controlId = controlId;
	}

	public Integer getStatusId()
	{
		return this.statusId;
	}

	public void setStatusId(Integer statusId)
	{
		this.statusId = statusId;
	}

}