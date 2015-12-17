package MyCallWebservice;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import uti.utility.MyLogger;
import MyProcessServer.LocalConfig;
import com.goldsword.alao.soap.sync.server.GoldSwordService_ServiceLocator;
public class GoldSword
{

	public static String CallWebservice(String User_ID, String Service_ID, String Command_Code, String Info,
			String Request_ID, String Receive_Date, String Operator, String UserName, String Password)
	{
		MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath, GoldSword.class.getName());

		String Result = "-1";
		try
		{

			GoldSwordService_ServiceLocator locator = new GoldSwordService_ServiceLocator();
			String URL = LocalConfig._prop.getProperty("URLGoldSword","http://interface.alaogame.com/services/GoldSwordService");
			
			locator.setGoldSwordServiceEndpointAddress(URL);
			Result = locator.getGoldSwordService().syncCharge(User_ID, Service_ID, Command_Code, Info,
					Request_ID, Receive_Date, Operator, UserName, Password);
			
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
			Result = "-1";
		}
		finally
		{
			String Format = "Request GoldSword --> User_ID:%s|Service_ID:%s|Command_Code:%s|Info:%s|Request_ID:%s|Receive_Date:%s|Operator:%s|UserName:%s|Password:%s|Result:%s";
					
			mLog.log.info(String.format(Format, User_ID, Service_ID, Command_Code, Info,
					Request_ID, Receive_Date, Operator, UserName, Password,Result));
		}
		return Result;
	}

	public static String getCharacterDataFromElement(Element e)
	{
		Node child = e.getFirstChild();
		if (child instanceof CharacterData)
		{
			CharacterData cd = (CharacterData) child;
			return cd.getData();
		}
		return "";
	}
}
