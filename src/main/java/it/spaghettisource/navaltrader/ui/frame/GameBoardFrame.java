package it.spaghettisource.navaltrader.ui.frame;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.GameManager;

public class GameBoardFrame extends JFrame{

	static Log log = LogFactory.getLog(GameBoardFrame.class.getName());

	//ui components
	private GameBoardDesktopPane desktop;
	private JMenuBar menuBar;

	//game components
	private GameManager gameManager;

	public GameBoardFrame(GameManager gameManager) {
		super("Naval Trader");
		
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setUndecorated(true);
		//setIconImage(ImageIconFactory.getAppImage());
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.gameManager = gameManager;
		desktop = new GameBoardDesktopPane(gameManager);
       

		//Add content to the window and pack it
		setContentPane(desktop);
		pack();		
		setResizable(true);

		//Display the window.
		setVisible(true);

	}
	

}
