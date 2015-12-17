package load.fix;

import my.base.web.loadBase;
import my.base.web.webBase;

public class LHome extends loadBase
{

	
	public LHome(webBase currentPage)
	{
		super(currentPage);
		this.templatePath = "/template/fix/home.html";
	}

	public String BuildHTML() throws Exception
	{
		return loadTemplate(templatePath);
	}
	
}
