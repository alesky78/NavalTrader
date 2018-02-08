package it.spaghettisource.navaltrader.game.factory;

import java.util.Arrays;
import java.util.List;

import it.spaghettisource.navaltrader.game.model.Company;
import it.spaghettisource.navaltrader.game.model.Port;
import it.spaghettisource.navaltrader.game.model.Ship;



/**
 * 
 * @author Alessandro
 *
 */
public class ShipFactory {



	//ship cargo
	private static final int SHIP_CARGO_LITTLE = 3000;	
	private static final int SHIP_CARGO_LARGE = 4000;
	private static final int SHIP_CARGO_HUGE = 6000;		
		
	//customized ship
	private static final Ship SHIP_OLD_LITTLE = 	new Ship("Small feeder", "nsr-32", 	50,	 SHIP_CARGO_LITTLE	, 1000,  3000, 0.02, 0.03, 2000, 15, 1000000);
	private static final Ship SHIP_OLD_LARGE = 		new Ship("Feeder", 		 "xModel", 	50,	 SHIP_CARGO_LARGE	, 2000,  3500, 0.02, 0.04, 2000, 16, 2000000);	
	private static final Ship SHIP_NORMAL_LITTLE = 	new Ship("Feedermax", 	 "SS32", 	60,	 SHIP_CARGO_LITTLE	, 3000,  3500, 0.02, 0.06, 2000, 17, 4000000);
	private static final Ship SHIP_NORMAL_LARGE = 	new Ship("Panamax",		 "FastS", 	70,	 SHIP_CARGO_LARGE	, 5100,  4000, 0.02, 0.1, 2000, 19, 5000000);
	private static final Ship SHIP_HITECH_LITTLE = 	new Ship("Post-Panamax", "FastSS",	80, SHIP_CARGO_LITTLE	, 10000, 4000, 0.02, 0.2, 2000, 21, 10000000);
	private static final Ship SHIP_HITECH_LARGE = 	new Ship("New Panamax",	 "tornato", 90, SHIP_CARGO_LARGE	, 14500, 5000, 0.02, 0.2, 2000, 22, 15000000);
	private static final Ship SHIP_HITECH_HUGE= 	new Ship("ULCV",		 "spaceX", 	100, SHIP_CARGO_HUGE	, 18270, 6000, 0.02, 0.3, 2000, 23, 25000000);	
	
	private static final Ship[] shipArray = new Ship[]{SHIP_OLD_LITTLE,SHIP_OLD_LARGE,SHIP_NORMAL_LITTLE,SHIP_NORMAL_LARGE,SHIP_HITECH_LITTLE,SHIP_HITECH_LARGE,SHIP_HITECH_HUGE};	
	private static double priceIndex = 1.0;

	private ShipFactory() {
	}
	
	
	//TODO create a real ship generator class this is just for test purpose
	public static List<Ship> getListSellShip(){
		return Arrays.asList(shipArray);
	}
	
	
	public static Ship factoryShip(String model,String name,Port port,Company company){
		Ship modelShip = null;
		Ship newShip = null;
		
		//find ship to copy
		for (Ship ship : getListSellShip()) {
			if(ship.getModel().equals(model)){
				modelShip = ship;
			}
		}
		newShip = new Ship(modelShip.getShipClass(), modelShip.getModel(), modelShip.getHull(), modelShip.getMaxDwt(), modelShip.getMaxTeu(),  modelShip.getMaxFuel(), modelShip.getFuelConsumptionIndexA(), modelShip.getFuelConsumptionIndexB(), modelShip.getOperatingCost(), modelShip.getMaxSpeed(),modelShip.getBasePrice());
		newShip.setName(name);
		newShip.setDockedPort(port);
		newShip.setCompany(company);		
		
		return newShip;
	}	
	
}
