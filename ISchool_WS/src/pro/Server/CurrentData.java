package pro.Server;

import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import uti.MyDate;
import uti.MyLogger;
import db.News;
import db.PlaySession;
import db.Question;
import db.Subscriber;

/**
 * Chứa các đối tượng đã table mẫu ở Database Các đối tượng này sẽ được load khi
 * chương trình bắt đầu chạy
 * 
 * @author Administrator
 * 
 */
public class CurrentData
{

	static MyLogger mLog = new MyLogger(LocalConfig.LogConfigPath(), CurrentData.class.toString());
	private static PlaySession currentSession = new PlaySession();

	/**
	 * Lấy phiên chơi hiện tại
	 * 
	 * @param mCal_Current
	 * @return
	 * @throws Exception
	 */
	public static synchronized PlaySession getCurrentSession(boolean isReget) throws Exception
	{
		try
		{
			Calendar mCal_Current = Calendar.getInstance();

			// isReget == false: nếu không cần lấy lại thì lấy phiên đã lấy
			// trước đó
			if (isReget == false && currentSession != null && currentSession.checkPlayDate(mCal_Current))
				return currentSession;
			
			Calendar calPlayDate = Calendar.getInstance();

			calPlayDate.set(Calendar.MILLISECOND, 0);
			calPlayDate.set(mCal_Current.get(Calendar.YEAR), mCal_Current.get(Calendar.MONTH),
					mCal_Current.get(Calendar.DATE), 0, 0, 0);

			PlaySession playSessionDB = new PlaySession();

			currentSession = playSessionDB.getCurrentSession(calPlayDate);

			return currentSession;
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
		return null;
	}

	/**
	 * Lấy phiên chơi ngày hôm qua
	 * 
	 * @return
	 * @throws Exception
	 */
	public static synchronized PlaySession getYesterdaySession() throws Exception
	{
		try
		{
			Calendar calYesterday = Calendar.getInstance();
			calYesterday.add(Calendar.DATE, -1);
			Calendar calPlayDate = Calendar.getInstance();

			calPlayDate.set(Calendar.MILLISECOND, 0);
			calPlayDate.set(calYesterday.get(Calendar.YEAR), calYesterday.get(Calendar.MONTH),
					calYesterday.get(Calendar.DATE), 0, 0, 0);

			PlaySession playSessionDB = new PlaySession();

			return playSessionDB.getCurrentSession(calPlayDate);
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
		return null;
	}

	private static Vector<Question> listCurrentQuestion = new Vector<Question>();

	/**
	 * Lấy danh sách các câu hỏi hiện tại
	 * 
	 * @return
	 * @throws Exception
	 */
	public static synchronized Vector<Question> getListCurrentQuestion() throws Exception
	{
		try
		{
			if (listCurrentQuestion != null && listCurrentQuestion.size() >= 15
					&& listCurrentQuestion.get(0).getQuestionId().equals(getCurrentSession(false).getSessionId()))
				return listCurrentQuestion;

			if (listCurrentQuestion != null)
				listCurrentQuestion.clear();

			Question questionDB = new Question();
			List<Question> listQuestion = questionDB.getBySession(getCurrentSession(true).getSessionId());

			for (Question item : listQuestion)
				listCurrentQuestion.add(item);
			return listCurrentQuestion;
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
		return null;
	}

	public static synchronized Question getQuestionByOrder(Integer OrderNuber) throws Exception
	{
		try
		{
			if (getListCurrentQuestion().size() < 15)
				return null;

			for (Question item : getListCurrentQuestion())
			{
				if (item.getOrderNumber().equals(OrderNuber))
					return item;
			}

			return null;
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}
		return null;

	}

	public static synchronized Question getQuestionById(Integer QuestionID) throws Exception
	{
		Question questionObj = null;

		try
		{
			for (Question item : getListCurrentQuestion())
			{
				if (item.getQuestionId().equals(QuestionID))
					questionObj = item;
			}

			if (questionObj == null)
			{
				Question questionDB = new Question();

				questionObj = (Question) questionDB.Get(QuestionID);

				if (questionObj != null)
				{
					getListCurrentQuestion().add(questionObj);
				}
			}
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}

		return questionObj;
	}

	/**
	 * Lấy câu hỏi tiếp theo
	 * 
	 * @return
	 */
	public static Question getNextQuestion(Subscriber subObj)
	{
		try
		{
			return getQuestionByOrder(subObj.getSendCount() + 1);
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
		}

		return null;
	}

	/**
	 * Kiểm tra sự tồn tại của session và Question.
	 * 
	 * @return
	 */
	public static boolean checkSessionValid()
	{
		try
		{
			if (getCurrentSession(false) == null)
				return false;

			if (getListCurrentQuestion() == null || getListCurrentQuestion().size() < 15)
				return false;
			return true;
		}
		catch (Exception ex)
		{
			mLog.log.error(ex);
			return false;
		}
	}

	/**
	 * Lấy 1 tin tức trong ngày
	 * @return
	 */
	public static News getNewsToday()
	{
		try
		{
			Calendar calBeginDate = Calendar.getInstance();
			Calendar calEndDate = Calendar.getInstance();

			calBeginDate.set(Calendar.HOUR, 0);
			calBeginDate.set(Calendar.SECOND, 0);
			calBeginDate.set(Calendar.MILLISECOND, 0);
			calBeginDate.set(Calendar.MINUTE, 0);

			calEndDate.add(Calendar.DATE, 1);
			calEndDate.set(Calendar.HOUR, 0);
			calEndDate.set(Calendar.MINUTE, 0);
			calEndDate.set(Calendar.SECOND, 0);
			calEndDate.set(Calendar.MILLISECOND, 0);
			

			News newsDB = new News();
			List<News> mList = newsDB.GetNews(News.NewsType.Push, calBeginDate, calEndDate);
			if (mList != null && mList.size() > 0)
			{
				return mList.get(0);
			}
			
		}
		catch(Exception ex)
		{
			mLog.log.error(ex);
		}
		return null;
	}
}
