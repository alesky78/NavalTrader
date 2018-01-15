package it.spaghettisource.navaltrader.ui;

import javax.swing.JInternalFrame;

import it.spaghettisource.navaltrader.game.GameManager;
import it.spaghettisource.navaltrader.ui.event.EventListener;

public abstract class InternalFrameAbstract extends JInternalFrame implements EventListener {

	private GameManager gameManager;
	
	public InternalFrameAbstract(GameManager gameManager, String name) {
		super(name, true, true, true, true);
        setSize(300,300);
        this.gameManager = gameManager; 
        
        //Set the window's location.
        setLocation(30,30);
        
	}
	
	
}
