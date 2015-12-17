package pro.server;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import uti.utility.MyConfig;
import uti.utility.MyConfig.Telco;
import db.define.DBConfig;

public class LocalConfig
{
	public static String ProcessConfigFile = "config.properties";
	
	public static String LogConfigPath = "log4j.properties";
	public static String LogDataFolder = ".\\LogFile\\";

	private static String  DBConfigPath = "ProxoolConfig.xml";
	private static String  MySQLPoolName = "MySQL";
	private static String  MSSQLPoolName = "MSSQL";
	
	public static DBConfig mDBConfig_MSSQL = new DBConfig("MySQL");	
	public static DBConfig mDBConfig_MySQL = new DBConfig("MSSQL");
	
	
	public static int NUM_THREAD = 10;
	public static int NUM_THREAD_LOAD_MO = 2;
	public static int NUM_THREAD_INSERTLOG = 1;

	public static MyConfig.Telco CURRENT_TELCO = Telco.NOTHING;

	public static String[] TELCOS = {"GPC"};
	public static String SHORT_CODE = "9315";

	public static int TIME_DELAY_LOAD_MO = 100;
	
	/**
	 * Khoảng thời gian (phút) để khách hàng có thể confirm mua nội dung. 
	 */
	public static int TIME_EXPIRE_CONFIRM = 60;
	public static int CHECK_EXPIRE_TIME_DELAY = 60;
	public static int CHECK_EXPIRE_ROWCOUNT = 10;
	
	
	/**
	 * Cấu hình cho phép bắn MT dài theo content Type là gì
	 */
	public static int LONG_MESSAGE_CONTENT_TYPE = 21;

	public static Properties _prop;

	public static String MT_CHARGING = "1";
	public static String MT_NOCHARGE = "0";
	public static String MT_PUSH = "3";
	public static String MT_REFUND = "2";
	public static String MT_REFUND_SYNTAX = "21";
	public static String MT_REFUND_CONTENT = "22";


	public static String INV_CLASS = "pro.mo.InvalidProcess";
	public static String INV_KEYWORD = "INV";

	public static int MAX_PID = 50;
	
	/**
	 * Khoảng thời gian (phút) load lại service
	 */
	public static int TIME_DELAY_RELOAD_SERVICE = 15;
	
	/**
	 * Nếu bản tin thì cần delay để đảm bảo tin push đủ cho khách hàng
	 */
	public static int TIME_DELAY_SEND_MT = 300;
	
	// ----------------Cau hinh Charging-----------------------------
	public static String VNPURLCharging = "http://115.146.122.173:8092/SetRingBack.asmx";
	public static String VNPCPName = "MTRAFFIC";
	public static String VNPUserName = "mtraffic";
	public static String VNPPassword = "mtraffic#1235";
	
	public static int CHARGE_MAX_ERROR_RETRY = 1;

	
	// ----------------Cau hinh Push MT-----------------------------
	
