package it.spaghettisource.navaltrader.ui.component;

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
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.image.AffineTransformOp;
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

import it.spaghettisource.navaltrader.game.GameManager;
import it.spaghettisource.navaltrader.game.loop.LoopManager;
import it.spaghettisource.navaltrader.game.model.Company;
import it.spaghettisource.navaltrader.game.model.GameTime;
import it.spaghettisource.navaltrader.game.model.Port;
import it.spaghettisource.navaltrader.game.model.Ship;
import it.spaghettisource.navaltrader.game.model.World;
import it.spaghettisource.navaltrader.geometry.Point;
import it.spaghettisource.navaltrader.ui.FontUtil;
import it.spaghettisource.navaltrader.ui.ImageIconFactory;
import it.spaghettisource.navaltrader.ui.frame.GameBoardDesktopPane;
import it.spaghettisource.navaltrader.ui.internalframe.InternalFrameOffice;
import it.spaghettisource.navaltrader.ui.internalframe.InternalFrameShipBroker;
import it.spaghettisource.navaltrader.ui.internalframe.InternalFrameShipList;

/**
 * this is the pannel used to draw all then ports and the position of the ships
 * the ports are desiged has button then is possible to link an action to them
 * 
 * 
 * @author Alessandro
 *
 */
public class PanelGameBoard extends JPanel implements ComponentListener,  ActionListener  {

	static Log log = LogFactory.getLog(PanelGameBoard.class.getName());

	private final static long SLEEP_TIME = 10;

	private final static String ACTION_PAUSE = "pause";
	private final static String ACTION_PLAY_X1 = "playx1";	
	private final static String ACTION_PLAY_X2 = "playx2";	
	private final static String ACTION_PLAY_X3 = "playx3";		
	private final static String ACTION_SHIP_LIST = "ship_list";	
	private final static String ACTION_SHIP_BROKER = "ship_Broker";	
	private final static String ACTION_OFFICE = "office";			
	private final static String ACTION_SETTING = "setting";
	private final static String ACTION_OPEN_PORT = "port";	

	private GameBoardDesktopPane parentDesktopPane;
	private GameManager gameManager;

	private Company company;
	private World world;
	private GameTime gameTime;		
	private LoopManager loopManager;	

	private boolean stopThread;
	private NumberFormat formatCurrency = NumberFormat.getCurrencyInstance();

	private List<ButtonDrawPort> portsButton;

	private BufferedImage barUp;
	private BufferedImage barDown;	
	private BufferedImage ship;
	private BufferedImage shipDocked;	


	private ImageIcon puase;	
	private ImageIcon speedx1;
	private ImageIcon speedx2;
	private ImageIcon speedx3;	

	private ImageIcon shipList;
	private ImageIcon shipBroker;	
	private ImageIcon office;		
	private ImageIcon setting;	

