package it.spaghettisource.navaltrader.ui.model;

import java.util.ArrayList;
import java.util.List;

import it.spaghettisource.navaltrader.game.model.Loan;

public class LoanTableRow {

	private String id;
	private Double amount;
	private Double interest;
	private Double dailyPayment;	
	
	public LoanTableRow(String id, Double amount, Double interest,Double dailyPayment) {
		super();
		this.id = id;
		this.amount = amount;
		this.interest = interest;
		this.dailyPayment = dailyPayment;
	}

	public String getId() {
		return id;
	}

	public Double getAmount() {
		return amount;
	}

	public Double getInterest() {
		return interest;
	}
	
	public Double getDailyPayment() {
		return dailyPayment;
	}

	public static List<LoanTableRow> mapData(List<Loan> listOfLoans){
		
		List<LoanTableRow> LoanTableRow = new ArrayList<LoanTableRow>();
		for (Loan loan : listOfLoans) {
			LoanTableRow.add(new LoanTableRow(loan.getId(), loan.getAmount(), loan.getInterest(),loan.calculateDailyInterest(1)));
		}
		
		return LoanTableRow;
	}
	
}
