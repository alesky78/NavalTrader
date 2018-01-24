package it.spaghettisource.navaltrader.game.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.loop.Entity;
import it.spaghettisource.navaltrader.ui.event.Event;
import it.spaghettisource.navaltrader.ui.event.EventType;
import it.spaghettisource.navaltrader.ui.event.InboundEventQueue;

public class Company implements Entity {

	public final static String RATING_VPOOR = "very poor";
	public final static String RATING_POOR = "poor";	
	public final static String RATING_NORAL = "normal";
	public final static String RATING_GOOD = "good";		
	public final static String RATING_VGOOD = "very good";	
	
	static Log log = LogFactory.getLog(Company.class.getName());
	
	private String name;
	private List<Ship> ships;
	private double budget;	
	private String rating;		
	private Finance companyFinance;	
	
	public Company(String companyName, int initialBudget) {
		name = companyName;
		ships = new ArrayList<Ship>();
		budget = initialBudget;
		rating = RATING_NORAL;
		companyFinance = new Finance();
		
	}
	
	@Override
	public void update(int minutsPassed) {
		
	}

	public void buyShip(String shipType, String name, double shipPrice) {
		Ship newShip = Ship.factoryShip(shipType, name);
		ships.add(newShip);
		
		InboundEventQueue.getInstance().put(new Event(EventType.BUY_SHIP_EVENT,newShip));				
		companyFinance.addEntry(FinancialEntryType.SHIP_BUY, -shipPrice);
		
		removeBudget(shipPrice);
	}
	
	public void sellShip(String name, double shipPrice) {
		Ship toTemove = getShipByName(name);
		
		if(toTemove != null) {
			//add finance of this ship to company other way we lost it forever when delete the ship
			companyFinance.add(toTemove.getFinance()); 
			
			ships.remove(toTemove);
			companyFinance.addEntry(FinancialEntryType.SHIP_SELL, shipPrice);			
			InboundEventQueue.getInstance().put(new Event(EventType.SELL_SHIP_EVENT,toTemove));	
			addBudget(shipPrice);			
		}

	}	

	public List<Ship> getShips() {
		return ships;
	}
	
	public Ship getShipByName(String name) {
		for (Ship ship : ships) {
			if(ship.getName().equals(name)){
				return ship;
			}
		}
		return null;
	}	
	
	
	public void refuelShip(String shipName,int amountToRefuel,Double priceToPay) {
		Ship ship = getShipByName(shipName);
		ship.addFuel(amountToRefuel);
		ship.getFinance().addEntry(FinancialEntryType.SHIP_FUEL, -priceToPay);
		
		removeBudget(priceToPay);
		
	}
	
	public void repairShip(String shipName,int amountToRepair,Double priceToPay) {
		Ship ship = getShipByName(shipName);
		ship.addHull(amountToRepair);
		ship.getFinance().addEntry(FinancialEntryType.SHIP_REPAIR, -priceToPay);		
		removeBudget(priceToPay);
	}	
	
	

	public void addBudget(double toAdd) {
		budget = budget + toAdd;
		InboundEventQueue.getInstance().put(new Event(EventType.BUDGET_EVENT));			
	}

	public void removeBudget(double toRemove) {
		budget = budget - toRemove;
		InboundEventQueue.getInstance().put(new Event(EventType.BUDGET_EVENT));			
	}

	public Double getBudget() {
		return budget;
	}

	public String getRating() {
		return rating;
	}
	
	public String getName() {
		return name;
	}

	public Finance getCompanyFinance(){

		Finance finance = new Finance();	
		
		finance.add(companyFinance);
		
		for (Ship ship : ships) {
			finance.add(ship.getFinance());
		}
		
		return finance;
	}


	

	
}
