package it.spaghettisource.navaltrader.ui;

import it.spaghettisource.navaltrader.game.GameManager;
import it.spaghettisource.navaltrader.ui.event.Event;
import it.spaghettisource.navaltrader.ui.event.EventType;

public class InternalFrameOffice extends InternalFrameAbstract {

	private GameManager gameManager;
	
	public InternalFrameOffice(GameManager gameManager) {
		super(gameManager,"Office");
        
	}


	
	
	public void eventReceived(Event event) {
		// TODO Auto-generated method stub
		
	}

	public EventType[] getEventsOfInterest() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
