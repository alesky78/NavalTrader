package it.spaghettisource.navaltrader.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.GameManager;
import it.spaghettisource.navaltrader.game.model.Bank;
import it.spaghettisource.navaltrader.game.model.Company;
import it.spaghettisource.navaltrader.game.model.FinancialEntryType;
import it.spaghettisource.navaltrader.game.model.Loan;
import it.spaghettisource.navaltrader.game.model.Ship;
import it.spaghettisource.navaltrader.ui.event.Event;
import it.spaghettisource.navaltrader.ui.event.EventPublisher;
import it.spaghettisource.navaltrader.ui.event.EventType;
import it.spaghettisource.navaltrader.ui.event.InboundEventQueue;
import it.spaghettisource.navaltrader.ui.frame.InternalFrameOffice;
import it.spaghettisource.navaltrader.ui.frame.InternalFrameShipBroker;
import it.spaghettisource.navaltrader.ui.frame.InternalFrameShipList;
import it.spaghettisource.navaltrader.ui.frame.InternalFrameTimeSimulation;

public class MainFrame extends JFrame  implements ActionListener{

	static Log log = LogFactory.getLog(MainFrame.class.getName());

	private static final String MENU_ACTION_NEW_GAME = "new";
	private static final String MENU_ACTION_QUIT_GAME = "quit";
	private static final String MENU_ACTION_FRAME_OFFICE = "office";
	private static final String MENU_ACTION_FRAME_BROKER = "ship Broker";
	private static final String MENU_ACTION_FRAME_SHIP = "ship list";
	private static final String MENU_ACTION_TIME_SIMULAION = "time simultion";	



	//ui components
	private MainDesktopPane desktop;
	private JMenuBar menuBar;

	//game components
	private GameManager gameManager;

	public MainFrame(GameManager gameManager) {
		super("Naval Trader");

		this.gameManager = gameManager;

		setIconImage(ImageIconFactory.getAppImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		desktop = new MainDesktopPane();
		//Make dragging a little faster but perhaps uglier.
		desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);	        

		//Add content to the window and pack it
		setContentPane(desktop);
		pack();		
		setResizable(true);

		//Centre and size the frame
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(screenSize.width/2-getSize().width/2, screenSize.height/2-getSize().height/2);	        
		log.debug("center the frame");
		log.debug("monitor width:"+screenSize.width+" height:"+screenSize.height);
		log.debug("frame width:"+getSize().width+" height:"+getSize().height);
		int inset = 200;
		setBounds(inset, inset,screenSize.width  - inset*2,screenSize.height - inset*2);

		setJMenuBar(createMenuBar());

		//Display the window.
		setVisible(true);


	}

	private JMenuBar createMenuBar() {
		menuBar = new JMenuBar();

		JMenuItem menuItem;
		JMenu menu; 

		//Set up the Game menu.
		menu = new JMenu("Game");
		menuBar.add(menu);

		menuItem = new JMenuItem("New");
		menuItem.setMnemonic(KeyEvent.VK_N);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		menuItem.setActionCommand(MENU_ACTION_NEW_GAME);
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Quit");
		menuItem.setMnemonic(KeyEvent.VK_N);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		menuItem.setActionCommand(MENU_ACTION_QUIT_GAME);
		menuItem.addActionListener(this);
		menu.add(menuItem);		

		//Set up the Game menu.
		menu = new JMenu("Manage");
		menuBar.add(menu);

		menuItem = new JMenuItem("Time simulation");
		menuItem.setActionCommand(MENU_ACTION_TIME_SIMULAION);
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Office");
		menuItem.setActionCommand(MENU_ACTION_FRAME_OFFICE);
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Ship Broker");
		menuItem.setActionCommand(MENU_ACTION_FRAME_BROKER);
		menuItem.addActionListener(this);
		menu.add(menuItem);		

		menuItem = new JMenuItem("Ship List");
		menuItem.setActionCommand(MENU_ACTION_FRAME_SHIP);
		menuItem.addActionListener(this);
		menu.add(menuItem);		

		return menuBar;
	}



	public void actionPerformed(ActionEvent event) {
		if (MENU_ACTION_NEW_GAME.equals(event.getActionCommand())) { 

			//start the new game
			gameManager.newGame("test");			
			gameManager.startGame();
			InboundEventQueue.getInstance().startQueuePublisher();

		}else if (MENU_ACTION_QUIT_GAME.equals(event.getActionCommand())) {

			gameManager.quitGame();
			InboundEventQueue.getInstance().stopQueuePublisher();		
			EventPublisher.getInstance().clearAllListeners();

		}else if(MENU_ACTION_TIME_SIMULAION.equals(event.getActionCommand())){
			
			InternalFrameTimeSimulation frame = new InternalFrameTimeSimulation(desktop, gameManager);
			frame.setVisible(true);
			desktop.add(frame);
			try {
				frame.setSelected(true);
			} catch (java.beans.PropertyVetoException e) {}		
			
		}else if (MENU_ACTION_FRAME_OFFICE.equals(event.getActionCommand())) { 

			InternalFrameOffice frame = new InternalFrameOffice(desktop,gameManager);
			frame.setVisible(true);
			desktop.add(frame);
			try {
				frame.setSelected(true);
			} catch (java.beans.PropertyVetoException e) {}

		}else if (MENU_ACTION_FRAME_BROKER.equals(event.getActionCommand())) { 

			InternalFrameShipBroker frame = new InternalFrameShipBroker(desktop,gameManager);
			frame.setVisible(true);
			desktop.add(frame);
			try {
				frame.setSelected(true);
			} catch (java.beans.PropertyVetoException e) {}

		}else if (MENU_ACTION_FRAME_SHIP.equals(event.getActionCommand())) { 

			InternalFrameShipList frame = new InternalFrameShipList(desktop,gameManager);
			frame.setVisible(true);
			desktop.add(frame);
			try {
				frame.setSelected(true);
			} catch (java.beans.PropertyVetoException e) {}	        
		}     

	}	 	

}
