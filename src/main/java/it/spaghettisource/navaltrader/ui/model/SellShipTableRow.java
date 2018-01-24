package it.spaghettisource.navaltrader.ui.model;

import java.util.ArrayList;
import java.util.List;

import it.spaghettisource.navaltrader.game.model.Ship;

public class SellShipTableRow {

	private String name;
	private String type;
	private double price;	
	private String status;	
	private int hull;	
	private int cargoSpace;		
	private int teu;		
	private String actualFuel;	
	private double operatingCost;	
	
	public SellShipTableRow(String name,String type,String status, double price, double operatingCost,int hull, int cargoSpace, int teu, String actualFuel) {
		super();
		this.name = name;
		this.status = status;
		this.type = type;
		this.price = price;
		this.hull = hull;
		this.cargoSpace = cargoSpace;
		this.teu = teu;
		this.actualFuel = actualFuel;
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
	
	public int getTeu() {
		return teu;
	}

	public String getStatus() {
		return status;
	}

	public String getActualFuel() {
		return actualFuel;
	}
	
	public String getName() {
		return name;
	}

	public double getOperatingCost() {
		return operatingCost;
	}

	public static List<SellShipTableRow> mapData(List<Ship> listOfShip){
		
		List<SellShipTableRow> LoanTableRow = new ArrayList<SellShipTableRow>();
		for (Ship ship : listOfShip) {
			LoanTableRow.add(mapData(ship));
		}
		
		return LoanTableRow;
	}
	
	public static SellShipTableRow mapData(Ship ship){
		return new SellShipTableRow(ship.getName(), ship.getType(),ship.getStatus(), ship.getBasePrice(), ship.getOperatingCost(), ship.getHull(), ship.getCargoSpace(),ship.getTeu(), 
									ship.getFuel()+"/"+ship.getMaxFuel());
	}	
	
	public boolean equals(Object obj){
		if(obj==null){
			return false;
		}else if(!(obj instanceof SellShipTableRow)){
			return false;
		}else{
			return name.equals(((SellShipTableRow)obj).getName());
		}	
	}
	
}
