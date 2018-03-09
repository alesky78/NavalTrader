package it.spaghettisource.navaltrader.game.model;

import java.util.UUID;

public class TransportContract {

	private String id;
	private Product product;
	private int teu;
	private int dwtPerTeu;		
	private double pricePerTeu;
	private double dayClausePenalty;
	private int dayForDelivery;	

	private Port destination;
	

	public TransportContract(Product product, int teu, int dwtPerTeu, double pricePerTeu, double dayClausePenalty, int dayForDelivery, Port destination) {
		super();
		this.id = UUID.randomUUID().toString();		
		this.product = product;
		this.teu = teu;
		this.dwtPerTeu = dwtPerTeu;
		this.pricePerTeu = pricePerTeu;
		this.dayClausePenalty = dayClausePenalty;
		this.dayForDelivery = dayForDelivery;	
		this.destination = destination;
	}


	public String getId() {
		return id;
	}

	public Port getDestinationPort() {
		return destination;
	}		
	
	public Product getProduct() {
		return product;
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

	public double getTotalPrice() {
		return teu*pricePerTeu;
	}

	public double getDayClausePenalty() {
		return dayClausePenalty;
	}

	public int getDayForDelivery() {
		return dayForDelivery;
	}

	public void reduceDayForDelivery() {
		dayForDelivery-=1;
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
