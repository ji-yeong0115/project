package com.kh.baby.board.model.vo;

public class RequestBoard {
	
	private int requestNo;
	private String requestType;
	private String requestContent;
	private int boardNo;
	private int memberNo;
	
	public RequestBoard() {
		// TODO Auto-generated constructor stub
	}

	public RequestBoard(int requestNo, String requestType, String requestContent, int boardNo, int memberNo) {
		super();
		this.requestNo = requestNo;
		this.requestType = requestType;
		this.requestContent = requestContent;
		this.boardNo = boardNo;
		this.memberNo = memberNo;
	}
	
	

	public RequestBoard(String requestType, String requestContent, int memberNo) {
		super();
		this.requestType = requestType;
		this.requestContent = requestContent;
		this.memberNo = memberNo;
	}
	
	

	public RequestBoard(String requestType, String requestContent, int boardNo, int memberNo) {
		super();
		this.requestType = requestType;
		this.requestContent = requestContent;
		this.boardNo = boardNo;
		this.memberNo = memberNo;
	}

	public int getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(int requestNo) {
		this.requestNo = requestNo;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getRequestContent() {
		return requestContent;
	}

	public void setRequestContent(String requestContent) {
		this.requestContent = requestContent;
	}

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	public int getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}

	@Override
	public String toString() {
		return "RequestBoard [requestNo=" + requestNo + ", requestType=" + requestType + ", requestContent="
				+ requestContent + ", boardNo=" + boardNo + ", memberNo=" + memberNo + "]";
	}
	
	
	
	

}
