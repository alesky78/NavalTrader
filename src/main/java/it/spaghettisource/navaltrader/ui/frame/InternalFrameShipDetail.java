package it.spaghettisource.navaltrader.ui.frame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.GameManager;
import it.spaghettisource.navaltrader.ui.ImageIconFactory;
import it.spaghettisource.navaltrader.ui.MainDesktopPane;
import it.spaghettisource.navaltrader.ui.event.Event;
import it.spaghettisource.navaltrader.ui.event.EventType;

public class InternalFrameShipDetail extends InternalFrameAbstract  implements ActionListener  {

	static Log log = LogFactory.getLog(InternalFrameShipDetail.class.getName());

	private final static String TAB_SHIP_STATUS = "status";	


	
	//UI components
	private JTabbedPane tabbedPane;	

	//ship list tab
	private String shipName;


	public InternalFrameShipDetail(MainDesktopPane parentDesktopPane,GameManager gameManager,String shipName) {
		super(parentDesktopPane,gameManager, shipName);
		this.shipName = shipName;
		setSize(500,300);   
		setFrameIcon(ImageIconFactory.getForFrame("/icon/ship list.png"));

		initValuesFromModel();

		tabbedPane = new JTabbedPane();		
		tabbedPane.addTab(TAB_SHIP_STATUS, ImageIconFactory.getForTab("/icon/ship list.png"),createStatusPanel());

		getContentPane().add(tabbedPane);


	}

	private void initValuesFromModel() {


	}



	private Component createStatusPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		return panel;		
	}	


	public void actionPerformed(ActionEvent arg0) {

	}

	public void eventReceived(Event event) {

		EventType eventType = event.getEventType(); 

		if(eventType.equals(EventType.SELL_SHIP_EVENT)){
			//if the ship related to this panel is sell close the panel
			log.debug("delete request");
			if(gameData.getCompany().getShipByName(shipName)==null) {
				try {
					this.setClosed(true);
				} catch (PropertyVetoException e) {}
			}
		}	

	}


	public EventType[] getEventsOfInterest() {
		return new EventType[]{EventType.SELL_SHIP_EVENT};
	}







}
