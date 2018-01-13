package it.spaghettisource.navaltrader.game.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.loop.Entity;

public class Company implements Entity {

	static Log log = LogFactory.getLog(Company.class.getName());
	
	private String name;
	private List<Ship> ships;
	private int budget;
	
	public Company(String companyName, int initialBudget) {
		name = companyName;
		ships = new ArrayList<Ship>();
		budget = initialBudget;
	}
	
	@Override
	public void update(int minutsPassed) {
		
	}

	public void addShip(Ship ship) {
		ships.add(ship);
	}

	public void removeShip(Ship ship) {
		ships.remove(ship);
	}

	public void addBudget(int toAdd) {
		budget = budget + toAdd;
	}

	public void removeBudget(int toRemove) {
		budget = budget - toRemove;
	}
	
	
}
