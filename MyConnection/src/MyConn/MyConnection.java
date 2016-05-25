package MyConn;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import MyUtility.MyLogger;

public class MyConnection
{
	/**
	 * Thời gian delay cho mỗi lần check các connection đã hết hạn
	 */
	public static Integer DelayCheckConnection = 15;
	public static boolean AllowCheckConnection = true;
	public static boolean CheckConnectionRunning = false;

	private static Integer ConnectionCount = 1;

	private static Integer GetNextCount()
	{

		if (ConnectionCount < 1000000)
			return ConnectionCount++;
		else
			ConnectionCount = 1;
		return ConnectionCount;
	}

	/**
	 * Chứa danh sách các thuộc tính được lấy lên từ file Config xml
	 */
	private static Vector<ObjectProperties> ListProperties = new Vector<ObjectProperties>();

	/**
	 * Chứa danh sách các connection đã được khởi tạo
	 */
	public static Vector<ObjectConnection> ListConnection = new Vector<ObjectConnection>();

	public static void CloseAll() throws Exception
	{
		try
		{
			for (Iterator<ObjectConnection> mItem = MyConnection.ListConnection.listIterator(); mItem.hasNext();)
			{
				ObjectConnection mObjectConn = mItem.next();

				mObjectConn.RealClose();
				mItem.remove();
			}
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public static ObjectConnection getConnection() throws SQLException, Exception
	{
		try
		{
			ObjectConnection mObjectConn = getConnection("Default");
			return mObjectConn;
		}
		catch (SQLException ex)
		{
			throw ex;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public static ObjectConnection getConnection(String PoolName) throws SQLException, Exception
	{
		try
		{
			if (ListProperties.isEmpty())
			{
				GetPropertiesFromXML("DBPoolConfig.xml");
				if (!CheckConnectionRunning)
				{
					CheckConnection mCheckConnection = new CheckConnection();
					mCheckConnection.start();
				}
			}
			ObjectProperties mObjectPro = new ObjectProperties();

			for (Iterator<ObjectProperties> mItem = ListProperties.listIterator(); mItem.hasNext();)
			{
				ObjectProperties mTempObject = mItem.next();
				if (mTempObject.Alias.equalsIgnoreCase(PoolName))
				{
					mObjectPro = mTempObject;
				}
			}

			if (mObjectPro.IsEmpty())
			{
				throw new Exception("PoolName khong ton tai");
			}

			try
			{
				Class.forName(mObjectPro.DriverClass);
			}
			catch (java.lang.ClassNotFoundException ex)
			{
				throw new Exception("Khong tim thay Driver Class, co the chua import thu vien.");
			}

			for (Iterator<ObjectConnection> mItem = ListConnection.listIterator(); mItem.hasNext();)
			{
				ObjectConnection mTemp = mItem.next();
				if (mTemp.Alias.equalsIgnoreCase(mObjectPro.Alias) && mTemp.CheckAllowExecute())
				{
					mTemp.IsExecuting = true;
					mTemp.ExecuteDate = Calendar.getInstance();
					return mTemp;
				}
			}

			ObjectConnection mObjectConn = new ObjectConnection();

			Connection mConnection = DriverManager.getConnection(mObjectPro.DriverURL, mObjectPro.UserName, mObjectPro.Password);
			mObjectConn.mConn = mConnection;
			mObjectConn.CreateDate = Calendar.getInstance();
			mObjectConn.Alias = mObjectPro.Alias;
			mObjectConn.CloseWhenFisnish = mObjectPro.CloseWhenFisnish;
			mObjectConn.IsExecuting = true;
			mObjectConn.LiveTime = mObjectPro.LiveTime;
			mObjectConn.Index = GetNextCount();
			ListConnection.add(mObjectConn);

			mObjectConn.ExecuteDate = Calendar.getInstance();
			return mObjectConn;
		}
		catch (SQLException ex)
		{
			throw ex;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	private static void GetPropertiesFromXML(String XMLPath) throws Exception
	{
		try
		{
			File fXmlFile = new File(XMLPath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("pool");

			for (int temp = 0; temp < nList.getLength(); temp++)
			{
				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE)
				{
					Element eElement = (Element) nNode;
					ObjectProperties mObject = new ObjectProperties();
					mObject.Alias = eElement.getElementsByTagName("alias").item(0).getTextContent().trim();
					mObject.DriverClass = eElement.getElementsByTagName("driver-class").item(0).getTextContent().trim();
					mObject.DriverURL = eElement.getElementsByTagName("driver-url").item(0).getTextContent().trim();
					mObject.UserName = eElement.getElementsByTagName("driver-user").item(0).getTextContent();
					mObject.Password = eElement.getElementsByTagName("driver-password").item(0).getTextContent();
					mObject.LiveTime = eElement.getElementsByTagName("livetime").item(0).getTextContent().trim().equalsIgnoreCase("") ? 60 : Integer
							.parseInt(eElement.getElementsByTagName("livetime").item(0).getTextContent().trim());

					ListProperties.add(mObject);
				}
			}
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}
}
