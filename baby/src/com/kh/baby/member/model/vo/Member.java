package com.kh.baby.member.model.vo;

import java.sql.Date;

public class Member {
	private int memberNo;
	private String memberId;
	private String memberPwd;
	private String memberName;
	private String memberNickname;
	private String memberEmail;
	private String memberAddress;
	private Date memberEnrollDate;
	private String memberStatus;
	private String memberGrade;
	
	public Member() {}

	
	
	
	
	public Member(String memberAddress) {
		super();
		
		this.memberAddress = memberAddress;
	}





	public Member(int memberNo) {
		super();
		this.memberNo = memberNo;
	}



	public Member(String memberId, String memberPwd) {
		super();
		this.memberId = memberId;
		this.memberPwd = memberPwd;
	}

	
	public Member(String memberId, String memberNickname, String memberAddress) {
		super();
		this.memberId = memberId;
		this.memberNickname = memberNickname;
		this.memberAddress = memberAddress;
	}

	public Member(String memberId, String memberPwd, String memberName, String memberNickname, String memberEmail,
			String memberAddress, String memberGrade) {
		super();
		this.memberId = memberId;
		this.memberPwd = memberPwd;
		this.memberName = memberName;
		this.memberNickname = memberNickname;
		this.memberEmail = memberEmail;
		this.memberAddress = memberAddress;
		this.memberGrade = memberGrade;
	}
	
	public Member(int memberNo, String memberId, String memberName, String memberNickname, String memberEmail,
			String memberAddress, String memberGrade) {
		super();
		this.memberNo = memberNo;
		this.memberId = memberId;
		this.memberName = memberName;
		this.memberNickname = memberNickname;
		this.memberEmail = memberEmail;
		this.memberAddress = memberAddress;
		this.memberGrade = memberGrade;
	}

	public Member(int memberNo, String memberId, String memberName, String memberNickname, String memberEmail,
			String memberAddress, Date memberEnrollDate, String memberStatus, String memberGrade) {
		super();
		this.memberNo = memberNo;
		this.memberId = memberId;
		this.memberName = memberName;
		this.memberNickname = memberNickname;
		this.memberEmail = memberEmail;
		this.memberAddress = memberAddress;
		this.memberEnrollDate = memberEnrollDate;
		this.memberStatus = memberStatus;
		this.memberGrade = memberGrade;
	}

	public Member(int memberNo, String memberId, String memberPwd, String memberName, String memberNickname,
			String memberEmail, String memberAddress, Date memberEnrollDate, String memberStatus, String memberGrade) {
		super();
		this.memberNo = memberNo;
		this.memberId = memberId;
		this.memberPwd = memberPwd;
		this.memberName = memberName;
		this.memberNickname = memberNickname;
		this.memberEmail = memberEmail;
		this.memberAddress = memberAddress;
		this.memberEnrollDate = memberEnrollDate;
		this.memberStatus = memberStatus;
		this.memberGrade = memberGrade;
	}

	public int getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberPwd() {
		return memberPwd;
	}

	public void setMemberPwd(String memberPwd) {
		this.memberPwd = memberPwd;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberNickname() {
		return memberNickname;
	}

	public void setMemberNickname(String memberNickname) {
		this.memberNickname = memberNickname;
	}

	public String getMemberEmail() {
		return memberEmail;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	public String getMemberAddress() {
		return memberAddress;
	}

	public void setMemberAddress(String memberAddress) {
		this.memberAddress = memberAddress;
	}

	public Date getMemberEnrollDate() {
		return memberEnrollDate;
	}

	public void setMemberEnrollDate(Date memberEnrollDate) {
		this.memberEnrollDate = memberEnrollDate;
	}

	public String getMemberStatus() {
		return memberStatus;
	}

	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
	}

	public String getMemberGrade() {
		return memberGrade;
	}

	public void setMemberGrade(String memberGrade) {
		this.memberGrade = memberGrade;
	}

	@Override
	public String toString() {
		return "Member [memberNo=" + memberNo + ", memberId=" + memberId + ", memberPwd=" + memberPwd + ", memberName="
				+ memberName + ", memberNickname=" + memberNickname + ", memberEmail=" + memberEmail
				+ ", memberAddress=" + memberAddress + ", memberEnrollDate=" + memberEnrollDate + ", memberStatus="
				+ memberStatus + ", memberGrade=" + memberGrade + "]";
	}

}