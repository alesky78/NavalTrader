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
	private static final Ship SHIP_1 = 	new Ship("Small feeder", "nsr-32", 	2000, 5000,	 3000	, 1000,  1000, 0.02, 0.03, 2000, 15, 1000000);
	private static final Ship SHIP_2 = 	new Ship("Feeder", 		 "xModel", 	4000, 5000,	 3000	, 2000,  2000, 0.02, 0.04, 2000, 16, 2000000);	
	private static final Ship SHIP_3 = 	new Ship("Feedermax", 	 "SS32", 	1500, 5000,	 4000	, 3000,  2500, 0.02, 0.06, 2000, 17, 4000000);
	private static final Ship SHIP_4 = 	new Ship("Panamax",		 "FastS", 	3000, 10000, 4000	, 5100,  3000, 0.02, 0.1, 2000, 19, 5000000);
	private static final Ship SHIP_5 = 	new Ship("Post-Panamax", "FastSS",	5000, 20000,  8000	, 10000, 4000, 0.02, 0.2, 2000, 23, 10000000);
	private static final Ship SHIP_6 = 	new Ship("New Panamax",	 "tornato", 5000, 20000,  8000	, 14500, 5000, 0.02, 0.2, 2000, 25, 15000000);
	private static final Ship SHIP_7= 	new Ship("ULCV",		 "spaceX", 	10000, 25000, 8000	, 18270, 6000, 0.02, 0.3, 2000, 26, 25000000);	
	
	private static final Ship[] shipArray = new Ship[]{SHIP_1,SHIP_2,SHIP_3,SHIP_4,SHIP_5,SHIP_6,SHIP_7};	

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
		newShip = new Ship(modelShip.getShipClassName(), modelShip.getModel(), modelShip.getHp(), modelShip.getMaxHp(),  modelShip.getMaxDwt(), modelShip.getMaxTeu(),  modelShip.getMaxFuel(), modelShip.getFuelConsumptionIndexA(), modelShip.getFuelConsumptionIndexB(), modelShip.getOperatingCost(), modelShip.getMaxSpeed(),modelShip.getBasePrice());
		newShip.setName(name);
		newShip.setDockedPort(port);
		newShip.setCompany(company);		
		
		return newShip;
	}	
	
}
