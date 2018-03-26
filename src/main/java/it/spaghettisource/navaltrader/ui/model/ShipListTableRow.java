package it.spaghettisource.navaltrader.ui.model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import it.spaghettisource.navaltrader.game.model.Port;
import it.spaghettisource.navaltrader.game.model.Ship;

public class ShipListTableRow {

	private Ship ship;
	private String name;
	private String shipClassName;		
	private String model;
	private String status;	
	private double operatingCost;
	private int hull;	
	private String actualDwt;	
	private String actualTeu;		
	private String actualFuel;			
	
	public ShipListTableRow(Ship ship,String shipClassName, String name,String model,String status, double operatingCost, int hull, String actualDwt,String actualTeu, String actualFuel) {
		super();
		this.ship = ship;
		this.name = name;
		this.shipClassName = shipClassName;		
		this.status = status;
		this.model = model;
		this.operatingCost = operatingCost;

		this.hull = hull;
		this.actualDwt = actualDwt;
		this.actualTeu = actualTeu;
		this.actualFuel = actualFuel;
	}

	public String getName() {
		return name;
	}
	
	public Port getDockedPort() {
		return ship.getDockedPort();
	}
	
	public Ship getShip() {
		return ship;
	}

	public String getDockedPortName() {
		if(getDockedPort()==null) {
			return "";
		}
		return getDockedPort().getName();
	}

	public String getShipClassName() {
		return shipClassName;
	}

	public String getModel() {
		return model;
	}

	public String getStatus() {
		return status;
	}
	
	public double getOperatingCost() {
		return operatingCost;
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
	
	private static DecimalFormat format = new DecimalFormat(".#");
	
	public static ShipListTableRow mapData(Ship ship){
		return new ShipListTableRow(ship,ship.getShipClassName(), ship.getName(), ship.getModel(),  ship.getStatus(), ship.getOperatingCost(), ship.getHpPercentage(), 
									ship.getDwt()+"/"+ship.getMaxDwt(),
									ship.getTeu()+"/"+ship.getMaxTeu(), 
									format.format(ship.getFuel())+"/"+ship.getMaxFuel());
	}	
	

	
}
