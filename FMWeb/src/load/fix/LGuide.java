package load.fix;

import my.base.web.loadBase;
import my.base.web.webBase;

public class LGuide extends loadBase
{

	
	public LGuide(webBase currentPage)
	{
		super(currentPage);
		this.templatePath = "/template/fix/guide.html";
	}

	public String BuildHTML() throws Exception
	{
		return loadTemplate(templatePath);
	}
	
}
