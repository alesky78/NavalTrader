package it.spaghettisource.navaltrader.ui.model;

import java.util.ArrayList;
import java.util.List;

import it.spaghettisource.navaltrader.game.model.Ship;

public class ShipListTableRow {

	private String name;
	private String type;
	private String status;	
	private int hull;	
	private String actualDwt;	
	private String actualTeu;		
	private String actualFuel;			
	
	public ShipListTableRow(String name,String type,String status, int hull, String actualDwt,String actualTeu, String actualFuel) {
		super();
		this.name = name;
		this.status = status;
		this.type = type;

		this.hull = hull;
		this.actualDwt = actualDwt;
		this.actualTeu = actualTeu;
		this.actualFuel = actualFuel;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
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
		return new ShipListTableRow(ship.getName(), ship.getType(),ship.getStatus(), ship.getHull(), 
									ship.getDwt()+"/"+ship.getMaxDwt(),
									ship.getTeu()+"/"+ship.getMaxTeu(), 
									ship.getFuel()+"/"+ship.getMaxFuel());
	}	
	

	
}
