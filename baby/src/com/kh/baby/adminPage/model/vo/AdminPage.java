package com.kh.baby.adminPage.model.vo;

import java.sql.Date;

public class AdminPage {
	private int boardNo;
	private String boardTitle;
	private String boardContent;
	private int readCount;
	private Date createDate;
	private Date modifyDate;
	private String boardStatus;
	private int memberNo;
	private int boardType;
	private String memberName;
	private String categoryName;
	private int reportType;
	private int reportNo;
	private String reportReason;
	
	public AdminPage() {
		// TODO Auto-generated constructor stub
	}

	public AdminPage(int boardNo, String boardTitle, String boardContent, int readCount, Date createDate,
			String memberName, String categoryName, String reportReason) {
		super();
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.readCount = readCount;
		this.createDate = createDate;
		this.memberName = memberName;
		this.categoryName = categoryName;
		this.reportReason = reportReason;
	}

	public AdminPage(String boardTitle, String boardContent) {
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
	}


	public AdminPage(int boardNo, String boardTitle, String boardContent, int readCount, Date modifyDate, int memberNo,
			int boardType) {
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.readCount = readCount;
		this.modifyDate = modifyDate;
		this.memberNo = memberNo;
		this.boardType = boardType;
	}


	public AdminPage(int boardNo, String boardTitle, String boardContent, int readCount, Date createDate,
			Date modifyDate, String memberName, String categoryName) {
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.readCount = readCount;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.memberName = memberName;
		this.categoryName = categoryName;
	}

	public AdminPage(String boardTitle, String boardContent, int boardType) {
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardType = boardType;
	}


	public AdminPage(int boardNo, String categoryName, String boardTitle, String boardContent, String memberName, int readCount, Date createDate) {
		this.boardNo = boardNo;
		this.categoryName = categoryName;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.memberName = memberName;
		this.readCount = readCount;
		this.createDate = createDate;
	}
	
	public AdminPage(int boardNo, String categoryName, String boardTitle, String memberName, int readCount, Date createDate) {
		this.boardNo = boardNo;
		this.categoryName = categoryName;
		this.boardTitle = boardTitle;
		this.memberName = memberName;
		this.readCount = readCount;
		this.createDate = createDate;
	}
	
	public AdminPage(int boardNo, int boardType, String boardTitle, int memberNo, int readCount, Date createDate) {
		this.boardNo = boardNo;
		this.boardType = boardType;
		this.boardTitle = boardTitle;
		this.memberNo = memberNo;
		this.readCount = readCount;
		this.createDate = createDate;
	}
	
	public AdminPage(int boardNo, String boardTitle, String boardContent, int readCount, Date createDate, Date modifyDate,
			String boardStatus, int memberNo, int boardType) {
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.readCount = readCount;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.boardStatus = boardStatus;
		this.memberNo = memberNo;
		this.boardType = boardType;
	}

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	public String getBoardTitle() {
		return boardTitle;
	}

	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}

	public String getBoardContent() {
		return boardContent;
	}

	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getBoardStatus() {
		return boardStatus;
	}

	public void setBoardStatus(String boardStatus) {
		this.boardStatus = boardStatus;
	}

	public int getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}

	public int getBoardType() {
		return boardType;
	}

	public void setBoardType(int boardType) {
		this.boardType = boardType;
	}

	public String getMemberName() {
		return memberName;
	}


	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}


	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getReportType() {
		return reportType;
	}


	public void setReportType(int reportType) {
		this.reportType = reportType;
	}


	public int getReportNo() {
		return reportNo;
	}


	public void setReportNo(int reportNo) {
		this.reportNo = reportNo;
	}


	public String getReportReason() {
		return reportReason;
	}


	public void setReportReason(String reportReason) {
		this.reportReason = reportReason;
	}


	@Override
	public String toString() {
		return "AdminPage [boardNo=" + boardNo + ", boardTitle=" + boardTitle + ", boardContent=" + boardContent
				+ ", readCount=" + readCount + ", createDate=" + createDate + ", modifyDate=" + modifyDate
				+ ", boardStatus=" + boardStatus + ", memberNo=" + memberNo + ", boardType=" + boardType
				+ ", memberName=" + memberName + ", categoryName=" + categoryName + ", reportType=" + reportType
				+ ", reportNo=" + reportNo + ", reportReason=" + reportReason + "]";
	}
	
	
}
