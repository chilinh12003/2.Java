package my.base.web;

import java.io.File;
import java.text.MessageFormat;

import uti.MyConfig;
import uti.MyFile;
import uti.MyLogger;

public class loadBase
{
	protected MyLogger mLog = new MyLogger(this.getClass().toString());

	protected String templatePath = "";
	protected String templatePathRepeat = "";

	protected webBase currentPage;

	public loadBase(webBase currentPage)
	{
		this.currentPage = currentPage;
	}

	public String BuildHTML() throws Exception
	{
		return "";
	}

	public String getHTML() throws Exception
	{
		return BuildHTML();
	}

	public String getRealPath(String Path)
	{
		String realPath = currentPage.getServletContext().getRealPath("/") + Path;
		return realPath;
	}

	public String loadTemplate(String templatePath) throws Exception
	{
		templatePath = getRealPath(templatePath);

		File file = new File(templatePath);
		if (!file.exists())
		{
			return "";
		}

		String strTemplate = MyFile.readFile(file.getAbsolutePath());

		// Replace Domain trước khi Format vì sẽ gấy ra lỗi.
		// VD: Replace {DNS} thanh http://xxx.vn
		strTemplate = strTemplate.replace(MyConfig.DomainParameter, MyConfig.Domain);
		return strTemplate;
	}

	public String loadTemplate(String templatePath, String value) throws Exception
	{
		templatePath = getRealPath(templatePath);

		File file = new File(templatePath);
		if (!file.exists())
		{
			return "";
		}

		String strTemplate = MyFile.readFile(file.getAbsolutePath());

		// Replace Domain trước khi Format vì sẽ gấy ra lỗi.
		// VD: Replace {DNS} thanh http://xxx.vn
		strTemplate = strTemplate.replace(MyConfig.DomainParameter, MyConfig.Domain);
		return MessageFormat.format(strTemplate, value);

	}

	public String loadTemplate(String templatePath, Object[] arrObj) throws Exception
	{
		templatePath = getRealPath(templatePath);

		File file = new File(templatePath);
		if (!file.exists())
		{
			return "";
		}

		String strTemplate = MyFile.readFile(file.getAbsolutePath());

		// Replace Domain trước khi Format vì sẽ gấy ra lỗi.
		// VD: Replace {DNS} thanh http://xxx.vn
		strTemplate = strTemplate.replace(MyConfig.DomainParameter, MyConfig.Domain);
		return MessageFormat.format(strTemplate, arrObj);

	}
	
	public String loadWithString(String strTemplate, Object[] arrObj) throws Exception
	{
		// Replace Domain trước khi Format vì sẽ gấy ra lỗi.
		// VD: Replace {DNS} thanh http://xxx.vn
		strTemplate = strTemplate.replace(MyConfig.DomainParameter, MyConfig.Domain);
		return MessageFormat.format(strTemplate, arrObj);

	}
}
