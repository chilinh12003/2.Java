package my.base.cms;

import my.base.web.loadBase;
import my.base.web.webBase;

public class loadCMS extends loadBase
{

	public cmsBase currentCMSPage = null;
	public loadCMS(cmsBase currentCMSPage)
	{
		super((webBase)currentCMSPage);
		this.currentCMSPage =currentCMSPage;
	}

}
