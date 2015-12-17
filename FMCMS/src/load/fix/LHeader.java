package load.fix;

import uti.MyConfig;
import db.Member;
import my.base.cms.cmsBase;
import my.base.cms.loadCMS;

public class LHeader extends loadCMS
{
	String action = "";
	public LHeader(cmsBase currentCMSPage, String action)
	{

		super(currentCMSPage);

		this.templatePath = "/template/fix/header.html";
		this.action = action;
	}

	public String BuildHTML() throws Exception
	{

		if (currentCMSPage.checkLogined())
		{
			Member memObj = (Member) currentCMSPage.getLoginedUser();
			String logoutHTML = "<span class=\"hello\">Chào: <span> " + memObj.getLoginName()
					+ "</span></span><span class=\"split\">|</span><a id=\"a_logout\" href=\""+MyConfig.Domain+"/logout\">Thoát</a>";
			return loadTemplate(templatePath, new String[]{action, logoutHTML});
		}
		else
			
		{
			String loginHTML = "<a href=\""+MyConfig.Domain+"/login\" id=\"a_login\">Đăng nhập</a>";
			return loadTemplate(templatePath, new String[]{action, loginHTML});
			/*
			currentPageCMS.response.sendRedirect(MyConfig.Domain + "/login");
			return "";*/
		}
	}
}
