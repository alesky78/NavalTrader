package it.spaghettisource.navaltrader.ui.component;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.loop.LoopManager;
import it.spaghettisource.navaltrader.game.model.Company;
import it.spaghettisource.navaltrader.game.model.GameTime;
import it.spaghettisource.navaltrader.game.model.Port;
import it.spaghettisource.navaltrader.game.model.Ship;
import it.spaghettisource.navaltrader.game.model.World;
import it.spaghettisource.navaltrader.geometry.Point;
import it.spaghettisource.navaltrader.ui.FontUtil;
import it.spaghettisource.navaltrader.ui.ImageIconFactory;

/**
 * this is the pannel used to draw all then ports and the position of the ships
 * the ports are desiged has button then is possible to link an action to them
 * 
 * 
 * @author Alessandro
 *
 */
public class PanelDrawMainMap extends JPanel implements ComponentListener,  ActionListener  {

	static Log log = LogFactory.getLog(PanelDrawMainMap.class.getName());

	private final static long SLEEP_TIME = 10;
	
	private final static String ACTION_PAUSE = "pause";
	private final static String ACTION_PLAY_X1 = "playx1";	
	private final static String ACTION_PLAY_X2 = "playx2";	
	private final static String ACTION_PLAY_X3 = "playx3";		

	private Company company;
	private World world;
	private GameTime gameTime;		
	private LoopManager loopManager;	

	private boolean stopThread;
	private NumberFormat formatCurrency = NumberFormat.getCurrencyInstance();
	
	private List<ButtonDrawPort> portsButton;
	
	private BufferedImage barUp;
	private ImageIcon puase;	
	private ImageIcon speedx1;
	private ImageIcon speedx2;
	private ImageIcon speedx3;	

	public PanelDrawMainMap(Company company,World world,GameTime gameTime,LoopManager loopManager, int panelSize) {
		super(true);
		setLayout(null);
		setPreferredSize(new Dimension(panelSize,panelSize));
		setIgnoreRepaint(true);

		this.company = company;
		this.world = world;
		this.gameTime = gameTime;
		this.loopManager = loopManager;

		//load all the immages
		barUp = ImageIconFactory.getBufferImageByName("/images/barra-up.png");
		puase = ImageIconFactory.getImageIconByName("/images/clock-pause.png");
		speedx1 = ImageIconFactory.getImageIconByName("/images/clock-1.png");
		speedx2 = ImageIconFactory.getImageIconByName("/images/clock-2.png");
		speedx3 = ImageIconFactory.getImageIconByName("/images/clock-3.png");		
		
		//create a button for each port
		portsButton = new ArrayList<>();
		ButtonDrawPort button;
		for (Port port : world.getPorts()) {
			button = new ButtonDrawPort(port, null,null);
			add(button);			
			portsButton.add(button);
		}

		//create the button for the clock
		JButton pause = new JButton(puase);
		pause.setBounds(20, 5, puase.getIconWidth(), puase.getIconHeight());		
		pause.setBorder(BorderFactory.createEmptyBorder());
		pause.setContentAreaFilled(false);				
		pause.setFocusPainted(false);
		pause.setActionCommand(ACTION_PAUSE);
		pause.addActionListener(this);
		add(pause);		

		JButton speed1 = new JButton(speedx1);
		speed1.setBounds(100, 32, speedx1.getIconWidth(), speedx1.getIconHeight());		
		speed1.setBorder(BorderFactory.createEmptyBorder());
		speed1.setContentAreaFilled(false);				
		speed1.setFocusPainted(false);
		speed1.setActionCommand(ACTION_PLAY_X1);
		speed1.addActionListener(this);		
		add(speed1);		
		
		JButton speed2 = new JButton(speedx2);
		speed2.setBounds(140, 32, speedx2.getIconWidth(), speedx2.getIconHeight());		
		speed2.setBorder(BorderFactory.createEmptyBorder());
		speed2.setContentAreaFilled(false);				
		speed2.setFocusPainted(false);
		speed2.setActionCommand(ACTION_PLAY_X2);
		speed2.addActionListener(this);		
		add(speed2);	
		
		JButton speed3 = new JButton(speedx3);
		speed3.setBounds(180, 32, speedx3.getIconWidth(), speedx3.getIconHeight());		
		speed3.setBorder(BorderFactory.createEmptyBorder());
		speed3.setContentAreaFilled(false);				
		speed3.setFocusPainted(false);
		speed3.setActionCommand(ACTION_PLAY_X3);
		speed3.addActionListener(this);		
		add(speed3);	
		
		
		
		
		//prepare to start
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
		graphicsBuffer.drawImage(world.getWorldMap(),0,0,width,height,null);

		drawShips(graphicsBuffer);

		drawBarUp(graphicsBuffer,bufferImage);

		graphicsBuffer.dispose();

		//draw all to the JPanel Graphics
		graphicsPanel.drawImage(bufferImage,0,0,getWidth(),getHeight(),0,0,bufferImage.getWidth(),bufferImage.getHeight(),null);		

	}


	private void drawBarUp(Graphics2D graphicsBuffer,BufferedImage bufferImage) {

		graphicsBuffer.drawImage(barUp,0,0,bufferImage.getWidth(),150,0,0,barUp.getWidth(),barUp.getHeight(),null);
		
		drawTimeAndBudget(graphicsBuffer,bufferImage);
			
		
	}


	private void drawShips(Graphics2D graphicsBuffer) {
		Point point;
		for (Ship ship : company.getShips()) {
			point = ship.getPosition();
			
			if(!ship.isDocked()) {
				//draw the ships
				graphicsBuffer.setStroke(new BasicStroke(10));
				graphicsBuffer.setColor(Color.RED);	
				graphicsBuffer.fillOval(point.getIntX(), point.getIntY(), 30, 30);			
				graphicsBuffer.setColor(Color.BLACK);				
				graphicsBuffer.drawOval(point.getIntX(), point.getIntY(), 30, 30);				
			}
			
		}
	}


	private void drawTimeAndBudget(Graphics2D graphicsBuffer,BufferedImage bufferImage) {
	
		graphicsBuffer.setFont(FontUtil.getDrawFont());
		graphicsBuffer.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		FontMetrics fontMetrics = graphicsBuffer.getFontMetrics();
		int stringHeight = fontMetrics.getAscent();		

		
		//print date and clock
		String date = 	gameTime.getDate();		
		int xStart = 230;
		int yStart = 45;			
		
		graphicsBuffer.setColor(Color.WHITE);
		graphicsBuffer.drawString(date,xStart,yStart);
		graphicsBuffer.fill(new Arc2D.Float(xStart-stringHeight-30, yStart-stringHeight, stringHeight, stringHeight, 90, -gameTime.getTimeInAngleFormat(), Arc2D.PIE));

		//print budget
		String budget = formatCurrency.format(company.getBudget());
		int stringWidth = fontMetrics.stringWidth(budget);
		graphicsBuffer.drawString(budget,bufferImage.getWidth()/2 - stringWidth/2 ,95);
		
		
		
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



	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		if(ACTION_PAUSE.equals(command)) {
			loopManager.setPauseByUser(true);
		}else if(ACTION_PLAY_X1.equals(command)) {
			loopManager.setPauseByUser(false);
			loopManager.goFast(1);		
		}else if(ACTION_PLAY_X2.equals(command)) {
			loopManager.setPauseByUser(false);
			loopManager.goFast(7);	
		}else if(ACTION_PLAY_X3.equals(command)) {
			loopManager.setPauseByUser(false);
			loopManager.goFast(14);				
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
