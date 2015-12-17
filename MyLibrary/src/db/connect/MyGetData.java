package db.connect;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.define.DBConfig;
import db.define.MyTableModel;

public class MyGetData
{
	Connection mConnect;

	PreparedStatement stmt = null;
	ResultSet rs = null;
	CallableStatement call_stmt = null;

	public DBConfig mDBConfig = null;

	public MyGetData(DBConfig mDBConfig) throws Exception
	{
		try
		{
			this.mDBConfig = mDBConfig;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public Connection getConnection() throws SQLException, Exception
	{
		return MyConnection.getConnection(mDBConfig);
	}

	public void CloseAll() throws Exception
	{
		try
		{
			if (rs != null)
				rs.close();

			if (call_stmt != null)
				call_stmt.close();

			if (stmt != null)
				stmt.close();

			if (mConnect != null)
				mConnect.close();
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	/**
	 * Lay du lieu bang cau query
	 * 
	 * @param Query
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public MyTableModel GetData_Query(String Query) throws Exception, SQLException
	{
		try
		{
			mConnect = getConnection();

			stmt = mConnect.prepareStatement(Query);
			rs = stmt.executeQuery();

			MyTableModel mTable = new MyTableModel(rs);

			return mTable;

		}
		catch (SQLException e)
		{
			try
			{
				if (mDBConfig.AutoSwitchConnection && mDBConfig.OrderConnection <= mDBConfig.MaxConnectionBackup)
				{
					if (	e.getSQLState().equalsIgnoreCase(DBConfig.SQLState.SQLState_TimeOut.GetValue()) || 
							e.getErrorCode() == DBConfig.SQLErrorCode.SQLErrorCode_TimeOut.GetValue())
					{
						mDBConfig.SetOrderConnection();
						return GetData_Query(Query);
					}
				}
				throw e;
			}
			catch (Exception ex)
			{
				throw ex;
			}			
		}
		catch (Exception ex)
		{
			throw ex;
		}
		finally
		{
			CloseAll();
		}
	}

	public MyTableModel GetData_Pro(String ProName, String[] Arr_Name, String[] Arr_Value) throws Exception,
			SQLException
	{
		try
		{

			if (Arr_Name.length != Arr_Value.length)
			{
				throw new Exception("Arr_Name va Arr_Value co doi dai mang la khac nhau");
			}

			mConnect = getConnection();
			String ListFormat = "";
			for (int i = 0; i < Arr_Name.length; i++)
			{
				if (ListFormat.length() > 0)
					ListFormat += ",";
				ListFormat += "?";
			}

			call_stmt = mConnect.prepareCall("{call " + ProName + "(" + ListFormat + ")}");

			for (int i = 0; i < Arr_Name.length; i++)
			{
				call_stmt.setString(Arr_Name[i], Arr_Value[i]);
			}

			rs = call_stmt.executeQuery();

			MyTableModel mTable = new MyTableModel(rs);

			return mTable;

		}
		catch (SQLException e)
		{

			try
			{
				if (mDBConfig.AutoSwitchConnection && mDBConfig.OrderConnection <= mDBConfig.MaxConnectionBackup)
				{
					if (	e.getSQLState().equalsIgnoreCase(DBConfig.SQLState.SQLState_TimeOut.GetValue()) || 
							e.getErrorCode() == DBConfig.SQLErrorCode.SQLErrorCode_TimeOut.GetValue())
					{
						mDBConfig.SetOrderConnection();
						return GetData_Pro(ProName,Arr_Name,Arr_Value);
					}
				}
				throw e;
			}
			catch (Exception ex)
			{
				throw ex;
			}			
		}
		catch (Exception ex)
		{
			throw ex;
		}
		finally
		{
			CloseAll();
		}
	}

	public MyTableModel GetData_Pro(String ProName, String[] Arr_Name, String[] Arr_Value, Integer IndexResultSet)
			throws Exception, SQLException
	{
		try
		{

			if (Arr_Name.length != Arr_Value.length)
			{
				throw new Exception("Arr_Name va Arr_Value co doi dai mang la khac nhau");
			}

			mConnect = getConnection();
			String ListFormat = "";
			for (int i = 0; i < Arr_Name.length; i++)
			{
				if (ListFormat.length() > 0)
					ListFormat += ",";
				ListFormat += "?";
			}

			call_stmt = mConnect.prepareCall("{call " + ProName + "(" + ListFormat + ")}");

			for (int i = 0; i < Arr_Name.length; i++)
			{
				call_stmt.setString(Arr_Name[i], Arr_Value[i]);
			}

			boolean results = call_stmt.execute();
			int Index = 0;

			for (Index = 0; Index <= IndexResultSet; Index++)
			{
				if (Index == IndexResultSet && results == true)
				{
					rs = call_stmt.getResultSet();
					MyTableModel mTable = new MyTableModel(rs);

					return mTable;
				}

				// are there anymore result sets?
				results = call_stmt.getMoreResults();
			}

			MyTableModel mTable = new MyTableModel(rs);

			return mTable;

		}
		catch (SQLException e)
		{

			try
			{
				if (mDBConfig.AutoSwitchConnection && mDBConfig.OrderConnection <= mDBConfig.MaxConnectionBackup)
				{
					if (	e.getSQLState().equalsIgnoreCase(DBConfig.SQLState.SQLState_TimeOut.GetValue()) || 
							e.getErrorCode() == DBConfig.SQLErrorCode.SQLErrorCode_TimeOut.GetValue())
					{
						mDBConfig.SetOrderConnection();
						return GetData_Pro(ProName,Arr_Name,Arr_Value,IndexResultSet);
					}
				}
				throw e;
			}
			catch (Exception ex)
			{
				throw ex;
			}			
		}
		catch (Exception ex)
		{
			throw ex;
		}
		finally
		{
			CloseAll();
		}
	}
}
