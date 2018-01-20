package it.spaghettisource.navaltrader.ui.model;

import java.util.ArrayList;
import java.util.List;

import it.spaghettisource.navaltrader.game.model.Ship;

public class SellShipTableRow {

	private String name;
	private String type;
	private double price;	
	private String status;	
	private double hull;	
	private int cargoSpace;		
	private double actualFuel;			
	
	public SellShipTableRow(String name,String type,String status, double price, double hull, int cargoSpace, double actualFuel) {
		super();
		this.name = name;
		this.status = status;
		this.type = type;
		this.price = price;
		this.hull = hull;
		this.cargoSpace = cargoSpace;
		this.actualFuel = actualFuel;
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

	public String getStatus() {
		return status;
	}

	public double getActualFuel() {
		return actualFuel;
	}
	
	public String getName() {
		return name;
	}

	public static List<SellShipTableRow> mapData(List<Ship> listOfShip){
		
		List<SellShipTableRow> LoanTableRow = new ArrayList<SellShipTableRow>();
		for (Ship ship : listOfShip) {
			LoanTableRow.add(new SellShipTableRow(ship.getName(), ship.getType(),ship.getStatus(), ship.getBasePrice(), ship.getHull(), ship.getCargoSpace(), ship.getActualFuel()));
		}
		
		return LoanTableRow;
	}
	
}
