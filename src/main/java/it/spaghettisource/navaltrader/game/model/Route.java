package it.spaghettisource.navaltrader.game.model;

import it.spaghettisource.navaltrader.geometry.Mathematic;
import it.spaghettisource.navaltrader.geometry.Point;

/**
 * 
 * 
 * 
 * @author Alessandro
 *
 */
public class Route {

	private Port destination;
	private int distanceInScale;	
	private Point[] path;
	private Point[] pathInScale;	
	
	public Route(Port destination, int scale, Point[] path) {
		super();
		this.destination = destination;
		this.path = path;

		pathInScale = new Point[path.length];
		Point point;
		for (int i = 0; i < path.length; i++) {
			point = path[i];
			pathInScale[i] = Mathematic.scale(point, scale);
		}
		
		//calcualte real distance
		this.distanceInScale = 0;
		for (int i = 1; i < pathInScale.length; i++) {
			distanceInScale += Mathematic.distance(pathInScale[i], pathInScale[i-1]);
		}		
		
	}
		
	public int getDistanceInScale() {
		return distanceInScale;
	}

	public Port getDestination() {
		return destination;
	}

	public boolean isDestination(Port port) {
		return destination.equals(port);
	}
	
	public int calcDaysToDestination(int speed) {
		return distanceInScale/(speed*24);
	}	

	public Point[] getPath() {
		return path;
	}

	public Point[] getPathInScale() {
		return pathInScale;
	}
	

	

	
	
	
}
