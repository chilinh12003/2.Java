package db;

import java.sql.Timestamp;

/**
 * Keyword entity. @author MyEclipse Persistence Tools
 */

public class Keyword extends DAOBase implements java.io.Serializable {

	// Fields

	private Integer keywordId;
	private String shortCode;
	private String className;
	private String keyword;
	private Timestamp beginDate;
	private Timestamp endDate;
	private String note;
	private Short statusId;

	// Constructors

	/** default constructor */
	public Keyword() {
	}

	/** full constructor */
	public Keyword(String shortCode, String className, String keyword,
			Timestamp beginDate, Timestamp endDate, String note, Short statusId) {
		this.shortCode = shortCode;
		this.className = className;
		this.keyword = keyword;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.note = note;
		this.statusId = statusId;
	}

	// Property accessors

	public Integer getKeywordId() {
		return this.keywordId;
	}

	public void setKeywordId(Integer keywordId) {
		this.keywordId = keywordId;
	}

	public String getShortCode() {
		return this.shortCode;
	}

	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Timestamp getBeginDate() {
		return this.beginDate;
	}

	public void setBeginDate(Timestamp beginDate) {
		this.beginDate = beginDate;
	}

	public Timestamp getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Short getStatusId() {
		return this.statusId;
	}

	public void setStatusId(Short statusId) {
		this.statusId = statusId;
	}

}