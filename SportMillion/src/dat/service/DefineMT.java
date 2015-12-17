package dat.service;

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
		RegFail(220),

		/**
		 * DK nhưng hệ thống bị lỗi
		 */
		RegSystemError(221),

		/**
		 * Đăng ký từ CCOS của vinaphone và được miễn phí chu kỳ cước đầu
		 */
		RegCCOSSuccessFree(222),
		
		/**
		 * Đăng ký từ CCOS của vinaphone và không được miễn phí 
		 */
		RegCCOSSuccessNotFree(223),		
		
		RegVASDealerSuccessFree(224),
		
		RegVASDealerSuccessNotFree(225),
		
		RegVASVoucherSuccessFree(226),
		
		RegVASVoucherSuccessNotFree(227),
		
		RegMOBILEADSSuccessFree(228),
		
		RegMOBILEADSSuccessNotFree(229),
		
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
		 * Xác nhận hành động hủy
		 */
		DeregConfirm(302),

		/**
		 * Hủy không thành công do lỗi hệ thống...
		 */
		DeregFail(303),

		DeregSystemError(304),

		/**
		 * Gửi Y trong khi chưa gửi Confirm trước đó
		 */
		DeregNotSendConfirm(305),
		

		/**
		 * Hủy khi gia hạn không thành công
		 */
		ExtendDereg(400),

		// -----CAC TIN NHẮN VỀ THÔNG BÁO----------------
		/**
		 * Thông báo về điểm
		 */
		NotifyMark(500),

		/**
		 * Thông báo khách hàng được miễn phí 1 ngày khi đăng ký lần
		 * đầu
		 */
		NotifyPromotion(501),
		
		/**
		 * Thông báo hướng dẫn sử dụng dịch vụ
		 */
		NotifyGuide(502),

		// -----HƯỚNG DẪN SỬ DỤNG DV
		/**
		 * Các tin nhắn phục vụ cho keyword DD
		 */
		Guide(600), GuideKQ(601), GuideBT(602), GuideTS(603), GuideGB(604), GuideTV(605), GuideMaxMO(606),

		// -----TRẢ LỜI
		/**
		 * Khách hàng gửi câu trả lời không giá trị VD: KQ hoac BT mà ko
		 * có giá trị theo sau
		 */
		AnswerInvalid(700), AnswerKQ1(701), AnswerKQ2(702), AnswerKQ3(703),

		AnswerBT(704),

		AnswerTS(705),

		AnswerGB1(706), AnswerGB2(707), AnswerGB3(708),

		AnswerTV(709),

		/**
		 * Tin nhắn dự đoán cuối cùng (tin nhắn thứ 10)
		 */
		AnswerFinal(710),

		/**
		 * Gửi tin nhắn khi thời gian dự đoán đã hết
		 */
		AnswerExpire(711),

		/**
		 * Khi nhắn tin vượt quá 10 tin (tin nhắn thứ 11 trở đi)
		 */
		AnswerOver(712),

		/**
		 * Trả lời khi tài khoảng không thể trừ tiền được
		 */
		AnswerNotExtend(713),

		/**
		 * Trả lời khi chưa đăng ký
		 */
		AnswerNotReg(714),

		AnswerGuideKQ(715), 
		
		AnswerGuideBT(716), 
		
		AnswerGuideTS(717),
		
		AnswerGuideGB(718), 
		
		AnswerGuideTV(719),

		AnswerFail(720), AnswerSystemError(721),

		// -----CÁC TRƯỜNG HỢP KHÁC
		/**
		 * Tra cứu mã dữ thưởng
		 */
		ConsultCode(800),

		/**
		 * Tra cứu trận đấu
		 */
		ConsultMatch(801),

		/**
		 * Ngừng nhận bản tin thông báo
		 */
		StopRemider(802),
		/**
		 * Tiếp tục nhận bản tin thông báo
		 */
		StartRemider(803),
		/**
		 * Chưa đăng ký đã tra cứu MDT
		 */
		ConsultCodeNotReg(804),

		/**
		 * Tra cức khi thuê bao đang retry charge
		 */
		ConsultCodeNotExtend(805),

		/**
		 * Hủy, đăng ký nhận tin khi mà chưa đăng ký
		 */
		RemiderNotReg(806),

		/**
		 * Thông báo kết quả dự đoán sau khi trận đấu kết thúc
		 */
		NotifyResult(807),
		
		PushMT(808),
	
		GetOTPSuccess(809),
		
		/**
		 * Bắn bản tin remider
		 */
		PushMTReminder(810),
		
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
				if (type.GetValue() == iValue)
					return type;
			}
			return Default;
		}

	}

	public DefineMT(DBConfig mConfigObj) throws Exception
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
			String Arr_Name[] = { "Type", "Para_1" };
			String Arr_Value[] = { Integer.toString(Type), Para_1 };

			return mGet.GetData_Pro("Sp_DefineMT_Select", Arr_Name, Arr_Value);
		}
		catch (SQLException ex)
		{
			int s = ex.getErrorCode();
			String t = ex.getSQLState();
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
				case Invalid:
					MT = "Tin nhan sai cu phap, de bien thong tin chi tiet ve dich vu voi long soan TG gui " + ShortCode;
					break;
				default :
					MT = "Tin nhan sai cu phap, de bien thong tin chi tiet ve dich vu voi long soan TG gui " + ShortCode;
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
			if (mList.size() < 1)
				return GetDefaultMT(mMTType);

			Vector<DefineMTObject> mList_Random = new Vector<DefineMTObject>();

			for (DefineMTObject mObject : mList)
			{
				if (mObject.mMTType == mMTType && mObject.MTContent.length() > 0)
					mList_Random.add(mObject);
			}

			if (mList_Random.size() < 1)
				return GetDefaultMT(mMTType);

			if (mList_Random.size() == 1)
				return mList_Random.get(0).MTContent;

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
