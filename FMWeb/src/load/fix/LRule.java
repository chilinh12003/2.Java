package load.fix;

import my.base.web.loadBase;
import my.base.web.webBase;

public class LRule extends loadBase
{

	
	public LRule(webBase currentPage)
	{
		super(currentPage);
		this.templatePath = "/template/fix/rule.html";
	}

	public String BuildHTML() throws Exception
	{
		return loadTemplate(templatePath);
	}
	
}
