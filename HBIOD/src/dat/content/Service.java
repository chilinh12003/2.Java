package dat.content;

import java.sql.SQLException;
import java.util.Vector;

import uti.utility.MyText;
import db.connect.MyExecuteData;
import db.connect.MyGetData;
import db.define.DBConfig;
import db.define.MyTableModel;

public class Service
{
	/**
	 * Kiểu push MT cho dịch vụ
	 * @author Administrator
	 *
	 */
	public enum PushType
	{
		NoThing(0),
		/**
		 * 
		 * Push theo thời gian định sẵn
		 */
		PushTime(1),
		/**
		 * Push theo độ ưu tiên
		 */
		Priority(2),
		
		/**
		 * Push theo dạng ngẫu nhiên (random)
		 */
		Random(3)
		;

		private int value;

		private PushType(int value)
		{
			this.value = value;
		}

		public int GetValue()
		{
			return this.value;
		}

		public static PushType FromInt(int iValue)
		{
			for (PushType type : PushType.values())
			{
				if (type.GetValue() == iValue)
					return type;
			}
			return NoThing;
		}
	}

	
	public enum ServiceType
	{
		NoThing(0),
		/**
		 * Push tin ngay nếu có tin trong table news
		 */
		Normal(1),
		/**
		 * Dịch vụ theo dạng Cung Hoàng đạo
		 */
		Zodiac(2),
		
		/**
		 * Dịch vụ theo dạng Lịch vạn sự
		 */
		Calendar(3)
		;

		private int value;

		private ServiceType(int value)
		{
			this.value = value;
		}

		public int GetValue()
		{
			return this.value;
		}

		public static ServiceType FromInt(int iValue)
		{
			for (ServiceType type : ServiceType.values())
			{
				if (type.GetValue() == iValue)
					return type;
			}
			return NoThing;
		}
	}

	public MyExecuteData mExec;
	public MyGetData mGet;

	public Service(DBConfig mConfigObj) throws Exception
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
	 *            Type = 2: Lất tất cả đã được kích thoạt <br/>
	 *            Type = 3: Lất tất cả
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

			return mGet.GetData_Pro("Sp_Service_Select", Arr_Name, Arr_Value);
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

	public MyTableModel Select(int Type, String Para_1) throws Exception, SQLException
	{
		try
		{
			String Arr_Name[] = {"Type", "Para_1"};
			String Arr_Value[] = {Integer.toString(Type), Para_1};

			return mGet.GetData_Pro("Sp_Service_Select", Arr_Name, Arr_Value);
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
