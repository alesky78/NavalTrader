package it.spaghettisource.navaltrader.game.model;

import it.spaghettisource.navaltrader.geometry.Mathematic;
import it.spaghettisource.navaltrader.geometry.Point;

public class Route {

	private Port destination;
	
	private int totalDistance;	
	private int pathScale;
	private Point[] path;
	private Point[] pathInScale;	
	
	public Route(Port destination, int pathScale, Point[] path) {
		super();
		this.destination = destination;
		this.pathScale = pathScale;
		this.path = path;
		this.totalDistance = (path.length-1)*pathScale;
		
		pathInScale = new Point[path.length];
		Point point;
		for (int i = 0; i < path.length; i++) {
			point = path[i];
			pathInScale[i] = Mathematic.scale(point, pathScale);
		}	
	}
		
	public int getTotalDistance() {
		return totalDistance;
	}

	public Port getDestination() {
		return destination;
	}

	public boolean isDestination(Port port) {
		return destination.equals(port);
	}

	public int getPathScale() {
		return pathScale;
	}

	public Point[] getPath() {
		return path;
	}

	public Point[] getPathInScale() {
		return pathInScale;
	}
	

	

	
	
	
}
