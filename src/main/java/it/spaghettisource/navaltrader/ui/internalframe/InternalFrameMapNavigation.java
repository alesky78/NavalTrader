package it.spaghettisource.navaltrader.ui.internalframe;

import java.beans.PropertyVetoException;

import javax.swing.event.InternalFrameEvent;

import it.spaghettisource.navaltrader.game.GameManager;
import it.spaghettisource.navaltrader.ui.ImageIconFactory;
import it.spaghettisource.navaltrader.ui.component.PanelDrawMainMap;
import it.spaghettisource.navaltrader.ui.event.Event;
import it.spaghettisource.navaltrader.ui.event.EventType;
import it.spaghettisource.navaltrader.ui.frame.MainDesktopPane;

public class InternalFrameMapNavigation extends InternalFrameAbstract {
	
	private PanelDrawMainMap panel;
	
	
	public InternalFrameMapNavigation(MainDesktopPane parentDesktopPane, GameManager gameManager) {
		super(parentDesktopPane, gameManager, "navigation", false, false, false, false);
		
		try {
			setMaximum(true);
			
			setFrameIcon(ImageIconFactory.getForFrame("/icon/globe.png"));
			
			panel = new PanelDrawMainMap(gameManager.getGameData().getCompany(), gameManager.getGameData().getWorld(), 600);
			
			getContentPane().add(panel);
			panel.start();
			
		} catch (PropertyVetoException e) {
		
			e.printStackTrace();
		}
		
	}
	
	public void internalFrameClosed(InternalFrameEvent arg0) {
		panel.stop();		
		super.internalFrameClosed(arg0);
	}	

	public void eventReceived(Event event) {

	}

	public EventType[] getEventsOfInterest() {
		return null;
	}

}
