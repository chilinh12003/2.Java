package dat.gateway;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import uti.utility.MyConfig;
import db.connect.MyExecuteData;
import db.connect.MyGetData;
import db.define.DBConfig;
import db.define.MyTableModel;

public class ems_send_queue
{
	public MyExecuteData mExec;
	public MyGetData mGet;

	public ems_send_queue(DBConfig mDBConfig) throws Exception
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
	
	public MyTableModel Select(int processnum, int processindex) throws Exception
	{

		String Query = "";
		try
		{
			Query = "Select  * from ems_send_queue " + "where (mod(id," + processnum + ")=" + processindex + ")";

			return mGet.GetData_Query(Query);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}
	public MyTableModel Select(int processnum, int processindex, Integer RowCount) throws Exception
	{

		String Query = "";
		try
		{
			Query = "Select  * from ems_send_queue " + "where (mod(id," + processnum + ")=" + processindex + ") limit "
					+ RowCount.toString();

			return mGet.GetData_Query(Query);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public boolean Insert(String USER_ID, String SERVICE_ID, String MOBILE_OPERATOR, String COMMAND_CODE,
			String INFO, Date SUBMIT_DATE, Date DONE_DATE, String MESSAGE_TYPE, String REQUEST_ID,
			String MESSAGE_ID, String CONTENT_TYPE,String CPID) throws Exception
	{

		// Cho phep ghi CDR
		String PROCESS_RESULT = "1";
		try
		{
			String Format_Query = "INSERT INTO ems_send_queue( USER_ID, SERVICE_ID, MOBILE_OPERATOR, "
					+ "COMMAND_CODE, INFO, SUBMIT_DATE, DONE_DATE, PROCESS_RESULT, MESSAGE_TYPE, REQUEST_ID,"
					+ " MESSAGE_ID, CONTENT_TYPE,CPID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			String strDoneDate = MyConfig.Get_DateFormat_InsertDB().format(Calendar.getInstance().getTime());
			String strSubmitDate = MyConfig.Get_DateFormat_InsertDB().format(Calendar.getInstance().getTime());
			if (SUBMIT_DATE != null)
			{
				strSubmitDate =  MyConfig.Get_DateFormat_InsertDB().format(SUBMIT_DATE);
			}
			if (DONE_DATE != null)
			{
				strDoneDate =MyConfig.Get_DateFormat_InsertDB().format(DONE_DATE);
			}
			Object[] arr_value = {USER_ID, SERVICE_ID, MOBILE_OPERATOR, COMMAND_CODE, INFO, strSubmitDate, strDoneDate,
					PROCESS_RESULT, MESSAGE_TYPE, REQUEST_ID, MESSAGE_ID, CONTENT_TYPE, CPID};

			return mExec.Execute_Query(Format_Query, arr_value);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	
	public boolean Insert_VMS(String USER_ID, String SERVICE_ID, String MOBILE_OPERATOR,
			String COMMAND_CODE, String INFO, Timestamp SUBMIT_DATE, Timestamp DONE_DATE, String MESSAGE_TYPE,
			String REQUEST_ID, String MESSAGE_ID, String CONTENT_TYPE, String CPID, String VMS_SVID) throws Exception
	{
		// Cho phep ghi CDR
		String PROCESS_RESULT = "1";
		try
		{
			String Format_Query = "INSERT INTO ems_send_queue( USER_ID, SERVICE_ID, MOBILE_OPERATOR, "
					+ "COMMAND_CODE, INFO, SUBMIT_DATE, DONE_DATE, PROCESS_RESULT, MESSAGE_TYPE, REQUEST_ID,"
					+ " MESSAGE_ID, CONTENT_TYPE,CPID,VMS_SVID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

			String strDoneDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
			String strSubmitDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
			if (SUBMIT_DATE != null)
			{
				strSubmitDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(SUBMIT_DATE);
			}
			if (DONE_DATE != null)
			{
				strDoneDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DONE_DATE);
			}
			Object[] arr_value = {USER_ID, SERVICE_ID, MOBILE_OPERATOR, COMMAND_CODE, INFO, strSubmitDate, strDoneDate,
					PROCESS_RESULT, MESSAGE_TYPE, REQUEST_ID, MESSAGE_ID, CONTENT_TYPE, CPID, VMS_SVID};

			return mExec.Execute_Query(Format_Query, arr_value);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public Boolean Insert(String USER_ID, String SERVICE_ID, String MOBILE_OPERATOR, String COMMAND_CODE,
			String CONTENT_TYPE, String INFO,  String MESSAGE_TYPE, String REQUEST_ID,
			String MESSAGE_ID, String TOTAL_SEGMENTS, String RETRIES_NUM, String CPId) throws Exception, SQLException
	{
		//Cho phep ghi CDR
				String PROCESS_RESULT = "1";
		try
		{
			String Format_Query = "	INSERT INTO ems_send_queue(USER_ID,SERVICE_ID,MOBILE_OPERATOR,COMMAND_CODE,CONTENT_TYPE,INFO,PROCESS_RESULT,MESSAGE_TYPE,REQUEST_ID,MESSAGE_ID,TOTAL_SEGMENTS,RETRIES_NUM,CPId) "
					+ " 	VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

			Object[] arr_value = {USER_ID, SERVICE_ID, MOBILE_OPERATOR, COMMAND_CODE, CONTENT_TYPE, INFO,
					PROCESS_RESULT, MESSAGE_TYPE, REQUEST_ID, MESSAGE_ID, TOTAL_SEGMENTS, RETRIES_NUM, CPId};

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

	public Boolean Delete(String ListID) throws Exception
	{
		String Query = "DELETE FROM ems_send_queue WHERE ID IN (" + ListID + ")";
		try
		{
			return mExec.Execute_Query(Query);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public boolean Insert(String USER_ID, String SERVICE_ID, String COMMAND_CODE, String INFO, String REQUEST_ID,
			String CONTENT_TYPE) throws Exception
	{

		try
		{
			String MOBILE_OPERATOR = "GPC";
			//Cho phep ghi CDR
			String PROCESS_RESULT = "1";
			String MESSAGE_TYPE = "2"; // 2 la khong tru tien
			String MESSAGE_ID = "1";
			String CPID = "0";

			String Format_Query = "INSERT INTO ems_send_queue( USER_ID, SERVICE_ID, MOBILE_OPERATOR, "
					+ "COMMAND_CODE, INFO, SUBMIT_DATE, DONE_DATE, PROCESS_RESULT, MESSAGE_TYPE, REQUEST_ID,"
					+ " MESSAGE_ID, CONTENT_TYPE,CPID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			String strDoneDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
			String strSubmitDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

			Object[] arr_value = {USER_ID, SERVICE_ID, MOBILE_OPERATOR, COMMAND_CODE, INFO, strSubmitDate, strDoneDate,
					PROCESS_RESULT, MESSAGE_TYPE, REQUEST_ID, MESSAGE_ID, CONTENT_TYPE, CPID};

			return mExec.Execute_Query(Format_Query, arr_value);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

}
