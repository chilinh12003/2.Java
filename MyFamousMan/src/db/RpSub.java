package db;

/**
 * RpSub entity. @author MyEclipse Persistence Tools
 */

public class RpSub extends DAOBase implements java.io.Serializable
{

	// Fields

	private RpSubId id;

	// Constructors

	/** default constructor */
	public RpSub()
	{
	}

	/** full constructor */
	public RpSub(RpSubId id)
	{
		this.id = id;
	}

	// Property accessors

	public RpSubId getId()
	{
		return this.id;
	}

	public void setId(RpSubId id)
	{
		this.id = id;
	}

}