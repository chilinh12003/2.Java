package dat.sub;

import java.sql.SQLException;

import db.connect.MyExecuteData;
import db.connect.MyGetData;
import db.define.DBConfig;
import db.define.MyTableModel;

public class Subscriber
{

	public enum Status
	{
		NoThing(0),
		/**
		 * Kích hoạt
		 */
		Active(1),
		/**
		 * Hủy kích hoạt
		 */
		Deactive(2),

		/**
		 * Charge không thành công
		 */
		ChargeFail(3),
		/**
		 * Đang chờ xác nhận hủy dịch vụ
		 */
		ConfirmDeregister(4),

		/**
		 * Đăng từ kênh API và được sử dụng gọi bundle
		 */
		ActiveBundle(5),
		/**
		 * Đăng ký từ kênh API và được sử dụng trial
		 */
		ActiveTrial(6),
		/**
		 * Đăng ký từ kênh API và được sử dụng Promtion
		 */
		ActivePromotion(7),

		/**
		 * Thuê bao này đã được hệ thống Vinaphone Hủy Thuê Bao, nếu có đăng ký
		 * lại thì sẽ coi như số điện thoại mới
		 */
		UndoSub(8), 
		
		/**
		 * Đăng ký lần đầu và được miễn phí
		 */
		ActiveFree(10),
		;

		private int value;

		private Status(int value)
		{
			this.value = value;
		}

		public int GetValue()
		{
			return this.value;
		}

		public static Status FromInt(int iValue)
		{
			for (Status type : Status.values())
			{
				if (type.GetValue() == iValue) return type;
			}
			return NoThing;
		}
	}

	public MyExecuteData mExec;
	public MyGetData mGet;
	
	public Subscriber(DBConfig mConfigObj) throws Exception
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

