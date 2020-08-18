package com.kh.baby.mypage.model.vo;

public class DiaryAttachment {
	private int fileNo;
	private int diaryNo;
	private String fileOriginName;
	private String fileChangeName;
	private String filePath;
	private int fileLevel;
	private String fileStatus;
	
	public DiaryAttachment() {}

	public DiaryAttachment(int diaryNo, String fileChangeName) {
		super();
		this.diaryNo = diaryNo;
		this.fileChangeName = fileChangeName;
	}

	public DiaryAttachment(int fileNo, String fileChangeName, String filePath, int fileLevel, int diaryNo) {
		super();
		this.fileNo = fileNo;
		this.fileChangeName = fileChangeName;
		this.filePath = filePath;
		this.fileLevel = fileLevel;
		this.diaryNo = diaryNo;
	}

	public DiaryAttachment(int fileNo, int diaryNo, String fileOriginName, String fileChangeName, String filePath,
			int fileLevel, String fileStatus) {
		super();
		this.fileNo = fileNo;
		this.diaryNo = diaryNo;
		this.fileOriginName = fileOriginName;
		this.fileChangeName = fileChangeName;
		this.filePath = filePath;
		this.fileLevel = fileLevel;
		this.fileStatus = fileStatus;
	}

	public int getFileNo() {
		return fileNo;
	}

	public void setFileNo(int fileNo) {
		this.fileNo = fileNo;
	}

	public int getDiaryNo() {
		return diaryNo;
	}

	public void setDiaryNo(int diaryNo) {
		this.diaryNo = diaryNo;
	}

	public String getFileOriginName() {
		return fileOriginName;
	}

	public void setFileOriginName(String fileOriginName) {
		this.fileOriginName = fileOriginName;
	}

	public String getFileChangeName() {
		return fileChangeName;
	}

	public void setFileChangeName(String fileChangeName) {
		this.fileChangeName = fileChangeName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getFileLevel() {
		return fileLevel;
	}

	public void setFileLevel(int fileLevel) {
		this.fileLevel = fileLevel;
	}

	public String getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}

	@Override
	public String toString() {
		return "DiaryAttachment [fileNo=" + fileNo + ", diaryNo=" + diaryNo + ", fileOriginName=" + fileOriginName
				+ ", fileChangeName=" + fileChangeName + ", filePath=" + filePath + ", fileLevel=" + fileLevel
				+ ", fileStatus=" + fileStatus + "]";
	}

	
}
