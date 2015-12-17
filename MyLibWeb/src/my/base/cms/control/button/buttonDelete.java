package my.base.cms.control.button;

public class buttonDelete extends buttonBase
{

	public buttonDelete()
	{
		super();
		this.id = "btnDelete";
		this.text = "Xóa";
		this.onClick = "";
		this.classCSS = "red-button";
	}
	
	public buttonDelete(String onClick)
	{
		this();
		this.onClick = onClick;
	}
	
	public buttonDelete(String id, String text, String onClick, String classCSS)
	{
		super();
		this.id = id;
		this.text = text;
		this.onClick = onClick;
		this.classCSS = classCSS;
	}
}
