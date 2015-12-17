package dat.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import uti.utility.MyConfig;
import db.define.MyTableModel;

public class AnswerObject
{
	public String MSISDN = "";
	public int PID = 0;
	public int ChargeMark = 0;
	public int WeekMark = 0;
	public int MatchID = 0;
	public String AnswerKQ = "";
	public String AnswerBT = "";
	public String AnswerGB = "";
	public String AnswerTS = "";
	public String AnswerTV = "";
	public Date LastUpdate = null;

	public int OrderID = 0;
	public Boolean IsCompute = false;
	public Date ComputeDate = null;

	/**
	 * CHo biết thuê bao này đã hủy đăng ký hay chưa
	 */
	public boolean IsDeReg = true;

	public boolean IsNull()
	{
		if (MSISDN == null || MSISDN.equalsIgnoreCase(""))
			return true;
		else return false;
	}

	public static AnswerObject Convert(MyTableModel mTable) throws Exception
	{
		try
		{
			if (mTable.GetRowCount() < 1)
				return new AnswerObject();

			AnswerObject mObject = new AnswerObject();

			mObject.MSISDN = mTable.GetValueAt(0, "MSISDN").toString();

			mObject.PID = Integer.parseInt(mTable.GetValueAt(0, "PID").toString());

			mObject.ChargeMark = Integer.parseInt(mTable.GetValueAt(0, "ChargeMark").toString());
			mObject.WeekMark = Integer.parseInt(mTable.GetValueAt(0, "WeekMark").toString());
			mObject.MatchID = Integer.parseInt(mTable.GetValueAt(0, "MatchID").toString());

			if (mTable.GetValueAt(0, "AnswerKQ") != null)
				mObject.AnswerKQ = mTable.GetValueAt(0, "AnswerKQ").toString();

			if (mTable.GetValueAt(0, "AnswerBT") != null)
				mObject.AnswerBT = mTable.GetValueAt(0, "AnswerBT").toString();

			if (mTable.GetValueAt(0, "AnswerGB") != null)
				mObject.AnswerGB = mTable.GetValueAt(0, "AnswerGB").toString();

			if (mTable.GetValueAt(0, "AnswerTS") != null)
				mObject.AnswerTS = mTable.GetValueAt(0, "AnswerTS").toString();

			if (mTable.GetValueAt(0, "AnswerTV") != null)
				mObject.AnswerTV = mTable.GetValueAt(0, "AnswerTV").toString();

			mObject.OrderID = Integer.parseInt(mTable.GetValueAt(0, "OrderID").toString());
			mObject.IsCompute = Boolean.parseBoolean(mTable.GetValueAt(0, "IsCompute").toString());

			if (mTable.GetValueAt(0, "LastUpdate") != null)
				mObject.LastUpdate = MyConfig.Get_DateFormat_InsertDB().parse(
						mTable.GetValueAt(0, "LastUpdate").toString());

			if (mTable.GetValueAt(0, "ComputeDate") != null)
				mObject.ComputeDate = MyConfig.Get_DateFormat_InsertDB().parse(
						mTable.GetValueAt(0, "ComputeDate").toString());

			if (mTable.CheckColumnsExists("IsDeReg"))
			{
				if (mTable.GetValueAt(0, "IsDeReg") == null)
				{
					mObject.IsDeReg = true;
				}
				else
				{
					mObject.IsDeReg = false;
				}
			}

			return mObject;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public static Vector<AnswerObject> ConvertToList(MyTableModel mTable) throws Exception
	{
		try
		{
			Vector<AnswerObject> mList = new Vector<AnswerObject>();
			if (mTable.GetRowCount() < 1)
				return mList;

			for (int i = 0; i < mTable.GetRowCount(); i++)
			{
				AnswerObject mObject = new AnswerObject();

				mObject.MSISDN = mTable.GetValueAt(i, "MSISDN").toString();

				mObject.PID = Integer.parseInt(mTable.GetValueAt(i, "PID").toString());

				mObject.ChargeMark = Integer.parseInt(mTable.GetValueAt(i, "ChargeMark").toString());
				mObject.WeekMark = Integer.parseInt(mTable.GetValueAt(i, "WeekMark").toString());
				mObject.MatchID = Integer.parseInt(mTable.GetValueAt(i, "MatchID").toString());
				if (mTable.GetValueAt(i, "AnswerKQ") != null)
					mObject.AnswerKQ = mTable.GetValueAt(i, "AnswerKQ").toString();

				if (mTable.GetValueAt(i, "AnswerBT") != null)
					mObject.AnswerBT = mTable.GetValueAt(i, "AnswerBT").toString();

				if (mTable.GetValueAt(i, "AnswerGB") != null)
					mObject.AnswerGB = mTable.GetValueAt(i, "AnswerGB").toString();

				if (mTable.GetValueAt(i, "AnswerTS") != null)
					mObject.AnswerTS = mTable.GetValueAt(i, "AnswerTS").toString();

				if (mTable.GetValueAt(i, "AnswerTV") != null)
					mObject.AnswerTV = mTable.GetValueAt(i, "AnswerTV").toString();

				mObject.OrderID = Integer.parseInt(mTable.GetValueAt(i, "OrderID").toString());
				mObject.IsCompute = Boolean.parseBoolean(mTable.GetValueAt(i, "IsCompute").toString());

				if (mTable.GetValueAt(i, "LastUpdate") != null)
					mObject.LastUpdate = MyConfig.Get_DateFormat_InsertDB().parse(
							mTable.GetValueAt(i, "LastUpdate").toString());

				if (mTable.GetValueAt(i, "ComputeDate") != null)
					mObject.ComputeDate = MyConfig.Get_DateFormat_InsertDB().parse(
							mTable.GetValueAt(i, "ComputeDate").toString());
				
				if (mTable.CheckColumnsExists("IsDeReg"))
				{
					if (mTable.GetValueAt(i, "IsDeReg") == null)
					{
						mObject.IsDeReg = true;
					}
					else
					{
						mObject.IsDeReg = false;
					}
				}

				mList.add(mObject);
			}
			return mList;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	/**
	 * Kiểm tra LastUpdate có phải là ngày hôm này hay không
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean LastUpdateIsToday() throws Exception
	{
		if (LastUpdate == null)
			return false;
		Calendar mCal_Current = Calendar.getInstance();
		Calendar mCal_LastUpdate = Calendar.getInstance();

		mCal_LastUpdate.setTime(LastUpdate);
		if (mCal_Current.get(Calendar.YEAR) == mCal_LastUpdate.get(Calendar.YEAR)
				&& mCal_Current.get(Calendar.MONTH) == mCal_LastUpdate.get(Calendar.MONTH)
				&& mCal_Current.get(Calendar.DATE) == mCal_LastUpdate.get(Calendar.DATE))
			return true;
		else return false;
	}

}
