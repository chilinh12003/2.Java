package dat.sub;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import uti.utility.MyConfig;
import db.define.MyTableModel;

public class SubscriberObject
{
	public String MSISDN = "";
	public Date FirstDate = null;
	public Date EffectiveDate = null;
	public Date ExpiryDate = null;
	public Date ChargeDate = null;
	public Date RenewChargeDate = null;
	public Date RetryChargeDate = null;

	public int RetryChargeCount = 0;

	public int ChannelTypeID = 0;
	public int StatusID = 0;
	public int PID = 0;
	public int MOByDay = 0;
	public int ChargeMark = 0;
	public int WeekMark = 0;
	public int CodeByDay = 0;
	public int TotalCode = 0;
	public int MatchID = 0;
	public String AnswerKQ = "";
	public String AnswerBT = "";
	public String AnswerGB = "";
	public String AnswerTS = "";
	public String AnswerTV = "";
	public Date LastUpdate = null;
	public Boolean IsNotify = true;
	public int OrderID = 0;
	public Date CofirmDeregDate = null;
	public Date NotifyDate = null;

	// các thông tin khi Vinaphone goi API
	public int AppID = 0;
	public String AppName = "";
	public String UserName = "";
	public String IP = "";
	public int PartnerID = 0;

	/**
	 * Ngày hủy dịch vụ - nếu đối tượng convert từ table UnSub
	 */
	public Date DeregDate = null;

	/**
	 * Cho biết đây có phải là đối tượng lấy từ table UnSub ra không
	 */
	public boolean IsDereg = false;

	public boolean IsNull()
	{
		if (MSISDN == null || MSISDN.equalsIgnoreCase(""))
			return true;
		else return false;
	}

