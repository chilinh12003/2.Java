package MyGateway;

import db.connect.MyExecuteData;
import db.connect.MyGetData;
import db.define.DBConfig;
import db.define.MyTableModel;

public class SMSReceiveQueue
{
	public MyExecuteData mExec;
	public MyGetData mGet;

	public SMSReceiveQueue(DBConfig mConfigObj) throws Exception
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

	public MyTableModel SelectMO(int processnum, int processindex) throws Exception
	{

		String Query = "";
		try
		{
			Query = "Select  ID, SERVICE_ID,USER_ID, INFO, RECEIVE_DATE,MOBILE_OPERATOR,  " + "REQUEST_ID, DPORT from sms_receive_queue " + "where (mod(id,"
					+ processnum + ")=" + processindex + ")";

			return mGet.GetData_Query(Query);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public Boolean DeleteMO(String ListID) throws Exception
	{
		String Query = "DELETE FROM sms_receive_queue WHERE ID IN (" + ListID + ")";
		try
		{
			return mExec.Execute_Query(Query);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

}
