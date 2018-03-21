package it.spaghettisource.navaltrader.ui.component;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.model.Company;
import it.spaghettisource.navaltrader.game.model.GameTime;
import it.spaghettisource.navaltrader.game.model.Port;
import it.spaghettisource.navaltrader.game.model.Ship;
import it.spaghettisource.navaltrader.game.model.World;
import it.spaghettisource.navaltrader.geometry.Point;
import it.spaghettisource.navaltrader.ui.FontUtil;

/**
 * this is the pannel used to draw all then ports and the position of the ships
 * the ports are desiged has button then is possible to link an action to them
 * 
 * 
 * @author Alessandro
 *
 */
public class PanelDrawMainMap extends JPanel implements ComponentListener  {

	static Log log = LogFactory.getLog(PanelDrawMainMap.class.getName());

	private final static long SLEEP_TIME = 10;

	private Company company;
	private World world;
	private GameTime gameTime;		

	private boolean stopThread;
	
	private List<ButtonDrawPort> portsButton;

	public PanelDrawMainMap(Company company,World world,GameTime gameTime,int panelSize) {
		super(true);
		setLayout(null);
		setPreferredSize(new Dimension(panelSize,panelSize));
		setIgnoreRepaint(true);

		this.company = company;
		this.world = world;
		this.gameTime = gameTime;

		//create a button for each port
		portsButton = new ArrayList<>();
		ButtonDrawPort button;
		for (Port port : world.getPorts()) {
			button = new ButtonDrawPort(port, null,null);
			add(button);			
			portsButton.add(button);
		}

		addComponentListener(this);
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

		
		//TODO the real size must a correct relationship between the immage and the grid
		int width = world.getWorldWidth();	//+1 because is not consider index 0 in the size of the immage
		int height = world.getWorldHeight();

		//create the double buffer of the same size of the grid
		BufferedImage bufferImage = (BufferedImage)createImage(width, height); 
		Graphics2D graphicsBuffer = bufferImage.createGraphics();

		//draw
		graphicsBuffer.drawImage(world.getWorldMap(),0,0,width,height,0,0,world.getWorldMap().getWidth(),world.getWorldMap().getHeight(),null);		

		drawShips(graphicsBuffer);
		
		drawTime(graphicsBuffer);

		graphicsBuffer.dispose();

		//draw all to the JPanel Graphics
		graphicsPanel.drawImage(bufferImage,0,0,getWidth(),getHeight(),0,0,bufferImage.getWidth(),bufferImage.getHeight(),null);		

	}


	private void drawShips(Graphics2D graphicsBuffer) {
		Point point;
		for (Ship ship : company.getShips()) {
			point = ship.getPosition();

			//draw the ships
			graphicsBuffer.setStroke(new BasicStroke(10));
			graphicsBuffer.setColor(Color.RED);	
			graphicsBuffer.fillOval(point.getX(), point.getY(), 30, 30);			
			graphicsBuffer.setColor(Color.BLACK);				
			graphicsBuffer.drawOval(point.getX(), point.getY(), 30, 30);			
		}
	}


	private void drawTime(Graphics2D graphicsBuffer) {
	
		graphicsBuffer.setFont(FontUtil.getDrawFont());
		graphicsBuffer.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		String date = 	gameTime.getDate();
		
		int stringWidth = graphicsBuffer.getFontMetrics().stringWidth(date);		
		int stringHeight = graphicsBuffer.getFontMetrics().getAscent();
		
		graphicsBuffer.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		int xStart = 50;
		int yStart = 50;			
		
		graphicsBuffer.setColor(Color.WHITE);
		graphicsBuffer.drawString(date,xStart,yStart);
		graphicsBuffer.fill(new Arc2D.Float(xStart-stringHeight-5, yStart-stringHeight, stringHeight, stringHeight, 90, -gameTime.getTimeInAngleFormat(), Arc2D.PIE));

		
		
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
	}


}
