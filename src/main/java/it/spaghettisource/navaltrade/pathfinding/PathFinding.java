package it.spaghettisource.navaltrade.pathfinding;

import java.util.List;

import it.spaghettisource.navaltrader.graphic.Point;

public interface PathFinding {

	public List<Point> search(Grid grid, Point start, Point end);
	
	
}