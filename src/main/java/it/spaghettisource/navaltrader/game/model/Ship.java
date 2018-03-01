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
	public static final String SHIP_STATUS_UNLOADING = "unloading";		


	private String status;
	private double waitingTimeInHours; //used	to manage the time to stay in particular status	

	private Company company;

	private Point position;	
	private NavigationRoute navigationRoute;
	private ProfitabilityRoute profitabilityRoute;

	private String name;	
	private String model;
	private String shipClass;	
	private Port dockedPort;

	private Finance finance;
	private double basePrice;	
	private double operatingCost;

	private int hp;	
	private int maxHp;	
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

	private List<TransportContract> transportContracts;


	public Ship(String shipClass, String model, int hp, int maxHp, int maxDwt,int maxTeu, double maxFuel,double fuelConsumptionIndexA, double fuelConsumptionIndexB,  double operatingCost, int maxSpeed, double basePrice) {

		this.shipClass = shipClass;
		this.model = model;

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
		name = "";		
		finance = new Finance();
		transportContracts = new ArrayList<TransportContract>();

		this.status = SHIP_STATUS_DOCKED;
		waitingTimeInHours = 0;

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
			int totalBudget = 0;
			for (TransportContract transportContract : toClose) {
				//reset the Teu and the DWT
				teuToUnload += transportContract.getTeu();
				teu -= transportContract.getTeu();
				dwt -= transportContract.getTeu()*transportContract.getDwtPerTeu();			

				transportContracts.remove(transportContract);
				transportContract.getDestinationPort().getMarket().addQuantityToMarket(transportContract.getProduct(), teu);
				
				totalBudget += transportContract.getTotalPrice();
				finance.addEntry(FinancialEntryType.SHIP_INCOME, transportContract.getTotalPrice());
			}

			company.addBudget(totalBudget);
			
			profitabilityRoute.setIncomeObtained(totalBudget);
			profitabilityRoute.addContractClosed(toClose);
			
			InboundEventQueue.getInstance().put(new Event(EventType.CONTRACT_COMPLETED_EVENT,profitabilityRoute));
			InboundEventQueue.getInstance().put(new Event(EventType.FINANCIAL_EVENT,this));
		}
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
		this.position = new Point(dockedPort.getCooridnate().getX(), dockedPort.getCooridnate().getY()) ;
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
		
		if(waitingTimeInHours<0) {	//if finish to load the ship
			log.debug("ship :"+name+" completed loading start navigation");
			status = SHIP_STATUS_NAVIGATION;

			//cost for cast off
			finance.addEntry(FinancialEntryType.SHIP_CAST_OFF_COST_TUG, -dockedPort.getCastOffCost());
			profitabilityRoute.addTugCharges(dockedPort.getCastOffCost());
			company.removeBudget(dockedPort.getCastOffCost());			
			InboundEventQueue.getInstance().put(new Event(EventType.FINANCIAL_EVENT,this));			

			//reset the variables
			dockedPort = null;
			teuToLoad = 0;

			//ship start to navigate
			InboundEventQueue.getInstance().put(new Event(EventType.SHIP_STATUS_CHANGE_EVENT,this));			
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
	
	/**
	 * navigate to the destination
	 * 
	 * @param hourPassed
	 */	
	private void navigation(double hourPassed) {

		position = navigationRoute.navigate(hourPassed);
		
		double fuelConsumed = getFuelConsumptionPerHour(navigationRoute.getSpeed())*hourPassed;
		fuel -= fuelConsumed;
		profitabilityRoute.addFuelConsumed(fuelConsumed);

		//TODO implement the damage of the ship
		
		
		if(navigationRoute.isArrivedAtDestination()) {

			setDockedPort(navigationRoute.getDestinationPort());	//this set also final coordinate of the ship
			waitingTimeInHours = 4;		//TODO set the docking time,	may be function of the port and ship type??? depend also from the traffic
			navigationRoute = null;
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
			finance.addEntry(FinancialEntryType.SHIP_DOCK_COST_TUG, -dockedPort.getCastOffCost());
			company.removeBudget(dockedPort.getCastOffCost());			
			profitabilityRoute.addTugCharges(dockedPort.getCastOffCost());
			
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

	@Override	
	public void update(int minutsPassed, boolean isNewDay, boolean isNewWeek, boolean isNewMonth) {

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
