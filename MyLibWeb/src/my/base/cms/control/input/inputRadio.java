package my.base.cms.control.input;

public class inputRadio extends inputBase
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


	public inputRadio()
	{
		super();
	}
	
	
	public inputRadio(String id, String text, String name, String value)
	{
		super();
		this.id = id;		
		this.text = text;
		this.name= name;
		this.type="radio";
		this.value = value;
	}
	
	public String buildHTML()
	{
		if(!this.visible)
			return "";
		
		String html ="<div class=\"raido mar-right-5 "+classCSS+"\">"+
				        "<span class=\"border mar-right-3\">"+
				            "<input type=\"radio\" id=\""+id+"\" name=\""+name+"\" "+otherAttribute+" value=\""+value+"\" "+checked+" />"+
				            "<label class=\"mark\" for=\""+id+"\"></label>"+
				        "</span>"+
				        "<label class=\"text inline\" for=\""+id+"\">"+text+"</label>"+
				    "</div>";
		return html;
	}
}
