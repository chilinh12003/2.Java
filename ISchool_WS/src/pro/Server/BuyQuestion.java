package pro.Server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import my.ws.getcontent.Mode;
import my.ws.subscribe;
import db.*;
import db.DefineMt.MTType;
import db.Play.PlayType;
import uti.MyDate;
import uti.MyConvert;
import uti.MyLogger;

public class BuyQuestion
{
	MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath(), this.getClass().toString());
	List<Mtqueue> listMTQueue = new ArrayList<Mtqueue>();

	Moqueue moQueueObj = null;
	Subscriber subObj = new Subscriber();

	Question questionObj = new Question();

	Calendar mCal_Current = Calendar.getInstance();
	Calendar mCal_Begin = Calendar.getInstance();
	Calendar mCal_End = Calendar.getInstance();

	Short PID = 0;
	DefineMt.MTType mMTType = MTType.BuyFail;

	Mode mMode = Mode.Nothing;
	int amount = 0;
	String LogBeforeSub ="";
	
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
					mCal_Current.get(Calendar.DATE), 22, 0, 0);
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
			if (mMTType == MTType.BuyOneSuccess || mMTType == MTType.BuyTwoSuccess)
			{
				String MTContent = Program.GetDefineMT_Message(mMTType);

				if (!MTContent.equalsIgnoreCase(""))
				{
					// Tin nhắn sẽ trả về cho MPS và MPS sẽ trả về cho KH
					Mtqueue mtQueueObj = new Mtqueue(moQueueObj, PID, MTContent, mMTType, null);
					mtQueueObj.setSendTypeID(Mtqueue.SendType.NotSend.GetValue());
					listMTQueue.add(mtQueueObj);
				}

				if (questionObj.getMt() != null && !questionObj.getMt().equalsIgnoreCase(""))
				{
					String BuyQuestiongestMT = Program.GetDefineMT_Message(DefineMt.MTType.BuyQuestionMT);
					BuyQuestiongestMT = BuyQuestiongestMT.replace("[QuestionMT]", questionObj.getMt());

					Calendar calPushTime = Calendar.getInstance();

					Mtqueue mtQueueObj = new Mtqueue(moQueueObj, PID, BuyQuestiongestMT, DefineMt.MTType.BuyQuestionMT,
							MyDate.Date2Timestamp(calPushTime));

					listMTQueue.add(mtQueueObj);
				}
			}
			else
			{
				String MTContent = Program.GetDefineMT_Message(mMTType);

				if (!MTContent.equalsIgnoreCase(""))
				{
					Mtqueue mtQueueObj = new Mtqueue(moQueueObj, PID, MTContent, mMTType, null);
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
		subObj.setLastBuyDate(MyDate.Date2Timestamp(mCal_Current));
		subObj.setLastQuestionId(questionObj.getQuestionId());

		int questionCount = subObj.getQuestionCount();
		if (amount == 1000)
		{
			questionCount += 3;
			subObj.setBuyMark(subObj.getBuyMark() + LocalConfig.Buy1Mark);

			if (subObj.getBuyType().shortValue() == Subscriber.BuyQuestionType.QuestionFree.GetValue().shortValue())
			{
				subObj.setBuyType(Subscriber.BuyQuestionType.BuyOneQuestion.GetValue());
			}
			else
			{
				subObj.setBuyType(Subscriber.BuyQuestionType.BuyTwoOneQuestion.GetValue());
			}
		}
		else
		{
			questionCount += 7;
			subObj.setBuyMark(subObj.getBuyMark() + LocalConfig.Buy2Mark);

			if (subObj.getBuyType().shortValue() == Subscriber.BuyQuestionType.QuestionFree.GetValue().shortValue())
			{
				subObj.setBuyType(Subscriber.BuyQuestionType.BuyTwoQuestion.GetValue());
			}
			else
			{
				subObj.setBuyType(Subscriber.BuyQuestionType.BuyOneTwoQuestion.GetValue());
			}
		}
		subObj.setQuestionCount(questionCount);
		subObj.setSendCount(subObj.getSendCount() == null ? 1 : subObj.getSendCount() + 1);

		// Mỗi lần mua là phải reset lại câu trả lời đúng của bộ câu hỏi đã mua.
		// để có thể dự vào đây để tính điểm thưởng sau khi trả lời đúng hết bộ
		// câu hỏi
		subObj.setAnswerRightBuyType(0);
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
			mID.setPhoneNumber(subObj.getId().getPhoneNumber());
			playObj.setId(mID);
			playObj.setPid(subObj.getId().getPid());

			playObj.setSessionId(questionObj.getSessionId());
			playObj.setQuestionId(questionObj.getQuestionId());
			playObj.setSendCount(subObj.getSendCount());
			playObj.setAnswerCount(subObj.getAnswerCount());
			playObj.setAnswerRight(subObj.getAnswerRight());
			playObj.setPlayDate(CurrentData.getCurrentSession(false).getPlayDate());
			playObj.setPlayTypeId(Play.PlayType.BuyQuestion.GetValue());
			playObj.setReceiveDate(moQueueObj.getReceiveDate());
			// playObj.setUserAnswer(userAnswer);
			playObj.setStatusId(Play.Status.BuyQuestion.GetValue());
			playObj.setWeekMark(subObj.getWeekMark());
			playObj.setDayMark(subObj.getDayMark());
			playObj.setAddMark(subObj.getAddMark());
			playObj.setChargeMark(subObj.getChargeMark());
			playObj.setBuyMark(subObj.getBuyMark());
			playObj.setAnswerMark(subObj.getAnswerMark());
			playObj.setPromotionMark(subObj.getPromotionMark());
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
			subObj.Update();
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
			// Nếu mua không thành công hoặc mode = check thì không lưu xuống
			// chargelog
			if (this.mMode != Mode.Real || ((mMTType != MTType.BuyOneSuccess) && (mMTType != MTType.BuyTwoSuccess)))
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
			chargeObj.setChargeTypeId(ChargeLog.ChargeType.BuyContent.GetValue());
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
	public MTType getMessages(Moqueue moQueueObj, Mode mMode, int amount) throws Exception
	{
		try
		{
			this.mMode = mMode;
			this.amount = amount;
			// Khoi tao
			Init(moQueueObj);

			// Lấy thông tin khách hàng đã đăng ký
			subObj = subObj.GetSub(PID, moQueueObj.getPhoneNumber());

			// Chưa đăng ký
			if (subObj == null)
			{
				mMTType = MTType.BuyNotReg;
				return mMTType;
			}

			LogBeforeSub = MyLogger.GetLog("BEFORE_SUB:",subObj);
			
			// Phiên chơi chưa bắt đầu
			if (mCal_Current.before(mCal_Begin) || mCal_Current.after(mCal_End) || !CurrentData.checkSessionValid())
			{
				mMTType = MTType.BuyExpire;
				return mMTType;
			}

			// Tình trạng không hợp lệ
			if (subObj.getStatusId().equals(Subscriber.Status.Pending.GetValue()))
			{
				if (amount == 1000)
					mMTType = MTType.BuyOneNotExtend;
				else mMTType = MTType.BuyTwoNotExtend;
				return mMTType;
			}

			// Chưa trả lời xong câu hỏi thì không được mua
			if ((subObj.getBuyType().shortValue() == Subscriber.BuyQuestionType.QuestionFree.GetValue().shortValue() && subObj
					.getAnswerCount().intValue() < 5)
					|| (subObj.getBuyType().shortValue() == Subscriber.BuyQuestionType.BuyOneQuestion.GetValue()
							.shortValue() && subObj.getAnswerCount().intValue() < 8)
					|| (subObj.getBuyType().shortValue() == Subscriber.BuyQuestionType.BuyTwoQuestion.GetValue()
							.shortValue() && subObj.getAnswerCount().intValue() < 12))
			{
				mMTType = MTType.Invalid;
				return mMTType;
			}

			// Nếu đã mua 2 bộ câu hỏi rồi
			if (subObj.getBuyType().shortValue() == Subscriber.BuyQuestionType.BuyOneTwoQuestion.GetValue()
					.shortValue()
					|| subObj.getBuyType().shortValue() == Subscriber.BuyQuestionType.BuyTwoOneQuestion.GetValue()
							.shortValue())
			{
				mMTType = MTType.BuyLimit;
				return mMTType;
			}

			// Nếu đã mua bộ 3 câu hỏi rồi
			if (amount == 1000
					&& subObj.getBuyType().shortValue() == Subscriber.BuyQuestionType.BuyOneQuestion.GetValue()
							.shortValue())
			{
				mMTType = MTType.BuyOneLimit;
				return mMTType;
			}

			// Nếu đã mua bộ 7 câu hỏi rồi
			if (amount == 2000
					&& subObj.getBuyType().shortValue() == Subscriber.BuyQuestionType.BuyTwoQuestion.GetValue()
							.shortValue())
			{
				mMTType = MTType.BuyTwoLimit;
				return mMTType;
			}

			questionObj = CurrentData.getNextQuestion(subObj);
			if (questionObj == null)
			{
				mLog.log.warn("Cau hoi khong lay duoc, kiem tra ngay");
				mLog.log.warn(MyLogger.GetLog(subObj));

				mMTType = MTType.BuyFail;
				return mMTType;
			}

			CreateUpdateSub();

			// Cập nhật thông tin vào DB
			UpdateSubInfo();

			Insert_Play();

			if (amount == 1000)
				mMTType = MTType.BuyOneSuccess;
			else mMTType = MTType.BuyTwoSuccess;

			return mMTType;
		}
		catch (Exception ex)
		{
			mLog.log.error(MyLogger.GetLog(moQueueObj), ex);
			mMTType = MTType.BuyFail;
			return mMTType;
		}
		finally
		{
			InsertChargeLog();
			mLog.log.debug(MyLogger.GetLog(moQueueObj));
			mLog.log.debug(LogBeforeSub);
			mLog.log.debug( MyLogger.GetLog("AFTER_SUB:",subObj));
		}
	}
}
