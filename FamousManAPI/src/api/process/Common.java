package api.process;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import uti.utility.MyCheck;
import uti.utility.MyConfig;
import uti.utility.MyLogger;
import uti.utility.MyText;
import uti.utility.VNPApplication;
import dat.content.DefineMT;
import dat.gateway.ems_send_queue;
import dat.sub.Subscriber;
import db.define.MyTableModel;

public class Common
{
	static MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath,Common.class.toString());

	public static boolean SendMT(String MSISDN, String Keyword, String MTContent, String RequestID) throws Exception
	{
		try
		{
			if (MTContent.trim().equalsIgnoreCase(""))
				return true;

			ems_send_queue mSendQueue = new ems_send_queue(LocalConfig.mDBConfig_MySQL);

			String USER_ID = MSISDN;
			String SERVICE_ID = LocalConfig.SHORT_CODE;

			String COMMAND_CODE = Keyword;

			Long Temp = 0l;
			try
			{
				Temp = Long.parseLong(RequestID);
			}
			catch (Exception ex)
			{
				Temp = System.currentTimeMillis();
			}
			String REQUEST_ID = Temp.toString();

			return mSendQueue.Insert(USER_ID, SERVICE_ID, COMMAND_CODE, MTContent, REQUEST_ID, LocalConfig.LONG_MESSAGE_CONTENT_TYPE.toString());

		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
			return false;
		}
	}

	/**
	 * Lấy PID theo số điện thoại VD: 097(99)67755 thì (99%20+1) là số được lấy
	 * làm PID
	 * 
	 * @param MSISDN
	 * @return
	 * @throws Exception
	 */
	public static int GetPIDByMSISDN(String MSISDN) throws Exception
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
				// là số điện thoại 11 số
				PID_Temp = MSISDN_Temp.substring(3, 5);
			}

			PID = Integer.parseInt(PID_Temp);

			PID = PID % 20 + 1;

			return PID;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	/**
	 * Kiếm tra trong danh sách đăng ký (trong db) có số điện thoại này chưa
	 * 
	 * @param PID
	 * @param MSISDN
	 * @return
	 * @throws Exception
	 */
	public static boolean CheckRegister(int PID, String MSISDN, int ServcieID) throws Exception
	{
		try
		{
			Subscriber mSub = new Subscriber(LocalConfig.mDBConfig_MSSQL);

			MyTableModel mTable = mSub.Select(2, Integer.toString(PID), MSISDN, Integer.toString(ServcieID));
			if (mTable.GetRowCount() > 0)
				return true;
			else
				return false;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	/**
	 * Lấy MT đã được định nghĩa trong DB, nếu ko có thì lấy MT mặc định
	 * 
	 * @param mMTType
	 * @return
	 * @throws Exception
	 */
	public static String GetDefineMT_Message(DefineMT.MTType mMTType) throws Exception
	{
		try
		{
			String MT = DefineMT.GetMTContent(LocalConfig.GetListDefineMT(), mMTType);
			MT = MyText.RemoveSpecialLetter(2, MT, ".,;?:-_/[]{}()@!%&*=+ ");
			return MT;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public static String GetDefineMT_Message(DefineMT.MTType mMTType, String FreeTime) throws Exception
	{
		try
		{
			String MT = DefineMT.GetMTContent(LocalConfig.GetListDefineMT(), mMTType);
			MT = MyText.RemoveSpecialLetter(2, MT, ".,;?:-_/[]{}()@!%&*=+ ");

			MT = MT.replace("[FreeTime]", FreeTime);
			return MT;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public static MyConfig.ChannelType GetChannelType(String Channel)
	{
		try
		{
			return MyConfig.ChannelType.valueOf(Channel);
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
		return MyConfig.ChannelType.NOTHING;
	}

	/**
	 * Lấy
	 * 
	 * @param AppName
	 * @return
	 */
	public static VNPApplication GetApplication(String AppName)
	{
		return VNPApplication.valueOf(AppName.toUpperCase());
	}

	/**
	 * Lấy giá trị 1 node trong chỗi XML
	 * 
	 * @param XML
	 * @param NodeName
	 * @return
	 * @throws Exception
	 */
	public static String GetValueNode(String XML, String NodeName) throws Exception
	{

		try
		{
			String Value = "";
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(XML));

			Document doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName(NodeName);

			Element line = (Element) nodes.item(0);

			Node child = line.getFirstChild();

			if (child instanceof CharacterData)
			{
				CharacterData cd = (CharacterData) child;
				Value = cd.getData();
			}
			return Value;
		}
		catch (Exception ex)
		{
			throw ex;
		}

	}
}
