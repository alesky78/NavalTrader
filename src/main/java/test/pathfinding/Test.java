package test.pathfinding;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import test.pathfinding.algorithm.AStar;
import test.pathfinding.algorithm.BreadthFirstSearch;

public class Test {

	static Log log = LogFactory.getLog(Test.class.getName());
	
	public static void main(String[] args) {
		
		int gridSize = 7000;
		
		Grid grid = new Grid(gridSize);
		
		grid.resetCells();
		stratBreadthFirstSearch(gridSize, grid);
		
		grid.resetCells();
		stratAStar(gridSize, grid);
		
		
	}

	private static void stratBreadthFirstSearch(int gridSize, Grid grid) {
		log.info("strat BreadthFirstSearch");
		BreadthFirstSearch finder = new BreadthFirstSearch();
		
		double start  = System.currentTimeMillis();
		
		List<Cell> path = finder.search(grid, new Cell(gridSize/2, gridSize/2), new Cell(gridSize-1, gridSize-1));
		
		double end  = System.currentTimeMillis();
		
//		for (Cell cell : path) {
//			log.info("step "+cell);
//		}
		
		log.info("total time seconds:"+(end-start)/1000);
	}
	
	private static void stratAStar(int gridSize, Grid grid) {
		log.info("strat AStar");		
		AStar finder = new AStar();
		
		double start  = System.currentTimeMillis();
		
		List<Cell> path = finder.search(grid, new Cell(gridSize/2, gridSize/2), new Cell(gridSize-1, gridSize-1));
		
		double end  = System.currentTimeMillis();
		
//		for (Cell cell : path) {
//			log.info("step "+cell);
//		}
		
		log.info("total time seconds:"+(end-start)/1000);
	}	
	
}
