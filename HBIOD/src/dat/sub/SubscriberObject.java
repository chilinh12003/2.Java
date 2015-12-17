package dat.sub;

import java.util.Date;
import java.util.Vector;

import uti.utility.MyConfig;
import uti.utility.MyConfig.ChannelType;
import dat.sub.Subscriber.Status;
import db.define.MyDataRow;
import db.define.MyTableModel;

public class SubscriberObject
{
	/**
	 * Kiểu khởi tạo 1 đối tượng Sub hoặc Unsub
	 * <p>
	 * VD: Tạo đăng ký mới, tạo đăng ký đã hủy
	 * </p>
	 * 
	 * @author Administrator
	 * 
	 */
	public enum InitType
	{
		Nothing(0),
		/**
		 * Chưa từng sử dụng dịch vụ
		 */
		NeverUse(1),
		/**
		 * Đã sử dụng dịch vụ trước đây
		 */
		Exist(2);

		private int value;

		private InitType(int value)
		{
			this.value = value;
		}

		public int GetValue()
		{
			return this.value;
		}

		public static InitType FromInt(int iValue)
		{
			for (InitType type : InitType.values())
			{
				if (type.GetValue() == iValue)
					return type;
			}
			return Nothing;
		}
	}

	public int ServiceID = 0;
	public String MSISDN = "";
	public Date FirstDate = null;
	public ChannelType mChannelType = ChannelType.NOTHING;
	public Subscriber.Status mStatus = Status.NoThing;
	public int PID = 0;
	public int MOByDay = 0;
	public int MTByDay = 0;
	public int MOTotal = 0;
	public int MTTotal = 0;

	public int RequestByDay = 0;
	public int RequestTotal = 0;
	public Date RequestDate = null;
	public int ConfirmByDay = 0;
	public int ConfirmTotal = 0;
	public Date ConfirmDate = null;

	public Date ChargeDate = null;
	public Date ChargeSuccessDate = null;

	public int ChargeByDay = 0;
	public int ChargeSuccessByDay = 0;

	public int ChargeTotal = 0;
	public int ChargeSuccessTotal = 0;
	public Date NotifyDate = null;
	public int PartnerID = 0;

	public boolean IsNull()
	{
		if (MSISDN == null || MSISDN.equalsIgnoreCase("") || ServiceID == 0)
			return true;
		else return false;
	}

