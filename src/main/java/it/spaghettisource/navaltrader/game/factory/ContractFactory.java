package it.spaghettisource.navaltrader.game.factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import it.spaghettisource.navaltrader.game.model.Port;
import it.spaghettisource.navaltrader.game.model.Product;
import it.spaghettisource.navaltrader.game.model.Ship;
import it.spaghettisource.navaltrader.game.model.TransportContract;
import it.spaghettisource.navaltrader.game.model.World;

public class ContractFactory {
	
	/**
	 * this class create the contract in the port..
	 * 
	 * the contract are generated in function of the ship in the port, that mean that the size of the contract like teu, dwt depend from the ships doked
	 *
	 *
	 * @param world
	 * @param sourcePort where we be introduced the contracts
	 * @return
	 */
	public static List<TransportContract> generateContracts(World world, Port sourcePort){
		
		List<Ship> ships = sourcePort.getDockedShip();
		List<TransportContract> newContracts = new ArrayList<TransportContract>();		
		
		if(!ships.isEmpty()) {

			List<Port> connectePorts = world.getConnectedPorts(sourcePort);
			
			int numberOfContracts;
			int teu;
			int dwt;
			double price;
			Port destinationPort;
			Product product;
			
			//generate the contracts for each ship
			for (Ship ship : ships) {
				numberOfContracts = ThreadLocalRandom.current().nextInt(0, 10);	//generate an amount of contracts max 7 for ship
	
				for (int i = 0; i< numberOfContracts; i++) {
					destinationPort = connectePorts.get(ThreadLocalRandom.current().nextInt(0, connectePorts.size() ));	//get random port
					product =  chooseRandomProduct(sourcePort, destinationPort);
					
					//if choosed a valid product
					if(product!=null){
						dwt = product.getDwt() * ThreadLocalRandom.current().nextInt(60, 120)/100; //dwt between 60% to 120%
						int maxTeuLoadable = ship.getMaxDwt() / dwt;
						maxTeuLoadable = Math.min(maxTeuLoadable, ship.getMaxTeu());
						
						teu = ThreadLocalRandom.current().nextInt(1, maxTeuLoadable  );	//generate from 1 to max 
						
						price = calcolatePricePerTeu(sourcePort, destinationPort, ship, product,maxTeuLoadable);
						
						newContracts.add(new TransportContract(product, teu, dwt, price,destinationPort));				
					}
			
				}
				
			}
			
		}
		
		return newContracts;
		

	}
	

	private static Product chooseRandomProduct(Port supplyPort, Port demandPort) {
		Product[] supply = supplyPort.getMarket().productSupply();
		Product[] demand = demandPort.getMarket().productDemand();
		
	    Object[] intersection =  Arrays.stream(supply)
	                 					.distinct()
	                 					.filter(x -> Arrays.stream(demand).anyMatch(y -> y.equals(x)))
	                 					.toArray();
	    
	    if(intersection.length == 0){
	    	return null;
	    }
	    return (Product) intersection[ThreadLocalRandom.current().nextInt(0, intersection.length)];
	    
	}
		
	//TODO non puo essere funzione del carico che posso portare al massimo
	private static double calcolatePricePerTeu(Port sourcePort,Port destinationPort,Ship ship,Product product,int maxTeuLoadable) {

		//amount of fuel consumed
		double consumption = ship.getFuelConsumptionPerDistance(17, sourcePort.getRouteTo(destinationPort).getDistanceInScale());	//consider standard speed at 17 nodes
		
		//total cost 
		double totalCost = consumption * 500;	//consider 500 euro standard price for fuel
		
		//prezzo medio per teu
		return totalCost / maxTeuLoadable * product.getPrice()  * ThreadLocalRandom.current().nextInt(80, 150)/100;	//reset the price between 80 to 150 %
	}

}
