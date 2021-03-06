package api.process;

import java.util.Calendar;

import uti.utility.MyConfig;
import uti.utility.MyConfig.ChannelType;
import uti.utility.VNPApplication;
import uti.utility.MyConvert;
import uti.utility.MyLogger;
import api.process.Charge.ErrorCode;
import dat.content.DefineMT;
import dat.content.DefineMT.MTType;
import dat.history.MOLog;
import dat.history.MOObject;
import dat.sub.Subscriber;
import dat.sub.SubscriberObject;
import dat.sub.UnSubscriber;
import db.define.MyTableModel;

public class ProDeregister
{
	public enum DeregResult
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

		private DeregResult(int value)
		{
			this.value = value;
		}

		public Integer GetValue()
		{
			return this.value;
		}

		public static DeregResult FromInt(int iValue)
		{
			for (DeregResult type : DeregResult.values())
			{
				if (type.GetValue() == iValue)
					return type;
			}
			return Fail;
		}
	}

	String MSISDN = "";
	String RequestID = "";
	String PackageName = "";
	String Channel = "";

	String Note = "";
	String Keyword = "HUY API";
	String AppName = "";
	String UserName = "";
	String IP = "";

	MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath, this.getClass().toString());

	SubscriberObject mSubObj = new SubscriberObject();

	Calendar mCal_Current = Calendar.getInstance();

	Subscriber mSub = null;
	UnSubscriber mUnSub = null;
	MOLog mMOLog = null;

	MyTableModel mTable_MOLog = null;
	MyTableModel mTable_Sub = null;
	MyTableModel mTable_UnSub = null;

	Charge mCharge = new Charge();
	DefineMT.MTType mMTType = MTType.RegFail;

	String MTContent = "";
	MyTableModel mTableLog = null;

	MyConfig.ChannelType mChannel = ChannelType.NOTHING;
	VNPApplication mVNPApp = new VNPApplication();

	Integer PID = 0;

	public ProDeregister(String MSISDN, String RequestID, String PackageName,String Note,  String Channel, String AppName, String UserName, String IP) throws Exception
	{
		this.MSISDN = MSISDN.trim();
		this.RequestID = RequestID.trim();
		this.PackageName = PackageName.trim();
		this.Channel = Channel.toUpperCase().trim();
		this.AppName = AppName.trim();
		this.UserName = UserName.trim();
		this.IP = IP.trim();
		
		this.Note = Note.trim();
		this.mChannel = Common.GetChannelType(Channel);
		this.mVNPApp = Common.GetApplication(AppName);
		this.PID = MyConvert.GetPIDByMSISDN(MSISDN, LocalConfig.MAX_PID);
	}

	private void Init() throws Exception
	{
		try
		{
			mSub = new Subscriber(LocalConfig.mDBConfig_MSSQL);
			mUnSub = new UnSubscriber(LocalConfig.mDBConfig_MSSQL);
			mMOLog = new MOLog(LocalConfig.mDBConfig_MSSQL);

			mTable_MOLog = CurrentData.GetTable_MOLog();
			mTable_Sub = CurrentData.GetTable_Sub();
			mTable_UnSub = CurrentData.GetTable_UnSub();
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	private MTType AddToList()
	{
		try
		{
			if (MSISDN.startsWith("8484"))
				return mMTType;

			MTContent = Common.GetDefineMT_Message(mMTType);
			if (mSubObj.mVNPApp.mApp == VNPApplication.TelcoApplication.VASVOUCHER)
			{
				AddToMOLog(mMTType,"[Khong gui]"+ MTContent);
			}
			else
			{
				if (Common.SendMT(MSISDN, Keyword, MTContent, RequestID))
					AddToMOLog(mMTType, MTContent);
			}
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
		return mMTType;
	}
	
	/**
	 * Lấy thông tin MO từ VNP gửi sang
	 */
	private void GetMO()
	{
		try
		{
			String[] arr = Note.split("\\|");
			if(arr.length >=2)
			{
				Keyword = arr[1];
			}
		}
		catch(Exception ex)
		{
			mLog.log.error(ex);
		}
	}
	
	private void AddToMOLog(MTType mMTType_Current, String MTContent_Current) throws Exception
	{
		try
		{
			MOObject mMOObj = new MOObject(MSISDN, mChannel, mMTType_Current, Keyword, MTContent_Current, RequestID, PID, mCal_Current.getTime(), Calendar
					.getInstance().getTime(), mVNPApp, UserName, IP, mSubObj.PartnerID);

			mTable_MOLog = mMOObj.AddNewRow(mTable_MOLog);
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
	}

	private void Insert_MOLog()
	{
		try
		{
			mMOLog.Insert(0, mTable_MOLog.GetXML());
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
	}

	private boolean MoveToSub() throws Exception
	{
		try
		{
			mTable_UnSub.Clear();
			mTable_UnSub = mSubObj.AddNewRow(mTable_UnSub);

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

	protected void CreateUnSub() throws Exception
	{
		mSubObj.mChannelType = mChannel;
		mSubObj.DeregDate = mCal_Current.getTime();

		mSubObj.mVNPApp = mVNPApp;
		mSubObj.UserName = UserName;
		mSubObj.IP = IP;

	}

	public MTType Process()
	{
		try
		{
			//Khoi tao
			Init();

			GetMO();
			
			MyTableModel mTable_Sub = mSub.Select(2, PID.toString(), MSISDN);

			if (mTable_Sub.GetRowCount() > 0)
				mSubObj = SubscriberObject.Convert(mTable_Sub, false);

			mSubObj.PID = PID;

			// Nếu chưa đăng ký dịch vụ
			if (mSubObj.IsNull())
			{
				mMTType = MTType.DeregNotRegister;
				return AddToList();
			}

			CreateUnSub();

			if (ErrorCode.ChargeSuccess != mCharge.ChargeDereg(mSubObj, mSubObj.mChannelType, Keyword))
			{
				mMTType = MTType.RegFail;
				return AddToList();
			}

			if (MoveToSub())
			{
				mMTType = MTType.DeregSuccess;
				return AddToList();
			}

			mMTType = MTType.DeregFail;

			return AddToList();
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
			mMTType = MTType.DeregFail;
			return AddToList();
		}
		finally
		{
			// Insert vao log
			Insert_MOLog();
		}
	}

}