	/**
	 * Lấy dữ liệu
	 * 
	 * @param Type
	 *            <p>
	 *            Type = 0: Lấy dữ liệu mẫu
	 *            </p>
	 * @param Para_1
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public MyTableModel Select(int Type) throws Exception
	{
		try
		{
			String Arr_Name[] = {"Type"};
			String Arr_Value[] = {Integer.toString(Type)};

			return mGet.GetData_Pro("Sp_Subscriber_Select", Arr_Name, Arr_Value);
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
	 *            Type = 2: Lấy tất cả dịch vụ 1 số điện thoại (Para_1 =
	 *            PID, Para_2 = MSISDN)
	 *            </p>
	 * @param Para_1
	 * @param Para_2
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public MyTableModel Select(int Type, String Para_1, String Para_2) throws Exception, SQLException
	{
		try
		{
			String Arr_Name[] = {"Type", "Para_1", "Para_2"};
			String Arr_Value[] = {Integer.toString(Type), Para_1, Para_2};

			return mGet.GetData_Pro("Sp_Subscriber_Select", Arr_Name, Arr_Value);
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

	/**
	 * 
	 * @param Type
	 *            <p>
	 *            Type = 3: Lấy danh sách (Para_1 = RowCount, Para_2 = PID,
	 *            Para_3 = OrderID )
	 *            </p>
	 * @param Para_1
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public MyTableModel Select(int Type, String Para_1, String Para_2, String Para_3) throws Exception, SQLException
	{
		try
		{
			String Arr_Name[] = {"Type", "Para_1", "Para_2", "Para_3"};
			String Arr_Value[] = {Integer.toString(Type), Para_1, Para_2, Para_3};

			return mGet.GetData_Pro("Sp_Subscriber_Select", Arr_Name, Arr_Value);
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

	/**
	 * 
	 * @param Type
	 *            <p>
	 *            Type = 4:Lấy danh sách (Para_1 = RowCount, Para_2 = PID,
	 *            Para_3 = OrderID, Para_4 = ProcessNumber, Para_5 =
	 *            ProcessIndex, Para_6 = StatusID)
	 *            </p>
	 * @param Para_1
	 * @param Para_2
	 * @param Para_3
	 * @param Para_4
	 * @param Para_5
	 * @param Para_6
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public MyTableModel Select(int Type, String Para_1, String Para_2, String Para_3, String Para_4, String Para_5,
			String Para_6) throws Exception, SQLException
	{
		try
		{
			String Arr_Name[] = {"Type", "Para_1", "Para_2", "Para_3", "Para_4", "Para_5", "Para_6"};
			String Arr_Value[] = {Integer.toString(Type), Para_1, Para_2, Para_3, Para_4, Para_5, Para_6};

			return mGet.GetData_Pro("Sp_Subscriber_Select", Arr_Name, Arr_Value);
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

	/**
	 * 
	 * @param Type
	 *            <p>
	 *            Type = 5: Lấy danh sách(Para_1 = RowCount, Para_2 = PID,
	 *            Para_3 = OrderID, Para_4 = ProcessNumber, Para_5 =
	 *            ProcessIndex )
	 *            </p>
	 * @param Para_1
	 * @param Para_2
	 * @param Para_3
	 * @param Para_4
	 * @param Para_5
	 * @param Para_6
	 * @param Para_7
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public MyTableModel Select(int Type, String Para_1, String Para_2, String Para_3, String Para_4, String Para_5)
			throws Exception, SQLException
	{
		try
		{
			String Arr_Name[] = {"Type", "Para_1", "Para_2", "Para_3", "Para_4", "Para_5",};
			String Arr_Value[] = {Integer.toString(Type), Para_1, Para_2, Para_3, Para_4, Para_5};

			return mGet.GetData_Pro("Sp_Subscriber_Select", Arr_Name, Arr_Value);
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

	/**
	 * 
	 * @param Type
	 *            <p>
	 *            Type = 0: Update full
	 *            </p>
	 *            <p>
	 *            Type = 1: Update thông tin mua dữ kiện
	 *            </p>
	 *            <p>
	 *            Type = 2: Update thông tin trả lời câu hỏi
	 *            </p>
	 * 
	 * @param XMLContent
	 * @return
	 * @throws Exception
	 */
	public boolean Update(int Type, String XMLContent) throws Exception
	{
		try
		{
			String[] Arr_Name = {"Type", "XMLContent"};
			String[] Arr_Value = {Integer.toString(Type), XMLContent};
			return mExec.Execute_Pro("Sp_Subscriber_Update", Arr_Name, Arr_Value);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	/**
	 * Cập nhật thông tin khi charging
	 * 
	 * @param Type
	 * @param XMLContent
	 * @return
	 * @throws Exception
	 */
	public boolean UpdateCharge(int Type, String XMLContent) throws Exception
	{
		try
		{
			String[] Arr_Name = {"Type", "XMLContent"};
			String[] Arr_Value = {Integer.toString(Type), XMLContent};
			return mExec.Execute_Pro("Sp_Subscriber_UpdateCharge", Arr_Name, Arr_Value);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public boolean Insert(int Type, String XMLContent) throws Exception
	{
		try
		{
			String[] Arr_Name = {"Type", "XMLContent"};
			String[] Arr_Value = {Integer.toString(Type), XMLContent};
			return mExec.Execute_Pro("Sp_Subscriber_Insert", Arr_Name, Arr_Value);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	/**
	 * Di chuyển từ Sub sang UnSub
	 * 
	 * @param Type
	 * @param XMLContent
	 * @return
	 * @throws Exception
	 */
	public boolean Move(int Type, String XMLContent) throws Exception
	{
		try
		{
			mExec.UseTransaction = true;
			String[] Arr_Name = {"Type", "XMLContent"};
			String[] Arr_Value = {Integer.toString(Type), XMLContent};
			return mExec.Execute_Pro("Sp_Subscriber_Move", Arr_Name, Arr_Value);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	/**
	 * Xóa dữ liệu
	 * 
	 * @param Type
	 *            <p>
	 *            Type = 4: Xóa dữ liếu với PID, MSISDN, ServiceID
	 *            </p>
	 * @param XMLContent
	 * @return
	 * @throws Exception
	 */
	public boolean Delete(int Type, String XMLContent) throws Exception
	{
		try
		{
			String[] Arr_Name = {"Type", "XMLContent"};
			String[] Arr_Value = {Integer.toString(Type), XMLContent};
			return mExec.Execute_Pro("Sp_Subscriber_Delete", Arr_Name, Arr_Value);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public boolean Delete(Integer PID, String MSISDN, Integer ServiceID) throws Exception
	{
		try
		{
			String XMLContent = "<Parent><Child><PID>" + PID + "</PID><MSISDN>" + MSISDN + "</MSISDN><ServiceID>"
					+ ServiceID + "</ServiceID></Child></Parent>";
			return Delete(4, XMLContent);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	/**
	 * Thay doi so dien thoai
	 * 
	 * @param PIDOld
	 * @param MSISDNOld
	 * @param PIDNew
	 * @param MSISDNNew
	 * @return
	 * @throws Exception
	 */
	public boolean ChangeMSISDN(Integer PIDOld, String MSISDNOld, Integer PIDNew, String MSISDNNew) throws Exception
	{
		try
		{
			String[] Arr_Name = {"PIDOld", "MSISDNOld", "PIDNew", "MSISDNNew"};
			String[] Arr_Value = {PIDOld.toString(), MSISDNOld, PIDNew.toString(), MSISDNNew};
			return mExec.Execute_Pro("Sp_Subscriber_ChangeMSISDN", Arr_Name, Arr_Value);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

}
