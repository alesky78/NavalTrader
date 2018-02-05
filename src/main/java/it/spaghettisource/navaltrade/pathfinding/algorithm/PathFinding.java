package it.spaghettisource.navaltrade.pathfinding.algorithm;

import java.util.List;

import it.spaghettisource.navaltrade.pathfinding.Cell;
import it.spaghettisource.navaltrade.pathfinding.Grid;

public interface PathFinding {

	public List<Cell> search(Grid grid, Cell start, Cell end);
	
	
}