package dat.content;

import java.sql.SQLException;

import db.connect.MyExecuteData;
import db.connect.MyGetData;
import db.define.DBConfig;
import db.define.MyTableModel;

public class News
{

	public enum Status
	{
		Nothing(0), Active(1), Deactive(2);

		private int value;

		private Status(int value)
		{
			this.value = value;
		}

		public Integer GetValue()
		{
			return this.value;
		}

		public static Status FromInt(int iValue)
		{
			for (Status type : Status.values())
			{
				if (type.GetValue() == iValue)
					return type;
			}
			return Nothing;
		}
	}

	public enum NewsType
	{
		Nothing(0), Content(1), Reminder(2);

		private Integer value;

		private NewsType(int value)
		{
			this.value = value;
		}

		public Integer GetValue()
		{
			return this.value;
		}

		public static NewsType FromInt(Integer iValue)
		{
			for (NewsType type : NewsType.values())
			{
				if (type.GetValue() == iValue)
					return type;
			}
			return Nothing;
		}
	}

	public MyExecuteData mExec;
	public MyGetData mGet;

	public News(DBConfig mConfigObj) throws Exception
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

	public MyTableModel Select(int Type) throws Exception, SQLException
	{
		try
		{
			String Arr_Name[] = {"Type"};
			String Arr_Value[] = {Integer.toString(Type)};

			return mGet.GetData_Pro("Sp_News_Select", Arr_Name, Arr_Value);
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
	 * <br/>
	 *            Type = 7: Lấy chi tiết 1 Record theo status(Para_1 = NewsID,
	 *            Para_2 = StatusID)
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
			return mGet.GetData_Pro("Sp_News_Select", Arr_Name, Arr_Value);
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
	 * <br/>
	 *            Type = 5:Lấy 1 tin random không quan tâm là đã push cho khách
	 *            hàng hay chưa (Para_1 = ServiceID, Para_2 = StatusID, Para_3 =
	 *            BeginDate, Para_4 = EndDate
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
			return mGet.GetData_Pro("Sp_News_Select", Arr_Name, Arr_Value);
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
	 * <br/>
	 *            Type = 6:Lấy 1 tin random không quan tâm là đã push cho khách
	 *            hàng hay chưa cho dịch vụ Cung hoàng đạo (Para_1 = ServiceID,
	 *            Para_2 = StatusID, Para_3 = BeginDate, Para_4 = EndDate,
	 *            Para_5 = ZodiacID)<br/>
	 *            Type = 2: Lấy 1 tin theo Service và theo ngày tháng (Para_1 =
	 *            ServiceID, Para_2 = StatusID, Para_3 = NewsTypeID, Para_4 =
	 *            BeginDate, Para_5 = EndDate
	 * @param Para_1
	 * @param Para_2
	 * @param Para_3
	 * @param Para_4
	 * @param Para_5
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public MyTableModel Select(int Type, String Para_1, String Para_2, String Para_3, String Para_4, String Para_5)
			throws Exception, SQLException
	{
		try
		{
			String Arr_Name[] = {"Type", "Para_1", "Para_2", "Para_3", "Para_4", "Para_5"};
			String Arr_Value[] = {Integer.toString(Type), Para_1, Para_2, Para_3, Para_4, Para_5};
			return mGet.GetData_Pro("Sp_News_Select", Arr_Name, Arr_Value);
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
	 * <br/>
	 *            Type = 3: Lấy danh sách tin chưa push cho 1 khách hàng theo 1
	 *            dịch vụ (Para_1 = PID, Para_2 = MSISDN, Para_3 = ServiceID,
	 *            Para_4 = StatusID, Para_5 = PushTypeID, Para_6 = BeginDate,
	 *            Para_7 = EndDate
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
			return mGet.GetData_Pro("Sp_News_Select", Arr_Name, Arr_Value);
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
	 * <br/>
	 *            Type = 4: Lấy 1 tin cho dịch vụ Cung hoàng đạo (Para_1 = PID,
	 *            Para_2 = MSISDN, Para_3 = ServiceID, Para_4 = StatusID, Para_5
	 *            = PushTypeID, Para_6 = BeginDate, Para_7 = EndDate, Para_8 =
	 *            ZodiacID)
	 * @param Para_1
	 * @param Para_2
	 * @param Para_3
	 * @param Para_4
	 * @param Para_5
	 * @param Para_6
	 * @param Para_7
	 * @param Para_8
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public MyTableModel Select(int Type, String Para_1, String Para_2, String Para_3, String Para_4, String Para_5,
			String Para_6, String Para_7, String Para_8) throws Exception, SQLException
	{
		try
		{
			String Arr_Name[] = {"Type", "Para_1", "Para_2", "Para_3", "Para_4", "Para_5", "Para_6", "Para_7", "Para_8"};
			String Arr_Value[] = {Integer.toString(Type), Para_1, Para_2, Para_3, Para_4, Para_5, Para_6, Para_7,
					Para_8};
			return mGet.GetData_Pro("Sp_News_Select", Arr_Name, Arr_Value);
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
	 *            Type = 1: Update lai Status
	 *            </p>
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
			return mExec.Execute_Pro("Sp_News_Update", Arr_Name, Arr_Value);
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
			return mExec.Execute_Pro("Sp_News_Insert", Arr_Name, Arr_Value);
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
