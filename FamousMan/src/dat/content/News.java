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
		NoThing(0), New(1), Sending(2), Complete(3), 
		
		/*
		 * Chờ tin phát sinh từ hệ thống và chờ duyện
		 */
		Waiting(4);

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
			return NoThing;
		}
	}

	public enum NewsType
	{
		NoThing(0), Push(1), Reminder(2), Winner(3);

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
	 *            <p>
	 *            Type = 7: Lấy danh sách tin đã được kích hoạt và chưa gửi cho
	 *            1 dịch vụ, và sắp xếp theo mới đến cũ
	 *            </p>
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
	 * <p>Type = 3: Lấy 1 tin mới nhất theo (Para_1 = StatisID, Para_2 = NewsTypeID, Para_3 = BeginDate, Para_4 = EndDate) </p>
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
