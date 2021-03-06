package api.process;

import java.util.Calendar;
import java.util.Collections;
import java.util.Vector;

import uti.utility.MyConfig;
import uti.utility.MyConvert;
import uti.utility.MyLogger;
import api.process.Charge.ErrorCode;
import dat.service.MatchObject;
import dat.sub.Subscriber;
import dat.sub.SubscriberObject;
import dat.sub.UnSubscriber;
import db.define.MyDataRow;
import db.define.MyTableModel;

public class ProDeregisterAll
{

	public enum DeregAllResult
	{
		// 0 Đăng ký thành công dịch vụ
		Success(0),
		// 1 Thuê bao này đã tồn tại
		NotExistSub(1),
		// 1xx Đều là đăng ký không thành công
		Fail(100),
		// Lỗi hệ thống
		SystemError(101),
		// Thông tin nhập vào không hợp lệ
		InputInvalid(102), ;

		private int value;

		private DeregAllResult(int value)
		{
			this.value = value;
		}

		public Integer GetValue()
		{
			return this.value;
		}

		public static DeregAllResult FromInt(int iValue)
		{
			for (DeregAllResult type : DeregAllResult.values())
			{
				if (type.GetValue() == iValue)
					return type;
			}
			return Fail;
		}
	}

	MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath, this.getClass().toString());

	MatchObject mMatchObj = new MatchObject();

	Calendar mCal_Current = Calendar.getInstance();
	Calendar mCal_SendMO = Calendar.getInstance();

	Subscriber mSub = null;
	UnSubscriber mUnSub = null;

	DeregAllResult mDeregAllResult = DeregAllResult.Fail;

	String MSISDN = "";
	String RequestID = "";
	String Channel = "";
	String Keyword = "HUY";
	String AppName = "";
	String UserName = "";
	String IP = "";

	public ProDeregisterAll(String MSISDN, String RequestID, String Channel, String AppName, String UserName, String IP)
	{
		this.MSISDN = MSISDN;
		this.RequestID = RequestID;
		this.Channel = Channel.toUpperCase().trim();
		this.AppName = AppName;
		this.UserName = UserName;
		this.IP = IP;
	}

	private MyTableModel AddInfo(SubscriberObject mSubObj) throws Exception
	{
		try
		{

			MyTableModel mTable_UnSub = (MyTableModel) TableTemplate.Get_mUnSubscriber().clone();
			mTable_UnSub.Clear();

			// Tạo row để insert vào Table Sub
			MyDataRow mRow_Sub = mTable_UnSub.CreateNewRow();
			mRow_Sub.SetValueCell("MSISDN", mSubObj.MSISDN);

			mRow_Sub.SetValueCell("FirstDate", MyConfig.Get_DateFormat_InsertDB().format(mSubObj.FirstDate));
			mRow_Sub.SetValueCell("EffectiveDate", MyConfig.Get_DateFormat_InsertDB().format(mSubObj.EffectiveDate));
			mRow_Sub.SetValueCell("ExpiryDate", MyConfig.Get_DateFormat_InsertDB().format(mSubObj.ExpiryDate));

			mRow_Sub.SetValueCell("RetryChargeCount", mSubObj.RetryChargeCount);

			if (mSubObj.RetryChargeDate != null)
				mRow_Sub.SetValueCell("RetryChargeDate",
						MyConfig.Get_DateFormat_InsertDB().format(mSubObj.RetryChargeDate));

			if (mSubObj.ChargeDate != null)
				mRow_Sub.SetValueCell("ChargeDate", MyConfig.Get_DateFormat_InsertDB().format(mSubObj.ChargeDate));

			if (mSubObj.RenewChargeDate != null)
				mRow_Sub.SetValueCell("RenewChargeDate",
						MyConfig.Get_DateFormat_InsertDB().format(mSubObj.RenewChargeDate));

			mRow_Sub.SetValueCell("ChannelTypeID", mSubObj.ChannelTypeID);
			mRow_Sub.SetValueCell("StatusID", mSubObj.StatusID);
			mRow_Sub.SetValueCell("PID", mSubObj.PID);
			mRow_Sub.SetValueCell("OrderID", mSubObj.OrderID);

			mRow_Sub.SetValueCell("MOByDay", mSubObj.MOByDay);
			mRow_Sub.SetValueCell("ChargeMark", mSubObj.ChargeMark);
			mRow_Sub.SetValueCell("WeekMark", mSubObj.WeekMark);
			mRow_Sub.SetValueCell("CodeByDay", mSubObj.CodeByDay);
			mRow_Sub.SetValueCell("TotalCode", mSubObj.TotalCode);
			mRow_Sub.SetValueCell("MatchID", mMatchObj.MatchID);
			mRow_Sub.SetValueCell("IsNotify", mSubObj.IsNotify);
			mRow_Sub.SetValueCell("AppID", mSubObj.AppID);
			mRow_Sub.SetValueCell("AppName", mSubObj.AppName);
			mRow_Sub.SetValueCell("UserName", mSubObj.UserName);
			mRow_Sub.SetValueCell("IP", mSubObj.IP);
			mRow_Sub.SetValueCell("PartnerID", mSubObj.PartnerID);

			if (mSubObj.LastUpdate != null)
				mRow_Sub.SetValueCell("LastUpdate", MyConfig.Get_DateFormat_InsertDB().format(mSubObj.LastUpdate));

			if (mSubObj.NotifyDate != null)
				mRow_Sub.SetValueCell("NotifyDate", MyConfig.Get_DateFormat_InsertDB().format(mSubObj.NotifyDate));

			if (mSubObj.DeregDate != null)
			{
				mRow_Sub.SetValueCell("DeregDate", MyConfig.Get_DateFormat_InsertDB().format(mSubObj.DeregDate));
			}

			if (mSubObj.CofirmDeregDate != null)
			{
				mRow_Sub.SetValueCell("CofirmDeregDate",
						MyConfig.Get_DateFormat_InsertDB().format(mSubObj.CofirmDeregDate));
			}

			mTable_UnSub.AddNewRow(mRow_Sub);
			return mTable_UnSub;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	private boolean MoveToSub(SubscriberObject mSubObj) throws Exception
	{
		try
		{
			MyTableModel mTable_UnSub = AddInfo(mSubObj);

			if (!mUnSub.Move(0, mTable_UnSub.GetXML()))
			{
				mLog.log.info(" Move Tu Sub Sang UnSub KHONG THANH CONG: XML Insert-->" + mTable_UnSub.GetXML());
				return false;
			}

			return true;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	/**
	 * tạo dữ liệu cho những đăng ký lại (trước đó đã hủy dịch vụ)
	 * 
	 * @throws Exception
	 */
	private void CreateDeReg(SubscriberObject mSubObj) throws Exception
	{
		try
		{
			mSubObj.StatusID = dat.sub.Subscriber.Status.UndoSub.GetValue();
			mSubObj.ChannelTypeID = Common.GetChannelType(Channel).GetValue();
			mSubObj.DeregDate = mCal_Current.getTime();

			mSubObj.AppID = Common.GetApplication(AppName).GetValue();
			mSubObj.AppName = Common.GetApplication(AppName).toString();
			mSubObj.UserName = UserName;
			mSubObj.IP = IP;

		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public String Process()
	{
		mDeregAllResult = DeregAllResult.Fail;
		String ListFail = "";
		try
		{
			mSub = new Subscriber(LocalConfig.mDBConfig_MSSQL);
			mUnSub = new UnSubscriber(LocalConfig.mDBConfig_MSSQL);

			Vector<SubscriberObject> mList = new Vector<SubscriberObject>();
			Vector<SubscriberObject> mListUnSub = new Vector<SubscriberObject>();

			Integer PID = MyConvert.GetPIDByMSISDN(MSISDN, LocalConfig.MAX_PID);

			// Lấy thông tin khách hàng đã đăng ký
			MyTableModel mTable_Sub = mSub.Select(2, PID.toString(), MSISDN);
			mList = SubscriberObject.ConvertToList(mTable_Sub, false);

			MyTableModel mTable_UnSub = mUnSub.Select(2, PID.toString(), MSISDN);
			mListUnSub = SubscriberObject.ConvertToList(mTable_UnSub, true);
			if (mList.size() > 0 && mListUnSub.size() > 0)
			{
				Collections.addAll(mListUnSub);
			}
			else if (mList.size() == 0 && mListUnSub.size() > 0)
			{
				mList = mListUnSub;
			}

			if (mList.size() < 1)
			{
				mDeregAllResult = DeregAllResult.NotExistSub;
				return GetResponse(mDeregAllResult);
			}

			ListFail = "MSISDN:" + MSISDN;
			for (SubscriberObject mSubObj : mList)
			{
				if (mSubObj.IsDereg)
				{
					mSubObj.StatusID = dat.sub.Subscriber.Status.UndoSub.GetValue();
					
					MyTableModel mTable_UnSub_Update = AddInfo(mSubObj);
					
					if (mUnSub.Update(2, mTable_UnSub_Update.GetXML()))
					{
						ListFail += "|Update Undo Status khong thanh cong";
					}
				}
				else
				{
					CreateDeReg(mSubObj);
					ErrorCode mResult = Charge.ChargeDereg(mSubObj.PartnerID, MSISDN, Keyword,
							Common.GetChannelType(Channel), Common.GetApplication(AppName), UserName, IP);
					if (mResult != ErrorCode.ChargeSuccess)
					{
						ListFail += "|ChargeDereg khong thanh cong ErrorCode:" + mResult.toString();
					}
					if (!MoveToSub(mSubObj))
					{
						ListFail += "|MoveToSub khong thanh cong";
					}
				}

			}

			mDeregAllResult = DeregAllResult.Success;

		}
		catch (Exception ex)
		{
			mDeregAllResult = DeregAllResult.SystemError;
			mLog.log.error(ex);
		}
		finally
		{
			MyLogger.WriteDataLog(LocalConfig.LogDataFolder, "_DeregsterAll_FAIL", "INFO --> " + ListFail);
		}
		return GetResponse(mDeregAllResult);
	}

	private String GetResponse(DeregAllResult mDeregAllResult)
	{
		String XMLReturn = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>" + "<RESPONSE>" + "<ERRORID>"
				+ mDeregAllResult.GetValue() + "</ERRORID>" + "<ERRORDESC>" + mDeregAllResult.toString()
				+ "</ERRORDESC>" + "</RESPONSE>";
		return XMLReturn;
	}

}