package it.spaghettisource.navaltrader.game.model;

import java.util.UUID;

public class Loan {

	String id;
	double amount;
	double interest;
	
	public Loan(int amount, double interest) {
		super();
		this.id = UUID.randomUUID().toString();
		this.amount = amount;
		this.interest = interest;
	}
	
	public String getId() {
		return id;
	}

	public double getAmount() {
		return amount;
	}

	public double getInterest() {
		return interest;
	}

	public void repair(int repairAmount){
		amount = amount - repairAmount;
	}
	
	public boolean isTotalyRepair(){
		if(amount <= 0){
			return true;
		}
		return false;
	}
	
	public double calculateDailyInterest(int daysPass){
		//I = Crd/36.500
		return  amount*interest*daysPass/36500;
	}

	public double calculateMontlyInterest(int monthsPass){
		//I = Crm/1200
		return amount*interest*monthsPass/1200;
	}
	
	public double calculateYearlyInterest(int yearsPass){
		//I = Cry/100
		return amount*interest*yearsPass/100;
	}	
		
}
