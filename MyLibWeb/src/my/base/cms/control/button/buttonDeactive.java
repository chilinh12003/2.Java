package my.base.cms.control.button;

public class buttonDeactive extends buttonBase
{

	public buttonDeactive()
	{
		super();
		this.id = "btnDeactive";
		this.text = "Hủy Kích Hoạt";
		this.onClick = "";
		this.classCSS = "bordeaux-button";
	}
	
	public buttonDeactive(String onClick)
	{
		this();
		this.onClick = onClick;
	}
	
	public buttonDeactive(String id, String text, String onClick, String classCSS)
	{
		super();
		this.id = id;
		this.text = text;
		this.onClick = onClick;
		this.classCSS = classCSS;
	}
}