	public static SubscriberObject Convert(MyTableModel mTable, boolean IsDereg) throws Exception
	{
		try
		{
			if (mTable.GetRowCount() < 1)
				return new SubscriberObject();

			SubscriberObject mObject = new SubscriberObject();

			mObject.MSISDN = mTable.GetValueAt(0, "MSISDN").toString();
			mObject.FirstDate = MyConfig.Get_DateFormat_InsertDB().parse(mTable.GetValueAt(0, "FirstDate").toString());
			mObject.EffectiveDate = MyConfig.Get_DateFormat_InsertDB().parse(
					mTable.GetValueAt(0, "EffectiveDate").toString());
			mObject.ExpiryDate = MyConfig.Get_DateFormat_InsertDB()
					.parse(mTable.GetValueAt(0, "ExpiryDate").toString());

			if (mTable.GetValueAt(0, "ChargeDate") != null)
				mObject.ChargeDate = MyConfig.Get_DateFormat_InsertDB().parse(
						mTable.GetValueAt(0, "ChargeDate").toString());

			if (mTable.GetValueAt(0, "RenewChargeDate") != null)
				mObject.RenewChargeDate = MyConfig.Get_DateFormat_InsertDB().parse(
						mTable.GetValueAt(0, "RenewChargeDate").toString());

			if (mTable.GetValueAt(0, "RetryChargeDate") != null)
				mObject.RetryChargeDate = MyConfig.Get_DateFormat_InsertDB().parse(
						mTable.GetValueAt(0, "RetryChargeDate").toString());

			if (mTable.GetValueAt(0, "RetryChargeCount") != null)
				mObject.RetryChargeCount = Integer.parseInt(mTable.GetValueAt(0, "RetryChargeCount").toString());

			mObject.ChannelTypeID = Integer.parseInt(mTable.GetValueAt(0, "ChannelTypeID").toString());
			mObject.StatusID = Integer.parseInt(mTable.GetValueAt(0, "StatusID").toString());
			mObject.PID = Integer.parseInt(mTable.GetValueAt(0, "PID").toString());

			mObject.MOByDay = Integer.parseInt(mTable.GetValueAt(0, "MOByDay").toString());
			mObject.ChargeMark = Integer.parseInt(mTable.GetValueAt(0, "ChargeMark").toString());
			mObject.WeekMark = Integer.parseInt(mTable.GetValueAt(0, "WeekMark").toString());
			mObject.CodeByDay = Integer.parseInt(mTable.GetValueAt(0, "CodeByDay").toString());
			mObject.TotalCode = Integer.parseInt(mTable.GetValueAt(0, "TotalCode").toString());
			mObject.MatchID = Integer.parseInt(mTable.GetValueAt(0, "MatchID").toString());

			if (mTable.GetValueAt(0, "PartnerID") != null)
				mObject.PartnerID = Integer.parseInt(mTable.GetValueAt(0, "PartnerID").toString());

			// Thong tin tu Vinaphone goi sang
			if (mTable.GetValueAt(0, "AppID") != null)
				mObject.AppID = Integer.parseInt(mTable.GetValueAt(0, "AppID").toString());

			if (mTable.GetValueAt(0, "AppName") != null)
				mObject.AppName = mTable.GetValueAt(0, "AppName").toString();

			if (mTable.GetValueAt(0, "UserName") != null)
				mObject.UserName = mTable.GetValueAt(0, "UserName").toString();

			if (mTable.GetValueAt(0, "IP") != null)
				mObject.IP = mTable.GetValueAt(0, "IP").toString();

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
			mObject.IsNotify = Boolean.parseBoolean(mTable.GetValueAt(0, "IsNotify").toString());

			if (mTable.GetValueAt(0, "LastUpdate") != null)
				mObject.LastUpdate = MyConfig.Get_DateFormat_InsertDB().parse(
						mTable.GetValueAt(0, "LastUpdate").toString());

			if (mTable.GetValueAt(0, "CofirmDeregDate") != null)
				mObject.CofirmDeregDate = MyConfig.Get_DateFormat_InsertDB().parse(
						mTable.GetValueAt(0, "CofirmDeregDate").toString());

			if (mTable.GetValueAt(0, "NotifyDate") != null)
				mObject.NotifyDate = MyConfig.Get_DateFormat_InsertDB().parse(
						mTable.GetValueAt(0, "NotifyDate").toString());

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
			if (mTable.GetRowCount() < 1)
				return mList;

			for (int i = 0; i < mTable.GetRowCount(); i++)
			{
				SubscriberObject mObject = new SubscriberObject();

				mObject.MSISDN = mTable.GetValueAt(i, "MSISDN").toString();
				mObject.FirstDate = MyConfig.Get_DateFormat_InsertDB().parse(
						mTable.GetValueAt(i, "FirstDate").toString());
				mObject.EffectiveDate = MyConfig.Get_DateFormat_InsertDB().parse(
						mTable.GetValueAt(i, "EffectiveDate").toString());
				mObject.ExpiryDate = MyConfig.Get_DateFormat_InsertDB().parse(
						mTable.GetValueAt(i, "ExpiryDate").toString());

				if (mTable.GetValueAt(i, "ChargeDate") != null)
					mObject.ChargeDate = MyConfig.Get_DateFormat_InsertDB().parse(
							mTable.GetValueAt(i, "ChargeDate").toString());

				if (mTable.GetValueAt(i, "RenewChargeDate") != null)
					mObject.RenewChargeDate = MyConfig.Get_DateFormat_InsertDB().parse(
							mTable.GetValueAt(i, "RenewChargeDate").toString());

				if (mTable.GetValueAt(i, "RetryChargeDate") != null)
					mObject.RetryChargeDate = MyConfig.Get_DateFormat_InsertDB().parse(
							mTable.GetValueAt(i, "RetryChargeDate").toString());

				if (mTable.GetValueAt(i, "RetryChargeCount") != null)
					mObject.RetryChargeCount = Integer.parseInt(mTable.GetValueAt(i, "RetryChargeCount").toString());

				mObject.ChannelTypeID = Integer.parseInt(mTable.GetValueAt(i, "ChannelTypeID").toString());
				mObject.StatusID = Integer.parseInt(mTable.GetValueAt(i, "StatusID").toString());
				mObject.PID = Integer.parseInt(mTable.GetValueAt(i, "PID").toString());

				mObject.MOByDay = Integer.parseInt(mTable.GetValueAt(i, "MOByDay").toString());
				mObject.ChargeMark = Integer.parseInt(mTable.GetValueAt(i, "ChargeMark").toString());
				mObject.WeekMark = Integer.parseInt(mTable.GetValueAt(i, "WeekMark").toString());
				mObject.CodeByDay = Integer.parseInt(mTable.GetValueAt(i, "CodeByDay").toString());
				mObject.TotalCode = Integer.parseInt(mTable.GetValueAt(i, "TotalCode").toString());
				mObject.MatchID = Integer.parseInt(mTable.GetValueAt(i, "MatchID").toString());

				if (mTable.GetValueAt(i, "PartnerID") != null)
					mObject.PartnerID = Integer.parseInt(mTable.GetValueAt(i, "PartnerID").toString());

				// Thong tin tu Vinaphone goi sang
				if (mTable.GetValueAt(i, "AppID") != null)
					mObject.AppID = Integer.parseInt(mTable.GetValueAt(i, "AppID").toString());

				if (mTable.GetValueAt(i, "AppName") != null)
					mObject.AppName = mTable.GetValueAt(i, "AppName").toString();

				if (mTable.GetValueAt(i, "UserName") != null)
					mObject.UserName = mTable.GetValueAt(i, "UserName").toString();

				if (mTable.GetValueAt(i, "IP") != null)
					mObject.IP = mTable.GetValueAt(i, "IP").toString();

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
				mObject.IsNotify = Boolean.parseBoolean(mTable.GetValueAt(i, "IsNotify").toString());

				if (mTable.GetValueAt(i, "LastUpdate") != null)
					mObject.LastUpdate = MyConfig.Get_DateFormat_InsertDB().parse(
							mTable.GetValueAt(i, "LastUpdate").toString());

				if (mTable.GetValueAt(i, "CofirmDeregDate") != null)
					mObject.CofirmDeregDate = MyConfig.Get_DateFormat_InsertDB().parse(
							mTable.GetValueAt(i, "CofirmDeregDate").toString());

				if (mTable.GetValueAt(i, "NotifyDate") != null)
					mObject.NotifyDate = MyConfig.Get_DateFormat_InsertDB().parse(
							mTable.GetValueAt(i, "NotifyDate").toString());

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

	/**
	 * Kiểm tra thời gian khuyến mại đăng ký Reutrn: True - Được phép free khi
	 * đăng ký False - Tính phí khi đăng ký
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean IsFreeReg(int FreeDayCount) throws Exception
	{
		try
		{
			if (FirstDate == null) { return true; }

			Calendar cal_Current = Calendar.getInstance();
			Calendar cal_FirstDate = Calendar.getInstance();

			cal_FirstDate.setTime(FirstDate);
			cal_FirstDate.set(cal_FirstDate.get(Calendar.YEAR), cal_FirstDate.get(Calendar.MONTH),
					cal_FirstDate.get(Calendar.DATE), 0, 0, 0);
			
			cal_FirstDate.add(Calendar.DATE, FreeDayCount);

			if (cal_FirstDate.after(cal_Current)) return true;
			else return false;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}
	public boolean IsFreeReg_Effective(int FreeDayCount) throws Exception
	{
		try
		{
			if (EffectiveDate == null) { return true; }

			Calendar cal_Current = Calendar.getInstance();
			Calendar cal_EffectiveDatee = Calendar.getInstance();

			cal_EffectiveDatee.setTime(EffectiveDate);
			cal_EffectiveDatee.set(cal_EffectiveDatee.get(Calendar.YEAR),cal_EffectiveDatee.get(Calendar.MONTH),
					cal_EffectiveDatee.get(Calendar.DATE), 0, 0, 0);
			
			cal_EffectiveDatee.add(Calendar.DATE, FreeDayCount);

			if (cal_EffectiveDatee.after(cal_Current)) return true;
			else return false;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}
}
