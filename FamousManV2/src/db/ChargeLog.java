package db;

import java.sql.Timestamp;

/**
 * ChargeLog entity. @author MyEclipse Persistence Tools
 */

public class ChargeLog extends DAOBase implements java.io.Serializable {



	public enum ChargeType
	{
		Nothing(0), Register(1), Renew(2), BuyContent(3), Restore(4), Deregister(5) ;

		private int value;

		private ChargeType(int value)
		{
			this.value = value;
		}

		public Short GetValue()
		{
			return ((Integer) this.value).shortValue();
		}

		public static ChargeType FromValue(short iValue)
		{
			for (ChargeType type : ChargeType.values())
			{
				if (type.GetValue() == iValue)
					return type;
			}
			return Nothing;
		}
	}

	public enum Status
	{
		/*
		 * 0: Đăng ký sub 1: Hủy sub 2: Tạm dừng 3: Đăng ký lại
		 */

		Nothing(-1), ChargeSuccess(0), ChargeFail(1), ;

		private int value;

		private Status(int value)
		{
			this.value = value;
		}

		public Short GetValue()
		{
			return ((Integer) this.value).shortValue();
		}

		public static Status FromValue(short iValue)
		{
			for (Status type : Status.values())
			{
				if (type.GetValue() == iValue)
					return type;
			}
			return Nothing;
		}
	}
	
	// Fields

	private ChargeLogId id;
	private Timestamp chargeDate;
	private Float pirce;
	private Short chargeTypeId;
	private Short statusId;
	private Short channelId;
	private Timestamp logDate;
	private Integer partnerId;
	private Integer noteTypeId;

	// Constructors

	/** default constructor */
	public ChargeLog() {
	}

	/** minimal constructor */
	public ChargeLog(ChargeLogId id) {
		this.id = id;
	}

	/** full constructor */
	public ChargeLog(ChargeLogId id, Timestamp chargeDate, Float pirce,
			Short chargeTypeId, Short statusId, Short channelId,
			Timestamp logDate, Integer partnerId, Integer noteTypeId) {
		this.id = id;
		this.chargeDate = chargeDate;
		this.pirce = pirce;
		this.chargeTypeId = chargeTypeId;
		this.statusId = statusId;
		this.channelId = channelId;
		this.logDate = logDate;
		this.partnerId = partnerId;
		this.noteTypeId = noteTypeId;
	}

	// Property accessors

	public ChargeLogId getId() {
		return this.id;
	}

	public void setId(ChargeLogId id) {
		this.id = id;
	}

	public Timestamp getChargeDate() {
		return this.chargeDate;
	}

	public void setChargeDate(Timestamp chargeDate) {
		this.chargeDate = chargeDate;
	}

	public Float getPirce() {
		return this.pirce;
	}

	public void setPirce(Float pirce) {
		this.pirce = pirce;
	}

	public Short getChargeTypeId() {
		return this.chargeTypeId;
	}

	public void setChargeTypeId(Short chargeTypeId) {
		this.chargeTypeId = chargeTypeId;
	}

	public Short getStatusId() {
		return this.statusId;
	}

	public void setStatusId(Short statusId) {
		this.statusId = statusId;
	}

	public Short getChannelId() {
		return this.channelId;
	}

	public void setChannelId(Short channelId) {
		this.channelId = channelId;
	}

	public Timestamp getLogDate() {
		return this.logDate;
	}

	public void setLogDate(Timestamp logDate) {
		this.logDate = logDate;
	}

	public Integer getPartnerId() {
		return this.partnerId;
	}

	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}

	public Integer getNoteTypeId() {
		return this.noteTypeId;
	}

	public void setNoteTypeId(Integer noteTypeId) {
		this.noteTypeId = noteTypeId;
	}

}