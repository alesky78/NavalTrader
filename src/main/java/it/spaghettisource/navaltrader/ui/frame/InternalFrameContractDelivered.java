package it.spaghettisource.navaltrader.ui.frame;

import javax.swing.JPanel;
import javax.swing.event.InternalFrameEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.GameManager;
import it.spaghettisource.navaltrader.game.loop.LoopManager;
import it.spaghettisource.navaltrader.ui.ImageIconFactory;
import it.spaghettisource.navaltrader.ui.MainDesktopPane;
import it.spaghettisource.navaltrader.ui.event.Event;
import it.spaghettisource.navaltrader.ui.event.EventType;

public class InternalFrameContractDelivered extends InternalFrameAbstract {

	static Log log = LogFactory.getLog(InternalFrameContractDelivered.class.getName());
	
	private LoopManager loopManager;
	
	
	public InternalFrameContractDelivered(MainDesktopPane parentDesktopPane, GameManager gameManager) {
		super(parentDesktopPane, gameManager, "contract delivered", true, true, false, false);
		
		loopManager = gameManager.getLoopManager();
		loopManager.setPauseByGame(true);
		
		setSize(300,300);
		setFrameIcon(ImageIconFactory.getForFrame("/icon/container.png"));		
		parentDesktopPane.centerInTheDesktopPane(this);
			
		getContentPane().add(createContentPanel());
		
	}

	
	private JPanel createContentPanel() {
		JPanel panel = new JPanel();
		
		return panel;
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
