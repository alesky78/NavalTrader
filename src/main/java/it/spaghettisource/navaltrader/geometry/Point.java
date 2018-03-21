package it.spaghettisource.navaltrader.geometry;

public class Point {

	private double x,y;
	
	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public Point(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}	

	public double getX() {
		return x;
	}
	
	public int getIntX() {
		return (int)x;
	}	

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}
	
	public int getIntY() {
		return (int)y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public boolean equals(Object obj){
		if(obj==null){
			return false;
		}else if(!(obj instanceof Point)){
			return false;
		}else{
			return x==((Point)obj).x && y==((Point)obj).y;
		}	
	}	
	
	
}
