package it.spaghettisource.navaltrade.pathfinding;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Grid {

	static Log log = LogFactory.getLog(Grid.class.getName());
	
	private int width;	
	private int height;	
	private int totalCell;	
	private Cell[][] grid;

	/**
	 * make a perfect square grid
	 * 
	 * @param size
	 */
	public Grid(int size) {
		super();
		width = size;
		height = size;		
		
		grid = new Cell[width][height];
		
		for (int x = 0; x<size; x++) {
			for (int y = 0; y<size; y++) {
				grid[x][y] = new Cell(x, y);
			}	
		}
		
		totalCell = width*height;
	}
	
	/**
	 * make a perfect square grid
	 * 
	 * @param size
	 */
	public Grid(int width,int height) {
		super();
		this.width = width;
		this.height = height;		
		
		grid = new Cell[width][height];
		
		for (int x = 0; x<width; x++) {
			for (int y = 0; y<height; y++) {
				grid[x][y] = new Cell(x, y);
			}	
		}
		
		totalCell = width*height;
	}	
		
		


	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getTotalCell() {
		return totalCell;
	}
	
	public void resetCells() {
		for (int x = 0; x<width; x++) {
			for (int y = 0; y<height; y++) {
				grid[x][y].reset();
			}	
		}
	}
	
	public Cell getCell(int x, int y) {
		if(x < 0 || y < 0 || x > (width-1) || y > (height-1)) {
			return null;
		}
		
		return grid[x][y];
	}
	
	
	public List<Cell> getAdjacentcells(Cell actualCell, boolean allowDiagonal){
		List<Cell> adjacent = new ArrayList<Cell>();
		int x = actualCell.getX();
		int y = actualCell.getY();
		
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				if ((i == x && j == y) || i < 0 || j < 0 || j >= height|| i >=width) {
					continue;
				}

				if (!allowDiagonal && ((i < x && j < y) || (i > x && j > y) || (i > x && j < y) || (i < x && j > y))) {
					continue;
				}
				
				if(!grid[i][j].isWall()){
					adjacent.add(grid[i][j]);					
				}

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
