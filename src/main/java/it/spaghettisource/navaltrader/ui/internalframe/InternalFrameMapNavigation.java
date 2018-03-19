package it.spaghettisource.navaltrader.ui.internalframe;

import java.beans.PropertyVetoException;

import javax.swing.event.InternalFrameEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.GameManager;
import it.spaghettisource.navaltrader.ui.ImageIconFactory;
import it.spaghettisource.navaltrader.ui.component.PanelDrawMainMap;
import it.spaghettisource.navaltrader.ui.event.Event;
import it.spaghettisource.navaltrader.ui.event.EventType;
import it.spaghettisource.navaltrader.ui.frame.MainDesktopPane;

public class InternalFrameMapNavigation extends InternalFrameAbstract {
	
	static Log log = LogFactory.getLog(InternalFrameMapNavigation.class.getName());
	
	private PanelDrawMainMap panel;
	
	
	public InternalFrameMapNavigation(MainDesktopPane parentDesktopPane, GameManager gameManager) {
		super(parentDesktopPane, gameManager, "navigation", true, true, true, true);
		
		try {
			setSize(850,850);   
			setFrameIcon(ImageIconFactory.getForFrame("/icon/globe.png"));
			
			panel = new PanelDrawMainMap(gameManager.getGameData().getCompany(), gameManager.getGameData().getWorld(), 600);
			
			getContentPane().add(panel);
			panel.start();
			
		} catch (Exception e) {
			log.error("Erro creating InternalFrameMapNavigation",e);
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
