package my.base.cms.control.input;

public class inputCheckbox extends inputBase
{

	String checked="";

	public String getChecked()
	{
		return checked;
	}


	public void setChecked(String checked)
	{
		this.checked = checked;
	}


	public inputCheckbox()
	{
		super();
	}
	
	
	public inputCheckbox(String id, String text, String name, String value)
	{
		super();
		this.id = id;		
		this.text = text;
		this.name= name;
		this.type="checkbox";
		this.value = value;
	}
	
	public String buildHTML()
	{
		if(!this.visible)
			return "";
		
		String html ="<div class=\"checkbox mar-right-5 "+classCSS+"\">"+
				        "<span class=\"border mar-right-3\">"+
				            "<input type=\"checkbox\" id=\""+id+"\" name=\""+name+"\" "+otherAttribute+" value=\""+value+"\" "+checked+" />"+
				            "<label class=\"mark\" for=\""+id+"\"></label>"+
				        "</span>"+
				        "<label class=\"text inline\" for=\""+id+"\">"+text+"</label>"+
				    "</div>";
		return html;
	}
}
