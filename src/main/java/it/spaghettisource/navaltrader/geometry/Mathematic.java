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
		return (int) Math.sqrt(  powBy2(b.getX()-a.getX()) + powBy2(b.getY()-a.getY()) );
	}
	
	public static Point scale(Point point, int scale) {
		return new Point(point.getX()*scale, point.getY()*scale);
	}

	public static int powBy2(int value) {
		return  (int) Math.pow(value, 2);
	}

	
}
