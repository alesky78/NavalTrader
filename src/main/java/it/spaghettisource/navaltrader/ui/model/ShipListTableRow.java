package it.spaghettisource.navaltrader.ui.model;

import java.util.ArrayList;
import java.util.List;

import it.spaghettisource.navaltrader.game.model.Port;
import it.spaghettisource.navaltrader.game.model.Ship;

public class ShipListTableRow {

	private String name;
	private Port dockedPort;	
	private String shipClass;		
	private String model;
	private String status;	
	private double operatingCost;
	private int hull;	
	private String actualDwt;	
	private String actualTeu;		
	private String actualFuel;			
	
	public ShipListTableRow(String shipClass, String name,String model,Port dockedPort,String status, double operatingCost, int hull, String actualDwt,String actualTeu, String actualFuel) {
		super();
		this.name = name;
		this.dockedPort = dockedPort;
		this.shipClass = shipClass;		
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
		return dockedPort;
	}

	public String getDockedPortName() {
		if(dockedPort==null) {
			return "";
		}
		return dockedPort.getName();
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
	
	public static ShipListTableRow mapData(Ship ship){
		return new ShipListTableRow(ship.getShipClass(), ship.getName(), ship.getModel(), ship.getDockedPort(), ship.getStatus(), ship.getOperatingCost(), ship.getHull(), 
									ship.getDwt()+"/"+ship.getMaxDwt(),
									ship.getTeu()+"/"+ship.getMaxTeu(), 
									ship.getFuel()+"/"+ship.getMaxFuel());
	}	
	

	
}
