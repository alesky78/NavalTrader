package it.spaghettisource.navaltrader.game.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.loop.Entity;
import it.spaghettisource.navaltrader.geometry.Angle;
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
	public static final String SHIP_STATUS_UNLOADING = "unloading";		


	private String status;
	private double waitingTimeInHours; //used	to manage the time to stay in particular status	

	private Company company;

	private Point position;	
	private NavigationRoute navigationRoute;
	private ProfitabilityRoute profitabilityRoute;

	private String name;	
	private String model;
	private int shipSize;	
	private String shipClassName;	
	private Port dockedPort;

	private Finance finance;
	private double basePrice;	
	private double operatingCost;

	private int hp;	
	private int maxHp;
	private double hpDamagePerNodeOfNavigation;
	private double hpDamagePerRoute;	

	private int dwt;
	private int maxDwt;	
	private int teu;
	private int teuToLoad;
	private int teuToUnload;				
	private int maxTeu;	

	private double fuelConsumptionIndexA;
	private double fuelConsumptionIndexB;	
	private double fuel;
	private double maxFuel;	

	private int speed;	
	private int maxSpeed;	

	private Angle shipAngle;		//used to draw the ship for the correct angulation	
	private double rotationSpeed = 1;			

	private List<TransportContract> transportContracts;


	public Ship(String shipClassName, String model, int shipSize, int hp, int maxHp, int maxDwt,int maxTeu, double maxFuel,double fuelConsumptionIndexA, double fuelConsumptionIndexB,  double operatingCost, int maxSpeed, double basePrice) {

		this.shipClassName = shipClassName;
		this.model = model;
		this.shipSize = shipSize;

		this.maxDwt = maxDwt;
		this.maxTeu = maxTeu;
		this.maxFuel = maxFuel;
		this.fuelConsumptionIndexA = fuelConsumptionIndexA;
		this.fuelConsumptionIndexB = fuelConsumptionIndexB;
		this.maxSpeed = maxSpeed;
		this.hp = hp;
		this.maxHp = maxHp;				
		this.basePrice = basePrice;
		this.operatingCost = operatingCost;

		dwt = 0; 
		teu = 0;
		teuToLoad = 0;		
		teuToUnload = 0;
		fuel = 0;		
		speed = 0;		
		hpDamagePerNodeOfNavigation = 0;	
		hpDamagePerRoute = 0;
		name = "";		
		finance = new Finance();
		transportContracts = new ArrayList<TransportContract>();

		this.status = SHIP_STATUS_DOCKED;
		waitingTimeInHours = 0;

		shipAngle = new Angle(0);

	}

	public void setCompany(Company company) {
		this.company = company;
	}	

	public Point getPosition() {
		return position;
	}

	public List<TransportContract> getTransportContracts() {
		return transportContracts;
	}

	public void addContract(TransportContract contract) {
		transportContracts.add(contract);
		teu += contract.getTeu();
		dwt += contract.getTeu()*contract.getDwtPerTeu();
		teuToLoad += contract.getTeu();
	}


	public void closeContracts(){

		List<TransportContract> toClose = new ArrayList<>();
		for (TransportContract transportContract : transportContracts) {
			if(transportContract.getDestinationPort().equals(dockedPort)){
				toClose.add(transportContract);
			}
		}

		teuToUnload = 0;

		if(!toClose.isEmpty()){
			double totalBudget = 0;
			double penaltyCharges = 0;			
			for (TransportContract transportContract : toClose) {
				//reset the Teu and the DWT
				teuToUnload += transportContract.getTeu();
				teu -= transportContract.getTeu();
				dwt -= transportContract.getTeu()*transportContract.getDwtPerTeu();			

				transportContracts.remove(transportContract);

				totalBudget += transportContract.getTotalPrice();
				finance.addEntry(FinancialEntryType.SHIP_INCOME, transportContract.getTotalPrice());

				if(transportContract.getDayForDelivery()<0) {
					penaltyCharges += Math.abs(transportContract.getDayForDelivery())*transportContract.getDayClausePenalty();
				}

			}

			finance.addEntry(FinancialEntryType.PENALTY_CHARGES, -penaltyCharges);
			company.addBudget(totalBudget-penaltyCharges);

			profitabilityRoute.setIncomeObtained(totalBudget);
			profitabilityRoute.setPenaltyCharges(penaltyCharges);
			profitabilityRoute.addContractClosed(toClose);

			InboundEventQueue.getInstance().put(new Event(EventType.CONTRACT_COMPLETED_EVENT,profitabilityRoute));
			InboundEventQueue.getInstance().put(new Event(EventType.FINANCIAL_EVENT,this));
		}
	}


	public String getShipClassName() {
		return shipClassName;
	}

	public void setShipClassName(String shipClassName) {
		this.shipClassName = shipClassName;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getShipSize() {
		return shipSize;
	}

	public void setShipSize(int shipSize) {
		this.shipSize = shipSize;
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

	public void setDockedPort(Port port) {
		dockedPort = port;
		dockedPort.addDockedShip(this);
		position = new Point(port.getCooridnate().getX(), port.getCooridnate().getY()) ;
		navigationRoute = null;		
	}

	public void leaveDockedPort() {
		this.dockedPort.removeDockedShip(this);
		this.dockedPort = null;
		teuToLoad = 0;		
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


	public boolean isDocked() {
		return SHIP_STATUS_DOCKED.equals(status);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getHpPercentage() {
		return 	(hp*100)/maxHp;
	}

	public int getHpPercentage(int damagedHp) {
		return 	(damagedHp*100)/maxHp;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public void addHp(int hpToAdd) {
		this.hp += hpToAdd;
	}	

	public int getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}

	/**
	 * the function to calculate the damage for unit of navigation is based in an idex
	 * index = teu * average dwt * speed
	 * 
	 * then the final function of the unit of damage for node of navigation is 
	 * HpDamagePerNodeOfNavigation = ATAN(2) + ATAN( index ) / (1,6*ATAN(1))
	 * 
	 * @return
	 */
	private void calculateDamageForNodeOfNavigation(){
		hpDamagePerNodeOfNavigation = 1.1 +  Math.atan((dwt*speed)/300000D - 4D) / 1.26  ;
		hpDamagePerRoute = 0;

		log.debug("ship damage new damage per node:"+hpDamagePerNodeOfNavigation);
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

	public double getFuel() {
		return fuel;
	}

	public void setFuel(double fuel) {
		this.fuel = fuel;
	}

	public void addFuel(double toAdd) {
		this.fuel = fuel + toAdd;
	}	

	/**
	 * fuel consumption is calculated as:
	 *   consume = (speed^2 * A)  + (speed * A)
	 * 
	 * @param speed
	 * @return
	 */
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

	public double getMaxFuel() {
		return maxFuel;
	}

	public void setMaxFuel(double maxFuel) {
		this.maxFuel = maxFuel;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public double getShipAngle() {
		return shipAngle.getValue();
	}


	public void updateShipAngle(double hourPassed) {
		if(!SHIP_STATUS_NAVIGATION.equals(status)) {
			shipAngle.resetAngle(navigationRoute.getDegreeNavigationAngle());
		}else {
			shipAngle.rotateTo(navigationRoute.getDegreeNavigationAngle(), 20*hourPassed);	//move 20 degree per hour
		}

	}



	/**
	 * this is the main method that must be called to cast of a ship
	 * it tacking care to prepare the ship for the load activities and the navigation route
	 * 
	 * @param speed
	 * @param route
	 */
	public void prepareToloadAndToNavigate(int navigationSpeed, Route route) {
		log.debug("ship :"+name+" strat loading operations");
		speed = navigationSpeed;
		navigationRoute = new NavigationRoute(speed, route);
		profitabilityRoute = new ProfitabilityRoute(navigationRoute,this);
		status = SHIP_STATUS_LOADING;

		//calculate the time to load all the cargo based on the accepted contract in this port
		waitingTimeInHours = teuToLoad / dockedPort.getLoadTeuPerHour() ;	

		InboundEventQueue.getInstance().put(new Event(EventType.SHIP_STATUS_CHANGE_EVENT,this));	

	}

	/**
	 * spend time loading the ship
	 * 
	 * @param hourPassed
	 */
	private void loadShip(double hourPassed) {
		waitingTimeInHours = waitingTimeInHours - hourPassed;

		if(waitingTimeInHours<0) {	//if finish to load the ship prepare to navigate
			log.debug("ship :"+name+" completed loading start navigation");

			updateShipAngle(hourPassed);	//prepare the starting angle

			status = SHIP_STATUS_NAVIGATION;

			//cost for cast off
			double castOffCost = dockedPort.getCastOffCost(this);			
			finance.addEntry(FinancialEntryType.SHIP_CAST_OFF_COST_TUG, -castOffCost);
			profitabilityRoute.addTugCharges(castOffCost);
			company.removeBudget(castOffCost);			
			InboundEventQueue.getInstance().put(new Event(EventType.FINANCIAL_EVENT,this));			

			//leave the port			
			leaveDockedPort();

			//calculate the damage
			calculateDamageForNodeOfNavigation();			

			//ship start to navigate
			InboundEventQueue.getInstance().put(new Event(EventType.SHIP_STATUS_CHANGE_EVENT,this));			
		}
	}

	/**
	 * navigate to the destination
	 * 
	 * @param hourPassed
	 */	
	private void navigation(double hourPassed) {

		position = navigationRoute.navigate(hourPassed);
		updateShipAngle(hourPassed);

		//fuel
		double fuelConsumed = getFuelConsumptionPerHour(navigationRoute.getSpeed())*hourPassed;
		fuel -= fuelConsumed;
		profitabilityRoute.addFuelConsumed(fuelConsumed);

		//damage
		double damage = hpDamagePerNodeOfNavigation * navigationRoute.getSpeed()*hourPassed;
		hpDamagePerRoute += damage;

		if(navigationRoute.isArrivedAtDestination()) {

			//evaluate the final damage
			profitabilityRoute.addHpDamaged((int)hpDamagePerRoute);
			hp -= (int)hpDamagePerRoute;
			log.debug("ship damage per route:"+hpDamagePerRoute);

			//docking the ship
			setDockedPort(navigationRoute.getDestinationPort());	//this set also final coordinate of the ship

			waitingTimeInHours = 4;		//TODO set the docking time, may be function of the port ??? in fact depend from the traffic in the dock

			status = SHIP_STATUS_DOCKING;
			InboundEventQueue.getInstance().put(new Event(EventType.SHIP_STATUS_CHANGE_EVENT,this));					
		}

		InboundEventQueue.getInstance().put(new Event(EventType.SHIP_FUEL_CHANGE_EVENT,this));	

	}


	/**
	 * spend time loading the ship
	 * 
	 * @param hourPassed
	 */
	private void docking(double hourPassed) {
		waitingTimeInHours = waitingTimeInHours - hourPassed;
		if(waitingTimeInHours<0) {
			log.debug("ship :"+name+" completed docking");

			//cost for cast off to dock
			double castOffCost = dockedPort.getCastOffCost(this);
			finance.addEntry(FinancialEntryType.SHIP_DOCK_COST_TUG, -castOffCost);
			company.removeBudget(castOffCost);			
			profitabilityRoute.addTugCharges(castOffCost);

			//close contracts
			closeContracts();

			if(teuToUnload > 0){
				status = SHIP_STATUS_UNLOADING;		
				waitingTimeInHours = teuToUnload / dockedPort.getLoadTeuPerHour() ;					
			}else{
				status = SHIP_STATUS_DOCKED;				
			}

			InboundEventQueue.getInstance().put(new Event(EventType.SHIP_STATUS_CHANGE_EVENT,this));
			InboundEventQueue.getInstance().put(new Event(EventType.FINANCIAL_EVENT,this));							
		}
	}	


	/**
	 * spend time loading the ship
	 * 
	 * @param hourPassed
	 */
	private void unloadShip(double hourPassed) {
		waitingTimeInHours = waitingTimeInHours - hourPassed;

		if(waitingTimeInHours<0) {	//if finish to load the ship
			log.debug("ship :"+name+" completed unloading ");
			status = SHIP_STATUS_DOCKED;

			//ship start to navigate
			InboundEventQueue.getInstance().put(new Event(EventType.SHIP_STATUS_CHANGE_EVENT,this));			
		}		

	}	


	@Override	
	public void update(int minutsPassed, boolean isNewDay, boolean isNewWeek, boolean isNewMonth) {

		//reduce the delivery time to each contract
		if(isNewDay) {
			for (TransportContract transportContract : transportContracts) {
				transportContract.reduceDayForDelivery();
			}
		}

		double hourPassed = minutsPassed/60.0;

		if(SHIP_STATUS_DOCKED.equals(status)) {

		}else if(SHIP_STATUS_LOADING.equals(status)) {

			loadShip(hourPassed);

		}else if(SHIP_STATUS_NAVIGATION.equals(status)) {

			navigation(hourPassed);

		}else if(SHIP_STATUS_DOCKING.equals(status)) {

			docking(hourPassed);

		}else if(SHIP_STATUS_UNLOADING.equals(status)) {

			unloadShip(hourPassed);

		}  



	}







}
