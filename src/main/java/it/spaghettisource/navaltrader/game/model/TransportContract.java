package it.spaghettisource.navaltrader.game.model;

import java.util.List;
import java.util.UUID;

import it.spaghettisource.navaltrader.graphic.Point;

public class TransportContract {

	private String id;
	private String good;
	private int teu;
	private int dwtPerTeu;		
	private double pricePerTeu;
	private Port destinationPort;
	private List<Point> route;

	//TODO implement the management of the bonus
	private double clauseBonus;	
	private double clausePenalty;
	private int clauseDay;	


	public TransportContract(String good, int teu, int dwtPerTeu, double pricePerTeu, Port destinationPort,List<Point> route) {
		super();
		this.id = UUID.randomUUID().toString();		
		this.good = good;
		this.teu = teu;
		this.dwtPerTeu = dwtPerTeu;
		this.pricePerTeu = pricePerTeu;
		this.destinationPort = destinationPort;
		this.route = route;
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

	public Port getDestinationPort() {
		return destinationPort;
	}

	public void setDestinationPort(Port destinationPort) {
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
