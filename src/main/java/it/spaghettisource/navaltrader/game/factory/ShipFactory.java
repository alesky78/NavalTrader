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
		
	//customized ship
	private static final Ship SHIP_OLD_S_LITTLE = 	new Ship("Small feeder", "nsr-xx", 	60,	 1000	, 500,   1000, 0.02, 0.03, 2000, 13, 1000000);	
	private static final Ship SHIP_OLD_LITTLE = 	new Ship("Small feeder", "nsr-32", 	60,	 3000	, 1000,  1000, 0.02, 0.03, 2000, 15, 1000000);
	private static final Ship SHIP_OLD_LARGE = 		new Ship("Feeder", 		 "xModel", 	60,	 3000	, 2000,  2000, 0.02, 0.04, 2000, 16, 2000000);	
	private static final Ship SHIP_NORMAL_LITTLE = 	new Ship("Feedermax", 	 "SS32", 	60,	 4000	, 3000,  2500, 0.02, 0.06, 2000, 17, 4000000);
	private static final Ship SHIP_NORMAL_LARGE = 	new Ship("Panamax",		 "FastS", 	70,	 4000	, 5100,  3000, 0.02, 0.1, 2000, 19, 5000000);
	private static final Ship SHIP_HITECH_LITTLE = 	new Ship("Post-Panamax", "FastSS",	80,  8000	, 10000, 4000, 0.02, 0.2, 2000, 23, 10000000);
	private static final Ship SHIP_HITECH_LARGE = 	new Ship("New Panamax",	 "tornato", 90,  8000	, 14500, 5000, 0.02, 0.2, 2000, 25, 15000000);
	private static final Ship SHIP_HITECH_HUGE= 	new Ship("ULCV",		 "spaceX", 	100, 8000	, 18270, 6000, 0.02, 0.3, 2000, 26, 25000000);	
	
	private static final Ship[] shipArray = new Ship[]{SHIP_OLD_S_LITTLE,SHIP_OLD_LITTLE,SHIP_OLD_LARGE,SHIP_NORMAL_LITTLE,SHIP_NORMAL_LARGE,SHIP_HITECH_LITTLE,SHIP_HITECH_LARGE,SHIP_HITECH_HUGE};	
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
