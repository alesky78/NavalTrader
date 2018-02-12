package it.spaghettisource.navaltrader.ui.frame;

import javax.swing.JLabel;
import javax.swing.event.InternalFrameEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.GameManager;
import it.spaghettisource.navaltrader.game.loop.LoopManager;
import it.spaghettisource.navaltrader.ui.ImageIconFactory;
import it.spaghettisource.navaltrader.ui.MainDesktopPane;
import it.spaghettisource.navaltrader.ui.event.Event;
import it.spaghettisource.navaltrader.ui.event.EventType;

public class InternalFrameContractCompleted extends InternalFrameAbstract {

	static Log log = LogFactory.getLog(InternalFrameContractCompleted.class.getName());
	
	private LoopManager loopManager;
	
	
	public InternalFrameContractCompleted(MainDesktopPane parentDesktopPane, GameManager gameManager) {
		super(parentDesktopPane, gameManager, "contract completed", true, true, false, false);
		
		loopManager = gameManager.getLoopManager();
		loopManager.setPauseByGame(true);
		
		setSize(300,300);
		setFrameIcon(ImageIconFactory.getForFrame("/icon/container.png"));		
		parentDesktopPane.centerInTheDesktopPane(this);
			
		getContentPane().add(new JLabel("aperto"));
		
	}

	
	public void internalFrameClosed(InternalFrameEvent event) {
		super.internalFrameClosed(event);
		loopManager.setPauseByGame(false);
		log.info("internalFrameClosed");		
	}		
	
	public void internalFrameDeactivated(InternalFrameEvent event) {
		super.internalFrameDeactivated(event);
		this.doDefaultCloseAction();			
		log.info("internalFrameDeactivated");
	}	
	
	
	@Override
	public void eventReceived(Event event) {
	}

	@Override
	public EventType[] getEventsOfInterest() {
		return null;
	}

}
