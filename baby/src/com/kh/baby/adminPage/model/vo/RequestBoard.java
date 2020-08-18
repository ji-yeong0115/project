package com.kh.baby.adminPage.model.vo;

public class RequestBoard {
		private int boardNo;
		private String rType;
		private String rContent;
		private int memberNo;
		private String memberNM;
		private int parentNo;
		private int readCount;

		private String hosAddress;
		private String hosTime;
		private String hosNightYN;
		private String hosWeekenYN;
		 
		public RequestBoard() {
			// TODO Auto-generated constructor stub
		}

		public RequestBoard(int boardNo, String rContent, String hosAddress, String hosTime, String hosNightYN, String hosWeekenYN) {
			this.boardNo = boardNo;
			this.rContent = rContent;
			this.hosAddress = hosAddress;
			this.hosTime = hosTime;
			this.hosNightYN = hosNightYN;
			this.hosWeekenYN = hosWeekenYN;
		}


		public RequestBoard(int boardNo, String hosAddress, String hosTime, String hosNightYN, String hosWeekenYN) {
			this.boardNo = boardNo;
			this.hosAddress = hosAddress;
			this.hosTime = hosTime;
			this.hosNightYN = hosNightYN;
			this.hosWeekenYN = hosWeekenYN;
		}


		public RequestBoard(int boardNo, String rType, String rContent, String memberNM, int parentNo) {
			this.boardNo = boardNo;
			this.rType = rType;
			this.rContent = rContent;
			this.memberNM = memberNM;
			this.parentNo = parentNo;
		}

		public int getBoardNo() {
			return boardNo;
		}

		public void setBoardNo(int boardNo) {
			this.boardNo = boardNo;
		}

		public String getrType() {
			return rType;
		}

		public void setrType(String rType) {
			this.rType = rType;
		}

		public String getrContent() {
			return rContent;
		}

		public void setrContent(String rContent) {
			this.rContent = rContent;
		}

		public int getMemberNo() {
			return memberNo;
		}

		public void setMemberNo(int memberNo) {
			this.memberNo = memberNo;
		}

		public String getMemberNM() {
			return memberNM;
		}

		public void setMemberNM(String memberNM) {
			this.memberNM = memberNM;
		}

		public int getParentNo() {
			return parentNo;
		}

		public void setParentNo(int parentNo) {
			this.parentNo = parentNo;
		}

		public int getReadCount() {
			return readCount;
		}

		public void setReadCount(int readCount) {
			this.readCount = readCount;
		}

		public String getHosAddress() {
			return hosAddress;
		}

		public void setHosAddress(String hosAddress) {
			this.hosAddress = hosAddress;
		}

		public String getHosTime() {
			return hosTime;
		}

		public void setHosTime(String hosTime) {
			this.hosTime = hosTime;
		}

		public String getHosNightYN() {
			return hosNightYN;
		}

		public void setHosNightYN(String hosNightYN) {
			this.hosNightYN = hosNightYN;
		}

		public String getHosWeekenYN() {
			return hosWeekenYN;
		}

		public void setHosWeekenYN(String hosWeekenYN) {
			this.hosWeekenYN = hosWeekenYN;
		}

		@Override
		public String toString() {
			return "RequestBoard [boardNo=" + boardNo + ", rType=" + rType + ", rContent=" + rContent + ", memberNo="
					+ memberNo + ", memberNM=" + memberNM + ", parentNo=" + parentNo + ", readCount=" + readCount
					+ ", hosAddress=" + hosAddress + ", hosTime=" + hosTime + ", hosNightYN=" + hosNightYN
					+ ", hosWeekenYN=" + hosWeekenYN + "]";
		}
		
		
}