	public static SubscriberObject Convert(MyDataRow mRow) throws Exception
	{
		try
		{
			if (mRow == null)
				return new SubscriberObject();

			SubscriberObject mObject = new SubscriberObject();

			mObject.ServiceID = Integer.parseInt(mRow.GetValueCell("ServiceID").toString());
			mObject.MSISDN = mRow.GetValueCell("MSISDN").toString();
			mObject.FirstDate = MyConfig.Get_DateFormat_InsertDB().parse(mRow.GetValueCell("FirstDate").toString());
			mObject.mChannelType = ChannelType.FromInt(Integer.parseInt(mRow.GetValueCell("ChannelTypeID")
					.toString()));
			mObject.mStatus = Subscriber.Status.FromInt(Integer.parseInt(mRow.GetValueCell("StatusID").toString()));
			mObject.PID = Integer.parseInt(mRow.GetValueCell("PID").toString());

			mObject.MOByDay = Integer.parseInt(mRow.GetValueCell("MOByDay").toString());
			mObject.MTByDay = Integer.parseInt(mRow.GetValueCell("MTByDay").toString());
			mObject.MOTotal = Integer.parseInt(mRow.GetValueCell("MOTotal").toString());
			mObject.MTTotal = Integer.parseInt(mRow.GetValueCell("MTTotal").toString());

			mObject.RequestByDay = Integer.parseInt(mRow.GetValueCell("RequestByDay").toString());
			mObject.RequestTotal = Integer.parseInt(mRow.GetValueCell("RequestTotal").toString());

			mObject.ConfirmByDay = Integer.parseInt(mRow.GetValueCell("ConfirmByDay").toString());
			mObject.ConfirmTotal = Integer.parseInt(mRow.GetValueCell("ConfirmTotal").toString());

			if (mRow.GetValueCell("ConfirmDate") != null)
				mObject.ConfirmDate = MyConfig.Get_DateFormat_InsertDB().parse(
						mRow.GetValueCell("ConfirmDate").toString());

			if (mRow.GetValueCell("RequestDate") != null)
				mObject.RequestDate = MyConfig.Get_DateFormat_InsertDB().parse(
						mRow.GetValueCell("RequestDate").toString());

			if (mRow.GetValueCell("ChargeDate") != null)
				mObject.ChargeDate = MyConfig.Get_DateFormat_InsertDB().parse(
						mRow.GetValueCell("ChargeDate").toString());

			if (mRow.GetValueCell("ChargeSuccessDate") != null)
				mObject.ChargeSuccessDate = MyConfig.Get_DateFormat_InsertDB().parse(
						mRow.GetValueCell("ChargeSuccessDate").toString());

			mObject.ChargeByDay = Integer.parseInt(mRow.GetValueCell("ChargeByDay").toString());
			mObject.ChargeSuccessByDay = Integer.parseInt(mRow.GetValueCell("ChargeSuccessByDay").toString());

			mObject.ChargeTotal = Integer.parseInt(mRow.GetValueCell("ChargeTotal").toString());
			mObject.ChargeSuccessTotal = Integer.parseInt(mRow.GetValueCell("ChargeSuccessTotal").toString());

			if (mRow.GetValueCell("NotifyDate") != null)
				mObject.NotifyDate = MyConfig.Get_DateFormat_InsertDB().parse(
						mRow.GetValueCell("NotifyDate").toString());

			mObject.PartnerID = Integer.parseInt(mRow.GetValueCell("PartnerID").toString());
			
			return mObject;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public static Vector<SubscriberObject> ConvertToList(MyTableModel mTable) throws Exception
	{
		try
		{
			Vector<SubscriberObject> mList = new Vector<SubscriberObject>();
			if (mTable.GetRowCount() < 1)
				return mList;

			for (int i = 0; i < mTable.GetRowCount(); i++)
			{
				SubscriberObject mObject = new SubscriberObject();

				mObject = Convert(mTable.GetRow(i));
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

		mRow.SetValueCell("ServiceID", ServiceID);
		mRow.SetValueCell("MSISDN", MSISDN);
		mRow.SetValueCell("FirstDate", MyConfig.Get_DateFormat_InsertDB().format(FirstDate.getTime()));
		mRow.SetValueCell("ChannelTypeID", mChannelType.GetValue());
		mRow.SetValueCell("StatusID", mStatus.GetValue());
		mRow.SetValueCell("PID", PID);
		mRow.SetValueCell("MOByDay", MOByDay);
		mRow.SetValueCell("MTByDay", MTByDay);
		mRow.SetValueCell("MOTotal", MOTotal);
		mRow.SetValueCell("MTTotal", MTTotal);
		
		mRow.SetValueCell("RequestByDay", RequestByDay);
		mRow.SetValueCell("RequestTotal", RequestTotal);
		mRow.SetValueCell("ConfirmByDay", ConfirmByDay);
		mRow.SetValueCell("ConfirmTotal", ConfirmTotal);
		
		if (RequestDate != null)
			mRow.SetValueCell("RequestDate", MyConfig.Get_DateFormat_InsertDB().format(RequestDate.getTime()));
		
		if (ConfirmDate != null)
			mRow.SetValueCell("ConfirmDate",
					MyConfig.Get_DateFormat_InsertDB().format(ConfirmDate.getTime()));
		
		if (ChargeDate != null)
			mRow.SetValueCell("ChargeDate", MyConfig.Get_DateFormat_InsertDB().format(ChargeDate.getTime()));
		
		if (ChargeSuccessDate != null)
			mRow.SetValueCell("ChargeSuccessDate",
					MyConfig.Get_DateFormat_InsertDB().format(ChargeSuccessDate.getTime()));
		
		mRow.SetValueCell("ChargeByDay", ChargeByDay);
		mRow.SetValueCell("ChargeSuccessByDay", ChargeSuccessByDay);
		mRow.SetValueCell("ChargeTotal", ChargeTotal);
		mRow.SetValueCell("ChargeSuccessTotal", ChargeSuccessTotal);
		if (NotifyDate != null)
			mRow.SetValueCell("NotifyDate", MyConfig.Get_DateFormat_InsertDB().format(NotifyDate.getTime()));

		mRow.SetValueCell("PartnerID", PartnerID);
		
		mTable.AddNewRow(mRow);
		return mTable;
	}

}
