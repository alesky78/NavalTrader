package it.spaghettisource.navaltrader.ui.model;

import java.util.ArrayList;
import java.util.List;

import it.spaghettisource.navaltrader.game.model.Loan;

public class LoanTableRow {

	private String id;
	private String amount;
	private String interest;
	private String dailyPayment;	
	
	public LoanTableRow(String id, String amount, String interest,String dailyPayment) {
		super();
		this.id = id;
		this.amount = amount;
		this.interest = interest;
		this.dailyPayment = dailyPayment;
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
	
	public String getDailyPayment() {
		return dailyPayment;
	}

	public static List<LoanTableRow> mapData(List<Loan> listOfLoans){
		
		List<LoanTableRow> LoanTableRow = new ArrayList<LoanTableRow>();
		for (Loan loan : listOfLoans) {
			LoanTableRow.add(new LoanTableRow(loan.getId(), Integer.toString(loan.getAmount()), Double.toString(loan.getInterest()*100)+"%",Double.toString(loan.calculateDailyInterest(1))));
		}
		
		return LoanTableRow;
	}
	
}
