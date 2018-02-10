package it.spaghettisource.navaltrader.ui.component;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.model.Company;
import it.spaghettisource.navaltrader.game.model.Port;
import it.spaghettisource.navaltrader.game.model.Ship;
import it.spaghettisource.navaltrader.game.model.World;
import it.spaghettisource.navaltrader.geometry.Point;
import it.spaghettisource.navaltrader.ui.ImageIconFactory;

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

	private BufferedImage shipImmage;
	private BufferedImage portImmage; 	
	private int spirteSize = 120;

	public PanelDrawNavigation(Company company,World world,int panelSize) {
		super(true);
		setLayout(null);
		setPreferredSize(new Dimension(panelSize,panelSize));
		setIgnoreRepaint(true);

		this.company = company;
		this.world = world;

		worldSize = world.getGridSize() * world.getGridScale();	//TODO no good if will be biggest will become huge amount of memory, better to implement scale also here

		try {
			shipImmage = ImageIO.read(PanelDrawRoute.class.getResourceAsStream("/icon/ship-map.png"));
			shipImmage = ImageIconFactory.getScaledBufferedImage(shipImmage, spirteSize, spirteSize);	
			
			portImmage = ImageIO.read(PanelDrawRoute.class.getResourceAsStream("/icon/harbor.png"));
			portImmage = ImageIconFactory.getScaledBufferedImage(portImmage, spirteSize, spirteSize);
		} catch (IOException e) {
			log.error("erro loading immages",e);
		}


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

		//draw
		graphicsBuffer.drawImage(world.getWorldMap(),0,0,width,height,0,0,world.getWorldMap().getWidth(),world.getWorldMap().getHeight(),null);		
		drawPorts(graphicsBuffer);
		drawShips(graphicsBuffer);

		graphicsBuffer.dispose();

		//draw all to the JPanel Graphics
		graphicsPanel.drawImage(bufferImage,0,0,getWidth(),getHeight(),0,0,bufferImage.getWidth(),bufferImage.getHeight(),null);		

	}


	private void drawPorts(Graphics2D graphicsBuffer) {
		Point point;
		for (Port port : world.getPorts()) {
			point = port.getCooridnate();
			graphicsBuffer.drawImage(portImmage, point.getX()-(spirteSize/2), point.getY()-(spirteSize/2), null);	
		}
		
	}


	private void drawShips(Graphics2D graphicsBuffer) {
		Point point;
		for (Ship ship : company.getShips()) {
			point = ship.getPosition();
			graphicsBuffer.drawImage(shipImmage, point.getX()-(spirteSize/2), point.getY()-(spirteSize/2), null);	

			//draw the ships
			//graphicsBuffer.setStroke(new BasicStroke(10));
			//graphicsBuffer.setColor(Color.RED);	
			//graphicsBuffer.fillOval(point.getX(), point.getY(), 30, 30);			
			//graphicsBuffer.setColor(Color.BLACK);				
			//graphicsBuffer.drawOval(point.getX(), point.getY(), 30, 30);			
		}
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
