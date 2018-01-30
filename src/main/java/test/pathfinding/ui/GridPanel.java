package test.pathfinding.ui;

import java.awt.BasicStroke;
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


	public GridPanel(Grid grid,int size){
		super();
		this.grid = grid;
		this.size = size;
		path = new LinkedList<Cell>();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintGrid(g);

	}

	public Dimension getPreferredSize() {
		return new Dimension(size, size);
	}

	public void setPath(List<Cell> path){
		this.path = path;
		repaint();
	}
	
	private void paintGrid(Graphics g) {

		BufferedImage bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
	    Graphics2D g2d = bufferedImage.createGraphics();
	    g2d.setStroke(new BasicStroke(0.1f));
	    
	    g2d.setColor(Color.WHITE);	    
	    g2d.fillRect(0, 0, getWidth(), getHeight());
	    
	    
		float cellWidth = getWidth()/(float)grid.getSize();
		float cellHeight = getHeight()/(float)grid.getSize();
		log.info("cellWidth:"+cellWidth+" cellHeight:"+cellHeight);		

		float windowX = 0;
		float windowY = 0;

		Cell cell;
		
		for (int x = 0; x < grid.getSize(); x++) {
			for (int y = 0; y < grid.getSize(); y++) {
				
				cell = grid.getCell(x, y);
				
				if(cell.isVisited()){
					g2d.setColor(Color.RED);					
				    g2d.fillRect((int)windowX, (int)windowY, (int)cellWidth, (int)cellHeight);
				}
				
				g2d.setColor(Color.BLACK);
				g2d.drawRect((int)windowX, (int)windowY, (int)cellWidth, (int)cellHeight);			
				windowY = windowY + cellHeight;
			}		
			windowX = windowX + cellWidth;		
			windowY = 0;			
		}
		
		//draw teh path
		for (Cell cellPath : path) {
			g2d.setColor(Color.BLUE);			
			g2d.fillRect((int)(cellPath.getX()*cellWidth), (int)(cellPath.getY()*cellHeight), (int)cellWidth, (int)cellHeight);	
			
		}
		
				
		
		g.drawImage(bufferedImage,0,0,getWidth(),getHeight(),0,0,bufferedImage.getWidth(),bufferedImage.getHeight(),null);		
	}

}
