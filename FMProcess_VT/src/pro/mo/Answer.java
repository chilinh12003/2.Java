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
	Subscriber mSubObj = new Subscriber();

	Suggest mSuggestObj = new Suggest();

	Calendar mCal_Current = Calendar.getInstance();
	Calendar mCal_Begin = Calendar.getInstance();
	Calendar mCal_End = Calendar.getInstance();

	Short PID = 0;
	DefineMt.MTType mMTType = MTType.AnswerFail;

	String UserAnswer = "";

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

			String MTContent = Program.GetDefineMT_Message(mMTType);

			if (mMTType == MTType.AnswerSuccess)
			{
				MTContent = MTContent.replace("[Prize]", CurrentData.Get_Current_QuestionObj().getPrize());
				MTContent = MTContent.replace("[PlayDate]", CurrentData.Get_Current_QuestionObj().Get_PlayDate());
				MTContent = MTContent.replace("[NextDate]", CurrentData.Get_Current_QuestionObj().Get_NextDate());
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

		if (mSubObj.CheckLastAnswerDate(mCal_Current))
		{
			mSubObj.setAnswerByDay(mSubObj.getAnswerByDay() + 1);
		}
		else
		{
			mSubObj.setAnswerByDay(1);
		}

		if (UserAnswer.equalsIgnoreCase(CurrentData.Get_Current_QuestionObj().getRightAnswer()))
		{
			mSubObj.setAnswerStatusId(Play.Status.CorrectAnswer.GetValue());
			AnswerMark = LocalConfig.AnswerMark;
		}
		else
		{
			mSubObj.setAnswerStatusId(Play.Status.IncorrectAnswer.GetValue());
		}

		mSubObj.setLastAnswerDate(MyDate.Date2Timestamp(mCal_Current));
		mSubObj.setAnswerForSuggestId(mSubObj.getLastSuggestId());
		mSubObj.setLastAnswer(UserAnswer);

		mSubObj.setAnswerMark(mSubObj.getAnswerMark() + AnswerMark);

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
			mID.setPhoneNumber(mSubObj.getId().getPhoneNumber());
			playObj.setId(mID);
			playObj.setPid(mSubObj.getId().getPid());

			playObj.setPlayTypeId(PlayType.Answer.GetValue());
			playObj.setStatusId(mSubObj.getAnswerStatusId());
			playObj.setOrderNumber(mSuggestObj.getOrderNumber());
			playObj.setQuestionId(mSuggestObj.getQuestionId());
			playObj.setPlayDate(CurrentData.Get_Current_QuestionObj().getPlayDate());
			playObj.setReceiveDate(moQueueObj.getReceiveDate());
			playObj.setSuggestId(mSuggestObj.getSuggestId());
			playObj.setUserAnswer(UserAnswer);

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

	private String Get_UserAnswer() throws Exception
	{
		String MO = moQueueObj.getMo().substring(moQueueObj.getKeyword().length());
		MO = MyText.RemoveSpecialLetter(2, MO);

		return MO;
	}

	protected List<Mtqueue> getMessages(Moqueue moQueueObj, Keyword mKeyword) throws Exception
	{
		try
		{
			// Khoi tao
			Init(moQueueObj, mKeyword);

			// Lấy thông tin khách hàng đã đăng ký
			mSubObj = mSubObj.GetSub(PID, moQueueObj.getPhoneNumber());

			// Chưa đăng ký
			if (mSubObj == null)
			{
				mMTType = MTType.AnswerNotReg;
				return AddToList();
			}

			// Phiên chơi chưa bắt đầu
			if (mCal_Current.before(mCal_Begin) || mCal_Current.after(mCal_End)
					|| CurrentData.Get_Current_QuestionObj() == null)
			{
				mMTType = MTType.AnswerExpire;
				return AddToList();
			}

			// Kiểm tra trả lời vượt quá giới hạn
			if (mSubObj.CheckLastAnswerDate(mCal_Current))
			{
				if (mSubObj.getAnswerByDay().intValue() >= LocalConfig.MaxAnswerByDay.intValue())
				{
					mMTType = MTType.AnswerLimit;
					return AddToList();
				}
				if (mSubObj.getAnswerStatusId().shortValue() == Play.Status.CorrectAnswer.GetValue().shortValue())
				{
					mMTType = MTType.AnswerWhenAnswerRight;
					return AddToList();
				}
			}

			mSuggestObj = CurrentData.Get_SuggestObj_BuyID(mSubObj.getLastSuggestId());

			if (mSuggestObj == null)
			{
				mMTType = MTType.AnswerExpire;
				return AddToList();
			}

			UserAnswer = Get_UserAnswer();

			CreateUpdateSub();

			// Cập nhật thông tin vào DB
			UpdateSubInfo();

			Insert_Play();

			if (mSubObj.getAnswerStatusId().equals(Play.Status.CorrectAnswer.GetValue()))
			{
				mMTType = MTType.AnswerSuccess;
				return AddToList();
			}
			else
			{
				mMTType = MTType.AnswerWrong;
				return AddToList();
			}
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
		}
	}
}