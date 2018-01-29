package test.pathfinding;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import test.pathfinding.algorithm.PathFinding;

public class Test {

	static Log log = LogFactory.getLog(Test.class.getName());
	
	public static void main(String[] args) {
		
		int gridSize = 4000;
		
		Grid grid = new Grid(gridSize);
		
		PathFinding finder = new PathFinding();
		
		double start  = System.currentTimeMillis();
		
		List<Cell> path = finder.search(grid, new Cell(0, 0), new Cell(gridSize-1, gridSize-1));
		
		double end  = System.currentTimeMillis();
		
		for (Cell cell : path) {
			log.info("step "+cell);
		}
		
		
		log.info("total time seconds:"+(end-start)/1000);
		
		
		
	}
	
}
