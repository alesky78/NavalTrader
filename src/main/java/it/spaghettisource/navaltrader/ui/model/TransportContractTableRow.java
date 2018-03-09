package it.spaghettisource.navaltrader.ui.model;

import java.util.ArrayList;
import java.util.List;

import it.spaghettisource.navaltrader.game.model.Port;
import it.spaghettisource.navaltrader.game.model.TransportContract;

public class TransportContractTableRow {

	private String id;
	private String productName;
	private int totalTeu;
	private int totalDwt;		
	private double pricePerTeu;
	private double totalPrice;
	private Port destination;	
	private int distance;
	private int daysToDestination;	
	private double dayClausePenalty;
	private int dayForDelivery;	
	

	
	public TransportContractTableRow(String id, String productName, int totalTeu, int totalDwt, double pricePerTeu,double totalPrice, double dayClausePenalty, int dayForDelivery, Port destination) {
		super();
		this.id = id;
		this.productName = productName;
		this.totalTeu = totalTeu;
		this.totalDwt = totalDwt;
		this.pricePerTeu = pricePerTeu;
		this.totalPrice = totalPrice;
		this.dayClausePenalty = dayClausePenalty;
		this.dayForDelivery = dayForDelivery;
		this.destination = destination;
		this.daysToDestination = 0;
	}

	public String getId() {
		return id;
	}
	
	public String getProductName() {
		return productName;
	}

	public int getTotalTeu() {
		return totalTeu;
	}
	
	public int getTotalDwt() {
		return totalDwt;
	}
	
	public double getPricePerTeu() {
		return pricePerTeu;
	}
	
	public double getTotalPrice() {
		return totalPrice;
	}
	
	public double getDayClausePenalty() {
		return dayClausePenalty;
	}

	public int getDayForDelivery() {
		return dayForDelivery;
	}

	public Port getDestinationPort() {
		return destination;
	}

	public String getDestinationPortName() {
		return destination.getName();
	}
	
	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getDistance() {
		return distance;
	}
		
	public int getDaysToDestination() {
		return daysToDestination;
	}
	
	public void setDaysToDestination(int daysToDestination) {
		this.daysToDestination = daysToDestination;
	}

	public boolean equals(Object obj){
		if(obj==null){
			return false;
		}else if(!(obj instanceof TransportContractTableRow)){
			return false;
		}else{
			return id.equals(((TransportContractTableRow)obj).getId());
		}	
	}	

	public static List<TransportContractTableRow> mapData(List<TransportContract> listOfContract){
		
		List<TransportContractTableRow> LoanTableRow = new ArrayList<TransportContractTableRow>();
		for (TransportContract contract : listOfContract) {
			LoanTableRow.add(mapData(contract));
		}
		
		return LoanTableRow;
	}
	
	public static TransportContractTableRow mapData(TransportContract contract){
		return new TransportContractTableRow(contract.getId(), contract.getProduct().getName(), contract.getTeu(), 
											 contract.getTeu()*contract.getDwtPerTeu(), 
											 contract.getPricePerTeu(), 
											 contract.getTotalPrice(), 
											 contract.getDayClausePenalty(),
											 contract.getDayForDelivery(),
											 contract.getDestinationPort());	
	}		
	
	
}
