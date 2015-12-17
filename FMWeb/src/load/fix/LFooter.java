package load.fix;

import my.base.web.loadBase;
import my.base.web.webBase;

public class LFooter extends loadBase
{
	public LFooter(webBase currentPage)
	{
		super(currentPage);
		this.templatePath = "/template/fix/footer.html";
	}

	public String BuildHTML() throws Exception
	{
		return loadTemplate(templatePath);
	}
	
}
