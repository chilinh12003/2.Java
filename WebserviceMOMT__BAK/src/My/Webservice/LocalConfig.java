package My.Webservice;

import uti.utility.MyCurrent;
import uti.utility.MyFile;
import db.define.DBConfig;

public class LocalConfig
{
	static String Log4jConfigPath = null;
	public static String LogConfigPath()
	{
		if (Log4jConfigPath == null)
		{
			String Path = MyCurrent.GetRootPath() + "/ForwardMO/Config/log4j.properties";
			Log4jConfigPath = MyFile.ConvertPath(Path);
			return Log4jConfigPath;
		}
		else
		{
			return Log4jConfigPath;
		}
	}

	public static String  DBConfigPath = "ProxoolConfig.xml";
	
	public static String  MSSQLPoolName_HBStore = "MSSQLPoolName_HBStore";
	
	public static DBConfig mDBConfig_MSSQL_HBStore = null;	
	
	public static String  MySQLPoolName_Viettel = "MySQLPoolName_Viettel";
	public static DBConfig mDBConfig_MySQL_Viettel = null;
	
	public static String  MySQLPoolName_GPC = "MySQLPoolName_GPC";
	public static DBConfig mDBConfig_MySQL_GPC = null;
	
	public static String  MySQLPoolName_VMS = "MySQLPoolName_VMS";
	public static DBConfig mDBConfig_MySQL_VMS = null;
	
	public static String  MySQLPoolName_HTC = "MySQLPoolName_HTC";
	public static DBConfig mDBConfig_MySQL_HTC = null;
	
}
