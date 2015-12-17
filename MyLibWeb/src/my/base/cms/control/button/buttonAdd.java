package my.base.cms.control.button;

public class buttonAdd extends buttonBase
{

	public buttonAdd()
	{
		super();
		this.id = "btnAdd";
		this.text = "Thêm";
		this.onClick = "";
		this.classCSS = "blue-button";
	}
	public buttonAdd(String onClick)
	{
		this();
		this.onClick = onClick;
	}
	
	public buttonAdd(String id, String text, String onClick, String classCSS)
	{
		super();
		this.id = id;
		this.text = text;
		this.onClick = onClick;
		this.classCSS = classCSS;
	}
}
