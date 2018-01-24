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

	//ship cargo
	private static final int SHIP_CARGO_LITTLE = 3000;	
	private static final int SHIP_CARGO_LARGE = 4000;
	private static final int SHIP_CARGO_HUGE = 6000;		
		
	//customized ship
	private static final Ship SHIP_OLD_LITTLE = 	new Ship("very old"	, 	50,	 SHIP_CARGO_LITTLE	, 700, 3000, 2000, 15, 1000000);
	private static final Ship SHIP_OLD_LARGE = 		new Ship("very old", 	50,	 SHIP_CARGO_LARGE	, 700, 3500, 2000, 15, 2000000);	
	private static final Ship SHIP_NORMAL_LITTLE = 	new Ship("old", 		60,	 SHIP_CARGO_LITTLE	, 700, 3500, 2000, 17, 4000000);
	private static final Ship SHIP_NORMAL_LARGE = 	new Ship("old", 		70,	 SHIP_CARGO_LARGE	, 700, 4000, 2000, 17, 5000000);
	private static final Ship SHIP_HITECH_LITTLE = 	new Ship("old", 		80, SHIP_CARGO_LITTLE	, 700, 4000, 2000, 21, 10000000);
	private static final Ship SHIP_HITECH_LARGE = 	new Ship("new", 		90, SHIP_CARGO_LARGE	, 700, 5000, 2000, 21, 15000000);
	private static final Ship SHIP_HITECH_HUGE= 	new Ship("new", 		100, SHIP_CARGO_HUGE	, 700, 6000, 2000, 21, 25000000);	
	
	private static final Ship[] shipArray = new Ship[]{SHIP_OLD_LITTLE,SHIP_OLD_LARGE,SHIP_NORMAL_LITTLE,SHIP_NORMAL_LARGE,SHIP_HITECH_LITTLE,SHIP_HITECH_LARGE,SHIP_HITECH_HUGE};	
	private static double priceIndex = 1.0;
	
	
	private String type;	
	
	//private String shipClass; --> PANAMAX ETC....	
	//https://en.wikipedia.org/wiki/Container_ship
	//https://en.wikipedia.org/wiki/Cargo_ship
	//http://maritime-connector.com/wiki/ship-sizes/
	
	
	private String name;	
	private Finance finance;
	
	private double basePrice;	
	
	private String status;
	private int hull;	
	
	private double operatingCost;
	
	private int cargoSpace;	//change variable name Deadweight tonnage 
	private int teu;		
	//private int maxTeu;	
	private int fuel;	
	private int maxFuel;	
	private double speed;	
	private double maxSpeed;	
	
	
	
	public Ship(String type, int hull, int cargoSpace,int teu, int maxFuel, double operatingCost, double maxSpeed, double basePrice) {
		this.type = type;

		this.status = SHIP_STATUS_DOCKED;
		this.cargoSpace = cargoSpace;
		this.teu = teu;
		this.maxFuel = maxFuel;
		this.maxSpeed = maxSpeed;
		this.hull = hull;		
		this.basePrice = basePrice;
		this.operatingCost = operatingCost;
		
		fuel = 0;		
		speed = 0;		
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

	public int getHull() {
		return hull;
	}

	public void setHull(int hull) {
		this.hull = hull;
	}
	
	public void addHull(int toAdd) {
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

	public int getFuel() {
		return fuel;
	}

	public void setFuel(int fuel) {
		this.fuel = fuel;
	}

	public void addFuel(int toAdd) {
		this.fuel = fuel + toAdd;
		InboundEventQueue.getInstance().put(new Event(EventType.SHIP_FUEL_CHANGE_EVENT,this));
	}	
	
	
	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public int getMaxFuel() {
		return maxFuel;
	}

	public void setMaxFuel(int maxFuel) {
		this.maxFuel = maxFuel;
	}

	public double getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	
	@Override	
	public void update(int minutsPassed) {

		
				
		
	}
	
				
}
