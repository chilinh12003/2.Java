package dat.service;

import java.sql.SQLException;

import db.connect.MyExecuteData;
import db.connect.MyGetData;
import db.define.DBConfig;
import db.define.MyTableModel;
public class Answer
{

	public MyExecuteData mExec;
	public MyGetData mGet;

	public Answer(DBConfig mConfigObj) throws Exception
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
	 * @param Type
	 *            : Cách thức lấy dữ liệu
	 *            <p>
	 *            Type = 1: lấy thông tin chi tiết 1 record Para_1 = AnswerID
	 *            </p>
	 * @param Para_1
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public MyTableModel Select(int Type, String Para_1) throws Exception, SQLException
	{
		try
		{
			String Arr_Name[] = {"Type", "Para_1"};
			String Arr_Value[] = {Integer.toString(Type), Para_1};

			return mGet.GetData_Pro("Sp_Answer_Select", Arr_Name, Arr_Value);
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
	 * @param Type
	 *            : Cách thức lấy dữ liệu <br/>
	 *            Type = 0: Lay du lieu mẫu <br/>
	 *            Type = 6: Lấy chi tiết 1 Record mới nhất
	 * @param Para_1
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public MyTableModel Select(int Type) throws Exception, SQLException
	{
		try
		{
			String Arr_Name[] = {"Type"};
			String Arr_Value[] = {Integer.toString(Type)};

			return mGet.GetData_Pro("Sp_Answer_Select", Arr_Name, Arr_Value);
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

	public boolean Truncate() throws Exception, SQLException
	{
		try
		{
			return mExec.Execute_Query(" DELETE FROM ANSWER ");
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
	 * @param Type
	 *            <p>
	 *            Type = 5: Lấy danh sách(Para_1 = RowCount, Para_2 = PID,
	 *            Para_3 = OrderID, Para_4 = ProcessNumber, Para_5 =
	 *            ProcessIndex )
	 *            </p>
	 * @param Para_1
	 * @param Para_2
	 * @param Para_3
	 * @param Para_4
	 * @param Para_5
	 * @param Para_6
	 * @param Para_7
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public MyTableModel Select(int Type, String Para_1, String Para_2, String Para_3, String Para_4, String Para_5)
			throws Exception, SQLException
	{
		try
		{
			String Arr_Name[] = {"Type", "Para_1", "Para_2", "Para_3", "Para_4", "Para_5",};
			String Arr_Value[] = {Integer.toString(Type), Para_1, Para_2, Para_3, Para_4, Para_5};

			return mGet.GetData_Pro("Sp_Answer_Select", Arr_Name, Arr_Value);
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
	 * @param Type
	 *            <p>
	 *            Type = 0: Update full
	 *            </p>
	 *            <p>
	 *            Type = 1: Update tinh trang tinh diem
	 *            </p>
	 * 
	 * @param XMLContent
	 * @return
	 * @throws Exception
	 */
	public boolean Update(int Type, String XMLContent) throws Exception
	{
		try
		{
			String[] Arr_Name = {"Type", "XMLContent"};
			String[] Arr_Value = {Integer.toString(Type), XMLContent};
			return mExec.Execute_Pro("Sp_Answer_Update", Arr_Name, Arr_Value);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public boolean Insert(int Type, String XMLContent) throws Exception, SQLException
	{
		try
		{
			String[] Arr_Name = {"Type", "XMLContent"};
			String[] Arr_Value = {Integer.toString(Type), XMLContent};
			return mExec.Execute_Pro("Sp_Answer_Insert", Arr_Name, Arr_Value);
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