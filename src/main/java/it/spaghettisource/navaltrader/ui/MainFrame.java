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

public class MainFrame extends JFrame  implements ActionListener{

	static Log log = LogFactory.getLog(MainFrame.class.getName());

	//ui components
	private JDesktopPane desktop;
	private JMenuBar menuBar;
	
	//game components
	private GameManager gameManager;

	public MainFrame(GameManager gameManager) {
		super("Naval Trader");

		this.gameManager = gameManager;

		setIconImage(ImageIconFactory.getAppImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		desktop = new JDesktopPane();
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
		menuItem.setActionCommand("New");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Quit");
		menuItem.setMnemonic(KeyEvent.VK_N);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		menuItem.setActionCommand("Quit");
		menuItem.addActionListener(this);
		menu.add(menuItem);		

		//Set up the Game menu.
		menu = new JMenu("Report");
		menuBar.add(menu);

		menuItem = new JMenuItem("Office");
		menuItem.setActionCommand("Office");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Ship Broker");
		menuItem.setActionCommand("Ship Broker");
		menuItem.addActionListener(this);
		menu.add(menuItem);		
		
		return menuBar;
	}



	public void actionPerformed(ActionEvent event) {
		if ("New".equals(event.getActionCommand())) { 
			
			//start the new game
			gameManager.newGame("test");			
			gameManager.startGame();
			InboundEventQueue.getInstance().startQueuePublisher();
			
			testThread.start();
			
		}else if ("Quit".equals(event.getActionCommand())) {
			
			gameManager.quitGame();
			InboundEventQueue.getInstance().stopQueuePublisher();		
			EventPublisher.getInstance().clearAllListeners();
			
			testThread.stop();
			
			
		}else if ("Office".equals(event.getActionCommand())) { 
			InternalFrameOffice frame = new InternalFrameOffice(gameManager);
			frame.setVisible(true);
			desktop.add(frame);
	        try {
	            frame.setSelected(true);
	        } catch (java.beans.PropertyVetoException e) {}			
		}else if ("Ship Broker".equals(event.getActionCommand())) { 
			InternalFrameShipBroker frame = new InternalFrameShipBroker(gameManager);
			frame.setVisible(true);
			desktop.add(frame);
	        try {
	            frame.setSelected(true);
	        } catch (java.beans.PropertyVetoException e) {}			
		}     
		
		
	}	 	
	
	private TestThread testThread = new TestThread();
	
	class TestThread implements Runnable{

		private boolean stop = false;
		private int timeSleep = 2000;	
		private Random random = new Random();
		
		public void start(){
			stop = false;
			(new Thread(this)).start();
		}
		
		public void stop(){
			stop = true;
		}		
		
		public void run() {
			
			Company company = gameManager.getGameData().getCompany();
			company.addShip(new Ship("testShip-1"));
			
			Bank bank = gameManager.getGameData().getBank();
			
			while(!stop){
				try {
					Thread.sleep(timeSleep);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				company.addBudget(random.nextInt(200));
				company.getShipByName("testShip-1").getFinance().addProfit(FinancialEntryType.SHIP_INCOME, random.nextInt(50));
				company.getShipByName("testShip-1").getFinance().addLoss(FinancialEntryType.SHIP_MAINTAINANCE, random.nextInt(50));
				company.getShipByName("testShip-1").getFinance().addLoss(FinancialEntryType.SHIP_FUEL, random.nextInt(50));		
				
				
				for (Loan loan : bank.getLoanList()) {
					bank.repairLoad(loan.getId(), 1,company);
				}
				
				InboundEventQueue.getInstance().put(new Event(EventType.FINANCIAL_EVENT));		
				
			}
			
		}
		
	}

}
