package it.spaghettisource.navaltrader.game.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import it.spaghettisource.navaltrader.game.loop.Entity;
import it.spaghettisource.navaltrader.geometry.Point;

public class Port  implements Entity{

	private World world;

	private Point cooridnate;
	
	private String name;
	private int classAccepted;
	private double dailyFeeCost;	
	private double fuelPrice;
	private double repairPrice;	
	private int dayContractRegeneration;	
	private int dayToNextContractRegeneration;
	private List<TransportContract> contracts;
	private List<Route> routes;

	
	
	public Port(World world,Point cooridnate,String name, double dailyFeeCost, int classAccepted,int dayContractRegeneration) {
		super();
		this.world =  world;
		this.cooridnate = cooridnate;
		this.name = name;
		this.dailyFeeCost = dailyFeeCost;
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

	//TODO implement the logic to calculate the new contracts
	public void generateContracts(){
		
		int numberOfContracts = 10;
		
		List<Port> connectePorts = world.getConnectedPorts(this);
		int connected = connectePorts.size();
		
		List<TransportContract> newContracts = new ArrayList<TransportContract>(numberOfContracts);

		int teu;
		int dwt;
		int price;
		Port port;
		
		for (int i = 0; i< numberOfContracts; i++) {
			teu = ThreadLocalRandom.current().nextInt(50, 999+1 );
			dwt = ThreadLocalRandom.current().nextInt(2, 10+1 );
			price = ThreadLocalRandom.current().nextInt(1000, 12000+1 );			
			port = connectePorts.get(ThreadLocalRandom.current().nextInt(0, connected ));	//get random port
			
			newContracts.add(new TransportContract("wood", teu, dwt, price, getRouteTo(port)));			
		}
		
		contracts = newContracts;
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
