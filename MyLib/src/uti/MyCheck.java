package uti;

import uti.MyConfig.Telco;

public class MyCheck
{

	public static Boolean CheckServiceID(String ServiceID) throws Exception
	{
		try
		{
			if (MyConfig.ListServiceID.indexOf("," + ServiceID) >= 0)
			{
				return true;
			}
			else return false;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public static Boolean CheckMobileOperator(String Operator) throws Exception
	{
		try
		{
			if (MyConfig.ListOperator.toUpperCase().indexOf("," + Operator.toUpperCase()) >= 0)
			{
				return true;
			}
			else return false;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}
	public static MyConfig.Telco GetTelco(String PhoneNumber) throws Exception
	{
		try
		{
			MyConfig.Telco mTelco = Telco.NOTHING;

			if (PhoneNumber.equals("")) return mTelco;
			
			// Là số test của Vinaphone
			if (PhoneNumber.startsWith("8484")) return MyConfig.Telco.GPC;

			if (PhoneNumber.startsWith("0")) PhoneNumber = PhoneNumber.substring(1);

			if (PhoneNumber.startsWith("84")) PhoneNumber = PhoneNumber.substring(2);

			if (PhoneNumber.startsWith("1"))
			{
				if (MyConfig.ViettelPrefix.indexOf("," + PhoneNumber.substring(0, 3)) >= 0)
					mTelco = MyConfig.Telco.VIETTEL;

				if (MyConfig.VMSPrefix.indexOf("," + PhoneNumber.substring(0, 3)) >= 0) mTelco = MyConfig.Telco.VMS;

				if (MyConfig.VNPPrefix.indexOf("," + PhoneNumber.substring(0, 3)) >= 0) mTelco = MyConfig.Telco.GPC;

				if (MyConfig.HTCPrefix.indexOf("," + PhoneNumber.substring(0, 3)) >= 0) mTelco = MyConfig.Telco.HTC;
			}

			if (PhoneNumber.startsWith("9") || PhoneNumber.startsWith("8"))
			{
				if (MyConfig.ViettelPrefix.indexOf("," + PhoneNumber.substring(0, 2)) >= 0)
					mTelco = MyConfig.Telco.VIETTEL;

				if (MyConfig.VMSPrefix.indexOf("," + PhoneNumber.substring(0, 2)) >= 0) mTelco = MyConfig.Telco.VMS;

				if (MyConfig.VNPPrefix.indexOf("," + PhoneNumber.substring(0, 2)) >= 0) mTelco = MyConfig.Telco.GPC;

				if (MyConfig.HTCPrefix.indexOf("," + PhoneNumber.substring(0, 2)) >= 0) mTelco = MyConfig.Telco.HTC;
			}

			return mTelco;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	/**
	 * Hiệu chỉnh lại đầu số điện thoại
	 * 
	 * @param PhoneNumber
	 *            Số điện thoại cần hiệu chỉnh
	 * @param Prefix
	 *            Tiền tố cần thêm vào VD:0 hoặc 84
	 * @return
	 * @throws Exception
	 */
	public static String ValidPhoneNumber(String PhoneNumber, String Prefix) throws Exception
	{
		try
		{	
			if (PhoneNumber == null || PhoneNumber.equals("")) return "";

			if (PhoneNumber.startsWith("+")) PhoneNumber = PhoneNumber.substring(1);

			if (PhoneNumber.startsWith("0")) PhoneNumber = PhoneNumber.substring(1);

			if (PhoneNumber.startsWith("84")) PhoneNumber = PhoneNumber.substring(2);

			return Prefix + PhoneNumber;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public static String CheckPhoneNumber(String PhoneNumber) throws Exception
	{
		try
		{
			
			MyConfig.Telco mTelco = Telco.NOTHING;

			PhoneNumber = MyText.RemoveSpecialLetter(1, PhoneNumber, "");
			
			
			if (PhoneNumber.equals("")) return "";
			//Xóa các số 0 ở đầu số
			if (PhoneNumber.startsWith("0")) PhoneNumber = PhoneNumber.substring(1);
			
			//Xóa số 84 ở đầu số
			if (PhoneNumber.startsWith("84")) PhoneNumber = PhoneNumber.substring(2);

			//Nếu chữ số bắt đầu không phải là 9,8,1 thì không hợp lệ
			if (!PhoneNumber.startsWith("9") && !PhoneNumber.startsWith("1") && !PhoneNumber.startsWith("8")) return "";
			
			//Nếu đồ dài khác 9 va khác 10 thì không hợp lệ
			if (PhoneNumber.length() != 9 && PhoneNumber.length() != 10) return "";

			//nếu đồ dài khác 9 mà với số có đầu số là 9 thì không hợp lệ
			if (PhoneNumber.length() != 9 && (PhoneNumber.substring(0, 1) == "9" || PhoneNumber.substring(0, 1) == "8")) return "";

			if (PhoneNumber.length() != 10 && PhoneNumber.substring(0, 1) == "1") return "";

			if (PhoneNumber.startsWith("1"))
			{
				if (MyConfig.ViettelPrefix.indexOf("," + PhoneNumber.substring(0, 3)) >= 0)
					mTelco = MyConfig.Telco.VIETTEL;

				if (MyConfig.VMSPrefix.indexOf("," + PhoneNumber.substring(0, 3)) >= 0) mTelco = MyConfig.Telco.VMS;

				if (MyConfig.VNPPrefix.indexOf("," + PhoneNumber.substring(0, 3)) >= 0) mTelco = MyConfig.Telco.GPC;

				if (MyConfig.HTCPrefix.indexOf("," + PhoneNumber.substring(0, 3)) >= 0) mTelco = MyConfig.Telco.HTC;
			}

			if (PhoneNumber.startsWith("9") || PhoneNumber.startsWith("8"))
			{
				if (MyConfig.ViettelPrefix.indexOf("," + PhoneNumber.substring(0, 2)) >= 0)
					mTelco = MyConfig.Telco.VIETTEL;

				if (MyConfig.VMSPrefix.indexOf("," + PhoneNumber.substring(0, 2)) >= 0) mTelco = MyConfig.Telco.VMS;

				if (MyConfig.VNPPrefix.indexOf("," + PhoneNumber.substring(0, 2)) >= 0) mTelco = MyConfig.Telco.GPC;

				if (MyConfig.HTCPrefix.indexOf("," + PhoneNumber.substring(0, 2)) >= 0) mTelco = MyConfig.Telco.HTC;
			}

			if (mTelco == Telco.NOTHING) return "";

			return "84" + PhoneNumber;

		}
		catch (Exception ex)
		{
			throw ex;
		}

	}

	/**
	 * Kiem tra chuoi co phai so hay khong
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str)
	{
		for (char c : str.toCharArray())
		{
			if (!Character.isDigit(c)) return false;
		}
		return true;
	}
}
