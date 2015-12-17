package db;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.HibernateException;

/**
 * Moqueue entity. @author MyEclipse Persistence Tools
 */

public class Moqueue extends DAOBase implements java.io.Serializable
{

	// Fields

	private Integer moid;
	private String phoneNumber;
	private String shortCode;
	private Short telcoId;
	private String keyword;
	private String mo;
	private Short channelId;
	private String requestId;
	private Timestamp moInsertDate;
	private Timestamp receiveDate;

	// Constructors

	/** default constructor */
	public Moqueue()
	{
	}

	/** full constructor */
	public Moqueue(String phoneNumber, String shortCode, Short telcoId, String keyword, String mo, Short channelId,
			String requestId, Timestamp moInsertDate, Timestamp receiveDate)
	{
		this.phoneNumber = phoneNumber;
		this.shortCode = shortCode;
		this.telcoId = telcoId;
		this.keyword = keyword;
		this.mo = mo;
		this.channelId = channelId;
		this.requestId = requestId;
		this.moInsertDate = moInsertDate;
		this.receiveDate = receiveDate;
	}

	// Property accessors

	public Integer getMoid()
	{
		return this.moid;
	}

	public void setMoid(Integer moid)
	{
		this.moid = moid;
	}

	public String getPhoneNumber()
	{
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getShortCode()
	{
		return this.shortCode;
	}

	public void setShortCode(String shortCode)
	{
		this.shortCode = shortCode;
	}

	public Short getTelcoId()
	{
		return this.telcoId;
	}

	public void setTelcoId(Short telcoId)
	{
		this.telcoId = telcoId;
	}

	public String getKeyword()
	{
		return this.keyword;
	}

	public void setKeyword(String keyword)
	{
		this.keyword = keyword;
	}

	public String getMo()
	{
		return this.mo;
	}

	public void setMo(String mo)
	{
		this.mo = mo;
	}

	public Short getChannelId()
	{
		return this.channelId;
	}

	public void setChannelId(Short channelId)
	{
		this.channelId = channelId;
	}

	public String getRequestId()
	{
		return this.requestId;
	}

	public void setRequestId(String requestId)
	{
		this.requestId = requestId;
	}

	public Timestamp getMoInsertDate()
	{
		return this.moInsertDate;
	}

	public void setMoInsertDate(Timestamp moInsertDate)
	{
		this.moInsertDate = moInsertDate;
	}

	public Timestamp getReceiveDate()
	{
		return this.receiveDate;
	}

	public void setReceiveDate(Timestamp receiveDate)
	{
		this.receiveDate = receiveDate;
	}

	/**
	 * Lấy MO theo từng thread
	 * @param threadNumber
	 * @param threadIndex
	 * @return
	 * @throws Exception 
	 * @throws  
	 */
	@SuppressWarnings("unchecked")
	public List<Moqueue> GetByThread(Integer threadNumber, Integer threadIndex) throws Exception
	{
		String Query = "FROM Moqueue WHERE (mod(moid," + threadNumber + ")=" + threadIndex + ")";
		return (List<Moqueue>) Get(Query);
	}
}