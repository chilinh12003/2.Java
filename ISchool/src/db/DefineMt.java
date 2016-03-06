package db;

import java.util.Random;
import java.util.Vector;

/**
 * DefineMt entity. @author MyEclipse Persistence Tools
 */

public class DefineMt extends DAOBase implements java.io.Serializable {

	public enum MTType
	{
		Default(100), Invalid(101), Help(102), SystemError(103), Fail(104), GetOTPSuccess(105), GetOTPNotReg(106), PushMT(
				107), Reminder(108),
				NotifyBeginSession (109),

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
		 * MT chứa thông tin câu hỏi đầu tiền cho lần đăng ký đầu
		 */
		RegQuestionMT(208),
		
		
		/**
		 * Khôi phục thành công
		 */
		RestoreSuccess(220),
		/**
		 * Khôi phục khi chưa đăng ký
		 */
		RestoreNotReg(221),
		/**
		 * Khôi phục không đủ tiền
		 */
		RestoreNotEnoughMoney(222),
		/**
		 * Khôi phục khi thuê bao đang hoạt động
		 */
		RestoreWhenActive(223),
		/**
		 * 
		 */
		RestoreFail(224),

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
	
		// ----MUA BỘ CÂU HỎI

		/**
		 * Mua bộ câu hỏi khi chưa đăng ký
		 */
		BuyNotReg(400),
		
		/**
		 * Mua bộ câu hỏi khi phiên chơi chưa bắt đầu hoặc đã kết thúc
		 */
		BuyExpire(401),

		/**
		 * MUA khi đã hết quyền mua (đã mua 1 và mua 2)
		 */
		BuyLimit(402),
		
		/**
		 * Mua bộ câu hỏi không thành công do lỗi hệ thống hoặc một lý do
		 * nào khác
		 */
		BuyFail(403),		

		/**
		 * Bản tin nhắc nhở khi mua bộ câu hỏi (nếu có)
		 */
		BuyNotify(404),
		
		
		/**
		 * MT chứa nội dung của câu hỏi
		 */
		BuyQuestionMT(405),
		
		/////////////////////////////////////////////
		///MUA 1
		////////////////////////////////////////////
		/*
		 * Mua thành công bộ 3 câu hỏi
		 */
		BuyOneSuccess(410),
		
		/*
		 * Mua 1 nhưng không đủ tiền
		 */
		BuyOneNotEnoughMoney(411),	
		
		/**
		 * Mua bộ câu hỏi khi thuê bao gia hạn không thành công
		 */
		BuyOneNotExtend(412),
		
		/**
		 * Mua bộ 3 câu hỏi khi đã mua trước đó (chưa MUA 2)
		 */
		BuyOneLimit(413),
		
		/////////////////////////////////////////////
		///MUA 2
		////////////////////////////////////////////
		/*
		 * Mua thành công bộ 7 câu hỏi
		 */
		BuyTwoSuccess(420),
		
		/*
		 * Mua 2 nhưng không đủ tiền
		 */
		BuyTwoNotEnoughMoney(421),		
		/**
		 * Mua bộ câu hỏi khi thuê bao gia hạn không thành công
		 */
		BuyTwoNotExtend(422),
		
		/**
		 * Mua bộ 7 câu hỏi khi đã mua trước đó (chưa MUA 1)
		 */
		BuyTwoLimit(423),
		
		
		

		// -----TRẢ LỜI
		/**
		 * trả lời khi chưa mua bộ câu hỏi
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
		AnswerRight(506),
		
		//----TRẢ LỜI CÂU CUỐI CÙNG CHO 5 CÂU HỎI FREE
		/**
		 * Trả lời đúng câu cuối cùng nhưng có ít nhất 1 câu sai trong 5 câu
		 */
		AnswerFree_LastRight_Complete(510),
		/**
		 * Trả lời sai câu cuối cùng
		 */
		AnswerFree_LastWrong_Complete(511),
		/**
		 * Trả lời câu cuối cùng và 5 câu đều đúng
		 */
		AnswerFree_AllRight_Complete(512),
		
		//----TRẢ LỜI CÂU CUỐI CÙNG CHO GÓI 3 CÂU HỎI
		/**
		 * Trả lời đúng câu cuối cùng nhưng có ít nhất 1 câu sai trong 3 câu chưa mua 2
		 */
		AnswerOne_LastRight_Complete_NotBuyTwo(520),
		/**
		 * Trả lời sai câu cuối cùng chưa mua 2
		 */
		AnswerOne_LastWrong_Complete_NotBuyTwo(521),
		/**
		 * Trả lời câu cuối cùng và 3 câu đều đúng chưa mua 2
		 */
		AnswerOne_AllRight_Complete_NotBuyTwo(522),
		
