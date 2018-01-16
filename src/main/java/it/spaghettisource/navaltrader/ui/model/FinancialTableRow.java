package it.spaghettisource.navaltrader.ui.model;

import java.util.ArrayList;
import java.util.List;

import it.spaghettisource.navaltrader.game.model.Finance;
import it.spaghettisource.navaltrader.game.model.FinancialEntryType;

public class FinancialTableRow {

	private String entry;
	private int profit;
	private int loss;
	
	public FinancialTableRow(String entry, int profit, int loss) {
		super();
		this.entry = entry;
		this.profit = profit;
		this.loss = loss;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public int getProfit() {
		return profit;
	}

	public void setProfit(int profit) {
		this.profit = profit;
	}

	public int getLoss() {
		return loss;
	}

	public void setLoss(int loss) {
		this.loss = loss;
	}	
	
	public static List<FinancialTableRow> mapData(Finance finance){
		
		List<FinancialTableRow> entryList = new ArrayList<FinancialTableRow>();
		
		for (FinancialEntryType key : finance.getProfit().keySet()) {
			entryList.add(new FinancialTableRow(key.toString(), finance.getProfit().get(key), 0));
		}
		for (FinancialEntryType key : finance.getLoss().keySet()) {
			entryList.add(new FinancialTableRow(key.toString(), 0, finance.getLoss().get(key)));
		}
		
		return entryList;
	}
	
	
}
