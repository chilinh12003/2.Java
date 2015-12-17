package pro.Server;

import uti.MyCurrent;
import uti.MyFile;

public class LocalConfig
{
	static String Log4jConfigPath = null;
	public static String LogConfigPath()
	{
		if (Log4jConfigPath == null)
		{
			String Path = MyCurrent.GetRootPath() + "/ISchoolWS/Config/log4j.properties";
			Log4jConfigPath = MyFile.ConvertPath(Path);
			return Log4jConfigPath;
		}
		else
		{
			return Log4jConfigPath;
		}
	}

	public static String SHORT_CODE = "5138";
	public static String SERVICE_ID = "NHANDIEN";
	public static String SERVICE_NAME = "NhanDienNNT";

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
	public static Integer Buy1Mark = 10;
	public static Integer Buy2Mark = 10;

	/**
	 * Số lần được phép trả lời nhiều nhất trong ngày
	 */
	public static Integer MaxAnswerByDay = 10;

	/**
	 * Số lần được phép mua nhiều nhất trong ngày
	 */
	public static Integer MaxBuySuggestByDay = 10;

	/**
	 * Số câu hỏi được free khi đăng ký hoặc gia hạn
	 */
	public static Integer FreeQuestionCount = 5;
	/**
	 * Số câu hỏi được thêm khi mua 1
	 */
	public static Integer BuyOneQuestionCount = 3;
	/**
	 * Số câu hỏi được thêm khi mua 2
	 */
	public static Integer BuyTwoQuestionCount = 7;
	
	
}
