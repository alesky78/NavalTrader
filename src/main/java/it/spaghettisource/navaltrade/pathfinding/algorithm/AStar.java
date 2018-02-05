package it.spaghettisource.navaltrade.pathfinding.algorithm;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrade.pathfinding.Cell;
import it.spaghettisource.navaltrade.pathfinding.Grid;
import it.spaghettisource.navaltrader.graphic.Point;

/**
 * simple algorithm 
 * 
 * 
 * @author Alessandro
 *
 */
public class AStar implements PathFinding {

	static Log log = LogFactory.getLog(AStar.class.getName());

	private PriorityQueue<Cell> open;

	public AStar() {
		super();
	}



	public List<Cell> search(Grid grid, Point start, Point end) {

		boolean allowDiagonal = false;	
		boolean finish = false;		
		open = new PriorityQueue<Cell>();		
		
		Cell targetCell = grid.getCell(end.getX(), end.getY());		

		Cell actualCell = grid.getCell(start.getX(), start.getY());
		actualCell.setVisited(true);
		actualCell.setOpen(true);
		actualCell.calculatehCosts(targetCell); 			
		open.add(actualCell);	//start from first node

		while (!finish) {
			actualCell = open.poll(); // get node with lowest fCosts from openList
			actualCell.setVisited(true);

			if (actualCell.equals(targetCell)  ) { // found goal
				return calcPath( grid.getCell(start.getX(), start.getY()), actualCell);
			}

			// for all adjacent nodes:
			List<Cell> adjacentNodes =  grid.getAdjacentcellsNotVisited(actualCell, allowDiagonal);
			for (Cell cell : adjacentNodes) {
				if(!cell.isOpen()) {
					cell.setPrevious(actualCell); 				// set current node as previous for this node
					cell.calculatehCosts(targetCell); 					// set h costs of this node (estimated costs to goal)
					cell.calculategCosts(actualCell); 			// set g costs of this node (costs from start to this node)
					cell.setOpen(true);							//set node open		
					open.add(cell);								// add node to openList
				} else { // node is in openList
					if (cell.getgCosts() > (actualCell.getgCosts()+cell.getNodeCost())) { 	// if cost was higher then arrive to this node... use this node
						cell.setPrevious(actualCell);										// set current node as previous for this node
						cell.calculategCosts(actualCell); 									// set g costs of this node (costs from start to this node)
					}
				}
			}

			if (open.isEmpty()) { // no path exists
				finish = true; 
			}
		}

		return null;

	}

	private List<Cell> calcPath(Cell start, Cell goal) {
		LinkedList<Cell> path = new LinkedList<Cell>();

		Cell curr = goal;
		boolean done = false;
		while (!done) {
			path.addFirst(curr);
			curr = curr.getPrevious();

			if (curr.equals(start)) {
				path.addFirst(curr);			
				done = true;
			}
		}
		return path;
	}

}
