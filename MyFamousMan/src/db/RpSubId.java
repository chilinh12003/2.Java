package db;

import java.sql.Timestamp;

/**
 * RpSubId entity. @author MyEclipse Persistence Tools
 */

public class RpSubId extends DAOBase implements java.io.Serializable
{

	// Fields

	private Timestamp reportDay;
	private Integer partnerId;
	private Float subTotal;
	private Float subFail;
	private Float subActive;
	private Float subNew;
	private Float subSms;
	private Float subWap;
	private Float subOther;
	private Float subNewPartner;
	private Float subTotalPartner;
	private Float unsubTotal;
	private Float unsubFail;
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
	private Float renew5000;
	private Float renew3000;
	private Float renew1000;
	private Float saleReg;
	private Float saleRenew;
	private Float rateSaleDay;
	private String note;

	// Constructors

	/** default constructor */
	public RpSubId()
	{
	}

	/** minimal constructor */
	public RpSubId(Timestamp reportDay, Integer partnerId, Float subTotal, Float subFail, Float subActive,
			Float subNew, Float subSms, Float subWap, Float subOther, Float subNewPartner, Float subTotalPartner,
			Float unsubTotal, Float unsubFail, Float unsubNew, Float unsubSelf, Float unsubExtend, Float unsubOther,
			Float unsubNewPartner, Float unsubTotalPartner, Float renewTotal, Float renewSuccess, Float renewFail,
			Float renewRate, Float renew5000, Float renew3000, Float renew1000, Float saleReg, Float saleRenew,
			Float rateSaleDay)
	{
		this.reportDay = reportDay;
		this.partnerId = partnerId;
		this.subTotal = subTotal;
		this.subFail = subFail;
		this.subActive = subActive;
		this.subNew = subNew;
		this.subSms = subSms;
		this.subWap = subWap;
		this.subOther = subOther;
		this.subNewPartner = subNewPartner;
		this.subTotalPartner = subTotalPartner;
		this.unsubTotal = unsubTotal;
		this.unsubFail = unsubFail;
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
		this.renew5000 = renew5000;
		this.renew3000 = renew3000;
		this.renew1000 = renew1000;
		this.saleReg = saleReg;
		this.saleRenew = saleRenew;
		this.rateSaleDay = rateSaleDay;
	}

	/** full constructor */
	public RpSubId(Timestamp reportDay, Integer partnerId, Float subTotal, Float subFail, Float subActive,
			Float subNew, Float subSms, Float subWap, Float subOther, Float subNewPartner, Float subTotalPartner,
			Float unsubTotal, Float unsubFail, Float unsubNew, Float unsubSelf, Float unsubExtend, Float unsubOther,
			Float unsubNewPartner, Float unsubTotalPartner, Float renewTotal, Float renewSuccess, Float renewFail,
			Float renewRate, Float renew5000, Float renew3000, Float renew1000, Float saleReg, Float saleRenew,
			Float rateSaleDay, String note)
	{
		this.reportDay = reportDay;
		this.partnerId = partnerId;
		this.subTotal = subTotal;
		this.subFail = subFail;
		this.subActive = subActive;
		this.subNew = subNew;
		this.subSms = subSms;
		this.subWap = subWap;
		this.subOther = subOther;
		this.subNewPartner = subNewPartner;
		this.subTotalPartner = subTotalPartner;
		this.unsubTotal = unsubTotal;
		this.unsubFail = unsubFail;
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
		this.renew5000 = renew5000;
		this.renew3000 = renew3000;
		this.renew1000 = renew1000;
		this.saleReg = saleReg;
		this.saleRenew = saleRenew;
		this.rateSaleDay = rateSaleDay;
		this.note = note;
	}

	// Property accessors

	public Timestamp getReportDay()
	{
		return this.reportDay;
	}

	public void setReportDay(Timestamp reportDay)
	{
		this.reportDay = reportDay;
	}

	public Integer getPartnerId()
	{
		return this.partnerId;
	}

	public void setPartnerId(Integer partnerId)
	{
		this.partnerId = partnerId;
	}

	public Float getSubTotal()
	{
		return this.subTotal;
	}

	public void setSubTotal(Float subTotal)
	{
		this.subTotal = subTotal;
	}

	public Float getSubFail()
	{
		return this.subFail;
	}

	public void setSubFail(Float subFail)
	{
		this.subFail = subFail;
	}

	public Float getSubActive()
	{
		return this.subActive;
	}

	public void setSubActive(Float subActive)
	{
		this.subActive = subActive;
	}

	public Float getSubNew()
	{
		return this.subNew;
	}

	public void setSubNew(Float subNew)
	{
		this.subNew = subNew;
	}

	public Float getSubSms()
	{
		return this.subSms;
	}

