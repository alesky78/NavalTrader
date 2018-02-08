package it.spaghettisource.navaltrader.ui.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.model.Company;
import it.spaghettisource.navaltrader.game.model.Ship;
import it.spaghettisource.navaltrader.game.model.World;
import it.spaghettisource.navaltrader.geometry.Point;

/**
 * 
 * 
 * @author Alessandro
 *
 */
public class PanelDrawNavigation extends JPanel {

	static Log log = LogFactory.getLog(PanelDrawNavigation.class.getName());
	
	private final static long SLEEP_TIME = 10;
	
	private Company company;
	private World world;	
	private int worldSize;
	
	private boolean stopThread;
	
	public PanelDrawNavigation(Company company,World world,int panelSize) {
		super(true);
		setLayout(null);
		setPreferredSize(new Dimension(panelSize,panelSize));
		setIgnoreRepaint(true);
		
		this.company = company;
		this.world = world;
		
		worldSize = world.getGridSize() * world.getGridScale();
		
		stopThread = false;
	
	}
	
	
	public void start(){
		new Thread(new Repainter(SLEEP_TIME, this)).start();
	}
	
	public void stop(){
		stopThread = true;
	}
	
	
	public void paintComponent(Graphics graphicsPanel) {
		super.paintComponent(graphicsPanel);

		int width = worldSize;	//+1 because is not consider index 0 in the size of the immage
		int height = worldSize;

		//create the double buffer of the same size of the grid
		BufferedImage bufferImage = (BufferedImage)createImage(width, height); 
		Graphics2D graphicsBuffer = bufferImage.createGraphics();

		//draw the map background
		graphicsBuffer.drawImage(world.getWorldMap(),0,0,width,height,0,0,world.getWorldMap().getWidth(),world.getWorldMap().getHeight(),null);		

		//draw the ships
		graphicsBuffer.setColor(Color.RED);		
		Point point;
		for (Ship ship : company.getShips()) {
			point = ship.getPosition();
			
			graphicsBuffer.fillOval(point.getX(), point.getY(), 15, 15);
		}

		graphicsBuffer.dispose();
		
		//draw all to the JPanel Graphics
		graphicsPanel.drawImage(bufferImage,0,0,getWidth(),getHeight(),0,0,bufferImage.getWidth(),bufferImage.getHeight(),null);		

	}
	
	
	
	private class Repainter implements Runnable{

		long timeSleep;
		private JPanel panel;
		
		public Repainter(long timeSleep, JPanel panel) {
			super();
			this.timeSleep = timeSleep;
			this.panel = panel;
		}


		@Override
		public void run() {

			
			while(!stopThread){
								
				try {
					
					Thread.sleep(timeSleep);
					panel.repaint();
				} catch (InterruptedException e) {
				}
				
			}
			
			log.info("stop thread to draw ship map navigation");
			
		}
		
		
		
	}
	
	
}
