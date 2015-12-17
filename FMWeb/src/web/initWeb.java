package web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import uti.MyConfig;
public class initWeb  implements ServletContextListener
{

	@Override
	public void contextDestroyed(ServletContextEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0)
	{
		MyConfig.Domain = "http://localhost:8080/FMWeb";
		
		ServletContext context = arg0.getServletContext();
		MyConfig.Domain = context.getInitParameter("Domain");
	}

}
