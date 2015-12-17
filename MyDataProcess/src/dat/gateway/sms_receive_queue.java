package dat.gateway;

import java.sql.SQLException;

import db.connect.MyExecuteData;
import db.connect.MyGetData;
import db.define.DBConfig;
import db.define.MyTableModel;

public class sms_receive_queue
{
	public MyExecuteData mExec;
	public MyGetData mGet;

	public sms_receive_queue(DBConfig mDBConfig) throws Exception
	{
		try
		{
			mExec = new MyExecuteData(mDBConfig);
			mGet = new MyGetData(mDBConfig);
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
			Query = "Select  * from sms_receive_queue " + "where (mod(id," + processnum + ")=" + processindex + ")";

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

	public Boolean Insert(String USER_ID, String SERVICE_ID, String MOBILE_OPERATOR, String COMMAND_CODE, String INFO, String REQUEST_ID, String receive_date)
			throws Exception, SQLException
	{
		try
		{
			String Format_Query = "	INSERT INTO sms_receive_queue(USER_ID,SERVICE_ID,MOBILE_OPERATOR,COMMAND_CODE,INFO,receive_date,RESPONDED,REQUEST_ID,DPORT,CHANNEL_TYPE) "
					+ " 	VALUES(?,?,?,?,?,?,?,?,?,?)";

			String RESPONDED = "0";
			String CHANNEL_TYPE = "1";
			String DPORT = "0";

			Object[] arr_value = { USER_ID, SERVICE_ID, MOBILE_OPERATOR, COMMAND_CODE, INFO, receive_date, RESPONDED, REQUEST_ID, DPORT, CHANNEL_TYPE };

			return mExec.Execute_Query(Format_Query, arr_value);
		}
		catch (SQLException ex)
		{
			throw ex;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}
	
	public Boolean Insert_VMS(String USER_ID, String SERVICE_ID, String MOBILE_OPERATOR, String COMMAND_CODE, String INFO, String REQUEST_ID, String receive_date, String VMS_SVID)
			throws Exception, SQLException
	{
		try
		{
			String Format_Query = "	INSERT INTO sms_receive_queue(USER_ID,SERVICE_ID,MOBILE_OPERATOR,COMMAND_CODE,INFO,receive_date,RESPONDED,REQUEST_ID,DPORT,CHANNEL_TYPE,VMS_SVID) "
					+ " 	VALUES(?,?,?,?,?,?,?,?,?,?,?)";

			String RESPONDED = "0";
			String CHANNEL_TYPE = "1";
			String DPORT = "0";

			Object[] arr_value = { USER_ID, SERVICE_ID, MOBILE_OPERATOR, COMMAND_CODE, INFO, receive_date, RESPONDED, REQUEST_ID, DPORT, CHANNEL_TYPE,VMS_SVID };

			return mExec.Execute_Query(Format_Query, arr_value);
		}
		catch (SQLException ex)
		{
			throw ex;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}
}
