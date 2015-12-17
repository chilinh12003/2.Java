package pro.Server;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import uti.MyCurrent;
import uti.MyFile;
import db.HibernateSessionFactory;

public class InitApp implements ServletContextListener
{
	static
	{
		if(HibernateSessionFactory.ConfigPath == null)
		{
			HibernateSessionFactory.ConfigPath = MyFile.ConvertPath(MyCurrent.GetRootPath()  +"/FMWS/Config/hibernate.cfg.xml");
		}
		
		HibernateSessionFactory.init();
		LocalConfig.LogConfigPath();
	}
	
	public void contextInitialized(ServletContextEvent event)
	{
		try
		{
			ServletContext context = event.getServletContext();
			LocalConfig.SERVICE_ID = context.getInitParameter("SERVICE_ID");
			LocalConfig.SHORT_CODE = context.getInitParameter("SHORT_CODE");
			LocalConfig.SERVICE_NAME = context.getInitParameter("SERVICE_NAME");
			
			Program.dicAccount.put("vtuser", "1qSWDE$32D");
			Program.getListDefineMT();
		}
		catch (Exception ex)
		{
			System.out.println(ex);
		}
	}

	public void contextDestroyed(ServletContextEvent event)
	{
		ServletContext context = event.getServletContext();
		context.log("***ShowLifecycles - Destroyed the servlet context");
	}

}
