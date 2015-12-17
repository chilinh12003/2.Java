package dat.content;

import java.sql.SQLException;

import db.connect.MyExecuteData;
import db.connect.MyGetData;
import db.define.DBConfig;
import db.define.MyTableModel;

public class Question
{
	public enum Status
	{
		Nothing(0),
		/**
		 * Câu hỏi mới tạo
		 */
		New(100),
		/**
		 * câu hỏi đang trong phiên đang diễn ra
		 */
		Playing(101),
		/**
		 * Câu hỏi đã chơi xong và kết thúc phiên
		 */
		Finish(102), ;

		private int value;

		private Status(int value)
		{
			this.value = value;
		}

		public int GetValue()
		{
			return this.value;
		}

		public static Status FromInt(int iValue)
		{
			for (Status type : Status.values())
			{
				if (type.GetValue() == iValue) return type;
			}
			return Nothing;
		}

	}

	public MyExecuteData mExec;
	public MyGetData mGet;

	public Question(DBConfig mConfigObj) throws Exception
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
	 * 
	 * @param Type
	 *            <p>
	 *            Type = 2: Lấy câu hỏi theo ngày (Para_1 = PlayDate)
	 *            <p>
	 *            Type = 3: Lấy 1 câu hỏi mới nhất đang chạy (Para_1 =
	 *            StatusID)
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
			String Arr_Name[] =
			{"Type", "Para_1"};
			String Arr_Value[] =
			{Integer.toString(Type), Para_1};

			return mGet.GetData_Pro("Sp_Question_Select", Arr_Name, Arr_Value);
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
	 * <p>Type = 4: Lấy câu hỏi theo Status va theo ngày (Para_1 = StatusID, Para_2 = PlayDate)</p>
	 * @param Para_1
	 * @param Para_2
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public MyTableModel Select(int Type, String Para_1, String Para_2) throws Exception, SQLException
	{
		try
		{
			String Arr_Name[] =
			{"Type", "Para_1", "Para_2"};
			String Arr_Value[] =
			{Integer.toString(Type), Para_1, Para_2};

			return mGet.GetData_Pro("Sp_Question_Select", Arr_Name, Arr_Value);
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

	public MyTableModel Select(int Type) throws Exception, SQLException
	{
		try
		{
			String Arr_Name[] =
			{"Type"};
			String Arr_Value[] =
			{Integer.toString(Type)};

			return mGet.GetData_Pro("Sp_Question_Select", Arr_Name, Arr_Value);
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
	 * Cập nhật
	 * 
	 * @param Type
	 *            <p>
	 *            Type = 0: Update full
	 *            </p>
	 *            <p>
	 *            Type = 1: Update status
	 *            </p>
	 * @param XMLContent
	 * @return
	 * @throws Exception
	 */
	public boolean Update(int Type, String XMLContent) throws Exception
	{
		try
		{
			String[] Arr_Name =
			{"Type", "XMLContent"};
			String[] Arr_Value =
			{Integer.toString(Type), XMLContent};
			return mExec.Execute_Pro("Sp_Question_Update", Arr_Name, Arr_Value);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

}
