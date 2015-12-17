package my.base.cms.control.button;

public class buttonActive extends buttonBase
{

	public buttonActive()
	{
		super();
		this.id = "btnActive";
		this.text = "Kích Hoạt";
		this.onClick = "";
		this.classCSS = "green-button";
	}
	
	public buttonActive(String onClick)
	{
		this();
		this.onClick = onClick;
	}
	public buttonActive(String id, String text, String onClick, String classCSS)
	{
		super();
		this.id = id;
		this.text = text;
		this.onClick = onClick;
		this.classCSS = classCSS;
	}
}
