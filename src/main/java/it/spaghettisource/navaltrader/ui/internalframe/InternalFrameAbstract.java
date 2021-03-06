package it.spaghettisource.navaltrader.ui.internalframe;

import java.text.NumberFormat;

import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.GameData;
import it.spaghettisource.navaltrader.game.GameManager;
import it.spaghettisource.navaltrader.ui.event.EventListener;
import it.spaghettisource.navaltrader.ui.event.EventPublisher;
import it.spaghettisource.navaltrader.ui.frame.GameBoardDesktopPane;

public abstract class InternalFrameAbstract extends JInternalFrame implements EventListener,InternalFrameListener {

	static Log log = LogFactory.getLog(InternalFrameAbstract.class.getName());

	protected GameBoardDesktopPane parentDesktopPane;

	protected GameManager gameManager;
	protected GameData gameData; 


	protected NumberFormat percentageFormat = NumberFormat.getPercentInstance();		

	public InternalFrameAbstract(GameBoardDesktopPane parentDesktopPane,GameManager gameManager, String name) {
		this(parentDesktopPane, gameManager, name, true, true, true, true);
	}
	
	
	public InternalFrameAbstract(GameBoardDesktopPane parentDesktopPane,GameManager gameManager, String name,boolean resizable, boolean closable,boolean maximizable, boolean iconifiable) {
		super(name, resizable, closable, maximizable, iconifiable);
		
		//TODO to make this frame undecorated
//		BasicInternalFrameTitlePane titlePane = (BasicInternalFrameTitlePane) ((BasicInternalFrameUI) getUI()).getNorthPane();
//		remove(titlePane);
		
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

	public void internalFrameActivated(InternalFrameEvent event) {	
	}

	public void internalFrameClosed(InternalFrameEvent event) {
		log.debug("unregister listener: "+title);
		EventPublisher.getInstance().unRegister(this);
	}

	public void internalFrameClosing(InternalFrameEvent event) {
	}

	public void internalFrameDeactivated(InternalFrameEvent event) {
	}

	public void internalFrameDeiconified(InternalFrameEvent event) {
	}

	public void internalFrameIconified(InternalFrameEvent event) {
	}

	public void internalFrameOpened(InternalFrameEvent event) {
	}


}
