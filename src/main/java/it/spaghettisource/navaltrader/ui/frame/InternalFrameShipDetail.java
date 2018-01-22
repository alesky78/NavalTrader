package it.spaghettisource.navaltrader.ui.frame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.GameManager;
import it.spaghettisource.navaltrader.game.model.Ship;
import it.spaghettisource.navaltrader.ui.ImageIconFactory;
import it.spaghettisource.navaltrader.ui.MainDesktopPane;
import it.spaghettisource.navaltrader.ui.SpringLayoutUtilities;
import it.spaghettisource.navaltrader.ui.component.CurrencyTextField;
import it.spaghettisource.navaltrader.ui.component.DoubleTextField;
import it.spaghettisource.navaltrader.ui.component.HullProgressBarField;
import it.spaghettisource.navaltrader.ui.event.Event;
import it.spaghettisource.navaltrader.ui.event.EventType;

public class InternalFrameShipDetail extends InternalFrameAbstract  implements ActionListener  {

	static Log log = LogFactory.getLog(InternalFrameShipDetail.class.getName());

	private final static String TAB_SHIP_STATUS = "status";	
	private final static String TAB_SHIP_REFUEL = "refuel";		
	private final static String TAB_SHIP_REPAIR = "repair";	
	
	
	private final static String ACTION_REFUEL = "refuel";

	private String shipName;
	private Ship ship; 

	//UI components
	private JTabbedPane tabbedPane;	

	//ship status tab
	private JTextField shipStatus;
	private JTextField shipType;
	private HullProgressBarField shipHull;
	private DoubleTextField shipActualFuel;
	
	//ship refuel tab
	private DoubleTextField shipMaxFuel;
	private JSlider amountToRefuelSlider;	
	private DoubleTextField amountToRefuel;
	private CurrencyTextField amountToPayForRefuel;
	private CurrencyTextField amountToPerUnitOfRefuel;	
	
		


	public InternalFrameShipDetail(MainDesktopPane parentDesktopPane,GameManager gameManager,String shipName) {
		super(parentDesktopPane,gameManager, shipName);
		this.shipName = shipName;
		setSize(500,250);   
		setFrameIcon(ImageIconFactory.getForFrame("/icon/ship list.png"));

		initValuesFromModel();

		tabbedPane = new JTabbedPane();		
		tabbedPane.addTab(TAB_SHIP_STATUS, ImageIconFactory.getForTab("/icon/clipboard.png"),createStatusPanel());
		tabbedPane.addTab(TAB_SHIP_REFUEL, ImageIconFactory.getForTab("/icon/gasoline.png"),createRefuelPanel());		
		tabbedPane.addTab(TAB_SHIP_REPAIR, ImageIconFactory.getForTab("/icon/tools.png"),createRepairPanel());				

		getContentPane().add(tabbedPane);


	}


	private void initValuesFromModel() {

		ship = gameData.getCompany().getShipByName(shipName);
		//ship status
		shipStatus = new JTextField(ship.getStatus());
		shipStatus.setEditable(false);		
		shipType = new JTextField(ship.getType());
		shipType.setEditable(false);
		shipHull = new HullProgressBarField(ship.getHull());
		shipActualFuel = new DoubleTextField(ship.getActualFuel());
		
		//ship refuel
		shipMaxFuel = new DoubleTextField(ship.getMaxFuel());
		amountToRefuelSlider = new JSlider(0, (int)(ship.getMaxFuel()-ship.getActualFuel()), 0);
		amountToRefuel = new DoubleTextField(0.0);
		amountToPayForRefuel = new CurrencyTextField(0.0);
		amountToPerUnitOfRefuel =  new CurrencyTextField(700.0);	//TODO where to get fuel price? maybe FUEL_PRICE_CHANGE EVENT

		
	}



	private Component createStatusPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createTitledBorder("ship status"));	
		
		///////////////////////////		
		//create ship status info
		JPanel statusPanel = new JPanel(new SpringLayout());		
		statusPanel.add(new Label("ship status"));
		statusPanel.add(shipStatus);
		statusPanel.add(new Label("ship type"));
		statusPanel.add(shipType);
		statusPanel.add(new Label("actual fuel"));
		statusPanel.add(shipActualFuel);		
		statusPanel.add(new Label("hull status"));
		statusPanel.add(shipHull);		

		SpringLayoutUtilities.makeCompactGrid(statusPanel,4, 2,5, 5,5, 5);		

		//add all together
		panel.add(statusPanel, BorderLayout.NORTH);	

		return panel;		
	}	


	private Component createRefuelPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createTitledBorder("refuel ship"));	
		
		///////////////////////////		
		//create ship refuel 
		JButton amountToRefuelButton = new JButton(ImageIconFactory.getForTab("/icon/money.png"));
		amountToRefuelButton.setActionCommand(ACTION_REFUEL);
		amountToRefuelButton.addActionListener(this);	
	
		amountToRefuelSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				if (!source.getValueIsAdjusting()) {
					amountToRefuel.setValue(source.getValue());
					amountToPayForRefuel.setValue(source.getValue()*amountToPerUnitOfRefuel.getValue());
				}
			}
		});
		
		JPanel refuelPanel = new JPanel(new SpringLayout());	
		refuelPanel.add(new Label("max fuel"));
		refuelPanel.add(shipMaxFuel);		
		refuelPanel.add(amountToRefuelSlider);	
		refuelPanel.add(amountToRefuelButton)	;	
		refuelPanel.add(new Label("requested amount"));		
		refuelPanel.add(amountToRefuel);
		refuelPanel.add(new Label("price per t of fuel"));		
		refuelPanel.add(amountToPerUnitOfRefuel);
		refuelPanel.add(new Label("total price"));		
		refuelPanel.add(amountToPayForRefuel);		
		
		SpringLayoutUtilities.makeCompactGrid(refuelPanel,5, 2,5, 5,5, 5);	
		
		//add all together
		panel.add(refuelPanel, BorderLayout.NORTH);	
		
		return panel;			
	}	

	
	private Component createRepairPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createTitledBorder("ship repair"));	
		return panel;	
	}
	
	
	
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		if(ACTION_REFUEL.equals(command)){
			if(gameData.getCompany().getBudget()>amountToPayForRefuel.getValue()) {
				gameData.getCompany().refuelShip(shipName, amountToRefuel.getValue(), amountToPayForRefuel.getValue());	
				//reset ui before the event to avoid multiple click
				amountToRefuelSlider.setMaximum((int)(ship.getMaxFuel()-ship.getActualFuel()));
			}else{
				parentDesktopPane.showErrorMessageDialog("not enought money");
			}
		}

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
				amountToRefuelSlider.setMaximum((int)(source.getMaxFuel()-source.getActualFuel()));
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
