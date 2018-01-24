package it.spaghettisource.navaltrader.ui.model;

import java.util.ArrayList;
import java.util.List;

import it.spaghettisource.navaltrader.game.model.Ship;

public class ShipListTableRow {

	private String name;
	private String shipClass;		
	private String model;
	private String status;	
	private int hull;	
	private String actualDwt;	
	private String actualTeu;		
	private String actualFuel;			
	
	public ShipListTableRow(String shipClass, String name,String model,String status, int hull, String actualDwt,String actualTeu, String actualFuel) {
		super();
		this.name = name;
		this.shipClass = shipClass;		
		this.status = status;
		this.model = model;

		this.hull = hull;
		this.actualDwt = actualDwt;
		this.actualTeu = actualTeu;
		this.actualFuel = actualFuel;
	}

	public String getName() {
		return name;
	}

	public String getShipClass() {
		return shipClass;
	}

	public String getModel() {
		return model;
	}

	public String getStatus() {
		return status;
	}

	public int getHull() {
		return hull;
	}

	public String getActualDwt() {
		return actualDwt;
	}

	public String getActualTeu() {
		return actualTeu;
	}

	public String getActualFuel() {
		return actualFuel;
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
		return new ShipListTableRow(ship.getShipClass(), ship.getName(), ship.getModel(),ship.getStatus(), ship.getHull(), 
									ship.getDwt()+"/"+ship.getMaxDwt(),
									ship.getTeu()+"/"+ship.getMaxTeu(), 
									ship.getFuel()+"/"+ship.getMaxFuel());
	}	
	

	
}
