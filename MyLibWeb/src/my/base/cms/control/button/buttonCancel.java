package my.base.cms.control.button;

public class buttonCancel extends buttonBase
{

	public buttonCancel()
	{
		super();
		this.id = "btnCancel";
		this.text = "Hủy";
		this.onClick = "";
		this.classCSS = "bordeaux-button";
	}
	public buttonCancel(String onClick)
	{
		this();
		this.onClick = onClick;
	}
	
	public buttonCancel(String id, String text, String onClick, String classCSS)
	{
		super();
		this.id = id;
		this.text = text;
		this.onClick = onClick;
		this.classCSS = classCSS;
	}
}
