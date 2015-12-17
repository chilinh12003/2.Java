package dat.gateway;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import db.connect.MyExecuteData;
import db.connect.MyGetData;
import db.define.DBConfig;

public class sms_receive_queue_inv
{
	public MyExecuteData mExec;
	public MyGetData mGet;

	public sms_receive_queue_inv(DBConfig mDBConfig) throws Exception
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
	
	public Boolean Insert(String REQUEST_ID, String USER_ID, String SERVICE_ID, String MOBILE_OPERATOR, String COMMAND_CODE, String INFO, Timestamp RECEIVE_DATE)
			throws Exception, SQLException
	{
		try
		{
			String Format_Query = "	INSERT INTO sms_receive_queue_inv(REQUEST_ID,USER_ID,SERVICE_ID,MOBILE_OPERATOR,COMMAND_CODE,INFO,RECEIVE_DATE) "
					+ " 	VALUES(?,?,?,?,?,?,?)";

			String RECEIVE_DATE_String = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance());
			if (RECEIVE_DATE != null)
			{
				RECEIVE_DATE_String = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(RECEIVE_DATE);
			}
			Object[] arr_value = { REQUEST_ID, USER_ID, SERVICE_ID, MOBILE_OPERATOR, COMMAND_CODE, INFO, RECEIVE_DATE_String };

			

			return mExec.Execute_Query(Format_Query,arr_value);

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
