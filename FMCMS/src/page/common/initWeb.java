package page.common;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import db.HibernateSessionFactory;
import db.Member;
import uti.MyConfig;
import uti.MyFile;
public class initWeb  implements ServletContextListener
{
	static
	{
		MyConfig.log4jConfigPath = MyFile.ConvertPath(System.getProperty("catalina.base")  +"/FMCMS/Config/log4j.properties");
		MyConfig.hibernateConfigPath = MyFile.ConvertPath(System.getProperty("catalina.base")  +"/FMCMS/Config/hibernate.cfg.xml");
		
		if(HibernateSessionFactory.ConfigPath == null)
		{
			HibernateSessionFactory.ConfigPath = MyConfig.hibernateConfigPath;
		}		
		HibernateSessionFactory.init();
	}
	

	@Override
	public void contextInitialized(ServletContextEvent arg0)
	{
      
		
		
		MyConfig.Domain = "http://localhost:8080/FMCMS";
		
		ServletContext context = arg0.getServletContext();
		MyConfig.Domain = context.getInitParameter("Domain");
	}


	@Override
	public void contextDestroyed(ServletContextEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

}
