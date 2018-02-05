package it.spaghettisource.navaltrader.ui.frame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.gui.TableFormat;
import ca.odell.glazedlists.swing.EventTableModel;
import it.spaghettisource.navaltrader.game.GameManager;
import it.spaghettisource.navaltrader.game.model.Port;
import it.spaghettisource.navaltrader.game.model.Ship;
import it.spaghettisource.navaltrader.game.model.TransportContract;
import it.spaghettisource.navaltrader.ui.ImageIconFactory;
import it.spaghettisource.navaltrader.ui.MainDesktopPane;
import it.spaghettisource.navaltrader.ui.SpringLayoutUtilities;
import it.spaghettisource.navaltrader.ui.component.TextFieldCurrency;
import it.spaghettisource.navaltrader.ui.component.ProgressBarHull;
import it.spaghettisource.navaltrader.ui.component.TextFieldInteger;
import it.spaghettisource.navaltrader.ui.event.Event;
import it.spaghettisource.navaltrader.ui.event.EventType;
import it.spaghettisource.navaltrader.ui.model.TransportContractTableRow;

public class InternalFramePort extends InternalFrameAbstract  implements ActionListener  {

	static Log log = LogFactory.getLog(InternalFramePort.class.getName());

	private final static String TAB_SHIP_MAINTAINACE = "maitainance";	
	private final static String TAB_TRANSPORT_CONTRACT = "contract";	


	private final static String ACTION_REFUEL = "refuel";
	private final static String ACTION_REPAIR = "repair";	
	private final static String ACTION_ACCEPT_CONTRACT = "accept contract";		

	private String shipName;
	private String portName;	
	private Ship ship; 
	private Port port; 	

	//UI components
	private JTabbedPane tabbedPane;	

	//ship status tab
	private JTextField shipStatus;
	private JTextField shipModel;
	private JTextField shipClass;	
	private ProgressBarHull shipHull;
	private TextFieldInteger shipActualFuel;
	private TextFieldCurrency operatingCost;	

	//ship maintainance tab
	private TextFieldInteger shipMaxFuel;
	private JSlider amountToRefuelSlider;	
	private TextFieldInteger amountToRefuel;
	private TextFieldCurrency amountToPayForRefuel;
	private TextFieldCurrency priceUnitOfFuel;	
	private JSlider amountToRepairSlider;	
	private TextFieldInteger amountToRepair;
	private TextFieldCurrency amountToPayForRepair;
	private TextFieldCurrency priceUnitOfRepair;		


	//ship contract tab
	private EventList<TransportContractTableRow> listNewContractData;	
	private EventList<TransportContractTableRow> listAcceptedContractData;		
	private JTable newContractTable;	
	private TextFieldInteger controlTeu;
	private TextFieldInteger controlDwt;	
	private TextFieldInteger controlFuel;	
	


	//TODO pause the time when we create this frame and reactivate when close... other way implement the notification of all the events
	public InternalFramePort(MainDesktopPane parentDesktopPane,GameManager gameManager,String portName, String shipName) {
		super(parentDesktopPane,gameManager, portName);
		try {		
			this.shipName = shipName;
			this.portName = portName;
			setSize(700,500);   
			setFrameIcon(ImageIconFactory.getForFrame("/icon/ship list.png"));

			initValuesFromModel();

			tabbedPane = new JTabbedPane();		
			tabbedPane.addTab(TAB_SHIP_MAINTAINACE, ImageIconFactory.getForTab("/icon/clipboard.png"),createMaintainancePanel());
			tabbedPane.addTab(TAB_TRANSPORT_CONTRACT, ImageIconFactory.getForTab("/icon/investment.png"),createTransportContractPanel());				
			getContentPane().add(tabbedPane);
		}catch (Exception e) {
			log.error("error creating port internal frame",e);
		}

	}


