package page.common;

import db.Member;
import my.base.cms.cmsBase;

public class configWeb
{
	public static String getMenu(cmsBase currentCMSPage)
	{
		return (String)currentCMSPage.getSession("menu");
	}
	
	public static void setMenu(cmsBase currentCMSPage, String value)
	{
		currentCMSPage.setSession("menu", value);
	}
	
	public static Member getLoginedUser(cmsBase currentCMSPage)
	{
		Object obj = currentCMSPage.getLoginedUser();
		if(obj == null)
			return null;
		
		Member memObj = (Member) obj;
		return memObj;
	}
	
	public static void setLoginedUser(cmsBase currentCMSPage,Member memObj)
	{
		currentCMSPage.setLoginedUser(memObj);
	}
}
