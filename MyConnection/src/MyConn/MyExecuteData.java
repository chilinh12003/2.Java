package MyConn;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class MyExecuteData
{
	ObjectConnection mConnect;
	public String PoolName = "Default";

	PreparedStatement stmt = null;
	ResultSet rs = null;
	CallableStatement call_stmt = null;

	/**
	 * Cho phép execute có dùng transaction hay không
	 */
	public boolean UseTransaction = false;

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

			if (!mConnect.IsNull())
			{
				mConnect.Close();
			}
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public MyExecuteData() throws Exception, SQLException
	{

	}

	public MyExecuteData(String mPoolName) throws Exception
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

	public Boolean Execute_Query(String Query) throws Exception
	{
		try
		{
			if (mConnect == null)
			{
				mConnect = MyConnection.getConnection(PoolName);
			}
			
			if (UseTransaction)
			{
				mConnect.mConn.setAutoCommit(false);
			}
			else
			{
				mConnect.mConn.setAutoCommit(true);
			}

			stmt = mConnect.mConn.prepareStatement(Query);
			int Result = stmt.executeUpdate();

			if (UseTransaction)
				mConnect.mConn.commit();

			if (Result > 0)
				return true;
			else
				return false;
		}
		catch (Exception ex)
		{
			try
			{
				if (UseTransaction)
					mConnect.mConn.rollback();
			}
			catch (Exception ex_1)
			{
				throw ex_1;
			}

			throw ex;
		}
		finally
		{
			CloseAll();
		}
	}

	/**
	 * Update voi List gia tri duoc truyen vao
	 * 
	 * @param QueryFormat
	 *            Chuoi query duoc dinh danh theo tempate: INSERT INTO Table(ID)
	 *            VALUES(?)
	 * @param ValueList
	 *            Danh sach gia tri can truyen vao, theo dung thu tu cua
	 *            QueryFormat
	 * @return
	 * @throws Exception
	 */
	public Boolean Execute_Query(String QueryFormat, Object[] ValueList) throws Exception
	{
		try
		{
			if (mConnect == null)
			{
				mConnect = MyConnection.getConnection(PoolName);
			}
			if (UseTransaction)
			{
				mConnect.mConn.setAutoCommit(false);
			}
			else
			{
				mConnect.mConn.setAutoCommit(true);
			}

			stmt = mConnect.mConn.prepareStatement(QueryFormat);

			int i = 1;
			for (Object item : ValueList)
			{
				if (item instanceof Integer)
				{
					stmt.setInt(i, (Integer) item);
				}
				else if (item instanceof Double)
				{
					stmt.setDouble(i, (Double) item);
				}
				else if (item instanceof BigDecimal)
				{
					stmt.setBigDecimal(i, (BigDecimal) item);
				}
				else if (item instanceof Float)
				{
					stmt.setFloat(i, (Float) item);
				}
				else if (item instanceof String)
				{
					stmt.setString(i, (String) item);
				}
				else if (item instanceof Timestamp)
				{
					stmt.setTimestamp(i, (Timestamp) item);
				}
				else
				{
					boolean IsError = false;
					try
					{
						stmt.setObject(i, item);
					}
					catch (Exception ex)
					{
						IsError = true;
						System.out.print(ex.getMessage());
					}
					if (IsError)
					{
						stmt.setString(i, item.toString());
					}
				}

				i++;
			}
			int Result = stmt.executeUpdate();

			if (UseTransaction)
				mConnect.mConn.commit();

			if (Result > 0)
				return true;
			else
				return false;
		}
		catch (Exception ex)
		{
			try
			{
				if (UseTransaction)
					mConnect.mConn.rollback();
			}
			catch (Exception ex_1)
			{
				throw ex_1;
			}

			throw ex;
		}
		finally
		{
			CloseAll();
		}
	}

	public boolean Execute_Pro(String ProName, String[] Arr_Name, String[] Arr_Value) throws Exception
	{
		try
		{

			if (Arr_Name.length != Arr_Value.length)
			{
				throw new Exception("Arr_Name va Arr_Value co doi dai mang la khac nhau");
			}

			if (mConnect == null)
			{
				mConnect = MyConnection.getConnection(PoolName);
			}
			
			if (UseTransaction)
			{
				mConnect.mConn.setAutoCommit(false);
			}
			else
			{
				mConnect.mConn.setAutoCommit(true);
			}

			String ListFormat = "";
			for (int i = 0; i < Arr_Name.length; i++)
			{
				if (ListFormat.length() > 0)
					ListFormat += ",";
				ListFormat += "?";
			}

			call_stmt = mConnect.mConn.prepareCall("{call " + ProName + "(" + ListFormat + ")}");

			for (int i = 0; i < Arr_Name.length; i++)
			{
				call_stmt.setString(Arr_Name[i], Arr_Value[i]);
			}

			int Result = call_stmt.executeUpdate();

			if (UseTransaction)
				mConnect.mConn.commit();

			if (Result > 0)
				return true;
			else
				return false;

		}
		catch (Exception ex)
		{
			try
			{
				if (UseTransaction)
					mConnect.mConn.rollback();
			}
			catch (SQLException ex_1)
			{
				throw ex_1;
			}

			throw ex;
		}
		finally
		{
			CloseAll();
		}
	}

}
