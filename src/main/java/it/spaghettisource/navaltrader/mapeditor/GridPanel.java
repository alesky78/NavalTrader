package it.spaghettisource.navaltrader.mapeditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrade.pathfinding.Cell;
import it.spaghettisource.navaltrade.pathfinding.Grid;
import it.spaghettisource.navaltrader.geometry.Point;


public class GridPanel extends JPanel {

	static Log log = LogFactory.getLog(GridPanel.class.getName());

	private Grid grid;
	private Point[] foundPath;
	private Cell startCell;
	private Cell endCell;
	
	private BufferedImage background;
	
	private int panelSize;
	private int cellSize;
	
	private boolean drawGrid;		
	
	private Color BLACK; 
	private Color GRAY;
	private Color WHITE;	
	private Color RED;
	private Color YELLOW;
	private Color GREEN;
	private Color ORANGE;

	
	public GridPanel(Grid grid,int size){
		super();
		this.grid = grid;
		this.panelSize = size;
		cellSize = 6;
		foundPath = null;
		drawGrid = true;
		
		BLACK = Color.BLACK;
		GRAY = Color.GRAY;		
		WHITE = Color.WHITE;
		RED = Color.RED;		
		YELLOW = Color.YELLOW;	
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
		BLACK = new Color(BLACK.getRed(), BLACK.getGreen(), BLACK.getBlue(), alpha);
		GRAY = new Color(GRAY.getRed(), GRAY.getGreen(), GRAY.getBlue(), alpha);
		WHITE = new Color(WHITE.getRed(), WHITE.getGreen(), WHITE.getBlue(), alpha);
		RED = new Color(RED.getRed(), RED.getGreen(), RED.getBlue(), alpha);
		YELLOW = new Color(YELLOW.getRed(), YELLOW.getGreen(), YELLOW.getBlue(), alpha);
		GREEN = new Color(GREEN.getRed(), GREEN.getGreen(), GREEN.getBlue(), alpha);
		ORANGE = new Color(ORANGE.getRed(), ORANGE.getGreen(), ORANGE.getBlue(), alpha);		
		repaint();	
	}

	public Dimension getPreferredSize() {
		return new Dimension(panelSize, panelSize);
	}

	public void setPath(Point[] path){
		this.foundPath = path;
		repaint();
	}
	
	public void setGrid(Grid grid) {
		this.grid = grid;
		foundPath = null;
		startCell = null;
		endCell = null;		
		repaint();
	}

	public void setBackground(BufferedImage background) {
		this.background = background;
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
	
	public void addWallByScreenCoordinate(int x,int y) {
		Cell wall = getCellByScreenCoordinate(x, y);
		if(wall!=null) {
			wall.setWall(true);
			repaint();			
		}
	}	

	public void removeWallByScreenCoordinate(int x, int y) {
		Cell wall = getCellByScreenCoordinate(x, y);
		if(wall!=null) {
			wall.setWall(false);
			repaint();			
		}
	}	

	
	public Cell getCellByScreenCoordinate(int x,int y){
		float cellWidth = getWidth()/(float)grid.getSize();
		float cellHeight = getHeight()/(float)grid.getSize();		
		return grid.getCell((int)(x/cellWidth), (int)(y/cellHeight));
	}


	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintGrid(g);

	}

		
	private void paintGrid(Graphics graphicsPanel) {

		int width = grid.getSize();
		int height = grid.getSize();		
		
		width = grid.getSize() * cellSize + 1;	//+1 because is not consider index 0 in the size of the immage
		height = grid.getSize() * cellSize + 1;
		
		//the image of the grid is big exactly has the grid
		BufferedImage gridImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphicsGrid = gridImage.createGraphics();
		//g2d.setStroke(new BasicStroke(0.1f));

		drawBackground(width, height, graphicsGrid);			
		

		int actualX = 0;
		int actualY = 0;

		Cell cell;

		for (int x = 0; x < grid.getSize(); x++) {
			for (int y = 0; y < grid.getSize(); y++) {

				cell = grid.getCell(x, y);

				if(cell.isWall()){
					graphicsGrid.setColor(GRAY);
					graphicsGrid.fillRect(actualX, actualY, cellSize, cellSize);
				}
				
				if(cell.isVisited()){
					graphicsGrid.setColor(RED);					
					graphicsGrid.fillRect(actualX, actualY, cellSize, cellSize);
				}
				
				if(drawGrid){
					graphicsGrid.setColor(BLACK);
					graphicsGrid.drawRect(actualX, actualY, cellSize, cellSize);							
				}

				actualY = actualY + cellSize;	
			}		
			actualX = actualX + cellSize;		
			actualY = 0;			
		}


		//draw found path
		if(foundPath!=null && foundPath.length != 0){
			for (int i = 0; i < foundPath.length; i++) {
				graphicsGrid.setColor(YELLOW);			
				graphicsGrid.fillRect(foundPath[i].getX()*cellSize, foundPath[i].getY()*cellSize, cellSize, cellSize);	
				graphicsGrid.setColor(BLACK);
				graphicsGrid.drawRect(foundPath[i].getX()*cellSize, foundPath[i].getY()*cellSize, cellSize, cellSize);			
			}
				
		}

		//draw strat end point path
		if(startCell!=null){
			graphicsGrid.setColor(GREEN);			
			graphicsGrid.fillRect(startCell.getX()*cellSize, startCell.getY()*cellSize, cellSize, cellSize);	
			graphicsGrid.setColor(BLACK);
			graphicsGrid.drawRect(startCell.getX()*cellSize, startCell.getY()*cellSize, cellSize, cellSize);					
		}

		if(endCell!=null){
			graphicsGrid.setColor(ORANGE);			
			graphicsGrid.fillRect(endCell.getX()*cellSize, endCell.getY()*cellSize, cellSize, cellSize);	
			graphicsGrid.setColor(BLACK);
			graphicsGrid.drawRect(endCell.getX()*cellSize, endCell.getY()*cellSize, cellSize, cellSize);				

		}		

		graphicsPanel.drawImage(gridImage,0,0,getWidth(),getHeight(),0,0,gridImage.getWidth(),gridImage.getHeight(),null);		
	}

	private void drawBackground(int width, int height, Graphics2D graphicsGrid) {
//		if(background==null) {
//			//clean the background of the screen
//			graphicsGrid.setColor(WHITE);	    
//			graphicsGrid.fillRect(0, 0, getWidth(), getHeight());	
//		}else {
			try {
				background = ImageIO.read(GridPanel.class.getResourceAsStream("/scenario/world.png")); 	
				graphicsGrid.drawImage(background,0,0,width,height,0,0,background.getWidth(),background.getHeight(),null);
			} catch (IOException e) {
				log.error("error creating background immage", e);
			}		
			
//		}
		
		
	}

	

}
