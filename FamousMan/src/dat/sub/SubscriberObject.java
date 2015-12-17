package dat.sub;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import uti.utility.MyConfig;
import uti.utility.MyConfig.ChannelType;
import uti.utility.VNPApplication;
import dat.history.Play;
import dat.sub.Subscriber.Status;
import db.define.MyDataRow;
import db.define.MyTableModel;

public class SubscriberObject
{
	/**
	 * Kiểu khởi tạo 1 đối tượng Sub hoặc Unsub
	 * <p>VD: Tạo đăng ký mới, tạo đăng ký đã hủy</p>
	 * @author Administrator
	 *
	 */
	public enum InitType
	{
		Nothing(0),
		/**
		 * Đăng ký mới
		 */
		NewReg(1),
		/**
		 * Đăng ký nhưng trước đó đã hủy dịch vụ
		 */
		RegAgain(2),
		/**
		 * Đăng ký cho số thuê bao đã bị Hủy từ Vinaphone
		 */
		UndoReg(3)
		;			

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
	
	
	public String MSISDN = "";
	public Date FirstDate = null;
	public Date ResetDate = null;
	public Date EffectiveDate = null;
	public Date ExpiryDate = null;
	
	public Date RenewChargeDate = null;
	public Date RetryChargeDate = null;

	public int RetryChargeCount = 0;

	public ChannelType mChannelType = ChannelType.NOTHING;

	public Subscriber.Status mStatus = Status.NoThing;

	public int PID = 0;

	public int LastSuggestrID = 0;
	public int SuggestByDay = 0;
	public int TotalSuggest = 0;
	public Date LastSuggestDate = null;

	public int AnswerForSuggestID = 0;
	public Play.Status mLastAnswerStatus = Play.Status.Nothing;
	public Integer AnswerByDay = 0;
	public String LastAnswer = "";
	public Date LastAnswerDate = null;

	public Integer OrderID = 0;

	// các thông tin khi Vinaphone goi API
	public VNPApplication mVNPApp = new VNPApplication();
	public String UserName = "";
	public String IP = "";
	public int PartnerID = 0;

	public Date DeregDate = null;

	/**
	 * Cho biết đây có phải là đối tượng lấy từ table UnSub ra không
	 */
	public boolean IsDereg = false;

	public boolean IsNull()
	{
		if (MSISDN == null || MSISDN.equalsIgnoreCase("")) return true;
		else return false;
	}

	public boolean CheckLastSuggestDate(Calendar mCal_Current) throws Exception
	{
		if (LastSuggestDate == null) return false;
		Calendar mCal_CheckDate = Calendar.getInstance();

		mCal_CheckDate.setTime(LastSuggestDate);
		if (mCal_Current.get(Calendar.YEAR) == mCal_CheckDate.get(Calendar.YEAR)
				&& mCal_Current.get(Calendar.MONTH) == mCal_CheckDate.get(Calendar.MONTH)
				&& mCal_Current.get(Calendar.DATE) == mCal_CheckDate.get(Calendar.DATE)) return true;
		else return false;
	}
	
	public boolean CheckLastAnswerDate(Calendar mCal_Current) throws Exception
	{
		if (LastAnswerDate == null) return false;
		Calendar mCal_CheckDate = Calendar.getInstance();

		mCal_CheckDate.setTime(LastAnswerDate);
		if (mCal_Current.get(Calendar.YEAR) == mCal_CheckDate.get(Calendar.YEAR)
				&& mCal_Current.get(Calendar.MONTH) == mCal_CheckDate.get(Calendar.MONTH)
				&& mCal_Current.get(Calendar.DATE) == mCal_CheckDate.get(Calendar.DATE)) return true;
		else return false;
	}
	
