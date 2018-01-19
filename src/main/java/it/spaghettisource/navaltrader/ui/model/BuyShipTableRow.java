package it.spaghettisource.navaltrader.ui.model;

import java.util.ArrayList;
import java.util.List;

import it.spaghettisource.navaltrader.game.model.Ship;

public class BuyShipTableRow {

	private String type;
	private double price;	
	private double hull;	
	private int cargoSpace;		
	private double maxFuel;		
	private double maxSpeed;	
	
	public BuyShipTableRow(String type, double price, double hull, int cargoSpace, double maxFuel, double maxSpeed) {
		super();
		this.type = type;
		this.price = price;
		this.hull = hull;
		this.cargoSpace = cargoSpace;
		this.maxFuel = maxFuel;
		this.maxSpeed = maxSpeed;
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

	public double getMaxFuel() {
		return maxFuel;
	}

	public double getMaxSpeed() {
		return maxSpeed;
	}


	public static List<BuyShipTableRow> mapData(List<Ship> listOfShip){
		
		List<BuyShipTableRow> LoanTableRow = new ArrayList<BuyShipTableRow>();
		for (Ship ship : listOfShip) {
			LoanTableRow.add(new BuyShipTableRow(ship.getType(), ship.getBasePrice(), ship.getHull(), ship.getCargoSpace(), ship.getMaxFuel(), ship.getMaxSpeed()));
		}
		
		return LoanTableRow;
	}
	
}
