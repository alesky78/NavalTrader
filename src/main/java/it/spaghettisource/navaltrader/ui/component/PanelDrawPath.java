package it.spaghettisource.navaltrader.ui.component;

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

import it.spaghettisource.navaltrader.game.model.Route;
import it.spaghettisource.navaltrader.geometry.Point;


public class PanelDrawPath extends JPanel {

	static Log log = LogFactory.getLog(PanelDrawPath.class.getName());

	private List<Route> routes;	
	private BufferedImage background;
	private Point portCoordinate;

	private int gridSize;
	private int panelSize;
	private int cellSize;


	public PanelDrawPath(int panelSize, int gridSize ,BufferedImage background, Point portCoordinate){
		super();
		this.gridSize = gridSize;
		this.panelSize = panelSize;
		this.background = background;
		this.portCoordinate = portCoordinate;

		cellSize = 6;
		routes = new LinkedList<Route>();

	}


	public Dimension getPreferredSize() {
		return new Dimension(panelSize, panelSize);
	}

	public void setRoutes(List<Route> routes){
		this.routes = routes;
		repaint();
	}



	public void paintComponent(Graphics graphicsPanel) {
		super.paintComponent(graphicsPanel);

		int width = gridSize * cellSize + 1;	//+1 because is not consider index 0 in the size of the immage
		int height = gridSize * cellSize + 1;

		//the image of the grid is big exactly has the grid
		BufferedImage generateImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphicsImage = generateImage.createGraphics();

		//draw the map background
		graphicsImage.drawImage(background,0,0,width,height,0,0,background.getWidth(),background.getHeight(),null);		

		drawPaths(graphicsImage);	//draw the paths

		//draw starting port point
		graphicsImage.setColor(Color.YELLOW);			
		graphicsImage.fillRect(portCoordinate.getX()*cellSize, portCoordinate.getY()*cellSize, cellSize, cellSize);	
		graphicsImage.setColor(Color.BLACK);
		graphicsImage.drawRect(portCoordinate.getX()*cellSize, portCoordinate.getY()*cellSize, cellSize, cellSize);
		
		//draw all to the JPanel Graphics
		graphicsPanel.drawImage(generateImage,0,0,getWidth(),getHeight(),0,0,generateImage.getWidth(),generateImage.getHeight(),null);			

	}


	private void drawPaths(Graphics2D graphicsGrid) {
		if(!routes.isEmpty()){
			Point[] path;
			for (Route route : routes) {
				path = route.getPath();
				for (int i = 0; i < path.length; i++) {
					graphicsGrid.setColor(Color.RED);			
					graphicsGrid.fillRect(path[i].getX()*cellSize, path[i].getY()*cellSize, cellSize, cellSize);	
					graphicsGrid.setColor(Color.BLACK);
					graphicsGrid.drawRect(path[i].getX()*cellSize, path[i].getY()*cellSize, cellSize, cellSize);
				}	
			}
		}
	}





}
