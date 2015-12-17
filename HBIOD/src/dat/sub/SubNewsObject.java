package dat.sub;

import java.util.Date;
import java.util.Vector;

import uti.utility.MyConfig;
import dat.sub.SubNews.Status;
import db.define.MyDataRow;
import db.define.MyTableModel;

public class SubNewsObject
{
	/**
	 * Key này sẽ được gửi xuống khách hàng để confirm
	 */
	public String NewsKey= "";
	public String MSISDN = "";
	public int NewsID=0;
	public int PID=0;
	public Date RequestDate = null;
	public Date	ExpireDate = null;
	public int ServiceID =0;
	public Status mStatus = Status.Nothing;
	public Date ConfirmDate = null;
	public Date SendDate = null;
	public Date NotifyDate = null;
	
	public boolean IsNull()
	{
		if (MSISDN == null || MSISDN.equalsIgnoreCase("") || NewsID == 0) return true;
		else return false;
	}
	
	public static Vector<SubNewsObject> ConvertToList(MyTableModel mTable) throws Exception
	{
		try
		{
			Vector<SubNewsObject> mList = new Vector<SubNewsObject>();
			if (mTable.GetRowCount() < 1) return mList;

			for (int i = 0; i < mTable.GetRowCount(); i++)
			{
				SubNewsObject mObject = new SubNewsObject();

				mObject.NewsKey = mTable.GetValueAt(i, "NewsKey").toString();
				
				mObject.ServiceID =Integer.parseInt(mTable.GetValueAt(i, "ServiceID").toString());
				mObject.MSISDN = mTable.GetValueAt(i, "MSISDN").toString();
				mObject.NewsID = Integer.parseInt(mTable.GetValueAt(i, "NewsID").toString());
				mObject.RequestDate = MyConfig.Get_DateFormat_InsertDB().parse(mTable.GetValueAt(i, "RequestDate").toString());
				mObject.ExpireDate = MyConfig.Get_DateFormat_InsertDB().parse(mTable.GetValueAt(i, "ExpireDate").toString());
				mObject.PID = Integer.parseInt(mTable.GetValueAt(i, "PID").toString());
				mObject.mStatus = SubNews.Status.FromInt(Integer.parseInt(mTable.GetValueAt(i, "StatusID").toString()));

				if (mTable.GetValueAt(i, "ConfirmDate") != null)
					mObject.ConfirmDate = MyConfig.Get_DateFormat_InsertDB().parse(
							mTable.GetValueAt(i, "ConfirmDate").toString());
				
				if (mTable.GetValueAt(i, "SendDate") != null)
					mObject.SendDate = MyConfig.Get_DateFormat_InsertDB().parse(
							mTable.GetValueAt(i, "SendDate").toString());
				
				if (mTable.GetValueAt(i, "NotifyDate") != null)
					mObject.NotifyDate = MyConfig.Get_DateFormat_InsertDB().parse(
							mTable.GetValueAt(i, "NotifyDate").toString());
				
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

		mRow.SetValueCell("NewsKey", NewsKey);
		
		mRow.SetValueCell("MSISDN", MSISDN);
		mRow.SetValueCell("PID", PID);
		mRow.SetValueCell("RequestDate", MyConfig.Get_DateFormat_InsertDB().format(RequestDate));
		mRow.SetValueCell("ExpireDate", MyConfig.Get_DateFormat_InsertDB().format(ExpireDate));
		mRow.SetValueCell("NewsID", NewsID);
		mRow.SetValueCell("ServiceID", ServiceID);
		mRow.SetValueCell("StatusID", mStatus.GetValue());
	
		if (ConfirmDate != null)
			mRow.SetValueCell("ConfirmDate", MyConfig.Get_DateFormat_InsertDB().format(ConfirmDate));
		
		if (SendDate != null)
			mRow.SetValueCell("SendDate",
					MyConfig.Get_DateFormat_InsertDB().format(SendDate.getTime()));
		
		if (NotifyDate != null)
			mRow.SetValueCell("NotifyDate", MyConfig.Get_DateFormat_InsertDB().format(NotifyDate));
		
		mTable.AddNewRow(mRow);
		return mTable;
	}
}
