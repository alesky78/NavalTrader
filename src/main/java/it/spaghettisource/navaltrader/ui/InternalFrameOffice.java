package it.spaghettisource.navaltrader.ui;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import it.spaghettisource.navaltrader.game.GameManager;
import it.spaghettisource.navaltrader.ui.event.Event;
import it.spaghettisource.navaltrader.ui.event.EventType;

public class InternalFrameOffice extends InternalFrameAbstract  implements ChangeListener {

	private final static String TAB_FINANCIAL_STATUS = "financial status";
	private final static String TAB_BANK = "bank";
	private final static String TAB_MARKET = "ship market";		
	
	//UI components
	JTabbedPane tabbedPane;
	
	//game components
	private GameManager gameManager;
	
	public InternalFrameOffice(GameManager gameManager) {
		super(gameManager,"Office");
        setFrameIcon(ImageIconFactory.getForFrame("/icon/desk.png"));
		
		tabbedPane = new JTabbedPane();		
		tabbedPane.addChangeListener(this);
        
		tabbedPane.addTab(TAB_FINANCIAL_STATUS, ImageIconFactory.getForTab("/icon/coins.png"),null);
		tabbedPane.addTab(TAB_BANK, ImageIconFactory.getForTab("/icon/bank.png"),null);
		tabbedPane.addTab(TAB_MARKET, ImageIconFactory.getForTab("/icon/justice.png"),null);				
	
		
		getContentPane().add(tabbedPane);
		
	}


	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	public void eventReceived(Event event) {
		// TODO Auto-generated method stub
		
	}

	public EventType[] getEventsOfInterest() {
		// TODO Auto-generated method stub
		return null;
	}


	
}