		/**
		 * Trả lời đúng câu cuối cùng nhưng có ít nhất 1 câu sai trong 3 câu đã mua 2
		 */
		AnswerOne_LastRight_Complete_BuyTwo(523),
		/**
		 * Trả lời sai câu cuối cùng đã chưa mua 2
		 */
		AnswerOne_LastWrong_Complete_BuyTwo(524),
		/**
		 * Trả lời câu cuối cùng và 3 câu đều đúng đã chưa mua 2
		 */
		AnswerOne_AllRight_Complete_BuyTwo(525),
		
		
		/**
		 * Trả lời đúng câu cuối cùng nhưng có ít nhất 1 câu sai trong 7 câu chưa mua 1
		 */
		AnswerTwo_LastRight_Complete_NotBuyOne(530),
		/**
		 * Trả lời sai câu cuối cùng chưa mua 1
		 */
		AnswerTwo_LastWrong_Complete_NotBuyOne(531),
		/**
		 * Trả lời câu cuối cùng và 3 câu đều đúng chưa mua 1
		 */
		AnswerTwo_AllRight_Complete_NotBuyOne(532),
		
		/**
		 * Trả lời đúng câu cuối cùng nhưng có ít nhất 1 câu sai trong 7 câu đã mua 1
		 */
		AnswerTwo_LastRight_Complete_BuyOne(533),
		/**
		 * Trả lời sai câu cuối cùng đã chưa mua 1
		 */
		AnswerTwo_LastWrong_Complete_BuyOne(534),
		/**
		 * Trả lời câu cuối cùng và 3 câu đều đúng đã chưa mua 1
		 */
		AnswerTwo_AllRight_Complete_BuyOne(535),
		
		// -----NOTIFY
				

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
		 * Thông báo khi gia hạn thành công, mà lần gia hạn trước không thành
		 * công.
		 */
		NotifyRenewSuccessBeforeFail(604),

		 ;
		
		private int value;

		private MTType(int value)
		{
			this.value = value;
		}

		public Integer GetValue()
		{
			return this.value;
		}

		public static MTType Value(Integer iValue)
		{
			for (MTType type : MTType.values())
			{
				if (type.GetValue().equals(iValue))
					return type;
			}
			return Default;
		}

	}

	// Fields

	private Integer defineMtid;
	private Integer serviceId;
	private String mt;
	private Integer mttypeId;
	private String mttypeName;
	private String description;
	private Short isActive;
	private Integer orderId;

	// Constructors

	/** default constructor */
	public DefineMt() {
	}

	/** full constructor */
	public DefineMt(Integer serviceId, String mt, Integer mttypeId,
			String mttypeName, String description, Short isActive,
			Integer orderId) {
		this.serviceId = serviceId;
		this.mt = mt;
		this.mttypeId = mttypeId;
		this.mttypeName = mttypeName;
		this.description = description;
		this.isActive = isActive;
		this.orderId = orderId;
	}

	// Property accessors

	public Integer getDefineMtid() {
		return this.defineMtid;
	}

	public void setDefineMtid(Integer defineMtid) {
		this.defineMtid = defineMtid;
	}

	public Integer getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public String getMt() {
		return this.mt;
	}

	public void setMt(String mt) {
		this.mt = mt;
	}

	public Integer getMttypeId() {
		return this.mttypeId;
	}

	public void setMttypeId(Integer mttypeId) {
		this.mttypeId = mttypeId;
	}

	public String getMttypeName() {
		return this.mttypeName;
	}

	public void setMttypeName(String mttypeName) {
		this.mttypeName = mttypeName;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Short getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Short isActive) {
		this.isActive = isActive;
	}

	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}


	private static String getDefaultMT(MTType mMTTypeID) throws Exception
	{
		try
		{
			String ShortCode = "5138";
			String MT = "";

			switch (mMTTypeID)
			{
				case Invalid :
					MT = "Tin nhan sai cu phap, de bien thong tin chi tiet ve dich vu voi long soan TG gui "
							+ ShortCode;
					break;
				default :
					MT = "Hien tai he thong dang ban. Quy khach vui long thao tac lai sau. Chi tiet LH 0435381029 (cuoc goi co dinh).";
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
	 * thì lấy MT mặc định của dịch vụ. Trong trường hợp có nhiều hơn 2 MT cùng
	 * mã. thì lấy random
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
	public static String getMTContent(Vector<DefineMt> mList, MTType mMTType) throws Exception
	{
		try
		{
			if (mList.size() < 1)
				return getDefaultMT(mMTType);

			Vector<DefineMt> mList_Random = new Vector<DefineMt>();

			for (DefineMt item : mList)
			{
				if (item.getMttypeId().equals(mMTType.GetValue()) && item.getMt().length() > 0)
					mList_Random.add(item);
			}

			if (mList_Random.size() < 1)
				return getDefaultMT(mMTType);

			if (mList_Random.size() == 1)
				return mList_Random.get(0).getMt();

			Random mRandom = new Random();
			int Range = mList_Random.size();
			int Rand = mRandom.nextInt(Range);

			return mList_Random.get(Rand).getMt();
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

}