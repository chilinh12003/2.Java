package my.base.cms.control.button;

import my.base.cms.control.controlBase;

public class buttonBase extends controlBase
{
	/**
	 * Chuỗi javascript cho sự kiện click
	 */
	protected String onClick = "";
	protected  String href = "javascript:void(0);";	
	
	public buttonBase()
	{
	}
	public buttonBase(String id, String text, String href, String onClick, String classCSS, String otherAttribute)
	{
		this.id = id;
		this.text = text;
		this.href = href;
		this.onClick = onClick;
		this.classCSS = classCSS;
		this.otherAttribute = otherAttribute;
	}
	public buttonBase(String id, String text, String onClick,String classCSS)
	{
		super();
		this.id = id;
		this.text = text;
		this.onClick = onClick;
		this.classCSS = classCSS;
	}	
	
	public String getHref()
	{
		return href;
	}

	public void setHref(String href)
	{
		this.href = href;
	}
	
	public String getOnClick()
	{
		return onClick;
	}

	public void setOnClick(String onClick)
	{
		this.onClick = onClick;
	}
	
	@Override
	protected String buildHTML()
	{
		String html = "<a href=\"" + href + "\" id=\"" + id + "\" class=\"mar-right-5 " + classCSS + "\"  onclick=\"" + onClick
				+ "\" " + otherAttribute + " >" + text + "</a>";
		return html;
	}
	
	@Override
	protected void begin()
	{
		// TODO Auto-generated method stub
		return ;
	}
	@Override
	protected void end()
	{
		// TODO Auto-generated method stub
		return ;
	}

}
