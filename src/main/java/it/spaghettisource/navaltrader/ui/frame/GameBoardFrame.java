package it.spaghettisource.navaltrader.ui.frame;

import java.beans.PropertyVetoException;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.GameManager;
import it.spaghettisource.navaltrader.ui.internalframe.InternalFrameMapNavigation;

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
		//Make dragging a little faster but perhaps uglier.
		desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);	        

		//Add content to the window and pack it
		setContentPane(desktop);
		pack();		
		setResizable(true);

		//Display the window.
		setVisible(true);

		createMapNavigationFrame();

	}
	
	private void createMapNavigationFrame() {
		InternalFrameMapNavigation frame = new InternalFrameMapNavigation(desktop, gameManager);
		desktop.add(frame);			
		try {
			frame.setMaximum(true);
			frame.setSelected(true);					
		} catch (PropertyVetoException e1) {
			e1.printStackTrace();
		}														
		frame.setVisible(true);

		try {
			frame.setSelected(true);
		} catch (java.beans.PropertyVetoException e) {}
	}	 	


	

}
