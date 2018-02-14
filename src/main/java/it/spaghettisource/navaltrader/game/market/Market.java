package it.spaghettisource.navaltrader.game.market;

public interface Market {

	public int[] productDemand();
	public int[] productSupply();
	
	
	
	public double getBasePriceForProduct(int productId);
	
	
	
	
	
}
