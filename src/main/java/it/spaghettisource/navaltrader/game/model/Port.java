package it.spaghettisource.navaltrader.game.model;

import java.util.ArrayList;
import java.util.List;

import it.spaghettisource.navaltrader.game.factory.ContractFactory;
import it.spaghettisource.navaltrader.game.loop.Entity;
import it.spaghettisource.navaltrader.geometry.Point;

public class Port  implements Entity{

	private World world;

	private Point cooridnate;
	
	private String name;
	private int classAccepted;
	private double dailyFeeCost;	
	private double castOffCost;	
	private double fuelPrice;
	private double repairPrice;	
	private int dayContractRegeneration;	
	private int dayToNextContractRegeneration;
	private List<TransportContract> contracts;
	private List<Route> routes;

	
	
	public Port(World world,Point cooridnate,String name, double dailyFeeCost, double castOffCost, int classAccepted,int dayContractRegeneration) {
		super();
		this.world =  world;
		this.cooridnate = cooridnate;
		this.name = name;
		this.dailyFeeCost = dailyFeeCost;
		this.castOffCost = castOffCost;
		this.classAccepted = classAccepted;
		this.dayContractRegeneration = dayContractRegeneration;
		this.dayToNextContractRegeneration = dayContractRegeneration;
		this.contracts = new ArrayList<TransportContract>(0);
		this.routes = new ArrayList<Route>();
		this.fuelPrice = 700.0;
		this.repairPrice = 25000.0;		
	}

	public String getName() {
		return name;
	}
		
	public Point getCooridnate() {
		return cooridnate;
	}
	
	public void setCooridnate(Point cooridnate) {
		this.cooridnate = cooridnate;
	}

	public int getClassAccepted() {
		return classAccepted;
	}

	public double getDailyFeeCost() {
		return dailyFeeCost;
	}

	public double getCastOffCost() {
		return castOffCost;
	}

	public int getDayContractRegeneration() {
		return dayContractRegeneration;
	}

	public List<TransportContract> getContracts() {
		return contracts;
	}
	
	public void addRoute(Route route) {
		routes.add(route);
	}
	
	public Route getRouteTo(Port destination) {
		for (Route route : routes) {
			if(route.isDestination(destination)) {
				return route;
			}
		}
		
		return null;
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
		
	//TODO implement logic to calculate fuel price
	public double getFuelPrice() {
		return fuelPrice;	
	}

	//TODO implement logic to calculate repair price
	public double getRepairPrice() {
		return repairPrice;	
	}


	public void generateContracts(){
		contracts.clear();
		contracts.addAll(ContractFactory.generateContracts(world, this)) ;
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
	
	
	public boolean equals(Object obj){
		if(obj==null){
			return false;
		}else if(!(obj instanceof Port)){
			return false;
		}else{
			return name.equals(((Port)obj).getName());
		}	
	}	
	
	public String toString() {
		return "{name:"+name+" cooridnate:"+cooridnate.getX()+"/"+cooridnate.getY()+"}";
	}	
	
	
}
