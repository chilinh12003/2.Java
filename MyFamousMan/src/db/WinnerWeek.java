package db;

import java.sql.Timestamp;
import java.util.Calendar;

import uti.*;

/**
 * WinnerWeek entity. @author MyEclipse Persistence Tools
 */

public class WinnerWeek extends DAOBase implements java.io.Serializable
{

	// Fields

	private Integer weekId;
	private String phoneNumber;
	private String address;
	private String winnerName;
	private String prize;
	private Integer weekOfYear;
	private Timestamp beginSession;
	private Timestamp endSession;
	private Integer weekMark;
	private Short isActive;

	// Constructors

	/** default constructor */
	public WinnerWeek()
	{
		
	}

	/**
	 * 
	 * @param subObj
	 * @param calMonday: ngày thứ 2 là ngày hiện tại
	 * @throws Exception 
	 */
	public WinnerWeek(Subscriber subObj, Calendar calMonday) throws Exception
	{
		this.phoneNumber = subObj.getId().getPhoneNumber();
		this.address = "";
		this.winnerName = "";
		this.prize = "";
		//Ngày hiện tại sẽ là ngày thứ 2
		Calendar calFriday = Calendar.getInstance();
		calFriday.setTime(calMonday.getTime());
		
		//Lùi lại 2 ngày để lấy ngày thứ 6
		calFriday.add(Calendar.DATE, -3);
		
		this.weekOfYear = calFriday.get(Calendar.WEEK_OF_YEAR);
		
		Calendar calMonday_LassWeek = MyDate.GetMonday(calFriday);
		Calendar calSunday_LassWeek = MyDate.GetSunday(calFriday);
		
		this.beginSession = MyDate.Date2Timestamp(calMonday_LassWeek);
		this.endSession = MyDate.Date2Timestamp(calSunday_LassWeek);
		this.weekMark = subObj.getWeekMark();
		this.isActive = (short)0;
	}
	/** full constructor */
	public WinnerWeek(String phoneNumber, String address, String winnerName, String prize, Integer weekOfYear,
			Timestamp beginSession, Timestamp endSession, Integer weekMark, Short isActive)
	{
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.winnerName = winnerName;
		this.prize = prize;
		this.weekOfYear = weekOfYear;
		this.beginSession = beginSession;
		this.endSession = endSession;
		this.weekMark = weekMark;
		this.isActive = isActive;
	}

	// Property accessors

	public Integer getWeekId()
	{
		return this.weekId;
	}

	public void setWeekId(Integer weekId)
	{
		this.weekId = weekId;
	}

	public String getPhoneNumber()
	{
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getAddress()
	{
		return this.address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getWinnerName()
	{
		return this.winnerName;
	}

	public void setWinnerName(String winnerName)
	{
		this.winnerName = winnerName;
	}

	public String getPrize()
	{
		return this.prize;
	}

	public void setPrize(String prize)
	{
		this.prize = prize;
	}

	public Integer getWeekOfYear()
	{
		return this.weekOfYear;
	}

	public void setWeekOfYear(Integer weekOfYear)
	{
		this.weekOfYear = weekOfYear;
	}

	public Timestamp getBeginSession()
	{
		return this.beginSession;
	}

	public void setBeginSession(Timestamp beginSession)
	{
		this.beginSession = beginSession;
	}

	public Timestamp getEndSession()
	{
		return this.endSession;
	}

	public void setEndSession(Timestamp endSession)
	{
		this.endSession = endSession;
	}

	public Integer getWeekMark()
	{
		return this.weekMark;
	}

	public void setWeekMark(Integer weekMark)
	{
		this.weekMark = weekMark;
	}

	public Short getIsActive()
	{
		return this.isActive;
	}

	public void setIsActive(Short isActive)
	{
		this.isActive = isActive;
	}

}