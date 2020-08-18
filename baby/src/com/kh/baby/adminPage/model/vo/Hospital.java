package com.kh.baby.adminPage.model.vo;

import java.sql.Date;

public class Hospital {
	private int boardNo;
	private String hosAddr;
	private String hosTime;
	private String hosNight;
	private String hosWeekend;
	private String boardTitle;
	
	public Hospital() {
		// TODO Auto-generated constructor stub
	}
	
	
	public Hospital(String hosAddr, String hosTime, String hosNight, String hosWeekend, String boardTitle) {
		this.hosAddr = hosAddr;
		this.hosTime = hosTime;
		this.hosNight = hosNight;
		this.hosWeekend = hosWeekend;
		this.boardTitle = boardTitle;
	}


	public Hospital(int boardNo, String boardTitle, String hosAddr, String hosTime, String hosNight, String hosWeekend) {
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
		this.hosAddr = hosAddr;
		this.hosTime = hosTime;
		this.hosNight = hosNight;
		this.hosWeekend = hosWeekend;
	}

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	public String getHosAddr() {
		return hosAddr;
	}

	public void setHosAddr(String hosAddr) {
		this.hosAddr = hosAddr;
	}

	public String getHosTime() {
		return hosTime;
	}

	public void setHosTime(String hosTime) {
		this.hosTime = hosTime;
	}

	public String getHosNight() {
		return hosNight;
	}

	public void setHosNight(String hosNight) {
		this.hosNight = hosNight;
	}

	public String getHosWeekend() {
		return hosWeekend;
	}

	public void setHosWeekend(String hosWeekend) {
		this.hosWeekend = hosWeekend;
	}

	public String getBoardTitle() {
		return boardTitle;
	}

	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}

	@Override
	public String toString() {
		return "hospital [boardNo=" + boardNo + ", hosAddr=" + hosAddr + ", hosTime=" + hosTime + ", hosNight="
				+ hosNight + ", hosWeekend=" + hosWeekend + "]";
	}
	
	
	
}