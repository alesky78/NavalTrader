package it.spaghettisource.navaltrader.game.model;

import java.util.HashMap;
import java.util.Map;

public class Finance {

	private Map<FinancialEntryType,Double> entry;
	
	
	public Finance() {
		entry = new HashMap<FinancialEntryType,Double>();
	}
	
	public void init() {
		entry.clear();
		entry.put(FinancialEntryType.SHIP_INCOME, 0.0);
		entry.put(FinancialEntryType.SHIP_REPAIR, 0.0);
		entry.put(FinancialEntryType.SHIP_OPERATING_COST, 0.0);
		entry.put(FinancialEntryType.SHIP_FUEL, 0.0);		
	}
	
	public void addEntry(FinancialEntryType type,double amount) {
		Double actual = entry.get(type);
		if(actual == null){
			actual = new Double(0);
		}		
		entry.put(type, actual+amount);
	}

	
	public Map<FinancialEntryType, Double> getEntry() {
		return entry;
	}

	
	public double getNetProfit(){
		
		double netProfit = 0;
		
		for (FinancialEntryType key : entry.keySet()) {
			netProfit += entry.get(key);
		}

		return netProfit;
	}
	
	public void add(Finance other){
		
		Double amount = null;
		
		for (FinancialEntryType key : other.entry.keySet()) {
			amount = entry.get(key);
			if(amount == null){
				amount = Double.valueOf(0);
			}
			entry.put(key, amount+other.entry.get(key));
		}

		
	}


	
}
