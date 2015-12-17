package dat.service;

import java.sql.SQLException;

import db.connect.MyExecuteData;
import db.connect.MyGetData;
import db.define.DBConfig;
import db.define.MyTableModel;

public class News
{
	public enum Status
	{
		NoThing(0), New(1), Sending(2), Complete(3);

		private Integer value;

		private Status(int value)
		{
			this.value = value;
		}

		public Integer GetValue()
		{
			return this.value;
		}

		public static Status FromInt(Integer iValue)
		{
			for (Status type : Status.values())
			{
				if (type.GetValue() == iValue)
					return type;
			}
			return NoThing;
		}
	}

	public enum NewsType
	{
		NoThing(0), Push(1), Reminder(2);

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
			return NoThing;
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
	
	/**
	 * 
	 * @param Type
	 * 			<p> Type = 0: Lấy dữ liệu mâu </p>
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public MyTableModel Select(int Type) throws Exception, SQLException
	{
		try
		{
			String Arr_Name[] = { "Type" };
			String Arr_Value[] = { Integer.toString(Type) };

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
	 * <p>Type = 2: Lấy 1 tin mới nhất theo (Para_1 = StatisID, Para_2 = NewsTypeID, Para_3 = BeginDate, Para_4 = EndDate) </p>
	 * @param Para_1
	 * @param Para_2
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public MyTableModel Select(int Type, String Para_1,String Para_2, String Para_3, String Para_4) throws Exception, SQLException
	{
		try
		{
			String Arr_Name[] = { "Type", "Para_1","Para_2","Para_3","Para_4"};
			String Arr_Value[] = { Integer.toString(Type), Para_1,Para_2,Para_3,Para_4 };
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
	 * <p>Type = 3: lấy 1 tin mới nhất theo newsType (Para_1 = NewsTypeID, Para_2 = BeginTime, Para_3 = EndTime )</p>
	 * @param Para_1
	 * @param Para_2
	 * @param Para_3
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public MyTableModel Select(int Type, String Para_1,String Para_2, String Para_3) throws Exception, SQLException
	{
		try
		{
			String Arr_Name[] = { "Type", "Para_1","Para_2","Para_3"};
			String Arr_Value[] = { Integer.toString(Type), Para_1,Para_2,Para_3 };
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
	 * <p>Type = 1: Update lai Status</p>
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
			return mExec.Execute_Pro("Sp_News_Update", Arr_Name, Arr_Value);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}	
	
}
