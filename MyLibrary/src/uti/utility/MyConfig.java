package uti.utility;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Hashtable;
public class MyConfig
{
	public static String KeyconfigName = "MyConfig.properties";

	public static String ViettelPrefix = ",162,163,164,165,166,167,168,169,97,98,96,86";
	public static String VMSPrefix = ",90,93,121,122,126,128,120,89";
	public static String VNPPrefix = ",124,123,125,127,129,91,94,88";
	public static String HTCPrefix = ",186,188,92";
	public static String ListOperator = ",VIETTEL,VMS,GPC,HTC";
	public static String ListServiceID = ",6083,6183,6283,6383,6483,6583,6683,6783";

	

	public static enum Telco
	{
		NOTHING(0), VIETTEL(1), GPC(2), VMS(3), BEELINE(4), SFONE(6), HTC(7);

		private final int value;

		private Telco(int value)
		{
			this.value = value;
		}

		public int getValue()
		{
			return value;
		}
	}

	public static Hashtable<String, String> ListKeyInConfig;

	/*
	 * public static String GetConfig_Value(String Key) throws Exception { try {
	 * Properties mPro = new Properties(); } catch(Exception ex) { throw ex; } }
	 */

	public static String GetConfigValue(String Key) throws Exception
	{
		java.util.Properties pro = new java.util.Properties();

		try
		{
			if (ListKeyInConfig == null || ListKeyInConfig.isEmpty())
			{
				ListKeyInConfig = new Hashtable<String, String>();

				FileInputStream in = new FileInputStream("MyConfig.properties");
				pro.load(in);

				Enumeration<Object> em = pro.keys();
				while (em.hasMoreElements())
				{
					String strKey = (String) em.nextElement();
					ListKeyInConfig.put(strKey, pro.get(strKey).toString());
				}
			}

			if (ListKeyInConfig.containsKey(Key))
			{
				return ListKeyInConfig.get(Key);
			}
			else return "";

		}
		catch (Exception ex)
		{
			throw ex;
		}

	}

	/**
	 * Lấy tên của 1 telco
	 * 
	 * @param mTelco
	 * @return
	 */
	public static String GetTelCoName(Telco mTelco)
	{
		switch (mTelco)
		{
			case BEELINE :
				return "GMobile";
			case VIETTEL :
				return "Viettel";
			case VMS :
				return "Mobifone";
			case GPC :
				return "Vinaphone";
			case SFONE :
				return "SFone";
			case HTC :
				return "VietNamMobile";
			default :
				return "";

		}
	}

	private static String DateFormat_InsertDB = "yyyy-MM-dd HH:mm:ss.SSS";

	public static synchronized SimpleDateFormat Get_DateFormat_InsertDB() throws Exception
	{
		return new SimpleDateFormat(DateFormat_InsertDB);
	}

	private static SimpleDateFormat DateFormat_LongFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

	public static synchronized SimpleDateFormat Get_DateFormat_LongFormat()
	{
		return DateFormat_LongFormat;
	}

	private static SimpleDateFormat DateFormat_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
	public static synchronized SimpleDateFormat Get_DateFormat_yyyyMMdd()
	{
		return DateFormat_yyyyMMdd;
	}

	private static SimpleDateFormat DateFormat_VNShort = new SimpleDateFormat("dd-MM-yyyy");
	public static synchronized SimpleDateFormat Get_DateFormat_VNShort()
	{
		return DateFormat_VNShort;
	}

	private static SimpleDateFormat DateFormat_VNShortSlash = new SimpleDateFormat("dd/MM/yyyy");
	public static synchronized SimpleDateFormat Get_DateFormat_VNShortSlash()
	{
		return DateFormat_VNShortSlash;
	}

	private static SimpleDateFormat DateFormat_VNLong = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	public static synchronized SimpleDateFormat Get_DateFormat_VNLong()
	{
		return DateFormat_VNLong;
	}

	private static SimpleDateFormat DateFormat_VNTimeShort = new SimpleDateFormat("HH:mm");
	public static synchronized SimpleDateFormat Get_DateFormat_VNTimeShort()
	{
		return DateFormat_VNTimeShort;
	}

	private static SimpleDateFormat DateFormat_yyyymmddhhmmss = new SimpleDateFormat("yyyyMMddHHmmss");
	public static synchronized SimpleDateFormat Get_DateFormat_yyyymmddhhmmss()
	{
		return DateFormat_yyyymmddhhmmss;
	}

	private static SimpleDateFormat DateFormat_yyyymmddhhmmssSSS = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	public static synchronized SimpleDateFormat Get_DateFormat_yyyymmddhhmmssSSS()
	{
		return DateFormat_yyyymmddhhmmssSSS;
	}

	/**
	 * Định nghĩa các kênh Đăng ký, Hủy
	 * 
	 * @author Administrator
	 * 
	 */
	public enum ChannelType
	{
		NOTHING(0), SMS(1), IVR(2), WEB(3), WAP(4), USSD(5), CLIENT(6), API(7), UNSUB(7), CSKH(8), MAXRETRY(9), SUBNOTEXIST(
				10), SYSTEM(11);

		private int value;

		private ChannelType(int value)
		{
			this.value = value;
		}

		public Integer GetValue()
		{
			return this.value;
		}

		public static ChannelType FromInt(int iValue)
		{
			for (ChannelType type : ChannelType.values())
			{
				if (type.GetValue() == iValue)
					return type;
			}
			return NOTHING;
		}
	}
}
