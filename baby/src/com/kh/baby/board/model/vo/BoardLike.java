package com.kh.baby.board.model.vo;

public class BoardLike {
	private int memberNo;
	private int boardNo;
	private int boardType;
	
	public BoardLike() {}

	public BoardLike(int memberNo, int boardNo, int boardType) {
		super();
		this.memberNo = memberNo;
		this.boardNo = boardNo;
		this.boardType = boardType;
	}

	public int getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	public int getBoardType() {
		return boardType;
	}

	public void setBoardType(int boardType) {
		this.boardType = boardType;
	}

	@Override
	public String toString() {
		return "BoardLike [memberNo=" + memberNo + ", boardNo=" + boardNo + ", boardType=" + boardType + "]";
	}

}
