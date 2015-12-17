package load.fix;

import my.base.web.loadBase;
import my.base.web.webBase;

public class LContact extends loadBase
{

	
	public LContact(webBase currentPage)
	{
		super(currentPage);
		this.templatePath = "/template/fix/contact.html";
	}

	public String BuildHTML() throws Exception
	{
		return loadTemplate(templatePath);
	}
	
}
