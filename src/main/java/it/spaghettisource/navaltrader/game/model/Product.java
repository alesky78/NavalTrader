package it.spaghettisource.navaltrader.game.model;

public class Product {

	private int id;
	private String name;
	private double minprice;
	private double maxprice;
	private int dwt;
	
	public Product(int id, String name, double minprice, double maxprice, int dwt) {
		super();
		this.id = id;
		this.name = name;
		this.minprice = minprice;
		this.maxprice = maxprice;
		this.dwt = dwt;
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
