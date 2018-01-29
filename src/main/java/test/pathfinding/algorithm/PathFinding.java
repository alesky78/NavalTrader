package test.pathfinding.algorithm;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import test.pathfinding.Cell;
import test.pathfinding.Grid;

/**
 * simple algorithm 
 * 
 * 
 * @author Alessandro
 *
 */
public class PathFinding {
	
	static Log log = LogFactory.getLog(PathFinding.class.getName());
	
	private List<Cell> open;
	
	public PathFinding() {
		super();
		open = new ArrayList<Cell>();
	}



	public List<Cell> search(Grid grid, Cell start, Cell end) {
		
		boolean found = false;
		int indexCounter = 0;
		int totalCell = grid.totalCell();

		
		Cell startCell = grid.getCell(end.getX(), end.getY());
		startCell.setVisited(true);
		
		end.setStep(0);			//initialize first step
		open.add(startCell);	//start from end node
		
		Cell actualCell = null;
		List<Cell> newCell;		
			
		
		while(!found || indexCounter < totalCell) {
			
			actualCell = open.get(indexCounter);
			indexCounter++;			
			if(indexCounter%1000==0) {
				log.debug("ceck:"+indexCounter+" of "+totalCell);				
			}
			
			
			if(actualCell.equals(start)) {
				found = true;
			}else {
				newCell = grid.getAdjacentcellsNotVisited(actualCell);
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
				newCell = grid.getAdjacentcells(actualCell);
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