package My.Webservice;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import db.define.DBConfig;

public class InitApp implements ServletContextListener
{

	public void contextInitialized(ServletContextEvent event)
	{
		try
		{
			ServletContext context = event.getServletContext();
			
			if (!context.getInitParameter("MySQLPoolName_Viettel").equalsIgnoreCase(""))
			{
				LocalConfig.MySQLPoolName_Viettel = context.getInitParameter("MySQLPoolName_Viettel");
			}

			if (!context.getInitParameter("MySQLPoolName_GPC").equalsIgnoreCase(""))
			{
				LocalConfig.MySQLPoolName_GPC = context.getInitParameter("MySQLPoolName_GPC");
			}
			
			if (!context.getInitParameter("MySQLPoolName_VMS").equalsIgnoreCase(""))
			{
				LocalConfig.MySQLPoolName_VMS = context.getInitParameter("MySQLPoolName_VMS");
			}

			if (!context.getInitParameter("MySQLPoolName_HTC").equalsIgnoreCase(""))
			{
				LocalConfig.MySQLPoolName_HTC = context.getInitParameter("MySQLPoolName_HTC");
			}
			
			if (!context.getInitParameter("MSSQLPoolName_HBStore").equalsIgnoreCase(""))
			{
				LocalConfig.MSSQLPoolName_HBStore = context.getInitParameter("MSSQLPoolName_HBStore");
			}
			
			if (!context.getInitParameter("DBConfigPath").equalsIgnoreCase(""))
			{
				LocalConfig.DBConfigPath = context.getInitParameter("DBConfigPath");

				LocalConfig.mDBConfig_MSSQL_HBStore = new DBConfig(LocalConfig.DBConfigPath, LocalConfig.MSSQLPoolName_HBStore);
				LocalConfig.mDBConfig_MySQL_Viettel = new DBConfig(LocalConfig.DBConfigPath, LocalConfig.MySQLPoolName_Viettel);
				LocalConfig.mDBConfig_MySQL_GPC = new DBConfig(LocalConfig.DBConfigPath, LocalConfig.MySQLPoolName_GPC);
				LocalConfig.mDBConfig_MySQL_VMS = new DBConfig(LocalConfig.DBConfigPath, LocalConfig.MySQLPoolName_VMS);
				LocalConfig.mDBConfig_MySQL_HTC = new DBConfig(LocalConfig.DBConfigPath, LocalConfig.MySQLPoolName_HTC);
			}

			
			
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
