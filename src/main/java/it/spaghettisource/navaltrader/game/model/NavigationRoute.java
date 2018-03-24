package it.spaghettisource.navaltrader.game.model;

import it.spaghettisource.navaltrader.geometry.Mathematic;
import it.spaghettisource.navaltrader.geometry.Point;

public class NavigationRoute {

	private Route route;

	private boolean arrived;

	private double totalNavigationHours;
	
	private int speed;
	private int actualPathIndex;
	private int latPathIndex;	
	private Point actualPosition;
	private Point[] path;

	public NavigationRoute(int speed, Route route) {
		super();
		this.speed = speed;
		this.route = route;

		totalNavigationHours = 0;
		
		arrived = false;
		path = route.getPathInScale();
		actualPathIndex = 1;
		latPathIndex = path.length-1;
		actualPosition = path[0];

	}
	
	public double getDegreeNavigationAngle() {
		return Mathematic.angleDegreesBetweenPoints(actualPosition, path[actualPathIndex]);
		
	}
	

	public Port getDestinationPort() {
		return route.getDestination();
	}

	public int getSpeed() {
		return speed;
	}	

	public boolean isArrivedAtDestination() {
		return arrived;
	}

	public double getTotalNavigationHours() {
		return totalNavigationHours;
	}

	public Point navigate(double hourPassed) {

		totalNavigationHours += hourPassed;
		
		double distance = Mathematic.distance(actualPosition, path[actualPathIndex]);
		double realSpeed = speed*hourPassed;
		
		if(distance > realSpeed) {	//move close to the next point

			actualPosition = Mathematic.move(actualPosition, path[actualPathIndex], realSpeed);

		}else { //ship will go over the next point

			if(actualPathIndex == latPathIndex) {//if the next point is the last one, ship is arrived
				arrived = true;
				actualPosition = path[latPathIndex];

			}else {
				
				// skip the first point and move to the second one of rest distance = realSpeed - distance 
				actualPosition = Mathematic.move(path[actualPathIndex], path[actualPathIndex+1], realSpeed - distance);

				actualPathIndex++;
			}
		}

		return actualPosition;

	}





}
