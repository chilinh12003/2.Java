package dat.content;


import java.util.Date;
import java.util.Vector;

import uti.utility.MyConfig;
import db.define.MyTableModel;

public class SuggestObject
{
	public int SuggestID = 0;
	public int QuestionID =0;
	public String MT="";
	public int OrderNumber = 0;
	public String NotifyMT="";
	public boolean IsActive = false;
	public Date CreateDate= null;
	
	public boolean IsNull()
	{
		if (SuggestID == 0 || MT.equalsIgnoreCase("")) return true;
		else return false;
	}

	
	
	public static SuggestObject Convert(MyTableModel mTable) throws Exception
	{
		try
		{
			if (mTable.GetRowCount() < 1) return new SuggestObject();

			SuggestObject mObject = new SuggestObject();

			mObject.SuggestID = Integer.parseInt(mTable.GetValueAt(0, "SuggestID").toString());
			mObject.QuestionID = Integer.parseInt(mTable.GetValueAt(0, "QuestionID").toString());

			mObject.MT = mTable.GetValueAt(0, "MT").toString();

			mObject.OrderNumber = Integer.parseInt(mTable.GetValueAt(0, "OrderNumber").toString());
			
			if(mTable.GetValueAt(0, "NotifyMT") != null)
				mObject.NotifyMT = mTable.GetValueAt(0, "NotifyMT").toString();

			if (mTable.GetValueAt(0, "CreateDate") != null)
				mObject.CreateDate = MyConfig.Get_DateFormat_InsertDB().parse(
						mTable.GetValueAt(0, "CreateDate").toString());

			mObject.IsActive = Boolean.parseBoolean(mTable.GetValueAt(0, "IsActive").toString());

			return mObject;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}
	
	public static Vector<SuggestObject> ConvertToList(MyTableModel mTable) throws Exception
	{
		try
		{
			Vector<SuggestObject> mList = new Vector<SuggestObject>();
			if (mTable.GetRowCount() < 1) return mList;

			for (int i = 0; i < mTable.GetRowCount(); i++)
			{
				SuggestObject mObject = new SuggestObject();

				mObject.SuggestID = Integer.parseInt(mTable.GetValueAt(i, "SuggestID").toString());
				mObject.QuestionID = Integer.parseInt(mTable.GetValueAt(i, "QuestionID").toString());

				mObject.MT = mTable.GetValueAt(i, "MT").toString();

				mObject.OrderNumber = Integer.parseInt(mTable.GetValueAt(i, "OrderNumber").toString());
				
				if(mTable.GetValueAt(i, "NotifyMT") != null)
					mObject.NotifyMT = mTable.GetValueAt(i, "NotifyMT").toString();

				if (mTable.GetValueAt(i, "CreateDate") != null)
					mObject.CreateDate = MyConfig.Get_DateFormat_InsertDB().parse(
							mTable.GetValueAt(i, "CreateDate").toString());

				mObject.IsActive = Boolean.parseBoolean(mTable.GetValueAt(i, "IsActive").toString());

				mList.add(mObject);

			}
			return mList;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

}
