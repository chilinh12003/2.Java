package dat.gateway;

import java.sql.SQLException;

import db.connect.MyExecuteData;
import db.connect.MyGetData;
import db.define.DBConfig;

public class cdr_queue
{

	public MyExecuteData mExec;
	public MyGetData mGet;

	public cdr_queue(DBConfig mDBConfig) throws Exception
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

	public Boolean Insert(String USER_ID, String SERVICE_ID,
			String MOBILE_OPERATOR, String COMMAND_CODE, String INFO,
			String SUBMIT_DATE, String DONE_DATE, String TOTAL_SEGMENTS,
			String Message_Type, String process_result, String request_id)
			throws Exception, SQLException
	{
		try
		{
			if (INFO == null)
			{
				INFO = COMMAND_CODE;
			}
			else if (INFO.length() > 20)
			{
				INFO = INFO.substring(0, 20);
				INFO = INFO + "...";
			}
			
			String Format_Query = "	INSERT INTO cdr_queue(USER_ID, SERVICE_ID,MOBILE_OPERATOR, COMMAND_CODE, INFO, "
					+ "SUBMIT_DATE, DONE_DATE, TOTAL_SEGMENTS,Message_Type,process_result,request_id,cpid) "
					+ " 	VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";

			String cpid = "0";

			Object[] arr_value =
			{USER_ID, SERVICE_ID, MOBILE_OPERATOR, COMMAND_CODE, INFO,
					SUBMIT_DATE, DONE_DATE, TOTAL_SEGMENTS, Message_Type,
					process_result, request_id, cpid};

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
