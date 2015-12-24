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

	Calendar mCal_Current = Calendar.getInstance();
	Calendar mCal_Expire = Calendar.getInstance();

	DefineMt.MTType mMTType = DefineMt.MTType.RestoreFail;

	String MTContent = "";

	Short PID = 0;

	Suggest mSuggestObj = null;

	/**
	 * ID của đối tác, khi đăng ký qua các kênh của đối tác
	 */
	Integer PartnerID = 0;

	Mode mMode = Mode.Nothing;
	int amount = 0;
	private void Init(Moqueue moQueueObj) throws Exception
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

	public List<Mtqueue> AddToList() throws Exception
	{
		try
		{
			listMTQueue.clear();
			MTContent = Program.GetDefineMT_Message(mMTType);

			if (!MTContent.equalsIgnoreCase(""))
			{
				Mtqueue mtQueueObj = new Mtqueue(moQueueObj, PID, MTContent, mMTType, null);
				mtQueueObj.setSendTypeID(Mtqueue.SendType.NotSend.GetValue());
				listMTQueue.add(mtQueueObj);
			}

			if (mMTType == MTType.RestoreSuccess && mSuggestObj != null && mSuggestObj.getMt() != null && !mSuggestObj.getMt().equalsIgnoreCase(""))
			{
				String MTSuggest = Program.GetDefineMT_Message(MTType.RestoreSuggestMT);

				MTSuggest = MTSuggest.replace("[SuggestMT]", mSuggestObj.getMt());

				Mtqueue mtQueueObj = new Mtqueue(moQueueObj, PID, MTSuggest, MTType.RegSuggestMT, null);
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

				subObj.setExpiryDate(MyDate.Date2Timestamp(mCal_Expire));

				subObj.setChannelId(moQueueObj.getChannelId());
				subObj.setStatusId(Subscriber.Status.Active.GetValue());

				// Nếu là khôi phục lại trong tuần (ngày pedding cùng tuần với
				// ngày khổi phục)
				if (subObj.CheckIsWeek(mCal_Current))
				{
					
				}
				else
				{
					//Nếu là khác tuần thì các điểm tuần sẽ bị reset
					subObj.setWeekMark(0);
					subObj.setDayMark(0);
					subObj.setAddMark(0);
				}

				subObj.setChargeMark(LocalConfig.RegMark);
				subObj.setBuyMark(0);
				subObj.setAnswerMark(0);

				// Lấy dữ kiện cho thuê bao
				mSuggestObj = CurrentData.Get_SuggestObj(1);

				if (mSuggestObj != null)
				{
					subObj.setLastSuggestId(mSuggestObj.getSuggestId());

					subObj.setSuggestByDay(1);
					subObj.setTotalSuggest(mSubLog.getTotalSuggest() + 1);
					subObj.setLastSuggestDate(MyDate.Date2Timestamp(mCal_Current));
				}
				else
				{
					subObj.setSuggestByDay(0);
					subObj.setLastSuggestId(0);
				}
				
				subObj.setAnswerForSuggestId(0);
				subObj.setLastAnswer("");
				subObj.setAnswerStatusId(Play.Status.Nothing.GetValue());
				subObj.setAnswerByDay(0);
				subObj.setLastAnswerDate(null);


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

	public MTType getMessages(Moqueue moQueueObj, subscribe.Mode mMode, int amount) throws Exception
	{
		try
		{
			this.mMode = mMode;
			this.amount = amount;
			// Khoi tao
			Init(moQueueObj);

			// Lấy thông tin khách hàng đã đăng ký
			subObj = subObj.GetSub(PID, moQueueObj.getPhoneNumber());

			// Đang đăng ký nhưng dk lại
			if (subObj == null)
			{
				mMTType = MTType.RestoreNotReg;
				return mMTType;
			}

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
		}
	}
}
