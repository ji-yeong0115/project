package com.kh.baby.mypage.model.vo;

public class Grow {
	private String gender;
	private double cm;
	private double kg;
	private int month;
	
	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}
	
	public Grow() {
	}
	
	public Grow(String gender, double cm, double kg) {
		super();
		this.gender = gender;
		this.cm = cm;
		this.kg = kg;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public double getCm() {
		return cm;
	}

	public void setCm(double cm) {
		this.cm = cm;
	}

	public double getKg() {
		return kg;
	}

	public void setKg(double kg) {
		this.kg = kg;
	}

	@Override
	public String toString() {
		return "babyGrow [gender=" + gender + ", cm=" + cm + ", kg=" + kg + "]";
	}
	
	
}
