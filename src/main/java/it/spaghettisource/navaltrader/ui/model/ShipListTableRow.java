package it.spaghettisource.navaltrader.ui.model;

import java.util.ArrayList;
import java.util.List;

import it.spaghettisource.navaltrader.game.model.Ship;

public class ShipListTableRow {

	private String name;
	private String type;
	private String status;	
	private int hull;	
	private int cargoSpace;	
	private int teu;		
	private double actualFuel;			
	
	public ShipListTableRow(String name,String type,String status, int hull, int cargoSpace,int teu, double actualFuel) {
		super();
		this.name = name;
		this.status = status;
		this.type = type;

		this.hull = hull;
		this.cargoSpace = cargoSpace;
		this.teu = teu;
		this.actualFuel = actualFuel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getHull() {
		return hull;
	}

	public void setHull(int hull) {
		this.hull = hull;
	}

	public int getCargoSpace() {
		return cargoSpace;
	}

	public void setCargoSpace(int cargoSpace) {
		this.cargoSpace = cargoSpace;
	}

	public int getTeu() {
		return teu;
	}

	public void setTeu(int teu) {
		this.teu = teu;
	}

	public double getActualFuel() {
		return actualFuel;
	}

	public void setActualFuel(double actualFuel) {
		this.actualFuel = actualFuel;
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

	public static List<ShipListTableRow> mapData(List<Ship> listOfShip){
		
		List<ShipListTableRow> LoanTableRow = new ArrayList<ShipListTableRow>();
		for (Ship ship : listOfShip) {
			LoanTableRow.add(mapData(ship));
		}
		
		return LoanTableRow;
	}
	
	public static ShipListTableRow mapData(Ship ship){
		return new ShipListTableRow(ship.getName(), ship.getType(),ship.getStatus(), ship.getHull(), ship.getCargoSpace(),ship.getTeu(), ship.getActualFuel());
	}	
	

	
}
