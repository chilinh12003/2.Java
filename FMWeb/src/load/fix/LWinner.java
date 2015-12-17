package load.fix;

import my.base.web.loadBase;
import my.base.web.webBase;

public class LWinner extends loadBase
{

	
	public LWinner(webBase currentPage)
	{
		super(currentPage);
		this.templatePath = "/template/fix/winner.html";
	}

	public String BuildHTML() throws Exception
	{
		return loadTemplate(templatePath);
	}
	
}