	private void initValuesFromModel() {

		ship = gameData.getCompany().getShipByName(shipName);
		port = gameData.getWorld().getPortByName(portName);

		//ship status
		shipStatus = new JTextField(ship.getStatus());
		shipStatus.setEditable(false);		
		shipModel = new JTextField(ship.getModel());
		shipModel.setEditable(false);		
		shipClass = new JTextField(ship.getShipClass());		
		shipClass.setEditable(false);
		shipHull = new ProgressBarHull(ship.getHull());
		shipActualFuel = new TextFieldInteger(ship.getFuel());
		operatingCost = new TextFieldCurrency(ship.getOperatingCost());

		//ship maintainance
		shipMaxFuel = new TextFieldInteger(ship.getMaxFuel());
		amountToRefuelSlider = new JSlider(0, ship.getMaxFuel()-ship.getFuel(), 0);
		amountToRefuel = new TextFieldInteger(0);
		amountToPayForRefuel = new TextFieldCurrency(0.0);
		priceUnitOfFuel =  new TextFieldCurrency(port.getFuelPrice()); 
		amountToRepairSlider = new JSlider(0, 100-ship.getHull(), 0);
		amountToRepair = new TextFieldInteger(0);
		amountToPayForRepair = new TextFieldCurrency(0.0);
		priceUnitOfRepair =  new TextFieldCurrency(port.getRepairPrice()); 

		//transport contract
		listNewContractData = GlazedLists.threadSafeList(new BasicEventList<TransportContractTableRow>());	
		listNewContractData.addAll(TransportContractTableRow.mapData(port.getContracts()));

		listAcceptedContractData = GlazedLists.threadSafeList(new BasicEventList<TransportContractTableRow>());	
		listNewContractData.addAll(TransportContractTableRow.mapData(ship.getTransportContracts()));

		controlTeu = new TextFieldInteger(ship.getAcceptedTeu());
		controlDwt = new TextFieldInteger(ship.getAcceptedDwt());
		controlFuel = new TextFieldInteger(ship.getFuel());
	}



