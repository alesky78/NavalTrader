package it.spaghettisource.navaltrader.game.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import it.spaghettisource.navaltrader.game.model.Port;
import it.spaghettisource.navaltrader.game.model.TransportContract;
import it.spaghettisource.navaltrader.game.model.World;

public class ContractFactory {
	
	
	
	//TODO implement the logic to calculate the new contracts
	public static List<TransportContract> generateContracts(World world, Port targetPort){
		
		int numberOfContracts = 10;
		
		List<Port> connectePorts = world.getConnectedPorts(targetPort);
		int connected = connectePorts.size();
		
		List<TransportContract> newContracts = new ArrayList<TransportContract>(numberOfContracts);

		int teu;
		int dwt;
		int price;
		Port port;
		
		for (int i = 0; i< numberOfContracts; i++) {
			teu = ThreadLocalRandom.current().nextInt(50, 999+1 );
			dwt = ThreadLocalRandom.current().nextInt(2, 10+1 );
			price = ThreadLocalRandom.current().nextInt(1000, 12000+1 );			
			port = connectePorts.get(ThreadLocalRandom.current().nextInt(0, connected ));	//get random port
			
			newContracts.add(new TransportContract("wood", teu, dwt, price,port));	
		}
		
		return newContracts;
	}

}
