package my.base.cms.control.button;

public class buttonSearch extends buttonBase
{

	public buttonSearch()
	{
		super();
		this.id = "btnSearch";
		this.text = "Tìm kiếm";
		this.onClick = "";
		this.classCSS = "turquoise-button";
	}
	public buttonSearch(String onClick)
	{
		this();
		this.onClick = onClick;
	}
	public buttonSearch(String id, String text, String onClick, String classCSS)
	{
		super();
		this.id = id;
		this.text = text;
		this.onClick = onClick;
		this.classCSS = classCSS;
	}
}
