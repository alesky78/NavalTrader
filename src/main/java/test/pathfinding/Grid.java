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
	
	
	public List<Cell> getAdjacentcells(Cell actualCell, boolean allowDiagonal){
		List<Cell> adjacent = new ArrayList<Cell>();
		int x = actualCell.getX();
		int y = actualCell.getY();
		
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				if ((i == x && j == y) || i < 0 || j < 0 || j >= size|| i >=size) {
					continue;
				}

				if (!allowDiagonal && ((i < x && j < y) || (i > x && j > y) || (i > x && j < y) || (i < x && j > y))) {
					continue;
				}

				adjacent.add(grid[i][j]);
			}
		}
		
		return adjacent;
	}
	
	
	public List<Cell> getAdjacentcellsNotVisited(Cell actualCell, boolean allowDiagonal) {
		List<Cell> cells = new LinkedList<Cell>();  
		for (Cell cell : getAdjacentcells(actualCell,allowDiagonal)) {
			if(!cell.isVisited()) {
				cells.add(cell);
			}
		}
		
		return cells;
	}	
	
	
	
}
