package it.spaghettisource.navaltrader.game.model;

import java.util.ArrayList;
import java.util.List;

public class Bank {

	private List<Loan> loanList;
	private double interest;
	private  double maxLoanAmount = 5000000;	

	public Bank(double interest) {
		super();
		loanList = new ArrayList<Loan>();
		this.interest = interest;
	}
	
	public Loan createNewLoad(int amount){
		Loan newLoan = new Loan(amount, interest); 
		loanList.add(newLoan);
		return newLoan;
	}
	
	public Loan repairLoad(String loanId, int amount){
		Loan loan = getLoanById(loanId);
		loan.repair(amount);
		
		if(loan.isTotalyRepair()){
			loanList.remove(loan);
		}	
		return loan;		
	}	
	
	public double getActualInterest(Company company){
		return interest;
	}

	public Double getMaxAcceptedAmount(Company company){
		double actualUsedAmount = 0;
		for (Loan loan : loanList) {
			actualUsedAmount += loan.getAmount();
		}
		
		return (maxLoanAmount - actualUsedAmount > 0 ) ? new Double(maxLoanAmount - actualUsedAmount) : new Double(0);
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
		return  new ArrayList(loanList);
	}	
	
}
