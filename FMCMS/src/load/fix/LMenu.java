package load.fix;

import java.text.MessageFormat;
import java.util.List;

import db.Menu;
import page.common.configWeb;
import uti.MyConfig;
import my.base.cms.cmsBase;
import my.base.cms.loadCMS;

public class LMenu extends loadCMS
{
	public LMenu(cmsBase currentCMSPage)
	{
		super(currentCMSPage);
		this.templatePath = "/template/fix/menu.html";
	}

	public String BuildHTML()
	{
		try
		{
			String menuHTML = "";

			menuHTML = configWeb.getMenu(this.currentCMSPage);
			if (menuHTML != null)
			{
				return menuHTML;
			}

			Menu menuDB = new Menu();
			List<Menu> mlist = menuDB.getMenu(configWeb.getLoginedUser(currentCMSPage).getGroupId());

			String strFormat_1 = "  <a class='text-inline' href='javascript:void(0);' onmouseover='ShowSubMenu(this);' onmouseout='HideSubMenu(this.title);' title=\"Sub_{0}\"> "
					+ "{1}" + "</a>";

			String strFormat_2_Out = "  <div class='item' id=\"Sub_{0}\" onmouseover='UndoHideSubMenu();' onmouseout='HideSubMenu(this.id);'>";

			String strFormat_2 = "  <a class='text-inline' href=\"{0}\">" + "{1}" + "</a>";

			StringBuilder mBuilder_1 = new StringBuilder("");
			StringBuilder mBuilder_2 = new StringBuilder("");
			for (Menu menuParent : mlist)
			{
				if (menuParent.getLevel() != 0)
					continue;

				mBuilder_1.append(loadWithString(strFormat_1, new String[]{menuParent.getMenuId().toString(),
						menuParent.getMenuName()}));
				mBuilder_2.append(MessageFormat.format(strFormat_2_Out, menuParent.getMenuId().toString()));

				for (Menu menuChild : mlist)
				{
					if (menuChild.getParentId() != menuParent.getMenuId())
						continue;

					mBuilder_2.append(loadWithString(strFormat_2, new String[]{MyConfig.Domain + menuChild.getLink(),
							menuChild.getMenuName()}));
				}
				mBuilder_2.append("</div>");
			}

			menuHTML = loadTemplate(templatePath, new String[]{mBuilder_1.toString(), mBuilder_2.toString()});

			configWeb.setMenu(currentCMSPage, menuHTML);
			return menuHTML;
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
			return "";
		}
	}

}
