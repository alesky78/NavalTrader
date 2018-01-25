package it.spaghettisource.navaltrader.game.model;

import java.util.ArrayList;
import java.util.List;

import it.spaghettisource.navaltrader.game.loop.Entity;

public class Port  implements Entity{

	private String name;
	private int classAccepted;
	private int dayContractRegeneration;	
	private List<TransportContract> contracts;

	
	
	public Port(String name, int classAccepted,int dayContractRegeneration) {
		super();
		this.name = name;
		this.classAccepted = classAccepted;
		this.dayContractRegeneration = dayContractRegeneration;
		this.contracts = new ArrayList<TransportContract>(0);
	}


	private void generateContracts(){
		contracts = TransportContract.generateNewContract(10);
	}

	public void update(int minutsPassed, boolean isNewDate) {
	
		
	}
	
	
}
