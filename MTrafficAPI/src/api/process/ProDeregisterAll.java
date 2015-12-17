package api.process;

import java.util.Calendar;
import java.util.Vector;

import uti.utility.MyConfig;
import uti.utility.VNPApplication;
import uti.utility.MyConvert;
import uti.utility.MyLogger;
import api.process.Charge.ErrorCode;
import dat.service.ServiceObject;
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

	Calendar CurrentDate = Calendar.getInstance();
	Calendar ExpireDate = Calendar.getInstance();

	Subscriber mSub = null;
	UnSubscriber mUnSub = null;

	DeregAllResult mDeregAllResult = DeregAllResult.Fail;

	String MSISDN = "";
	String RequestID = "";
	String Channel = "";

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

	public MyConfig.ChannelType GetChannelType()
	{
		try
		{
			return MyConfig.ChannelType.valueOf(Channel);
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
			return MyConfig.ChannelType.NOTHING;
		}
	}

	public VNPApplication GetApplication()
	{
		try
		{
			return VNPApplication.valueOf(AppName.toUpperCase());
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
			return new VNPApplication();
		}
	}

	private MyTableModel AddInfo(SubscriberObject mSubObj) throws Exception
	{
		try
		{
			MyTableModel mTable_UnSub = mUnSub.Select(0);
			mTable_UnSub.Clear();

			// Tạo row để insert vào Table Sub
			MyDataRow mNewRow = mTable_UnSub.CreateNewRow();
			mNewRow.SetValueCell("MSISDN", mSubObj.MSISDN);
			mNewRow.SetValueCell("ServiceID", mSubObj.ServiceID);

			mNewRow.SetValueCell("FirstDate", MyConfig.Get_DateFormat_InsertDB().format(mSubObj.FirstDate));
			mNewRow.SetValueCell("EffectiveDate", MyConfig.Get_DateFormat_InsertDB().format(mSubObj.EffectiveDate));
			mNewRow.SetValueCell("ExpiryDate", MyConfig.Get_DateFormat_InsertDB().format(mSubObj.ExpiryDate));

			if (mSubObj.ChargeDate != null)
				mNewRow.SetValueCell("ChargeDate", MyConfig.Get_DateFormat_InsertDB().format(mSubObj.ChargeDate));

			if (mSubObj.RetryChargeCount != null)
				mNewRow.SetValueCell("RetryChargeCount", mSubObj.RetryChargeCount);

			if (mSubObj.RetryChargeDate != null)
				mNewRow.SetValueCell("RetryChargeDate", MyConfig.Get_DateFormat_InsertDB().format(mSubObj.RetryChargeDate));

			if (mSubObj.RenewChargeDate != null)
				mNewRow.SetValueCell("RenewChargeDate", MyConfig.Get_DateFormat_InsertDB().format(mSubObj.RenewChargeDate));

			mNewRow.SetValueCell("ChannelTypeID", mSubObj.ChannelTypeID);
			mNewRow.SetValueCell("ChannelTypeName", mSubObj.ChannelTypeName);

			// Thay đổi tình trạng là đang Hủy Thuyê bao
			mNewRow.SetValueCell("StatusID", dat.sub.Subscriber.Status.UndoSub.GetValue());
			mNewRow.SetValueCell("StatusName", dat.sub.Subscriber.Status.UndoSub.toString());

			mNewRow.SetValueCell("PID", mSubObj.PID);
			mNewRow.SetValueCell("TotalMT", mSubObj.TotalMT);
			mNewRow.SetValueCell("TotalMTByDay", mSubObj.TotalMTByDay);
			mNewRow.SetValueCell("OrderID", mSubObj.OrderID);

			mNewRow.SetValueCell("AppID", mSubObj.AppID);
			mNewRow.SetValueCell("AppName", mSubObj.AppName);
			mNewRow.SetValueCell("UserName", mSubObj.UserName);
			mNewRow.SetValueCell("IP", mSubObj.IP);

			mNewRow.SetValueCell("PartnerID", mSubObj.PartnerID);

			if (mSubObj.LastUpdate != null)
				mNewRow.SetValueCell("LastUpdate", MyConfig.Get_DateFormat_InsertDB().format(mSubObj.LastUpdate));

			if (mSubObj.DeregDate != null)
				mNewRow.SetValueCell("DeregDate", MyConfig.Get_DateFormat_InsertDB().format(mSubObj.DeregDate));

			mTable_UnSub.AddNewRow(mNewRow);
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

			mSubObj.ChannelTypeID = GetChannelType().GetValue();
			mSubObj.ChannelTypeName = GetChannelType().toString();
			mSubObj.DeregDate = CurrentDate.getTime();

			mSubObj.AppID = GetApplication().GetValue();
			mSubObj.AppName = GetApplication().toString();
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
			MyTableModel mTable_Sub = mSub.Select(9, PID.toString(), MSISDN);
			mList = SubscriberObject.ConvertToList(mTable_Sub, false);

			MyTableModel mTable_UnSub = mUnSub.Select(9, PID.toString(), MSISDN);
			mListUnSub = SubscriberObject.ConvertToList(mTable_UnSub, true);

			if (mList.size() > 0 && mListUnSub.size() > 0)
			{
				mList.addAll(mListUnSub);
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
				ServiceObject mServiceObj = Common.GetService(mSubObj.ServiceID);
				if (mServiceObj.IsNull())
				{
					ListFail += "|Khong ton tai ServiceID:" + mSubObj.ServiceID;
				}

				if (mSubObj.IsDereg)
				{
					mSubObj.StatusID = dat.sub.Subscriber.Status.UndoSub.GetValue();

					MyTableModel mTable_UnSub_Update = AddInfo(mSubObj);

					if (mUnSub.Update(2, mTable_UnSub_Update.GetXML()))
					{
						ListFail += "|Update Undo Status khong thanh cong ServiceID:" + mSubObj.ServiceID.toString();
					}
				}
				else
				{
					CreateDeReg(mSubObj);

					ErrorCode mResult = Charge.ChargeDereg(mSubObj.PartnerID, mServiceObj, MSISDN, mServiceObj.DeregKeyword, GetChannelType(),
							GetApplication(), UserName, IP);

					if (mResult != ErrorCode.ChargeSuccess)
					{
						ListFail += "|ChargeDereg khong thanh cong ErrorCode:" + mResult.toString();
					}
					if (!MoveToSub(mSubObj))
					{
						ListFail += "|MoveToSub khong thanh cong ServiceID:" + mSubObj.ServiceID.toString();
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
		String XMLReturn = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>" + "<RESPONSE>" + "<ERRORID>" + mDeregAllResult.GetValue() + "</ERRORID>"
				+ "<ERRORDESC>" + mDeregAllResult.toString() + "</ERRORDESC>" + "</RESPONSE>";
		return XMLReturn;
	}

}