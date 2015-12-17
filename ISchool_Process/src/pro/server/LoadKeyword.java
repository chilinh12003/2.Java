package pro.server;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import uti.*;
import db.*;

/**
 * Process load keyword trong database
 * 
 * @author Administrator
 * 
 */
public class LoadKeyword extends Thread
{
 	static MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath, LoadKeyword.class.toString());

	private Hashtable<?, ?> hasKeywords;
	// Là 1 cắp ServiceID và keyword chó định dang là: ServiceID@Keyword.
	// Dữ liệu này được lấy trong table Keyword_Config.

	public Vector<?> vtKeyword;
	public boolean isLoaded = false;

	// Kiếm tra cặp ServiceID@Keyword đã được cấu hình hay chưa.
	public Keyword getKeyword(String keyword, String shortCode)
	{
		Keyword mKeyword = new Keyword();

		mKeyword.setClassName(LocalConfig.INV_CLASS);
		mKeyword.setKeyword(LocalConfig.INV_KEYWORD);
		mKeyword.setShortCode(shortCode);

		String keytosearch = shortCode + "@" + keyword;

		keytosearch = keytosearch.toUpperCase();
		String strkey = LocalConfig.INV_KEYWORD;

		// Kiếm tra xem cắp ServiceID@Keyword đã được cấu hình trong DB (Table
		// keyword_Config) hay chưa
		for (Iterator<?> it = vtKeyword.iterator(); it.hasNext();)
		{
			String currLabel = (String) it.next();

			if (keytosearch.startsWith(currLabel))
			{
				strkey = currLabel;
				mKeyword = (Keyword) hasKeywords.get(strkey);
				return mKeyword;
			}
		}
		return mKeyword;
	}

	/**
	 * Lấy keyword khi đã bỏ đi các kỹ tự khoảng trắng
	 * @param keyword
	 * @param shortCode
	 * @return
	 */
	public Keyword getKeywordInvalid(String keyword, String shortCode)
	{
		Keyword retobj = new Keyword();
		String newkeyword = MyText.replaceWhiteLetter(keyword);
		retobj.setClassName(LocalConfig.INV_CLASS);
		retobj.setKeyword(LocalConfig.INV_KEYWORD);
		retobj.setShortCode(shortCode);

		String keytosearch = shortCode + "@" + newkeyword;
		keytosearch = keytosearch.toUpperCase();
		String strkey = LocalConfig.INV_KEYWORD;
		
		for (Iterator<?> it = vtKeyword.iterator(); it.hasNext();)
		{
			String currLabel = (String) it.next();
			if (keytosearch.startsWith(currLabel))
			{
				strkey = currLabel;
				retobj = (Keyword) hasKeywords.get(strkey);
				return retobj;

			}
		}
		return retobj;
	}

	/**
	 * Lấy keyword khi đã bỏ đi các kỹ tự khoảng trắng và ký tự .
	 * @param keyword
	 * @param shortCode
	 * @return
	 */
	public Keyword getKeywordInvalidLast(String keyword, String shortCode)
	{
		Keyword mKeyword = new Keyword();
		String newkeyword = MyText.replaceWhiteLetter(keyword);

		newkeyword = newkeyword.replace(".", "");

		newkeyword = newkeyword.replace(" ", "");

		mKeyword.setClassName(LocalConfig.INV_CLASS);
		mKeyword.setKeyword(LocalConfig.INV_KEYWORD);
		mKeyword.setShortCode(shortCode);

		String keytosearch = shortCode + "@" + newkeyword;
		keytosearch = keytosearch.toUpperCase();
		String strkey = LocalConfig.INV_KEYWORD;
		for (Iterator<?> it = vtKeyword.iterator(); it.hasNext();)
		{
			String currLabel = (String) it.next();
			if (keytosearch.startsWith(currLabel))
			{
				strkey = currLabel;
				mKeyword = (Keyword) hasKeywords.get(strkey);
				return mKeyword;

			}
		}
		return mKeyword;
	}


	public static Hashtable<?, Keyword> retrieveKeyword() throws Exception
	{

		Keyword mKeyword = new Keyword();
		
		Hashtable<String, Keyword> hasKeyword = new Hashtable<String, Keyword>();
		Vector<String> vtkeywords = new Vector<String>();

		try
		{
			@SuppressWarnings("unchecked")
			List<Keyword> mList= (List<Keyword>) mKeyword.Get();

			for (Keyword mItem: mList)
			{
				
				hasKeyword.put(mItem.getShortCode() + "@" + mItem.getKeyword(), mItem);

				vtkeywords.addElement(mItem.getShortCode() + "@" + mItem.getKeyword());
			}

		}		
		catch (Exception ex)
		{
			
			mLog.log.error("Error retrieveKeyword" + ex);
		}

		Program.mLoadKeyword.vtKeyword = vtkeywords;
		return hasKeyword;
	}

	
	public void run()
	{

		mLog.log.info("LoadConfig - Start");
		while (Program.processData)
		{
			try
			{
				hasKeywords = retrieveKeyword();
				isLoaded = true;
				try
				{
					sleep(1000 * 60);
				}
				catch (InterruptedException ex3)
				{
					mLog.log.error(ex3);
				}
			}
			catch (Exception ex3)
			{
				mLog.log.error("Loi khi doc cau hinh:" + ex3.toString(), ex3);
			}

		}

	}

}
