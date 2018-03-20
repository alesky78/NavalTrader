package it.spaghettisource.navaltrader.ui.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.model.Port;
import it.spaghettisource.navaltrader.game.model.Route;
import it.spaghettisource.navaltrader.game.model.Ship;
import it.spaghettisource.navaltrader.game.model.World;
import it.spaghettisource.navaltrader.geometry.Point;


public class PanelDrawRoute extends JPanel implements ComponentListener {

	static Log log = LogFactory.getLog(PanelDrawRoute.class.getName());

	private List<Route> routes;	
	private BufferedImage background;
	
	private Port port;
	private World world;
	
	private int panelSize;
	private int cellSize;
	
	private List<ButtonDrawPort> portsButton;
	
	
	public PanelDrawRoute(Port port, World world,int panelSize) {
		super();
		setLayout(null);
		setPreferredSize(new Dimension(panelSize,panelSize));		
		
		this.panelSize = panelSize;
		this.port = port;
		this.world = world;
		
		background = world.getWorldMap();
		cellSize = 6;
		routes = new LinkedList<Route>();
		
	}
	
	public void drawPorts(Ship ship, String actionCommand,  ActionListener listner) {
		
		//create a button for each port
		portsButton = new ArrayList<>();
		ButtonDrawPort button;
		for (Port actualPort : world.getPorts()) {
			button = new ButtonDrawPort(actualPort, ship, actionCommand,listner);
			add(button);			
			portsButton.add(button);
			button.resetLocation(this, world.getGridWidth(),world.getGridHeight());			
		}		
		
		addComponentListener(this);
		
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

		int width = world.getGridWidth() * cellSize + 1;	//+1 because is not consider index 0 in the size of the immage
		int height = world.getGridHeight() * cellSize + 1;

		//create the double buffer of the same size of the grid
		BufferedImage bufferImage = (BufferedImage)createImage(width, height); 
		Graphics2D graphicsImage = bufferImage.createGraphics();

		//draw the map background
		graphicsImage.drawImage(background,0,0,width,height,0,0,background.getWidth(),background.getHeight(),null);		

		drawPaths(graphicsImage);	//draw the paths

		//draw starting port point
		graphicsImage.setColor(Color.YELLOW);
		graphicsImage.fillRect(port.getCooridnate().getX()*cellSize, port.getCooridnate().getY()*cellSize, cellSize, cellSize);	
		graphicsImage.setColor(Color.BLACK);
		graphicsImage.drawRect(port.getCooridnate().getX()*cellSize, port.getCooridnate().getY()*cellSize, cellSize, cellSize);
		
		graphicsImage.dispose();
		
		//draw all to the JPanel Graphics
		graphicsPanel.drawImage(bufferImage,0,0,getWidth(),getHeight(),0,0,bufferImage.getWidth(),bufferImage.getHeight(),null);		

	}


	private void drawPaths(Graphics2D graphicsGrid) {
		if(!routes.isEmpty()){
			Point[] path;
			for (Route route : routes) {
				path = route.getPath();
				for (int i = 0; i < path.length; i++) {
					graphicsGrid.setColor(Color.RED);			
					graphicsGrid.fillRect(path[i].getX()*cellSize, path[i].getY()*cellSize, cellSize, cellSize);	
				}	
			}
		}
	}



	public void componentResized(ComponentEvent e) {
		//put the correct position of the port buttons		
		for (ButtonDrawPort buttonDrawPort : portsButton) {
			buttonDrawPort.resetLocation(this, world.getWorldWidth(),world.getWorldHeight());
		}
	}


	public void componentMoved(ComponentEvent e) {		
		//put the correct position of the port buttons
		for (ButtonDrawPort buttonDrawPort : portsButton) {
			buttonDrawPort.resetLocation(this, world.getWorldWidth(),world.getWorldHeight());
		}		
	}


	public void componentShown(ComponentEvent e) {
		//put the correct position of the port buttons
		for (ButtonDrawPort buttonDrawPort : portsButton) {
			buttonDrawPort.resetLocation(this, world.getWorldWidth(),world.getWorldHeight());
		}
	}


	public void componentHidden(ComponentEvent e) {
		//put the correct position of the port buttons
		for (ButtonDrawPort buttonDrawPort : portsButton) {
			buttonDrawPort.resetLocation(this, world.getWorldWidth(),world.getWorldHeight());
		}		
	}

}
