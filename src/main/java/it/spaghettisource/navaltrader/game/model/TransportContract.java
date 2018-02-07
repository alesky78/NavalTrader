package it.spaghettisource.navaltrader.game.model;

import java.util.UUID;

public class TransportContract {

	private String id;
	private String good;
	private int teu;
	private int dwtPerTeu;		
	private double pricePerTeu;
	private Route route;

	//TODO implement the creation/management of the bonus
	private double clauseBonus;	
	private double clausePenalty;
	private int clauseDay;	


	public TransportContract(String good, int teu, int dwtPerTeu, double pricePerTeu, Route route) {
		super();
		this.id = UUID.randomUUID().toString();		
		this.good = good;
		this.teu = teu;
		this.dwtPerTeu = dwtPerTeu;
		this.pricePerTeu = pricePerTeu;
		this.route = route;
	}


	public String getId() {
		return id;
	}

	public String getGood() {
		return good;
	}

	public int getTeu() {
		return teu;
	}

	public int getDwtPerTeu() {
		return dwtPerTeu;
	}

	public double getPricePerTeu() {
		return pricePerTeu;
	}

	public Port getDestinationPort() {
		return route.getDestination();
	}

	public Route getRoute() {
		return route;
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