	public void setSubSms(Float subSms)
	{
		this.subSms = subSms;
	}

	public Float getSubWap()
	{
		return this.subWap;
	}

	public void setSubWap(Float subWap)
	{
		this.subWap = subWap;
	}

	public Float getSubOther()
	{
		return this.subOther;
	}

	public void setSubOther(Float subOther)
	{
		this.subOther = subOther;
	}

	public Float getSubNewPartner()
	{
		return this.subNewPartner;
	}

	public void setSubNewPartner(Float subNewPartner)
	{
		this.subNewPartner = subNewPartner;
	}

	public Float getSubTotalPartner()
	{
		return this.subTotalPartner;
	}

	public void setSubTotalPartner(Float subTotalPartner)
	{
		this.subTotalPartner = subTotalPartner;
	}

	public Float getUnsubTotal()
	{
		return this.unsubTotal;
	}

	public void setUnsubTotal(Float unsubTotal)
	{
		this.unsubTotal = unsubTotal;
	}

	public Float getUnsubFail()
	{
		return this.unsubFail;
	}

	public void setUnsubFail(Float unsubFail)
	{
		this.unsubFail = unsubFail;
	}

	public Float getUnsubNew()
	{
		return this.unsubNew;
	}

	public void setUnsubNew(Float unsubNew)
	{
		this.unsubNew = unsubNew;
	}

	public Float getUnsubSelf()
	{
		return this.unsubSelf;
	}

	public void setUnsubSelf(Float unsubSelf)
	{
		this.unsubSelf = unsubSelf;
	}

	public Float getUnsubExtend()
	{
		return this.unsubExtend;
	}

	public void setUnsubExtend(Float unsubExtend)
	{
		this.unsubExtend = unsubExtend;
	}

	public Float getUnsubOther()
	{
		return this.unsubOther;
	}

	public void setUnsubOther(Float unsubOther)
	{
		this.unsubOther = unsubOther;
	}

	public Float getUnsubNewPartner()
	{
		return this.unsubNewPartner;
	}

	public void setUnsubNewPartner(Float unsubNewPartner)
	{
		this.unsubNewPartner = unsubNewPartner;
	}

	public Float getUnsubTotalPartner()
	{
		return this.unsubTotalPartner;
	}

	public void setUnsubTotalPartner(Float unsubTotalPartner)
	{
		this.unsubTotalPartner = unsubTotalPartner;
	}

	public Float getRenewTotal()
	{
		return this.renewTotal;
	}

	public void setRenewTotal(Float renewTotal)
	{
		this.renewTotal = renewTotal;
	}

	public Float getRenewSuccess()
	{
		return this.renewSuccess;
	}

	public void setRenewSuccess(Float renewSuccess)
	{
		this.renewSuccess = renewSuccess;
	}

	public Float getRenewFail()
	{
		return this.renewFail;
	}

	public void setRenewFail(Float renewFail)
	{
		this.renewFail = renewFail;
	}

	public Float getRenewRate()
	{
		return this.renewRate;
	}

	public void setRenewRate(Float renewRate)
	{
		this.renewRate = renewRate;
	}

	public Float getRenew5000()
	{
		return this.renew5000;
	}

	public void setRenew5000(Float renew5000)
	{
		this.renew5000 = renew5000;
	}

	public Float getRenew3000()
	{
		return this.renew3000;
	}

	public void setRenew3000(Float renew3000)
	{
		this.renew3000 = renew3000;
	}

	public Float getRenew1000()
	{
		return this.renew1000;
	}

	public void setRenew1000(Float renew1000)
	{
		this.renew1000 = renew1000;
	}

	public Float getSaleReg()
	{
		return this.saleReg;
	}

	public void setSaleReg(Float saleReg)
	{
		this.saleReg = saleReg;
	}

	public Float getSaleRenew()
	{
		return this.saleRenew;
	}

	public void setSaleRenew(Float saleRenew)
	{
		this.saleRenew = saleRenew;
	}

	public Float getRateSaleDay()
	{
		return this.rateSaleDay;
	}

	public void setRateSaleDay(Float rateSaleDay)
	{
		this.rateSaleDay = rateSaleDay;
	}

	public String getNote()
	{
		return this.note;
	}

	public void setNote(String note)
	{
		this.note = note;
	}

