package test.pathfinding.algorithm;

import java.util.List;

import test.pathfinding.Cell;
import test.pathfinding.Grid;

public interface PathFinding {

	public List<Cell> search(Grid grid, Cell start, Cell end);
	
	
}