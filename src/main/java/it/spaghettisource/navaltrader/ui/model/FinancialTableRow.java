package it.spaghettisource.navaltrader.ui.model;

import java.util.ArrayList;
import java.util.List;

import it.spaghettisource.navaltrader.game.model.Finance;
import it.spaghettisource.navaltrader.game.model.FinancialEntryType;
import test.ButtonColumn;

public class FinancialTableRow {

	private String name;
	private Double amount;
	
	public FinancialTableRow(String entryName, Double amount) {
		super();
		this.name = entryName;
		this.amount = amount;
	}
	
	public String getName() {
		return name;
	}

	public Double getAmount() {
		return amount;
	}


	public static List<FinancialTableRow> mapData(Finance finance){
		
		List<FinancialTableRow> entryList = new ArrayList<FinancialTableRow>();
		
		for (FinancialEntryType key : finance.getEntry().keySet()) {
			entryList.add(new FinancialTableRow(key.toString(), finance.getEntry().get(key)));
		}
	
		return entryList;
	}
	
}
