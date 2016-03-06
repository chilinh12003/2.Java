package uti.utility;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class MyText
{
	 private static String[] VietnameseSigns = new String[] { "aAeEoOuUiIdDyY", "ấầậẩẫắằặẳẵáàạảãâă", "ẤẦẬẨẪẮẰẶẲẴÁÀẠẢÃÂĂ", "ếềệểễéèẹẻẽê", "ẾỀỆỂỄÉÈẸẺẼÊ", "ốồộổỗớờợởỡóòọỏõôơ", "ỐỒỘỔỖỚỜỢỞỠÓÒỌỎÕÔƠ", "ứừựửữúùụủũư", "ỨỪỰỬỮÚÙỤỦŨƯ", "íìịỉĩ", "ÍÌỊỈĨ", "đ", "Đ", "ýỳỵỷỹ", "ÝỲỴỶỸ" };

     /// <summary>
     /// Chuyên tiếng việt có dấu thành không dấu
     /// </summary>
     /// <param name="strContent"></param>
     /// <returns></returns>
     public static String RemoveSignVietnameseString(String Content)
     {
         //Tiến hành thay thế , lọc bỏ dấu cho chuỗi

    	 if(Content == null || Content.equals(""))
    		 return "";
    	 
         for (int i = 1; i < VietnameseSigns.length; i++)
         {
             for (int j = 0; j < VietnameseSigns[i].length(); j++)

            	 Content = Content.replace(VietnameseSigns[i].toCharArray()[j], VietnameseSigns[0].toCharArray()[i - 1]);
         }
         return Content;
     }
     
	/**
	 * Đọc nội dung của 1 trang web
	 * 
	 * @param LinkURL
	 *            Đường dẫn cần đọc nội dung
	 * @return
	 * @throws Exception
	 */
	public static String ReadFromURL(String LinkURL) throws Exception
	{
		InputStreamReader mInput = null;
		BufferedReader mBuff = null;
		try
		{
			mInput = new InputStreamReader(new URL(LinkURL).openStream());
			mBuff = new BufferedReader(mInput);
			String line = mBuff.readLine();
			String content = "";
			while (line != null)
			{
				content += line;
				line = mBuff.readLine();
			}
			return content;

		}
		catch (Exception ex)
		{
			throw ex;
		}
		finally
		{
			if(mBuff != null)
			mBuff.close();
		}

	}
	
	/**
	 * Xoa bo ky tu dac biet cua mot chuoi
	 * @param Type: Type = 1: Xoa tat ca, chi giu lai ky tu so
	 * <p>Type = 2: xóa tất cả chỉ giữ lại ký tự chữ và số.</p>
	 * <p>Type = 3: xóa tất cả chỉ giử lại ký tự chữ
	 * @param Source
	 * @return
	 * @throws Exception
	 */
	public static String RemoveSpecialLetter(int Type,String Source) throws Exception
	{
		try
		{
			if(Source == null || Source.equals(""))
				return "";
			
			String Result ="";
			
			if (Type == 1) // Xoa bo tat ca, chi giu lai ky tu so
			{
				int i = 0;
				while (i < Source.length())
				{
					char ch = Source.charAt(i);
					if ((ch >= '0' && ch <= '9'))
					{
						Result +=ch;
					}
					
					i++;
				}
			}
			else if(Type ==2) //Giữ lại ký tự chữ và số
			{
				
				int i = 0;
				while (i < Source.length())
				{
					char ch = Source.charAt(i);
					if ((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z'))
					{
						Result +=ch;
					}
					
					i++;
				}
			}
			else if(Type ==3) //Giữ lại ký tự chữ
			{
				
				int i = 0;
				while (i < Source.length())
				{
					char ch = Source.charAt(i);
					if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z'))
					{
						Result +=ch;
					}
					
					i++;
				}
			}
			return Result;
		}
		catch(Exception ex)
		{
			throw ex;
		}
	}

	/**
	 * Xoa bo ky tu dac biet cua mot chuoi nhưng ko xóa bảo ký tự đặc biệt truyền vào
	 * @param Type: Type = 1: Xoa tat ca, chi giu lai ky tu so
	 * <p>Type = 2: xóa tất cả chỉ giữ lại ký tự chữ và số.</p>
	 * <p>Type = 3: xóa tất cả chỉ giử lại ký tự chữ
	 * @param Source
	 * @param SpecialLetter
	 * Chuỗi ký tự đặc  biết sẽ không bị xóa bỏ
	 * @return
	 * @throws Exception
	 */
	public static String RemoveSpecialLetter(int Type,String Source, String SpecialLetter) throws Exception
	{
		try
		{
			if(Source == null || Source.equals(""))
				return "";
			
			String Result ="";
			
			if (Type == 1) // Xoa bo tat ca, chi giu lai ky tu so
			{
				int i = 0;
				while (i < Source.length())
				{
					char ch = Source.charAt(i);
					if ( SpecialLetter.indexOf(ch) >=0 || (ch >= '0' && ch <= '9'))
					{
						Result +=ch;
					}
					
					i++;
				}
			}
			else if(Type ==2) //Giữ lại ký tự chữ và số
			{
				
				int i = 0;
				while (i < Source.length())
				{
					char ch = Source.charAt(i);
					if (SpecialLetter.indexOf(ch) >=0 ||(ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z'))
					{
						Result +=ch;
					}
					
					i++;
				}
			}
			else if(Type ==3) //Giữ lại ký tự chữ
			{
				
				int i = 0;
				while (i < Source.length())
				{
					char ch = Source.charAt(i);
					if (SpecialLetter.indexOf(ch) >=0 || (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z'))
					{
						Result +=ch;
					}
					
					i++;
				}
			}
			return Result;
		}
		catch(Exception ex)
		{
			throw ex;
		}
	}

	public static String replaceWhiteLetter(String sInput)
	{
		String strTmp = sInput;
		String sReturn = "";
		boolean flag = true;
		int i = 0;
		while (i < sInput.length() && flag)
		{
			char ch = sInput.charAt(i);
			if ((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z'))
			{
				flag = false;
			}
			else
			{
				strTmp = sInput.substring(i + 1);
			}
			i++;

		}
		i = strTmp.length() - 1;
		flag = true;
		sReturn = strTmp;
		while (i >= 0 && flag)
		{
			char ch = strTmp.charAt(i);
			if ((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z'))
			{
				flag = false;
			}
			else
			{
				sReturn = strTmp.substring(0, i);
			}
			i--;
		}
		return sReturn;
	}
}
