package pro.Server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import uti.MyLogger;
import uti.MyText;
import db.DefineMt;

public class Program
{

	static MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath(), Program.class.toString());

	/**
	 * Danh sách các MT đã được định nghĩa (được lấy trong DB khi chươn trinh
	 * bắt đầu chạy
	 */
	public static Vector<DefineMt> listDefineMT = new Vector<DefineMt>();

	public static void getListDefineMT()
	{
		try
		{
			// Lấy DefineMT trong DB
			DefineMt mDefineMT = new DefineMt();

			@SuppressWarnings("unchecked")
			List<DefineMt> Temp_List = (List<DefineMt>) mDefineMT.Get();
			for (DefineMt item : Temp_List)
				listDefineMT.add(item);
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
	}

	public static Map<String, String> dicAccount = new HashMap<String, String>();

	/**
	 * Kiểm tra account
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public static synchronized boolean checkAccount(String username, String password)
	{
		try
		{
			username = username.toLowerCase();
			String TempPass = dicAccount.get(username);

			if (TempPass == null)
				return false;
			else if (TempPass.equals(password))
				return true;
			else return false;
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
		return false;
	}

	/*
	 * Lấy MT đã được định nghĩa trong DB, nếu ko có thì lấy MT mặc định
	 * 
	 * @param mMTType
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	public static String GetDefineMT_Message(DefineMt.MTType mMTType) throws Exception
	{
		try
		{
			String MT = DefineMt.getMTContent(Program.listDefineMT, mMTType);
			MT = MyText.RemoveSpecialLetter(2, MT, ".,;?:-_/[]{}()@!%&*=+ ");
			return MT;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public static String GetDefineMT_Message(DefineMt.MTType mMTType, String FreeTime) throws Exception
	{
		try
		{
			String MT = DefineMt.getMTContent(Program.listDefineMT, mMTType);
			MT = MyText.RemoveSpecialLetter(2, MT, ".,;?:-_/[]{}()@!%&*=+ ");
			MT = MT.replace("[FreeTime]", FreeTime);
			return MT;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

}
