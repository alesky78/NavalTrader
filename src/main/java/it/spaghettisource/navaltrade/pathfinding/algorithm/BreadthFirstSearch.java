package it.spaghettisource.navaltrade.pathfinding.algorithm;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrade.pathfinding.Cell;
import it.spaghettisource.navaltrade.pathfinding.Grid;

/**
 * simple algorithm 
 * 
 * 
 * @author Alessandro
 *
 */
public class BreadthFirstSearch  implements PathFinding {
	
	static Log log = LogFactory.getLog(BreadthFirstSearch.class.getName());
	
	private List<Cell> open;
	
	public BreadthFirstSearch() {
		super();
		open = new ArrayList<Cell>();
	}



	public List<Cell> search(Grid grid, Cell start, Cell end) {
		
		boolean allowDiagonal = false;
		
		boolean found = false;
		
		int indexCounter = 0;
		int totalCell = grid.getTotalCell();

		
		Cell startCell = grid.getCell(end.getX(), end.getY());
		startCell.setVisited(true);
		
		end.setStep(0);			//initialize first step
		open.add(startCell);	//start from end node
		
		Cell actualCell = null;
		List<Cell> newCell;		
			
		
		while(!found && indexCounter < totalCell) {
			
			actualCell = open.get(indexCounter);
			indexCounter++;			
			
			if(actualCell.equals(start)) {
				found = true;
			}else {
				newCell = grid.getAdjacentcellsNotVisited(actualCell,allowDiagonal);
				for (Cell cell : newCell) {
					cell.setVisited(true);
					cell.setStep(actualCell.getStep()+1);						
					open.add(cell);
				}
			}
			
		}//end while
		
		List<Cell> foundPath = new ArrayList<Cell>();		
		
		if(found) { //crete reverse path
			foundPath.add(actualCell);	//in this moment actualCell is valorized with the start node because found = true
			for (int i = actualCell.getStep()-1; i >= 0; i--) {
				newCell = grid.getAdjacentcells(actualCell,allowDiagonal);
				for (Cell cell : newCell) {
					if(cell.getStep()==i) {
						foundPath.add(cell);
						actualCell = cell;
						break;
					}
				}
				
			}
			
		}
		
		return foundPath;

	}
	
}
