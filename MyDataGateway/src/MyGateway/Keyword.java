package MyGateway;

import db.connect.MyExecuteData;
import db.connect.MyGetData;
import db.define.DBConfig;
import db.define.MyTableModel;

public class Keyword
{
	public MyExecuteData mExec;
	public MyGetData mGet;

	public Keyword(DBConfig mConfigObj) throws Exception
	{
		try
		{
			mExec = new MyExecuteData(mConfigObj);
			mGet = new MyGetData(mConfigObj);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}
	
	
	public MyTableModel Select(int Type) throws Exception
	{
		String Query = "";
		try
		{
			if(Type == 0)//Lấy tất cả dữ liệu
			{
				Query = " SELECT * FROM keyword_config";
			}
			return mGet.GetData_Query(Query);
		}
		catch(Exception ex)
		{
			throw ex;
		}
	}
	
	/**
	 * Lấy các keyword đang active
	 * @return
	 * @throws Exception
	 */
	public MyTableModel SelectActive() throws Exception
	{
		String Query = " SELECT service_id,keyword,class_name,options, cpid " +
				" FROM  keyword_config  " +
				" WHERE status = 1 and current_timestamp >= activedate and ((current_timestamp < inactivedate ) or " +
				" inactivedate is null  or inactivedate='0000-00-00 00:00:00' ) " +
				" order by length(keyword) desc, keyword asc ";
		try
		{			
			return mGet.GetData_Query(Query);
		}
		catch(Exception ex)
		{
			throw ex;
		}
	}
}
