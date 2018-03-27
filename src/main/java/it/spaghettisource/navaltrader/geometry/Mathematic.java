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
	
	public static double distance(Point a, Point b) {
		return Math.sqrt(  powBy2(b.getX()-a.getX()) + powBy2(b.getY()-a.getY()) );
	}

	public static Point move(Point a, Point b, double amountToMove) {
		double distance = Mathematic.distance(a,b);
		
		double x = a.getX() + amountToMove/distance*(b.getX()-a.getX());
		double y = a.getY() + amountToMove/distance*(b.getY()-a.getY());		
		
		return new Point(x, y);
	}

	public static double angleRadiansBetweenPoints(Point p1, Point p2) {
		return Math.atan2(p2.getY() - p1.getY(), p2.getX() - p1.getX());
	}
	
	/**
	 * return a value between -180 to 180 extremes included
	 * 
	 * the angle is calculated by the x axis and the line generated between the two point passed as parameter
	 * 0 degree is over the x axis
	 * later the angle is positive on clock direction
	 * until -x that is 180 then starting from -179 to -1
	 * 
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static double angleDegreesBetweenPoints(Point p1, Point p2) {
		return  Math.toDegrees(Math.atan2(p2.getY() - p1.getY(), p2.getX() - p1.getX())); 
	}	
	
	public static Point scale(Point point, int scale) {
		return new Point(point.getX()*scale, point.getY()*scale);
	}

	public static double powBy2(double value) {
		return  Math.pow(value, 2);
	}
	

	

	
}
