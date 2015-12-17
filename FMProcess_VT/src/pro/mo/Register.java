package pro.mo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import db.*;
import db.DefineMt.MTType;
import pro.server.CurrentData;
import pro.server.LocalConfig;
import pro.server.ProcessMOAbstract;
import pro.server.Program;
import uti.MyDate;
import uti.MyConvert;
import uti.MyLogger;

public class Register extends ProcessMOAbstract
{
	MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath, this.getClass().toString());
	List<Mtqueue> listMTQueue = new ArrayList<Mtqueue>();

	Moqueue moQueueObj = null;
	Subscriber mSubObj = new Subscriber();
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
				Mtqueue mtQueueObj = new Mtqueue(moQueueObj, PID, MTContent, mMTType,null);

				listMTQueue.add(mtQueueObj);
			}

			if (mSuggestObj != null && mSuggestObj.getMt() != null && !mSuggestObj.getMt().equalsIgnoreCase(""))
			{
				String MTSuggest = Program.GetDefineMT_Message(MTType.RegSuggestMT);

				MTSuggest = MTSuggest.replace("[SuggestMT]", mSuggestObj.getMt());

				Mtqueue mtQueueObj = new Mtqueue(moQueueObj, PID, MTSuggest, MTType.RegSuggestMT,null);

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
			if (!mSubObj.Save())
			{
				mLog.log.info("Insert vao table Subscriber KHONG THANH CONG: XML Insert-->" + MyLogger.GetLog(mSubObj));
				return false;
			}

			SubLog mSubLog_Temp = new SubLog(mSubObj, (short) 1);
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
				mSubObj = new Subscriber();
				SubscriberId mSubID = new SubscriberId();
				mSubID.setPhoneNumber(moQueueObj.getPhoneNumber());
				mSubID.setPid(PID);

				mSubObj.setId(mSubID);
				mSubObj.setFirstDate(MyDate.Date2Timestamp(mCal_Current));
				mSubObj.setResetDate(MyDate.Date2Timestamp(mCal_Current));
				mSubObj.setEffectiveDate(MyDate.Date2Timestamp(mCal_Current));
				mSubObj.setExpiryDate(MyDate.Date2Timestamp(mCal_Expire));

				mSubObj.setChannelId(moQueueObj.getChannelId());
				mSubObj.setStatusId(Subscriber.Status.Active.GetValue());

				mSubObj.setSuggestByDay(1);
				mSubObj.setLastSuggestDate(MyDate.Date2Timestamp(mCal_Current));
				
				mSubObj.setTotalSuggest(mSubObj.getTotalSuggest() + 1);

				// Lấy dữ kiện cho thuê bao
				mSuggestObj = CurrentData.Get_SuggestObj(mSubObj.getSuggestByDay());
				mSubObj.setLastSuggestId(mSuggestObj.getSuggestId());
				
				mSubObj.setChargeMark(LocalConfig.RegMark);

				mSubObj.setPartnerId(0);

				break;
			case RegAgain :

				mSubObj.setEffectiveDate(MyDate.Date2Timestamp(mCal_Current));
				mSubObj.setExpiryDate(MyDate.Date2Timestamp(mCal_Expire));

				mSubObj.setChannelId(moQueueObj.getChannelId());
				mSubObj.setStatusId(Subscriber.Status.Active.GetValue());

				// Nếu hủy, đăng ký lại trong ngày, thì thông tin này giữ nguyên
				if (mSubObj.CheckLastSuggestDate(mCal_Current))
				{
					if( (mSubObj.getAnswerByDay().intValue() < LocalConfig.MaxAnswerByDay.intValue()
							&& mSubObj.getAnswerStatusId().shortValue() != Play.Status.CorrectAnswer.GetValue()
							.shortValue()))
					{
						// Chỉ lấy dữ kiện cho trường hợp
						//-Trong ngày ĐK, sau đó hủy và ĐK lại.KH chua dự đoán đúng/còn quyền dự đoán
						mSuggestObj = CurrentData.Get_SuggestObj(mSubObj.getSuggestByDay());
					}
				}
				else 
				{
					mSubObj.setSuggestByDay(1);
					mSubObj.setTotalSuggest(mSubObj.getTotalSuggest() + 1);
					mSubObj.setLastSuggestDate(MyDate.Date2Timestamp(mCal_Current));
					
					mSuggestObj = CurrentData.Get_SuggestObj(mSubObj.getSuggestByDay());
					
					if (mSuggestObj != null)
					mSubObj.setLastSuggestId(mSuggestObj.getSuggestId());
				}

				if (!mSubObj.CheckIsWeek(mCal_Current))
				{
					mSubObj.setAnswerForSuggestId(0);
					mSubObj.setLastAnswer("");
					mSubObj.setAnswerStatusId(Play.Status.Nothing.GetValue());
					mSubObj.setAnswerByDay(0);
					mSubObj.setLastAnswerDate(null);
				}

				// Khi hủy tất cả các điểm đều bị mất				
				mSubObj.setChargeMark(LocalConfig.RegMark);
				mSubObj.setAddMark(0);
				mSubObj.setBuyMark(0);
				mSubObj.setAnswerMark(0);

				mSubObj.setPartnerId(0);

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
			if(mMTType != MTType.RegNewSuccess)
			{
				return;
			}
			
			ChargeLog chargeObj = new ChargeLog();
			
			ChargeLogId mID = new ChargeLogId();
			mID.setPid(mSubObj.getId().getPid());
			mID.setPhoneNumber(mSubObj.getId().getPhoneNumber());
			
			chargeObj.setId(mID);

			chargeObj.setChannelId(moQueueObj.getChannelId());
			chargeObj.setChargeDate(moQueueObj.getReceiveDate());
			chargeObj.setChargeTypeId(ChargeLog.ChargeType.Register.GetValue());
			chargeObj.setLogDate(MyDate.Date2Timestamp(Calendar.getInstance()));
			chargeObj.setPartnerId(mSubObj.getPartnerId());
			chargeObj.setPirce((float) 2000);
			
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
	
	protected List<Mtqueue> getMessages(Moqueue moQueueObj, Keyword mKeyword) throws Exception
	{
		try
		{
			// Khoi tao
			Init(moQueueObj, mKeyword);

			// Lấy thông tin khách hàng đã đăng ký
			mSubObj = mSubObj.GetSub(PID, moQueueObj.getPhoneNumber());

			// Đang đăng ký nhưng dk lại
			if (mSubObj != null)
			{
				mMTType = MTType.RegRepeatFree;
				return AddToList();
			}

			if (mSubObj == null)
			{
				// Lấy thông tin thuê bao đã từng đăng ký và đã hủy
				SubLog mUnsubObj = mSubLog.GetSub(PID, moQueueObj.getPhoneNumber());
				if (mUnsubObj != null)
					mSubObj = new Subscriber(mUnsubObj);
			}

			// Đăng ký mới (chưa từng đăng ký trước đây)
			if (mSubObj == null)
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

				return AddToList();
			}
			// Đã đăng ký trước đó nhưng đang hủy
			else if (mSubObj != null)
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
				return AddToList();
			}

			mMTType = MTType.RegFail;
			return AddToList();
		}
		catch (Exception ex)
		{
			mLog.log.error(MyLogger.GetLog(moQueueObj));
			mLog.log.error(MyLogger.GetLog(mSubObj), ex);
			mMTType = MTType.RegFail;
			return AddToList();
		}
		finally
		{
			InsertChargeLog();
			mLog.log.debug(MyLogger.GetLog(moQueueObj));
			
		}
	}
}
