package it.spaghettisource.navaltrader.game.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TransportContract {

	private String good;
	private int teu;
	private int dwtPerTeu;		
	private double pricePerTeu;
	private String destination;

	//TODO implement the management of the bonus
	private double clauseBonus;	
	private double clausePenalty;
	private int clauseDay;	


	public TransportContract(String good, int teu, int dwtPerTeu, double pricePerTeu, String destination) {
		super();
		this.good = good;
		this.teu = teu;
		this.dwtPerTeu = dwtPerTeu;
		this.pricePerTeu = pricePerTeu;
		this.destination = destination;
	}


	//TODO reimplement the genreation	
	public static List<TransportContract> generateNewContract(int maxGenerated){

		List<TransportContract> contracts = new ArrayList<TransportContract>(maxGenerated);

		for (int i = 0; i< maxGenerated; i++) {
			int teu = ThreadLocalRandom.current().nextInt(100, 999+1 );
			int dwt = ThreadLocalRandom.current().nextInt(2, 10+1 );
			int price = ThreadLocalRandom.current().nextInt(1000, 3500+1 );			

			contracts.add(new TransportContract("wood", teu, dwt, price, "port x"));			
		}

		return contracts;
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

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	

}
