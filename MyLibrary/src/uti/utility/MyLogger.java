package uti.utility;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Logging class for the pools.
 */
public class MyLogger
{
	public Logger log = Logger.getLogger(MyLogger.class);
	public String ClassName = "";

	public void SetClassName(String ClassName)
	{
		this.ClassName = ClassName;

		log = Logger.getLogger(this.ClassName);
	}

	/**
	 * 
	 * @param LogConfigPath: Đường dẫn đến file log4j.properties
	 * @param ClassName
	 */
	public MyLogger(String LogConfigPath, String ClassName)
	{
		this.ClassName = ClassName;
		log = Logger.getLogger(this.ClassName);
		PropertyConfigurator.configure(LogConfigPath);
	}

	public static void WriteDataLog(String Folder, String Data) throws Exception
	{
		try
		{
			String CurrentDate = MyConfig.Get_DateFormat_yyyyMMdd().format(Calendar.getInstance().getTime());
			String FileName = (new StringBuilder("DataLog_")).append(CurrentDate).append(".txt").toString();
			String Path = (new StringBuilder(String.valueOf(Folder))).append(FileName).toString();
			Data = MyConfig.Get_DateFormat_LongFormat().format(Calendar.getInstance().getTime()) + " || " + Data;
			MyFile.WriteToFile(Path, Data);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public static void WriteDataLog(String Folder, String LastName, String Data)
	{
		try
		{
			String CurrentDate = MyConfig.Get_DateFormat_yyyyMMdd().format(Calendar.getInstance().getTime());
			String FileName = (new StringBuilder("DataLog_")).append(CurrentDate).append(LastName).append(".txt").toString();
			String Path = (new StringBuilder(String.valueOf(Folder))).append(FileName).toString();
			Data = MyConfig.Get_DateFormat_LongFormat().format(Calendar.getInstance().getTime()) + " || " + Data;
			MyFile.WriteToFile(Path, Data);
		}
		catch (Exception ex)
		{
			Logger log = Logger.getLogger(MyLogger.class);
			log.error(ex);
		}
	}
	public static void WriteDataLog(Date LogDate,String Folder, String LastName, String Data)
	{
		try
		{
			String CurrentDate = MyConfig.Get_DateFormat_yyyyMMdd().format(LogDate);
			String FileName = (new StringBuilder("DataLog_")).append(CurrentDate).append(LastName).append(".txt").toString();
			String Path = (new StringBuilder(String.valueOf(Folder))).append(FileName).toString();
			Data = MyConfig.Get_DateFormat_LongFormat().format(LogDate) + " || " + Data;
			MyFile.WriteToFile(Path, Data);
		}
		catch (Exception ex)
		{
			Logger log = Logger.getLogger(MyLogger.class);
			log.error(ex);
		}
	}

	
	public static String GetLog(Object obj)
	{

		if (obj == null)
			return "";

		StringBuilder mBuilder = new StringBuilder(obj.getClass().toString() + " --> ");

		try
		{
			for (Field field : obj.getClass().getDeclaredFields())
			{
				field.setAccessible(true);
				Object objChild = field.get(obj);

				if (objChild != null)
				{
					mBuilder.append(field.getName() + ":" + objChild + "|");
				}
				else
				{
					mBuilder.append(field.getName() + ":" + " null " + "|");
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return mBuilder.toString();

	}
	public static String GetLog(String Prefix, Object obj)
	{

		return Prefix + "|--> " + GetLog(obj);
	}

	public static String GetLog(Object obj, String Suffixes)
	{

		return GetLog(obj) + "|Note:" + Suffixes;
	}
}
