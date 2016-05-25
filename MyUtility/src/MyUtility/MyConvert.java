package MyUtility;

import java.util.Date;


public class MyConvert
{

	public static double ShortCodeToPrice(String ShortCode)
	{
		if(ShortCode.equals("6083"))
			return 500;
		else if(ShortCode.equals("6183"))
			return 1000;
		else if(ShortCode.equals("6283"))
			return 2000;
		else if(ShortCode.equals("6383"))
			return 3000;
		else if(ShortCode.equals("6483"))
			return 4000;
		else if(ShortCode.equals("6583"))
			return 5000;
		else if(ShortCode.equals("6683"))
			return 10000;
		else if(ShortCode.equals("6783"))
			return 15000;
		else
			return 0;
		
	}
	
	public static String ShortCodeToPrice_String(String ShortCode)
	{
		if(ShortCode.equals("6083"))
			return "500";
		else if(ShortCode.equals("6183"))
			return "1000";
		else if(ShortCode.equals("6283"))
			return "2000";
		else if(ShortCode.equals("6383"))
			return "3000";
		else if(ShortCode.equals("6483"))
			return "4000";
		else if(ShortCode.equals("6583"))
			return "5000";
		else if(ShortCode.equals("6683"))
			return "10000";
		else if(ShortCode.equals("6783"))
			return "15000";
		else
			return "0";
		
	}
	
	/**
	 * Lấy PID theo số điện thoại VD: 097(99)67755 thì (99%20+1) là số được lấy
	 * làm PID
	 * 
	 * @param MSISDN
	 * @return
	 * @throws Exception
	 */
	public static int GetPIDByMSISDN(String MSISDN, Integer MaxPID) throws Exception
	{
		try
		{
			int PID = 1;
			String PID_Temp = "1";

			// hiệu chỉnh số điện thoại thành dạng 9xxx hoặc 1xxx
			String MSISDN_Temp = MyCheck.ValidPhoneNumber(MSISDN, "");

			if (MSISDN_Temp.startsWith("9"))
			{
				PID_Temp = MSISDN_Temp.substring(2, 4);
			}
			else
			{
				//là số điện thoại 11 số
				PID_Temp = MSISDN_Temp.substring(3, 5);
			}

			PID = Integer.parseInt(PID_Temp);

			PID = PID % MaxPID + 1;

			return PID;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}
	
	/**
	 * Lấy PID theo month của 1 Date đưa vào
	 * @param mDate
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public static int GetPIDByDate(Date mDate) throws Exception
	{
		try
		{
			return mDate.getMonth();
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}
	
}
