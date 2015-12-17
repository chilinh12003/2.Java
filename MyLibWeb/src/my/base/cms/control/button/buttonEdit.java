package my.base.cms.control.button;

public class buttonEdit extends buttonBase
{

	public buttonEdit()
	{
		super();
		this.id = "btnEdit";
		this.text = "Sửa";
		this.onClick = "";
		this.classCSS = "purple-button";
	}
	
	public buttonEdit(String onClick)
	{
		this();
		this.onClick = onClick;
	}
	
	public buttonEdit(String id, String text, String onClick, String classCSS)
	{
		super();
		this.id = id;
		this.text = text;
		this.onClick = onClick;
		this.classCSS = classCSS;
	}
}
