package dat.history;

import java.util.Date;
import java.util.Vector;

import uti.utility.MyConfig;
import db.define.MyDataRow;
import db.define.MyTableModel;

public class WinnerWeekObject
{
	public int WeekID = 0; 
	public String MSISDN ="";
	public String Address ="";
	public String WinnerName ="";
	public String Prize="";
	public int WeekOfYear=0;
	public Date BeginSession=null;
	public Date EndSession=null;
	public int WinnerCount=0;
	/**
	 * Tổn thời gian trả lời trong tuần (được tính bằng miligiây)
	 */
	public long TotalTime=0;
	public boolean IsActive =false;
	
	public boolean IsNull()
	{
		if (MSISDN == null || MSISDN.equalsIgnoreCase(""))
			return true;
		else return false;
	}

	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}
	
	public static WinnerWeekObject Convert(MyTableModel mTable, int RowIndex) throws Exception
	{
		try
		{
			if (mTable.GetRowCount() < 1)
				return new WinnerWeekObject();

			WinnerWeekObject mObject = new WinnerWeekObject();

			mObject.WeekID = Integer.parseInt(mTable.GetValueAt(RowIndex, "WeekID").toString());

			mObject.MSISDN = mTable.GetValueAt(RowIndex, "MSISDN").toString();
			mObject.Address = mTable.GetValueAt(RowIndex, "Address").toString();
			mObject.WinnerName = mTable.GetValueAt(RowIndex, "WinnerName").toString();
			mObject.Prize = mTable.GetValueAt(RowIndex, "MSISDN").toString();

			mObject.WeekOfYear = Integer.parseInt(mTable.GetValueAt(RowIndex, "WeekOfYear").toString());
			mObject.WinnerCount = Integer.parseInt(mTable.GetValueAt(RowIndex, "WinnerCount").toString());
			mObject.TotalTime = Long.parseLong(mTable.GetValueAt(RowIndex, "TotalTime").toString());

			if (mTable.GetValueAt(RowIndex, "BeginSession") != null)
				mObject.BeginSession = MyConfig.Get_DateFormat_InsertDB().parse(
						mTable.GetValueAt(RowIndex, "BeginSession").toString());

			if (mTable.GetValueAt(RowIndex, "EndSession") != null)
				mObject.EndSession = MyConfig.Get_DateFormat_InsertDB().parse(
						mTable.GetValueAt(RowIndex, "EndSession").toString());
			
			mObject.IsActive = Boolean.parseBoolean(mTable.GetValueAt(RowIndex, "IsActive").toString());
			
			return mObject;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public static Vector<WinnerWeekObject> ConvertToList(MyTableModel mTable) throws Exception
	{
		try
		{
			Vector<WinnerWeekObject> mList = new Vector<WinnerWeekObject>();
			if (mTable.GetRowCount() < 1)
				return mList;

			for (int i = 0; i < mTable.GetRowCount(); i++)
			{
				mList.add(Convert(mTable, i));
			}
			return mList;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public MyTableModel AddNewRow(MyTableModel mTable) throws Exception
	{
		if (mTable == null)
			return null;

		if (IsNull())
			return mTable;

		MyDataRow mRow = mTable.CreateNewRow();
		mRow.SetValueCell("WeekID", WeekID);
		mRow.SetValueCell("MSISDN", MSISDN);
		mRow.SetValueCell("Address", Address);
		mRow.SetValueCell("WinnerName", WinnerName);
		mRow.SetValueCell("Prize", Prize);
		mRow.SetValueCell("WeekOfYear", WeekOfYear);
		if (BeginSession != null)
			mRow.SetValueCell("BeginSession", MyConfig.Get_DateFormat_InsertDB().format(BeginSession.getTime()));
		if (EndSession != null)
			mRow.SetValueCell("EndSession", MyConfig.Get_DateFormat_InsertDB().format(EndSession.getTime()));
		
		mRow.SetValueCell("WinnerCount", WinnerCount);
		mRow.SetValueCell("TotalTime", TotalTime);
		mRow.SetValueCell("IsActive", IsActive);
		mTable.AddNewRow(mRow);
		return mTable;
	}
	
	public String GetLog() throws Exception
	{

		String strBeginSession = "";
		String strEndSession = "";
		if (BeginSession != null)
			strBeginSession = MyConfig.Get_DateFormat_InsertDB().format(BeginSession);
		if (EndSession != null)
			strEndSession = MyConfig.Get_DateFormat_InsertDB().format(EndSession);
		
		String Format = "WinnerWeek --> WeekID:%s | MSISDN:%s | WeekOfYear:%s | BeginSession:%s | EndSession%s | WinnerCount:%s | TotalTime:%s";
		return String.format(Format, Integer.toString(WeekID), MSISDN, Integer.toString(WeekOfYear), strBeginSession, strEndSession, Integer.toString(WinnerCount), Long.toString(TotalTime));
	}

}
