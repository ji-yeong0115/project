package com.kh.baby.adminPage.model.vo;

import java.sql.Date;

public class ReportBoard {
	private int reportNo;
	private int boardNo;
	private String memberName;
	private String categoryName;
	private String content;
	private int boardReadCount;
	private Date boardWriteDate;
	private String reportReason;
	
	public ReportBoard() {
		// TODO Auto-generated constructor stub
	}

	public ReportBoard(int reportNo, int boardNo, String memberName, String categoryName, String content,
			int boardReadCount, Date boardWriteDate, String reportReason) {
		this.reportNo = reportNo;
		this.boardNo = boardNo;
		this.memberName = memberName;
		this.categoryName = categoryName;
		this.content = content;
		this.boardReadCount = boardReadCount;
		this.boardWriteDate = boardWriteDate;
		this.reportReason = reportReason;
	}

	public int getReportNo() {
		return reportNo;
	}

	public void setReportNo(int reportNo) {
		this.reportNo = reportNo;
	}

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getBoardReadCount() {
		return boardReadCount;
	}

	public void setBoardReadCount(int boardReadCount) {
		this.boardReadCount = boardReadCount;
	}

	public Date getBoardWriteDate() {
		return boardWriteDate;
	}

	public void setBoardWriteDate(Date boardWriteDate) {
		this.boardWriteDate = boardWriteDate;
	}

	public String getReportReason() {
		return reportReason;
	}

	public void setReportReason(String reportReason) {
		this.reportReason = reportReason;
	}

	@Override
	public String toString() {
		return "ReportBoard [reportNo=" + reportNo + ", boardNo=" + boardNo + ", memberName=" + memberName
				+ ", categoryName=" + categoryName + ", content=" + content + ", boardReadCount=" + boardReadCount
				+ ", boardWriteDate=" + boardWriteDate + ", reportReason=" + reportReason + "]";
	}

	
		
}
