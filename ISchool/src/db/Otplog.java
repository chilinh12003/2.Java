package db;

import java.sql.Timestamp;
import java.util.List;


/**
 * Otplog entity. @author MyEclipse Persistence Tools
 */

public class Otplog extends DAOBase implements java.io.Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum Status
	{
		NoThing(0),
		/**
		 * Kích hoạt
		 */

		Used(1), NotUse(2);

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
			return NoThing;
		}
	}
	
	// Fields

	private String otpcode;
	private String phoneNumber;
	private Short pid;
	private Timestamp createDate;
	private Timestamp loginDate;
	private Short statusId;

	// Constructors

	/** default constructor */
	public Otplog()
	{
	}

	/** minimal constructor */
	public Otplog(String otpcode)
	{
		this.otpcode = otpcode;
	}

	/** full constructor */
	public Otplog(String otpcode, String phoneNumber, Short pid, Timestamp createDate, Timestamp loginDate,
			Short statusId)
	{
		this.otpcode = otpcode;
		this.phoneNumber = phoneNumber;
		this.pid = pid;
		this.createDate = createDate;
		this.loginDate = loginDate;
		this.statusId = statusId;
	}

	// Property accessors

	public String getOtpcode()
	{
		return this.otpcode;
	}

	public void setOtpcode(String otpcode)
	{
		this.otpcode = otpcode;
	}

	public String getPhoneNumber()
	{
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public Short getPid()
	{
		return this.pid;
	}

	public void setPid(Short pid)
	{
		this.pid = pid;
	}

	public Timestamp getCreateDate()
	{
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate)
	{
		this.createDate = createDate;
	}

	public Timestamp getLoginDate()
	{
		return this.loginDate;
	}

	public void setLoginDate(Timestamp loginDate)
	{
		this.loginDate = loginDate;
	}

	public Short getStatusId()
	{
		return this.statusId;
	}

	public void setStatusId(Short statusId)
	{
		this.statusId = statusId;
	}

	public Otplog getOTPLog(Short PID, String PhoneNumber, Status mSatus) throws Exception
	{

		List<?> mList = null;
		try
		{
			String strSQL = "FROM " + this.getClass().getName() + " WHERE pid = " + PID.toString()
					+ " AND phoneNumber ='" + PhoneNumber + "' AND StatusID = " +mSatus.GetValue().toString();

			mList = (List<?>) Get(strSQL);
			if (mList.size() > 0)
			{
				return (Otplog) mList.get(0);
			}

			return null;

		}
		catch (Exception ex)
		{
			throw ex;
		}

	}
}