package pro.Server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import my.ws.subscribe;
import my.ws.subscribe.Mode;
import db.*;
import db.DefineMt.MTType;
import uti.MyConfig;
import uti.MyDate;
import uti.MyConvert;
import uti.MyLogger;

public class Register 
{
	MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath(), this.getClass().toString());
	List<Mtqueue> listMTQueue = new ArrayList<Mtqueue>();

	Moqueue moQueueObj = null;
	Subscriber subObj = new Subscriber();
	SubLog mSubLog = new SubLog();

	Calendar mCal_Current = Calendar.getInstance();
	Calendar mCal_Expire = Calendar.getInstance();

	DefineMt.MTType mMTType = DefineMt.MTType.RegFail;

	String MTContent = "";

	Short PID = 0;

	Suggest mSuggestObj = null;

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

			if (mMTType == MTType.RegNewSuccess &&  mSuggestObj != null && mSuggestObj.getMt() != null && !mSuggestObj.getMt().equalsIgnoreCase(""))
			{
				String MTSuggest = Program.GetDefineMT_Message(MTType.RegSuggestMT);

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

	private boolean Insert_Sub() throws Exception
	{
		try
		{
			if(mMode == Mode.Check)
				return true;
			
			if (!subObj.Save())
			{
				mLog.log.info("Insert vao table Subscriber KHONG THANH CONG: XML Insert-->" + MyLogger.GetLog(subObj));
				return false;
			}

			SubLog mSubLog_Temp = new SubLog(subObj, (short) 1);
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
			case NewReg :
				subObj = new Subscriber();
				SubscriberId mSubID = new SubscriberId();
				mSubID.setPhoneNumber(moQueueObj.getPhoneNumber());
				mSubID.setPid(PID);

				subObj.setId(mSubID);
				subObj.setFirstDate(MyDate.Date2Timestamp(mCal_Current));
				subObj.setResetDate(MyDate.Date2Timestamp(mCal_Current));
				subObj.setEffectiveDate(MyDate.Date2Timestamp(mCal_Current));
				subObj.setExpiryDate(MyDate.Date2Timestamp(mCal_Expire));

				subObj.setChannelId(moQueueObj.getChannelId());
				subObj.setStatusId(Subscriber.Status.Active.GetValue());

				subObj.setSuggestByDay(1);
				subObj.setLastSuggestDate(MyDate.Date2Timestamp(mCal_Current));
				
				subObj.setTotalSuggest(subObj.getTotalSuggest() + 1);

				// Lấy dữ kiện cho thuê bao
				mSuggestObj = CurrentData.Get_SuggestObj(subObj.getSuggestByDay());

				if (mSuggestObj != null)
				subObj.setLastSuggestId(mSuggestObj.getSuggestId());
				
				
				subObj.setWeekMark(0);
				subObj.setDayMark(0);
				subObj.setAddMark(0);
				subObj.setBuyMark(0);
				subObj.setAnswerMark(0);
				subObj.setChargeMark(LocalConfig.RegMark);

				subObj.setPartnerId(0);

				break;
			case RegAgain :

				subObj.setEffectiveDate(MyDate.Date2Timestamp(mCal_Current));
				subObj.setExpiryDate(MyDate.Date2Timestamp(mCal_Expire));

				subObj.setChannelId(moQueueObj.getChannelId());
				subObj.setStatusId(Subscriber.Status.Active.GetValue());

				// Nếu hủy, đăng ký lại trong ngày, thì thông tin này giữ nguyên
				if (subObj.CheckLastSuggestDate(mCal_Current))
				{
					if( (subObj.getAnswerByDay().intValue() < LocalConfig.MaxAnswerByDay.intValue()
							&& subObj.getAnswerStatusId().shortValue() != Play.Status.CorrectAnswer.GetValue()
							.shortValue()))
					{
						
						// Chỉ lấy dữ kiện cho trường hợp
						//-Trong ngày ĐK, sau đó hủy và ĐK lại.KH chua dự đoán đúng/còn quyền dự đoán
						mSuggestObj = CurrentData.Get_SuggestObj(subObj.getSuggestByDay());
					}
				}
				else 
				{
					subObj.setSuggestByDay(1);
					subObj.setTotalSuggest(subObj.getTotalSuggest() + 1);
					subObj.setLastSuggestDate(MyDate.Date2Timestamp(mCal_Current));
					
					mSuggestObj = CurrentData.Get_SuggestObj(subObj.getSuggestByDay());
					
					if (mSuggestObj != null)
					subObj.setLastSuggestId(mSuggestObj.getSuggestId());
				}

				if (!subObj.CheckIsWeek(mCal_Current))
				{
					subObj.setAnswerForSuggestId(0);
					subObj.setLastAnswer("");
					subObj.setAnswerStatusId(Play.Status.Nothing.GetValue());
					subObj.setAnswerByDay(0);
					subObj.setLastAnswerDate(null);
				}

				// Khi hủy tất cả các điểm đều bị mất
				subObj.setWeekMark(0);
				subObj.setDayMark(0);
				subObj.setChargeMark(LocalConfig.RegMark);
				subObj.setAddMark(0);
				subObj.setBuyMark(0);
				subObj.setAnswerMark(0);

				subObj.setPartnerId(0);

				break;
			case UndoReg :

				break;
			case Nothing :

				break;
			default :

				break;
		}
	}

	void InsertChargeLog()
	{
		try
		{
			if(this.mMode != Mode.Real || mMTType != MTType.RegNewSuccess)
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
			chargeObj.setChargeTypeId(ChargeLog.ChargeType.Register.GetValue());
			chargeObj.setStatusId(ChargeLog.Status.ChargeSuccess.GetValue());
			chargeObj.setLogDate(MyDate.Date2Timestamp(Calendar.getInstance()));
			chargeObj.setPartnerId(subObj.getPartnerId());
			chargeObj.setPirce((float) amount);
			
			if(!chargeObj.Save())
			{
				mLog.log.warn("Register Save ChargeLog Fail:" +MyLogger.GetLog(chargeObj));
			}
		}
		catch(Exception ex)
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

			//Lấy thông tin khách hàng đã đăng ký
			
			Subscriber mSubscriber = new Subscriber();
			subObj = mSubscriber.GetSub(PID, moQueueObj.getPhoneNumber());

			// Đang đăng ký nhưng dk lại
			if (subObj != null)
			{
				mMTType = MTType.RegRepeatFree;
				return mMTType;
			}

			if (subObj == null)
			{
				SubLog sublog = new SubLog();
				// Lấy thông tin thuê bao đã từng đăng ký và đã hủy
				SubLog mUnsubObj = sublog.GetSub(PID, moQueueObj.getPhoneNumber());
				if (mUnsubObj != null)
				{
					subObj = new Subscriber(mUnsubObj);
					LogBeforeSub = MyLogger.GetLog("BEFORE_SUB:", subObj);
				}
			}

			// Đăng ký mới (chưa từng đăng ký trước đây)
			if (subObj == null)
			{
				// Tạo dữ liệu cho đăng ký mới
				CreateSub(Subscriber.InitType.NewReg);

				if (Insert_Sub())
				{
					mMTType = MTType.RegNewSuccess;
				}
				else
				{
					mMTType = MTType.RegFail;
				}

				return mMTType;
			}
			// Đã đăng ký trước đó nhưng đang hủy
			else if (subObj != null)
			{
				CreateSub(Subscriber.InitType.RegAgain);
				if (Insert_Sub())
				{					
					mMTType = MTType.RegNewSuccess;
				}
				else
				{
					mMTType = MTType.RegFail;
				}
				return mMTType;
			}

			mMTType = MTType.RegFail;
			return mMTType;
		}
		catch (Exception ex)
		{
			mLog.log.error(MyLogger.GetLog(moQueueObj));
			mLog.log.error(MyLogger.GetLog(subObj), ex);
			mMTType = MTType.RegFail;
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
