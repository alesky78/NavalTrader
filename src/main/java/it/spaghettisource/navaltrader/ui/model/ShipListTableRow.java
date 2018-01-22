package it.spaghettisource.navaltrader.ui.model;

import java.util.ArrayList;
import java.util.List;

import it.spaghettisource.navaltrader.game.model.Ship;

public class ShipListTableRow {

	private String name;
	private String type;
	private double price;	
	private String status;	
	private double hull;	
	private int cargoSpace;		
	private double actualFuel;			
	
	public ShipListTableRow(String name,String type,String status, double price, double hull, int cargoSpace, double actualFuel) {
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

	public static List<ShipListTableRow> mapData(List<Ship> listOfShip){
		
		List<ShipListTableRow> LoanTableRow = new ArrayList<ShipListTableRow>();
		for (Ship ship : listOfShip) {
			LoanTableRow.add(mapData(ship));
		}
		
		return LoanTableRow;
	}
	
	public static ShipListTableRow mapData(Ship ship){
		return new ShipListTableRow(ship.getName(), ship.getType(),ship.getStatus(), ship.getBasePrice(), ship.getHull(), ship.getCargoSpace(), ship.getActualFuel());
	}	
	
	public boolean equals(Object obj){
		if(obj==null){
			return false;
		}else if(!(obj instanceof ShipListTableRow)){
			return false;
		}else{
			return name.equals(((ShipListTableRow)obj).getName());
		}	
	}	
	
}
