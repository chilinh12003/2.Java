package uti;

import java.io.File;

public class MyCurrent
{
	public static String GetCurrentPath_User()
	{
		return System.getProperty("user.dir");
	}

	public static String GetClassPath()
	{
		return MyCurrent.class.getClassLoader().getResource("").getPath();
	}
	
	public static String GetRootPath() 
	{
		try
		{
			return System.getProperty("catalina.base");
		}
		catch(Exception ex)
		{
			return "";
		}
	}
	
	public static String getWebRootPath()
	{
		try
		{
			return System.getProperty("catalina.base");
		}
		catch(Exception ex)
		{
			return "";
		}
	}
}
