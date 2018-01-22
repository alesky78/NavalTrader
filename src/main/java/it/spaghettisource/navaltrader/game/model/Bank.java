package it.spaghettisource.navaltrader.game.model;

import java.util.ArrayList;
import java.util.List;

import it.spaghettisource.navaltrader.game.loop.Entity;
import it.spaghettisource.navaltrader.ui.event.Event;
import it.spaghettisource.navaltrader.ui.event.EventType;
import it.spaghettisource.navaltrader.ui.event.InboundEventQueue;

public class Bank implements Entity {

	private List<Loan> loanList;
	private double interest;
	private  double maxLoanAmount = 5000000;	

	public Bank(double interest) {
		super();
		loanList = new ArrayList<Loan>();
		this.interest = interest;
	}
	
	public void createNewLoad(int amount,Company company){
		Loan newLoan = new Loan(amount, interest); 
		loanList.add(newLoan);
		InboundEventQueue.getInstance().put(new Event(EventType.LOAN_EVENT,newLoan));		
		
		company.addBudget(amount);			

	}
	
	public void repairLoad(String loanId, int amount,Company company){
		Loan loan = getLoanById(loanId);
		loan.repair(amount);
		
		if(loan.isTotalyRepair()){
			loanList.remove(loan);
		}	
		InboundEventQueue.getInstance().put(new Event(EventType.LOAN_EVENT,loan));
		
		company.removeBudget(amount);		
	}	
	
	public double getActualInterest(Company company){
		return 0.08;
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


	public void update(int minutsPassed) {
		// TODO business logic for bank
		
	}
	
	
	
	
}
