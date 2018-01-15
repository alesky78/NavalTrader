package it.spaghettisource.navaltrader.game.model;

import java.util.HashMap;
import java.util.Map;

public class Finance {

	private Map<FinancialEntryType,Integer> profit;
	private Map<FinancialEntryType,Integer> loss;	
	
	public Finance() {
		profit = new HashMap<FinancialEntryType,Integer>();
		loss = new HashMap<FinancialEntryType,Integer>();
	}
	
	public void init() {
		profit.clear();
		profit.put(FinancialEntryType.SHIP_INCOME, 0);
		
		loss.clear();		
		profit.put(FinancialEntryType.SHIP_REPAIR, 0);
		profit.put(FinancialEntryType.SHIP_MAINTAINANCE, 0);
		profit.put(FinancialEntryType.SHIP_FUEL, 0);		
	}
	
	public void addProfit(FinancialEntry toAdd) {
		Integer mount = profit.get(toAdd.getType());
		 profit.put(toAdd.getType(), mount+toAdd.getAmount());
	}

	public void addLoss(FinancialEntry toAdd) {
		Integer mount = loss.get(toAdd.getType());
		loss.put(toAdd.getType(), mount+toAdd.getAmount());
	}
	
}
