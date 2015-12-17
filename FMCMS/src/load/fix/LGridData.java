package load.fix;

import my.base.cms.cmsBase;
import my.base.cms.loadCMS;
import my.base.cms.control.data.gridView;

public class LGridData extends loadCMS
{
	public gridView gridData;
	public LGridData(cmsBase currentCMSPage, gridView gridData)
	{
		super(currentCMSPage);
		this.templatePath = "/template/fix/gridData.html";
		this.gridData = gridData;
	}

	public String BuildHTML() throws Exception
	{
		return loadTemplate(templatePath, new String[]{gridData.getHTML()});
	}
}