	/**
	 * Số lượng MT ngắn được push sang VInaphone trong vòng 1 giây
	 */
	public static int PUSHMT_TPS = 30;
	
	
	public static boolean loadProperties(String propFile)
	{
		Properties properties = new Properties();
		System.out.println( "Reading configuration file " + propFile);
		try
		{
			FileInputStream fin = new FileInputStream(propFile);
			properties.load(fin);
			_prop = properties;
			fin.close();
			
			LogDataFolder = properties.getProperty("LogDataFolder", LogDataFolder);
			LogConfigPath = properties.getProperty("LogConfigPath", LogConfigPath);
			DBConfigPath = properties.getProperty("DBConfigPath", DBConfigPath);
			MySQLPoolName = properties.getProperty("MySQLPoolName", MySQLPoolName);
			MSSQLPoolName = properties.getProperty("MSSQLPoolName", MSSQLPoolName);

			mDBConfig_MSSQL = new DBConfig(DBConfigPath, MSSQLPoolName);
			mDBConfig_MySQL = new DBConfig(DBConfigPath, MySQLPoolName);
			
			NUM_THREAD = Integer.parseInt(properties.getProperty("NUM_THREAD", "10"));
			NUM_THREAD_LOAD_MO = Integer.parseInt(properties.getProperty("NUM_THREAD_LOAD_MO", "2"));

			TIME_DELAY_LOAD_MO = Integer
					.parseInt(properties.getProperty("TIME_DELAY_LOAD_MO", Integer.toString(TIME_DELAY_LOAD_MO)));
			
			TIME_EXPIRE_CONFIRM = Integer
					.parseInt(properties.getProperty("TIME_EXPIRE_CONFIRM", Integer.toString(TIME_EXPIRE_CONFIRM)));
			
			TIME_DELAY_SEND_MT = Integer
					.parseInt(properties.getProperty("TIME_DELAY_SEND_MT", Integer.toString(TIME_DELAY_SEND_MT)));
			
			TIME_DELAY_RELOAD_SERVICE = Integer
					.parseInt(properties.getProperty("TIME_DELAY_RELOAD_SERVICE", Integer.toString(TIME_DELAY_RELOAD_SERVICE)));
			

			INV_CLASS = properties.getProperty("INV_CLASS", INV_CLASS);


			String Temp_CURRENT_TELCO = properties.getProperty("CURRENT_TELCO", "NOTHING");

			if (Temp_CURRENT_TELCO.equalsIgnoreCase(Telco.VIETTEL.toString()))
			{
				CURRENT_TELCO = Telco.VIETTEL;
			}
			else if (Temp_CURRENT_TELCO.equalsIgnoreCase(Telco.VMS.toString()))
			{
				CURRENT_TELCO = Telco.VMS;
			}
			else if (Temp_CURRENT_TELCO.equalsIgnoreCase(Telco.GPC.toString()))
			{
				CURRENT_TELCO = Telco.GPC;
			}
			else if (Temp_CURRENT_TELCO.equalsIgnoreCase(Telco.HTC.toString()))
			{
				CURRENT_TELCO = Telco.HTC;
			}
			else if (Temp_CURRENT_TELCO.equalsIgnoreCase(Telco.BEELINE.toString()))
			{
				CURRENT_TELCO = Telco.BEELINE;
			}
			else if (Temp_CURRENT_TELCO.equalsIgnoreCase(Telco.SFONE.toString()))
			{
				CURRENT_TELCO = Telco.SFONE;
			}
			else CURRENT_TELCO = Telco.NOTHING;

			SHORT_CODE = properties.getProperty("SHORT_CODE", SHORT_CODE);

			VNPURLCharging = properties.getProperty("VNPURLCharging", VNPURLCharging);
			VNPUserName = properties.getProperty("VNPUserName", VNPUserName);
			VNPPassword = properties.getProperty("VNPPassword", VNPPassword);
			VNPCPName = properties.getProperty("VNPCPName", VNPCPName);

			CHARGE_MAX_ERROR_RETRY = Integer.parseInt(properties.getProperty("CHARGE_MAX_ERROR_RETRY",
					Integer.toString(CHARGE_MAX_ERROR_RETRY)));
	
			PUSHMT_TPS = Integer.parseInt(properties.getProperty("PUSHMT_TPS", Integer.toString(PUSHMT_TPS)));
			
			return true;
		}
		catch (Exception e)
		{
			System.out.println(e);
			return false;
		}
	}

	public static int getintproperties(String text, int defaultval)
	{
		try
		{
			return Integer.parseInt(_prop.getProperty(text, defaultval + ""));

		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		return defaultval;
	}

	public static String getstringproperties(String text, String defaultval)
	{
		try
		{
			return (_prop.getProperty(text, defaultval + ""));
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		return defaultval;
	}

	public static String[] parseString(String text, String seperator)
	{
		Vector<String> vResult = new Vector<String>();
		if (text == null || "".equals(text)) { return null; }
		String tempStr = text.trim();
		String currentLabel = null;
		int index = tempStr.indexOf(seperator);
		while (index != -1)
		{
			currentLabel = tempStr.substring(0, index).trim();

			if (!"".equals(currentLabel))
			{
				vResult.addElement(currentLabel);
			}
			tempStr = tempStr.substring(index + 1);
			index = tempStr.indexOf(seperator);
		} // Last label
		currentLabel = tempStr.trim();
		if (!"".equals(currentLabel))
		{
			vResult.addElement(currentLabel);
		}
		String[] re = new String[vResult.size()];
		Iterator<String> it = vResult.iterator();
		index = 0;
		while (it.hasNext())
		{
			re[index] = (String) it.next();
			index++;
		}
		return re;
	}

}
