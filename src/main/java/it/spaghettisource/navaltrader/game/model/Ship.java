package it.spaghettisource.navaltrader.game.model;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.loop.Entity;

public class Ship implements Entity{

	private static Log log = LogFactory.getLog(Ship.class.getName());

	//ship status
	public static final String SHIP_STATUS_IDLE = "idle";
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
	private static final Ship SHIP_OLD_LITTLE = 	new Ship(SHIP_TYPE_OLD_LITTLE	, 60.0,	 SHIP_CARGO_LITTLE	, 3000, 15,1000000);
	private static final Ship SHIP_OLD_LARGE = 	new Ship(SHIP_TYPE_OLD_LARGE	, 60.0,	 SHIP_CARGO_LARGE	, 3500, 15,2000000);	
	private static final Ship SHIP_NORMAL_LITTLE = new Ship(SHIP_TYPE_NORMAL_LITTLE, 80.0,	 SHIP_CARGO_LITTLE	, 3500, 17,4000000);
	private static final Ship SHIP_NORMAL_LARGE = 	new Ship(SHIP_TYPE_NORMAL_LARGE	, 80.0,	 SHIP_CARGO_LARGE	, 4000, 17,5000000);
	private static final Ship SHIP_HITECH_LITTLE = new Ship(SHIP_TYPE_HITECH_LITTLE, 100.0, SHIP_CARGO_LITTLE	, 4000, 21,10000000);
	private static final Ship SHIP_HITECH_LARGE = 	new Ship(SHIP_TYPE_HITECH_LARGE	, 100.0, SHIP_CARGO_LARGE	, 5000, 21,15000000);
	private static final Ship SHIP_HITECH_HUGE= 	new Ship(SHIP_TYPE_HITECH_HUGE	, 100.0, SHIP_CARGO_HUGE	, 6000, 21,25000000);	
	
	private static final Ship[] shipArray = new Ship[]{SHIP_OLD_LITTLE,SHIP_OLD_LARGE,SHIP_NORMAL_LITTLE,SHIP_NORMAL_LARGE,SHIP_HITECH_LITTLE,SHIP_HITECH_LARGE,SHIP_HITECH_HUGE};	
	private static double priceIndex = 1.0;
	
	
	private String type;
	private String name;	
	private Finance finance;
	
	private double price;	
	
	private String status;
	
	private double hull;	
	private int cargoSpace;	
	private double actualFuel;	
	private double maxFuel;	
	private double actualSpeed;	
	private double maxSpeed;	
	
	
	
	public Ship(String type, double hull, int cargoSpace, double maxFuel,  double maxSpeed, double price) {
		this.type = type;

		this.status = SHIP_STATUS_IDLE;
		this.cargoSpace = cargoSpace;
		this.maxFuel = maxFuel;
		this.maxSpeed = maxSpeed;
		this.hull = hull;		
		this.price = price;
		
		actualFuel = 0;		
		actualSpeed = 0;		
		name = "";		
		finance = new Finance();
		finance.init();		
	}
	
	public List<Ship> getListSellShip(){
		return Arrays.asList(shipArray);
	}


	public Ship(String ShipName) {
		name = ShipName;
		finance = new Finance();
		finance.init();
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
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

	public int getCargoSpace() {
		return cargoSpace;
	}

	public void setCargoSpace(int cargoSpace) {
		this.cargoSpace = cargoSpace;
	}

	public double getActualFuel() {
		return actualFuel;
	}

	public void setActualFuel(double actualFuel) {
		this.actualFuel = actualFuel;
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
		// TODO business logic for ship
		
	}
	
				
}
