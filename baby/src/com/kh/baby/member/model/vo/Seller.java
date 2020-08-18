package com.kh.baby.member.model.vo;

public class Seller {
	private String companyNo;
	private String companyPhone;
	
	public Seller() {
	}

	
	public Seller(String companyPhone) {
		super();
		this.companyPhone = companyPhone;
	}


	public Seller(String companyNo, String companyPhone) {
		super();
		this.companyNo = companyNo;
		this.companyPhone = companyPhone;
	}

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	@Override
	public String toString() {
		return "Seller [companyNo=" + companyNo + ", companyPhone=" + companyPhone + "]";
	}
	
	
}
