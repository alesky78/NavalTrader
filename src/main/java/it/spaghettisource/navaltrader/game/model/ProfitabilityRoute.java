package it.spaghettisource.navaltrader.game.model;

import java.util.ArrayList;
import java.util.List;

public class ProfitabilityRoute {

	private NavigationRoute navigationRoute;
	private Ship ship;
	private double fuelConsumed;
	private int hpDamaged;
	private double incomeObtained;
	private double tugCharges;
	private List<TransportContract> contractClosed;
	
	public ProfitabilityRoute(NavigationRoute navigationRoute,Ship ship) {
		super();
		this.navigationRoute = navigationRoute;
		this.ship = ship;
		fuelConsumed = 0;
		hpDamaged = 0;	
		incomeObtained = 0;
		tugCharges = 0;
		
		contractClosed = new ArrayList<TransportContract>();
	}
	
	public Ship getShip() {
		return ship;
	}

	public double getIncomeObtained() {
		return incomeObtained;
	}
	
	public void setIncomeObtained(double incomeObtained) {
		this.incomeObtained = incomeObtained;
	}
	
	public double getFuelConsumed() {
		return fuelConsumed;
	}
	
	public double getFuelConsumedPrice() {
		return fuelConsumed*navigationRoute.getDestinationPort().getFuelPrice();
	}

	public void addFuelConsumed(double fuelConsumed) {
		this.fuelConsumed += fuelConsumed;
	}

	public double getTugCharges() {
		return tugCharges;
	}

	public void addTugCharges(double tugCharges) {
		this.tugCharges += tugCharges;
	}

	public void addHpDamaged(int hpDamaged) {
		this.hpDamaged += hpDamaged;
	}
		
	public double getHpDamagedPrice() {
		return hpDamaged*navigationRoute.getDestinationPort().getRepairPricePerHp();
	}

	public int getHpDamaged() {
		return hpDamaged;
	}

	public int getHpDamagedPercentage() {
		return ship.getHpPercentage(hpDamaged);
	}
	
	public List<TransportContract> getContractClosed() {
		return contractClosed;
	}

	public void setContractClosed(List<TransportContract> contracts) {
		this.contractClosed = contracts;
	}
	
	public void addContractClosed(List<TransportContract> contracts) {
		contractClosed.addAll(contracts);
	}	
	
	public int getDaysOfNavigation(){
		return (int)(navigationRoute.getTotalNavigationHours()/24);
	}
	
	public double getFinalBalance() {
		return incomeObtained - getTugCharges() - getFuelConsumedPrice() - getHpDamagedPrice();
	}
	

	
}
