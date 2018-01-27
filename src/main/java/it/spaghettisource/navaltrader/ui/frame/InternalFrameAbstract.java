package it.spaghettisource.navaltrader.ui.frame;

import java.text.NumberFormat;

import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.GameData;
import it.spaghettisource.navaltrader.game.GameManager;
import it.spaghettisource.navaltrader.ui.MainDesktopPane;
import it.spaghettisource.navaltrader.ui.event.EventListener;
import it.spaghettisource.navaltrader.ui.event.EventPublisher;

public abstract class InternalFrameAbstract extends JInternalFrame implements EventListener,InternalFrameListener {

	static Log log = LogFactory.getLog(InternalFrameAbstract.class.getName());

	protected MainDesktopPane parentDesktopPane;

	protected GameManager gameManager;
	protected GameData gameData; 


	protected NumberFormat percentageFormat = NumberFormat.getPercentInstance();		

	public InternalFrameAbstract(MainDesktopPane parentDesktopPane,GameManager gameManager, String name) {
		super(name, true, true, true, true);
		try {		
			this.gameManager = gameManager; 
			this.gameData = gameManager.getGameData();
			this.parentDesktopPane = parentDesktopPane;

			//register to receive event
			log.debug("register listener: "+title);        
			EventPublisher.getInstance().register(this);

			//Set the window's location.
			setLocation(30,30);

			addInternalFrameListener(this);
		}catch (Exception e) {
			log.error("error creating internal frame",e);
		}
	}

	public void internalFrameActivated(InternalFrameEvent arg0) {	
	}

	public void internalFrameClosed(InternalFrameEvent arg0) {
		log.debug("unregister listener: "+title);
		EventPublisher.getInstance().unRegister(this);
	}

	public void internalFrameClosing(InternalFrameEvent arg0) {
	}

	public void internalFrameDeactivated(InternalFrameEvent arg0) {
	}

	public void internalFrameDeiconified(InternalFrameEvent arg0) {
	}

	public void internalFrameIconified(InternalFrameEvent arg0) {
	}

	public void internalFrameOpened(InternalFrameEvent arg0) {
	}


}
