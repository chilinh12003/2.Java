package dat.service;

import java.sql.SQLException;

import db.connect.MyExecuteData;
import db.connect.MyGetData;
import db.define.DBConfig;
import db.define.MyTableModel;

public class DefineCode
{
	public MyExecuteData mExec;
	public MyGetData mGet;

	public DefineCode(DBConfig mConfigObj) throws Exception
	{
		try
		{
			mExec = new MyExecuteData(mConfigObj);
			mGet = new MyGetData(mConfigObj);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}
	

	/**
	 * Lấy dữ liệu
	 * 
	 * @param Type
	 *            <p>
	 *            Type = 0: Lấy dữ liệu mẫu
	 *            </p>
	 *            <p>
	 *            TYpe = 2: Lấy Random (Para_1 = RowCount)
	 *            </p>
	 * @param Para_1
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public MyTableModel Select(int Type) throws Exception
	{
		try
		{
			String Arr_Name[] = { "Type" };
			String Arr_Value[] = { Integer.toString(Type) };

			return mGet.GetData_Pro("Sp_DefineCode_Select", Arr_Name, Arr_Value);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}
	
	
	public MyTableModel Select(int Type, String Para_1) throws Exception, SQLException
	{
		try
		{
			mExec.UseTransaction = true;
			String Arr_Name[] = { "Type", "Para_1" };
			String Arr_Value[] = { Integer.toString(Type), Para_1 };

			return mGet.GetData_Pro("Sp_DefineCode_Select", Arr_Name, Arr_Value);
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
	
	public MyTableModel SelectAndDelete(int Type, String Para_1) throws Exception, SQLException
	{
		try
		{
			mExec.UseTransaction = true;
			String Arr_Name[] = { "Type", "Para_1" };
			String Arr_Value[] = { Integer.toString(Type), Para_1 };

			return mGet.GetData_Pro("Sp_DefineCode_Select", Arr_Name, Arr_Value,2);
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
	
	/**
	 * 
	 * @param Type Type = 3: Lấy Random (Para_1 = RowCount, Para_2 = ProcessNumber, Para_3 = ProcessIndex) đông thời xóa luôn trong db
	 * @param Para_1
	 * @param Para_2
	 * @param Para_3
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public MyTableModel SelectAndDelete(int Type, String Para_1, String Para_2, String Para_3) throws Exception, SQLException
	{
		try
		{
			mExec.UseTransaction = true;
			String Arr_Name[] = { "Type", "Para_1","Para_2","Para_3" };
			String Arr_Value[] = { Integer.toString(Type), Para_1,Para_2,Para_3 };

			return mGet.GetData_Pro("Sp_DefineCode_Select", Arr_Name, Arr_Value,2);
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
	
	public boolean Insert(int Type, String XMLContent) throws Exception
	{
		try
		{
			String[] Arr_Name = { "Type", "XMLContent" };
			String[] Arr_Value = { Integer.toString(Type), XMLContent };
			return mExec.Execute_Pro("Sp_DefineCode_Insert", Arr_Name, Arr_Value);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public boolean Delete(int Type, String XMLContent) throws Exception
	{
		try
		{
			String[] Arr_Name = { "Type", "XMLContent" };
			String[] Arr_Value = { Integer.toString(Type), XMLContent };
			return mExec.Execute_Pro("Sp_DefineCode_Delete", Arr_Name, Arr_Value);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}
	
	public boolean Truncate(int Type) throws Exception
	{
		try
		{
			String[] Arr_Name = { "Type",  };
			String[] Arr_Value = { Integer.toString(Type) };
			return mExec.Execute_Pro("Sp_DefineCode_Truncate", Arr_Name, Arr_Value);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}
}
