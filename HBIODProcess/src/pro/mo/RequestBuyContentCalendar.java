package pro.mo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import pro.charge.Charge;
import pro.charge.Charge.ErrorCode;
import pro.server.Common;
import pro.server.ContentAbstract;
import pro.server.CurrentData;
import pro.server.Keyword;
import pro.server.LocalConfig;
import pro.server.MsgObject;
import uti.utility.MyConfig.ChannelType;
import uti.utility.VNPApplication;
import uti.utility.MyConvert;
import uti.utility.MyDate;
import uti.utility.MyLogger;
import uti.utility.MyText;
import dat.content.DefineMT;
import dat.content.DefineMT.MTType;
import dat.content.NewsObject;
import dat.content.ServiceObject;
import dat.history.MOLog;
import dat.history.MOObject;
import dat.sub.SubNews;
import dat.sub.SubNewsObject;
import dat.sub.Subscriber;
import dat.sub.Subscriber.Status;
import dat.sub.SubscriberObject;
import dat.sub.SubscriberObject.InitType;
import db.define.MyTableModel;

/**
 * Yêu cầu mua nội dung từ MenuSim của Vinaphone
 * 
 * @author Administrator
 * 
 */
public class RequestBuyContentCalendar extends ContentAbstract
{
	MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath, this.getClass().toString());
	Collection<MsgObject> ListMessOject = new ArrayList<MsgObject>();

	MsgObject mMsgObject = null;
	SubscriberObject mSubObj = new SubscriberObject();

	SubNewsObject mSubNewsObj = new SubNewsObject();

	/**
	 * Dịch vụ KH đang cần mua nội dung
	 */
	ServiceObject mServiceObj = new ServiceObject();

	/**
	 * Bản tin cần trả về cho khách hàng để confirm download
	 */
	NewsObject mNewsObj = new NewsObject();

	Calendar mCal_Current = Calendar.getInstance();
	Calendar mCal_Expire = Calendar.getInstance();

	Subscriber mSub = null;
	MOLog mMOLog = null;
	dat.content.Keyword mKeyword = null;
	SubNews mSubNews = null;

	MyTableModel mTable_MOLog = null;
	MyTableModel mTable_Sub = null;
	MyTableModel mTable_SubNews = null;

	pro.charge.Charge mCharge = new Charge();
	DefineMT.MTType mMTType = MTType.RequestFail;

	/**
	 * ID của đối tác, khi đăng ký qua các kênh của đối tác
	 */
	Integer PartnerID = 0;

	/**
	 * Ngày khách hàng cần tra cứu Lịch vạn sự
	 */
	Date UserDate = new Date();

	private void Init(MsgObject msgObject, Keyword keyword) throws Exception
	{
		try
		{
			mSub = new Subscriber(LocalConfig.mDBConfig_MSSQL);
			mMOLog = new MOLog(LocalConfig.mDBConfig_MSSQL);
			mKeyword = new dat.content.Keyword(LocalConfig.mDBConfig_MSSQL);
			mSubNews = new SubNews(LocalConfig.mDBConfig_MSSQL);

			mTable_MOLog = CurrentData.GetTable_MOLog();
			mTable_Sub = CurrentData.GetTable_Sub();
			mTable_SubNews = CurrentData.GetTable_SubNews();

			mMsgObject = msgObject;

			mCal_Expire.set(Calendar.MILLISECOND, 0);
			mCal_Expire.set(Calendar.MINUTE, LocalConfig.TIME_EXPIRE_CONFIRM);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	private Collection<MsgObject> AddToList() throws Exception
	{
		try
		{
			ListMessOject.clear();
			String MTContent = "";

			if (mMTType == MTType.RequestSuccess)
			{
				MTContent = mNewsObj.MT;
			}
			else
			{
				MTContent = Common.GetDefineMT_Message(mMTType);
			}
			
			if (!MTContent.equalsIgnoreCase(""))
			{
				mMsgObject.setUsertext(MTContent);
				mMsgObject.setContenttype(LocalConfig.LONG_MESSAGE_CONTENT_TYPE);
				mMsgObject.setMsgtype(1);
				ListMessOject.add(new MsgObject((MsgObject) mMsgObject.clone()));
				AddToMOLog(mMTType, MTContent);
			}

			return ListMessOject;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	private void AddToMOLog(MTType mMTType_Current, String MTContent_Current) throws Exception
	{
		try
		{
			MOObject mMOObj = new MOObject(mServiceObj.ServiceID, mMsgObject.getUserid(),
					ChannelType.FromInt(mMsgObject.getChannelType()), mMTType_Current, mMsgObject.getMO(),
					MTContent_Current, mMsgObject.getRequestid().toString(), MyConvert.GetPIDByMSISDN(
							mMsgObject.getUserid(), LocalConfig.MAX_PID), mMsgObject.getReceiveDate(), Calendar
							.getInstance().getTime(), new VNPApplication(), null, null, PartnerID);

			mTable_MOLog = mMOObj.AddNewRow(mTable_MOLog);
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
	}

	private void Insert_MOLog() throws Exception
	{
		try
		{
			MOLog mMOLog = new MOLog(LocalConfig.mDBConfig_MSSQL);
			mMOLog.Insert(0, mTable_MOLog.GetXML());
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
	}

	private boolean Insert_Sub() throws Exception
	{
		try
		{
			mTable_Sub.Clear();
			mTable_Sub = mSubObj.AddNewRow(mTable_Sub);

			if (!mSub.Insert(0, mTable_Sub.GetXML()))
			{
				mLog.log.info("Insert vao table Subscriber KHONG THANH CONG: XML Insert-->" + mTable_Sub.GetXML());
				return false;
			}

			return true;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	private boolean Update_Sub() throws Exception
	{
		try
		{
			mTable_Sub.Clear();
			mTable_Sub = mSubObj.AddNewRow(mTable_Sub);

			if (!mSub.Update(0, mTable_Sub.GetXML()))
			{
				mLog.log.info("Update vao table Subscriber KHONG THANH CONG: XML Update-->" + mTable_Sub.GetXML());
				return false;
			}

			return true;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	protected void CreateSub(SubscriberObject.InitType mInitType, ErrorCode mChargeResult) throws Exception
	{

		switch (mInitType)
		{
			case NeverUse :
				mSubObj = new SubscriberObject();
				mSubObj.ServiceID = mServiceObj.ServiceID;
				mSubObj.MSISDN = mMsgObject.getUserid();
				mSubObj.FirstDate = mCal_Current.getTime();
				mSubObj.mChannelType = ChannelType.FromInt(mMsgObject.getChannelType());
				mSubObj.mStatus = Status.Active;
				mSubObj.PID = MyConvert.GetPIDByMSISDN(mMsgObject.getUserid(), LocalConfig.MAX_PID);
				mSubObj.MOByDay = 1;
				mSubObj.MOTotal = 1;
				mSubObj.MTByDay = 1;
				mSubObj.MTTotal = 1;
				mSubObj.RequestByDay = 1;
				mSubObj.RequestTotal = 1;
				mSubObj.RequestDate = mMsgObject.getReceiveDate();
				mSubObj.ConfirmByDay = 1;
				mSubObj.ConfirmTotal = 1;
				mSubObj.ConfirmDate = mCal_Current.getTime();
				;
				mSubObj.ChargeDate = mCal_Current.getTime();
				mSubObj.ChargeByDay = 1;
				mSubObj.ChargeTotal = 1;

				if (mChargeResult == ErrorCode.ChargeSuccess)
				{
					mSubObj.ChargeSuccessDate = mCal_Current.getTime();
					mSubObj.ChargeSuccessByDay = 1;
					mSubObj.ChargeSuccessTotal = 1;
				}

				mSubObj.NotifyDate = null;
				mSubObj.PartnerID = PartnerID;

				break;

			case Exist :
				mSubObj.mChannelType = ChannelType.FromInt(mMsgObject.getChannelType());
				mSubObj.mStatus = Status.Active;

				mSubObj.MOTotal++;
				mSubObj.MTTotal++;

				mSubObj.ConfirmTotal++;

				// Count MO, MT theo ngay
				if (MyDate.IsToday(mSubObj.RequestDate))
				{
					mSubObj.MOByDay++;
					mSubObj.MTByDay++;
				}
				else
				{
					mSubObj.MOByDay = 1;
					mSubObj.MTByDay = 1;
				}
				// Count confirm theo ngay
				if (MyDate.IsToday(mSubObj.ConfirmDate))
				{
					mSubObj.ConfirmByDay++;
				}
				else
				{
					mSubObj.ConfirmByDay = 1;
				}

				mSubObj.ConfirmDate = mCal_Current.getTime();

				// Count Charge
				mSubObj.ChargeTotal++;
				if (MyDate.IsToday(mSubObj.ChargeDate))
				{
					mSubObj.ChargeByDay++;
				}
				else
				{
					mSubObj.ChargeByDay = 1;
				}
				mSubObj.ChargeDate = mCal_Current.getTime();

				// Count Charge thành công
				if (mChargeResult == ErrorCode.ChargeSuccess)
				{
					if (MyDate.IsToday(mSubObj.ChargeSuccessDate))
					{
						mSubObj.ChargeSuccessByDay++;
					}
					else
					{
						mSubObj.ChargeSuccessByDay = 1;
					}

					mSubObj.ChargeSuccessDate = mCal_Current.getTime();

					mSubObj.ChargeSuccessTotal++;
				}

				mSubObj.PartnerID = PartnerID;
				break;
			default :
				break;
		}
	}
	/**
	 * Lấy dịch vụ dựa vào keyword
	 * 
	 * @throws Exception
	 */
	private void GetService() throws Exception
	{
		String Keyword = mMsgObject.getKeyword();
		mServiceObj = CurrentData.Get_ServiceObj(Keyword);
	}

	private void CreateSubNews() throws Exception
	{
		mSubNewsObj.NewsKey = CurrentData.GetSubNewsKey();
		mSubNewsObj.RequestDate = mMsgObject.getReceiveDate();
		mSubNewsObj.ExpireDate = mCal_Expire.getTime();
		mSubNewsObj.MSISDN = mSubObj.MSISDN;
		mSubNewsObj.mStatus = SubNews.Status.WaitConfirm;
		mSubNewsObj.NewsID = mNewsObj.NewsID;
		mSubNewsObj.PID = mSubObj.PID;
		mSubNewsObj.ServiceID = mServiceObj.ServiceID;
	}

	private boolean Insert_SubNews() throws Exception
	{
		try
		{
			mTable_SubNews.Clear();
			mTable_SubNews = mSubNewsObj.AddNewRow(mTable_SubNews);

			if (!mSubNews.Insert(0, mTable_SubNews.GetXML()))
			{
				mLog.log.info("Insert vao table SubNews KHONG THANH CONG: XML Insert-->" + mTable_SubNews.GetXML());
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
	 * Lấy ngày tháng mà khách hàng gửi lên
	 * 
	 * @return
	 */
	private boolean GetDate()
	{
		try
		{
			String Value_UserDate = mMsgObject.getUsertext().replace(mMsgObject.getKeyword(), "");

			if (Value_UserDate.equalsIgnoreCase(""))
				return false;
			Value_UserDate = MyText.RemoveSpecialLetter(1, Value_UserDate, "*-/\\.");
			String[] Arr = Value_UserDate.split("\\W");

			if (Arr.length != 3)
				return false;

			int Day = Integer.parseInt(Arr[0]);
			int Month = Integer.parseInt(Arr[1]);
			int Year = Integer.parseInt(Arr[2]);

			Calendar mCal_UserDate = Calendar.getInstance();
			mCal_UserDate.set(Year, Month - 1, Day);

			UserDate = mCal_UserDate.getTime();
			return true;
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
			return true;
		}

	}
	protected Collection<MsgObject> getMessages(MsgObject msgObject, Keyword keyword) throws Exception
	{
		try
		{
			// Khoi tao
			Init(msgObject, keyword);

			// Lấy dịch vụ
			GetService();

			// Nếu dịch vụ hông tồn tại
			if (mServiceObj.IsNull())
			{
				mMTType = MTType.RequestFail;
				return AddToList();
			}

			if (!GetDate())
			{
				mMTType = MTType.Invalid;
				return AddToList();
			}
			int PID = MyConvert.GetPIDByMSISDN(mMsgObject.getUserid(), LocalConfig.MAX_PID);

			// Lấy 1 tin tức chưa trả cho khách hàng
			mNewsObj = CurrentData.GetNews(mServiceObj, UserDate);

			if (mNewsObj.IsNull())
			{
				mMTType = MTType.RequestNoNews;
				return AddToList();
			}

			// Tiến hành trừ tiền.
			ErrorCode mChargeResult = Charge.BuyContent(PartnerID, mServiceObj, mNewsObj, mMsgObject.getUserid(),
					"BUY CONTENT", ChannelType.FromInt(mMsgObject.getChannelType()));

			// Charge
			if (mChargeResult == ErrorCode.BlanceTooLow)
			{
				mMTType = MTType.RequestEnoughMoney;
				return AddToList();
			}

			if (mChargeResult != ErrorCode.ChargeSuccess)
			{
				mMTType = MTType.RequestChargeFail;
				return AddToList();
			}
			// Lấy thông tin khách hàng đã đăng ký
			MyTableModel mTable_Sub = mSub.Select(2, Integer.toString(PID), mMsgObject.getUserid(),
					Integer.toString(mServiceObj.ServiceID));

			if (mTable_Sub.GetRowCount() > 0)
			{
				mSubObj = SubscriberObject.Convert(mTable_Sub.GetRow(0));
			}
			
			// Đăng ký mới (chưa từng đăng ký trước đây)
			if (mSubObj.IsNull())
			{
				// Tạo dữ liệu cho đăng ký mới
				CreateSub(InitType.NeverUse,mChargeResult);

				if (!Insert_Sub())
				{
					mMTType = MTType.RequestFail;
					return AddToList();
				}
			}
			else
			{
				CreateSub(InitType.Exist,mChargeResult);

				if (!Update_Sub())
				{
					mMTType = MTType.RequestFail;
					return AddToList();
				}
			}
			
			// Tạo SubNews
			CreateSubNews();

			if (Insert_SubNews())
			{
				mMTType = MTType.RequestSuccess;
				return AddToList();
			}

			mMTType = MTType.RequestFail;
			return AddToList();

		}
		catch (Exception ex)
		{
			mLog.log.error(Common.GetStringLog(msgObject), ex);
			mMTType = MTType.RequestFail;
			return AddToList();
		}
		finally
		{
			// Insert vao log
			Insert_MOLog();
			mLog.log.debug(Common.GetStringLog(mMsgObject));
		}
	}
}
