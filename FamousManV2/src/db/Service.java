package db;

import java.util.Vector;

/**
 * Service entity. @author MyEclipse Persistence Tools
 */

public class Service extends DAOBase implements java.io.Serializable {

	// Fields

	private Integer serviceId =0;
	private String serviceName;
	private String regKeyword;
	private String deregKeyword;
	private String viettelServiceId;

	// Constructors

	public String getViettelServiceId() {
		return viettelServiceId;
	}

	public void setViettelServiceId(String viettelServiceId) {
		this.viettelServiceId = viettelServiceId;
	}

	/** default constructor */
	public Service() {
	}

	/** full constructor */
	public Service(String serviceName, String regKeyword, String deregKeyword, String viettelServiceId) {
		this.serviceName = serviceName;
		this.regKeyword = regKeyword;
		this.deregKeyword = deregKeyword;
		this.viettelServiceId = viettelServiceId;
	}

	// Property accessors

	public Integer getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return this.serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getRegKeyword() {
		return this.regKeyword;
	}

	public void setRegKeyword(String regKeyword) {
		this.regKeyword = regKeyword;
	}

	public String getDeregKeyword() {
		return this.deregKeyword;
	}

	public void setDeregKeyword(String deregKeyword) {
		this.deregKeyword = deregKeyword;
	}

	public Service clone()
	{
		Service temp = new Service();
		temp.setServiceId( this.getServiceId());
		temp.setServiceName(this.getServiceName());
		temp.setRegKeyword(this.getRegKeyword());
		temp.setDeregKeyword(this.getDeregKeyword());
		temp.setViettelServiceId(this.getViettelServiceId());
		return temp;
	}
	
	public static Service getService(Vector<Service> mList, String Keyword, Boolean IsReg) throws Exception
	{
		try
		{
			if (mList.size() < 1)
				return null;

			Service mService = null;
			

			for (Service item : mList)
			{
				if(IsReg)
				{
				if (	item.getRegKeyword().equalsIgnoreCase(Keyword) ||
						item.getRegKeyword().replace(" ", "").equalsIgnoreCase(Keyword.replace(" ", "")))
					mService = item;
				}
				else
				{
					if (	item.getDeregKeyword().equalsIgnoreCase(Keyword) ||
							item.getDeregKeyword().replace(" ", "").equalsIgnoreCase(Keyword.replace(" ", "")))
						mService = item;
				}
			}
			
			return (Service) mService.clone();
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}
	
	public static Service getServiceByViettelID(Vector<Service> mList, String ViettelServiceID) throws Exception
	{
		try
		{
			if (mList.size() < 1)
				return null;

			Service mService = null;
			

			for (Service item : mList)
			{
				if (	item.getViettelServiceId().equalsIgnoreCase(ViettelServiceID) ||
						item.getViettelServiceId().replace(" ", "").equalsIgnoreCase(ViettelServiceID.replace(" ", "")))
				{
					mService = item;
					break;
				}
			}
			
			return (Service) mService.clone();
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}
}