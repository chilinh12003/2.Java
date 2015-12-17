package MyProcessServer;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import uti.utility.MyConfig.Telco;

import db.define.DBConfig;

public class LocalConfig
{
	public static String ProcessConfigFile = "config.properties";

	public static String LogConfigPath = "log4j.properties";
	public static String LogDataFolder = ".\\LogFile\\";

	private static String DBConfigPath = "ProxoolConfig.xml";
	private static String MySQLPoolName = "MySQL";
	private static String MSSQLPoolName_HBStore = "MSSQL_HBStore";

	public static DBConfig mDBConfig_MSSQL_HBStore = new DBConfig("MSSQL_HBStore");
	public static DBConfig mDBConfig_MySQL = new DBConfig("MySQL");
	
	public static int NUM_THREAD = 10;
	public static int NUM_THREAD_LOAD_MO = 2;
	public static int NUM_THREAD_INSERTLOG = 1;

	public static int MAX_RETRIES = 10;

	public static String LOAD_MO_MODE = "DB";
	public static String MO_DIR = "Z:/";

	public static String[] RUNCLASS = null;

	public static Telco CURRENT_TELCO = Telco.NOTHING;

	public static String[] TELCOS = { "VIETTEL", "VMS", "GPC", "HTC" };
	public static int TIME_DELAY_LOAD_MO = 100;

	public static Properties _prop;

	public static String MT_CHARGING = "1";
	public static String MT_NOCHARGE = "0";
	public static String MT_PUSH = "3";
	public static String MT_REFUND = "2";
	public static String MT_REFUND_SYNTAX = "21";
	public static String MT_REFUND_CONTENT = "22";
	
	public static String MT_SYSTEM_ERROR = "Xin loi ban, hien tai the thong dang qua tai, xin vui long thu lai sau it phut.";
	

	public static String INV_CLASS = "MyProcess.InvalidProcess";
	public static String INV_KEYWORD = "INV";
	public static String INV_INFO = "Tin nhan sai cu phap";

	public static String REFUND_INFO ="";
	
	/**
	 * Neu IS_PUSH_MT = 1; se lay INV_INFO de lam MT tra ve cho khach hang Neu
	 * IS_PUSH_MT = 0; se Luu MO vao table sms_receive_queue_inv
	 */
	public static String IS_PUSH_MT = "1";

	//------BEGIN con fig cho thread MO forward
	public static String MOFORWARD_MT_NOT_FORWARD = "Xin loi ban, hien tai the thong dang qua tai, xin vui long thu lai sau it phut.";
	//Khoảng thời gian delay cho mỗi lần chạy
	public static Integer MOFORWAR_DELAYTIME = 5;
	//Sẽ lấy các recodr (insert_date) có thời gian trước (Time hiện tại - BeforeGetTime): BeforeGetTime- đước tính bằng phút
	public static Integer MOFORWARD_BEFORE_GET_TIME = 60;
	//Số lần retry cho phép
	public static Integer MOFORWARD_MAX_RETRY = 5;
	public static Integer MOFORWARD_IS_REFUND_MONEY = 1;
	//------END
	
	public static String URLSetRingback = "http://115.146.122.173:8092/SetRingBack.asmx";

	public static boolean loadProperties(String propFile)
	{
		Properties properties = new Properties();
		System.out.println("Reading configuration file " + propFile);
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
			MSSQLPoolName_HBStore = properties.getProperty("MSSQLPoolName_HBStore", MSSQLPoolName_HBStore);

			mDBConfig_MSSQL_HBStore = new DBConfig(DBConfigPath, MSSQLPoolName_HBStore);
			mDBConfig_MySQL = new DBConfig(DBConfigPath, MySQLPoolName);
			

			NUM_THREAD = Integer.parseInt(properties.getProperty("NUM_THREAD", "10"));
			NUM_THREAD_LOAD_MO = Integer.parseInt(properties.getProperty("NUM_THREAD_LOAD_MO", "2"));
			LOAD_MO_MODE = properties.getProperty("LOAD_MO_MODE", "DB");

			TIME_DELAY_LOAD_MO = Integer.parseInt(properties.getProperty("TIME_DELAY_LOAD_MO", "" + TIME_DELAY_LOAD_MO));

			String runclass = properties.getProperty("RUNCLASS", "");
			RUNCLASS = parseString(runclass, ",");

			INV_CLASS = properties.getProperty("INV_CLASS", INV_CLASS);
			INV_KEYWORD = properties.getProperty("INV_KEYWORD", INV_KEYWORD);
			INV_INFO = properties.getProperty("INV_INFO", INV_INFO);
			IS_PUSH_MT = properties.getProperty("IS_PUSH_MT", IS_PUSH_MT);
			
			REFUND_INFO = properties.getProperty("REFUND_INFO", REFUND_INFO);
			
			MT_SYSTEM_ERROR = properties.getProperty("MT_SYSTEM_ERROR", "Xin loi ban, hien tai the thong dang qua tai, xin vui long thu lai sau it phut.");

			MAX_RETRIES = Integer.parseInt(properties.getProperty("MAX_RETRIES", "10"));


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
			else
				CURRENT_TELCO = Telco.NOTHING;
			
			
			//------BEGIN MO Forward Thread
			MOFORWARD_MT_NOT_FORWARD = properties.getProperty("MOFORWARD_MT_NOT_FORWARD", MOFORWARD_MT_NOT_FORWARD);
			MOFORWAR_DELAYTIME = Integer.parseInt(properties.getProperty("MOFORWAR_DELAYTIME", MOFORWAR_DELAYTIME.toString()));
			MOFORWARD_BEFORE_GET_TIME = Integer.parseInt(properties.getProperty("MOFORWARD_BEFORE_GET_TIME", MOFORWARD_BEFORE_GET_TIME.toString()));
			MOFORWARD_MAX_RETRY = Integer.parseInt(properties.getProperty("MOFORWARD_MAX_RETRY", MOFORWARD_MAX_RETRY.toString()));
			MOFORWARD_IS_REFUND_MONEY = Integer.parseInt(properties.getProperty("MOFORWARD_IS_REFUND_MONEY", MOFORWARD_IS_REFUND_MONEY.toString()));
			//------END
			
			URLSetRingback = properties.getProperty("URLSetRingback", URLSetRingback);
			
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
		if (text == null || "".equals(text))
		{
			return null;
		}
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
