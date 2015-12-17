package pro.mo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import pro.server.Common;
import pro.server.ContentAbstract;
import pro.server.Keyword;
import pro.server.LocalConfig;
import pro.server.MsgObject;
import uti.utility.MyConfig;
import uti.utility.MyConvert;
import uti.utility.MyLogger;
import dat.service.DefineMT;
import dat.service.MOLog;
import dat.service.ServiceObject;
import dat.service.DefineMT.MTType;
import dat.sub.SubscriberObject;
import db.define.MyDataRow;
import db.define.MyTableModel;

/**
 * 
 * @author Administrator
 * 
 */
public class InvalidProcess extends ContentAbstract
{
	MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath,this.getClass().toString());
	Collection<MsgObject> ListMessOject = new ArrayList<MsgObject>();

	MsgObject mMsgObject = null;
	SubscriberObject mSubObj = new SubscriberObject();

	ServiceObject mServiceObj = new ServiceObject();
	Calendar mCal_Current = Calendar.getInstance();
	Calendar mCal_SendMO = Calendar.getInstance();
	Calendar mCal_Expire = Calendar.getInstance();

	MOLog mMOLog = null;
	dat.service.Keyword mKeyword = null;

	MyTableModel mTable_MOLog = null;

	DefineMT.MTType mMTType = MTType.Invalid;

	String MTContent = "";
	
	String FreeTime = "7 ngay";
	
	private void Init(MsgObject msgObject, Keyword keyword) throws Exception
	{
		try
		{
			mMOLog = new MOLog(LocalConfig.mDBConfig_MSSQL);
			mKeyword = new dat.service.Keyword(LocalConfig.mDBConfig_MSSQL);

			mTable_MOLog = mMOLog.Select(0);

			mMsgObject = msgObject;

			mCal_SendMO.setTime(mMsgObject.getTTimes());

			mCal_Expire.set(Calendar.MILLISECOND, 0);
			mCal_Expire.set(mCal_Current.get(Calendar.YEAR), mCal_Current.get(Calendar.MONTH),
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
			MTContent = Common.GetDefineMT_Message(mMTType, mServiceObj);

			MTContent = MTContent.replace("[FreeTime]", FreeTime);
			MTContent = MTContent.replace("[ExpireDate]",
					MyConfig.Get_DateFormat_VNShortSlash().format(mCal_Expire.getTime()));

			mMsgObject.setUsertext(MTContent);

			mMsgObject.setContenttype(21);
			mMsgObject.setMsgtype(1);

			ListMessOject.add(new MsgObject(mMsgObject));
			return ListMessOject;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	private void Insert_MOLog() throws Exception
	{
		try
		{
			mTable_MOLog.Clear();
			MyDataRow mRow_Log = mTable_MOLog.CreateNewRow();

			mRow_Log.SetValueCell("ServiceID", mServiceObj.ServiceID);
			mRow_Log.SetValueCell("MSISDN", mMsgObject.getUserid());
			mRow_Log.SetValueCell("ReceiveDate", MyConfig.Get_DateFormat_InsertDB().format(mCal_SendMO.getTime()));
			mRow_Log.SetValueCell("LogDate", MyConfig.Get_DateFormat_InsertDB().format(mCal_Current.getTime()));
			mRow_Log.SetValueCell("ChannelTypeID", mMsgObject.getChannelType());
			mRow_Log.SetValueCell("ChannelTypeName", MyConfig.ChannelType.FromInt(mMsgObject.getChannelType())
					.toString());
			mRow_Log.SetValueCell("MTTypeID", mMTType.GetValue());
			mRow_Log.SetValueCell("MTTypeName", mMTType.toString());
			mRow_Log.SetValueCell("MO", mMsgObject.getMO());
			mRow_Log.SetValueCell("MT", MTContent);
			mRow_Log.SetValueCell("LogContent", "");
			mRow_Log.SetValueCell("PID", MyConvert.GetPIDByMSISDN(mMsgObject.getUserid(), LocalConfig.MAX_PID));
			mRow_Log.SetValueCell("RequestID", mMsgObject.getRequestid().toString());

			mRow_Log.SetValueCell("PartnerID", mSubObj.PartnerID);

			mTable_MOLog.AddNewRow(mRow_Log);

			mMOLog.Insert(0, mTable_MOLog.GetXML());
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
	}
	
	@Override
	protected Collection<MsgObject> getMessages(MsgObject msgObject, Keyword keyword) throws Exception
	{
		try
		{
			Init(msgObject, keyword);			
			return AddToList();
		}
		catch (Exception ex)
		{
			mLog.log.error(Common.GetStringLog(msgObject), ex);
			mMTType = MTType.SystemError;
			return AddToList();
		}
		finally
		{
			mLog.log.debug(Common.GetStringLog(mMsgObject));
			Insert_MOLog();
		}

	}	

}
