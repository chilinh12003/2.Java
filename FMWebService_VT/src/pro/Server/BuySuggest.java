package pro.Server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import my.ws.getcontent.Mode;
import db.*;
import db.DefineMt.MTType;
import db.Play.PlayType;
import uti.MyDate;
import uti.MyConvert;
import uti.MyLogger;

public class BuySuggest
{
	MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath(), this.getClass().toString());
	List<Mtqueue> listMTQueue = new ArrayList<Mtqueue>();

	Moqueue moQueueObj = null;
	Subscriber mSubObj = new Subscriber();

	Suggest mSuggestObj = new Suggest();

	Calendar mCal_Current = Calendar.getInstance();
	Calendar mCal_Begin = Calendar.getInstance();
	Calendar mCal_End = Calendar.getInstance();

	Short PID = 0;
	DefineMt.MTType mMTType = MTType.BuySugFail;

	Mode mMode = Mode.Nothing;
	int amount = 0;
	private void Init(Moqueue moQueueObj) throws Exception
	{
		try
		{
			this.moQueueObj = moQueueObj;

			PID = ((Integer) MyConvert.GetPIDByMSISDN(moQueueObj.getPhoneNumber(), LocalConfig.MAX_PID)).shortValue();

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

	public List<Mtqueue> AddToList() throws Exception
	{
		try
		{
			listMTQueue.clear();
			if (mMTType == MTType.BuySugSuccess)
			{
				String MTContent = Program.GetDefineMT_Message(mMTType);

				if (!MTContent.equalsIgnoreCase(""))
				{
					//Tin nhắn sẽ trả về cho MPS và MPS sẽ trả về cho KH
					Mtqueue mtQueueObj = new Mtqueue(moQueueObj, PID, MTContent, mMTType,null);
					mtQueueObj.setSendTypeID(Mtqueue.SendType.NotSend.GetValue());
					listMTQueue.add(mtQueueObj);
				}
				
				if (mSuggestObj.getMt() != null && !mSuggestObj.getMt().equalsIgnoreCase(""))
				{
					String BuySuggestMT = Program.GetDefineMT_Message(DefineMt.MTType.BuySuggestMT);
					BuySuggestMT = BuySuggestMT.replace("[SuggestMT]", mSuggestObj.getMt());
					
					Calendar calPushTime = Calendar.getInstance();
					
					Mtqueue mtQueueObj = new Mtqueue(moQueueObj, PID, BuySuggestMT, DefineMt.MTType.BuySuggestMT,MyDate.Date2Timestamp(calPushTime));
					
					listMTQueue.add(mtQueueObj);
				}
			}
			else
			{
				String MTContent = Program.GetDefineMT_Message(mMTType);

				if (!MTContent.equalsIgnoreCase(""))
				{
					Mtqueue mtQueueObj = new Mtqueue(moQueueObj, PID, MTContent, mMTType,null);
					mtQueueObj.setSendTypeID(Mtqueue.SendType.NotSend.GetValue());
					listMTQueue.add(mtQueueObj);
				}
			}

			return listMTQueue;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	/**
	 * Lấy dữ kiện để trả về, đồng thời tạo các thông tin cần Update xuống DB
	 * 
	 * @throws Exception
	 */
	private void CreateUpdateSub() throws Exception
	{
		mSubObj.setLastSuggestDate(MyDate.Date2Timestamp(mCal_Current));
		mSubObj.setLastSuggestId(mSuggestObj.getSuggestId());
		Integer totalSuggest = mSubObj.getTotalSuggest() + 1;
		mSubObj.setTotalSuggest(totalSuggest);

		// mSubObj.setSuggestByDay(); Đã được tăng trước đó

		mSubObj.setBuyMark(mSubObj.getBuyMark() + LocalConfig.BuyMark);
	}

	/**
	 * Thêm vào log Mua dữ kiện và trả lời
	 * 
	 * @return
	 */
	private boolean Insert_Play()
	{
		if (mMode == Mode.Check)
			return true;

		try
		{
			Play playObj = new Play();
			PlayId mID = new PlayId();
			mID.setPhoneNumber(mSubObj.getId().getPhoneNumber());
			playObj.setId(mID);
			playObj.setPid(mSubObj.getId().getPid());

			playObj.setPlayTypeId(PlayType.BuySuggest.GetValue());
			playObj.setStatusId(Play.Status.BuySuggest.GetValue());
			playObj.setOrderNumber(mSuggestObj.getOrderNumber());
			playObj.setQuestionId(mSuggestObj.getQuestionId());
			playObj.setPlayDate(CurrentData.Get_Current_QuestionObj().getPlayDate());
			playObj.setReceiveDate(moQueueObj.getReceiveDate());
			playObj.setSuggestId(mSuggestObj.getSuggestId());

			playObj.setWeekMark(mSubObj.getWeekMark());
			playObj.setDayMark(mSubObj.getDayMark());
			playObj.setAddMark(mSubObj.getAddMark());
			playObj.setChargeMark(mSubObj.getChargeMark());
			playObj.setBuyMark(mSubObj.getBuyMark());
			playObj.setAnswerMark(mSubObj.getAnswerMark());

			playObj.setInsertDate(MyDate.Date2Timestamp(Calendar.getInstance()));

			return playObj.Save();
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
			return false;
		}
	}

	/**
	 * Update thông tin mua dữ kiện vào Sub
	 * 
	 * @return
	 */
	private boolean UpdateSubInfo()
	{
		if (mMode == Mode.Check)
			return true;

		try
		{
			mSubObj.Update();
			return true;
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
			return false;
		}

	}

	void InsertChargeLog()
	{
		try
		{
			if(this.mMode != Mode.Real || mMTType != MTType.BuySugSuccess)
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
			chargeObj.setChargeTypeId(ChargeLog.ChargeType.BuyContent.GetValue());
			chargeObj.setStatusId(ChargeLog.Status.ChargeSuccess.GetValue());
			chargeObj.setLogDate(MyDate.Date2Timestamp(Calendar.getInstance()));
			chargeObj.setPartnerId(mSubObj.getPartnerId());
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
	public MTType getMessages(Moqueue moQueueObj, Mode mMode, int amount) throws Exception
	{
		try
		{
			this.mMode = mMode;
			this.amount =amount;
			// Khoi tao
			Init(moQueueObj);

			// Lấy thông tin khách hàng đã đăng ký
			mSubObj = mSubObj.GetSub(PID, moQueueObj.getPhoneNumber());

			// Chưa đăng ký
			if (mSubObj == null)
			{
				mMTType = MTType.BuySugNotReg;
				return mMTType;
			}

			// Tình trạng không hợp lệ
			if (mSubObj.getStatusId().equals(Subscriber.Status.Pending.GetValue()))
			{
				mMTType = MTType.BuySugNotExtend;
				return mMTType;
			}

			// Phiên chơi chưa bắt đầu
			if (mCal_Current.before(mCal_Begin) || mCal_Current.after(mCal_End)
					|| CurrentData.Get_Current_QuestionObj() == null
					|| CurrentData.Get_Current_SuggestObj().size() < 1)
			{
				mMTType = MTType.BuySugExpire;
				return mMTType;
			}

			// Kiểm tra mua vượt quá giới hạn
			if (mSubObj.CheckLastSuggestDate(mCal_Current)
					&& mSubObj.getSuggestByDay().intValue() >= LocalConfig.MaxBuySuggestByDay.intValue())
			{
				mMTType = MTType.BuySugLimit;
				return mMTType;
			}

			// Đã trả lời đúng trước đó thì không được chơi nữa
			if (mSubObj.CheckLastAnswerDate(mCal_Current)
					&& mSubObj.getAnswerStatusId().shortValue() == Play.Status.CorrectAnswer.GetValue().shortValue())
			{
				mMTType = MTType.BuySugAnswerRight;
				return mMTType;
			}

			if (mSubObj.CheckLastSuggestDate(mCal_Current))
			{
				mSubObj.setSuggestByDay(mSubObj.getSuggestByDay() + 1);
			}
			else
			{
				mSubObj.setSuggestByDay(1);
			}

			mSuggestObj = CurrentData.Get_SuggestObj(mSubObj.getSuggestByDay());
			if (mSuggestObj == null)
			{
				mLog.log.warn("Du kien khong lay duoc, kiem tra ngay");
				mLog.log.warn(MyLogger.GetLog(mSubObj));

				mMTType = MTType.BuySugFail;
				return mMTType;
			}

			CreateUpdateSub();

			// Cập nhật thông tin vào DB
			UpdateSubInfo();

			Insert_Play();

			mMTType = MTType.BuySugSuccess;
			return mMTType;
		}
		catch (Exception ex)
		{
			mLog.log.error(MyLogger.GetLog(moQueueObj), ex);
			mMTType = MTType.BuySugFail;
			return mMTType;
		}
		finally
		{
			InsertChargeLog();
			mLog.log.debug(MyLogger.GetLog(moQueueObj));
		}
	}
}
