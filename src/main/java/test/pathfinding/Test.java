package test.pathfinding;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import test.pathfinding.algorithm.AStar;
import test.pathfinding.algorithm.BreadthFirstSearch;
import test.pathfinding.algorithm.PathFinding;

public class Test {

	static Log log = LogFactory.getLog(Test.class.getName());
	
	public static void main(String[] args) {
		
		int gridSize = 5000;	//max size of the word in miles is 12000 ps out of memory????

		double start  = System.currentTimeMillis();
		Grid grid = new Grid(gridSize);
		double end  = System.currentTimeMillis();		
		log.info("total create grid seconds:"+(end-start)/1000);		
		
		resetGrid(grid);
		stratAStar(gridSize, grid);		
		
		resetGrid(grid);
		stratBreadthFirstSearch(gridSize, grid);
		

		
		
	}

	private static void resetGrid(Grid grid) {
		double start;
		double end;
		start  = System.currentTimeMillis();
		grid.resetCells();
		end  = System.currentTimeMillis();		
		log.info("total reset time seconds:"+(end-start)/1000);
	}

	private static void stratBreadthFirstSearch(int gridSize, Grid grid) {
		log.info("strat BreadthFirstSearch");
		PathFinding finder = new BreadthFirstSearch();
		
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
		PathFinding finder = new AStar();
		
		double start  = System.currentTimeMillis();
		
		List<Cell> path = finder.search(grid, new Cell(gridSize/2, gridSize/2), new Cell(gridSize-1, gridSize-1));
		
		double end  = System.currentTimeMillis();
		
//		for (Cell cell : path) {
//			log.info("step "+cell);
//		}
		
		log.info("total time seconds:"+(end-start)/1000);
	}	
	
}
