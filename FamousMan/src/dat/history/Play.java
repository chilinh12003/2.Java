package dat.history;

import java.sql.SQLException;

import db.connect.MyExecuteData;
import db.connect.MyGetData;
import db.define.DBConfig;
import db.define.MyTableModel;

/**
 * Lưu log hiện tại khi khách hàng tham gia chơi (Mua dữ kiện, trả lời
 * câu hỏi)
 * 
 * @author Administrator
 * 
 */
public class Play
{
	/**
	 * Cho biết khách hàng mua dữ kiện hay là trả lời câu hỏi
	 * 
	 * @author Administrator
	 * 
	 */
	public enum PlayType
	{
		Nothing(0), BuySuggest(1), Answer(2), ;

		private int value;

		private PlayType(int value)
		{
			this.value = value;
		}

		public Integer GetValue()
		{
			return this.value;
		}

		public static PlayType FromInt(Integer iValue)
		{
			for (PlayType type : PlayType.values())
			{
				if (type.GetValue() == iValue) return type;
			}
			return Nothing;
		}
	}

	/**
	 * Tình trạng cho biết, khách hàng mua dữ kiện thành công. hoặc
	 * trả lời thành công...
	 * 
	 * @author Administrator
	 * 
	 */
	public enum Status
	{
		Nothing(0), CorrectAnswer(100), IncorrectAnswer(101), BuySuggest(200), ;

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

	public Play(DBConfig mConfigObj) throws Exception
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
	public MyTableModel Select(int Type, String Para_1) throws Exception, SQLException
	{
		try
		{
			String Arr_Name[] = {"Type", "Para_1"};
			String Arr_Value[] = {Integer.toString(Type), Para_1};

			return mGet.GetData_Pro("Sp_Play_Select", Arr_Name, Arr_Value);
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
	 * Lấy dữ liệu từ DB
	 * 
	 * @param Type
	 *            <p>
	 *            Type = 2: lấy lần mua gợi ý mới nhất (Para_1 =
	 *            PID,Para_2 = MSISDN)
	 *            </p>
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
			String Arr_Name[] = {"Type", "Para_1", "Para_2"};
			String Arr_Value[] = {Integer.toString(Type), Para_1, Para_2};

			return mGet.GetData_Pro("Sp_Play_Select", Arr_Name, Arr_Value);
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
	 *            Type = 3:Lấy danh sách (Para_1 = RowCount, Para_2 = LogID,
	 *            Para_3 = ProcessNumber, Para_4 = ProcessIndex )
	 *            </p>
	 * @param Para_1
	 * @param Para_2
	 * @param Para_3
	 * @param Para_4
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public MyTableModel Select(int Type, String Para_1, String Para_2, String Para_3, String Para_4) throws Exception,
			SQLException
	{
		try
		{
			String Arr_Name[] = {"Type", "Para_1", "Para_2", "Para_3", "Para_4"};
			String Arr_Value[] = {Integer.toString(Type), Para_1, Para_2, Para_3, Para_4};

			return mGet.GetData_Pro("Sp_Play_Select", Arr_Name, Arr_Value);
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
	 *            Type = 4: Lấy danh sách (Para_1 = RowCount,Para_2 =
	 *            PlayTypeID, Para_3 = StatusID,Para_4 = SuggestID, Para_5 =
	 *            LogID, Para_6 = ProcessNumber, Para_7 = ProcessIndex )
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
	public MyTableModel Select(int Type, String Para_1, String Para_2, String Para_3, String Para_4, String Para_5,
			String Para_6, String Para_7) throws Exception, SQLException
	{
		try
		{
			String Arr_Name[] = {"Type", "Para_1", "Para_2", "Para_3", "Para_4", "Para_5", "Para_6", "Para_7"};
			String Arr_Value[] = {Integer.toString(Type), Para_1, Para_2, Para_3, Para_4, Para_5, Para_6, Para_7};

			return mGet.GetData_Pro("Sp_Play_Select", Arr_Name, Arr_Value);
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
			String Arr_Name[] = {"Type"};
			String Arr_Value[] = {Integer.toString(Type)};

			return mGet.GetData_Pro("Sp_Play_Select", Arr_Name, Arr_Value);
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

	public boolean Update(int Type, String XMLContent) throws Exception
	{
		try
		{
			String[] Arr_Name = {"Type", "XMLContent"};
			String[] Arr_Value = {Integer.toString(Type), XMLContent};
			return mExec.Execute_Pro("Sp_Play_Update", Arr_Name, Arr_Value);
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
			return mExec.Execute_Pro("Sp_Play_Insert", Arr_Name, Arr_Value);
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

	public boolean Delete(int Type, String XMLContent) throws Exception
	{
		try
		{
			String[] Arr_Name = { "Type", "XMLContent" };
			String[] Arr_Value = { Integer.toString(Type), XMLContent };
			return mExec.Execute_Pro("Sp_Play_Delete", Arr_Name, Arr_Value);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}
}