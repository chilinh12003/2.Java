package pro.server;

import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import uti.MyLogger;
import db.Question;
import db.Suggest;

/**
 * Chứa các đối tượng đã table mẫu ở Database Các đối tượng này sẽ được load khi
 * chương trình bắt đầu chạy
 * 
 * @author Administrator
 * 
 */
public class CurrentData
{

	static MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath, Program.class.toString());
	private static Question Current_QuestionObj = new Question();

	/**
	 * Lấy câu hỏi theo ngày
	 * 
	 * @param mCal_Current
	 * @return
	 * @throws Exception
	 */
	public static synchronized Question Get_Current_QuestionObj() throws Exception
	{
		try
		{
			Calendar mCal_Current = Calendar.getInstance();
			if (Current_QuestionObj != null && Current_QuestionObj.CheckPlayDate(mCal_Current))
				return Current_QuestionObj;

			Calendar mCal_PlayDate = Calendar.getInstance();

			mCal_PlayDate.set(Calendar.MILLISECOND, 0);
			mCal_PlayDate.set(mCal_Current.get(Calendar.YEAR), mCal_Current.get(Calendar.MONTH),
					mCal_Current.get(Calendar.DATE), 0, 0, 0);

			Question mQuestion = new Question();

			Current_QuestionObj = mQuestion.getCurrentQuestion(mCal_PlayDate);

			return Current_QuestionObj;
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
		return null;
	}

	/**
	 * Lấy câu hỏi của ngày hôm qua
	 * 
	 * @return
	 * @throws Exception
	 */
	public static synchronized Question Get_Yesterday_QuestionObj() throws Exception
	{
		try
		{
			Calendar mCal_Yesterday = Calendar.getInstance();
			mCal_Yesterday.add(Calendar.DATE, -1);
			Calendar mCal_PlayDate = Calendar.getInstance();

			mCal_PlayDate.set(Calendar.MILLISECOND, 0);
			mCal_PlayDate.set(mCal_Yesterday.get(Calendar.YEAR), mCal_Yesterday.get(Calendar.MONTH),
					mCal_Yesterday.get(Calendar.DATE), 0, 0, 0);

			Question mQuestion = new Question();

			return mQuestion.getCurrentQuestion(mCal_PlayDate);
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
		return null;
	}

	private static Vector<Suggest> Current_SuggestObj = new Vector<Suggest>();

	/**
	 * Lấy danh sách các dữ kiện cho câu hỏi hiện tại
	 * 
	 * @return
	 * @throws Exception
	 */
	public static synchronized Vector<Suggest> Get_Current_SuggestObj() throws Exception
	{
		try
		{
			if (Current_SuggestObj != null && Current_SuggestObj.size() == 10
					&& Current_SuggestObj.get(0).getQuestionId().equals(Get_Current_QuestionObj().getQuestionId()))
				return Current_SuggestObj;

			if (Current_SuggestObj != null)
				Current_SuggestObj.clear();

			Suggest mSuggest = new Suggest();
			List<Suggest> mList = mSuggest.getByQuestion(Get_Current_QuestionObj().getQuestionId());

			for (Suggest item : mList)
				Current_SuggestObj.add(item);
			return Current_SuggestObj;
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
		return null;
	}

	public static synchronized Suggest Get_SuggestObj(Integer OrderNuber) throws Exception
	{
		try
		{
			if (Get_Current_SuggestObj().size() < 1)
				return null;

			for (Suggest mObject : Get_Current_SuggestObj())
			{
				if (mObject.getOrderNumber().equals(OrderNuber))
					return mObject;
			}

		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
		return null;
	}

	public static synchronized Suggest Get_SuggestObj_BuyID(Integer SuggestID) throws Exception
	{
		try
		{
			Suggest mSuggestObj = null;

			for (Suggest mObject : Get_Current_SuggestObj())
			{
				if (mObject.getSuggestId().equals(SuggestID))
					mSuggestObj = mObject;
			}

			if (mSuggestObj == null)
			{
				Suggest mSuggest = new Suggest();

				mSuggestObj = (Suggest) mSuggest.Get(SuggestID);

				if (mSuggestObj != null)
				{
					Get_Current_SuggestObj().add(mSuggestObj);
				}
			}
			return mSuggestObj;
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
		return null;
	}

}
