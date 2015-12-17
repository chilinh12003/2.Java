package dat.content;

import java.util.Calendar;
import java.util.Date;

import uti.utility.MyConfig;
import db.define.MyTableModel;

public class QuestionObject
{
	public int QuestionID = 0;
	public String QuestionName = "";
	public int StatusID = 0;
	public String StatusName = "";
	public Date CreateDate = null;
	public String RightAnswer = "";
	public Date PlayDate = null;

	/**
	 * Tên của giả thưởng
	 */
	public String Prize = "";
	
	/**
	 * Giá trị giải thưởng
	 */
	public String Price = "";
	
	
	public boolean IsNull()
	{
		if (QuestionID == 0) return true;
		else return false;
	}
	
	public String Get_PlayDate()
	{
		String strDate = "";
		try
		{
			strDate = MyConfig.Get_DateFormat_VNShortSlash().format(PlayDate);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		return strDate;
	}
	
	public String Get_NextDate()
	{
		Calendar mCal_NextDate = Calendar.getInstance();
		mCal_NextDate.add(Calendar.DATE, 1);
		
		String strDate = "";
		try
		{
			strDate = MyConfig.Get_DateFormat_VNShortSlash().format(mCal_NextDate.getTime());
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		return strDate;
	}
	public boolean CheckPlayDate(Calendar mCal_Current) throws Exception
	{
		if (PlayDate == null) return false;
		Calendar mCal_PlayDate = Calendar.getInstance();

		mCal_PlayDate.setTime(PlayDate);
		if (mCal_Current.get(Calendar.YEAR) == mCal_PlayDate.get(Calendar.YEAR)
				&& mCal_Current.get(Calendar.MONTH) == mCal_PlayDate.get(Calendar.MONTH)
				&& mCal_Current.get(Calendar.DATE) == mCal_PlayDate.get(Calendar.DATE)) return true;
		else return false;
	}
	
	public static QuestionObject Convert(MyTableModel mTable) throws Exception
	{
		try
		{
			if (mTable.GetRowCount() < 1) return new QuestionObject();

			QuestionObject mObject = new QuestionObject();

			mObject.QuestionID = Integer.parseInt(mTable.GetValueAt(0, "QuestionID").toString());

			mObject.QuestionName = mTable.GetValueAt(0, "QuestionName").toString();

			mObject.StatusID = Integer.parseInt(mTable.GetValueAt(0, "StatusID").toString());
			mObject.StatusName = mTable.GetValueAt(0, "StatusName").toString();

			if (mTable.GetValueAt(0, "CreateDate") != null)
				mObject.CreateDate = MyConfig.Get_DateFormat_InsertDB().parse(
						mTable.GetValueAt(0, "CreateDate").toString());

			mObject.RightAnswer = mTable.GetValueAt(0, "RightAnswer").toString();

			if (mTable.GetValueAt(0, "PlayDate") != null)
				mObject.PlayDate = MyConfig.Get_DateFormat_InsertDB().parse(
						mTable.GetValueAt(0, "PlayDate").toString());

			if(mTable.GetValueAt(0, "Prize") != null)
				mObject.Prize = mTable.GetValueAt(0, "Prize").toString();
			
			if(mTable.GetValueAt(0, "Price") != null)
				mObject.Price = mTable.GetValueAt(0, "Price").toString();
			
			return mObject;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public String GetLog() throws Exception
	{
		String Format = "QuestionID:%s | QuestionName:%s | StatusID:%s | StatusName:%s | CreateDate%s | RightAnswer:%s | PlayDate:%s | Prize:%s | Price:%s";
		return String.format(Format, QuestionID, QuestionName, StatusID, StatusName, 
				MyConfig.Get_DateFormat_InsertDB().format(CreateDate), RightAnswer, 
				MyConfig.Get_DateFormat_InsertDB().format(PlayDate), Prize, Price);
	}
}
