package it.spaghettisource.navaltrader.geometry;

/**
 * Math operations
 * 
 * @author Alessandro
 *
 */
public class Mathematic {

	private Mathematic() {
	}
	
	public static int distance(Point a, Point b) {
		return (int) Math.sqrt( Math.pow(b.getX()-a.getX(), 2) + Math.pow(b.getY()-a.getY(), 2));
	}
	
	public static Point scale(Point point, int scale) {
		return new Point(point.getX()*scale, point.getY()*scale);
	}
	
}
