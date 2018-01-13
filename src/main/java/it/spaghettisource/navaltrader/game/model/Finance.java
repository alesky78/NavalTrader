package it.spaghettisource.navaltrader.game.model;

import java.util.ArrayList;
import java.util.List;

public class Finance {

	private List<FinancialEntry> profit;
	private List<FinancialEntry> loss;	
	
	public Finance() {
		reset();	
	}
	
	public void reset() {
		profit = new ArrayList<FinancialEntry>();
		loss = new ArrayList<FinancialEntry>();		
	}
	
	public void addProfit(FinancialEntry toAdd) {
		profit.add(toAdd);
	}

	public void addLoss(FinancialEntry toAdd) {
		loss.add(toAdd);
	}
	
}
