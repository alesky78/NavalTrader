package it.spaghettisource.navaltrader.game.model;

import java.util.HashMap;
import java.util.Map;

public class Finance {

	private Map<FinancialEntryType,Double> profit;
	private Map<FinancialEntryType,Double> loss;	
	
	public Finance() {
		profit = new HashMap<FinancialEntryType,Double>();
		loss = new HashMap<FinancialEntryType,Double>();
	}
	
	public void init() {
		profit.clear();
		profit.put(FinancialEntryType.SHIP_INCOME, 0.0);
		
		loss.clear();		
		loss.put(FinancialEntryType.SHIP_REPAIR, 0.0);
		loss.put(FinancialEntryType.SHIP_MAINTAINANCE, 0.0);
		loss.put(FinancialEntryType.SHIP_FUEL, 0.0);		
	}
	
	public void addProfit(FinancialEntryType type,double amount) {
		Double actual = profit.get(type);
		if(actual == null){
			actual = new Double(0);
		}		
		profit.put(type, actual+amount);
	}

	public void addLoss(FinancialEntryType type,double amount) {
		Double actual = loss.get(type);
		if(actual == null){
			actual = new Double(0);
		}
		loss.put(type, actual+amount);
	}
	
	public Map<FinancialEntryType, Double> getProfit() {
		return profit;
	}

	public Map<FinancialEntryType, Double> getLoss() {
		return loss;
	}
	
	public double getNetProfit(){
		
		double netProfit = 0;
		
		for (FinancialEntryType key : profit.keySet()) {
			netProfit += profit.get(key);
		}
		for (FinancialEntryType key : loss.keySet()) {
			netProfit -= loss.get(key);
		}
		return netProfit;
	}
	
	public void add(Finance other){
		
		Double amount = null;
		
		for (FinancialEntryType key : other.profit.keySet()) {
			amount = profit.get(key);
			if(amount == null){
				amount = Double.valueOf(0);
			}
			profit.put(key, amount+other.profit.get(key));
		}
		for (FinancialEntryType key : other.loss.keySet()) {
			amount = loss.get(key);
			if(amount == null){
				amount = Double.valueOf(0);
			}			
			loss.put(key, amount+other.loss.get(key));
		}
		
	}


	
}
