package load.fix;

import java.util.List;

import uti.MyConfig;
import db.Member;
import my.base.cms.cmsBase;
import my.base.cms.loadCMS;
import my.base.cms.control.controlBase;
import my.base.cms.control.button.buttonBase;

public class LSearchBox extends loadCMS
{
	List<controlBase> listControl;
	public LSearchBox(cmsBase currentCMSPage, List<controlBase> listControl)
	{

		super(currentCMSPage);

		this.templatePath = "/template/fix/searchBox.html";
		this.listControl = listControl;
	}

	public String BuildHTML() throws Exception
	{
		StringBuilder builder = new StringBuilder("");

		for (controlBase item : listControl)
		{
			builder.append(item.getHTML());
		}
		return loadTemplate(templatePath, new String[]{builder.toString()});
	}
}
