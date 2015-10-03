package com.soalon.files.beans;

public class UitsettledetBean {
	private int lineNo;
	private String cardNo;
	private int amt;
	
	public int getLineNo(){
		return lineNo;
	}
	
	public void setLineNo(int lineNo){
		this.lineNo = lineNo;
	}

	public String getCardNo(){
		return cardNo;
	}

	public void setCardNo(String cardNo){
		this.cardNo = cardNo;
	}

	public int getAmt(){
		return amt;
	}

	public void setAmt(int amt){
		this.amt = amt;
	}
}