	public static SubscriberObject Convert(MyTableModel mTable, boolean IsDereg) throws Exception
	{
		try
		{
			if (mTable.GetRowCount() < 1) return new SubscriberObject();

			SubscriberObject mObject = new SubscriberObject();

			mObject.MSISDN = mTable.GetValueAt(0, "MSISDN").toString();
			mObject.FirstDate = MyConfig.Get_DateFormat_InsertDB().parse(mTable.GetValueAt(0, "FirstDate").toString());

			mObject.ResetDate = MyConfig.Get_DateFormat_InsertDB().parse(mTable.GetValueAt(0, "ResetDate").toString());

			mObject.EffectiveDate = MyConfig.Get_DateFormat_InsertDB().parse(
					mTable.GetValueAt(0, "EffectiveDate").toString());
			mObject.ExpiryDate = MyConfig.Get_DateFormat_InsertDB()
					.parse(mTable.GetValueAt(0, "ExpiryDate").toString());
			
			if (mTable.GetValueAt(0, "RenewChargeDate") != null)
				mObject.RenewChargeDate = MyConfig.Get_DateFormat_InsertDB().parse(
						mTable.GetValueAt(0, "RenewChargeDate").toString());

			if (mTable.GetValueAt(0, "RetryChargeDate") != null)
				mObject.RetryChargeDate = MyConfig.Get_DateFormat_InsertDB().parse(
						mTable.GetValueAt(0, "RetryChargeDate").toString());

			if (mTable.GetValueAt(0, "RetryChargeCount") != null)
				mObject.RetryChargeCount = Integer.parseInt(mTable.GetValueAt(0, "RetryChargeCount").toString());

			mObject.mChannelType = ChannelType.FromInt(Integer.parseInt(mTable.GetValueAt(0, "ChannelTypeID")
					.toString()));
			mObject.mStatus = Subscriber.Status.FromInt(Integer.parseInt(mTable.GetValueAt(0, "StatusID").toString()));
			mObject.PID = Integer.parseInt(mTable.GetValueAt(0, "PID").toString());

			// Thông tin mua dữ kiện
			mObject.LastSuggestrID = Integer.parseInt(mTable.GetValueAt(0, "LastSuggestrID").toString());
			mObject.SuggestByDay = Integer.parseInt(mTable.GetValueAt(0, "SuggestByDay").toString());
			mObject.TotalSuggest = Integer.parseInt(mTable.GetValueAt(0, "TotalSuggest").toString());
			if (mTable.GetValueAt(0, "LastSuggestDate") != null)
				mObject.LastSuggestDate = MyConfig.Get_DateFormat_InsertDB().parse(
						mTable.GetValueAt(0, "LastSuggestDate").toString());

			// Thông tin trả lời
			mObject.AnswerForSuggestID = Integer.parseInt(mTable.GetValueAt(0, "AnswerForSuggestID").toString());
			mObject.mLastAnswerStatus = Play.Status.FromInt(Integer.parseInt(mTable.GetValueAt(0, "AnswerStatusID")
					.toString()));
			mObject.AnswerByDay = Integer.parseInt(mTable.GetValueAt(0, "AnswerByDay").toString());
			if (mTable.GetValueAt(0, "LastAnswer") != null)
				mObject.LastAnswer = mTable.GetValueAt(0, "LastAnswer").toString();
			if (mTable.GetValueAt(0, "LastAnswerDate") != null)
				mObject.LastAnswerDate = MyConfig.Get_DateFormat_InsertDB().parse(
						mTable.GetValueAt(0, "LastAnswerDate").toString());

			if (mTable.GetValueAt(0, "PartnerID") != null)
				mObject.PartnerID = Integer.parseInt(mTable.GetValueAt(0, "PartnerID").toString());

			// Thong tin tu Vinaphone goi sang
			if (mTable.GetValueAt(0, "AppID") != null)
				mObject.mVNPApp = VNPApplication.FromInt(Integer.parseInt(mTable.GetValueAt(0, "AppID").toString()));

			if (mTable.GetValueAt(0, "UserName") != null)
				mObject.UserName = mTable.GetValueAt(0, "UserName").toString();

			if (mTable.GetValueAt(0, "IP") != null) mObject.IP = mTable.GetValueAt(0, "IP").toString();

			mObject.OrderID = Integer.parseInt(mTable.GetValueAt(0, "OrderID").toString());

			mObject.IsDereg = IsDereg;

			if (mTable.GetValueAt(0, "DeregDate") != null)
				mObject.DeregDate = MyConfig.Get_DateFormat_InsertDB().parse(
						mTable.GetValueAt(0, "DeregDate").toString());

			return mObject;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public static Vector<SubscriberObject> ConvertToList(MyTableModel mTable, boolean IsDereg) throws Exception
	{
		try
		{
			Vector<SubscriberObject> mList = new Vector<SubscriberObject>();
			if (mTable.GetRowCount() < 1) return mList;

			for (int i = 0; i < mTable.GetRowCount(); i++)
			{
				SubscriberObject mObject = new SubscriberObject();

				mObject.MSISDN = mTable.GetValueAt(i, "MSISDN").toString();
				mObject.FirstDate = MyConfig.Get_DateFormat_InsertDB().parse(
						mTable.GetValueAt(i, "FirstDate").toString());

				mObject.ResetDate = MyConfig.Get_DateFormat_InsertDB().parse(
						mTable.GetValueAt(i, "ResetDate").toString());

				mObject.EffectiveDate = MyConfig.Get_DateFormat_InsertDB().parse(
						mTable.GetValueAt(i, "EffectiveDate").toString());
				mObject.ExpiryDate = MyConfig.Get_DateFormat_InsertDB().parse(
						mTable.GetValueAt(i, "ExpiryDate").toString());

				if (mTable.GetValueAt(i, "RenewChargeDate") != null)
					mObject.RenewChargeDate = MyConfig.Get_DateFormat_InsertDB().parse(
							mTable.GetValueAt(i, "RenewChargeDate").toString());

				if (mTable.GetValueAt(i, "RetryChargeDate") != null)
					mObject.RetryChargeDate = MyConfig.Get_DateFormat_InsertDB().parse(
							mTable.GetValueAt(i, "RetryChargeDate").toString());

				if (mTable.GetValueAt(i, "RetryChargeCount") != null)
					mObject.RetryChargeCount = Integer.parseInt(mTable.GetValueAt(i, "RetryChargeCount").toString());

				mObject.mChannelType = ChannelType.FromInt(Integer.parseInt(mTable.GetValueAt(i, "ChannelTypeID")
						.toString()));
				mObject.mStatus = Subscriber.Status.FromInt(Integer.parseInt(mTable.GetValueAt(i, "StatusID")
						.toString()));
				mObject.PID = Integer.parseInt(mTable.GetValueAt(i, "PID").toString());

				// Thông tin mua dữ kiện
				mObject.LastSuggestrID = Integer.parseInt(mTable.GetValueAt(i, "LastSuggestrID").toString());
				mObject.SuggestByDay = Integer.parseInt(mTable.GetValueAt(i, "SuggestByDay").toString());
				mObject.TotalSuggest = Integer.parseInt(mTable.GetValueAt(i, "TotalSuggest").toString());
				if (mTable.GetValueAt(i, "LastSuggestDate") != null)
					mObject.LastSuggestDate = MyConfig.Get_DateFormat_InsertDB().parse(
							mTable.GetValueAt(i, "LastSuggestDate").toString());

				// Thông tin trả lời
				mObject.AnswerForSuggestID = Integer.parseInt(mTable.GetValueAt(i, "AnswerForSuggestID").toString());
				mObject.mLastAnswerStatus = Play.Status.FromInt(Integer.parseInt(mTable.GetValueAt(i, "AnswerStatusID")
						.toString()));
				mObject.AnswerByDay = Integer.parseInt(mTable.GetValueAt(i, "AnswerByDay").toString());

				if (mTable.GetValueAt(i, "LastAnswer") != null)
					mObject.LastAnswer = mTable.GetValueAt(i, "LastAnswer").toString();

				if (mTable.GetValueAt(i, "LastAnswerDate") != null)
					mObject.LastAnswerDate = MyConfig.Get_DateFormat_InsertDB().parse(
							mTable.GetValueAt(i, "LastAnswerDate").toString());

				if (mTable.GetValueAt(i, "PartnerID") != null)
					mObject.PartnerID = Integer.parseInt(mTable.GetValueAt(i, "PartnerID").toString());

				// Thong tin tu Vinaphone goi sang
				if (mTable.GetValueAt(i, "AppID") != null)
					mObject.mVNPApp = VNPApplication
							.FromInt(Integer.parseInt(mTable.GetValueAt(i, "AppID").toString()));

				if (mTable.GetValueAt(i, "UserName") != null)
					mObject.UserName = mTable.GetValueAt(i, "UserName").toString();

				if (mTable.GetValueAt(i, "IP") != null) mObject.IP = mTable.GetValueAt(i, "IP").toString();

				mObject.OrderID = Integer.parseInt(mTable.GetValueAt(i, "OrderID").toString());

				mObject.IsDereg = IsDereg;

				if (mTable.GetValueAt(i, "DeregDate") != null)
					mObject.DeregDate = MyConfig.Get_DateFormat_InsertDB().parse(
							mTable.GetValueAt(i, "DeregDate").toString());

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
	 * Kiểm tra xem KH con trong thời gian khuyễn mãi hay không
	 * 
	 * @param FreeDayCount
	 * @return
	 * @throws Exception
	 */
	public boolean IsFreeReg(int FreeDayCount) throws Exception
	{
		try
		{
			if (ResetDate == null) { return true; }

			Calendar cal_Current = Calendar.getInstance();
			Calendar cal_ResetDate = Calendar.getInstance();

			cal_ResetDate.setTime(ResetDate);
			cal_ResetDate.set(cal_ResetDate.get(Calendar.YEAR), cal_ResetDate.get(Calendar.MONTH),
					cal_ResetDate.get(Calendar.DATE), 0, 0, 0);
			
			cal_ResetDate.add(Calendar.DATE, FreeDayCount);

			if (cal_ResetDate.after(cal_Current)) return true;
			else return false;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}
	
	public MyTableModel AddNewRow(MyTableModel mTable) throws Exception
	{
		if (mTable == null) return null;

		if (IsNull()) return mTable;

		MyDataRow mRow = mTable.CreateNewRow();

		mRow.SetValueCell("MSISDN", MSISDN);
		mRow.SetValueCell("FirstDate", MyConfig.Get_DateFormat_InsertDB().format(FirstDate.getTime()));
		mRow.SetValueCell("ResetDate", MyConfig.Get_DateFormat_InsertDB().format(ResetDate.getTime()));
		mRow.SetValueCell("EffectiveDate", MyConfig.Get_DateFormat_InsertDB().format(EffectiveDate.getTime()));
		mRow.SetValueCell("ExpiryDate", MyConfig.Get_DateFormat_InsertDB().format(ExpiryDate.getTime()));

		if (RetryChargeDate != null)
			mRow.SetValueCell("RetryChargeDate", MyConfig.Get_DateFormat_InsertDB().format(RetryChargeDate.getTime()));
		mRow.SetValueCell("RetryChargeCount", RetryChargeCount);	

		if (RenewChargeDate != null)
			mRow.SetValueCell("RenewChargeDate", MyConfig.Get_DateFormat_InsertDB().format(RenewChargeDate.getTime()));

		mRow.SetValueCell("ChannelTypeID", mChannelType.GetValue());
		mRow.SetValueCell("StatusID", mStatus.GetValue());
		mRow.SetValueCell("PID", PID);
		mRow.SetValueCell("OrderID", OrderID);
		mRow.SetValueCell("LastSuggestrID", LastSuggestrID);
		mRow.SetValueCell("SuggestByDay", SuggestByDay);
		mRow.SetValueCell("TotalSuggest", TotalSuggest);
		
		if (LastSuggestDate != null)
			mRow.SetValueCell("LastSuggestDate", MyConfig.Get_DateFormat_InsertDB().format(LastSuggestDate.getTime()));
		mRow.SetValueCell("AnswerForSuggestID", AnswerForSuggestID);
		
		if (LastAnswer != null && !LastAnswer.equalsIgnoreCase("")) mRow.SetValueCell("LastAnswer", LastAnswer);
		mRow.SetValueCell("AnswerStatusID", mLastAnswerStatus.GetValue());
		mRow.SetValueCell("AnswerByDay", AnswerByDay);
		
		if (LastAnswerDate != null)
			mRow.SetValueCell("LastAnswerDate", MyConfig.Get_DateFormat_InsertDB().format(LastAnswerDate.getTime()));

		if (DeregDate != null)
			mRow.SetValueCell("DeregDate", MyConfig.Get_DateFormat_InsertDB().format(DeregDate.getTime()));
		
		mRow.SetValueCell("PartnerID", PartnerID);
		mRow.SetValueCell("AppID", mVNPApp.GetValue());
		mRow.SetValueCell("AppName", mVNPApp.toString());
		mRow.SetValueCell("UserName", UserName);
		mRow.SetValueCell("IP", IP);

		if (!mVNPApp.IsNull())
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
