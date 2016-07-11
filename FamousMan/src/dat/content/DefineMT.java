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
		Default(100), Invalid(101), Help(102), SystemError(103), Fail(104), GetOTPSuccess(105), GetOTPNotReg(106), 
		PushMT(107),Reminder(108),
		
		RequestMarkSuccess(110),
		RequestMarkNotReg(111),
		RequestMarkFail(112),
		
		// -----ĐĂNG KÝ DỊCH VỤ
		/**
		 * Đăng ký mới thành công
		 */
		RegNewSuccess(200),

		/**
		 * Đăng ký lai thành công và miễn phí
		 */
		RegAgainSuccessFree(201),
		/**
		 * Đăng ký lại thành công không miễn phí (đăng ký lại nhưng hết thời
		 * gian khuyến mại)
		 */
		RegAgainSuccessNotFree(202),

		/**
		 * Đăng ký rồi nhưng lại đăng ký tiếp vần còn trong thời gian khuyến mại
		 */
		RegRepeatFree(203),

		/**
		 * Đắng ký lặp trong thời gian hết khuyến mại
		 */
		RegRepeatNotFree(204),

		/**
		 * Đăng ký nhưng tải khoản khách hàng không đủ tiền
		 */
		RegNotEnoughMoney(205),
		/**
		 * Đăng ký không thành công
		 */
		RegFail(206),	
		
	

		/**
		 * DK nhưng hệ thống bị lỗi
		 */
		RegSystemError(207),
		
		/**
		 * Đăng ký từ hệ thống VASDealer của vinaphone
		 */
		RegVASDealerSuccessFree(208),
		
		RegVASDealerSuccessNotFree(209),
		
		RegVASVoucherSuccessFree(210),
		
		RegVASVoucherSuccessNotFree(211),
		
		RegMOBILEADSSuccessFree(212),
		
		RegMOBILEADSSuccessNotFree(213),
		
		RegSuggestMT(215),
		
		/**
		 * Đăng ký lại trong ngày thành công
		 */
		RegAgainSuccessOnDay(216),

		// -----HỦY DỊCH VỤ
		/**
		 * Hủy thành công dịch vụ
		 */
		DeregSuccess(300),

		/**
		 * Huy khi mà chưa đăng ký dịch vụ
		 */
		DeregNotRegister(301),

		/**
		 * Hủy không thành công do lỗi hệ thống...
		 */
		DeregFail(302),

		DeregSystemError(303),

		/**
		 * Hủy khi gia hạn không thành công
		 */
		DeregExtendFail(304),

		/**
		 * Nội dung MT DK FREE thành công từ kênh CCOS của Vinaphone
		 */
		RegCCOSSuccessFree(305),

		/**
		 * Nội dung MT DK Tính phí thành công từ kênh CCOS của Vinaphone
		 */
		RegCCOSSuccessNotFree(306),

		// ----MUA DỮ KIỆN

		/**
		 * Mua dữ kiện khi chưa đăng ký
		 */
		BuySugNotReg(400),

		/**
		 * Mua gợi ý nhưng không đủ tiền
		 */
		BuySugNotEnoughMoney(401),

		/**
		 * Mua dữ kiện khi phiên chơi chưa bắt đầu hoặc đã kết thúc
		 */
		BuySugExpire(402),

		/**
		 * Mua dữ kiện khi vượt quá 20 lần trong 1 ngày
		 */
		BuySugLimit(403),

		/**
		 * Mua dữ kiện không thành công do lỗi hệ thống hoặc một lý do
		 * nào khác
		 */
		BuySugFail(404),

		/**
		 * Mua dữ kiện khi đã trả lời đúng trước đó
		 */
		BuySugAnswerRight(405),

		/**
		 * Mua thành công và trả về dữ kiện
		 */
		BuySugSuccess(406),

		/**
		 * Bản tin nhắc nhở khi mua dữ kiện (nếu có)
		 */
		BuySugNotify(407),

		/**
		 * Mua dữ kiện khi thuê bao gia hạn không thành công
		 */
		BuySugNotExtend(408),
		
		/**
		 * MT chứa nội dung của dữ kiện
		 */
		BuySuggestMT(409),

		// -----TRẢ LỜI
		/**
		 * trả lời khi chưa mua dữ kiện
		 */
		AnswerNotBuy(500),

		/**
		 * Trả lời khi chưa đăng ký dịch vụ
		 */
		AnswerNotReg(501),

		/**
		 * Dự đoán vượt quá số lần cho phép, mỗi 1 lần mua chỉ được
		 * dự đoán 1 lần Và dự đoán là cho lần mua gần nhất
		 */
		AnswerLimit(502),

		/**
		 * Trả lời khi phiên chưa diễn ra
		 */
		AnswerExpire(503),

		/**
		 * Dự đoán sai
		 */
		AnswerWrong(504),

		/**
		 * Dự đoán không thành công do phát sinh lỗi hoặc lý do khác...
		 */
		AnswerFail(505),

		/**
		 * Dự đoán chính xác với kết quả
		 */
		AnswerSuccess(506),
		
		/**
		 * trả lời khi đã trả lời đúng trức đó
		 */
		AnswerWhenAnswerRight(507),

		/**
		 * Thông báo về phiên chơi mới
		 */
		NotifyNewSession(600),

		/**
		 * thông báo về người chiến thằng
		 */
		NotifyWinner(601),
		

		/**
		 * Tin tức hàng này
		 */
		NewsDaily(602),

		/**
		 * Thông báo khi gia hạn thành công
		 */
		NotifyRenewSuccess(603), 
		
		/**
		 * Thông báo khi gia hạn thành công, mà lần gia hạn trước không thành công.
		 */
		NotifyRenewSuccessBeforeFail(604),
		
		/**
		 * Thông báo chỉ gửi đến cho người chiến thắng
		 */
		NotifyOnlyWinner(605),
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
