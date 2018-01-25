package it.spaghettisource.navaltrader.game.model;

import java.util.HashMap;
import java.util.Map;

import it.spaghettisource.navaltrader.ui.event.Event;
import it.spaghettisource.navaltrader.ui.event.EventType;
import it.spaghettisource.navaltrader.ui.event.InboundEventQueue;

public class Finance {

	private Map<FinancialEntryType,Double> entry;
	
	
	public Finance() {
		entry = new HashMap<FinancialEntryType,Double>();
	}
		
	public void addEntry(FinancialEntryType type,double amount) {
		Double actual = entry.get(type);
		if(actual == null){
			entry.put(type, amount);			
		}else{
			entry.put(type, amount+actual);			
		}
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
