package it.spaghettisource.navaltrader.game.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import it.spaghettisource.navaltrader.ui.model.LoanTableRow;

public class TransportContract {

	private String id;
	private String good;
	private int teu;
	private int dwtPerTeu;		
	private double pricePerTeu;
	private String destinationPort;

	//TODO implement the management of the bonus
	private double clauseBonus;	
	private double clausePenalty;
	private int clauseDay;	


	public TransportContract(String good, int teu, int dwtPerTeu, double pricePerTeu, String destinationPort) {
		super();
		this.id = UUID.randomUUID().toString();		
		this.good = good;
		this.teu = teu;
		this.dwtPerTeu = dwtPerTeu;
		this.pricePerTeu = pricePerTeu;
		this.destinationPort = destinationPort;
	}


	//TODO reimplement the genreation	
	public static List<TransportContract> generateNewContract(int maxGenerated){

		List<TransportContract> contracts = new ArrayList<TransportContract>(maxGenerated);

		for (int i = 0; i< maxGenerated; i++) {
			int teu = ThreadLocalRandom.current().nextInt(50, 999+1 );
			int dwt = ThreadLocalRandom.current().nextInt(2, 10+1 );
			int price = ThreadLocalRandom.current().nextInt(1000, 12000+1 );			

			contracts.add(new TransportContract("wood", teu, dwt, price, "port x"));			
		}

		return contracts;
	}

	public String getId() {
		return id;
	}

	public String getGood() {
		return good;
	}

	public void setGood(String good) {
		this.good = good;
	}

	public int getTeu() {
		return teu;
	}

	public void setTeu(int teu) {
		this.teu = teu;
	}

	public int getDwtPerTeu() {
		return dwtPerTeu;
	}

	public void setDwtPerTeu(int dwtPerTeu) {
		this.dwtPerTeu = dwtPerTeu;
	}

	public double getPricePerTeu() {
		return pricePerTeu;
	}

	public void setPricePerTeu(double pricePerTeu) {
		this.pricePerTeu = pricePerTeu;
	}

	public String getDestinationPort() {
		return destinationPort;
	}

	public void setDestinationPort(String destinationPort) {
		this.destinationPort = destinationPort;
	}

	public boolean equals(Object obj){
		if(obj==null){
			return false;
		}else if(!(obj instanceof TransportContract)){
			return false;
		}else{
			return id.equals(((TransportContract)obj).getId());
		}	
	}	

}
