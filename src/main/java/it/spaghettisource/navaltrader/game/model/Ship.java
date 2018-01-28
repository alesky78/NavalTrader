package it.spaghettisource.navaltrader.game.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.loop.Entity;

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
	private static final Ship SHIP_OLD_LITTLE = 	new Ship("Small feeder", "nsr-32", 	50,	 SHIP_CARGO_LITTLE	, 1000,  3000, 2000, 15, 1000000);
	private static final Ship SHIP_OLD_LARGE = 		new Ship("Feeder", 		 "xModel", 	50,	 SHIP_CARGO_LARGE	, 2000,  3500, 2000, 15, 2000000);	
	private static final Ship SHIP_NORMAL_LITTLE = 	new Ship("Feedermax", 	 "SS32", 	60,	 SHIP_CARGO_LITTLE	, 3000,  3500, 2000, 17, 4000000);
	private static final Ship SHIP_NORMAL_LARGE = 	new Ship("Panamax",		 "FastS", 	70,	 SHIP_CARGO_LARGE	, 5100,  4000, 2000, 17, 5000000);
	private static final Ship SHIP_HITECH_LITTLE = 	new Ship("Post-Panamax", "FastSS",	80, SHIP_CARGO_LITTLE	, 10000, 4000, 2000, 21, 10000000);
	private static final Ship SHIP_HITECH_LARGE = 	new Ship("New Panamax",	 "tornato", 90, SHIP_CARGO_LARGE	, 14500, 5000, 2000, 21, 15000000);
	private static final Ship SHIP_HITECH_HUGE= 	new Ship("ULCV",		 "spaceX", 	100, SHIP_CARGO_HUGE	, 18270, 6000, 2000, 21, 25000000);	
	
	private static final Ship[] shipArray = new Ship[]{SHIP_OLD_LITTLE,SHIP_OLD_LARGE,SHIP_NORMAL_LITTLE,SHIP_NORMAL_LARGE,SHIP_HITECH_LITTLE,SHIP_HITECH_LARGE,SHIP_HITECH_HUGE};	
	private static double priceIndex = 1.0;

	private String name;	
	private String model;
	private String status;
	private String shipClass;	
	private String port;
	
	private Finance finance;
	private double basePrice;	
	private double operatingCost;
	
	private int hull;	
	private int dwt;
	private int maxDwt;	
	private int teu;		
	private int maxTeu;	
	private int fuel;	
	private int maxFuel;	
	private int speed;	
	private int maxSpeed;	
	
	private List<TransportContract> transportContracts;
	
	
	
	public Ship(String shipClass, String model, int hull, int maxDwt,int maxTeu, int maxFuel, double operatingCost, int maxSpeed, double basePrice) {
		
		this.shipClass = shipClass;
		this.model = model;

		this.status = SHIP_STATUS_DOCKED;
		this.maxDwt = maxDwt;
		this.maxTeu = maxTeu;
		this.maxFuel = maxFuel;
		this.maxSpeed = maxSpeed;
		this.hull = hull;		
		this.basePrice = basePrice;
		this.operatingCost = operatingCost;
		
		dwt = 0; 
		teu = 0;
		fuel = 0;		
		speed = 0;		
		name = "";		
		finance = new Finance();
		transportContracts = new ArrayList<TransportContract>();
		
	}
	
	public static Ship factoryShip(String model,String name,String port){
		Ship modelShip = null;
		Ship newShip = null;
		
		//find ship to copy
		for (Ship ship : getListSellShip()) {
			if(ship.getModel().equals(model)){
				modelShip = ship;
			}
		}
		newShip = new Ship(modelShip.getShipClass(), modelShip.getModel(), modelShip.getHull(), modelShip.getMaxDwt(), modelShip.getMaxTeu(),  modelShip.getMaxFuel(), modelShip.getOperatingCost(), modelShip.getMaxSpeed(),modelShip.getBasePrice());
		newShip.setName(name);
		newShip.setPort(port);
		
		return newShip;
	}
	
	public static List<Ship> getListSellShip(){
		return Arrays.asList(shipArray);
	}

	
	
	
	public List<TransportContract> getTransportContracts() {
		return transportContracts;
	}

	public void addContract(TransportContract contract) {
		transportContracts.add(contract);
		teu += contract.getTeu();
		dwt += contract.getTeu()*contract.getDwtPerTeu();
	}

	public String getShipClass() {
		return shipClass;
	}

	public void setShipClass(String shipClass) {
		this.shipClass = shipClass;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
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
	}	

	public int getDwt() {
		return dwt;
	}

	public void setDwt(int dwt) {
		this.dwt = dwt;
	}
	
	public int getAcceptedDwt() {
		return maxDwt-dwt;
	}	

	public int getMaxDwt() {
		return maxDwt;
	}

	public void setMaxDwt(int maxDwt) {
		this.maxDwt = maxDwt;
	}

	public int getTeu() {
		return teu;
	}

	public void setTeu(int teu) {
		this.teu = teu;
	}

	public int getAcceptedTeu() {
		return maxTeu-teu;
	}	
	
	public int getMaxTeu() {
		return maxTeu;
	}

	public void setMaxTeu(int maxTeu) {
		this.maxTeu = maxTeu;
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
	}	
	
	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getMaxFuel() {
		return maxFuel;
	}

	public void setMaxFuel(int maxFuel) {
		this.maxFuel = maxFuel;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	
	@Override	
	public void update(int minutsPassed, boolean isNewDay, boolean isNewWeek, boolean isNewMonth) {

			
	}
	
				
}
