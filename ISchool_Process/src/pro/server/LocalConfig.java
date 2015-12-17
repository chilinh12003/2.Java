package pro.server;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;
public class LocalConfig
{
	public static String ConfigPath = "config.properties";

	public static String LogConfigPath = "log4j.properties";
	public static String LogDataFolder = ".\\LogFile\\";

	public static Integer NUM_THREAD_EXECUTEQUEUE = 2;
	public static Integer NUM_THREAD_LOAD_MO = 2;
	public static Integer NUM_THREAD_LOAD_MT = 2;
	public static Integer TIME_DELAY_LOAD_MO = 100;
	public static Integer TIME_DELAY_LOAD_MT = 100;
	/**
	 * Thời gian nghỉ cho mỗi lần rety lại MT
	 */
	public static Integer TIME_DELAY_RETRY_SEND_MT = 300;

	/**
	 * Khoảng thời gian delay giữa 2 MT gửi cùng lúc
	 */
	public static Integer TIME_DELAY_SEND_MT = 1;
	public static Integer SENDMT_MAX_RETRY = 10;

	public static String SENDMT_LINK = "http://10.58.128.105:7300/smsws?wsdl";
	public static String SENDMT_USERNAME = "mps_test";
	public static String SENDMT_PASSWORK = "mps201407";

	/**
	 * số row đước lấy lên từ table mtqueue để xử lý
	 */
	public static Integer LOADMT_ROWCOUNT = 10;

	public static String SHORT_CODE = "5138";
	public static String SERVICE_NAME = "NhanDienNNT";

	public static String INV_CLASS = "pro.mo.InvalidProcess";
	public static String INV_KEYWORD = "INV";
	public static String INV_INFO = "Tin nhan sai cu phap";

	
	// Cấu hình thread Kết thúc tuần
	/**
	 * Ngày kết thúc phiên sunday = 1, monday = 2,...saturday= 7
	 */
	public static Integer FINISH_SESSION_DAY_OF_WEEK = 2;
	public static String[] FINISH_SESSION_LIST_TIME = {"07"};
	public static Integer FINISH_SESSION_TIME_DELAY = 60;

	// Cấu hình thread Kết thúc ngày
	public static String[] FINISH_DAY_LIST_TIME = {"07"};
	public static Integer FINISH_DAY_TIME_DELAY = 60;
	public static Integer FINISH_DAY_PROCESS_NUMBER = 1;
	public static Integer FINISH_DAY_ROWCOUNT = 10;

	/**
	 * Khung giờ Push tin
	 */
	public static String[] PUSHMT_LIST_TIME_NEWS = {"07"};

	/**
	 * Khoảng thời gian push tin Remider
	 */
	public static String[] PUSHMT_LIST_TIME_REMINDER = {"07"};
	public static Integer PUSHMT_TIME_DELAY = 60;
	public static Integer PUSHMT_PROCESS_NUMBER = 1;
	public static Integer PUSHMT_ROWCOUNT = 10;
	public static Integer PUSHMT_TPS = 0;

	public static Integer MAX_PID = 50;

	/**
	 * Điểm cho 1 lần trả lời đúng
	 */
	public static Integer AnswerMark = 10;
	
	/**
	 * Điểm được tăng khi trả lời đúng 5 câu hỏi free
	 */
	public static Integer AnswerPromotionFiveFree = 20;
	
	/**
	 * Điểm được tăng khi trả lời đúng hết bộ 3 câu hỏi
	 */
	public static Integer AnswerPromotionThree = 30;
	/**
	 * Điểm được tăng khi trả lời đúng hết bộ 7 câu hỏi
	 */
	public static Integer AnswerPromotionSeven = 60;
	
	
	/**
	 * Điểm thưởng khi đăng ký thành công
	 */
	public static Integer RegMark = 10;
	/**
	 * Điểm thưởng khi gia hạn thành công
	 */
	public static Integer RenewMark = 10;
	/**
	 * Điểm thưởng khi mua dữ kiện thành công
	 */
	public static Integer BuyMark = 5;

	/**
	 * Số lần được phép trả lời nhiều nhất trong ngày
	 */
	public static Integer MaxAnswerByDay = 10;

	/**
	 * Số lần được phép mua nhiều nhất trong ngày
	 */
	public static Integer MaxBuySuggestByDay = 10;

	public static Properties mProp;