	private JPanel createMaintainancePanel() {
		JPanel maintainancePanel = new JPanel(new BorderLayout());
		maintainancePanel.setBorder(BorderFactory.createTitledBorder("maintainance ship"));

		
		//STATUS PART
		JPanel globalStatusPanel = new JPanel(new BorderLayout());
		globalStatusPanel.setBorder(BorderFactory.createTitledBorder("status ship"));
		
		///////////////////////////		
		//create ship status info
		JPanel statusPanel = new JPanel(new SpringLayout());		
		statusPanel.add(new JLabel("ship status"));
		statusPanel.add(shipStatus);
		statusPanel.add(new JLabel("ship class"));
		statusPanel.add(shipClass);		
		statusPanel.add(new JLabel("ship model"));
		statusPanel.add(shipModel);
		statusPanel.add(new JLabel("daily operating cost"));
		statusPanel.add(operatingCost);		
		statusPanel.add(new JLabel("actual fuel"));
		statusPanel.add(shipActualFuel);		
		statusPanel.add(new JLabel("hull status"));
		statusPanel.add(shipHull);		

		SpringLayoutUtilities.makeCompactGrid(statusPanel,6, 2,5, 5,5, 5);			
		globalStatusPanel.add(statusPanel, BorderLayout.NORTH);	
		
		//REFUEL PART
		JPanel globalRefuelPanel = new JPanel(new BorderLayout());
		globalRefuelPanel.setBorder(BorderFactory.createTitledBorder("refuel ship"));	

		///////////////////////////		
		//create ship refuel 
		JButton amountToRefuelButton = new JButton(ImageIconFactory.getForTab("/icon/gasoline.png"));
		amountToRefuelButton.setActionCommand(ACTION_REFUEL);
		amountToRefuelButton.addActionListener(this);	

		amountToRefuelSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				if (!source.getValueIsAdjusting()) {
					amountToRefuel.setValue(source.getValue());
					amountToPayForRefuel.setValue(source.getValue()*priceUnitOfFuel.getValue());
				}
			}
		});

		JPanel refuelPanel = new JPanel(new SpringLayout());	
		refuelPanel.add(new JLabel("max fuel"));
		refuelPanel.add(shipMaxFuel);		
		refuelPanel.add(amountToRefuelSlider);	
		refuelPanel.add(amountToRefuelButton)	;	
		refuelPanel.add(new JLabel("requested amount"));		
		refuelPanel.add(amountToRefuel);
		refuelPanel.add(new JLabel("price per t of fuel"));		
		refuelPanel.add(priceUnitOfFuel);
		refuelPanel.add(new JLabel("total price"));		
		refuelPanel.add(amountToPayForRefuel);		

		SpringLayoutUtilities.makeCompactGrid(refuelPanel,5, 2,5, 5,5, 5);	
		globalRefuelPanel.add(refuelPanel, BorderLayout.NORTH);	

		//REPAIR PART
		JPanel globalShipRepairPanel = new JPanel(new BorderLayout());
		globalShipRepairPanel.setBorder(BorderFactory.createTitledBorder("repair ship"));	

		///////////////////////////		
		//create ship repair 
		JButton amountToRepairlButton = new JButton(ImageIconFactory.getForTab("/icon/tools.png"));
		amountToRepairlButton.setActionCommand(ACTION_REPAIR);
		amountToRepairlButton.addActionListener(this);	

		amountToRepairSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				if (!source.getValueIsAdjusting()) {
					amountToRepair.setValue(source.getValue());
					amountToPayForRepair.setValue(source.getValue()*priceUnitOfRepair.getValue());
				}
			}
		});

		JPanel repairPanel = new JPanel(new SpringLayout());			
		repairPanel.add(amountToRepairSlider);	
		repairPanel.add(amountToRepairlButton)	;	
		repairPanel.add(new JLabel("requested amount"));		
		repairPanel.add(amountToRepair);
		repairPanel.add(new JLabel("price per 1% of repair"));		
		repairPanel.add(priceUnitOfRepair);
		repairPanel.add(new JLabel("total price"));		
		repairPanel.add(amountToPayForRepair);		

		SpringLayoutUtilities.makeCompactGrid(repairPanel,4, 2,5, 5,5, 5);	
		globalShipRepairPanel.add(repairPanel, BorderLayout.NORTH);			


		//put all together
		maintainancePanel.add(globalStatusPanel, BorderLayout.NORTH);		
		maintainancePanel.add(globalRefuelPanel, BorderLayout.CENTER);
		maintainancePanel.add(globalShipRepairPanel, BorderLayout.SOUTH);		

		return maintainancePanel;	
	}



	private JPanel createTransportContractPanel() {
		JPanel panel = new JPanel(new BorderLayout());	
		panel.setBorder(BorderFactory.createTitledBorder("transport contract"));	


		///////////////
		//control of fuel teu and dwt				
		JPanel controPanel = new JPanel(new SpringLayout());	
		controPanel.add(new JLabel("teu status"));
		controPanel.add(controlTeu);		
		controPanel.add(new JLabel("dwt status"));	
		controPanel.add(controlDwt);
		controPanel.add(new JLabel("fuel status"));	
		controPanel.add(controlFuel);
		SpringLayoutUtilities.makeCompactGrid(controPanel,3, 2,5, 5,5, 5);	


		///////////////////////////////////
		//word map port
		JPanel mapOfPortPanel = new JPanel(new BorderLayout());
		JLabel picture = new JLabel(ImageIconFactory.getForTab("/icon/warning.png"));
		picture.setHorizontalAlignment(JLabel.CENTER);		
		JScrollPane pictureScrollPane = new JScrollPane(picture);        
		mapOfPortPanel.add(pictureScrollPane,BorderLayout.CENTER);

		///////////////
		//port contract
		JPanel portContractPanel = new JPanel(new BorderLayout());
		portContractPanel.setBorder(BorderFactory.createTitledBorder("new contract"));	
		String[] newContractpropertyNames = new String[] { "good","destinationPort", "totalTeu","totalDwt","pricePerTeu","totalPrice"};
		String[] newContractcolumnLabels = new String[] { "good","destinationPort", "totalTeu","totalDwt","pricePerTeu","totalPrice"};
		TableFormat<TransportContractTableRow> newContractTf = GlazedLists.tableFormat(TransportContractTableRow.class, newContractpropertyNames, newContractcolumnLabels);
		newContractTable = new JTable(new EventTableModel<TransportContractTableRow>(listNewContractData, newContractTf));			
		newContractTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		newContractTable.setAutoCreateRowSorter(true);		

		newContractTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent event) {
				try{
					int[] selected = newContractTable.getSelectedRows();
					//calculation on the control based on the selected rows
					TransportContractTableRow data;
					int newMaxTeu = ship.getAcceptedTeu();
					int newMaxDwt = ship.getAcceptedDwt();					
					for (int i = 0; i < selected.length; i++) {
						data = listNewContractData.get(newContractTable.convertRowIndexToModel(selected[i]));
						newMaxTeu -= data.getTotalTeu();
						newMaxDwt -= data.getTotalDwt();
						//TODO add fuel cotrol here and set new value
						//TODO add point in the map where is the port
					}

					controlTeu.setValue(newMaxTeu); 
					controlDwt.setValue(newMaxDwt);
					//controlFuel.setValue(XXXX); TODO set value for fuel cotrol 					

				}catch (Exception e) {
					log.error("error chosing contract", e);
				}
			}
		});		

		portContractPanel.add(new JScrollPane(newContractTable), BorderLayout.CENTER);
		portContractPanel.add(controPanel, BorderLayout.SOUTH);		


		//split pane with the contract and port
		//		JPanel MapControPanel = new JPanel(new BorderLayout()); 
		//		MapControPanel.add(mapOfPortPanel, BorderLayout.NORTH);
		//		MapControPanel.add(controPanel, BorderLayout.SOUTH);		

		JSplitPane newContractAndMapPortPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,mapOfPortPanel,portContractPanel);
		newContractAndMapPortPanel.setDividerLocation(200);


		///////////////	
		//accepted contract
		JTable acceptedContractTable;	
		String[] propertyNames = new String[] { "good","destinationPort", "totalTeu","totalDwt","pricePerTeu","totalPrice"};
		String[] columnLabels = new String[] { "good","destinationPort", "totalTeu","totalDwt","pricePerTeu","totalPrice"};
		TableFormat<TransportContractTableRow> acceptedContractTableTf = GlazedLists.tableFormat(TransportContractTableRow.class, propertyNames, columnLabels);
		acceptedContractTable = new JTable(new EventTableModel<TransportContractTableRow>(listAcceptedContractData, newContractTf));	
		acceptedContractTable.setAutoCreateRowSorter(true);		
		acceptedContractTable.setRowSelectionAllowed(false);

		///////////////
		//configure fuel used and speed and accept contract
		int startSpeed = 5;
		JTextField selectedSpeed = new JTextField();
		selectedSpeed.setText(startSpeed+"/"+ship.getMaxSpeed()+" nd");
		selectedSpeed.setEditable(false);		
		JSlider sliderNavigationSpeed;			
		sliderNavigationSpeed = new JSlider(JSlider.HORIZONTAL,1, ship.getMaxSpeed(), startSpeed);	
		
		sliderNavigationSpeed.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				if (!source.getValueIsAdjusting()) {
					selectedSpeed.setText(source.getValue()+"/"+ship.getMaxSpeed()+" nd");
					//TODO modify the date to arrive to the ports in the table newContractTable	
					//TODO modify the fuel used to arrive to the ports						
				}
			}
		});		

		JButton acceptContractButton = new JButton(ImageIconFactory.getForTab("/icon/investment.png"));
		acceptContractButton.setActionCommand(ACTION_ACCEPT_CONTRACT);
		acceptContractButton.addActionListener(this);

		JPanel speedControlPanel = new JPanel(new SpringLayout());	
		speedControlPanel.setBorder(BorderFactory.createTitledBorder("navigation speed"));		
		speedControlPanel.add(new JLabel("speed selection"));		
		speedControlPanel.add(selectedSpeed);		
		speedControlPanel.add(sliderNavigationSpeed);	
		speedControlPanel.add(acceptContractButton);		
		SpringLayoutUtilities.makeCompactGrid(speedControlPanel,1, 4,5, 5,5, 5);	

		//accepted contract and speed panels join
		JPanel acceptedContractAndSelectSpeedPanel = new JPanel(new BorderLayout());
		JScrollPane acceptedContract = new JScrollPane(acceptedContractTable);
		acceptedContract.setBorder(BorderFactory.createTitledBorder("accepted contract"));	
		acceptedContractAndSelectSpeedPanel.add(speedControlPanel, BorderLayout.NORTH);		
		acceptedContractAndSelectSpeedPanel.add(acceptedContract, BorderLayout.CENTER);			


		//put all togheter
		panel.add(newContractAndMapPortPanel, BorderLayout.CENTER);			
		panel.add(acceptedContractAndSelectSpeedPanel, BorderLayout.SOUTH);		


		return panel;
	}	



	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		if(ACTION_REFUEL.equals(command)){
			if(gameData.getCompany().getBudget()>amountToPayForRefuel.getValue()) {
				gameData.getCompany().refuelShip(shipName, amountToRefuel.getValue(), amountToPayForRefuel.getValue());	
				//reset ui before the event to avoid multiple click
				amountToRefuelSlider.setMaximum((int)(ship.getMaxFuel()-ship.getFuel()));
				amountToRefuelSlider.setValue(0);
			}else{
				parentDesktopPane.showErrorMessageDialog("not enought money");
			}
		}else if(ACTION_REPAIR.equals(command)){
			if(gameData.getCompany().getBudget()>amountToPayForRepair.getValue()) {
				gameData.getCompany().repairShip(shipName, amountToRepair.getValue(), amountToPayForRepair.getValue());	
				//reset ui before the event to avoid multiple click
				amountToRepairSlider.setMaximum(100-ship.getHull());
				amountToRepairSlider.setValue(0);
			}else{
				parentDesktopPane.showErrorMessageDialog("not enought money");
			}
		}else if(ACTION_ACCEPT_CONTRACT.equals(command)) {
			//accept contract 
			if(controlTeu.getValue()>=0 && controlDwt.getValue()>=0 && controlFuel.getValue()>=0) {
				int[] selected = newContractTable.getSelectedRows();
				TransportContractTableRow data;		
				List<TransportContractTableRow> selectedContract = new ArrayList<TransportContractTableRow>();				
				TransportContract contract;
				for (int i = 0; i < selected.length; i++) {
					data = listNewContractData.get(newContractTable.convertRowIndexToModel(selected[i]));
					selectedContract.add(data);
					contract = port.removeContractById(data.getId());
					ship.addContract(contract);	
					listAcceptedContractData.add(data);
				}
				
				for (TransportContractTableRow transportContractTableRow : selectedContract) {
					listNewContractData.remove(transportContractTableRow);
				}
				
				int newMaxTeu = ship.getAcceptedTeu();
				int newMaxDwt = ship.getAcceptedDwt();	
				controlTeu.setValue(newMaxTeu); 
				controlDwt.setValue(newMaxDwt);			
				//controlFuel.setValue(XXXX); TODO set value for fuel cotrol 					
				
			}else {
				parentDesktopPane.showErrorMessageDialog("the contract cannot be signed, not enought space or fuel");
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
				shipActualFuel.setValue(source.getFuel());
				amountToRefuelSlider.setMaximum(source.getMaxFuel()-source.getFuel());

				//reset the fuel control because fuel is changed
				controlTeu.setValue(ship.getAcceptedTeu());
				controlDwt.setValue(ship.getAcceptedDwt());
				controlFuel.setValue(ship.getFuel());
				newContractTable.clearSelection();

			}
		}else if(eventType.equals(EventType.SHIP_HULL_CHANGE_EVENT)){
			Ship source = (Ship) event.getSource();			
			if(source.getName().equals(shipName)) {
				shipHull.setValue(source.getHull());
				amountToRepairSlider.setMaximum(100-ship.getHull());
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
