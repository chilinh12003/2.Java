package pro.Server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import my.ws.subscribe;
import my.ws.subscribe.Mode;
import db.*;
import db.DefineMt.MTType;
import uti.MyDate;
import uti.MyConvert;
import uti.MyLogger;

public class RestoreRegister
{
	MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath(), this.getClass().toString());
	List<Mtqueue> listMTQueue = new ArrayList<Mtqueue>();

	Moqueue moQueueObj = null;
	Subscriber subObj = new Subscriber();
	SubLog mSubLog = new SubLog();
	Service serviceObj =null;

	Calendar calCurrent = Calendar.getInstance();
	Calendar calExpire = Calendar.getInstance();

	DefineMt.MTType mMTType = DefineMt.MTType.RestoreFail;

	String MTContent = "";

	Short PID = 0;

	/**
	 * ID của đối tác, khi đăng ký qua các kênh của đối tác
	 */
	Integer PartnerID = 0;

	Mode mMode = Mode.Nothing;
	int amount = 0;

	String LogBeforeSub = "";
	
	private void Init(Moqueue moQueueObj) throws Exception
	{
		try
		{
			this.moQueueObj = moQueueObj;

			PID = ((Integer) MyConvert.GetPIDByMSISDN(moQueueObj.getPhoneNumber(), LocalConfig.MAX_PID)).shortValue();

			calExpire.set(Calendar.MILLISECOND, 0);
			calExpire.set(calCurrent.get(Calendar.YEAR), calCurrent.get(Calendar.MONTH),
					calCurrent.get(Calendar.DATE), 23, 59, 59);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public List<Mtqueue> AddToList() throws Exception
	{
		try
		{
			listMTQueue.clear();
			MTContent = Program.GetDefineMT_Message(mMTType, serviceObj);

			if (!MTContent.equalsIgnoreCase(""))
			{
				Mtqueue mtQueueObj = new Mtqueue(serviceObj,moQueueObj, PID, MTContent, mMTType, null);
				mtQueueObj.setSendTypeId(Mtqueue.SendType.NotSend.GetValue());
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
			if (mMode == Mode.Check)
				return true;

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

				subObj.setExpiryDate(MyDate.Date2Timestamp(calExpire));

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
			if (this.mMode != Mode.Real || mMTType != MTType.RestoreSuccess)
			{
				return;
			}

			ChargeLog chargeObj = new ChargeLog();

			ChargeLogId mID = new ChargeLogId();
			mID.setPid(subObj.getId().getPid());
			mID.setPhoneNumber(subObj.getId().getPhoneNumber());
			mID.setServiceId(serviceObj.getServiceId());
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

	public MTType getMessages(Moqueue moQueueObj, subscribe.Mode mMode, int amount, Service serviceObj) throws Exception
	{
		try
		{
			this.mMode = mMode;
			this.amount = amount;
			this.serviceObj = serviceObj;
			
			// Khoi tao
			Init(moQueueObj);

			// Lấy thông tin khách hàng đã đăng ký
			subObj = subObj.GetSub(PID, moQueueObj.getPhoneNumber(), serviceObj);

			// Đang đăng ký nhưng dk lại
			if (subObj == null)
			{
				mMTType = MTType.RestoreNotReg;
				return mMTType;
			}
			
			LogBeforeSub = MyLogger.GetLog("BEFORE_SUB:", subObj);
			
			if (subObj.getStatusId() == Subscriber.Status.Active.GetValue().shortValue())
			{
				mMTType = MTType.RestoreWhenActive;
				return mMTType;
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
			return mMTType;
		}
		catch (Exception ex)
		{
			mLog.log.error(MyLogger.GetLog(moQueueObj));
			mLog.log.error(MyLogger.GetLog(subObj), ex);
			mMTType = MTType.RestoreFail;
			return mMTType;
		}
		finally
		{
			InsertChargeLog();
			mLog.log.debug(MyLogger.GetLog(moQueueObj));
			mLog.log.debug(LogBeforeSub);
			mLog.log.debug(MyLogger.GetLog("AFTER_SUB:", subObj));
		}
	}
}
