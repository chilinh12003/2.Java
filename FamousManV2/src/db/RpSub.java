package db;

/**
 * RpSub entity. @author MyEclipse Persistence Tools
 */

public class RpSub extends DAOBase implements java.io.Serializable {

	// Fields

	private RpSubId id;
	private Integer partnerId;
	private Float subTotal;
	private Float subActive;
	private Float subNew;
	private Float subSms;
	private Float subWap;
	private Float subOther;
	private Float subNewPartner;
	private Float subTotalPartner;
	private Float unsubTotal;
	private Float unsubNew;
	private Float unsubSelf;
	private Float unsubExtend;
	private Float unsubOther;
	private Float unsubNewPartner;
	private Float unsubTotalPartner;
	private Float renewTotal;
	private Float renewSuccess;
	private Float renewFail;
	private Float renewRate;
	private Float saleReg;
	private Float saleBuyContent;
	private Float saleRenew;
	private Float rateSaleDay;
	private String note;

	// Constructors

	/** default constructor */
	public RpSub() {
	}

	/** minimal constructor */
	public RpSub(RpSubId id, Integer partnerId, Float subTotal,
			Float subActive, Float subNew, Float subSms, Float subWap,
			Float subOther, Float subNewPartner, Float subTotalPartner,
			Float unsubTotal, Float unsubNew, Float unsubSelf,
			Float unsubExtend, Float unsubOther, Float unsubNewPartner,
			Float unsubTotalPartner, Float renewTotal, Float renewSuccess,
			Float renewFail, Float renewRate, Float saleReg,
			Float saleBuyContent, Float saleRenew, Float rateSaleDay) {
		this.id = id;
		this.partnerId = partnerId;
		this.subTotal = subTotal;
		this.subActive = subActive;
		this.subNew = subNew;
		this.subSms = subSms;
		this.subWap = subWap;
		this.subOther = subOther;
		this.subNewPartner = subNewPartner;
		this.subTotalPartner = subTotalPartner;
		this.unsubTotal = unsubTotal;
		this.unsubNew = unsubNew;
		this.unsubSelf = unsubSelf;
		this.unsubExtend = unsubExtend;
		this.unsubOther = unsubOther;
		this.unsubNewPartner = unsubNewPartner;
		this.unsubTotalPartner = unsubTotalPartner;
		this.renewTotal = renewTotal;
		this.renewSuccess = renewSuccess;
		this.renewFail = renewFail;
		this.renewRate = renewRate;
		this.saleReg = saleReg;
		this.saleBuyContent = saleBuyContent;
		this.saleRenew = saleRenew;
		this.rateSaleDay = rateSaleDay;
	}

	/** full constructor */
	public RpSub(RpSubId id, Integer partnerId, Float subTotal,
			Float subActive, Float subNew, Float subSms, Float subWap,
			Float subOther, Float subNewPartner, Float subTotalPartner,
			Float unsubTotal, Float unsubNew, Float unsubSelf,
			Float unsubExtend, Float unsubOther, Float unsubNewPartner,
			Float unsubTotalPartner, Float renewTotal, Float renewSuccess,
			Float renewFail, Float renewRate, Float saleReg,
			Float saleBuyContent, Float saleRenew, Float rateSaleDay,
			String note) {
		this.id = id;
		this.partnerId = partnerId;
		this.subTotal = subTotal;
		this.subActive = subActive;
		this.subNew = subNew;
		this.subSms = subSms;
		this.subWap = subWap;
		this.subOther = subOther;
		this.subNewPartner = subNewPartner;
		this.subTotalPartner = subTotalPartner;
		this.unsubTotal = unsubTotal;
		this.unsubNew = unsubNew;
		this.unsubSelf = unsubSelf;
		this.unsubExtend = unsubExtend;
		this.unsubOther = unsubOther;
		this.unsubNewPartner = unsubNewPartner;
		this.unsubTotalPartner = unsubTotalPartner;
		this.renewTotal = renewTotal;
		this.renewSuccess = renewSuccess;
		this.renewFail = renewFail;
		this.renewRate = renewRate;
		this.saleReg = saleReg;
		this.saleBuyContent = saleBuyContent;
		this.saleRenew = saleRenew;
		this.rateSaleDay = rateSaleDay;
		this.note = note;
	}

