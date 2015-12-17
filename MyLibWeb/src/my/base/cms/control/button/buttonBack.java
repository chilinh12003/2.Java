package my.base.cms.control.button;

public class buttonBack extends buttonBase
{

	public buttonBack()
	{
		super();
		this.id = "btnBack";
		this.text = "Quay lại";
		this.onClick = "";
		this.classCSS = "turquoise-button";
	}
	
	public buttonBack(String onClick)
	{
		this();
		this.onClick = onClick;
	}
	
	public buttonBack(String id, String text, String onClick, String classCSS)
	{
		super();
		this.id = id;
		this.text = text;
		this.onClick = onClick;
		this.classCSS = classCSS;
	}
}
