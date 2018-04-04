package it.spaghettisource.navaltrader.game.factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.model.Port;
import it.spaghettisource.navaltrader.game.model.Product;
import it.spaghettisource.navaltrader.game.model.Ship;
import it.spaghettisource.navaltrader.game.model.TransportContract;
import it.spaghettisource.navaltrader.game.model.World;

public class ContractFactory {
	
	static Log log = LogFactory.getLog(ContractFactory.class.getName());
	
	private static int DISTANCE_REDUCTION = 1000;
	private static int SPEED_REDUCTION = 10;	
	
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
			int maxTeuLoadable;			
			int dwt;
			int distance;		
			int days;				
			double pricePerTeu;
			double dayPenality;
			Port destinationPort;
			Product product;
			
			//generate the contracts for each ship
			for (Ship ship : ships) {
				numberOfContracts = ThreadLocalRandom.current().nextInt(0, 10);	//generate an amount of contracts max 10 for ship
	
				for (int i = 0; i< numberOfContracts; i++) {
					destinationPort = connectePorts.get(ThreadLocalRandom.current().nextInt(0, connectePorts.size() ));	//get random port
					product =  chooseRandomProduct(sourcePort, destinationPort); //choose a product that is demanded and supply between the two ports
					
					//if choosed a valid product
					if(product!=null){
						dwt = product.getDwt() * ThreadLocalRandom.current().nextInt(60, 120)/100; //dwt between 60% to 120%
						maxTeuLoadable = ship.getMaxDwt() / dwt;
						maxTeuLoadable = Math.min(maxTeuLoadable, ship.getMaxTeu());
						
						if(maxTeuLoadable>1) {
							teu = ThreadLocalRandom.current().nextInt(1, maxTeuLoadable  );	//generate from 1 to max of loaded TEU							
						}else {
							teu = 1;
						}

						
						distance = sourcePort.getRouteTo(destinationPort).getDistanceInScale();
						
						days = distance / (SPEED_REDUCTION * 24) * ThreadLocalRandom.current().nextInt(80, 200)/100; //days between 80% to 200%
						
						pricePerTeu = calcolatePricePerTeu(distance, product, teu, dwt);
						
						dayPenality = teu * pricePerTeu * ThreadLocalRandom.current().nextInt(1, 20)/100; //penalty  between 1% to 20% of the total contract per day
						
						newContracts.add(new TransportContract(product, teu, dwt, pricePerTeu, dayPenality, days, destinationPort));				
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

	//TODO il calcolo deve essere funzionde di (prodotto, distanza, tempo, variazione casuale)  manca ancora il tempo 
	private static double calcolatePricePerTeu(int distance, Product product,int teuInTheOrder, int dwtPerTeuInTheOrder) {
		
		//variazione casuale
		double variationPrice = ((double)ThreadLocalRandom.current().nextInt(80, 120)/100D) ;
		
		//distanza: ogni DISTANCE_REDUCTION nodi riduzione del 50% del prezzo per DISTANCE_REDUCTION nodi successivi
		int loops = distance/DISTANCE_REDUCTION;
		double variationDistance = 0;
		int reduction = 1;
		for (int i = 0; i<=loops; i++) {
			reduction = reduction * 2;
			if(i==loops) {//last loop take the rest and not DISTANCE_REDUCTION units
				variationDistance += (distance%DISTANCE_REDUCTION) / reduction;				
			}else {
				variationDistance += DISTANCE_REDUCTION / reduction;				
			}

		}
		
		return product.getPrice()* variationPrice * variationDistance;
	}

	
	public static void main(String[] args) {
		
		for (int i = 0; i < 1; i++) {
			test(600);
		}

		
	}

	private static void test(int totalDistance) {
		int loops = totalDistance/1000;
		double variationDistance = 0;
		for (int i = 0; i<=loops; i++) {
			if(i==loops) {//last loop take the rest and not 1000 units
				variationDistance += (totalDistance%1000) /(i+1);				
			}else {
				variationDistance += 1000/(i+1);				
			}

		}
		log.info("distance: "+totalDistance+" variationDistance:"+variationDistance);
	}
}
