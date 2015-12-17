package dat.history;
import java.util.Calendar;
import java.util.Date;

import uti.utility.MyConfig;
import uti.utility.MyConfig.ChannelType;
import uti.utility.VNPApplication;
import dat.content.DefineMT;
import dat.content.DefineMT.MTType;
import db.define.MyDataRow;
import db.define.MyTableModel;

public class MOObject
{
	public long LogID = 0l;
	public int ServiceID = 0;
	public String MSISDN = "";
	public Date LogDate = null;

	public MyConfig.ChannelType mChannelType = ChannelType.NOTHING;

	public DefineMT.MTType mMTType = MTType.Default;

	public String MO = "";
	public String MT = "";
	public String RequestID = "";
	public Integer PID = 0;
	public Date ReceiveDate = null;
	public Date SendDate = null;

	public VNPApplication mVNPApp = new VNPApplication();

	public String UserName = "";
	public String IP = "";
	public int PartnerID = 0;

	public boolean IsNull()
	{
		if (MSISDN == null || MSISDN.equalsIgnoreCase("")) return true;
		else return false;
	}

	public MOObject(int ServiceID, String MSISDN, ChannelType mChannelType, MTType mMTType, String MO, String MT, String RequestID,
			int PID, Date ReceiveDate, Date SendDate, VNPApplication mVNPApp, String UserName, String IP,
			int PartnerID)
	{
		this.ServiceID = ServiceID;
		this.MSISDN = MSISDN;
		this.LogDate = Calendar.getInstance().getTime();
		this.mChannelType = mChannelType;

		this.mMTType = mMTType;

		this.MO = MO;
		this.MT = MT;
		this.RequestID = RequestID;
		this.PID = PID;
		this.ReceiveDate = ReceiveDate;
		this.SendDate = SendDate;

		this.mVNPApp = mVNPApp;

		this.UserName = UserName;
		this.IP = IP;
		this.PartnerID = PartnerID;
	}

	public MyTableModel AddNewRow(MyTableModel mTable) throws Exception
	{
		if (mTable == null) return null;

		if (IsNull()) return mTable;

		MyDataRow mRow = mTable.CreateNewRow();

		mRow.SetValueCell("ServiceID", ServiceID);
		mRow.SetValueCell("MSISDN", MSISDN);
		mRow.SetValueCell("LogDate", MyConfig.Get_DateFormat_InsertDB().format(LogDate.getTime()));

		mRow.SetValueCell("ChannelTypeID", mChannelType.GetValue());
		mRow.SetValueCell("ChannelTypeName", mChannelType.toString());

		mRow.SetValueCell("MTTypeID", mMTType.GetValue());
		mRow.SetValueCell("MTTypeName", mMTType.toString());

		mRow.SetValueCell("MO", MO);
		mRow.SetValueCell("MT", MT);
		mRow.SetValueCell("RequestID", RequestID);
		mRow.SetValueCell("PID", PID);
		mRow.SetValueCell("ReceiveDate", MyConfig.Get_DateFormat_InsertDB().format(ReceiveDate.getTime()));
		mRow.SetValueCell("SendDate", MyConfig.Get_DateFormat_InsertDB().format(SendDate.getTime()));

		if (mVNPApp.mApp != VNPApplication.TelcoApplication.NoThing)
		{
			mRow.SetValueCell("AppID", mVNPApp.GetValue());
			mRow.SetValueCell("AppName", mVNPApp.toString());

			mRow.SetValueCell("UserName", UserName);
			mRow.SetValueCell("IP", IP);
		}
		mRow.SetValueCell("PartnerID", PartnerID);

		mTable.AddNewRow(mRow);
		return mTable;
	}
}
