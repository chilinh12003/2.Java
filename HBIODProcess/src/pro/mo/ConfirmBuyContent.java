package pro.mo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Vector;

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
import dat.content.ZodiacObject;
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
public class ConfirmBuyContent extends ContentAbstract
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

	DefineMT.MTType mMTType = MTType.ConfirmFail;

	/**
	 * ID của đối tác, khi đăng ký qua các kênh của đối tác
	 */
	int PartnerID = 0;
	int PID = 0;

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

			PID = MyConvert.GetPIDByMSISDN(mMsgObject.getUserid(), LocalConfig.MAX_PID);
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
			if (mMTType == MTType.ConfirmSuccess)
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
				mSubObj.RequestDate = mCal_Current.getTime();
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

		mServiceObj = CurrentData.Get_ServiceObj(mSubNewsObj.ServiceID);
	}

	/**
	 * Lấy tin tức từ Table SubNews
	 * 
	 * @throws Exception
	 */
	private void GetSubNews() throws Exception
	{
		String NewsKey = mMsgObject.getUsertext().replace(mMsgObject.getKeyword(), "");

		// Xóa tất cả ký tự đặc biệt, chỉ giữ lại ký tự là số
		NewsKey = MyText.RemoveSpecialLetter(1, NewsKey);

		MyTableModel mTable = mSubNews.Select(6, Integer.toString(PID), NewsKey,
				Integer.toString(SubNews.Status.WaitConfirm.GetValue()));

		if (mTable != null && mTable.GetRowCount() > 0)
		{
			Vector<SubNewsObject> mList = SubNewsObject.ConvertToList(mTable);
			if (mList.size() > 0)
			{
				mSubNewsObj = mList.get(0);

				mSubNewsObj.ConfirmDate = mMsgObject.getReceiveDate();
				mSubNewsObj.SendDate = Calendar.getInstance().getTime();
				mSubNewsObj.mStatus = SubNews.Status.Send;
			}
		}

	}

	/**
	 * Lấy 1 tin mới nhất mà khách hàng chưa confirn và chưa hết hạn
	 * 
	 * @throws Exception
	 */
	private SubNewsObject GetSubNews_Last() throws Exception
	{
		SubNewsObject mSubNewObj_Last = new SubNewsObject();
		String NewsKey = mMsgObject.getUsertext().replace(mMsgObject.getKeyword(), "");

		// Xóa tất cả ký tự đặc biệt, chỉ giữ lại ký tự là số
		NewsKey = MyText.RemoveSpecialLetter(1, NewsKey);

		MyTableModel mTable = mSubNews.Select(8, Integer.toString(PID), mMsgObject.getUserid(),
				Integer.toString(SubNews.Status.WaitConfirm.GetValue()));

		if (mTable != null && mTable.GetRowCount() > 0)
		{
			Vector<SubNewsObject> mList = SubNewsObject.ConvertToList(mTable);
			if (mList.size() > 0)
			{
				mSubNewObj_Last = mList.get(0);
			}
		}
		return mSubNewObj_Last;
	}

	private boolean Update_SubNews() throws Exception
	{
		try
		{
			mTable_SubNews.Clear();
			mTable_SubNews = mSubNewsObj.AddNewRow(mTable_SubNews);

			if (!mSubNews.Update(1, mTable_SubNews.GetXML()))
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

	protected Collection<MsgObject> getMessages(MsgObject msgObject, Keyword keyword) throws Exception
	{
		try
		{
			// Khoi tao
			Init(msgObject, keyword);

			GetSubNews();

			// Nếu tin tức chưa được tạo cho lần request
			if (mSubNewsObj.IsNull())
			{
				SubNewsObject mSubNewsObj_Last = GetSubNews_Last();

				if (mSubNewsObj_Last.IsNull())
				{
					//Chưa mua nội dung mà đã confirm
					mMTType = MTType.ConfirmNotBuy;
					return AddToList();
				}
				else
				{
					//Đã mua nội dung nhưng mã confirm bị sai
					mMTType = MTType.ConfirmInvalidContent;
					return AddToList();
				}
			}

			// Lấy dịch vụ
			GetService();

			// Lấy 1 tin tức chưa trả cho khách hàng
			mNewsObj = CurrentData.GetNews(mSubNewsObj.NewsID);

			if (mNewsObj.IsNull())
			{
				mMTType = MTType.ConfirmInvalidContent;
				return AddToList();
			}

			// Tiến hành trừ tiền.
			ErrorCode mChargeResult = Charge.BuyContent(PartnerID, mServiceObj, mNewsObj, mMsgObject.getUserid(),
					"BUY CONTENT", ChannelType.FromInt(mMsgObject.getChannelType()));

			// Charge
			if (mChargeResult == ErrorCode.BlanceTooLow)
			{
				mMTType = MTType.ConfirmNotEnoughMoney;
				return AddToList();
			}

			if (mChargeResult != ErrorCode.ChargeSuccess)
			{
				mMTType = MTType.ConfirmFail;
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
				CreateSub(InitType.NeverUse, mChargeResult);

				if (!Insert_Sub())
				{
					mMTType = MTType.ConfirmFail;
					return AddToList();
				}
			}
			else
			{
				CreateSub(InitType.Exist, mChargeResult);

				if (!Update_Sub())
				{
					mMTType = MTType.ConfirmFail;
					return AddToList();
				}
			}

			if (Update_SubNews())
			{
				mMTType = MTType.ConfirmSuccess;
				return AddToList();
			}

			mMTType = MTType.ConfirmFail;
			return AddToList();
		}
		catch (Exception ex)
		{
			mLog.log.error(Common.GetStringLog(msgObject), ex);
			mMTType = MTType.ConfirmFail;
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
