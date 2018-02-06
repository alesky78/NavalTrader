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
		this.distanceInScale = (path.length-1)*scale;
		
		pathInScale = new Point[path.length];
		Point point;
		for (int i = 0; i < path.length; i++) {
			point = path[i];
			pathInScale[i] = Mathematic.scale(point, scale);
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

	public Point[] getPath() {
		return path;
	}

	public Point[] getPathInScale() {
		return pathInScale;
	}
	

	

	
	
	
}
