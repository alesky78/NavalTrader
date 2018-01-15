package it.spaghettisource.navaltrader.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.GameManager;
import it.spaghettisource.navaltrader.ui.event.EventManager;
import it.spaghettisource.navaltrader.ui.event.InboundEventQueue;

public class MainFrame extends JFrame  implements ActionListener{

	static Log log = LogFactory.getLog(MainFrame.class.getName());

	//ui components
	private JDesktopPane desktop;
	private JMenuBar menuBar;
	
	//game components
	private GameManager gameManager;
	private EventManager eventManager;	
	private InboundEventQueue eventQueue;	




	public MainFrame(GameManager gameManager,InboundEventQueue eventQueue,EventManager eventManager) {
		super("Naval Trader");

		this.gameManager = gameManager;
		this.eventQueue = eventQueue;
		this.eventManager = eventManager;

		//frame.setIconImage(ImageIconFactory.getAppImage());        //TODO add App icon
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
		int inset = 100;
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
		
		return menuBar;
	}



	public void actionPerformed(ActionEvent event) {
		if ("New".equals(event.getActionCommand())) { 
			//quit old game if exist
			gameManager.quitGame();
			//start the new game
			gameManager.newGame("test");
			gameManager.startGame();
		}else if ("Quit".equals(event.getActionCommand())) { 
			gameManager.quitGame();
		}else if ("Office".equals(event.getActionCommand())) { 
			InternalFrameOffice frame = new InternalFrameOffice(gameManager);
			eventManager.register(frame);			
			frame.setVisible(true);
			desktop.add(frame);
	        try {
	            frame.setSelected(true);
	        } catch (java.beans.PropertyVetoException e) {}			
		}    
		
		
	}	 	

}
