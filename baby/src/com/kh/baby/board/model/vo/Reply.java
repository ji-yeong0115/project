package com.kh.baby.board.model.vo;

import java.sql.Timestamp;

public class Reply {
	
	private int  replyNo;
	private String replyContent;
	private Timestamp replyWriteDay;
	private Timestamp replyModifyDay;
	private String replyStatus;
	private String memberNickName;
	private String memberId;
	private int boardNo;
	
	public Reply() {
		// TODO Auto-generated constructor stub
	}

	public Reply(int replyNo, String replyContent, Timestamp replyWriteDay, Timestamp replyModifyDay,
			String replyStatus, String memberId, int boardNo) {
		super();
		this.replyNo = replyNo;
		this.replyContent = replyContent;
		this.replyWriteDay = replyWriteDay;
		this.replyModifyDay = replyModifyDay;
		this.replyStatus = replyStatus;
		this.memberId = memberId;
		this.boardNo = boardNo;
	}
	

	public Reply(int replyNo, String replyContent, Timestamp replyWriteDay, Timestamp replyModifyDay,
			String memberNinkName, String memberId) {
		super();
		this.replyNo = replyNo;
		this.replyContent = replyContent;
		this.replyWriteDay = replyWriteDay;
		this.replyModifyDay = replyModifyDay;
		this.memberNickName = memberNinkName;
		this.memberId = memberId;
	}
	

	public Reply(String replyContent, String memberId, int boardNo) {
		super();
		this.replyContent = replyContent;
		this.memberId = memberId;
		this.boardNo = boardNo;
	}
	
	

	public Reply(int replyNo, String replyContent) {
		super();
		this.replyNo = replyNo;
		this.replyContent = replyContent;
	}

	public int getReplyNo() {
		return replyNo;
	}

	public void setReplyNo(int replyNo) {
		this.replyNo = replyNo;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public Timestamp getReplyWriteDay() {
		return replyWriteDay;
	}

	public void setReplyWriteDay(Timestamp replyWriteDay) {
		this.replyWriteDay = replyWriteDay;
	}

	public Timestamp getReplyModifyDay() {
		return replyModifyDay;
	}

	public void setReplyModifyDay(Timestamp replyModifyDay) {
		this.replyModifyDay = replyModifyDay;
	}

	public String getReplyStatus() {
		return replyStatus;
	}

	public void setReplyStatus(String replyStatus) {
		this.replyStatus = replyStatus;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public int getBoardNo() {
		return boardNo;
	}

	public void setParentBoardNo(int parentBoardNo) {
		this.boardNo = parentBoardNo;
	}

	public String getMemberNickName() {
		return memberNickName;
	}

	public void setMemberNickName(String memberNickName) {
		this.memberNickName = memberNickName;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	
	@Override
	public String toString() {
		return "Reply [replyNo=" + replyNo + ", replyContent=" + replyContent + ", replyWriteDay=" + replyWriteDay
				+ ", replyModifyDay=" + replyModifyDay + ", replyStatus=" + replyStatus + ", memberId=" + memberId
				+ ", parentBoardNo=" + boardNo + "]";
	}
	
	

}
