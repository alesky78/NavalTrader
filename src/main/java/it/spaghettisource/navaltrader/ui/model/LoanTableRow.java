package it.spaghettisource.navaltrader.ui.model;

import java.util.ArrayList;
import java.util.List;

import it.spaghettisource.navaltrader.game.model.Loan;

public class LoanTableRow {

	private String id;
	private String amount;
	private String interest;
	
	public LoanTableRow(String id, String amount, String interest) {
		super();
		this.id = id;
		this.amount = amount;
		this.interest = interest;
	}

	public String getId() {
		return id;
	}

	public String getAmount() {
		return amount;
	}

	public String getInterest() {
		return interest;
	}


	public static List<LoanTableRow> mapData(List<Loan> listOfLoans){
		
		List<LoanTableRow> LoanTableRow = new ArrayList<LoanTableRow>();
		for (Loan loan : listOfLoans) {
			LoanTableRow.add(new LoanTableRow(loan.getId(), Integer.toString(loan.getAmount()), Double.toString(loan.getInterest())));
		}
		
		return LoanTableRow;
	}
	
}
