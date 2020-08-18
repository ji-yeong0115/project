package com.kh.baby.mypage.model.vo;

import java.sql.Timestamp;

public class Board {

	 private int boardNo;
	 private String boardTitle;
	 private String boardContent;
	 
	 private Timestamp boardCreateDate;
	 private Timestamp boardModifyDate;
	 
	 private int likeCount;
	 private int replyCount;
	 private int readCount;
	 
	 private String boardStatus;
	 private int boardType;
	 private String boardNotice;
	 private String StringboardType;
	 private String categoryName;

	public String getCategoryName() {
		return categoryName;
	}


	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}


	public String getStringboardType() {
		return StringboardType;
	}


	public void setStringboardType(String stringboardType) {
		StringboardType = stringboardType;
	}


	public Board() {
	}

	
	public Board(int boardNo, String boardTitle, String boardContent, Timestamp boardCreateDate,
			Timestamp boardModifyDate, int likeCount, int replyCount, int readCount, String boardStatus, int boardType,
			String hosAddress, String hosTime, String hosNightYN, String hosWeekenYN, String boardNotice) {
		super();
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardCreateDate = boardCreateDate;
		this.boardModifyDate = boardModifyDate;
		this.likeCount = likeCount;
		this.replyCount = replyCount;
		this.readCount = readCount;
		this.boardStatus = boardStatus;
		this.boardType = boardType;
		this.boardNotice = boardNotice;
	}


	public Board(int boardNo, String boardTitle, Timestamp boardCreateDate, int replyCount, int boardType) {
		super();
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
		this.boardCreateDate = boardCreateDate;
		this.replyCount = replyCount;
		this.boardType = boardType;
	}


	public Board(int boardNo, String boardTitle, int likeCount, int readCount, String hosAddress, String boardNotice) {
		super();
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
		this.likeCount = likeCount;
		this.readCount = readCount;
		this.boardNotice = boardNotice;
	}


	public Board(int boardNo, String boardTitle, Timestamp boardCreateDate, Timestamp boardModifyDate,
			String hosAddress, String hosTime, String hosNightYN, String hosWeekenYN, String boardNotice, String boardContent) {
		super();
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
		this.boardCreateDate = boardCreateDate;
		this.boardModifyDate = boardModifyDate;
		this.boardNotice = boardNotice;
		this.boardContent = boardContent;
	}
	
	public Board(String boardTitle, Timestamp boardCreateDate, Timestamp boardModifyDate, String hosAddress,
			String hosTime, String hosNightYN, String hosWeekenYN) {
		super();
		this.boardTitle = boardTitle;
		this.boardCreateDate = boardCreateDate;
		this.boardModifyDate = boardModifyDate;
	}
	
	public Board(int boardNo, String boardTitle, String hosAddress, String hosTime, String hosNightYN,
			String hosWeekenYN) {
		super();
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
	}
	
	public Board(int boardNo, String boardTitle, String boardContent) {
		super();
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
	}
	
	
	public Board(int boardNo, String boardTitle, int likeCount, int replyCount, int readCount, String hosAddress) {
		super();
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
		this.likeCount = likeCount;
		this.replyCount = replyCount;
		this.readCount = readCount;
	}

	public Board(int boardNo, String boardTitle, int likeCount, int replyCount, 
			int readCount, String hosAddress, String boardNotice, String boardContent) {
		super();
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
		this.likeCount = likeCount;
		this.replyCount = replyCount;
		this.readCount = readCount;
		this.boardNotice = boardNotice;
		this.boardContent = boardContent;
	}
	
	public Board(int boardNo, String boardTitle, int readCount, int commonAge) {
		super();
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
		this.readCount = readCount;
	}
	
	
	

	public Board(int boardNo, String boardTitle, String boardContent, Timestamp boardCreateDate,
			Timestamp boardModifyDate) {
		super();
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardCreateDate = boardCreateDate;
		this.boardModifyDate = boardModifyDate;
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


	public Timestamp getBoardCreateDate() {
		return boardCreateDate;
	}


	public void setBoardCreateDate(Timestamp boardCreateDate) {
		this.boardCreateDate = boardCreateDate;
	}


	public Timestamp getBoardModifyDate() {
		return boardModifyDate;
	}


	public void setBoardModifyDate(Timestamp boardModifyDate) {
		this.boardModifyDate = boardModifyDate;
	}


	public int getLikeCount() {
		return likeCount;
	}


	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}


	public int getReplyCount() {
		return replyCount;
	}


	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}


	public int getReadCount() {
		return readCount;
	}


	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}


	public String getBoardStatus() {
		return boardStatus;
	}


	public void setBoardStatus(String boardStatus) {
		this.boardStatus = boardStatus;
	}


	public int getBoardType() {
		return boardType;
	}


	public void setBoardType(int boardType) {
		this.boardType = boardType;
	}

	public String getBoardNotice() {
		return boardNotice;
	}


	public void setBoardNotice(String boardNotice) {
		this.boardNotice = boardNotice;
	}

	@Override
	public String toString() {
		return "Board [boardNo=" + boardNo + ", boardTitle=" + boardTitle + ", boardContent=" + boardContent
				+ ", boardCreateDate=" + boardCreateDate + ", boardModifyDate=" + boardModifyDate + ", likeCount="
				+ likeCount + ", replyCount=" + replyCount + ", readCount=" + readCount + ", boardStatus=" + boardStatus
				+ ", boardType=" + boardType + ", boardNotice=" + boardNotice;
	}


	
	
	


	
}
