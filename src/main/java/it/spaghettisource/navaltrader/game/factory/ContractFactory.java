package it.spaghettisource.navaltrader.game.factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import it.spaghettisource.navaltrader.game.model.Port;
import it.spaghettisource.navaltrader.game.model.Product;
import it.spaghettisource.navaltrader.game.model.TransportContract;
import it.spaghettisource.navaltrader.game.model.World;

public class ContractFactory {
	
	public static List<TransportContract> generateContracts(World world, Port targetPort){
		
		int productProduced = targetPort.getMarket().productSupply().length;
		int numberOfContracts = 10;//ThreadLocalRandom.current().nextInt(0, productProduced*2 ); //TODO how to calculate number of contracts
		
		List<Port> connectePorts = world.getConnectedPorts(targetPort);
		int connected = connectePorts.size();
		
		List<TransportContract> newContracts = new ArrayList<TransportContract>(numberOfContracts);

		int teu;
		int dwt;
		double price;
		Port port;
		Product product;
		
		for (int i = 0; i< numberOfContracts; i++) {
			teu = ThreadLocalRandom.current().nextInt(50, 200+1 );	//TODO how to calculate teu should be variable of the ships owned
			port = connectePorts.get(ThreadLocalRandom.current().nextInt(0, connected ));	//get random port
			product =  generateProduct(targetPort, port);
			
			if(product!=null){
				dwt = product.getDwt() * ThreadLocalRandom.current().nextInt(50, 150)/100; //dwt between 50% to 150%
				price = port.getMarket().getPriceForBuy(product) * ThreadLocalRandom.current().nextInt(50, 150)/100.0; //TODO price must consider distance between 50% to 150%
				newContracts.add(new TransportContract(product, teu, dwt, price,port));				
			}
	
		}
		
		return newContracts;
	}
	
	
	public static Product generateProduct(Port supplyPort, Port demandPort) {
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
		

}
