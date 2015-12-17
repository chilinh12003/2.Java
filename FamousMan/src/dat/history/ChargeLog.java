package dat.history;

import java.sql.SQLException;
import java.util.Date;

import uti.utility.MyConfig;
import db.connect.MyExecuteData;
import db.connect.MyGetData;
import db.define.DBConfig;
import db.define.MyTableModel;

public class ChargeLog
{
	public MyExecuteData mExec;
	public MyGetData mGet;

	public ChargeLog(DBConfig mConfigObj) throws Exception
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
	 *            Type = 1: lấy thông tin chi tiết 1 record Para_1 = ChargeLogID
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
			String Arr_Name[] = { "Type", "Para_1" };
			String Arr_Value[] = { Integer.toString(Type), Para_1 };

			return mGet.GetData_Pro("Sp_ChargeLog_Select", Arr_Name, Arr_Value);
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
	 *            : Cách thức lấy dữ liệu
	 *            <p>
	 *            Type = 0: Lay du lieu mẫu
	 *            </p>
	 * @param Para_1
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

			return mGet.GetData_Pro("Sp_ChargeLog_Select", Arr_Name, Arr_Value);
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

	public boolean Insert(int Type, String XMLContent) throws Exception, SQLException
	{
		try
		{
			String[] Arr_Name = { "Type", "XMLContent" };
			String[] Arr_Value = { Integer.toString(Type), XMLContent };
			return mExec.Execute_Pro("Sp_ChargeLog_Insert", Arr_Name, Arr_Value);
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

	public Integer Total(Integer Type, Integer BeginRow, Integer EndRow, String MSISDN, Integer PID,  Integer ChannelTypeID,
			Date BeginDate, Date EndDate) throws Exception
	{
		Integer SelectType = 0;
		String Arr_Name[] = { "Type", "BeginRow", "EndRow", "SearchContent", "PID", "ChargeTypeID","ChargeStatusID", "ChannelTypeID", "BeginDate", "EndDate","SelectType","OrderBy", "IsTotalRow" };
		String Arr_Value[] = { Type.toString(), BeginRow.toString(), EndRow.toString(), MSISDN, PID.toString(), "0","0", ChannelTypeID.toString(),
				MyConfig.Get_DateFormat_InsertDB().format(BeginDate), MyConfig.Get_DateFormat_InsertDB().format(EndDate),SelectType.toString(),"ChargeDate DESC", "1" };
		
		MyTableModel mTable = mGet.GetData_Pro("Sp_ChargeLog_Search", Arr_Name, Arr_Value);
		if (mTable != null && mTable.GetRowCount() > 0)
			return Integer.parseInt(mTable.GetValueAt(0, 0).toString());
		return 0;
	}

	public MyTableModel Search(Integer Type, Integer BeginRow, Integer EndRow, String MSISDN, Integer PID, Integer ChannelTypeID,
			Date BeginDate, Date EndDate) throws Exception
	{
		Integer SelectType = 0;
		String Arr_Name[] = { "Type", "BeginRow", "EndRow", "SearchContent", "PID","ChargeTypeID","ChargeStatusID", "ChannelTypeID", "BeginDate", "EndDate","SelectType","OrderBy", "IsTotalRow" };
		String Arr_Value[] = { Type.toString(), BeginRow.toString(), EndRow.toString(), MSISDN, PID.toString(), "0","0", ChannelTypeID.toString(),
				MyConfig.Get_DateFormat_InsertDB().format(BeginDate), MyConfig.Get_DateFormat_InsertDB().format(EndDate),SelectType.toString(),"ChargeDate DESC", "0" };
		MyTableModel mTable = mGet.GetData_Pro("Sp_ChargeLog_Search", Arr_Name, Arr_Value);

		return mTable;
	}
}
