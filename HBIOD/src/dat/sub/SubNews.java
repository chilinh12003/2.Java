package dat.sub;

import java.sql.SQLException;

import db.connect.MyExecuteData;
import db.connect.MyGetData;
import db.define.DBConfig;
import db.define.MyTableModel;

public class SubNews
{
	public MyExecuteData mExec;
	public MyGetData mGet;

	public enum Status
	{
		Nothing(0), WaitConfirm(1), Send(2), Notify(3);

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
	public SubNews(DBConfig mConfigObj) throws Exception
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
	 * <br/>
	 *            Type = 5:Lấy 1 Row mới nhất
	 * @return
	 * @throws Exception
	 */
	public MyTableModel Select(int Type) throws Exception
	{
		try
		{
			String Arr_Name[] = {"Type"};
			String Arr_Value[] = {Integer.toString(Type)};

			return mGet.GetData_Pro("Sp_SubNews_Select", Arr_Name, Arr_Value);
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
	 *            Type = 1: Lấy chi tiết 1 Record (Para_1 = SubNewsKey)
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

			return mGet.GetData_Pro("Sp_SubNews_Select", Arr_Name, Arr_Value);
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
	 *            Type = 2: Lấy 1 record theo NewsKey và status (Para_1 =
	 *            NewsKey, Para_2 = StatusID)
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

			return mGet.GetData_Pro("Sp_SubNews_Select", Arr_Name, Arr_Value);
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
	 *            Type = 3: Lấy danh sách (Para_1 = PID, Para_2 = MSISDN, Para_3
	 *            = ServiceID ) <br/>
	 *            Type = 6: Lấy 1 record theo PID, NewsKey và status (Para_1
	 *            PID, Para_2 = NewsKey, Para_3 = StatusID)<br/>
	 *            Type = 7: Lấy danh sách các SubNews đã hết hạn (Para_1 =
	 *            RowCount, Para_2 = StatusID, Para_3 = ExpireDate)<br/>
	 *            Type = 8: Lấy TOP 1 record theo PID, MSISDN, Status (Para_1 PID, Para_2 = MSISDN, Para_3 = StatusID)
	 * @param Para_1
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public MyTableModel Select(int Type, String Para_1, String Para_2, String Para_3) throws Exception, SQLException
	{
		try
		{
			String Arr_Name[] = {"Type", "Para_1", "Para_2", "Para_3"};
			String Arr_Value[] = {Integer.toString(Type), Para_1, Para_2, Para_3};

			return mGet.GetData_Pro("Sp_SubNews_Select", Arr_Name, Arr_Value);
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
	 *            Type = 4: Lấy danh sách theo (Para_1 = PID, Para_2 = MSISDN,
	 *            Para_3 = ServiceID, Para_4 = NewsID)
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

			return mGet.GetData_Pro("Sp_SubNews_Select", Arr_Name, Arr_Value);
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
	 *            Type = 0: Update full
	 *            </p>
	 *            <p>
	 *            Type = 1: Update thông tin confirm va Sent MT
	 *            </p>
	 *            <p>
	 *            Type = 2: Update thông tin Notify
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
			return mExec.Execute_Pro("Sp_SubNews_Update", Arr_Name, Arr_Value);
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
			String[] Arr_Name = {"Type", "XMLContent"};
			String[] Arr_Value = {Integer.toString(Type), XMLContent};
			return mExec.Execute_Pro("Sp_SubNews_Insert", Arr_Name, Arr_Value);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

}
