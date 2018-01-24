package it.spaghettisource.navaltrader.ui.model;

import java.util.ArrayList;
import java.util.List;

import it.spaghettisource.navaltrader.game.model.Ship;

public class BuyShipTableRow {

	private String type;
	private double price;	
	private int hull;	
	private int maxTeu;		
	private int cargoSpace;		
	private double maxFuel;		
	private double maxSpeed;	
	private double operatingCost;
	
	public BuyShipTableRow(String type, double price, int hull, int cargoSpace,int maxTeu, double operatingCost, double maxFuel, double maxSpeed) {
		super();
		this.type = type;
		this.price = price;
		this.hull = hull;
		this.cargoSpace = cargoSpace;
		this.maxTeu = maxTeu;
		this.maxFuel = maxFuel;
		this.maxSpeed = maxSpeed;
		this.operatingCost = operatingCost;
	}

	public String getType() {
		return type;
	}

	public double getPrice() {
		return price;
	}

	public int getHull() {
		return hull;
	}

	public int getCargoSpace() {
		return cargoSpace;
	}

	public int getMaxTeu() {
		return maxTeu;
	}

	public double getMaxFuel() {
		return maxFuel;
	}

	public double getMaxSpeed() {
		return maxSpeed;
	}
	
	public double getOperatingCost() {
		return operatingCost;
	}

	public static List<BuyShipTableRow> mapData(List<Ship> listOfShip){
		
		List<BuyShipTableRow> LoanTableRow = new ArrayList<BuyShipTableRow>();
		for (Ship ship : listOfShip) {
			LoanTableRow.add(new BuyShipTableRow(ship.getType(), ship.getBasePrice(), ship.getHull(), ship.getCargoSpace(), ship.getMaxTeu(), ship.getOperatingCost(), ship.getMaxFuel(), ship.getMaxSpeed()));
		}
		
		return LoanTableRow;
	}
	
}