	// Property accessors

	public RpSubId getId() {
		return this.id;
	}

	public void setId(RpSubId id) {
		this.id = id;
	}

	public Integer getPartnerId() {
		return this.partnerId;
	}

	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}

	public Float getSubTotal() {
		return this.subTotal;
	}

	public void setSubTotal(Float subTotal) {
		this.subTotal = subTotal;
	}

	public Float getSubActive() {
		return this.subActive;
	}

	public void setSubActive(Float subActive) {
		this.subActive = subActive;
	}

	public Float getSubNew() {
		return this.subNew;
	}

	public void setSubNew(Float subNew) {
		this.subNew = subNew;
	}

	public Float getSubSms() {
		return this.subSms;
	}

	public void setSubSms(Float subSms) {
		this.subSms = subSms;
	}

	public Float getSubWap() {
		return this.subWap;
	}

	public void setSubWap(Float subWap) {
		this.subWap = subWap;
	}

	public Float getSubOther() {
		return this.subOther;
	}

	public void setSubOther(Float subOther) {
		this.subOther = subOther;
	}

	public Float getSubNewPartner() {
		return this.subNewPartner;
	}

	public void setSubNewPartner(Float subNewPartner) {
		this.subNewPartner = subNewPartner;
	}

	public Float getSubTotalPartner() {
		return this.subTotalPartner;
	}

	public void setSubTotalPartner(Float subTotalPartner) {
		this.subTotalPartner = subTotalPartner;
	}

	public Float getUnsubTotal() {
		return this.unsubTotal;
	}

	public void setUnsubTotal(Float unsubTotal) {
		this.unsubTotal = unsubTotal;
	}

	public Float getUnsubNew() {
		return this.unsubNew;
	}

	public void setUnsubNew(Float unsubNew) {
		this.unsubNew = unsubNew;
	}

	public Float getUnsubSelf() {
		return this.unsubSelf;
	}

	public void setUnsubSelf(Float unsubSelf) {
		this.unsubSelf = unsubSelf;
	}

	public Float getUnsubExtend() {
		return this.unsubExtend;
	}

	public void setUnsubExtend(Float unsubExtend) {
		this.unsubExtend = unsubExtend;
	}

	public Float getUnsubOther() {
		return this.unsubOther;
	}

	public void setUnsubOther(Float unsubOther) {
		this.unsubOther = unsubOther;
	}

	public Float getUnsubNewPartner() {
		return this.unsubNewPartner;
	}

	public void setUnsubNewPartner(Float unsubNewPartner) {
		this.unsubNewPartner = unsubNewPartner;
	}

	public Float getUnsubTotalPartner() {
		return this.unsubTotalPartner;
	}

	public void setUnsubTotalPartner(Float unsubTotalPartner) {
		this.unsubTotalPartner = unsubTotalPartner;
	}

	public Float getRenewTotal() {
		return this.renewTotal;
	}

	public void setRenewTotal(Float renewTotal) {
		this.renewTotal = renewTotal;
	}

	public Float getRenewSuccess() {
		return this.renewSuccess;
	}

	public void setRenewSuccess(Float renewSuccess) {
		this.renewSuccess = renewSuccess;
	}

	public Float getRenewFail() {
		return this.renewFail;
	}

	public void setRenewFail(Float renewFail) {
		this.renewFail = renewFail;
	}

	public Float getRenewRate() {
		return this.renewRate;
	}

	public void setRenewRate(Float renewRate) {
		this.renewRate = renewRate;
	}

	public Float getSaleReg() {
		return this.saleReg;
	}

	public void setSaleReg(Float saleReg) {
		this.saleReg = saleReg;
	}

	public Float getSaleBuyContent() {
		return this.saleBuyContent;
	}

	public void setSaleBuyContent(Float saleBuyContent) {
		this.saleBuyContent = saleBuyContent;
	}

	public Float getSaleRenew() {
		return this.saleRenew;
	}

	public void setSaleRenew(Float saleRenew) {
		this.saleRenew = saleRenew;
	}

	public Float getRateSaleDay() {
		return this.rateSaleDay;
	}

	public void setRateSaleDay(Float rateSaleDay) {
		this.rateSaleDay = rateSaleDay;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}