	public static boolean loadProperties(String propFile)
	{
		Properties properties = new Properties();
		System.out.println("Reading configuration file " + propFile);
		try
		{
			FileInputStream fin = new FileInputStream(propFile);
			properties.load(fin);
			mProp = properties;
			fin.close();

			NUM_THREAD_EXECUTEQUEUE = Integer.parseInt(properties.getProperty("NUM_THREAD_EXECUTEQUEUE", "2"));

			NUM_THREAD_LOAD_MO = Integer.parseInt(properties.getProperty("NUM_THREAD_LOAD_MO", "2"));
			NUM_THREAD_LOAD_MT = Integer.parseInt(properties.getProperty("NUM_THREAD_LOAD_MT", "2"));

			TIME_DELAY_LOAD_MO = Integer
					.parseInt(properties.getProperty("TIME_DELAY_LOAD_MO", "" + TIME_DELAY_LOAD_MO));
			TIME_DELAY_LOAD_MT = Integer
					.parseInt(properties.getProperty("TIME_DELAY_LOAD_MT", "" + TIME_DELAY_LOAD_MT));

			TIME_DELAY_RETRY_SEND_MT = Integer.parseInt(properties.getProperty("TIME_DELAY_RETRY_SEND_MT", ""
					+ TIME_DELAY_RETRY_SEND_MT));

			TIME_DELAY_SEND_MT = Integer
					.parseInt(properties.getProperty("TIME_DELAY_SEND_MT", "" + TIME_DELAY_SEND_MT));

			SENDMT_MAX_RETRY = Integer
					.parseInt(properties.getProperty("SENDMT_MAX_RETRY", SENDMT_MAX_RETRY.toString()));

			SENDMT_LINK = properties.getProperty("SENDMT_LINK", SENDMT_LINK);
			SENDMT_USERNAME = properties.getProperty("SENDMT_USERNAME", SENDMT_USERNAME);
			SENDMT_PASSWORK = properties.getProperty("SENDMT_PASSWORK", SENDMT_PASSWORK);

			LOADMT_ROWCOUNT = Integer.parseInt(properties.getProperty("LOADMT_ROWCOUNT", LOADMT_ROWCOUNT.toString()));

			SHORT_CODE = properties.getProperty("SHORT_CODE", SHORT_CODE);

			INV_CLASS = properties.getProperty("INV_CLASS", INV_CLASS);
			INV_KEYWORD = properties.getProperty("INV_KEYWORD", INV_KEYWORD);
			INV_INFO = properties.getProperty("INV_INFO", INV_INFO);

			FINISH_SESSION_LIST_TIME = properties.getProperty("FINISH_SESSION_LIST_TIME", "10").split("\\|");
			FINISH_SESSION_DAY_OF_WEEK = Integer.parseInt(properties.getProperty("FINISH_SESSION_DAY_OF_WEEK",
					FINISH_SESSION_DAY_OF_WEEK.toString()));
			FINISH_SESSION_TIME_DELAY = Integer.parseInt(properties.getProperty("FINISH_SESSION_TIME_DELAY",
					FINISH_SESSION_TIME_DELAY.toString()));

			FINISH_DAY_LIST_TIME = properties.getProperty("FINISH_DAY_LIST_TIME", "10").split("\\|");
			FINISH_DAY_TIME_DELAY = Integer.parseInt(properties.getProperty("FINISH_DAY_TIME_DELAY",
					FINISH_DAY_TIME_DELAY.toString()));
			FINISH_DAY_ROWCOUNT = Integer.parseInt(properties.getProperty("FINISH_DAY_ROWCOUNT",
					FINISH_DAY_ROWCOUNT.toString()));
			FINISH_DAY_PROCESS_NUMBER = Integer.parseInt(properties.getProperty("FINISH_DAY_PROCESS_NUMBER",
					FINISH_DAY_PROCESS_NUMBER.toString()));

			PUSHMT_LIST_TIME_NEWS = properties.getProperty("PUSHMT_LIST_TIME_NEWS", PUSHMT_LIST_TIME_NEWS.toString())
					.split("\\|");
			PUSHMT_LIST_TIME_REMINDER = properties.getProperty("PUSHMT_LIST_TIME_REMINDER",
					PUSHMT_LIST_TIME_REMINDER.toString()).split("\\|");
			PUSHMT_TIME_DELAY = Integer.parseInt(properties.getProperty("PUSHMT_TIME_DELAY",
					PUSHMT_TIME_DELAY.toString()));
			PUSHMT_ROWCOUNT = Integer.parseInt(properties.getProperty("PUSHMT_ROWCOUNT", PUSHMT_ROWCOUNT.toString()));
			PUSHMT_PROCESS_NUMBER = Integer.parseInt(properties.getProperty("PUSHMT_PROCESS_NUMBER",
					PUSHMT_PROCESS_NUMBER.toString()));
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
			return Integer.parseInt(mProp.getProperty(text, defaultval + ""));

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
			return (mProp.getProperty(text, defaultval + ""));
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
