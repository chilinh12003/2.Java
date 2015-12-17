package dat.history;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import uti.utility.MyConfig;
import db.define.MyDataRow;
import db.define.MyTableModel;

public class SuggestCountObject implements Cloneable
{

	public int SuggestID = 0;
	public int OrderNumber = 0;
	public int QuestionID = 0;
	public int BuyCount = 0;
	public int CorrectCount = 0;
	public int IncorrectCount = 0;
	public Date PlayDate = null;
	public Date LastUpdate = null;

	public boolean IsNull()
	{
		if (SuggestID == 0)
			return true;
		else return false;
	}

	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}

	/**
	 * Kiểm tra LastUpdate có phải là ngày hôm này hay không
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean IsToday() throws Exception
	{
		if (PlayDate == null)
			return false;
		Calendar mCal_Current = Calendar.getInstance();
		Calendar mCal_LastUpdate = Calendar.getInstance();

		mCal_LastUpdate.setTime(PlayDate);
		if (mCal_Current.get(Calendar.YEAR) == mCal_LastUpdate.get(Calendar.YEAR)
				&& mCal_Current.get(Calendar.MONTH) == mCal_LastUpdate.get(Calendar.MONTH)
				&& mCal_Current.get(Calendar.DATE) == mCal_LastUpdate.get(Calendar.DATE))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static SuggestCountObject Convert(MyTableModel mTable) throws Exception
	{
		try
		{
			if (mTable.GetRowCount() < 1)
				return new SuggestCountObject();

			SuggestCountObject mObject = new SuggestCountObject();

			mObject.SuggestID = Integer.parseInt(mTable.GetValueAt(0, "SuggestID").toString());
			mObject.OrderNumber = Integer.parseInt(mTable.GetValueAt(0, "OrderNumber").toString());
			mObject.QuestionID = Integer.parseInt(mTable.GetValueAt(0, "QuestionID").toString());
			mObject.BuyCount = Integer.parseInt(mTable.GetValueAt(0, "BuyCount").toString());
			mObject.CorrectCount = Integer.parseInt(mTable.GetValueAt(0, "CorrectCount").toString());

			mObject.IncorrectCount = Integer.parseInt(mTable.GetValueAt(0, "IncorrectCount").toString());
			mObject.PlayDate = MyConfig.Get_DateFormat_InsertDB().parse(mTable.GetValueAt(0, "PlayDate").toString());

			mObject.LastUpdate = MyConfig.Get_DateFormat_InsertDB()
					.parse(mTable.GetValueAt(0, "LastUpdate").toString());

			return mObject;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public static Vector<SuggestCountObject> ConvertToList(MyTableModel mTable) throws Exception
	{
		try
		{
			Vector<SuggestCountObject> mList = new Vector<SuggestCountObject>();
			if (mTable.GetRowCount() < 1)
				return mList;

			for (int i = 0; i < mTable.GetRowCount(); i++)
			{
				SuggestCountObject mObject = new SuggestCountObject();

				mObject.SuggestID = Integer.parseInt(mTable.GetValueAt(i, "SuggestID").toString());
				mObject.OrderNumber = Integer.parseInt(mTable.GetValueAt(i, "OrderNumber").toString());
				mObject.QuestionID = Integer.parseInt(mTable.GetValueAt(i, "QuestionID").toString());
				mObject.BuyCount = Integer.parseInt(mTable.GetValueAt(i, "BuyCount").toString());
				mObject.CorrectCount = Integer.parseInt(mTable.GetValueAt(i, "CorrectCount").toString());

				mObject.IncorrectCount = Integer.parseInt(mTable.GetValueAt(i, "IncorrectCount").toString());
				mObject.PlayDate = MyConfig.Get_DateFormat_InsertDB()
						.parse(mTable.GetValueAt(i, "PlayDate").toString());

				mObject.LastUpdate = MyConfig.Get_DateFormat_InsertDB().parse(
						mTable.GetValueAt(i, "LastUpdate").toString());

				mList.add(mObject);
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
		mRow.SetValueCell("SuggestID", SuggestID);
		mRow.SetValueCell("OrderNumber", OrderNumber);
		mRow.SetValueCell("QuestionID", QuestionID);
		mRow.SetValueCell("BuyCount", BuyCount);
		mRow.SetValueCell("CorrectCount", CorrectCount);
		mRow.SetValueCell("IncorrectCount", IncorrectCount);
		mRow.SetValueCell("PlayDate", MyConfig.Get_DateFormat_InsertDB().format(PlayDate.getTime()));
		mRow.SetValueCell("LastUpdate", MyConfig.Get_DateFormat_InsertDB().format(LastUpdate.getTime()));

		mTable.AddNewRow(mRow);
		return mTable;
	}
}
