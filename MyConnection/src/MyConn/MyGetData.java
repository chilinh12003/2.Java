package MyConn;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import MyDataSource.*;

public class MyGetData
{
	
	ObjectConnection mConnect;
	public String PoolName = "Default";

	PreparedStatement stmt = null;
	ResultSet rs = null;
	CallableStatement call_stmt = null;
	
	public void CloseAll() throws Exception
	{
		try
		{
			if(rs!=null)
				rs.close();
			
			if(call_stmt != null)
				call_stmt.close();
			
			if (stmt != null)
				stmt.close();

			if (mConnect != null && !mConnect.IsNull())
			{
				mConnect.Close();
			}
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}
	
	public MyGetData() throws Exception, SQLException 
	{
		
		
	}
	
	public MyGetData(String mPoolName) throws Exception
	{
		try
		{
			PoolName = mPoolName;			
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
			if (mConnect == null)
			{
				mConnect = MyConnection.getConnection(PoolName);
			}
			stmt = mConnect.mConn.prepareStatement(Query);
			rs = stmt.executeQuery();

			MyTableModel mTable = new MyTableModel(rs);

			CloseAll();
			
			return mTable;

		}
		catch (SQLException e)
		{
			CloseAll();
			throw e;
			
		}
		catch (Exception ex)
		{
			CloseAll();
			throw ex;
		}
		

	}
	
	public MyTableModel GetData_Pro(String ProName,String[] Arr_Name, String[] Arr_Value) throws Exception, SQLException
	{
		try
		{
			
			if(Arr_Name.length != Arr_Value.length)
			{
				throw new Exception("Arr_Name va Arr_Value co doi dai mang la khac nhau");
			}
			
			if (mConnect == null)
			{
				mConnect = MyConnection.getConnection(PoolName);
			}
			String ListFormat ="";
			for(int i =0; i < Arr_Name.length; i++)
			{
				if(ListFormat.length()>0)
					ListFormat +=",";
				ListFormat +="?";
			}
			
			call_stmt = mConnect.mConn.prepareCall("{call "+ProName+"("+ListFormat+")}");
			
			for(int i=0; i< Arr_Name.length;i++)
			{
				call_stmt.setString(Arr_Name[i], Arr_Value[i]);
			}
			
			rs = call_stmt.executeQuery();
			
			MyTableModel mTable = new MyTableModel(rs);
			
			return mTable;

		}
		catch (SQLException e)
		{
			throw e;
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
