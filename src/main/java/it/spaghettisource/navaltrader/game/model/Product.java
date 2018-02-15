package it.spaghettisource.navaltrader.game.model;

public class Product {

	private int id;
	private String name;
	private double minprice;
	private double maxprice;
	
	public Product(int id, String name, double minprice, double maxprice) {
		super();
		this.id = id;
		this.name = name;
		this.minprice = minprice;
		this.maxprice = maxprice;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getMinprice() {
		return minprice;
	}

	public double getMaxprice() {
		return maxprice;
	}	

	
}
