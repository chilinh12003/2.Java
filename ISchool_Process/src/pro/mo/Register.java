
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
	Subscriber subObj = new Subscriber();
	SubLog mSubLog = new SubLog();
	News newsObj = null;
	
	Calendar mCal_Current = Calendar.getInstance();
	Calendar mCal_Expire = Calendar.getInstance();

	DefineMt.MTType mMTType = DefineMt.MTType.RegFail;

	String MTContent = "";

	Short PID = 0;

	Question questionObj = null;

	/**
	 * ID của đối tác, khi đăng ký qua các kênh của đối tác
	 */
	Integer PartnerID = 0;

	int amount = 0;
	String LogBeforeSub = "";
	
	private void Init(Moqueue moQueueObj, Keyword mKeyword) throws Exception
	{
		try
		{
			this.moQueueObj = moQueueObj;

			PID = ((Integer) MyConvert.GetPIDByMSISDN(moQueueObj.getPhoneNumber(), LocalConfig.MAX_PID)).shortValue();

			mCal_Expire.set(Calendar.MILLISECOND, 0);
			mCal_Expire.set(mCal_Current.get(Calendar.YEAR), mCal_Current.get(Calendar.MONTH),
					mCal_Current.get(Calendar.DATE), 23, 59, 59);

			questionObj = CurrentData.getQuestionByOrder(1);
			
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
				Mtqueue mtQueueObj = new Mtqueue(moQueueObj, PID, MTContent, mMTType, null);
				mtQueueObj.setSendTypeID(Mtqueue.SendType.NotSend.GetValue());
				listMTQueue.add(mtQueueObj);
			}

			if (questionObj != null && questionObj.getMt() != null && !questionObj.getMt().equalsIgnoreCase(""))
			{
				String MTSuggest = Program.GetDefineMT_Message(MTType.RegQuestionMT);

				MTSuggest = MTSuggest.replace("[QuestionMT]", questionObj.getMt());

				Calendar calPushTime = Calendar.getInstance();

				Mtqueue mtQueueObj = new Mtqueue(moQueueObj, PID, MTSuggest, MTType.RegQuestionMT,
						MyDate.Date2Timestamp(calPushTime));
				listMTQueue.add(mtQueueObj);
			}
			
			if(mMTType == MTType.RegNewSuccess && newsObj != null && newsObj.getMt()!= null && !newsObj.getMt().equalsIgnoreCase(""))
			{
				String NewsMT = newsObj.getMt();
				Calendar calPushTime = Calendar.getInstance();
				
				Mtqueue mtQueueObj = new Mtqueue(moQueueObj, PID, NewsMT, MTType.RegNewsMT,
						MyDate.Date2Timestamp(calPushTime));
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
				subObj.setChargeMark(LocalConfig.RegMark);
				subObj.setPartnerId(0);

				subObj.setLastBuyDate(null);
				subObj.setBuyType(Subscriber.BuyQuestionType.QuestionFree.GetValue());
				subObj.setQuestionCount(LocalConfig.FreeQuestionCount);
				subObj.setSendCount(1);
				subObj.setAnswerCount(0);
				subObj.setAnswerRight(0);
				subObj.setAnswerRightBuyType(0);
				subObj.setLastAnswerDate(null);
				if (questionObj != null)
					subObj.setLastQuestionId(questionObj.getQuestionId());
				subObj.setLastAnswer(null);

				subObj.setWeekMark(0);
				subObj.setDayMark(0);
				subObj.setAddMark(0);
				subObj.setChargeMark(LocalConfig.RegMark);
				subObj.setBuyMark(0);
				subObj.setAnswerMark(0);
				subObj.setPromotionMark(0);

				break;
			case RegAgain :

				subObj.setEffectiveDate(MyDate.Date2Timestamp(mCal_Current));
				subObj.setExpiryDate(MyDate.Date2Timestamp(mCal_Expire));

				subObj.setChannelId(moQueueObj.getChannelId());
				subObj.setStatusId(Subscriber.Status.Active.GetValue());

				subObj.setPartnerId(0);

				subObj.setChannelId(moQueueObj.getChannelId());
				subObj.setStatusId(Subscriber.Status.Active.GetValue());
				subObj.setPartnerId(0);

				subObj.setLastBuyDate(null);
				subObj.setBuyType(Subscriber.BuyQuestionType.QuestionFree.GetValue());
				subObj.setQuestionCount(LocalConfig.FreeQuestionCount);
				subObj.setSendCount(1);
				subObj.setAnswerCount(0);
				subObj.setAnswerRight(0);
				subObj.setAnswerRightBuyType(0);
				subObj.setLastAnswerDate(null);
				if (questionObj != null)
					subObj.setLastQuestionId(questionObj.getQuestionId());
				subObj.setLastAnswer(null);

				subObj.setWeekMark(0);
				subObj.setDayMark(0);
				subObj.setAddMark(0);
				subObj.setChargeMark(LocalConfig.RegMark);
				subObj.setBuyMark(0);
				subObj.setAnswerMark(0);
				subObj.setPromotionMark(0);

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
			chargeObj.setPrice((float) amount);

			if (!chargeObj.Save())
			{
				mLog.log.warn("Register Save ChargeLog Fail:" + MyLogger.GetLog(chargeObj));
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

			Subscriber mSubscriber = new Subscriber();
			subObj = mSubscriber.GetSub(PID, moQueueObj.getPhoneNumber());

			// Đang đăng ký nhưng dk lại
			if (subObj != null)
			{
				mMTType = MTType.RegRepeatFree;
				return AddToList();
			}

			if (subObj == null)
			{
				SubLog sublog = new SubLog();
				// Lấy thông tin thuê bao đã từng đăng ký và đã hủy
				SubLog mUnsubObj = sublog.GetSub(PID, moQueueObj.getPhoneNumber());
				if (mUnsubObj != null)
				{
					subObj = new Subscriber(mUnsubObj);
					if(!subObj.IsSameDayWithDereg(mCal_Current))
					{
						//nếu đăng ký khác ngày với ngày Hủy thì tính tiền là 2000
						amount = 2000;
					}
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
					//Lấy tin tức mới nhất trong ngày gửi cho kh
					newsObj = CurrentData.getNewsToday();
				}
				else
				{
					mMTType = MTType.RegFail;
				}
				
				return AddToList();
			}
			// Đã đăng ký trước đó nhưng đang hủy
			else if (subObj != null)
			{
				Calendar mCal_EffectiveDate = Calendar.getInstance();
				mCal_EffectiveDate.setTimeInMillis(subObj.getEffectiveDate().getTime());
				
				
				CreateSub(Subscriber.InitType.RegAgain);
				if (Insert_Sub())
				{
					mMTType = MTType.RegNewSuccess;
					if(!MyDate.Compare(mCal_EffectiveDate, mCal_Current, Calendar.DATE))
					{
						//Lấy tin tức mới nhất trong ngày gửi cho kh
						newsObj = CurrentData.getNewsToday();
					}
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
			mLog.log.error(MyLogger.GetLog(subObj), ex);
			mMTType = MTType.RegFail;
			return AddToList();
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