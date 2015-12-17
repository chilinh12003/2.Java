package my.base.cms;

import uti.MyConfig;
import my.base.web.webBase;
public class cmsBase extends webBase
{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getURL()
	{
		return MyConfig.Domain + "/" + this.getClass().getSimpleName();
	}
	public Object getLoginedUser()
	{
		return getSession("user");
	}

	public void setLoginedUser(Object value)
	{
		setSession("user", value);
	}

	String pageTitle = "";

	public String getPageTitle()
	{
		return pageTitle;
	}
	public void setPageTitle(String pageTitle)
	{
		this.pageTitle = pageTitle;
	}
	/**
	 * Kiểm tra tình trạng đăng nhập
	 * 
	 * @return
	 */
	public boolean checkLogined()
	{
		if (getLoginedUser() == null)
		{
			return false;
		}
		else return true;
	}
}
