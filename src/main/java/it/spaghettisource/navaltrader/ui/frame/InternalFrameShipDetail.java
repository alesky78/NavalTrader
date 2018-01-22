package it.spaghettisource.navaltrader.ui.frame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.GameManager;
import it.spaghettisource.navaltrader.game.model.Ship;
import it.spaghettisource.navaltrader.ui.ImageIconFactory;
import it.spaghettisource.navaltrader.ui.MainDesktopPane;
import it.spaghettisource.navaltrader.ui.SpringLayoutUtilities;
import it.spaghettisource.navaltrader.ui.component.DoubleTextField;
import it.spaghettisource.navaltrader.ui.component.HullProgressBarField;
import it.spaghettisource.navaltrader.ui.event.Event;
import it.spaghettisource.navaltrader.ui.event.EventType;

public class InternalFrameShipDetail extends InternalFrameAbstract  implements ActionListener  {

	static Log log = LogFactory.getLog(InternalFrameShipDetail.class.getName());

	private final static String TAB_SHIP_STATUS = "status";	

	private String shipName;

	//UI components
	private JTabbedPane tabbedPane;	

	//ship status tab
	private JTextField shipStatus;
	private JTextField shipType;
	private HullProgressBarField shipHull;
	private DoubleTextField shipActualFuel;	
	

	public InternalFrameShipDetail(MainDesktopPane parentDesktopPane,GameManager gameManager,String shipName) {
		super(parentDesktopPane,gameManager, shipName);
		this.shipName = shipName;
		setSize(500,300);   
		setFrameIcon(ImageIconFactory.getForFrame("/icon/ship list.png"));

		initValuesFromModel();

		tabbedPane = new JTabbedPane();		
		tabbedPane.addTab(TAB_SHIP_STATUS, ImageIconFactory.getForTab("/icon/clipboard.png"),createStatusPanel());

		getContentPane().add(tabbedPane);


	}

	private void initValuesFromModel() {

		Ship ship = gameData.getCompany().getShipByName(shipName);
		shipStatus = new JTextField(ship.getStatus());
		shipStatus.setEditable(false);		
		shipType = new JTextField(ship.getType());
		shipType.setEditable(false);
		shipHull = new HullProgressBarField(ship.getHull());
		shipActualFuel = new DoubleTextField(ship.getActualFuel());

	}



	private Component createStatusPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		
		///////////////////////////		
		//create ship status info
		JPanel statusPanel = new JPanel(new SpringLayout());		
		statusPanel.add(new Label("status"));
		statusPanel.add(shipStatus);
		statusPanel.add(new Label("type"));
		statusPanel.add(shipType);
		statusPanel.add(new Label("fuel"));
		statusPanel.add(shipActualFuel);		
		statusPanel.add(new Label("hull"));
		statusPanel.add(shipHull);		
		
		SpringLayoutUtilities.makeCompactGrid(statusPanel,4, 2,5, 5,5, 5);		

		//add all together
		panel.add(statusPanel, BorderLayout.NORTH);	
		
		return panel;		
	}	


	public void actionPerformed(ActionEvent arg0) {

	}

	public void eventReceived(Event event) {

		EventType eventType = event.getEventType(); 

		if(eventType.equals(EventType.SELL_SHIP_EVENT)){
			//if the ship related to this panel is sell close the panel
			if(gameData.getCompany().getShipByName(shipName)==null) {
				try {
					this.setClosed(true);
				} catch (PropertyVetoException e) {}
			}
		}else if(eventType.equals(EventType.SHIP_FUEL_CHANGE_EVENT)){
			Ship source = (Ship) event.getSource();			
			if(source.getName().equals(shipName)) {
				shipActualFuel.setValue(source.getActualFuel());
			}
		}else if(eventType.equals(EventType.SHIP_HULL_CHANGE_EVENT)){
			Ship source = (Ship) event.getSource();			
			if(source.getName().equals(shipName)) {
				shipHull.setValue(source.getHull());
			}
		}else if(eventType.equals(EventType.SHIP_STATUS_CHANGE_EVENT)){
			Ship source = (Ship) event.getSource();			
			if(source.getName().equals(shipName)) {
				shipStatus.setText(source.getStatus());
			}
		}			

	}


	public EventType[] getEventsOfInterest() {
		return new EventType[]{EventType.SELL_SHIP_EVENT,EventType.SHIP_STATUS_CHANGE_EVENT,EventType.SHIP_FUEL_CHANGE_EVENT,EventType.SHIP_HULL_CHANGE_EVENT,EventType.SHIP_STATUS_CHANGE_EVENT};
	}







}
