package dat.content;

import java.sql.SQLException;
import java.util.Random;
import java.util.Vector;

import db.connect.MyExecuteData;
import db.connect.MyGetData;
import db.define.DBConfig;
import db.define.MyTableModel;

public class DefineMT
{
	public MyExecuteData mExec;
	public MyGetData mGet;

	public enum MTType
	{
		Default(100), Invalid(101), Help(102), SystemError(103), Fail(104), 
		PushMT(105),Reminder(106),
				
		// ----Yeu cau tai noi dung
		/**
		 * Yêu cầu tải nội dung từ khách hàng thanh cong
		 */
		RequestSuccess(200),
		
		/**
		 * Không có tin tức nào để trả về cho khách hàng
		 */
		RequestNoNews(201),	
		
	
		/**
		 * Mua nội dung không thành công vì một lý do nào đó
		 */
		RequestFail(202),	
		
		/**
		 * KH không đủ tiền để tải nội dung
		 */
		RequestEnoughMoney(203),
		
		/**
		 * Thực hiện trừ tiền không thành công
		 */
		RequestChargeFail(204),

		// ----- Confirm
		/**
		 * KH xác nhận nội dung cần tải thành công
		 */
		ConfirmSuccess(300),
		
		/**
		 * KH không đủ tiền để tải nội dung
		 */
		ConfirmNotEnoughMoney(301),
		
		/**
		 * Đã mua nội dung nhưng mã confirm bị sai
		 */
		ConfirmInvalidContent(302),		
	
		/**
		 * Confirm download không thành công
		 */
		ConfirmFail(303),
		
		/**
		 * Chưa mua nội dung mà đã confirm
		 */
		ConfirmNotBuy(304),
		// -----THÔNG BÁO
		
		/**
		 * Thông báo sau khi khách hàng không confirm trong 1 khoảng thời gian nhất định
		 */
		NotifyExpire(400),
		
		;
		
		private int value;

		private MTType(int value)
		{
			this.value = value;
		}

		public int GetValue()
		{
			return this.value;
		}

		public static MTType FromInt(int iValue)
		{
			for (MTType type : MTType.values())
			{
				if (type.GetValue() == iValue) return type;
			}
			return Default;
		}
	}

	public DefineMT(DBConfig mDBConfig) throws Exception
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
	 * 
	 * @param Type
	 *            : Cách thức lấy dữ liệu
	 *            <p>
	 *            Type = 1: lấy thông tin chi tiết 1 record Para_1 = DefineMTID
	 *            </p>
	 *            <p>
	 *            Type = 2: Lấy dữ liệu theo MTTypeID (Para_1 = MTTypeID
	 *            </p>
	 *            <p>
	 *            Type = 4: Lấy tất cả dữ liệu đã được active
	 *            </p>
	 * @param Para_1
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public MyTableModel Select(int Type, String Para_1) throws Exception, SQLException
	{
		try
		{
			String Arr_Name[] = {"Type", "Para_1"};
			String Arr_Value[] = {Integer.toString(Type), Para_1};

			return mGet.GetData_Pro("Sp_DefineMT_Select", Arr_Name, Arr_Value);
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
	 * Lấy danh sách các DefineMT trong database
	 * 
	 * @return
	 * @throws Exception
	 */
	public Vector<DefineMTObject> GetAllMT() throws Exception
	{
		try
		{
			Vector<DefineMTObject> mList = new Vector<DefineMTObject>();
			MyTableModel mTable = Select(4, null);

			for (int i = 0; i < mTable.GetRowCount(); i++)
			{
				DefineMTObject mObject = new DefineMTObject();

				if (mTable.GetValueAt(i, "MTTypeID") != null)
				{
					mObject.mMTType = MTType.FromInt(Integer.parseInt(mTable.GetValueAt(i, "MTTypeID").toString()));
				}
				mObject.MTContent = mTable.GetValueAt(i, "MTContent").toString();

				mList.add(mObject);
			}
			return mList;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	/**
	 * Lấy nội dung MT mặc định theo MTType
	 * 
	 * @param MTTypeID
	 * @return
	 * @throws Exception
	 */
	private static String GetDefaultMT(MTType mMTTypeID) throws Exception
	{
		try
		{
			String ShortCode = "1546";
			String MT = "";

			switch(mMTTypeID)
			{
				case Invalid :
					MT = "Tin nhan sai cu phap, de bien thong tin chi tiet ve dich vu voi long soan TG gui "
							+ ShortCode;
					break;
				default :
					MT = "Tin nhan sai cu phap, de bien thong tin chi tiet ve dich vu voi long soan TG gui "
							+ ShortCode;
					break;

			}
			return MT;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	/**
	 * Lấy 1 MT đã được định nghĩa trong datbase, Nếu trong database không có
	 * thì lấy MT mặc định của dịch vụ
	 * 
	 * @param mList
	 *            : Danh sách các DefineMT
	 * @param mMTType
	 *            : Loại MT cần lấy
	 * 
	 * @param ServiceID
	 *            : ID của dịch vụ cần lấy
	 * @return
	 * @throws Exception
	 */
	public static String GetMTContent(Vector<DefineMTObject> mList, MTType mMTType) throws Exception
	{
		try
		{
			if (mList.size() < 1) return GetDefaultMT(mMTType);

			Vector<DefineMTObject> mList_Random = new Vector<DefineMTObject>();

			for (DefineMTObject mObject : mList)
			{
				if (mObject.mMTType == mMTType && mObject.MTContent.length() > 0) mList_Random.add(mObject);
			}

			if (mList_Random.size() < 1) return GetDefaultMT(mMTType);

			if (mList_Random.size() == 1) return mList_Random.get(0).MTContent;

			Random mRandom = new Random();
			int Range = mList_Random.size();
			int Rand = mRandom.nextInt(Range);

			return mList_Random.get(Rand).MTContent;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

}
