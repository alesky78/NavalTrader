package it.spaghettisource.navaltrader.game.model;

import java.util.ArrayList;
import java.util.List;

public class Bank {

	private List<Loan> loanList;

	public Bank() {
		super();
		loanList = new ArrayList<Loan>();
	}
	
	public void createNewLoad(int amount, float interest){
		loanList.add(new Loan(amount, interest));
	}
	
	public void repairLoad(String loanId, int amount){
		Loan loan = getLoanById(loanId);
		loan.repair(amount);
		
		if(loan.isTotalyRepair()){
			loanList.remove(loan);
		}	
	}	
	
	public double proposeInterest(Company company){
		
		return 0.08;
		
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
	
	
	
	
}