	public boolean equals(Object other)
	{
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RpSubId))
			return false;
		RpSubId castOther = (RpSubId) other;

		return ((this.getReportDay() == castOther.getReportDay()) || (this.getReportDay() != null
				&& castOther.getReportDay() != null && this.getReportDay().equals(castOther.getReportDay())))
				&& ((this.getPartnerId() == castOther.getPartnerId()) || (this.getPartnerId() != null
						&& castOther.getPartnerId() != null && this.getPartnerId().equals(castOther.getPartnerId())))
				&& ((this.getSubTotal() == castOther.getSubTotal()) || (this.getSubTotal() != null
						&& castOther.getSubTotal() != null && this.getSubTotal().equals(castOther.getSubTotal())))
				&& ((this.getSubFail() == castOther.getSubFail()) || (this.getSubFail() != null
						&& castOther.getSubFail() != null && this.getSubFail().equals(castOther.getSubFail())))
				&& ((this.getSubActive() == castOther.getSubActive()) || (this.getSubActive() != null
						&& castOther.getSubActive() != null && this.getSubActive().equals(castOther.getSubActive())))
				&& ((this.getSubNew() == castOther.getSubNew()) || (this.getSubNew() != null
						&& castOther.getSubNew() != null && this.getSubNew().equals(castOther.getSubNew())))
				&& ((this.getSubSms() == castOther.getSubSms()) || (this.getSubSms() != null
						&& castOther.getSubSms() != null && this.getSubSms().equals(castOther.getSubSms())))
				&& ((this.getSubWap() == castOther.getSubWap()) || (this.getSubWap() != null
						&& castOther.getSubWap() != null && this.getSubWap().equals(castOther.getSubWap())))
				&& ((this.getSubOther() == castOther.getSubOther()) || (this.getSubOther() != null
						&& castOther.getSubOther() != null && this.getSubOther().equals(castOther.getSubOther())))
				&& ((this.getSubNewPartner() == castOther.getSubNewPartner()) || (this.getSubNewPartner() != null
						&& castOther.getSubNewPartner() != null && this.getSubNewPartner().equals(
						castOther.getSubNewPartner())))
				&& ((this.getSubTotalPartner() == castOther.getSubTotalPartner()) || (this.getSubTotalPartner() != null
						&& castOther.getSubTotalPartner() != null && this.getSubTotalPartner().equals(
						castOther.getSubTotalPartner())))
				&& ((this.getUnsubTotal() == castOther.getUnsubTotal()) || (this.getUnsubTotal() != null
						&& castOther.getUnsubTotal() != null && this.getUnsubTotal().equals(castOther.getUnsubTotal())))
				&& ((this.getUnsubFail() == castOther.getUnsubFail()) || (this.getUnsubFail() != null
						&& castOther.getUnsubFail() != null && this.getUnsubFail().equals(castOther.getUnsubFail())))
				&& ((this.getUnsubNew() == castOther.getUnsubNew()) || (this.getUnsubNew() != null
						&& castOther.getUnsubNew() != null && this.getUnsubNew().equals(castOther.getUnsubNew())))
				&& ((this.getUnsubSelf() == castOther.getUnsubSelf()) || (this.getUnsubSelf() != null
						&& castOther.getUnsubSelf() != null && this.getUnsubSelf().equals(castOther.getUnsubSelf())))
				&& ((this.getUnsubExtend() == castOther.getUnsubExtend()) || (this.getUnsubExtend() != null
						&& castOther.getUnsubExtend() != null && this.getUnsubExtend().equals(
						castOther.getUnsubExtend())))
				&& ((this.getUnsubOther() == castOther.getUnsubOther()) || (this.getUnsubOther() != null
						&& castOther.getUnsubOther() != null && this.getUnsubOther().equals(castOther.getUnsubOther())))
				&& ((this.getUnsubNewPartner() == castOther.getUnsubNewPartner()) || (this.getUnsubNewPartner() != null
						&& castOther.getUnsubNewPartner() != null && this.getUnsubNewPartner().equals(
						castOther.getUnsubNewPartner())))
				&& ((this.getUnsubTotalPartner() == castOther.getUnsubTotalPartner()) || (this.getUnsubTotalPartner() != null
						&& castOther.getUnsubTotalPartner() != null && this.getUnsubTotalPartner().equals(
						castOther.getUnsubTotalPartner())))
				&& ((this.getRenewTotal() == castOther.getRenewTotal()) || (this.getRenewTotal() != null
						&& castOther.getRenewTotal() != null && this.getRenewTotal().equals(castOther.getRenewTotal())))
				&& ((this.getRenewSuccess() == castOther.getRenewSuccess()) || (this.getRenewSuccess() != null
						&& castOther.getRenewSuccess() != null && this.getRenewSuccess().equals(
						castOther.getRenewSuccess())))
				&& ((this.getRenewFail() == castOther.getRenewFail()) || (this.getRenewFail() != null
						&& castOther.getRenewFail() != null && this.getRenewFail().equals(castOther.getRenewFail())))
				&& ((this.getRenewRate() == castOther.getRenewRate()) || (this.getRenewRate() != null
						&& castOther.getRenewRate() != null && this.getRenewRate().equals(castOther.getRenewRate())))
				&& ((this.getRenew5000() == castOther.getRenew5000()) || (this.getRenew5000() != null
						&& castOther.getRenew5000() != null && this.getRenew5000().equals(castOther.getRenew5000())))
				&& ((this.getRenew3000() == castOther.getRenew3000()) || (this.getRenew3000() != null
						&& castOther.getRenew3000() != null && this.getRenew3000().equals(castOther.getRenew3000())))
				&& ((this.getRenew1000() == castOther.getRenew1000()) || (this.getRenew1000() != null
						&& castOther.getRenew1000() != null && this.getRenew1000().equals(castOther.getRenew1000())))
				&& ((this.getSaleReg() == castOther.getSaleReg()) || (this.getSaleReg() != null
						&& castOther.getSaleReg() != null && this.getSaleReg().equals(castOther.getSaleReg())))
				&& ((this.getSaleRenew() == castOther.getSaleRenew()) || (this.getSaleRenew() != null
						&& castOther.getSaleRenew() != null && this.getSaleRenew().equals(castOther.getSaleRenew())))
				&& ((this.getRateSaleDay() == castOther.getRateSaleDay()) || (this.getRateSaleDay() != null
						&& castOther.getRateSaleDay() != null && this.getRateSaleDay().equals(
						castOther.getRateSaleDay())))
				&& ((this.getNote() == castOther.getNote()) || (this.getNote() != null && castOther.getNote() != null && this
						.getNote().equals(castOther.getNote())));
	}

	public int hashCode()
	{
		int result = 17;

		result = 37 * result + (getReportDay() == null ? 0 : this.getReportDay().hashCode());
		result = 37 * result + (getPartnerId() == null ? 0 : this.getPartnerId().hashCode());
		result = 37 * result + (getSubTotal() == null ? 0 : this.getSubTotal().hashCode());
		result = 37 * result + (getSubFail() == null ? 0 : this.getSubFail().hashCode());
		result = 37 * result + (getSubActive() == null ? 0 : this.getSubActive().hashCode());
		result = 37 * result + (getSubNew() == null ? 0 : this.getSubNew().hashCode());
		result = 37 * result + (getSubSms() == null ? 0 : this.getSubSms().hashCode());
		result = 37 * result + (getSubWap() == null ? 0 : this.getSubWap().hashCode());
		result = 37 * result + (getSubOther() == null ? 0 : this.getSubOther().hashCode());
		result = 37 * result + (getSubNewPartner() == null ? 0 : this.getSubNewPartner().hashCode());
		result = 37 * result + (getSubTotalPartner() == null ? 0 : this.getSubTotalPartner().hashCode());
		result = 37 * result + (getUnsubTotal() == null ? 0 : this.getUnsubTotal().hashCode());
		result = 37 * result + (getUnsubFail() == null ? 0 : this.getUnsubFail().hashCode());
		result = 37 * result + (getUnsubNew() == null ? 0 : this.getUnsubNew().hashCode());
		result = 37 * result + (getUnsubSelf() == null ? 0 : this.getUnsubSelf().hashCode());
		result = 37 * result + (getUnsubExtend() == null ? 0 : this.getUnsubExtend().hashCode());
		result = 37 * result + (getUnsubOther() == null ? 0 : this.getUnsubOther().hashCode());
		result = 37 * result + (getUnsubNewPartner() == null ? 0 : this.getUnsubNewPartner().hashCode());
		result = 37 * result + (getUnsubTotalPartner() == null ? 0 : this.getUnsubTotalPartner().hashCode());
		result = 37 * result + (getRenewTotal() == null ? 0 : this.getRenewTotal().hashCode());
		result = 37 * result + (getRenewSuccess() == null ? 0 : this.getRenewSuccess().hashCode());
		result = 37 * result + (getRenewFail() == null ? 0 : this.getRenewFail().hashCode());
		result = 37 * result + (getRenewRate() == null ? 0 : this.getRenewRate().hashCode());
		result = 37 * result + (getRenew5000() == null ? 0 : this.getRenew5000().hashCode());
		result = 37 * result + (getRenew3000() == null ? 0 : this.getRenew3000().hashCode());
		result = 37 * result + (getRenew1000() == null ? 0 : this.getRenew1000().hashCode());
		result = 37 * result + (getSaleReg() == null ? 0 : this.getSaleReg().hashCode());
		result = 37 * result + (getSaleRenew() == null ? 0 : this.getSaleRenew().hashCode());
		result = 37 * result + (getRateSaleDay() == null ? 0 : this.getRateSaleDay().hashCode());
		result = 37 * result + (getNote() == null ? 0 : this.getNote().hashCode());
		return result;
	}

}