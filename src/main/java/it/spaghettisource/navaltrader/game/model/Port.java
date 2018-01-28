package it.spaghettisource.navaltrader.game.model;

import java.util.ArrayList;
import java.util.List;

import it.spaghettisource.navaltrader.game.loop.Entity;

public class Port  implements Entity{

	private String name;
	private int classAccepted;
	private double dailyFeeCost;	
	private double fuelPrice;
	private double repairPrice;	
	private int dayContractRegeneration;	
	private int dayToNextContractRegeneration;
	private List<TransportContract> contracts;

	
	
	public Port(String name, double dailyFeeCost, int classAccepted,int dayContractRegeneration) {
		super();
		this.name = name;
		this.dailyFeeCost = dailyFeeCost;
		this.classAccepted = classAccepted;
		this.dayContractRegeneration = dayContractRegeneration;
		this.dayToNextContractRegeneration = dayContractRegeneration;
		this.contracts = new ArrayList<TransportContract>(0);
		this.fuelPrice = 700.0;
		this.repairPrice = 25000.0;		
		generateContracts();
		
	}

	public String getName() {
		return name;
	}

	public int getClassAccepted() {
		return classAccepted;
	}

	public double getDailyFeeCost() {
		return dailyFeeCost;
	}

	public int getDayContractRegeneration() {
		return dayContractRegeneration;
	}

	public List<TransportContract> getContracts() {
		return contracts;
	}
	
	public TransportContract removeContractById(String contractId) {
		TransportContract selected = null;
		for (TransportContract transportContract : contracts) {
			if(transportContract.getId().equals(contractId)) {
				selected = transportContract;
			}
		}
		
		if(selected!=null) {
			contracts.remove(selected);			
		}
		return selected;
	}
		

	public double getFuelPrice() {
		return fuelPrice;	//TODO implement logic to calculate fuel price
	}

	public double getRepairPrice() {
		return repairPrice;	//TODO implement logic to calculate repari price
	}

	private void generateContracts(){
		contracts = TransportContract.generateNewContract(10);
	}
	
	public void update(int minutsPassed, boolean isNewDay, boolean isNewWeek, boolean isNewMonth) {
	
		if(isNewDay){
			dayToNextContractRegeneration = dayToNextContractRegeneration-1;
		}

		if(dayToNextContractRegeneration == 0){
			generateContracts();
			dayToNextContractRegeneration = dayContractRegeneration;
		}

		
	}
	
	
}
