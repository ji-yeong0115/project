package com.kh.baby.community.model.vo;

import java.sql.Timestamp;

public class Board {

	 private int boardNo;
	   private String boardTitle;
	   private String boardContent;
	   private String memberId;
	   private int readCount;
	   private Timestamp boardCreateDate;
	   private Timestamp boardModifyDate;
	   private String categoryName;
	   private String boardStatus;
	   private int boardType;
	   private int boardLike;
	   private String memberName;
	   private int sellAge;
	   private String hashtag;
	   private int memberNo;
	   private int price;
	   private String sellIntro;
	   private String boardNotice;
	   
	   
	public Board(int boardNo, String boardTitle, String boardContent, String memberId, int readCount,
			Timestamp boardCreateDate, Timestamp boardModifyDate, String categoryName, String boardStatus,
			int boardType) {
		super();
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.memberId = memberId;
		this.readCount = readCount;
		this.boardCreateDate = boardCreateDate;
		this.boardModifyDate = boardModifyDate;
		this.categoryName = categoryName;
		this.boardStatus = boardStatus;
		this.boardType = boardType;
	}


	

	public Board(String boardTitle, String boardContent, String memberId, String categoryName, int boardType, int price,
			String sellIntro, String hashtag) {
		super();
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.memberId = memberId;
		this.categoryName = categoryName;
		this.boardType = boardType;
		this.price = price;
		this.sellIntro = sellIntro;
		this.hashtag = hashtag;
	}




	public String getBoardNotice() {
		return boardNotice;
	}




	public void setBoardNotice(String boardNotice) {
		this.boardNotice = boardNotice;
	}




	public Board(int boardNo, int boardType, int memberNo) {
		super();
		this.boardNo = boardNo;
		this.boardType = boardType;
		this.memberNo = memberNo;
	}




	public Board(int boardNo, String boardTitle, String boardContent, int readCount, int boardLike,
			String boardNotice) {
		super();
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.readCount = readCount;
		this.boardLike = boardLike;
		this.boardNotice = boardNotice;
	}




	public Board(int boardNo, String boardTitle, int readCount, Timestamp boardCreateDate, String categoryName,
			String memberName, int boardLike, String boardNotice ) {
		super();
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
		this.readCount = readCount;
		this.boardCreateDate = boardCreateDate;
		this.categoryName = categoryName;
		this.memberName = memberName;
		this.boardLike = boardLike;
		this.boardNotice=boardNotice;
	}




	public Board(int boardNo, String boardTitle, String boardContent, int readCount, Timestamp boardCreateDate,
			int boardLike, String memberName, int sellAge, String hashtag, int price,
			String sellIntro,String memberId) {
		super();
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.readCount = readCount;
		this.boardCreateDate = boardCreateDate;
		this.boardLike = boardLike;
		this.memberName = memberName;
		this.sellAge = sellAge;
		this.hashtag = hashtag;
		this.price = price;
		this.sellIntro = sellIntro;
		this.memberId=memberId;
	}




	public Board(int boardNo, String boardTitle, String memberName, int readCount, Timestamp boardCreateDate,
			String categoryName) {
		super();
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
		this.memberName = memberName;
		this.readCount = readCount;
		this.boardCreateDate = boardCreateDate;
		this.categoryName = categoryName;
	}
	
	
	

	public Board(int boardNo, String boardTitle, String boardContent, String categoryName, int boardType) {
		super();
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.categoryName = categoryName;
		this.boardType = boardType;
	}




	



	public Board(int boardNo, String boardTitle, String boardContent, int sellAge, String hashtag, int price,
			String sellIntro) {
		super();
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.sellAge = sellAge;
		this.hashtag = hashtag;
		this.price = price;
		this.sellIntro = sellIntro;
	}



	public Board(int boardNo, String boardTitle, String boardContent, String categoryName) {
		super();
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.categoryName = categoryName;
	}




	public Board(int boardNo, int sellAge, String boardTitle , String hashtag, String memberName, Timestamp boardCreateDate) {
		super();
		this.boardNo = boardNo;
		this.sellAge = sellAge;
		this.boardTitle = boardTitle;
		this.hashtag = hashtag;
		this.memberName = memberName;
		this.boardCreateDate = boardCreateDate;
	}



	public Board(int boardNo,String boardTitle, String boardContent, String memberName, int readCount, Timestamp boardCreateDate,
			Timestamp boardModifyDate, String categoryName, int boardLike, String memberId) {
		super();
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.memberName = memberName;
		this.readCount = readCount;
		this.boardCreateDate = boardCreateDate;
		this.boardModifyDate = boardModifyDate;
		this.categoryName = categoryName;
		this.boardLike = boardLike;
		this.memberId=memberId;
	}


	public Board(String boardTitle, String boardContent, String memberId, String categoryName, int boardType) {
		super();
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.memberId = memberId;
		this.categoryName = categoryName;
		this.boardType = boardType;
	}

	

	public Board(String boardTitle, String boardContent, String memberId, int sellAge, int boardType,
			String hashtag, String sellIntro) {
		super();
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.memberId = memberId;
		this.sellAge = sellAge;
		this.boardType = boardType;
		this.hashtag = hashtag;
		this.sellIntro = sellIntro;
	}
	
	

	public Board(String boardTitle, String boardContent, String memberId, int sellAge, int boardType,
			int price, String sellIntro, String hashtag) {
		super();
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.memberId = memberId;
		this.sellAge = sellAge;
		this.boardType = boardType;
		this.price = price;
		this.sellIntro = sellIntro;
		this.hashtag = hashtag;
	}




	public Board() {
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

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
	

	public String getHashtag() {
		return hashtag;
	}

	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}



	public int getBoardLike() {
		return boardLike;
	}

	public void setBoardLike(int boardLike) {
		this.boardLike = boardLike;
	}

	public int getSellAge() {
		return sellAge;
	}



	public void setSellAge(int sellAge) {
		this.sellAge = sellAge;
	}



	public String getMemberName() {
		return memberName;
	}



	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}



	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	public String getSellIntro() {
		return sellIntro;
	}


	public void setSellIntro(String sellIntro) {
		this.sellIntro = sellIntro;
	}


	@Override
	public String toString() {
		return "Board [boardNo=" + boardNo + ", boardTitle=" + boardTitle + ", boardContent=" + boardContent
				+ ", memberId=" + memberId + ", readCount=" + readCount + ", boardCreateDate=" + boardCreateDate
				+ ", boardModifyDate=" + boardModifyDate + ", categoryName=" + categoryName + ", boardStatus="
				+ boardStatus + ", boardType=" + boardType + ", boardLike=" + boardLike + ", memberName=" + memberName
				+ ", sellAge=" + sellAge + ", hashtag=" + hashtag + ", memberNo=" + memberNo + ", price=" + price
				+ ", sellIntro=" + sellIntro + "]";
	}


	
	




	

}
