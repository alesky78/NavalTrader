package test.pathfinding;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Grid {

	static Log log = LogFactory.getLog(Grid.class.getName());
	
	private int size;
	private int totalCell;	
	private Cell[][] grid;

	public Grid(int size) {
		super();
		this.size = size;
		grid = new Cell[size][size];
		
		for (int x = 0; x<size; x++) {
			for (int y = 0; y<size; y++) {
				grid[x][y] = new Cell(x, y);
			}	
		}
		
		totalCell = size*size;
	}
	
	public int totalCell() {
		return totalCell;
	}
	
	public void resetCells() {
		for (int x = 0; x<size; x++) {
			for (int y = 0; y<size; y++) {
				grid[x][y].reset();
			}	
		}
	}
	
	public Cell getCell(int x, int y) {
		return grid[x][y];
	}
	

	public List<Cell> getAdjacentcells(Cell actualCell) {

		List<Cell> adjacent = new ArrayList<Cell>();
		
		//check up
		if(actualCell.getY()>0 && actualCell.getY()<size) {
			adjacent.add(grid[actualCell.getX()][actualCell.getY()-1]);
		}
		
		//check down
		if(actualCell.getY()>=0 && actualCell.getY()<size-1) {
			adjacent.add(grid[actualCell.getX()][actualCell.getY()+1]);
		}		
		
		//check left
		if(actualCell.getX()>0 && actualCell.getX()<size) {
			adjacent.add(grid[actualCell.getX()-1][actualCell.getY()]);
		}

		//check right
		if(actualCell.getX()>=0 && actualCell.getX()<size-1) {
			adjacent.add(grid[actualCell.getX()+1][actualCell.getY()]);
		}

		return adjacent;
	}
	
	
	public List<Cell> getAdjacentcellsNotVisited(Cell actualCell) {
		List<Cell> cells = new LinkedList<Cell>();  
		for (Cell cell : getAdjacentcells(actualCell)) {
			if(!cell.isVisited()) {
				cells.add(cell);
			}
		}
		
		return cells;
	}	
	
	
	
}
