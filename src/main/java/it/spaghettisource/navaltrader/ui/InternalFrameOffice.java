package it.spaghettisource.navaltrader.ui;

import javax.swing.JInternalFrame;

import it.spaghettisource.navaltrader.game.GameManager;

public class InternalFrameOffice extends JInternalFrame {

	private GameManager gameManager;
	
	public InternalFrameOffice(GameManager gameManager) {
		super("Office", true, true, true, true);
        setSize(300,300);
        this.gameManager = gameManager; 
        
        //Set the window's location.
        setLocation(30,30);
        
	}
	
}
