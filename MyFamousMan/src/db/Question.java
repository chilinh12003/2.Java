package db;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import uti.MyConfig;

/**
 * Question entity. @author MyEclipse Persistence Tools
 */

public class Question extends DAOBase implements java.io.Serializable
{

	public enum Status
	{
		Nothing(0),
		/**
		 * Câu hỏi mới tạo
		 */
		New(100),
		/**
		 * câu hỏi đang trong phiên đang diễn ra
		 */
		Playing(101),
		/**
		 * Câu hỏi đã chơi xong và kết thúc phiên
		 */
		Finish(102), ;

		private int value;

		private Status(int value)
		{
			this.value = value;
		}

		public Short GetValue()
		{
			return (Short) ((Integer) this.value).shortValue();
		}

		public static Status FromValue(Short iValue)
		{
			for (Status type : Status.values())
			{
				if (type.GetValue().equals(iValue))
					return type;
			}
			return Nothing;
		}

	}

	// Fields

	private Integer questionId =0;
	private String questionName;
	private Short statusId;
	private Timestamp createDate;
	private String rightAnswer;
	private Timestamp playDate;
	private String prize;
	private String price;

	// Constructors

	/** default constructor */
	public Question()
	{
	}

	/** full constructor */
	public Question(String questionName, Short statusId, Timestamp createDate, String rightAnswer, Timestamp playDate,
			String prize, String price)
	{
		this.questionName = questionName;
		this.statusId = statusId;
		this.createDate = createDate;
		this.rightAnswer = rightAnswer;
		this.playDate = playDate;
		this.prize = prize;
		this.price = price;
	}

	// Property accessors

	public Integer getQuestionId()
	{
		return this.questionId;
	}

	public void setQuestionId(Integer questionId)
	{
		this.questionId = questionId;
	}

	public String getQuestionName()
	{
		return this.questionName;
	}

	public void setQuestionName(String questionName)
	{
		this.questionName = questionName;
	}

	public Short getStatusId()
	{
		return this.statusId;
	}

	public void setStatusId(Short statusId)
	{
		this.statusId = statusId;
	}

	public Timestamp getCreateDate()
	{
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate)
	{
		this.createDate = createDate;
	}

	public String getRightAnswer()
	{
		return this.rightAnswer;
	}

	public void setRightAnswer(String rightAnswer)
	{
		this.rightAnswer = rightAnswer;
	}

	public Timestamp getPlayDate()
	{
		return this.playDate;
	}

	public void setPlayDate(Timestamp playDate)
	{
		this.playDate = playDate;
	}

	public String getPrize()
	{
		return this.prize;
	}

	public void setPrize(String prize)
	{
		this.prize = prize;
	}

	public String getPrice()
	{
		return this.price;
	}

	public void setPrice(String price)
	{
		this.price = price;
	}

	public String Get_PlayDate()
	{
		String strDate = "";
		try
		{
			strDate = MyConfig.Get_DateFormat_VNShortSlash().format(playDate);
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		return strDate;
	}

	public String Get_NextDate()
	{
		Calendar mCal_NextDate = Calendar.getInstance();
		mCal_NextDate.add(Calendar.DATE, 1);

		String strDate = "";
		try
		{
			strDate = uti.MyConfig.Get_DateFormat_VNShortSlash().format(mCal_NextDate.getTime());
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		return strDate;
	}

	public boolean CheckPlayDate(Calendar mCal_Current) throws Exception
	{
		if (playDate == null)
			return false;
		Calendar mCal_PlayDate = Calendar.getInstance();

		mCal_PlayDate.setTime(new Date( playDate.getTime()));
		if (mCal_Current.get(Calendar.YEAR) == mCal_PlayDate.get(Calendar.YEAR)
				&& mCal_Current.get(Calendar.MONTH) == mCal_PlayDate.get(Calendar.MONTH)
				&& mCal_Current.get(Calendar.DATE) == mCal_PlayDate.get(Calendar.DATE))
			return true;
		else return false;
	}

	public Question getCurrentQuestion(Calendar calPlayDate) throws Exception
	{

		List<?> mList = null;
		try
		{
			String strSQL = "FROM Question WHERE playDate = '"
					+ MyConfig.Get_DateFormat_InsertDB().format(calPlayDate.getTime()) + "' "
					+ " ORDER BY playDate DESC ";

			mList = (List<?>) Get(strSQL, 1);
			if (mList.size() > 0)
			{
				return (Question) mList.get(0);
			}
			return null;
		}
		catch (Exception ex)
		{
			throw ex;
		}
		
	}

}