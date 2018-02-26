package it.spaghettisource.navaltrader.game.model;

import java.util.ArrayList;
import java.util.List;

import it.spaghettisource.navaltrader.game.loop.Entity;
import it.spaghettisource.navaltrader.geometry.Point;

public class Port  implements Entity{

	private World world;

	private Point cooridnate;
	
	private String name;
	private int classAccepted;
	private double loadTeuPerHour;		
	private double dailyFeeCost;	
	private double castOffCost;	
	private double fuelPrice;
	private double repairPrice;	
	private List<Route> routes;
	private Market market;

	
	
	public Port(World world,Point cooridnate,String name, double dailyFeeCost, double castOffCost, int classAccepted,double loadTeuPerHour) {
		super();
		this.world =  world;
		this.cooridnate = cooridnate;
		this.name = name;
		this.dailyFeeCost = dailyFeeCost;
		this.castOffCost = castOffCost;
		this.classAccepted = classAccepted;
		this.loadTeuPerHour = loadTeuPerHour;
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
	
	public void addRoute(Route route) {
		routes.add(route);
	}
	
	public Market getMarket() {
		return market;
	}

	public void setMarket(Market market) {
		this.market = market;
	}

	public World getWorld() {
		return world;
	}

	public Route getRouteTo(Port destination) {
		for (Route route : routes) {
			if(route.isDestination(destination)) {
				return route;
			}
		}
		
		return null;
	}
	
	
	public double getLoadTeuPerHour() {
		return loadTeuPerHour;
	}

	//TODO implement logic to calculate fuel price
	public double getFuelPrice() {
		return fuelPrice;	
	}

	//TODO implement logic to calculate repair price
	public double getRepairPrice() {
		return repairPrice;	
	}


	
	
	public void update(int minutsPassed, boolean isNewDay, boolean isNewWeek, boolean isNewMonth) {
	
		market.update(minutsPassed, isNewDay, isNewWeek, isNewMonth);
		
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
