package it.spaghettisource.navaltrade.pathfinding;

import it.spaghettisource.navaltrader.geometry.Point;

public interface PathFinding {

	public Point[] search(Grid grid, Point start, Point end, boolean allowDiagonal);
	
	
}