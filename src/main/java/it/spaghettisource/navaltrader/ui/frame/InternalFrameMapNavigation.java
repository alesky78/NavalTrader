package it.spaghettisource.navaltrader.ui.frame;

import javax.swing.event.InternalFrameEvent;

import it.spaghettisource.navaltrader.game.GameManager;
import it.spaghettisource.navaltrader.ui.ImageIconFactory;
import it.spaghettisource.navaltrader.ui.MainDesktopPane;
import it.spaghettisource.navaltrader.ui.component.PanelDrawNavigationWithButton;
import it.spaghettisource.navaltrader.ui.event.Event;
import it.spaghettisource.navaltrader.ui.event.EventType;

public class InternalFrameMapNavigation extends InternalFrameAbstract {
	
	private PanelDrawNavigationWithButton panel;
	
	
	public InternalFrameMapNavigation(MainDesktopPane parentDesktopPane, GameManager gameManager) {
		super(parentDesktopPane, gameManager, "navigation", true, true, true, true);
		setSize(850,850);   		
		setFrameIcon(ImageIconFactory.getForFrame("/icon/globe.png"));
		
		panel = new PanelDrawNavigationWithButton(gameManager.getGameData().getCompany(), gameManager.getGameData().getWorld(), 600);
				
		getContentPane().add(panel);
		panel.start();
		
		
	}
	
	public void internalFrameClosed(InternalFrameEvent arg0) {
		super.internalFrameClosed(arg0);
		panel.stop();
	}	

	public void eventReceived(Event event) {

	}

	public EventType[] getEventsOfInterest() {
		return null;
	}

}
