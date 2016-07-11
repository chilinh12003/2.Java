package pro.mo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pro.server.CurrentData;
import pro.server.LocalConfig;
import pro.server.ProcessMOAbstract;
import pro.server.Program;
import db.*;
import db.DefineMt.MTType;
import uti.MyDate;
import uti.MyConvert;
import uti.MyLogger;

public class RestoreReg extends ProcessMOAbstract
{
	MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath, this.getClass().toString());
	List<Mtqueue> listMTQueue = new ArrayList<Mtqueue>();

	Moqueue moQueueObj = null;
	Subscriber subObj = new Subscriber();
	SubLog mSubLog = new SubLog();
	Service serviceObj = null;

	Calendar mCal_Current = Calendar.getInstance();
	Calendar mCal_Expire = Calendar.getInstance();

	DefineMt.MTType mMTType = DefineMt.MTType.RestoreFail;

	String MTContent = "";

	Short PID = 0;

	/**
	 * ID của đối tác, khi đăng ký qua các kênh của đối tác
	 */
	Integer PartnerID = 0;
	int amount = 2000;

	private void Init(Moqueue moQueueObj, Keyword mKeyword) throws Exception
	{
		try
		{
			this.moQueueObj = moQueueObj;

			PID = ((Integer) MyConvert.GetPIDByMSISDN(moQueueObj.getPhoneNumber(), LocalConfig.MAX_PID)).shortValue();

			mCal_Expire.set(Calendar.MILLISECOND, 0);
			mCal_Expire.set(mCal_Current.get(Calendar.YEAR), mCal_Current.get(Calendar.MONTH),
					mCal_Current.get(Calendar.DATE), 23, 59, 59);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	private List<Mtqueue> AddToList() throws Exception
	{
		try
		{
			listMTQueue.clear();
			MTContent = Program.GetDefineMT_Message(mMTType);

			if (!MTContent.equalsIgnoreCase(""))
			{
				Mtqueue mtQueueObj = new Mtqueue(serviceObj, moQueueObj, PID, MTContent, mMTType, null);
				//mtQueueObj.setSendTypeID(Mtqueue.SendType.NotSend.GetValue());
				listMTQueue.add(mtQueueObj);
			}		

			return listMTQueue;
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

			if (!subObj.Update())
			{
				mLog.log.info("Update vao table Subscriber KHONG THANH CONG: XML Insert-->" + MyLogger.GetLog(subObj));
				return false;
			}

			// 1: Đăng ký, 2: Khôi phục,0: Hủy đăng ký
			SubLog mSubLog_Temp = new SubLog(subObj, (short) 2);
			mSubLog_Temp.Save();

			return true;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	protected void CreateSub(Subscriber.InitType mInitType) throws Exception
	{

		switch (mInitType)
		{
			case Restore :

				subObj.setExpiryDate(MyDate.Date2Timestamp(mCal_Expire));

				subObj.setChannelId(moQueueObj.getChannelId());
				subObj.setStatusId(Subscriber.Status.Active.GetValue());
				
				break;
			default :

				break;
		}
	}

	void InsertChargeLog()
	{
		try
		{
			if (mMTType != MTType.RestoreSuccess)
			{
				return;
			}

			ChargeLog chargeObj = new ChargeLog();

			ChargeLogId mID = new ChargeLogId();
			mID.setPid(subObj.getId().getPid());
			mID.setPhoneNumber(subObj.getId().getPhoneNumber());

			chargeObj.setId(mID);

			chargeObj.setChannelId(moQueueObj.getChannelId());
			chargeObj.setChargeDate(moQueueObj.getReceiveDate());
			chargeObj.setChargeTypeId(ChargeLog.ChargeType.Restore.GetValue());
			chargeObj.setStatusId(ChargeLog.Status.ChargeSuccess.GetValue());
			chargeObj.setLogDate(MyDate.Date2Timestamp(Calendar.getInstance()));
			chargeObj.setPartnerId(subObj.getPartnerId());
			chargeObj.setPirce((float) amount);

			if (!chargeObj.Save())
			{
				mLog.log.warn("RestoreRegister Save ChargeLog Fail:" + MyLogger.GetLog(chargeObj));
			}
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
	}

	protected List<Mtqueue> getMessages(Moqueue moQueueObj, Keyword mKeyword) throws Exception
	{
		try
		{
			// Khoi tao
			Init(moQueueObj, mKeyword);

			// Lấy thông tin khách hàng đã đăng ký
			subObj = subObj.GetSub(PID, moQueueObj.getPhoneNumber(),serviceObj);

			// Đang đăng ký nhưng dk lại
			if (subObj == null)
			{
				mMTType = MTType.RestoreNotReg;
				return AddToList();
			}

			if (subObj.getStatusId() == Subscriber.Status.Active.GetValue().shortValue())
			{
				mMTType = MTType.RestoreWhenActive;
				return AddToList();
			}

			CreateSub(Subscriber.InitType.Restore);

			if (Update_Sub())
			{
				mMTType = MTType.RestoreSuccess;
			}
			else
			{
				mMTType = MTType.RestoreFail;
			}
			return AddToList();
		}
		catch (Exception ex)
		{
			mLog.log.error(MyLogger.GetLog(moQueueObj));
			mLog.log.error(MyLogger.GetLog(subObj), ex);
			mMTType = MTType.RestoreFail;
			return AddToList();
		}
		finally
		{
			mLog.log.debug(MyLogger.GetLog(moQueueObj));
		}
	}
}