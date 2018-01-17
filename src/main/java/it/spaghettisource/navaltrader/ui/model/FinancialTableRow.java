package it.spaghettisource.navaltrader.ui.model;

import java.util.ArrayList;
import java.util.List;

import it.spaghettisource.navaltrader.game.model.Finance;
import it.spaghettisource.navaltrader.game.model.FinancialEntryType;
import test.ButtonColumn;

public class FinancialTableRow {

	private String entry;
	private String profit;
	private String loss;
	
	public FinancialTableRow(String entry, String profit, String loss) {
		super();
		this.entry = entry;
		this.profit = profit;
		this.loss = loss;
	}

	public String getEntry() {
		return entry;
	}

	public String getProfit() {
		return profit;
	}

	public String getLoss() {
		return loss;
	}
	
	public static List<FinancialTableRow> mapData(Finance finance){
		
		List<FinancialTableRow> entryList = new ArrayList<FinancialTableRow>();
		
		for (FinancialEntryType key : finance.getProfit().keySet()) {
			entryList.add(new FinancialTableRow(key.toString(), Integer.toString(finance.getProfit().get(key)), "0"));
		}
		for (FinancialEntryType key : finance.getLoss().keySet()) {
			entryList.add(new FinancialTableRow(key.toString(), "0", Integer.toString(finance.getLoss().get(key))));
		}
		
		return entryList;
	}
	
}
