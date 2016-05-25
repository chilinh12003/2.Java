package MyConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;

import MyDataSource.DBConfig;

public class MyConnection
{
	public static Boolean IsLoadConfig = false;

	public static Connection getConnection(DBConfig mDBConfig) throws SQLException, Exception
	{
		try
		{
			Connection mConn = null;
			switch (mDBConfig.GetConfigType())
			{
				
				case ExistPoolName:
					if (!IsLoadConfig)
					{
						IsLoadConfig = true;
						// String CurrentPath = MyUtility.MyCurrent.GetCurrentPath();
						JAXPConfigurator.configure("ProxoolConfig.xml", false);
					}
					mConn = DriverManager.getConnection("proxool." + mDBConfig.PoolName);
					
				case Both:
					
					if (!IsLoadConfig)
					{
						IsLoadConfig = true;
						// String CurrentPath = MyUtility.MyCurrent.GetCurrentPath();
						JAXPConfigurator.configure(mDBConfig.ConfigPath, false);
					}

					mConn = DriverManager.getConnection("proxool." + mDBConfig.PoolName);
					
					break;
				default :
					mDBConfig.PoolName = "Default";
					
					if (!IsLoadConfig)
					{
						IsLoadConfig = true;
						// String CurrentPath = MyUtility.MyCurrent.GetCurrentPath();
						JAXPConfigurator.configure("ProxoolConfig.xml", false);
					}
					mConn = DriverManager.getConnection("proxool." + mDBConfig.PoolName);
					break;
			}

			return mConn;

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
	

}
