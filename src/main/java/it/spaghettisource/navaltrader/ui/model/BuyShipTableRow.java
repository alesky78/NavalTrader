package it.spaghettisource.navaltrader.ui.model;

import java.util.ArrayList;
import java.util.List;

import it.spaghettisource.navaltrader.game.model.Ship;

public class BuyShipTableRow {

	private String type;
	private double price;	
	private double hull;	
	private int teu;		
	private int cargoSpace;		
	private double maxFuel;		
	private double maxSpeed;	
	private double operatingCost;
	
	public BuyShipTableRow(String type, double price, double hull, int cargoSpace,int teu, double operatingCost, double maxFuel, double maxSpeed) {
		super();
		this.type = type;
		this.price = price;
		this.hull = hull;
		this.cargoSpace = cargoSpace;
		this.teu = teu;
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

	public double getHull() {
		return hull;
	}

	public int getCargoSpace() {
		return cargoSpace;
	}

	public int getTeu() {
		return teu;
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
			LoanTableRow.add(new BuyShipTableRow(ship.getType(), ship.getBasePrice(), ship.getHull(), ship.getCargoSpace(), ship.getTeu(), ship.getOperatingCost(), ship.getMaxFuel(), ship.getMaxSpeed()));
		}
		
		return LoanTableRow;
	}
	
}
