package it.spaghettisource.navaltrader.game.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.loop.Entity;

public class Ship implements Entity{

	static Log log = LogFactory.getLog(Ship.class.getName());
	
	private String type;
	private String name;	
	private Finance finance;
	
	private ShipStatus status;
	
	private float hull;	
	private int cargoSpace;	
	private float actualFuel;	
	private float maxFuel;	
	private float actualSpeed;	
	private float maxSpeed;	
	
	
	public Ship(String ShipName) {
		
		name = ShipName;
		finance = new Finance();
		finance.init();
	}

	
	@Override	
	public void update(int minutsPassed) {
		// TODO business logic for ship
		
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

	public ShipStatus getStatus() {
		return status;
	}

	public void setStatus(ShipStatus status) {
		this.status = status;
	}

	public float getHull() {
		return hull;
	}

	public void setHull(float hull) {
		this.hull = hull;
	}

	public int getCargoSpace() {
		return cargoSpace;
	}

	public void setCargoSpace(int cargoSpace) {
		this.cargoSpace = cargoSpace;
	}

	public float getActualFuel() {
		return actualFuel;
	}

	public void setActualFuel(float actualFuel) {
		this.actualFuel = actualFuel;
	}

	public float getMaxFuel() {
		return maxFuel;
	}

	public void setMaxFuel(float maxFuel) {
		this.maxFuel = maxFuel;
	}

	public float getActualSpeed() {
		return actualSpeed;
	}

	public void setActualSpeed(float actualSpeed) {
		this.actualSpeed = actualSpeed;
	}

	public float getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public Finance getFinance() {
		return finance;
	}
	
		
}
