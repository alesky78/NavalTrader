package it.spaghettisource.navaltrader.game.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import it.spaghettisource.navaltrader.game.factory.ContractFactory;
import it.spaghettisource.navaltrader.game.loop.Entity;

public class Market  implements Entity{
	
	
	private Product[] products;
	private Product[] demandProducts;	
	private Product[] supplyProducts;	

	
	private int dayContractRegeneration;	
	private int dayToNextContractRegeneration;
	
	private List<TransportContract> contracts;
	private Port port;
	
	public Market(Port port,List<Product> products, int[] demandProductsId, int[] supplyProductsId,int dayContractRegeneration)  {
		super();
		
		this.port = port;
		this.dayContractRegeneration = dayContractRegeneration;
		this.dayToNextContractRegeneration = dayContractRegeneration;
		this.contracts = new ArrayList<TransportContract>(0);		
		
		this.products = new Product[products.size()];
		for (Product product : products) {
			this.products[product.getId()] = product;
		}
	
		this.demandProducts = new Product[demandProductsId.length];			
		for (int i = 0; i < demandProductsId.length; i++) {
			this.demandProducts[i] = this.products[demandProductsId[i]]; 
		}
		
		this.supplyProducts = new Product[supplyProductsId.length];
		for (int i = 0; i < supplyProductsId.length; i++) {
			this.supplyProducts[i] = this.products[supplyProductsId[i]]; 
		}		
		
		
	}

	public TransportContract removeContractById(String contractId) {
		TransportContract selected = null;
		for (TransportContract transportContract : contracts) {
			if(transportContract.getId().equals(contractId)) {
				selected = transportContract;
			}
		}
		
		if(selected!=null) {
			contracts.remove(selected);			
		}
		return selected;
	}
	
	public List<TransportContract> getContracts() {
		return contracts;
	}
	
	public void generateContracts(){		
		//remove the old contracts and generate new contracts
		contracts.clear();
		contracts.addAll(ContractFactory.generateContracts(port.getWorld(), port)) ;

		dayToNextContractRegeneration = dayContractRegeneration;
	}	
	
	
	public Product[] productDemand() {
		return demandProducts;
	}
	
	public boolean demandThis(Product product) {
		for (int i = 0; i < demandProducts.length; i++) {
			if(demandProducts[i].equals(product)) {
				return true;
			}
		}
		return false;
	}
	
	public Product[] productSupply() {
		return supplyProducts;		
	}
	
	public boolean supplyThis(Product product) {
		for (int i = 0; i < supplyProducts.length; i++) {
			if(supplyProducts[i].equals(product)) {
				return true;
			}
		}
		return false;
	}	
	
	

	public void update(int minutsPassed, boolean isNewDay, boolean isNewWeek, boolean isNewMonth) {
		
		if(isNewDay){
			dayToNextContractRegeneration = dayToNextContractRegeneration-1;
		}

		if(dayToNextContractRegeneration == 0){
			generateContracts();	

		}

		
	}
	
}
