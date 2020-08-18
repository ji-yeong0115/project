package com.kh.baby.mypage.model.vo;

import java.sql.Date;

public class Diary {
	private int diaryNo;
	private String diaryContent;
	private Date diaryCreateDate;
	private Date diaryModifyDate;
	private String diaryStatus;
	private int memberNo;
	
	public Diary() {}

	public Diary(String diaryContent, int memberNo) {
		super();
		this.diaryContent = diaryContent;
		this.memberNo = memberNo;
	}

	public Diary(int diaryNo, String diaryContent) {
		super();
		this.diaryNo = diaryNo;
		this.diaryContent = diaryContent;
	}

	public Diary(int diaryNo, String diaryContent, Date diaryCreateDate, Date diaryModifyDate) {
		super();
		this.diaryNo = diaryNo;
		this.diaryContent = diaryContent;
		this.diaryCreateDate = diaryCreateDate;
		this.diaryModifyDate = diaryModifyDate;
	}

	public Diary(int diaryNo, String diaryContent, Date diaryCreateDate, Date diaryModifyDate, String diaryStatus,
			int memberNo) {
		super();
		this.diaryNo = diaryNo;
		this.diaryContent = diaryContent;
		this.diaryCreateDate = diaryCreateDate;
		this.diaryModifyDate = diaryModifyDate;
		this.diaryStatus = diaryStatus;
		this.memberNo = memberNo;
	}

	public int getDiaryNo() {
		return diaryNo;
	}

	public void setDiaryNo(int diaryNo) {
		this.diaryNo = diaryNo;
	}

	public String getDiaryContent() {
		return diaryContent;
	}

	public void setDiaryContent(String diaryContent) {
		this.diaryContent = diaryContent;
	}

	public Date getDiaryCreateDate() {
		return diaryCreateDate;
	}

	public void setDiaryCreateDate(Date diaryCreateDate) {
		this.diaryCreateDate = diaryCreateDate;
	}

	public Date getDiaryModifyDate() {
		return diaryModifyDate;
	}

	public void setDiaryModifyDate(Date diaryModifyDate) {
		this.diaryModifyDate = diaryModifyDate;
	}

	public String getDiaryStatus() {
		return diaryStatus;
	}

	public void setDiaryStatus(String diaryStatus) {
		this.diaryStatus = diaryStatus;
	}

	public int getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}

	@Override
	public String toString() {
		return "Diary [diaryNo=" + diaryNo + ", diaryContent=" + diaryContent + ", diaryCreateDate=" + diaryCreateDate
				+ ", diaryModifyDate=" + diaryModifyDate + ", diaryStatus=" + diaryStatus + ", memberNo=" + memberNo
				+ "]";
	}

}
