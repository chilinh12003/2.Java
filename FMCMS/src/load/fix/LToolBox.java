package load.fix;

import java.util.List;

import uti.MyConfig;
import db.Member;
import my.base.cms.cmsBase;
import my.base.cms.loadCMS;
import my.base.cms.control.button.buttonBase;

public class LToolBox extends loadCMS
{
	List<buttonBase> listButton;
	public LToolBox(cmsBase currentCMSPage, List<buttonBase> listButton)
	{

		super(currentCMSPage);

		this.templatePath = "/template/fix/toolBox.html";
		this.listButton = listButton;
	}

	public String BuildHTML() throws Exception
	{
		StringBuilder buildButton = new StringBuilder("");

		for (buttonBase item : listButton)
		{
			buildButton.append(item.getHTML());
		}
		return loadTemplate(templatePath, new String[]{currentCMSPage.getPageTitle(), buildButton.toString()});
	}
}
