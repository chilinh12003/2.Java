package db;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import uti.MyConfig;

/**
 * Session entity. @author MyEclipse Persistence Tools
 */

public class PlaySession extends DAOBase implements java.io.Serializable
{

	// Fields

	private Integer sessionId;
	private String sessionName;
	private Short statusId;
	private Timestamp playDate;
	private Timestamp beginDate;
	private Timestamp endDate;
	private Timestamp createDate;

	// Constructors

	/** default constructor */
	public PlaySession()
	{
	}

	/** full constructor */
	public PlaySession(String sessionName, Short statusId, Timestamp playDate, Timestamp beginDate, Timestamp endDate,
			Timestamp createDate)
	{
		this.sessionName = sessionName;
		this.statusId = statusId;
		this.playDate = playDate;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.createDate = createDate;
	}

	// Property accessors

	public Integer getSessionId()
	{
		return this.sessionId;
	}

	public void setSessionId(Integer sessionId)
	{
		this.sessionId = sessionId;
	}

	public String getSessionName()
	{
		return this.sessionName;
	}

	public void setSessionName(String sessionName)
	{
		this.sessionName = sessionName;
	}

	public Short getStatusId()
	{
		return this.statusId;
	}

	public void setStatusId(Short statusId)
	{
		this.statusId = statusId;
	}

	public Timestamp getPlayDate()
	{
		return this.playDate;
	}

	public void setPlayDate(Timestamp playDate)
	{
		this.playDate = playDate;
	}

	public Timestamp getBeginDate()
	{
		return this.beginDate;
	}

	public void setBeginDate(Timestamp beginDate)
	{
		this.beginDate = beginDate;
	}

	public Timestamp getEndDate()
	{
		return this.endDate;
	}

	public void setEndDate(Timestamp endDate)
	{
		this.endDate = endDate;
	}

	public Timestamp getCreateDate()
	{
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate)
	{
		this.createDate = createDate;
	}

	

	public String getNextDate()
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

	public boolean checkPlayDate(Calendar mCal_Current) throws Exception
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

	public PlaySession getCurrentSession(Calendar calPlayDate) throws Exception
	{

		List<?> mList = null;
		try
		{
			String strSQL = "FROM PlaySession WHERE PlayDate = '"
					+ MyConfig.Get_DateFormat_InsertDB().format(calPlayDate.getTime()) + "' "
					+ " ORDER BY PlayDate DESC ";

			mList = (List<?>) Get(strSQL, 1);
			if (mList.size() > 0)
			{
				return (PlaySession) mList.get(0);
			}
			return null;
		}
		catch (Exception ex)
		{
			throw ex;
		}
		
	}
}