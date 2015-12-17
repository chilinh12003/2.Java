package my.base.cms.control.data;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.List;

import my.base.cms.control.controlBase;

public abstract class gridView extends controlBase
{
	int pageSize = 10;
	int totalRow = 0;
	int totalPage = 0;

	public int currentPageIndex = 0;
	public String pageLink = "";
	public int maxPage = 3;

	String className;

	String[] listHeaderName;
	String[] listMethod;

	String getIdMethod = "";

	public String getGetIdMethod()
	{
		return getIdMethod;
	}

	public void setGetIdMethod(String getIdMethod)
	{
		this.getIdMethod = getIdMethod;
	}

	public Integer getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(Integer pageSize)
	{
		this.pageSize = pageSize;
	}

	public String getClassName()
	{
		return className;
	}

	public void setClassName(String className)
	{
		this.className = className;
	}

	public String[] getListHeaderName()
	{
		return listHeaderName;
	}

	public void setListHeaderName(String[] listHeaderName)
	{
		this.listHeaderName = listHeaderName;
	}

	public String[] getListMethod()
	{
		return listMethod;
	}

	public void setListMethod(String[] listMethod)
	{
		this.listMethod = listMethod;
	}

	public abstract Integer getTotalRow();

	public abstract List<?> getData();

	public gridView(String className, String[] listHeaderName, String[] listMethod, String getIdMethod)
	{
		this.className = className;
		this.listHeaderName = listHeaderName;
		this.listMethod = listMethod;
		this.getIdMethod = getIdMethod;
	}
	String buildTD(Object obj, int rowCount)
	{
		String formatTD = "<td>{0}</td>";

		StringBuilder builderTD = new StringBuilder(MessageFormat.format(formatTD, rowCount));

		try
		{
			Class<?> cls = Class.forName(className);

			Method metthodGetId = cls.getDeclaredMethod(getIdMethod);
			Object id = metthodGetId.invoke(obj);

			for (String method : listMethod)
			{
				Method metText = cls.getDeclaredMethod(method);
				Object value = metText.invoke(obj);

				builderTD.append(MessageFormat.format(formatTD, value));
			}

			builderTD.append("<td><input type=\"checkbox\" id=\"CheckAll_" + (rowCount + (currentPageIndex * pageSize))
					+ "\" value=\"" + id.toString() + "\"></td>");
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
		return builderTD.toString();
	}

	String buildHeader()
	{
		try
		{
			if (listHeaderName == null)
				throw new Exception("listHeaderName is null");

			String formatTH = "<th>{0}</th>";
			StringBuilder builderTH = new StringBuilder("");

			builderTH.append("<tr class=\"header\"><th class=\"top-left\">STT</th>");
			for (String head : listHeaderName)
			{
				builderTH.append(MessageFormat.format(formatTH, head));
			}
			builderTH
					.append("<th class=\"top-right\"><input type=\"checkbox\" onclick=\"SelectCheckBox_All(this);\"></th></tr>");
			return builderTH.toString();
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
		return "";
	}

	String buildFotter()
	{
		return "<tr><td colspan=\"100%\" class=\"footer\">" + buildPaging() + "</td></tr>";
	}
	@Override
	public String buildHTML()
	{

		String html = "";
		try
		{
			List<?> listObj = getData();

			StringBuilder builderTR = new StringBuilder("");
			String formatTR_1 = "<tr class=\"row-1\">{0}</tr>";
			String formatTR_2 = "<tr class=\"row-2\">{0}</tr>";
			builderTR.append("<table border=\"0\"><tbody>");
			builderTR.append(buildHeader());
			int rowCount = 0;

			if (listObj != null)
			{
				for (Object obj : listObj)
				{
					rowCount++;
					if (rowCount % 2 != 0)
					{
						builderTR.append(MessageFormat.format(formatTR_1, buildTD(obj, rowCount)));
					}
					else
					{
						builderTR.append(MessageFormat.format(formatTR_2, buildTD(obj, rowCount)));
					}
				}
			}

			builderTR.append(buildFotter());
			builderTR.append("</tbody></table>");
			return builderTR.toString();
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
			return html;
		}
	}

	@Override
	protected void begin()
	{
		totalRow = getTotalRow();

		totalPage = (int) (totalRow / pageSize);
		if (totalRow % pageSize > 0)
		{
			totalPage++;
		}
		if (currentPageIndex > totalPage)
			currentPageIndex = totalPage;
		if (currentPageIndex < 1)
			currentPageIndex = 1;
	}

	@Override
	protected void end()
	{

	}

	int beginPage()
	{

		if (totalPage <= maxPage)
			return 1;

		if (currentPageIndex == 1)
		{
			return 1;
		}
		if (currentPageIndex == totalPage)
		{
			return totalPage + 1 - maxPage;
		}

		if (currentPageIndex + maxPage / 2 >= totalPage)
		{
			return totalPage + 1 - maxPage;
		}
		if (currentPageIndex - maxPage / 2 <= 1)
		{
			return 1;
		}
		return currentPageIndex - maxPage / 2;

	}

	public int beginRow()
	{
		return (currentPageIndex - 1) * pageSize + 1;
	}
	public int endRow()
	{
		return (currentPageIndex - 1) * pageSize + pageSize;
	}

	String buildPaging()
	{
		try
		{
			StringBuilder mBuilder = new StringBuilder("");

			mBuilder.append(" <div id=\"paging\" class=\"paging\"> <select name=\"sel_pageSize\" id=\"sel_pageSize\"><option value=\"5\">5</option><option selected=\"selected\" value=\"10\">10</option><option value=\"15\">15</option><option value=\"30\">30</option><option value=\"50\">50</option><option value=\"100\">100</option><option value=\"500\">500</option></select>");
			if (totalPage > maxPage)
			{
				mBuilder.append("<a href=\"" + MessageFormat.format(pageLink, "1") + "\">Đầu</a>");
				for (int i = 0; i < maxPage; i++)
				{
					if (beginPage() + i == currentPageIndex)
					{
						mBuilder.append("<a class=\"active\" href=\""
								+ MessageFormat.format(pageLink, (beginPage() + i)) + "\">" + (beginPage() + i)
								+ "</a>");
					}
					else
					{
						mBuilder.append("<a href=\"" + MessageFormat.format(pageLink, (beginPage() + i)) + "\">"
								+ (beginPage() + i) + "</a>");
					}
				}
				mBuilder.append("<a href=\"" + MessageFormat.format(pageLink, totalPage) + "\">Cuối</a>");
			}
			else
			{
				for (int i = 0; i < totalPage; i++)
				{
					if (beginPage() + i == currentPageIndex)
					{
						mBuilder.append("<a class=\"active\" href=\""
								+ MessageFormat.format(pageLink, (beginPage() + i)) + "\">" + (beginPage() + i)
								+ "</a>");
					}
					else
					{
						mBuilder.append("<a href=\"" + MessageFormat.format(pageLink, (beginPage() + i)) + "\">"
								+ (beginPage() + i) + "</a>");
					}
				}
			}

			mBuilder.append("</div>");

			return mBuilder.toString();
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
			return "";
		}
	}
}
