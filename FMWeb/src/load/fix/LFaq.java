package load.fix;

import my.base.web.loadBase;
import my.base.web.webBase;

public class LFaq extends loadBase
{

	
	public LFaq(webBase currentPage)
	{
		super(currentPage);
		this.templatePath = "/template/fix/faq.html";
	}

	public String BuildHTML() throws Exception
	{
		return loadTemplate(templatePath);
	}
	
}
