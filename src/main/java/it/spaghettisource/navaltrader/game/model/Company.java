package it.spaghettisource.navaltrader.game.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.loop.Entity;

public class Company implements Entity {

	public final static String RATING_VPOOR = "very poor";
	public final static String RATING_POOR = "poor";	
	public final static String RATING_NORAL = "normal";
	public final static String RATING_GOOD = "good";		
	public final static String RATING_VGOOD = "very good";	
	
	static Log log = LogFactory.getLog(Company.class.getName());
	
	private String name;
	private List<Ship> ships;
	private int budget;	
	private String rating;		
	
	public Company(String companyName, int initialBudget) {
		name = companyName;
		ships = new ArrayList<Ship>();
		budget = initialBudget;
		rating = RATING_NORAL;
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

	public int getBudget() {
		return budget;
	}

	public String getRating() {
		return rating;
	}
	
	public Finance getCompanyFinance(){
		Finance global = new Finance();
		global.init();
		
		for (Ship ship : ships) {
			global.add(ship.getFinance());
		}
		
		return global;
	}
	
	
	
}
