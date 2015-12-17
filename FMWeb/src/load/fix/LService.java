package load.fix;

import my.base.web.loadBase;
import my.base.web.webBase;

public class LService extends loadBase
{

	
	public LService(webBase currentPage)
	{
		super(currentPage);
		this.templatePath = "/template/fix/service.html";
	}

	public String BuildHTML() throws Exception
	{
		return loadTemplate(templatePath);
	}
	
}
