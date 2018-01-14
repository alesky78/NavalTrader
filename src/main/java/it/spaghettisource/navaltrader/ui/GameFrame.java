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

public class GameFrame extends JFrame  implements ActionListener{

	static Log log = LogFactory.getLog(GameFrame.class.getName());
	
	private GameManager gameManager;
	
	
	public GameFrame(GameManager gameManager) {
		super("Naval Trader");
		
		this.gameManager = gameManager;
				
		//frame.setIconImage(ImageIconFactory.getAppImage());        //TODO add App icon
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JDesktopPane desktop = new JDesktopPane();
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
		JMenuBar menuBar = new JMenuBar();

		//Set up the lone menu.
		JMenu menu = new JMenu("Game");
		menuBar.add(menu);


		JMenuItem menuItem = new JMenuItem("New");
		menuItem.setMnemonic(KeyEvent.VK_N);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		menuItem.setActionCommand("new");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		return menuBar;
	}


	
	public void actionPerformed(ActionEvent event) {
		  if ("new".equals(event.getActionCommand())) { 
			  	gameManager.newGame("test");
			  	gameManager.startGame();
	        }		
	}	 	
	
}
