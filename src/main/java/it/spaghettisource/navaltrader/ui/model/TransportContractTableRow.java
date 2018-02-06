package it.spaghettisource.navaltrader.ui.model;

import java.util.ArrayList;
import java.util.List;

import it.spaghettisource.navaltrader.game.model.Route;
import it.spaghettisource.navaltrader.game.model.TransportContract;
import it.spaghettisource.navaltrader.geometry.Point;

public class TransportContractTableRow {

	private String id;
	private String good;
	private int totalTeu;
	private int totalDwt;		
	private double pricePerTeu;
	private double totalPrice;	
	private Route route;	
	
	public TransportContractTableRow(String id, String good, int totalTeu, int totalDwt, double pricePerTeu,double totalPrice, Route route) {
		super();
		this.id = id;
		this.good = good;
		this.totalTeu = totalTeu;
		this.totalDwt = totalDwt;
		this.pricePerTeu = pricePerTeu;
		this.totalPrice = totalPrice;
		this.route = route;
	}

	public String getId() {
		return id;
	}
	
	public String getGood() {
		return good;
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
	
	public String getDestinationPort() {
		return route.getDestination().getName();
	}
	
	public int getDistance() {
		return route.getDistanceInScale();
	}
	
	public Route getRoute() {
		return route;
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
		return new TransportContractTableRow(contract.getId(), contract.getGood(), contract.getTeu(), 
											 contract.getTeu()*contract.getDwtPerTeu(), 
											 contract.getPricePerTeu(), 
											 contract.getTeu()*contract.getPricePerTeu(), 
											 contract.getRoute());	
	}		
	
	
}
