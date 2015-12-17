package my.base.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uti.MyConfig;
import uti.MyLogger;

public class webBase extends HttpServlet
{
	public boolean getLoadedPage()
	{
		return loadedPage;
	}
	public void setLoadedPage(boolean loadedPage)
	{
		this.loadedPage = loadedPage;
	}

	boolean loadedPage = false;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected  MyLogger mLog = new MyLogger(this.getClass().toString());

	public HttpServletRequest request;
	public HttpServletResponse response;
	public PrintWriter out;	

	public Object getSession(String name)
	{
		return request.getSession(true).getAttribute(name);
	}
	
	public void setSession(String name, Object value)
	{
		request.getSession(true).setAttribute(name, value);
	}
	
	/**
	 * Constructor of the object.
	 */
	public webBase()
	{
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy()
	{
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	void action(HttpServletRequest request, HttpServletResponse response)
	{
		this.request = request;
		this.response = response;

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		
		try
		{
			out = response.getWriter();

			buildAndWriteHTML();

			if(loadedPage == false)
				loadedPage = true;
			
			out.flush();
			out.close();
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
		finally
		{
			visitLog();
		}
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		action(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		action(request, response);
	}

	/**
	 * Returns information about the servlet, such as author, version, and
	 * copyright.
	 * 
	 * @return String information about this servlet
	 */
	public String getServletInfo()
	{
		return "This is my default servlet created by Eclipse";
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException
	{
		// Put your code here
	}

	public void write(String content) throws IOException
	{
		out.print(content);
	}

	public String getIP()
	{
		return request.getRemoteAddr();
	}

	public String getPreviousURL()
	{
		String url = request.getHeader("Referer");
		if (url == null || url.equalsIgnoreCase(""))
		{
			Object obj = request.getAttribute("javax.servlet.forward.request_uri");
			if(obj != null)
				url = obj.toString();
		}
		if( url == null)
			return "";
		else
			return url;
	}

	public String getURL()
	{
		return request.getRequestURI();
	}

	public String getUserAgent()
	{
		return request.getHeader("User-Agent");
	}

	public void buildAndWriteHTML()
	{

	}

	void visitLog()
	{
		try
		{
			String format = "Request ---> IP:%s | URL:%s | UserAgent:%s | PreviusURL:%s";
			mLog.log.info(String.format(format, getIP(), getURL(), getUserAgent(), getPreviousURL()));
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
	}
}
