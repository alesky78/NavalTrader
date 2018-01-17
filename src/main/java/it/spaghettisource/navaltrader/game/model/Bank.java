package it.spaghettisource.navaltrader.game.model;

import java.util.ArrayList;
import java.util.List;

import it.spaghettisource.navaltrader.game.loop.Entity;

public class Bank implements Entity {

	private List<Loan> loanList;
	private double interest;
	private  int maxLoanAmount = 5000000;	

	public Bank(double interest) {
		super();
		loanList = new ArrayList<Loan>();
		this.interest = interest;
	}
	
	public void createNewLoad(int amount,Company company){
		loanList.add(new Loan(amount, interest));
		company.addBudget(amount);
	}
	
	public void repairLoad(String loanId, int amount){
		Loan loan = getLoanById(loanId);
		loan.repair(amount);
		
		if(loan.isTotalyRepair()){
			loanList.remove(loan);
		}	
	}	
	
	public double getActualInterest(Company company){
		return 0.08;
	}

	public int getMaxAcceptedAmount(Company company){
		int actualUsedAmount = 0;
		for (Loan loan : loanList) {
			actualUsedAmount += loan.getAmount();
		}
		
		return (maxLoanAmount - actualUsedAmount > 0 ) ? maxLoanAmount - actualUsedAmount : 0;
	}
	
	public Loan getLoanById(String id){
		for (Loan loan : loanList) {
			if(loan.getId().equals(id)){
				return loan;
			}
		}
		return null;
	}

	public List<Loan> getLoanList() {
		return loanList;
	}


	public void update(int minutsPassed) {
		// TODO business logic for bank
		
	}
	
	
	
	
}
