package dat.gateway;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import db.connect.MyExecuteData;
import db.connect.MyGetData;
import db.define.DBConfig;
import db.define.MyTableModel;

public class sms_receive_log
{
	public MyExecuteData mExec;
	public MyGetData mGet;

	public sms_receive_log(DBConfig mDBConfig) throws Exception
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
	
	
	public boolean Insert(String REQUEST_ID, String USER_ID, String SERVICE_ID, String MOBILE_OPERATOR, String COMMAND_CODE, String INFO,
			Timestamp RECEIVE_DATE, String RESPONDED, int CPID, int ChannelType) throws Exception
	{
		try
		{
			String ReceiveDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(RECEIVE_DATE);

			String TableName = "sms_receive_log" + new SimpleDateFormat("yyyyMM").format(new Date());

			String Format_Query = "INSERT INTO " + TableName + "(REQUEST_ID,USER_ID,SERVICE_ID,MOBILE_OPERATOR,COMMAND_CODE,INFO,RECEIVE_DATE,RESPONDED,CPID,CHANNEL_TYPE) "
					+ "				VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
			
			Object[] arr_value = { REQUEST_ID, USER_ID, SERVICE_ID, MOBILE_OPERATOR, COMMAND_CODE, INFO, ReceiveDate, RESPONDED, Integer.toString(CPID), Integer.toString(ChannelType) };
			return mExec.Execute_Query(Format_Query,arr_value);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}
	public boolean Insert_VMS(String REQUEST_ID, String USER_ID, String SERVICE_ID, String MOBILE_OPERATOR, String COMMAND_CODE, String INFO,
			Timestamp RECEIVE_DATE, String RESPONDED, int CPID, int ChannelType, String VMS_SVID) throws Exception
	{
		try
		{
			String ReceiveDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(RECEIVE_DATE);

			String TableName = "sms_receive_log" + new SimpleDateFormat("yyyyMM").format(new Date());

			String Format_Query = "INSERT INTO " + TableName + "(REQUEST_ID,USER_ID,SERVICE_ID,MOBILE_OPERATOR,COMMAND_CODE,INFO,RECEIVE_DATE,RESPONDED,CPID,CHANNEL_TYPE,VMS_SVID) "
					+ "				VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
			
			Object[] arr_value = { REQUEST_ID, USER_ID, SERVICE_ID, MOBILE_OPERATOR, COMMAND_CODE, INFO, ReceiveDate, RESPONDED, Integer.toString(CPID), Integer.toString(ChannelType),VMS_SVID };
			return mExec.Execute_Query(Format_Query,arr_value);
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
