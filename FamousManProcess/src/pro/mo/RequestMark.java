package pro.mo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

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
import uti.utility.MyLogger;
import dat.content.DefineMT;
import dat.content.DefineMT.MTType;
import dat.content.SuggestObject;
import dat.history.MOLog;
import dat.history.MOObject;
import dat.history.Play;
import dat.history.Play.PlayType;
import dat.history.PlayObject;
import dat.history.SuggestCount;
import dat.history.SuggestCountObject;
import dat.sub.Subscriber;
import dat.sub.Subscriber.Status;
import dat.sub.SubscriberObject;
import dat.sub.UnSubscriber;
import db.define.MyTableModel;

public class RequestMark extends ContentAbstract
{
	MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath, this.getClass().toString());
	Collection<MsgObject> ListMessOject = new ArrayList<MsgObject>();

	MsgObject mMsgObject = null;
	SubscriberObject mSubObj = new SubscriberObject();

	Calendar mCal_Current = Calendar.getInstance();
	Calendar mCal_Begin = Calendar.getInstance();
	Calendar mCal_End = Calendar.getInstance();

	Subscriber mSub = null;
	MOLog mMOLog = null;
	dat.content.Keyword mKeyword = null;

	MyTableModel mTable_MOLog = null;
	MyTableModel mTable_Sub = null;

	DefineMT.MTType mMTType = MTType.BuySugFail;

	private void Init(MsgObject msgObject, Keyword keyword) throws Exception
	{
		try
		{
			mSub = new Subscriber(LocalConfig.mDBConfig_MSSQL);
			mMOLog = new MOLog(LocalConfig.mDBConfig_MSSQL);
			mKeyword = new dat.content.Keyword(LocalConfig.mDBConfig_MSSQL);

			mTable_MOLog = CurrentData.GetTable_MOLog();
			mTable_Sub = CurrentData.GetTable_Sub();

			mMsgObject = msgObject;

			mCal_Begin.set(Calendar.MILLISECOND, 0);
			mCal_Begin.set(mCal_Current.get(Calendar.YEAR), mCal_Current.get(Calendar.MONTH),
					mCal_Current.get(Calendar.DATE), 8, 0, 0);

			mCal_End.set(Calendar.MILLISECOND, 0);
			mCal_End.set(mCal_Current.get(Calendar.YEAR), mCal_Current.get(Calendar.MONTH),
					mCal_Current.get(Calendar.DATE), 23, 59, 59);
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

			String MTContent = Common.GetDefineMT_Message(mMTType);

			if (!MTContent.equalsIgnoreCase(""))
			{
				if (mMTType == MTType.RequestMarkSuccess)
				{
					Integer WeekMark = mSubObj.WeekMark + mSubObj.AddMark + mSubObj.AnswerMark
							+ mSubObj.ChargeMark + mSubObj.BuyMark;
					
					MTContent = MTContent.replace("[WeekMark]", WeekMark.toString());
				}
				
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
			MOObject mMOObj = new MOObject(mMsgObject.getUserid(), ChannelType.FromInt(mMsgObject.getChannelType()),
					mMTType_Current, mMsgObject.getMO(), MTContent_Current, mMsgObject.getRequestid().toString(),
					MyConvert.GetPIDByMSISDN(mMsgObject.getUserid(), LocalConfig.MAX_PID), mMsgObject.getReceiveDate(),
					Calendar.getInstance().getTime(), new VNPApplication(), null, null, mSubObj.PartnerID);

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
	

	protected Collection<MsgObject> getMessages(MsgObject msgObject, Keyword keyword) throws Exception
	{
		try
		{
			// Khoi tao
			Init(msgObject, keyword);

			Integer PID = MyConvert.GetPIDByMSISDN(mMsgObject.getUserid(), LocalConfig.MAX_PID);

			// Lấy thông tin khách hàng đã đăng ký
			MyTableModel mTable_Sub = mSub.Select(2, PID.toString(), mMsgObject.getUserid());

			mSubObj = SubscriberObject.Convert(mTable_Sub, false);

			// Chưa đăng ký
			if (mSubObj.IsNull())
			{
				mMTType = MTType.RequestMarkNotReg;
				return AddToList();
			}

			mMTType = MTType.RequestMarkSuccess;
			return AddToList();
		}
		catch (Exception ex)
		{
			mLog.log.error(Common.GetStringLog(msgObject), ex);
			mMTType = MTType.BuySugFail;
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
