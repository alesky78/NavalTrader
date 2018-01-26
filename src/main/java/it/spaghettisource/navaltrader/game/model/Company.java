package it.spaghettisource.navaltrader.game.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import EDU.oswego.cs.dl.util.concurrent.misc.Fraction;
import it.spaghettisource.navaltrader.game.loop.Entity;
import it.spaghettisource.navaltrader.ui.event.Event;
import it.spaghettisource.navaltrader.ui.event.EventType;
import it.spaghettisource.navaltrader.ui.event.InboundEventQueue;

public class Company implements Entity {	
	
	static Log log = LogFactory.getLog(Company.class.getName());
	
	private String name;
	private double budget;	
	private String rating;		
	private Finance companyFinance;	
	private Bank bank;	
	private List<Ship> ships;	
	
	public Company(String companyName, int initialBudget) {
		name = companyName;
		ships = new ArrayList<Ship>();
		budget = initialBudget;
		rating = "unknow";	//TODO how to manage rating?
		companyFinance = new Finance();
		bank = new Bank(0.1);
	}

	public void buyShip(String shipType, String name, double shipPrice) {
		Ship newShip = Ship.factoryShip(shipType, name);
		ships.add(newShip);
				
		companyFinance.addEntry(FinancialEntryType.SHIP_BUY, -shipPrice);
		
		removeBudget(shipPrice);
		
		InboundEventQueue.getInstance().put(new Event(EventType.BUY_SHIP_EVENT,newShip));
		InboundEventQueue.getInstance().put(new Event(EventType.FINANCIAL_EVENT,this));	
				
	}
	
	public void sellShip(String name, double shipPrice) {
		Ship toTemove = getShipByName(name);
		
		if(toTemove != null) {
			//add finance of this ship to company other way we lost it forever when delete the ship
			companyFinance.add(toTemove.getFinance()); 
			
			ships.remove(toTemove);
			companyFinance.addEntry(FinancialEntryType.SHIP_SELL, shipPrice);			
	
			addBudget(shipPrice);
			
			InboundEventQueue.getInstance().put(new Event(EventType.SELL_SHIP_EVENT,toTemove));
			InboundEventQueue.getInstance().put(new Event(EventType.FINANCIAL_EVENT,this));			
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
		
		InboundEventQueue.getInstance().put(new Event(EventType.SHIP_FUEL_CHANGE_EVENT,ship));	
		InboundEventQueue.getInstance().put(new Event(EventType.FINANCIAL_EVENT,ship));		
		
	}
	
	public void repairShip(String shipName,int amountToRepair,Double priceToPay) {
		Ship ship = getShipByName(shipName);
		ship.addHull(amountToRepair);
		ship.getFinance().addEntry(FinancialEntryType.SHIP_REPAIR, -priceToPay);		
		removeBudget(priceToPay);
		
		InboundEventQueue.getInstance().put(new Event(EventType.SHIP_HULL_CHANGE_EVENT,ship));
		InboundEventQueue.getInstance().put(new Event(EventType.FINANCIAL_EVENT,ship));					
	}	
	
	public Bank getBank() {
		return bank;
	}

	public void repairLoad(String loanId, int amount){
		Loan loan = bank.repairLoad(loanId, amount);
		InboundEventQueue.getInstance().put(new Event(EventType.LOAN_EVENT,loan));		
		removeBudget(amount);		
	}	

	public void createNewLoad(int amount){
		Loan newLoan = bank.createNewLoad(amount);
		InboundEventQueue.getInstance().put(new Event(EventType.LOAN_EVENT,newLoan));		
		addBudget(amount);			
	}
	
	
	public void addBudget(double toAdd) {
		budget = budget + toAdd;
		InboundEventQueue.getInstance().put(new Event(EventType.BUDGET_EVENT,this));			
	}

	public void removeBudget(double toRemove) {
		budget = budget - toRemove;
		InboundEventQueue.getInstance().put(new Event(EventType.BUDGET_EVENT,this));			
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

	public Finance getGlobalCompanyFinance(){

		Finance finance = new Finance();	
		
		finance.add(companyFinance);
		
		for (Ship ship : ships) {
			finance.add(ship.getFinance());
		}
		
		return finance;
	}


	@Override
	public void update(int minutsPassed, boolean isNewDay, boolean isNewWeek, boolean isNewMonth) {
		
		double totalLostBudget = 0;
		double totalOperationalCost = 0;
		double totalInstallmentCost = 0;		
		
		////////////////////////////
		//- update ship
		//- pay ship operative cost
		if(isNewDay){
			for (Ship ship : ships) {
				//update ship
				ship.update(minutsPassed, isNewDay, isNewWeek, isNewMonth);
				//pay ship operative cost
				totalOperationalCost += ship.getOperatingCost();
				ship.getFinance().addEntry(FinancialEntryType.SHIP_OPERATING_COST, -ship.getOperatingCost());
			}
		}
		
		////////////////////////////
		//- pay loan Installment
		if(isNewWeek) {
			for (Loan loan : bank.getLoanList()) {
				totalInstallmentCost += loan.calculateDailyInterest(7);
			}
			companyFinance.addEntry(FinancialEntryType.PAY_INSTALLMEN, -totalInstallmentCost);
		}
		
		
		//controll the events
		if(totalOperationalCost>0 || totalInstallmentCost>0) {
			InboundEventQueue.getInstance().put(new Event(EventType.FINANCIAL_EVENT,this));	
		}
		
		
		totalLostBudget = totalOperationalCost + totalInstallmentCost;
		if(totalLostBudget>0) {
			removeBudget(totalLostBudget);
		}
		
	}

	
}
