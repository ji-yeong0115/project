package com.kh.baby.member.model.vo;

public class General {
	
	private String kidsName;
	private String kidsGender;
	private String kidsBirth;
	private double kidsHeight;
	private double kidsWeight;
	
	public General() {
		// TODO Auto-generated constructor stub
	}
	
	
	public General(String kidsName, double kidsHeight, double kidsWeight) {
		super();
		this.kidsName = kidsName;
		this.kidsHeight = kidsHeight;
		this.kidsWeight = kidsWeight;
	}


	public General(String kidsName, String kidsGender, String kidsBirth, double kidsHeight, double kidsWeight) {
		super();
		this.kidsName = kidsName;
		this.kidsGender = kidsGender;
		this.kidsBirth = kidsBirth;
		this.kidsHeight = kidsHeight;
		this.kidsWeight = kidsWeight;
	}

	public String getKidsName() {
		return kidsName;
	}

	public void setKidsName(String kidsName) {
		this.kidsName = kidsName;
	}

	public String getKidsGender() {
		return kidsGender;
	}

	public void setKidsGender(String kidsGender) {
		this.kidsGender = kidsGender;
	}

	public String getKidsBirth() {
		String result = kidsBirth;
		if(kidsBirth != null) {
			result = kidsBirth.substring(0, 10);
		}
		return result;
	}

	public void setKidsBirth(String kidsBirth) {
		this.kidsBirth = kidsBirth;
	}

	public double getKidsHeight() {
		return kidsHeight;
	}

	public void setKidsHeight(double kidsHeight) {
		this.kidsHeight = kidsHeight;
	}

	public double getKidsWeight() {
		return kidsWeight;
	}

	public void setKidsWeight(double kidsWeight) {
		this.kidsWeight = kidsWeight;
	}

	@Override
	public String toString() {
		return "General [kidsName=" + kidsName + ", kidsGender=" + kidsGender + ", kidsBirth=" + kidsBirth
				+ ", kidsHeight=" + kidsHeight + ", kidsWeight=" + kidsWeight + "]";
	}
	
	
}
