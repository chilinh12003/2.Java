package dat.history;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import uti.utility.MyConfig;
import dat.history.Play.PlayType;
import dat.history.Play.Status;
import db.define.MyDataRow;
import db.define.MyTableModel;

public class PlayObject implements Cloneable
{
	public long LogID = 0;
	public String MSISDN = "";
	public int QuestionID = 0;
	public int SuggestID = 0;
	public Date ReceiveDate = null;

	public PlayType mPlayType = PlayType.Nothing;
	public int OrderNumber = 0;
	public String UserAnswer = "";
	public Play.Status mStatus = Status.Nothing;
	public int PID = 0;
	
	public int WeekMark = 0;
	public int DayMark = 0;
	public int AddMark = 0;
	public int ChargeMark = 0;
	public int BuyMark = 0;
	public int AnswerMark = 0;
	public int AnswerRightCount =0;
	public int BuySuggestCount =0;
	

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

	/**
	 * Kiểm tra ReceiveDate có phải là ngày hôm này hay không
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean ReceiveIsToday() throws Exception
	{
		if (ReceiveDate == null)
			return false;
		Calendar mCal_Current = Calendar.getInstance();
		Calendar mCal_LastUpdate = Calendar.getInstance();

		mCal_LastUpdate.setTime(ReceiveDate);
		if (mCal_Current.get(Calendar.YEAR) == mCal_LastUpdate.get(Calendar.YEAR)
				&& mCal_Current.get(Calendar.MONTH) == mCal_LastUpdate.get(Calendar.MONTH)
				&& mCal_Current.get(Calendar.DATE) == mCal_LastUpdate.get(Calendar.DATE))
			return true;
		else return false;
	}

	public static PlayObject Convert(MyTableModel mTable) throws Exception
	{
		try
		{
			if (mTable.GetRowCount() < 1)
				return new PlayObject();

			PlayObject mObject = new PlayObject();

			mObject.LogID = Integer.parseInt(mTable.GetValueAt(0, "LogID").toString());

			mObject.MSISDN = mTable.GetValueAt(0, "MSISDN").toString();

			mObject.QuestionID = Integer.parseInt(mTable.GetValueAt(0, "QuestionID").toString());
			mObject.SuggestID = Integer.parseInt(mTable.GetValueAt(0, "SuggestID").toString());

			if (mTable.GetValueAt(0, "ReceiveDate") != null)
				mObject.ReceiveDate = MyConfig.Get_DateFormat_InsertDB().parse(
						mTable.GetValueAt(0, "ReceiveDate").toString());

			mObject.mPlayType = PlayType.FromInt(Integer.parseInt(mTable.GetValueAt(0, "PlayTypeID").toString()));

			mObject.OrderNumber = Integer.parseInt(mTable.GetValueAt(0, "OrderNumber").toString());

			if (mTable.GetValueAt(0, "UserAnswer") != null)
				mObject.UserAnswer = mTable.GetValueAt(0, "UserAnswer").toString();

			mObject.mStatus = Play.Status.FromInt(Integer.parseInt(mTable.GetValueAt(0, "StatusID").toString()));

			mObject.PID = Integer.parseInt(mTable.GetValueAt(0, "PID").toString());
			
			mObject.WeekMark = Integer.parseInt(mTable.GetValueAt(0, "WeekMark").toString());
			mObject.DayMark = Integer.parseInt(mTable.GetValueAt(0, "DayMark").toString());
			mObject.AddMark = Integer.parseInt(mTable.GetValueAt(0, "AddMark").toString());
			mObject.ChargeMark = Integer.parseInt(mTable.GetValueAt(0, "ChargeMark").toString());
			mObject.BuyMark = Integer.parseInt(mTable.GetValueAt(0, "BuyMark").toString());
			mObject.AnswerMark = Integer.parseInt(mTable.GetValueAt(0, "AnswerMark").toString());
			mObject.AnswerRightCount = Integer.parseInt(mTable.GetValueAt(0, "AnswerRightCount").toString());
			mObject.BuySuggestCount = Integer.parseInt(mTable.GetValueAt(0, "BuySuggestCount").toString());
			
			return mObject;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public static Vector<PlayObject> ConvertToList(MyTableModel mTable) throws Exception
	{
		try
		{
			Vector<PlayObject> mList = new Vector<PlayObject>();
			if (mTable.GetRowCount() < 1)
				return mList;

			for (int i = 0; i < mTable.GetRowCount(); i++)
			{
				PlayObject mObject = new PlayObject();

				mObject.LogID = Integer.parseInt(mTable.GetValueAt(i, "LogID").toString());

				mObject.MSISDN = mTable.GetValueAt(i, "MSISDN").toString();

				mObject.QuestionID = Integer.parseInt(mTable.GetValueAt(i, "QuestionID").toString());
				mObject.SuggestID = Integer.parseInt(mTable.GetValueAt(i, "SuggestID").toString());

				if (mTable.GetValueAt(i, "ReceiveDate") != null)
					mObject.ReceiveDate = MyConfig.Get_DateFormat_InsertDB().parse(
							mTable.GetValueAt(i, "ReceiveDate").toString());

				mObject.mPlayType = PlayType.FromInt(Integer.parseInt(mTable.GetValueAt(i, "PlayTypeID").toString()));

				mObject.OrderNumber = Integer.parseInt(mTable.GetValueAt(i, "OrderNumber").toString());

				if (mTable.GetValueAt(i, "UserAnswer") != null)
					mObject.UserAnswer = mTable.GetValueAt(i, "UserAnswer").toString();

				mObject.mStatus = Play.Status.FromInt(Integer.parseInt(mTable.GetValueAt(i, "StatusID").toString()));

				mObject.WeekMark = Integer.parseInt(mTable.GetValueAt(i, "WeekMark").toString());
				mObject.DayMark = Integer.parseInt(mTable.GetValueAt(i, "DayMark").toString());
				mObject.AddMark = Integer.parseInt(mTable.GetValueAt(i, "AddMark").toString());
				mObject.ChargeMark = Integer.parseInt(mTable.GetValueAt(i, "ChargeMark").toString());
				mObject.BuyMark = Integer.parseInt(mTable.GetValueAt(i, "BuyMark").toString());
				mObject.AnswerMark = Integer.parseInt(mTable.GetValueAt(i, "AnswerMark").toString());
				mObject.AnswerRightCount = Integer.parseInt(mTable.GetValueAt(i, "AnswerRightCount").toString());
				mObject.BuySuggestCount = Integer.parseInt(mTable.GetValueAt(i, "BuySuggestCount").toString());
				
				
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

		mRow.SetValueCell("MSISDN", MSISDN);
		mRow.SetValueCell("QuestionID", QuestionID);
		mRow.SetValueCell("SuggestID", SuggestID);
		mRow.SetValueCell("PlayTypeID", mPlayType.GetValue());
		if (ReceiveDate != null)
			mRow.SetValueCell("ReceiveDate", MyConfig.Get_DateFormat_InsertDB().format(ReceiveDate.getTime()));
		mRow.SetValueCell("OrderNumber", OrderNumber);
		if (UserAnswer != null & !UserAnswer.equalsIgnoreCase(""))
			mRow.SetValueCell("UserAnswer", UserAnswer);
		mRow.SetValueCell("StatusID", mStatus.GetValue());
		mRow.SetValueCell("PID", PID);

		mRow.SetValueCell("WeekMark", WeekMark);
		mRow.SetValueCell("DayMark", DayMark);
		mRow.SetValueCell("AddMark", AddMark);
		mRow.SetValueCell("ChargeMark", ChargeMark);
		mRow.SetValueCell("BuyMark", BuyMark);
		mRow.SetValueCell("AnswerMark", AnswerMark);
		mRow.SetValueCell("AnswerRightCount", AnswerRightCount);
		mRow.SetValueCell("BuySuggestCount", BuySuggestCount);	

		
		mTable.AddNewRow(mRow);
		return mTable;
	}

	public String GetLog() throws Exception
	{

		String Format = "LogID:%s | MSISDN:%s | QuestionID:%s | SuggestID:%s | ReceiveDate%s | PlayType:%s | OrderNumber:%s | UserAnswer:%s | Status:%s | PID:%s";
		return String.format(Format, Long.toString(LogID), MSISDN, QuestionID, SuggestID, MyConfig
				.Get_DateFormat_InsertDB().format(ReceiveDate), mPlayType.toString(), OrderNumber, UserAnswer, mStatus
				.toString(), PID);
	}
}
