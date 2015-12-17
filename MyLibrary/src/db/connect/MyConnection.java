package db.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;

import db.define.DBConfig;

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
				case ExistPoolName :
					if (!IsLoadConfig)
					{
						IsLoadConfig = true;
						// String CurrentPath =
						// utility.MyCurrent.GetCurrentPath();
						JAXPConfigurator.configure("ProxoolConfig.xml", false);
					}
					mConn = DriverManager.getConnection("proxool." + mDBConfig.GetPoolName());

				case Both :

					if (!IsLoadConfig)
					{
						IsLoadConfig = true;
						// String CurrentPath =
						// utility.MyCurrent.GetCurrentPath();
						JAXPConfigurator.configure(mDBConfig.ConfigPath, false);
					}

					mConn = DriverManager.getConnection("proxool." + mDBConfig.GetPoolName());

					break;
				default :
					mDBConfig.SetPoolName("Default");

					if (!IsLoadConfig)
					{
						IsLoadConfig = true;
						// String CurrentPath =
						// utility.MyCurrent.GetCurrentPath();
						JAXPConfigurator.configure("ProxoolConfig.xml", false);
					}
					mConn = DriverManager.getConnection("proxool." + mDBConfig.GetPoolName());
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
