package it.spaghettisource.navaltrader.game.model;

public class Product {

	private int id;
	private String name;
	private double price;
	private int dwt;
	
	public Product(int id, String name, double price, int dwt) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.dwt = dwt;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	
	public int getDwt() {
		return dwt;
	}

	public boolean equals(Object obj){
		if(obj==null){
			return false;
		}else if(!(obj instanceof Product)){
			return false;
		}else{
			return id == ((Product)obj).getId();
		}	
	}		

	
}
