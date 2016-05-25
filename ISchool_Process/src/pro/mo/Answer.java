package pro.mo;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import db.*;
import db.DefineMt.MTType;
import db.Play.PlayType;
import pro.server.CurrentData;
import pro.server.LocalConfig;
import pro.server.ProcessMOAbstract;
import pro.server.Program;
import uti.MyDate;
import uti.MyConvert;
import uti.MyLogger;
import uti.MyText;

public class Answer extends ProcessMOAbstract
{
	MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath, this.getClass().toString());
	List<Mtqueue> listMTQueue = new ArrayList<Mtqueue>();

	Moqueue moQueueObj = null;
	Subscriber subObj = new Subscriber();

	Question questionObj = new Question();

	Calendar mCal_Current = Calendar.getInstance();
	Calendar mCal_Begin = Calendar.getInstance();
	Calendar mCal_End = Calendar.getInstance();

	Short PID = 0;
	DefineMt.MTType mMTType = MTType.AnswerFail;

	String UserAnswer = "";

	// Trình trạng của Thuê bao khi bắt đầu xử lý.
	String LogBeforeSub = "";
	private void Init(Moqueue moQueueObj, Keyword mKeyword) throws Exception
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

	private List<Mtqueue> AddToList() throws Exception
	{
		try
		{
			listMTQueue.clear();

			String MTContent = Program.GetDefineMT_Message(mMTType);

			if (mMTType == MTType.AnswerRight || mMTType == MTType.AnswerWrong)
			{
				if (questionObj != null)
					MTContent = MTContent.replace("[QuestionMT]", questionObj.getMt());
			}

			if (!MTContent.equalsIgnoreCase(""))
			{
				Mtqueue mtQueueObj = new Mtqueue(moQueueObj, PID, MTContent, mMTType, null);
				listMTQueue.add(mtQueueObj);
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
		int AnswerMark = 0;
		int PromotionMark = 0;
		
		Question lastQuestion = CurrentData.getQuestionById(subObj.getLastQuestionId());
		
		//Nếu như lastquestion == null thì lấy câu hỏi đầu tiên của ngày hôm nay
		if(lastQuestion == null)
		{
			lastQuestion = CurrentData.getQuestionByOrder(1);
		}
		
		// Kiểm tra xem câu trả lời có đúng hay không
		if (UserAnswer.equalsIgnoreCase(lastQuestion.getRightAnswer()))
		{
			subObj.setAnswerStatusId(Play.Status.CorrectAnswer.GetValue());
			AnswerMark = LocalConfig.AnswerMark;

			subObj.setAnswerRightBuyType(subObj.getAnswerRightBuyType() == null
					? 1
					: subObj.getAnswerRightBuyType() + 1);

			subObj.setAnswerRight(subObj.getAnswerRight() == null ? 1 : subObj.getAnswerRight() + 1);

			mMTType = MTType.AnswerRight;
		}
		else
		{
			subObj.setAnswerStatusId(Play.Status.IncorrectAnswer.GetValue());
			mMTType = MTType.AnswerWrong;
		}

		subObj.setAnswerCount(subObj.getAnswerCount() == null ? 1 : subObj.getAnswerCount() + 1);
		subObj.setLastAnswerDate(MyDate.Date2Timestamp(mCal_Current));
		subObj.setLastAnswer(UserAnswer);

		subObj.setAnswerMark((subObj.getAnswerMark() == null ? 0 : subObj.getAnswerMark()) + AnswerMark);

		// Nếu đang trả lời bộ 5 câu hỏi free
		if (subObj.getBuyType().shortValue() == Subscriber.BuyQuestionType.QuestionFree.GetValue().shortValue())
		{
			// Nếu là câu trả lời cuối (lần thứ 5) của bộ 5 câu hỏi
			if (subObj.getAnswerCount() == 5)
			{
				// nếu trả lời đúng cả 5 câu
				if (subObj.getAnswerRightBuyType().intValue() == 5)
				{
					mMTType = MTType.AnswerFree_AllRight_Complete;
					PromotionMark = LocalConfig.AnswerPromotionFiveFree;
				}
				// nếu câu trả lời là đúng, nhưng có ít nhất 1 câu sai trong 5
				// câu
				else if (subObj.getAnswerStatusId().shortValue() == Play.Status.CorrectAnswer.GetValue().shortValue())
				{
					mMTType = MTType.AnswerFree_LastRight_Complete;
				}
				// Câu trả lời cuối là sai, và có ít nhất 1 câu sai trong 5 câu
				else
				{
					mMTType = MTType.AnswerFree_LastWrong_Complete;
				}
			}

		}
		// Nếu đang trả lời bộ 3 câu hỏi chưa MUA 2
		else if (subObj.getBuyType().shortValue() == Subscriber.BuyQuestionType.BuyOneQuestion.GetValue().shortValue())
		{
			// Nếu là câu trả lời cuối của bộ 3 câu hỏi
			if (subObj.getAnswerCount() == 8)
			{
				// trả lời đúng cả 3 câu
				if (subObj.getAnswerRightBuyType().intValue() == 3)
				{
					mMTType = MTType.AnswerOne_AllRight_Complete_NotBuyTwo;
					PromotionMark = LocalConfig.AnswerPromotionThree;
				}
				// Câu trả lời cuối là đúng, nhưng có ít nhất 1 câu trả lời sai
				else if (subObj.getAnswerStatusId().shortValue() == Play.Status.CorrectAnswer.GetValue().shortValue())
				{
					mMTType = MTType.AnswerOne_LastRight_Complete_NotBuyTwo;
				}
				// Câu trả lời cuối là sai, và có ít nhất 1 câu sai
				else
				{
					mMTType = MTType.AnswerOne_LastWrong_Complete_NotBuyTwo;
				}
			}
		}
		// Nếu đang trả lời bộ 3 câu hỏi đã MUA 2
		else if (subObj.getBuyType().shortValue() == Subscriber.BuyQuestionType.BuyTwoOneQuestion.GetValue()
				.shortValue())
		{
			// Nếu là câu trả lời cuối của bộ 3 câu hỏi
			if (subObj.getAnswerCount() == 15)
			{
				// trả lời đúng cả 3 câu
				if (subObj.getAnswerRightBuyType().intValue() == 3)
				{
					mMTType = MTType.AnswerOne_AllRight_Complete_BuyTwo;
					PromotionMark = LocalConfig.AnswerPromotionThree;
				}
				// Câu trả lời cuối là đúng, nhưng có ít nhất 1 câu trả lời sai
				else if (subObj.getAnswerStatusId().shortValue() == Play.Status.CorrectAnswer.GetValue().shortValue())
				{
					mMTType = MTType.AnswerOne_LastRight_Complete_BuyTwo;
				}
				// Câu trả lời cuối là sai, và có ít nhất 1 câu sai
				else
				{
					mMTType = MTType.AnswerOne_LastWrong_Complete_BuyTwo;
				}
			}
		}
		// Nếu đang trả lời bộ 7 câu hỏi chưa MUA 1
		else if (subObj.getBuyType().shortValue() == Subscriber.BuyQuestionType.BuyTwoQuestion.GetValue().shortValue())
		{
			if (subObj.getAnswerCount().intValue() == 12)
			{
				if (subObj.getAnswerRightBuyType().intValue() == 7)
				{
					mMTType = MTType.AnswerTwo_AllRight_Complete_NotBuyOne;
					PromotionMark = LocalConfig.AnswerPromotionSeven;
				}
				// Câu trả lời cuối là đúng, nhưng có ít nhất 1 câu trả lời sai
				else if (subObj.getAnswerStatusId().shortValue() == Play.Status.CorrectAnswer.GetValue().shortValue())
				{
					mMTType = MTType.AnswerTwo_LastRight_Complete_NotBuyOne;
				}
				// Câu trả lời cuối là sai, và có ít nhất 1 câu sai
				else
				{
					mMTType = MTType.AnswerTwo_LastWrong_Complete_NotBuyOne;
				}
			}
		}
		// Tra đang trả lời bộ 7 câu hỏi đã MUA 1
		else
		{
			if (subObj.getAnswerCount().intValue() == 15)
			{
				if (subObj.getAnswerRightBuyType().intValue() == 7)
				{
					mMTType = MTType.AnswerTwo_AllRight_Complete_BuyOne;
					PromotionMark = LocalConfig.AnswerPromotionSeven;
				}
				// Câu trả lời cuối là đúng, nhưng có ít nhất 1 câu trả lời sai
				else if (subObj.getAnswerStatusId().shortValue() == Play.Status.CorrectAnswer.GetValue().shortValue())
				{
					mMTType = MTType.AnswerTwo_LastRight_Complete_BuyOne;
				}
				// Câu trả lời cuối là sai, và có ít nhất 1 câu sai
				else
				{
					mMTType = MTType.AnswerTwo_LastWrong_Complete_BuyOne;
				}
			}
		}

		subObj.setPromotionMark((subObj.getPromotionMark() == null ? 0 : subObj.getPromotionMark()) + PromotionMark);

		if (subObj.getSendCount() < 15 && (mMTType == MTType.AnswerRight || mMTType == MTType.AnswerWrong))
		{
			questionObj = CurrentData.getNextQuestion(subObj);
			subObj.setLastQuestionId(questionObj.getQuestionId());
			subObj.setSendCount(subObj.getSendCount() == null ? 1 : subObj.getSendCount() + 1);
		}
	}
	/**
	 * Thêm vào log Mua dữ kiện và trả lời
	 * 
	 * @return
	 */
	private boolean Insert_Play()
	{
		try
		{
			Play playObj = new Play();
			PlayId mID = new PlayId();
			mID.setPhoneNumber(subObj.getId().getPhoneNumber());
			playObj.setId(mID);
			playObj.setPid(subObj.getId().getPid());

			playObj.setSessionId(questionObj == null ? null : questionObj.getSessionId());
			playObj.setQuestionId(questionObj == null ? null : questionObj.getQuestionId());
			playObj.setSendCount(subObj.getSendCount());
			playObj.setAnswerCount(subObj.getAnswerCount());
			playObj.setAnswerRight(subObj.getAnswerRight());
			playObj.setAnswerRightBuyType(subObj.getAnswerRightBuyType());

			if (CurrentData.checkSessionValid())
			{
				playObj.setPlayDate(CurrentData.getCurrentSession(false).getPlayDate());
			}

			playObj.setPlayTypeId(PlayType.Answer.GetValue());
			playObj.setReceiveDate(moQueueObj.getReceiveDate());
			playObj.setUserAnswer(UserAnswer);
			playObj.setStatusId(subObj.getAnswerStatusId());
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
	 * @throws Exception
	 */
	private boolean UpdateSubInfo() throws Exception
	{
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

	private String Get_UserAnswer() throws Exception
	{
		String MO = moQueueObj.getMo();
		MO = MyText.RemoveSpecialLetter(1, MO);

		return MO;
	}

	protected List<Mtqueue> getMessages(Moqueue moQueueObj, Keyword mKeyword) throws Exception
	{
		try
		{
			// Khoi tao
			Init(moQueueObj, mKeyword);

			// Lấy thông tin khách hàng đã đăng ký
			subObj = subObj.GetSub(PID, moQueueObj.getPhoneNumber());

			// Chưa đăng ký
			if (subObj == null)
			{
				mMTType = MTType.AnswerNotReg;
				return AddToList();
			}

			LogBeforeSub = MyLogger.GetLog("BEFORE_SUB:", subObj);
			// Phiên chơi chưa bắt đầu
			if (mCal_Current.before(mCal_Begin) || mCal_Current.after(mCal_End) || !CurrentData.checkSessionValid())
			{
				mMTType = MTType.AnswerExpire;
				return AddToList();
			}

			// Tình trạng không hợp lệ
			if (subObj.getStatusId().equals(Subscriber.Status.Pending.GetValue()))
			{
				mMTType = MTType.AnswerPending;
				return AddToList();
			}
			// Trả lời khi chưa mua hoặc đã trả lời hết 15 câu hỏi
			if ((subObj.getBuyType().shortValue() == Subscriber.BuyQuestionType.QuestionFree.GetValue().shortValue() && subObj
					.getAnswerCount().intValue() >= 5)
					|| (subObj.getBuyType().shortValue() == Subscriber.BuyQuestionType.BuyOneQuestion.GetValue()
							.shortValue() && subObj.getAnswerCount().intValue() >= 8)
					|| (subObj.getBuyType().shortValue() == Subscriber.BuyQuestionType.BuyTwoQuestion.GetValue()
							.shortValue() && subObj.getAnswerCount().intValue() >= 12)
					|| (subObj.getAnswerCount().intValue() >= 15))
			{
				mMTType = MTType.Invalid;
				return AddToList();
			}

			UserAnswer = Get_UserAnswer();

			// Create update sub va thay doi mMTType
			CreateUpdateSub();

			// Cập nhật thông tin vào DB
			if (UpdateSubInfo())
			{
				if (Insert_Play())
				{
					return AddToList();
				}
			}

			mMTType = MTType.AnswerFail;
			return AddToList();
		}
		catch (Exception ex)
		{
			mLog.log.error(MyLogger.GetLog(moQueueObj), ex);
			mMTType = MTType.AnswerFail;
			return AddToList();
		}
		finally
		{
			mLog.log.debug(MyLogger.GetLog(moQueueObj));
			mLog.log.debug(LogBeforeSub);
			mLog.log.debug(MyLogger.GetLog("AFTER_SUB:", subObj));
		}
	}
}