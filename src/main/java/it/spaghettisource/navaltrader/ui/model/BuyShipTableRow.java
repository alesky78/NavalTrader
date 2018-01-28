package it.spaghettisource.navaltrader.ui.model;

import java.util.ArrayList;
import java.util.List;

import it.spaghettisource.navaltrader.game.model.Ship;

public class BuyShipTableRow {

	private String model;
	private String shipClass;	
	private double price;	
	private int hull;	
	private int maxTeu;		
	private int maxDwt;		
	private int maxFuel;		
	private int maxSpeed;	
	private double operatingCost;
	
	public BuyShipTableRow(String shipClass, String type, double price, int hull, int maxDwt,int maxTeu, double operatingCost, int maxFuel, int maxSpeed) {
		super();
		this.shipClass = shipClass;
		this.model = type;
		this.price = price;
		this.hull = hull;
		this.maxDwt = maxDwt;
		this.maxTeu = maxTeu;
		this.maxFuel = maxFuel;
		this.maxSpeed = maxSpeed;
		this.operatingCost = operatingCost;
	}

	public String getShipClass() {
		return shipClass;
	}

	public void setShipClass(String shipClass) {
		this.shipClass = shipClass;
	}

	public String getModel() {
		return model;
	}

	public double getPrice() {
		return price;
	}

	public int getHull() {
		return hull;
	}

	public int getMaxDwt() {
		return maxDwt;
	}

	public int getMaxTeu() {
		return maxTeu;
	}

	public int getMaxFuel() {
		return maxFuel;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}
	
	public double getOperatingCost() {
		return operatingCost;
	}

	public static List<BuyShipTableRow> mapData(List<Ship> listOfShip){
		
		List<BuyShipTableRow> LoanTableRow = new ArrayList<BuyShipTableRow>();
		for (Ship ship : listOfShip) {
			LoanTableRow.add(new BuyShipTableRow(ship.getShipClass(), ship.getModel(), ship.getBasePrice(), ship.getHull(), ship.getMaxDwt(), ship.getMaxTeu(), ship.getOperatingCost(), ship.getMaxFuel(), ship.getMaxSpeed()));
		}
		
		return LoanTableRow;
	}
	
}
