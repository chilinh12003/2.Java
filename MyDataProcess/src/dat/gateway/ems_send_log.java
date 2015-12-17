package dat.gateway;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import db.connect.MyExecuteData;
import db.connect.MyGetData;
import db.define.DBConfig;

public class ems_send_log
{
	public MyExecuteData mExec;
	public MyGetData mGet;

	public ems_send_log(DBConfig mDBConfig) throws Exception
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

	public boolean Insert(String USER_ID, String SERVICE_ID, String MOBILE_OPERATOR, String COMMAND_CODE,
			String CONTENT_TYPE, String INFO, Timestamp SUBMIT_DATE, Timestamp DONE_DATE, String PROCESS_RESULT,
			String MESSAGE_TYPE, String REQUEST_ID, String MESSAGE_ID, String TOTAL_SEGMENTS, String RETRIES_NUM,
			String NOTES, String CPId, String VMS_MT_ID, String SEND_RESULT, String VMS_SVID) throws Exception
	{
		try
		{

			String TableName = "ems_send_log" + new SimpleDateFormat("yyyyMM").format(new Date(DONE_DATE.getTime()));

			String Format_Query = "INSERT INTO "
					+ TableName
					+ "(USER_ID, SERVICE_ID, MOBILE_OPERATOR, COMMAND_CODE, CONTENT_TYPE, INFO, SUBMIT_DATE, DONE_DATE, PROCESS_RESULT, MESSAGE_TYPE, REQUEST_ID, MESSAGE_ID, TOTAL_SEGMENTS, RETRIES_NUM, NOTES, CPId, VMS_MT_ID, SEND_RESULT,VMS_SVID) "
					+ "				VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			Object[] arr_value =
			{USER_ID, SERVICE_ID, MOBILE_OPERATOR, COMMAND_CODE, CONTENT_TYPE, INFO, SUBMIT_DATE, DONE_DATE,
					PROCESS_RESULT, MESSAGE_TYPE, REQUEST_ID, MESSAGE_ID, TOTAL_SEGMENTS, RETRIES_NUM, NOTES, CPId,
					VMS_MT_ID, SEND_RESULT,VMS_SVID};
			return mExec.Execute_Query(Format_Query, arr_value);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

}
