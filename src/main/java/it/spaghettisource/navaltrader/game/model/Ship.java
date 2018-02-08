package it.spaghettisource.navaltrader.game.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.loop.Entity;
import it.spaghettisource.navaltrader.geometry.Mathematic;
import it.spaghettisource.navaltrader.geometry.Point;
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
	public static final String SHIP_STATUS_LOADING = "loading";	


	private String status;
	private double waitingTimeInHours; //used	to manage the time to stay in particualr status	
	
	private Point shipPosition;	
	private NavigationRoute navigationRoute;
	
	private String name;	
	private String model;
	private String shipClass;	
	private Port dockedPort;
	
	private Finance finance;
	private double basePrice;	
	private double operatingCost;
	
	private int hull;	
	private int dwt;
	private int maxDwt;	
	private int teu;		
	private int maxTeu;	
	private double fuelConsumptionIndexA;
	private double fuelConsumptionIndexB;	
	private int fuel;	//TODO convert to double for the correct calculation	
	private int maxFuel;	
	private int speed;	
	private int maxSpeed;	
	

	
	private List<TransportContract> transportContracts;
	
	
	public Ship(String shipClass, String model, int hull, int maxDwt,int maxTeu, int maxFuel,double fuelConsumptionIndexA, double fuelConsumptionIndexB,  double operatingCost, int maxSpeed, double basePrice) {
		
		this.shipClass = shipClass;
		this.model = model;

		this.maxDwt = maxDwt;
		this.maxTeu = maxTeu;
		this.maxFuel = maxFuel;
		this.fuelConsumptionIndexA = fuelConsumptionIndexA;
		this.fuelConsumptionIndexB = fuelConsumptionIndexB;
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
		
		this.status = SHIP_STATUS_DOCKED;
		waitingTimeInHours = 0;
		
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

	public Port getDockedPort() {
		return dockedPort;
	}

	public void setDockedPort(Port dockedPort) {
		this.dockedPort = dockedPort;
		this.shipPosition = new Point(dockedPort.getCooridnate().getX(), dockedPort.getCooridnate().getY()) ;
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
	
	public double getFuelConsumptionPerHour(int speed) {
		return Mathematic.powBy2(speed)*fuelConsumptionIndexA + speed*fuelConsumptionIndexB;
	}
	
	public double getFuelConsumptionPerDistance(int speed,int distance) {
		return (distance/speed)*getFuelConsumptionPerHour(speed);
	}	
	
	public double getFuelConsumptionIndexA() {
		return fuelConsumptionIndexA;
	}

	public double getFuelConsumptionIndexB() {
		return fuelConsumptionIndexB;
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

	/**
	 * prepare the ship to be loaded
	 * 
	 * @param speed
	 * @param route
	 */
	public void loadShipAndPrepareToNavigate(int navigationSpeed, Route route) {
		log.debug("ship :"+name+" strat loading operations");
		speed = navigationSpeed;
		navigationRoute = new NavigationRoute(speed, route);
		status = SHIP_STATUS_LOADING;
		
		waitingTimeInHours = 24;	//TODO calculate in function of the loaded TEU amount to time to load and unload, consider 6,086 TEU - was 2.5 to 3 days
		InboundEventQueue.getInstance().put(new Event(EventType.SHIP_STATUS_CHANGE_EVENT,this));	
		
	}
	
	private void loadShip(double hourPassed) {
		waitingTimeInHours = waitingTimeInHours - hourPassed;
		if(waitingTimeInHours<0) {
			log.debug("ship :"+name+" completed loading start navigation");
			status = SHIP_STATUS_NAVIGATION;
			dockedPort = null;
			InboundEventQueue.getInstance().put(new Event(EventType.SHIP_STATUS_CHANGE_EVENT,this));			
		}
	}
	
	private void navigation(double hourPassed) {
		
		//TODO implement the damage of the ship
		fuel -= getFuelConsumptionPerHour(navigationRoute.getSpeed())*hourPassed;
		shipPosition = navigationRoute.navigate(hourPassed);
		
		if(navigationRoute.isArrivedAtDestination()) {

			setDockedPort(navigationRoute.getDestinationPort());	//this set also final coordinate of the ship
			waitingTimeInHours = 4;		//set the docking time
			navigationRoute = null;
			status = SHIP_STATUS_DOCKING;
			InboundEventQueue.getInstance().put(new Event(EventType.SHIP_STATUS_CHANGE_EVENT,this));					
		}
		
		
	}
	
	
	@Override	
	public void update(int minutsPassed, boolean isNewDay, boolean isNewWeek, boolean isNewMonth) {

		if(SHIP_STATUS_DOCKED.equals(status)) {
			
		}else if(SHIP_STATUS_LOADING.equals(status)) {
		
			loadShip(minutsPassed/60);
			
		}else if(SHIP_STATUS_NAVIGATION.equals(status)) {
			
			navigation(minutsPassed/60);
			
		}else if(SHIP_STATUS_DOCKING.equals(status)) {
			//TODO make the docking action
			
		} 
		
		
			
	}
	

				
}
