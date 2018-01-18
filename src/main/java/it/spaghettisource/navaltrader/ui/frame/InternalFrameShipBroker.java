package it.spaghettisource.navaltrader.ui.frame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.GameManager;
import it.spaghettisource.navaltrader.ui.ImageIconFactory;
import it.spaghettisource.navaltrader.ui.InternalFrameAbstract;
import it.spaghettisource.navaltrader.ui.event.Event;
import it.spaghettisource.navaltrader.ui.event.EventType;

public class InternalFrameShipBroker extends InternalFrameAbstract  implements ActionListener  {

	static Log log = LogFactory.getLog(InternalFrameShipBroker.class.getName());

	private final static String TAB_BUY_SHIP = "buy ship";
	private final static String TAB_SELL_SHIP = "sell ship";	
	
	//UI components
	private JTabbedPane tabbedPane;	
	
	public InternalFrameShipBroker(GameManager gameManager) {
		super(gameManager, "ship broker");
		setSize(600,300);   
		setFrameIcon(ImageIconFactory.getForFrame("/icon/justice.png"));

		initValuesFromModel();

		tabbedPane = new JTabbedPane();		

		tabbedPane.addTab(TAB_BUY_SHIP, ImageIconFactory.getForTab("/icon/cart-buy.png"),createBuyShipPanel());
		tabbedPane.addTab(TAB_SELL_SHIP, ImageIconFactory.getForTab("/icon/cart-sell.png"),createSellShipPanel());		
				

		getContentPane().add(tabbedPane);

		
	}


	private Component createBuyShipPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		return panel;
	}
	
	private Component createSellShipPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		return panel;
	}	


	private void initValuesFromModel() {

		
	}


	public void eventReceived(Event event) {
		// TODO Auto-generated method stub
		
	}


	public EventType[] getEventsOfInterest() {
		// TODO Auto-generated method stub
		return null;
	}


	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
