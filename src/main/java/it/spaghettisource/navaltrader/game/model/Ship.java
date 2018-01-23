package it.spaghettisource.navaltrader.game.model;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.loop.Entity;
import it.spaghettisource.navaltrader.ui.event.Event;
import it.spaghettisource.navaltrader.ui.event.EventType;
import it.spaghettisource.navaltrader.ui.event.InboundEventQueue;

public class Ship implements Entity{

	private static Log log = LogFactory.getLog(Ship.class.getName());

	//ship status
	public static final String SHIP_STATUS_DOCKED = "docked";
	public static final String SHIP_STATUS_REPAIRING = "repairing";
	public static final String SHIP_STATUS_NAVIGATION = "navigation";
	public static final String SHIP_STATUS_DOCKING = "docking";

	//ship type
	private static final String SHIP_TYPE_OLD_LITTLE = "old little";	
	private static final String SHIP_TYPE_OLD_LARGE = "old large";	
	private static final String SHIP_TYPE_NORMAL_LITTLE = "normal little";
	private static final String SHIP_TYPE_NORMAL_LARGE = "normal large";
	private static final String SHIP_TYPE_HITECH_LITTLE = "HiTech little";
	private static final String SHIP_TYPE_HITECH_LARGE = "HiTech large";		
	private static final String SHIP_TYPE_HITECH_HUGE = "HiTech huge";	
	
	//ship cargo
	private static final int SHIP_CARGO_LITTLE = 3000;	
	private static final int SHIP_CARGO_LARGE = 4000;
	private static final int SHIP_CARGO_HUGE = 6000;		
		
	//customized ship
	private static final Ship SHIP_OLD_LITTLE = 	new Ship(SHIP_TYPE_OLD_LITTLE	, 0.1,	 SHIP_CARGO_LITTLE	, 700, 3000,2000, 15,1000000);
	private static final Ship SHIP_OLD_LARGE = 	new Ship(SHIP_TYPE_OLD_LARGE	, 0.3,	 SHIP_CARGO_LARGE	, 700, 3500, 2000,15,2000000);	
	private static final Ship SHIP_NORMAL_LITTLE = new Ship(SHIP_TYPE_NORMAL_LITTLE, 0.5,	 SHIP_CARGO_LITTLE	, 700, 3500, 2000,17,4000000);
	private static final Ship SHIP_NORMAL_LARGE = 	new Ship(SHIP_TYPE_NORMAL_LARGE	, 0.7,	 SHIP_CARGO_LARGE	, 700, 4000, 2000,17,5000000);
	private static final Ship SHIP_HITECH_LITTLE = new Ship(SHIP_TYPE_HITECH_LITTLE, 0.8, SHIP_CARGO_LITTLE	, 700, 4000, 2000,21,10000000);
	private static final Ship SHIP_HITECH_LARGE = 	new Ship(SHIP_TYPE_HITECH_LARGE	, 1.0, SHIP_CARGO_LARGE	, 700, 5000, 2000,21,15000000);
	private static final Ship SHIP_HITECH_HUGE= 	new Ship(SHIP_TYPE_HITECH_HUGE	, 1.0, SHIP_CARGO_HUGE	, 700, 6000, 2000,21,25000000);	
	
	private static final Ship[] shipArray = new Ship[]{SHIP_OLD_LITTLE,SHIP_OLD_LARGE,SHIP_NORMAL_LITTLE,SHIP_NORMAL_LARGE,SHIP_HITECH_LITTLE,SHIP_HITECH_LARGE,SHIP_HITECH_HUGE};	
	private static double priceIndex = 1.0;
	
	
	private String type;
	private String name;	
	private Finance finance;
	
	private double basePrice;	
	
	private String status;
	private double hull;	
	
	private double operatingCost;
	
	private int cargoSpace;
	private int teu;		
	private double actualFuel;	
	private double maxFuel;	
	private double actualSpeed;	
	private double maxSpeed;	
	
	
	
	public Ship(String type, double hull, int cargoSpace,int teu, double maxFuel, double operatingCost, double maxSpeed, double basePrice) {
		this.type = type;

		this.status = SHIP_STATUS_DOCKED;
		this.cargoSpace = cargoSpace;
		this.teu = teu;
		this.maxFuel = maxFuel;
		this.maxSpeed = maxSpeed;
		this.hull = hull;		
		this.basePrice = basePrice;
		this.operatingCost = operatingCost;
		
		actualFuel = 0;		
		actualSpeed = 0;		
		name = "";		
	}
	
	public static Ship factoryShip(String type,String name){
		Ship modelShip = null;
		Ship newShip = null;
		
		//find ship to copy
		for (Ship ship : getListSellShip()) {
			if(ship.getType().equals(type)){
				modelShip = ship;
			}
		}
		newShip = new Ship(modelShip.getType(), modelShip.getHull(), modelShip.getCargoSpace(), modelShip.getTeu(),  modelShip.getMaxFuel(), modelShip.getOperatingCost(), modelShip.getMaxSpeed(),modelShip.getBasePrice());
		newShip.setName(name);
		Finance finance = new Finance();
		finance.init();
		newShip.setFinance(finance);
		return newShip;
	}
	
	public static List<Ship> getListSellShip(){
		return Arrays.asList(shipArray);
	}

	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Finance getFinance() {
		return finance;
	}

	public void setFinance(Finance finance) {
		this.finance = finance;
	}

	public double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getHull() {
		return hull;
	}

	public void setHull(double hull) {
		this.hull = hull;
	}
	
	public void addHull(double toAdd) {
		this.hull = hull + toAdd;
		InboundEventQueue.getInstance().put(new Event(EventType.SHIP_HULL_CHANGE_EVENT,this));		
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

	
	public double getOperatingCost() {
		return operatingCost;
	}

	public void setOperatingCost(double operatingCost) {
		this.operatingCost = operatingCost;
	}

	public double getActualFuel() {
		return actualFuel;
	}

	public void setActualFuel(double actualFuel) {
		this.actualFuel = actualFuel;
	}
	
	public void addFuel(double toAdd) {
		this.actualFuel = actualFuel + toAdd;
		InboundEventQueue.getInstance().put(new Event(EventType.SHIP_FUEL_CHANGE_EVENT,this));
	}	

	public double getMaxFuel() {
		return maxFuel;
	}

	public void setMaxFuel(double maxFuel) {
		this.maxFuel = maxFuel;
	}

	public double getActualSpeed() {
		return actualSpeed;
	}

	public void setActualSpeed(double actualSpeed) {
		this.actualSpeed = actualSpeed;
	}

	public double getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	
	@Override	
	public void update(int minutsPassed) {

		
			
			if(hull > 1.0){
				hull = 0;
				InboundEventQueue.getInstance().put(new Event(EventType.SHIP_HULL_CHANGE_EVENT,this));	
			}
			else{
				hull = hull + 0.01;				
				InboundEventQueue.getInstance().put(new Event(EventType.SHIP_HULL_CHANGE_EVENT,this));	
			}
			
			if(actualFuel>=maxFuel){
				actualFuel = 0;
				InboundEventQueue.getInstance().put(new Event(EventType.SHIP_FUEL_CHANGE_EVENT,this));
			}else{
				actualFuel = actualFuel + 100;				
				InboundEventQueue.getInstance().put(new Event(EventType.SHIP_FUEL_CHANGE_EVENT,this));
			}			
			
		
		
	}
	
				
}