	public PanelGameBoard(GameBoardDesktopPane parentDesktopPane,GameManager gameManager, int panelSize) {
		super(true);
		setLayout(null);
		setPreferredSize(new Dimension(panelSize,panelSize));
		setIgnoreRepaint(true);

		this.parentDesktopPane = parentDesktopPane;
		this.gameManager = gameManager;

		this.company = gameManager.getGameData().getCompany();
		this.world = gameManager.getGameData().getWorld();
		this.gameTime = gameManager.getGameData().getGameTime();
		this.loopManager = gameManager.getLoopManager();

		//load all the immages
		barUp = ImageIconFactory.getBufferImageByName("/images/bar-up.png");
		barDown = ImageIconFactory.getBufferImageByName("/images/bar-down.png");
		ship = ImageIconFactory.getBufferImageByName("/images/ship.png");
		shipDocked = ImageIconFactory.getBufferImageByName("/images/dock-ship.png");

		puase = ImageIconFactory.getImageIconByName("/images/clock-pause.png");
		speedx1 = ImageIconFactory.getImageIconByName("/images/clock-1.png");
		speedx2 = ImageIconFactory.getImageIconByName("/images/clock-2.png");
		speedx3 = ImageIconFactory.getImageIconByName("/images/clock-3.png");	
		shipList = ImageIconFactory.getImageIconByName("/images/ship-list.png");
		shipBroker = ImageIconFactory.getImageIconByName("/images/ship-broker.png");
		office = ImageIconFactory.getImageIconByName("/images/office.png");
		setting = ImageIconFactory.getImageIconByName("/images/setting.png");

		//create a button for each port
		portsButton = new ArrayList<>();
		ButtonDrawPort button;
		for (Port port : world.getPorts()) {
			button = new ButtonDrawPort(port, ACTION_OPEN_PORT,this);
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

		//create the button for the bar down
		JButton shipListB = new JButton(shipList);
		shipListB.setBounds(810, 998, shipList.getIconWidth(), shipList.getIconHeight());		
		shipListB.setBorder(BorderFactory.createEmptyBorder());
		shipListB.setContentAreaFilled(false);				
		shipListB.setFocusPainted(false);
		shipListB.setActionCommand(ACTION_SHIP_LIST);
		shipListB.addActionListener(this);
		add(shipListB);	

		JButton shipBrokerB = new JButton(shipBroker);
		shipBrokerB.setBounds(885, 998, shipBroker.getIconWidth(), shipBroker.getIconHeight());		
		shipBrokerB.setBorder(BorderFactory.createEmptyBorder());
		shipBrokerB.setContentAreaFilled(false);				
		shipBrokerB.setFocusPainted(false);
		shipBrokerB.setActionCommand(ACTION_SHIP_BROKER);
		shipBrokerB.addActionListener(this);
		add(shipBrokerB);		

		JButton officeB = new JButton(office);
		officeB.setBounds(965, 998, office.getIconWidth(), office.getIconHeight());		
		officeB.setBorder(BorderFactory.createEmptyBorder());
		officeB.setContentAreaFilled(false);				
		officeB.setFocusPainted(false);
		officeB.setActionCommand(ACTION_OFFICE);
		officeB.addActionListener(this);
		add(officeB);			

		JButton settingB = new JButton(setting);
		settingB.setBounds(1040, 998, setting.getIconWidth(), setting.getIconHeight());		
		settingB.setBorder(BorderFactory.createEmptyBorder());
		settingB.setContentAreaFilled(false);				
		settingB.setFocusPainted(false);
		settingB.setActionCommand(ACTION_SETTING);
		settingB.addActionListener(this);
		add(settingB);			




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

		Graphics2D mainGraphics = (Graphics2D)graphicsPanel;

		//TODO the real size must a correct relationship between the immage and the grid
		int width = world.getWorldWidth();	//+1 because is not consider index 0 in the size of the immage
		int height = world.getWorldHeight();

		//create the double buffer of the same size of the grid
		BufferedImage bufferImage = (BufferedImage)createImage(width, height); 
		Graphics2D graphicsBuffer = bufferImage.createGraphics();

		//draw on the new immage
		graphicsBuffer.drawImage(world.getWorldMap(),0,0,width,height,null);
		drawShips(graphicsBuffer);
		drawPorts(graphicsBuffer);		
		graphicsBuffer.dispose();

		//draw all to the JPanel Graphics
		graphicsPanel.drawImage(bufferImage,0,0,getWidth(),getHeight(),0,0,bufferImage.getWidth(),bufferImage.getHeight(),null);		

		//draw the command on main immage
		drawBar(mainGraphics,getWidth(),getHeight());		


	}


	private void drawBar(Graphics2D graphicsBuffer,int width, int height) {

		//bar up
		graphicsBuffer.drawImage(barUp,0,0,width,barUp.getHeight(),0,0,barUp.getWidth(),barUp.getHeight(),null);

		//bar down
		graphicsBuffer.drawImage(barDown,(width/2)-barDown.getWidth()/2,height-barDown.getHeight(),(width/2)+barDown.getWidth()/2,height,0,0,barDown.getWidth(),barDown.getHeight(),null);

		drawTimeAndBudget(graphicsBuffer,width,height);


	}


	private void drawTimeAndBudget(Graphics2D graphicsBuffer,int width, int height) {

		graphicsBuffer.setFont(FontUtil.getDrawFont());
		graphicsBuffer.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		FontMetrics fontMetrics = graphicsBuffer.getFontMetrics();
		int stringHeight = fontMetrics.getAscent();		


		//print date and clock
		String date = 	gameTime.getDate();		
		int xStart = 130;
		int yStart = 21;			

		graphicsBuffer.setColor(Color.WHITE);
		graphicsBuffer.drawString(date,xStart,yStart);
		graphicsBuffer.fill(new Arc2D.Float(xStart-stringHeight-15, yStart-stringHeight, stringHeight, stringHeight, 90, -gameTime.getTimeInAngleFormat(), Arc2D.PIE));

		//print budget
		String budget = formatCurrency.format(company.getBudget());
		int stringWidth = fontMetrics.stringWidth(budget);
		graphicsBuffer.drawString(budget,width/2 - stringWidth/2 ,45);

	}

	private void drawShips(Graphics2D graphicsBuffer) {
		Point shipPosition;
		for (Ship actualShip : company.getShips()) {
			shipPosition = actualShip.getPosition();

			if(!actualShip.isDocked()) {

				// Rotation information				
				double rotationRequired = Math.toRadians (actualShip.getShipAngle());
				double locationX = ship.getWidth() / 2;
				double locationY = ship.getHeight() / 2;
				AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
				AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

				// Drawing the rotated image at the required drawing locations
				graphicsBuffer.drawImage(op.filter(ship, null), shipPosition.getIntX()-ship.getWidth()/2, shipPosition.getIntY()-ship.getHeight()/2, null);

			}

		}
	}	

	private void drawPorts(Graphics2D graphicsBuffer) {
		//the ports are draw as button  and maintained in the variable portsButton
		//but there are extra staff to draw.. for example the ship docked in the port is draw here
		Point coordinate = null;
		for (ButtonDrawPort port : portsButton) {
			if(!port.getManagedPort().getDockedShip().isEmpty()) {	//if the port has ship draw related icon
				coordinate = port.getManagedPort().getCooridnate();
				graphicsBuffer.drawImage(shipDocked, coordinate.getIntX() - 50 - shipDocked.getWidth() , coordinate.getIntY() -50 - shipDocked.getHeight(), shipDocked.getWidth(), shipDocked.getHeight(), null);


			}
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
		}else if(ACTION_SHIP_BROKER.equals(command)) {
			InternalFrameShipBroker frame = new InternalFrameShipBroker(parentDesktopPane,gameManager);
			frame.setVisible(true);
			parentDesktopPane.add(frame);
			try {
				frame.setSelected(true);
			} catch (java.beans.PropertyVetoException e) {}
		}else if(ACTION_SHIP_LIST.equals(command)) {
			InternalFrameShipList frame = new InternalFrameShipList(parentDesktopPane,gameManager);
			frame.setVisible(true);
			parentDesktopPane.add(frame);
			try {
				frame.setSelected(true);
			} catch (java.beans.PropertyVetoException e) {}	      
		}else if(ACTION_OFFICE.equals(command)) {
			InternalFrameOffice frame = new InternalFrameOffice(parentDesktopPane,gameManager);
			frame.setVisible(true);
			parentDesktopPane.add(frame);
			try {
				frame.setSelected(true);
			} catch (java.beans.PropertyVetoException e) {}
		}else if(ACTION_SETTING.equals(command)) {
			//gameManager.quitGame();

			//close the main menu frame
			//this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
			System.exit(0);
		}else if(ACTION_OPEN_PORT.equals(command)){
			Port targetPort = ((ButtonDrawPort)event.getSource()).getManagedPort();
			if(!targetPort.getDockedShip().isEmpty()){
				parentDesktopPane.createInternalFramePort(gameManager,targetPort,targetPort.getDockedShip().get(0));
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
