package dat.content;

import java.sql.SQLException;
import db.connect.MyExecuteData;
import db.connect.MyGetData;
import db.define.DBConfig;
import db.define.MyTableModel;

public class Zodiac
{
	public enum ZodiacList
	{
		Nothing(0),
		BachDuong(1),
		KimNguu(2),
		SongTu(3),
		Cugiai(4),
		Sutu(5),
		Xunu(6),
		HoCap(7),
		NhanMa(8),
		MaKet(9),
		BaoBinh(10),
		SongNgu(11),
		ThienBinh(12);

		private int value;

		private ZodiacList(int value)
		{
			this.value = value;
		}

		public Integer GetValue()
		{
			return this.value;
		}

		public static ZodiacList FromInt(int iValue)
		{
			for (ZodiacList type : ZodiacList.values())
			{
				if (type.GetValue() == iValue)
					return type;
			}
			return Nothing;
		}
	}
	
	
	public MyExecuteData mExec;
	public MyGetData mGet;

	public Zodiac(DBConfig mConfigObj) throws Exception
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
	 *            Type = 2: Lất tất cả <br/>
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

			return mGet.GetData_Pro("Sp_Zodiac_Select", Arr_Name, Arr_Value);
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

			return mGet.GetData_Pro("Sp_Zodiac_Select", Arr_Name, Arr_Value);
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
