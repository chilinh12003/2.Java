package uti.utility;

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
	
	public static String GetCurrentPath() 
	{
		try
		{
			File f1 = new File(".");
			return f1.getCanonicalPath();
		}
		catch(Exception ex)
		{
			return "";
		}
	}
}
