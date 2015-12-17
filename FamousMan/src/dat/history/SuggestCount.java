package dat.history;

import java.sql.SQLException;

import db.connect.MyExecuteData;
import db.connect.MyGetData;
import db.define.DBConfig;
import db.define.MyTableModel;

public class SuggestCount
{
	
	public MyExecuteData mExec;
	public MyGetData mGet;
	
	public SuggestCount(DBConfig mConfigObj) throws Exception
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
	 * lấy thông tin từ DB
	 * @param Type
	 * <p>Type = 1: Lấy chi tiết 1 Record (Para_1 = SuggestID) </p>
	 * <p>Type = 2: Lấy theo QuestionID (Para_1 = QuestionID) </p>
	 * <p>Type = 5: Lất tất cả dữ liệu theo PlayDate (Para_1 = PlayDate)</p>
	 * @param Para_1
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public MyTableModel Select(int Type, String Para_1) throws Exception, SQLException
	{
		try
		{
			String Arr_Name[] ={"Type", "Para_1"};
			String Arr_Value[] ={Integer.toString(Type), Para_1};
			
			return mGet.GetData_Pro("Sp_SuggestCount_Select", Arr_Name, Arr_Value);
		}
		catch(SQLException ex)
		{
			throw ex;
		}
		catch(Exception ex)
		{
			throw ex;
		}
	}
	
	/**
	 * 
	 * @param Type
	 * <p>Type = 0: Lấy dữ liệu mẫu</p>
	 * <p>Type = 3: Kiểm tra xem Table có dữ liệu hay không</p>
	 * <p>TYpe = 4: Lất tất cả dữ liệu</p>
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public MyTableModel Select(int Type) throws Exception, SQLException
	{
		try
		{
			String Arr_Name[] ={"Type"};
			String Arr_Value[] ={Integer.toString(Type)};
			
			return mGet.GetData_Pro("Sp_SuggestCount_Select", Arr_Name, Arr_Value);
		}
		catch(SQLException ex)
		{
			throw ex;
		}
		catch(Exception ex)
		{
			throw ex;
		}
	}
	

	/**
	 * 
	 * @param Type
	 * <p>Type = 0: update full</p>
	 * <p>Type = 1: Update nếu không tồn tại thì thêm mới</p>
	 * @param XMLContent
	 * @return
	 * @throws Exception
	 */
	public boolean Update(int Type, String XMLContent) throws Exception
	{
		try
		{
			String[] Arr_Name = { "Type", "XMLContent" };
			String[] Arr_Value = { Integer.toString(Type), XMLContent };
			return mExec.Execute_Pro("Sp_SuggestCount_Update", Arr_Name, Arr_Value);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	 
	public boolean Insert(int Type, String XMLContent) throws Exception,SQLException
	{
		try
		{
			String[] Arr_Name = { "Type", "XMLContent" };
			String[] Arr_Value = { Integer.toString(Type), XMLContent };
			return mExec.Execute_Pro("Sp_SuggestCount_Insert", Arr_Name, Arr_Value);
		}
		catch(SQLException ex)
		{
			throw ex;
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
			return mExec.Execute_Pro("Sp_SuggestCount_Delete", Arr_Name, Arr_Value);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}
}
