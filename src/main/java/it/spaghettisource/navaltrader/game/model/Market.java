package it.spaghettisource.navaltrader.game.model;

import java.util.Arrays;
import java.util.List;

public class Market {
	
	private static int INITIAL_RESOURCES_AMOUNT = 1000;
	
	private Product[] products;
	private Product[] demandProducts;	
	private Product[] supplyProducts;	

	private int[] actualQuantityStored;	
	
	
	public Market(List<Product> products, int[] demandProductsId, int[] supplyProductsId) {
		super();
		
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
		
		actualQuantityStored = new int[products.size()];
		Arrays.fill(actualQuantityStored, INITIAL_RESOURCES_AMOUNT);
		
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
	
	/**
	 * P = a/((Q+1)/b) - c
	 * where 
	 * P is price
	 * Q is quantity
	 * a is the product max price
	 * b is index of reduction
	 * c is the price reduction for crisis
	 * 
	 * @param productId
	 * @return
	 */
	public double getPriceForBuy(Product product) {
		return products[product.getId()].getMaxprice() / ((actualQuantityStored[product.getId()]+1)/1000.0);	//TODO implement also the index of price reductions
		
	}
	
	public void addQuantityToMarket(Product product, int amount) {
		actualQuantityStored[product.getId()] += amount;
	}

	
}
