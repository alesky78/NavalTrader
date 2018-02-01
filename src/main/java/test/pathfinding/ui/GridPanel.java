package test.pathfinding.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import test.pathfinding.Cell;
import test.pathfinding.Grid;

public class GridPanel extends JPanel {

	static Log log = LogFactory.getLog(GridPanel.class.getName());

	private Grid grid;
	private int size;
	private List<Cell> path;
	private Cell startCell;
	private Cell endCell;
	private boolean drawGrid;		
	
	private Color BLACK; 
	private Color WHITE;	
	private Color RED;
	private Color BLUE;
	private Color GREEN;
	private Color ORANGE;
	private int alpha = 256;

	
	public GridPanel(Grid grid,int size){
		super();
		this.grid = grid;
		this.size = size;
		path = new LinkedList<Cell>();
		drawGrid = true;
		
		BLACK = Color.BLACK;
		WHITE = Color.WHITE;
		RED = Color.RED;		
		BLUE = Color.BLUE;	
		GREEN = Color.GREEN;	
		ORANGE = Color.ORANGE;	
	}
	
	public boolean isDrawGrid() {
		return drawGrid;
	}
	
	public void setDrawGrid(boolean drawGrid) {
		this.drawGrid = drawGrid;
		repaint();		
	}

	public void setAlpha(int alpha) {
		this.alpha = alpha;
		BLACK = new Color(BLACK.getRed(), BLACK.getGreen(), BLACK.getBlue(), alpha);
		WHITE = new Color(WHITE.getRed(), WHITE.getGreen(), WHITE.getBlue(), alpha);
		RED = new Color(RED.getRed(), RED.getGreen(), RED.getBlue(), alpha);
		BLUE = new Color(BLUE.getRed(), BLUE.getGreen(), BLUE.getBlue(), alpha);
		GREEN = new Color(GREEN.getRed(), GREEN.getGreen(), GREEN.getBlue(), alpha);
		ORANGE = new Color(ORANGE.getRed(), ORANGE.getGreen(), ORANGE.getBlue(), alpha);		
		repaint();	
	}

	public Dimension getPreferredSize() {
		return new Dimension(size, size);
	}

	public void setPath(List<Cell> path){
		this.path = path;
		repaint();
	}
	
	public void setGrid(Grid grid) {
		this.grid = grid;
		size = grid.getSize();
		path = null;
		startCell = null;
		endCell = null;		
		repaint();
	}

	public Cell setStartCellByScreenCoordinate(int x,int y) {
		startCell = getCellByScreenCoordinate(x, y);
		repaint();	
		return startCell;
	}

	public Cell setEndCellByScreenCoordinate(int x,int y) {
		endCell = getCellByScreenCoordinate(x, y);
		repaint();
		return endCell;		
	}
	
	public Cell addWallByScreenCoordinate(int x,int y) {
		Cell wall = getCellByScreenCoordinate(x, y);
		wall.setWall(true);
		repaint();
		return endCell;		
	}	

	public Cell removeWallByScreenCoordinate(int x, int y) {
		Cell wall = getCellByScreenCoordinate(x, y);
		wall.setWall(false);
		repaint();
		return endCell;	
	}	

	
	private Cell getCellByScreenCoordinate(int x,int y){
		float cellWidth = getWidth()/(float)grid.getSize();
		float cellHeight = getHeight()/(float)grid.getSize();		
		return grid.getCell((int)(x/cellWidth), (int)(y/cellHeight));
	}


	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintGrid(g);

	}

		
	private void paintGrid(Graphics g) {

		int width = grid.getSize();
		int height = grid.getSize();		
		
		int cellSize = 6;
		width = width * cellSize + 1;	//+1 because is not consider index 0 in the size of the immage
		height = height * cellSize + 1;
		
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bufferedImage.createGraphics();
		//g2d.setStroke(new BasicStroke(0.1f));

		//clean the background of the screen
		g2d.setColor(WHITE);	    
		g2d.fillRect(0, 0, getWidth(), getHeight());

		int actualX = 0;
		int actualY = 0;

		Cell cell;

		for (int x = 0; x < grid.getSize(); x++) {
			for (int y = 0; y < grid.getSize(); y++) {

				cell = grid.getCell(x, y);

				if(cell.isWall()){
					g2d.setColor(BLACK);
					g2d.fillRect(actualX, actualY, cellSize, cellSize);
				}
				
				if(cell.isVisited()){
					g2d.setColor(RED);					
					g2d.fillRect(actualX, actualY, cellSize, cellSize);
				}
				
				if(drawGrid){
					g2d.setColor(BLACK);
					g2d.drawRect(actualX, actualY, cellSize, cellSize);							
				}

				actualY = actualY + cellSize;	
			}		
			actualX = actualX + cellSize;		
			actualY = 0;			
		}


		//draw found path
		if(path!=null && !path.isEmpty()){
			for (Cell cellPath : path) {
				g2d.setColor(BLUE);			
				g2d.fillRect(cellPath.getX()*cellSize, cellPath.getY()*cellSize, cellSize, cellSize);	
				g2d.setColor(BLACK);
				g2d.drawRect(cellPath.getX()*cellSize, cellPath.getY()*cellSize, cellSize, cellSize);							
			}			
		}

		//draw strat end point path
		if(startCell!=null){
			g2d.setColor(GREEN);			
			g2d.fillRect(startCell.getX()*cellSize, startCell.getY()*cellSize, cellSize, cellSize);	
			g2d.setColor(BLACK);
			g2d.drawRect(startCell.getX()*cellSize, startCell.getY()*cellSize, cellSize, cellSize);					
		}

		if(endCell!=null){
			g2d.setColor(ORANGE);			
			g2d.fillRect(endCell.getX()*cellSize, endCell.getY()*cellSize, cellSize, cellSize);	
			g2d.setColor(BLACK);
			g2d.drawRect(endCell.getX()*cellSize, endCell.getY()*cellSize, cellSize, cellSize);				

		}		

		g.drawImage(bufferedImage,0,0,getWidth(),getHeight(),0,0,bufferedImage.getWidth(),bufferedImage.getHeight(),null);		
	}



}
