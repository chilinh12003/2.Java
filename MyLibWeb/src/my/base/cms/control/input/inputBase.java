package my.base.cms.control.input;

import java.awt.Checkbox;

import my.base.cms.control.controlBase;

public class inputBase extends controlBase
{
	protected String value = "";
	protected String type = "";

	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}

	public inputBase()
	{

	}
	public inputBase(String id, String name, String text, String type, String value, String classCSS,
			String otherAttribute)
	{
		this.id = id;
		this.name = name;
		this.text = text;
		this.type = type;
		this.value = value;
		this.classCSS = classCSS;
		this.otherAttribute = otherAttribute;
	}
	public inputBase(String id, String value)
	{
		super();
		this.id = id;
		this.name = id;
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	/**
	 * lấy lại dữ liệu trước khi submit
	 */
	void regetValue()
	{
		if (currentCMSPage == null)
			return;

		String temp = currentCMSPage.request.getParameter(this.name);

		if (temp != null)
		{
			if (type.equalsIgnoreCase("text"))
				this.value = temp;
			else if(type.equalsIgnoreCase("radio"))
			{
				if(temp.equalsIgnoreCase(value))
					((inputRadio)this).setChecked("checked");
				else
					((inputRadio)this).setChecked("");
			}
		}
	}
	
	@Override
	protected String buildHTML()
	{
		String html = "<input name=\"" + id + "\" type=\"" + type + "\" id=\"" + id + "\" class=\"mar-right-5  "
				+ classCSS + "\" " + otherAttribute + " value=\"" + value + "\" />";
		return html;
	}
	
	@Override
	protected void begin()
	{
		regetValue();		
	}
	
	@Override
	protected void end()
	{
		
	}
	
}
