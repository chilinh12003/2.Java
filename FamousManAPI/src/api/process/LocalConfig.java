package api.process;

import java.util.Vector;

import dat.content.DefineMT;
import dat.content.DefineMTObject;
import db.define.DBConfig;

public class LocalConfig
{

	public static String LogConfigPath = "log4j.properties";
	public static String LogDataFolder = ".\\LogFile\\";

	public static String  DBConfigPath = "ProxoolConfig.xml";
	public static String  MySQLPoolName = "MySQL";
	public static String  MSSQLPoolName = "MSSQL";
	
	public static DBConfig mDBConfig_MSSQL = new DBConfig("MSSQL");	
	public static DBConfig mDBConfig_MySQL = new DBConfig("MySQL");
	
	
	public static String SHORT_CODE = "1566";
	// ----------------cau hinh Charging-----------------------------
	public static String VNPURLCharging = "";
	public static String VNPCPName = "MTRAFFIC";
	public static String VNPUserName = "mtraffic";
	public static String VNPPassword = "mtraffic#1235";

	public static Integer MAX_PID = 50;

	/**
	 * Số lượng MO cho phép trả lời trong 1 ngày
	 */
	public static Integer MaxAnswerByDay = 10;


	/**
	 * Điểm cho 1 lần trả lời đúng
	 */
	public static Integer AnswerMark = 20;
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
	

	public static Integer DELAY_SENT_MT = 0;
	
	public static Integer CHARGE_MAX_ERROR_RETRY = 1;
	
	public static Integer LONG_MESSAGE_CONTENT_TYPE = 21;
	
	public static String PackageName = "NGUOINOITIENG";
	public static String ServiceName = "Nhan dien nguoi noi tieng";

	private static Vector<DefineMTObject> mListDefineMT = new Vector<DefineMTObject>();

	public static Vector<DefineMTObject> GetListDefineMT() throws Exception
	{
		if (mListDefineMT == null || mListDefineMT.size() == 0)
		{
			DefineMT mDefineMT = new DefineMT(LocalConfig.mDBConfig_MSSQL);
			mListDefineMT = mDefineMT.GetAllMT();
		}
		return mListDefineMT;
	}

}
