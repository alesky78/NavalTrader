package it.spaghettisource.navaltrader.geometry;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class Point {

	private int x,y;
	
	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void draw(Graphics g) { // draw itself
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawOval(x, y, 10, 10);		

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
