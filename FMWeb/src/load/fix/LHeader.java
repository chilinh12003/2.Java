package load.fix;

import my.base.web.loadBase;
import my.base.web.webBase;

public class LHeader extends loadBase
{

	
	public LHeader(webBase currentPage)
	{
		super(currentPage);
		this.templatePath = "/template/fix/header.html";
	}

	public String BuildHTML() throws Exception
	{
		return loadTemplate(templatePath);
	}
	
}
