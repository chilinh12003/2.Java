package dat.gateway;

import db.connect.MyExecuteData;
import db.connect.MyGetData;
import db.define.DBConfig;
import db.define.MyTableModel;

public class sms_receive_forward
{
	public MyExecuteData mExec;
	public MyGetData mGet;

	public sms_receive_forward(DBConfig mDBConfig) throws Exception
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
	
	/**
	 * lấy dữ liệu table sms_receive_forward (table dùng để lưu các thông tin MO
	 * đã forward cho đối tác
	 * 
	 * @param Type
	 *            : Type = 1: lấy dữ liệu theo RequestID = Para_1
	 * @param Para_1
	 * @return
	 * @throws Exception
	 */
	public MyTableModel Select(int Type, String Para_1) throws Exception
	{

		String Query = "";
		try
		{
			if (Type == 1) // Lấy dữ liệu theo RequestID
			{
				Query = " SELECT * FROM sms_receive_forward WHERE REQUEST_ID = '" + Para_1 + "'";
			}

			return mGet.GetData_Query(Query);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	/**
	 * 
	 * @param Type
	 *            <p>
	 *            Lấy các MO để kiểm tra retry
	 *            </p>
	 * @param Para_1
	 * @param Para_2
	 * @return
	 * @throws Exception
	 */
	public MyTableModel Select(Long MaxID, Integer RowCount) throws Exception
	{

		String Query = "";
		try
		{
			/*
			 * // Lấy các record cách đây 1h mà chưa trả MT cho khách hàng //
			 * (Para_1 = RowCount, Para_2 = Minute Calendar mCalendar_Get =
			 * Calendar.getInstance(); mCalendar_Get.set(Calendar.MINUTE,
			 * BeforeMinute * -1); String InsertDate =
			 * MyConfig.DateFormat_InsertDB.format(mCalendar_Get.getTime());
			 */

			Query = " SELECT * FROM sms_receive_forward WHERE id > " + MaxID.toString() + "  ORDER BY id ASC LIMIT 0,"
					+ RowCount.toString();

			return mGet.GetData_Query(Query);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public MyTableModel Select(String ServiceID, String UserID, String RequestID) throws Exception
	{

		String Query = "";
		try
		{
			Query = " SELECT * FROM sms_receive_forward WHERE SERVICE_ID ='" + ServiceID + "' AND USER_ID ='" + UserID
					+ "' AND REQUEST_ID = '" + RequestID + "'";

			return mGet.GetData_Query(Query);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public Boolean Insert(String USER_ID, String SERVICE_ID, String MOBILE_OPERATOR, String COMMAND_CODE, String INFO,
			String insert_date, String receive_date, String RESPONDED, String REQUEST_ID, String NOTES, String CPId,
			String SPAM, int Status, int RetryCount) throws Exception
	{
		try
		{
			String Format_Query = " INSERT INTO sms_receive_forward (USER_ID,SERVICE_ID,MOBILE_OPERATOR,COMMAND_CODE,INFO,insert_date,receive_date,RESPONDED,REQUEST_ID,NOTES,CPId,SPAM,Status,RetryCount) "
					+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			Object[] arr_value =
			{USER_ID, SERVICE_ID, MOBILE_OPERATOR, COMMAND_CODE, INFO, insert_date, receive_date, RESPONDED,
					REQUEST_ID, NOTES, CPId, SPAM, Status, RetryCount};

			return mExec.Execute_Query(Format_Query, arr_value);

		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public Boolean Insert_VMS(String USER_ID, String SERVICE_ID, String MOBILE_OPERATOR, String COMMAND_CODE,
			String INFO, String insert_date, String receive_date, String RESPONDED, String REQUEST_ID, String NOTES,
			String CPId, String SPAM, int Status, int RetryCount, String VMS_SVID) throws Exception
	{
		try
		{

			String Format_Query = " INSERT INTO sms_receive_forward (USER_ID,SERVICE_ID,MOBILE_OPERATOR,COMMAND_CODE,INFO,insert_date,receive_date,RESPONDED,REQUEST_ID,NOTES,CPId,SPAM,Status,RetryCount,VMS_SVID) "
					+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			Object[] arr_value =
			{USER_ID, SERVICE_ID, MOBILE_OPERATOR, COMMAND_CODE, INFO, insert_date, receive_date, RESPONDED,
					REQUEST_ID, NOTES, CPId, SPAM, Status, RetryCount, VMS_SVID};

			return mExec.Execute_Query(Format_Query, arr_value);

		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	/**
	 * Xóa dữ liệu theo RequestID
	 * 
	 * @param RequestID
	 * @return
	 * @throws Exception
	 */
	public Boolean Delete(String RequestID) throws Exception
	{
		String Query = "DELETE FROM sms_receive_forward WHERE REQUEST_ID= '" + RequestID + "'";
		try
		{
			return mExec.Execute_Query(Query);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	/**
	 * 
	 * @param Type
	 *            <p>
	 *            Type = 1: Xóa theo 1 list các ID (ListRequestID=1,2,3,4)
	 *            </p>
	 *            <p>
	 *            Type = 2: Xóa theo 1 ID (ListRequestID =1)
	 *            </p>
	 * @param ListRequestID
	 * @return
	 * @throws Exception
	 */
	public Boolean Delete(int Type, String ListRequestID) throws Exception
	{

		String Query = "";

		try
		{
			if (Type == 1)
			{
				Query = "DELETE FROM sms_receive_forward WHERE id IN (" + ListRequestID + ")";
			}
			else if (Type == 2)
			{
				Query = "DELETE FROM sms_receive_forward WHERE id = " + ListRequestID + " ";
			}
			else return false;

			return mExec.Execute_Query(Query);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	/**
	 * Update số lần retry count của 1 Record
	 * 
	 * @param ID
	 * @param RetryCount
	 * @return
	 * @throws Exception
	 */
	public Boolean Update_RetryCount(Long ID, Integer RetryCount, String Result) throws Exception
	{
		String Query = "";
		try
		{
			Query = " UPDATE sms_receive_forward SET RetryCount = " + RetryCount.toString() + ", Status = 1*" + Result
					+ " WHERE id = " + ID.toString() + " ";

			return mExec.Execute_Query(Query);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public Boolean Update_RetryCount(String RequestID, Integer RetryCount, String Result) throws Exception
	{
		String Query = "";
		try
		{
			Query = " UPDATE sms_receive_forward SET RetryCount = " + RetryCount.toString() + ", Status = " + Result
					+ " WHERE REQUEST_ID = '" + RequestID + "' ";

			return mExec.Execute_Query(Query);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}
}
