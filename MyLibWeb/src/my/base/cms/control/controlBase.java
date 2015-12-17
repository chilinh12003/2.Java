package my.base.cms.control;

import uti.MyLogger;
import my.base.cms.cmsBase;

public abstract class controlBase
{
	protected MyLogger mLog = new MyLogger(this.getClass().toString());
	
	protected cmsBase currentCMSPage;
	
	public cmsBase getCurrentCMSPage()
	{
		return currentCMSPage;
	}
	public void setCurrentCMSPage(cmsBase currentCMSPage)
	{
		this.currentCMSPage = currentCMSPage;
	}

	protected String id = "";
	protected String name = "";
	protected String text = "";
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}

	protected boolean visible = true;
	public boolean isVisible()
	{
		return visible;
	}
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}




	/**
	 * Danh sách các lớp css cần thêm
	 */
	protected String classCSS = "";
	/**
	 * các thành phần khác
	 */
	protected String otherAttribute = "";

	public String getClassCSS()
	{
		return classCSS;
	}
	public controlBase()
	{
		
	}
	
	
	public void setClassCSS(String classCSS)
	{
		this.classCSS = classCSS;
	}

	public String getOtherAttribute()
	{
		return otherAttribute;
	}

	public void setOtherAttribute(String otherAttribute)
	{
		this.otherAttribute = otherAttribute;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}


	
	protected abstract String buildHTML();
	protected abstract void begin();
	protected abstract void end();
	
	public final String getHTML()
	{
		if(!this.visible)
			return "";
		
		begin();
		
		String html = buildHTML();
		
		end();
		
		return html;
	}
	

}
