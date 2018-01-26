package it.spaghettisource.navaltrader.game.model;

import java.util.ArrayList;
import java.util.List;

import it.spaghettisource.navaltrader.game.loop.Entity;

public class Port  implements Entity{

	private String name;
	private int classAccepted;
	private int dayContractRegeneration;	
	private int dayToNextContractRegeneration;
	private List<TransportContract> contracts;

	
	
	public Port(String name, int classAccepted,int dayContractRegeneration) {
		super();
		this.name = name;
		this.classAccepted = classAccepted;
		this.dayContractRegeneration = dayContractRegeneration;
		this.dayToNextContractRegeneration = dayContractRegeneration;
		this.contracts = new ArrayList<TransportContract>(0);
		generateContracts();
		
	}

	
	private void generateContracts(){
		contracts = TransportContract.generateNewContract(10);
	}

	
	public void update(int minutsPassed, boolean isNewDate, boolean isNewWeek, boolean isNewMonth) {
	
		if(isNewDate){
			dayToNextContractRegeneration = dayToNextContractRegeneration-1;
		}

		if(dayToNextContractRegeneration == 0){
			generateContracts();
			dayToNextContractRegeneration = dayContractRegeneration;
		}

		
	}
	
	
}
