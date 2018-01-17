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
		loss.put(FinancialEntryType.SHIP_REPAIR, 0);
		loss.put(FinancialEntryType.SHIP_MAINTAINANCE, 0);
		loss.put(FinancialEntryType.SHIP_FUEL, 0);		
	}
	
	public void addProfit(FinancialEntryType type,int amount) {
		Integer actual = profit.get(type);
		if(actual == null){
			actual = new Integer(0);
		}		
		profit.put(type, actual+amount);
	}

	public void addLoss(FinancialEntryType type,int amount) {
		Integer actual = loss.get(type);
		if(actual == null){
			actual = new Integer(0);
		}
		loss.put(type, actual+amount);
	}
	
	public Map<FinancialEntryType, Integer> getProfit() {
		return profit;
	}

	public Map<FinancialEntryType, Integer> getLoss() {
		return loss;
	}
	
	public int getNetProfit(){
		
		int netProfit = 0;
		
		for (FinancialEntryType key : profit.keySet()) {
			netProfit += profit.get(key);
		}
		for (FinancialEntryType key : loss.keySet()) {
			netProfit -= loss.get(key);
		}
		return netProfit;
	}
	
	public void add(Finance other){
		
		Integer amount = null;
		
		for (FinancialEntryType key : other.profit.keySet()) {
			amount = profit.get(key);
			if(amount == null){
				amount = Integer.valueOf(0);
			}
			profit.put(key, amount+other.profit.get(key));
		}
		for (FinancialEntryType key : other.loss.keySet()) {
			amount = loss.get(key);
			if(amount == null){
				amount = Integer.valueOf(0);
			}			
			loss.put(key, amount+other.loss.get(key));
		}
		
	}


	
}
