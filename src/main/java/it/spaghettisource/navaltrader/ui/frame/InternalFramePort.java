package it.spaghettisource.navaltrader.ui.frame;

import java.awt.BorderLayout;
import java.awt.Component;
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
import javax.swing.event.InternalFrameEvent;
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
import it.spaghettisource.navaltrader.game.loop.LoopManager;
import it.spaghettisource.navaltrader.game.model.Port;
import it.spaghettisource.navaltrader.game.model.Route;
import it.spaghettisource.navaltrader.game.model.Ship;
import it.spaghettisource.navaltrader.game.model.TransportContract;
import it.spaghettisource.navaltrader.game.model.World;
import it.spaghettisource.navaltrader.ui.ImageIconFactory;
import it.spaghettisource.navaltrader.ui.MainDesktopPane;
import it.spaghettisource.navaltrader.ui.SpringLayoutUtilities;
import it.spaghettisource.navaltrader.ui.component.PanelDrawRoute;
import it.spaghettisource.navaltrader.ui.component.ProgressBarHull;
import it.spaghettisource.navaltrader.ui.component.TextFieldCurrency;
import it.spaghettisource.navaltrader.ui.component.TextFieldDouble;
import it.spaghettisource.navaltrader.ui.component.TextFieldInteger;
import it.spaghettisource.navaltrader.ui.event.Event;
import it.spaghettisource.navaltrader.ui.event.EventType;
import it.spaghettisource.navaltrader.ui.model.TransportContractTableRow;

public class InternalFramePort extends InternalFrameAbstract  implements ActionListener  {

	static Log log = LogFactory.getLog(InternalFramePort.class.getName());

	private final static String TAB_SHIP_MAINTAINACE = "maitainance";	
	private final static String TAB_TRANSPORT_CONTRACT = "contract";	
	private final static String TAB_CAST_OFF = "cast off";	


	private final static String ACTION_REFUEL = "refuel";
	private final static String ACTION_REPAIR = "repair";	
	private final static String ACTION_ACCEPT_CONTRACT = "accept contract";		
	private final static String ACTION_SAIL = "sail";	

	private LoopManager loopManager;
	private String shipName;
	private String portName;	
	private Ship ship; 
	private Port port; 	
	private World world; 	

	//UI components
	private JTabbedPane tabbedPane;	

	//ship status tab
	private JTextField shipStatus;
	private JTextField shipModel;
	private JTextField shipClass;	
	private ProgressBarHull shipHull;
	private TextFieldDouble shipActualFuel;
	private TextFieldCurrency operatingCost;	

	//ship maintainance tab
	private TextFieldDouble shipMaxFuel;
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
	private TextFieldDouble controlFuel;	

	//ship sail tab
	private JTable sailContractTable;	
	private TextFieldDouble sailControlFuel;	
	private JSlider sliderNavigationSpeed;		


	public InternalFramePort(MainDesktopPane parentDesktopPane,GameManager gameManager,String portName, String shipName) {
		super(parentDesktopPane,gameManager, portName);
		try {		
			this.shipName = shipName;
			this.portName = portName;
			loopManager = gameManager.getLoopManager();
			loopManager.setPauseByGame(true);
			
			setSize(700,500);
		
			setFrameIcon(ImageIconFactory.getForFrame("/icon/ship list.png"));

			
			initValuesFromModel();

			tabbedPane = new JTabbedPane();		
			tabbedPane.addTab(TAB_SHIP_MAINTAINACE, ImageIconFactory.getForTab("/icon/clipboard.png"),createMaintainancePanel());
			tabbedPane.addTab(TAB_TRANSPORT_CONTRACT, ImageIconFactory.getForTab("/icon/investment.png"),createTransportContractPanel());		
			tabbedPane.addTab(TAB_CAST_OFF, ImageIconFactory.getForTab("/icon/helm.png"),createCastOffPanel());			


			getContentPane().add(tabbedPane);
		}catch (Exception e) {
			log.error("error creating port internal frame",e);
		}

	}

	
	public void internalFrameClosed(InternalFrameEvent arg0) {
		super.internalFrameClosed(arg0);
		loopManager.setPauseByGame(false);
	}	


	private void initValuesFromModel() {

		ship = gameData.getCompany().getShipByName(shipName);
		world = gameData.getWorld();
		port = gameData.getWorld().getPortByName(portName);

		//ship status
		shipStatus = new JTextField(ship.getStatus());
		shipStatus.setEditable(false);		
		shipModel = new JTextField(ship.getModel());
		shipModel.setEditable(false);		
		shipClass = new JTextField(ship.getShipClass());		
		shipClass.setEditable(false);
		shipHull = new ProgressBarHull(ship.getHull());
		shipActualFuel = new TextFieldDouble(ship.getFuel());
		operatingCost = new TextFieldCurrency(ship.getOperatingCost());

		//ship maintainance
		shipMaxFuel = new TextFieldDouble(ship.getMaxFuel());
		amountToRefuelSlider = new JSlider(0, (int)Math.ceil(ship.getMaxFuel()-ship.getFuel()) , 0);
		amountToRefuel = new TextFieldInteger(0);
		amountToPayForRefuel = new TextFieldCurrency(0.0);
		priceUnitOfFuel =  new TextFieldCurrency(port.getFuelPrice()); 
		amountToRepairSlider = new JSlider(0, 100-ship.getHull(), 0);
		amountToRepair = new TextFieldInteger(0);
		amountToPayForRepair = new TextFieldCurrency(0.0);
		priceUnitOfRepair =  new TextFieldCurrency(port.getRepairPrice()); 

		//transport contract
		listNewContractData = GlazedLists.threadSafeList(new BasicEventList<TransportContractTableRow>());	
		listNewContractData.addAll(TransportContractTableRow.mapData(port.getMarket().getContracts()));

		listAcceptedContractData = GlazedLists.threadSafeList(new BasicEventList<TransportContractTableRow>());	
		listAcceptedContractData.addAll(TransportContractTableRow.mapData(ship.getTransportContracts()));

		controlTeu = new TextFieldInteger(ship.getAcceptedTeu());
		controlDwt = new TextFieldInteger(ship.getAcceptedDwt());
		controlFuel = new TextFieldDouble(ship.getFuel());

		sailControlFuel = new TextFieldDouble(ship.getFuel());
	}



	/**
	 * MaintainancePanel
	 * 
	 * @return
	 */
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



	/**
	 * TransportContractPanel
	 * 
	 * @return
	 */
	private JPanel createTransportContractPanel() {
		JPanel panel = new JPanel(new BorderLayout());	
		panel.setBorder(BorderFactory.createTitledBorder("transport contract"));	


		///////////////
		//control of fuel teu and dwt				
		JPanel controPanel = new JPanel(new SpringLayout());	
		controPanel.add(new JLabel("teu forecast"));
		controPanel.add(controlTeu);		
		controPanel.add(new JLabel("dwt forecast"));	
		controPanel.add(controlDwt);
		controPanel.add(new JLabel("fuel forecast"));	
		controPanel.add(controlFuel);
		SpringLayoutUtilities.makeCompactGrid(controPanel,3, 2,5, 5,5, 5);	


		///////////////
		//configure fuel used and speed and accept contract			
		int startSpeed = 5;		
		JTextField selectedSpeed = new JTextField();
		JTextField selectedFuelConsumption = new JTextField();		
		selectedSpeed.setText(startSpeed+"/"+ship.getMaxSpeed()+" nd");
		selectedSpeed.setEditable(false);	
		selectedFuelConsumption.setText(ship.getFuelConsumptionPerHour(startSpeed) +"t /"+ship.getFuelConsumptionPerHour(startSpeed)*24+" t");
		selectedFuelConsumption.setEditable(false);

		JSlider sliderNavigationSpeed;			
		sliderNavigationSpeed = new JSlider(JSlider.HORIZONTAL,1, ship.getMaxSpeed(), startSpeed);

		//set the day and distance to destination for all contracts
		TransportContractTableRow contract;		
		Route route;
		for (int index = 0; index < listNewContractData.size(); index++) {
			contract = listNewContractData.get(index);
			route = port.getRouteTo(contract.getDestinationPort());
			contract.setDaysToDestination(route.calcDaysToDestination(startSpeed));
			contract.setDistance(route.getDistanceInScale());
			listNewContractData.set(index, contract);
		}



		sliderNavigationSpeed.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				if (!source.getValueIsAdjusting()) {
					selectedSpeed.setText(source.getValue()+"/"+ship.getMaxSpeed()+" nd");
					selectedFuelConsumption.setText(ship.getFuelConsumptionPerHour(source.getValue()) +" t hour /"+ship.getFuelConsumptionPerHour(source.getValue())*24+" t day");	

					if(newContractTable!=null) {	//reset the control of the fuel
						TransportContractTableRow contract;		
					
						double newMaxFule = ship.getFuel();
						int[] selected = newContractTable.getSelectedRows();
						for (int i = 0; i < selected.length; i++) {	//Calculate the used fuel based on selected rows
							contract = listNewContractData.get(newContractTable.convertRowIndexToModel(selected[i]));
							newMaxFule -= ship.getFuelConsumptionPerDistance(sliderNavigationSpeed.getValue(), contract.getDistance());
						}		
						Route route;	
						for (int index = 0; index < listNewContractData.size(); index++) {	//reset the days to arrive at destination for each contract
							contract = listNewContractData.get(index);
							route = port.getRouteTo(contract.getDestinationPort());							
							contract.setDaysToDestination(route.calcDaysToDestination(source.getValue()));
							listNewContractData.set(index, contract);
						}						
						controlFuel.setValue(newMaxFule);
					}


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
		speedControlPanel.add(new JLabel("fuel consumption per hour/day"));	
		speedControlPanel.add(selectedFuelConsumption);
		speedControlPanel.add(new JLabel("select speed"));		
		speedControlPanel.add(sliderNavigationSpeed);	
		speedControlPanel.add(new JLabel("sign contract"));		
		speedControlPanel.add(acceptContractButton);		
		SpringLayoutUtilities.makeCompactGrid(speedControlPanel,2, 4,5, 5,5, 5);	


		///////////////////////////////////
		//word map port
		PanelDrawRoute mapOfPortPanel = new PanelDrawRoute(port, world, 600); 

		
		///////////////
		//port contract
		JPanel portContractPanel = new JPanel(new BorderLayout());
		portContractPanel.setBorder(BorderFactory.createTitledBorder("new contract"));	
		String[] newContractpropertyNames = new String[] { "productName","destinationPortName", "distance", "daysToDestination", "totalTeu","totalDwt","pricePerTeu","totalPrice"};
		String[] newContractcolumnLabels  = new String[] { "productName","destinationPortName", "distance", "daysToDestination", "totalTeu","totalDwt","pricePerTeu","totalPrice"};
		TableFormat<TransportContractTableRow> newContractTf = GlazedLists.tableFormat(TransportContractTableRow.class, newContractpropertyNames, newContractcolumnLabels);
		newContractTable = new JTable(new EventTableModel<TransportContractTableRow>(listNewContractData, newContractTf));			
		newContractTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		newContractTable.setAutoCreateRowSorter(true);		

		newContractTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent event) {
				try{
					int[] selected = newContractTable.getSelectedRows();
					//calculation on the control based on the selected rows
					TransportContractTableRow contract;
					int newMaxTeu = ship.getAcceptedTeu();
					int newMaxDwt = ship.getAcceptedDwt();	
					double newMaxFule = ship.getFuel();		
					List<Route> routes = new ArrayList<Route>();
					for (int i = 0; i < selected.length; i++) {
						contract = listNewContractData.get(newContractTable.convertRowIndexToModel(selected[i]));
						newMaxTeu -= contract.getTotalTeu();
						newMaxDwt -= contract.getTotalDwt();
						newMaxFule -= ship.getFuelConsumptionPerDistance(sliderNavigationSpeed.getValue(), contract.getDistance());
						routes.add(port.getRouteTo(contract.getDestinationPort()));
					}

					controlTeu.setValue(newMaxTeu); 
					controlDwt.setValue(newMaxDwt);
					controlFuel.setValue(newMaxFule);

					//add the path to the map
					mapOfPortPanel.setRoutes(routes);

				}catch (Exception e) {
					log.error("error chosing contract", e);
				}
			}
		});		

		portContractPanel.add(new JScrollPane(newContractTable), BorderLayout.CENTER);
		portContractPanel.add(controPanel, BorderLayout.SOUTH);		


		JSplitPane newContractAndMapPortPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,mapOfPortPanel,portContractPanel);
		newContractAndMapPortPanel.setDividerLocation(450);


		////////////////////	
		//accepted contracts
		JTable acceptedContractTable;	
		String[] propertyNames = new String[] { "productName","destinationPortName", "distance",  "totalTeu","totalDwt","pricePerTeu","totalPrice"};
		String[] columnLabels = new String[] { "productName","destinationPortName", "distance",  "totalTeu","totalDwt","pricePerTeu","totalPrice"};
		TableFormat<TransportContractTableRow> acceptedContractTableTf = GlazedLists.tableFormat(TransportContractTableRow.class, propertyNames, columnLabels);
		acceptedContractTable = new JTable(new EventTableModel<TransportContractTableRow>(listAcceptedContractData, acceptedContractTableTf));	
		acceptedContractTable.setAutoCreateRowSorter(true);		
		acceptedContractTable.setRowSelectionAllowed(false);


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


	/**
	 * SailPanel
	 * 
	 * @return
	 */
	//TODO must be possible also set routes without have contracts
	private Component createCastOffPanel() {

		JPanel panel = new JPanel(new BorderLayout());	
		panel.setBorder(BorderFactory.createTitledBorder("sail"));	


		///////////////
		//configure fuel used and speed and accept contract			
		int startSpeed = 5;		
		JTextField selectedSpeed = new JTextField();
		JTextField selectedFuelConsumption = new JTextField();		
		selectedSpeed.setText(startSpeed+"/"+ship.getMaxSpeed()+" nd");
		selectedSpeed.setEditable(false);	
		selectedFuelConsumption.setText(ship.getFuelConsumptionPerHour(startSpeed) +"t /"+ship.getFuelConsumptionPerHour(startSpeed)*24+" t");
		selectedFuelConsumption.setEditable(false);

		
		sliderNavigationSpeed = new JSlider(JSlider.HORIZONTAL,1, ship.getMaxSpeed(), startSpeed);

		//set the day and distance to destination for all contracts
		TransportContractTableRow contract;		
		Route route;	
		for (int index = 0; index < listAcceptedContractData.size(); index++) {
			contract = listAcceptedContractData.get(index);
			route = port.getRouteTo(contract.getDestinationPort());
			contract.setDaysToDestination(route.calcDaysToDestination(startSpeed));
			contract.setDistance(route.getDistanceInScale());
			listAcceptedContractData.set(index, contract);
		}

		sliderNavigationSpeed.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				if (!source.getValueIsAdjusting()) {
					selectedSpeed.setText(source.getValue()+"/"+ship.getMaxSpeed()+" nd");
					selectedFuelConsumption.setText(ship.getFuelConsumptionPerHour(source.getValue()) +" t hour /"+ship.getFuelConsumptionPerHour(source.getValue())*24+" t day");	

					if(sailContractTable!=null) {	//reset the control of the fuel
						TransportContractTableRow contract;		
						Route route;						
						double newMaxFule = ship.getFuel();
						int index = sailContractTable.getSelectedRow();
						if(index!=-1){	
							contract = listAcceptedContractData.get(sailContractTable.convertRowIndexToModel(index));
							route = port.getRouteTo(contract.getDestinationPort());
							
							contract.setDaysToDestination(route.calcDaysToDestination(source.getValue()));							
							listAcceptedContractData.set(index, contract);	//TODO there is a bug, only the selected contract change days to destination but must be done for all						
							newMaxFule -= ship.getFuelConsumptionPerDistance(sliderNavigationSpeed.getValue(), contract.getDistance());							
						}

						sailControlFuel.setValue(newMaxFule); 
					}
				}
			}
		});				

		JButton acceptRouteButton = new JButton(ImageIconFactory.getForTab("/icon/helm.png"));
		acceptRouteButton.setActionCommand(ACTION_SAIL);
		acceptRouteButton.addActionListener(this);

		JPanel speedControlPanel = new JPanel(new SpringLayout());	
		speedControlPanel.setBorder(BorderFactory.createTitledBorder("navigation speed"));		
		speedControlPanel.add(new JLabel("speed selection"));		
		speedControlPanel.add(selectedSpeed);	
		speedControlPanel.add(new JLabel("fuel consumption per hour/day"));	
		speedControlPanel.add(selectedFuelConsumption);
		speedControlPanel.add(new JLabel("select speed"));		
		speedControlPanel.add(sliderNavigationSpeed);
		speedControlPanel.add(new JLabel("fuel forecast"));		
		speedControlPanel.add(sailControlFuel);		
		speedControlPanel.add(new JLabel("accept route"));		
		speedControlPanel.add(acceptRouteButton);		
		SpringLayoutUtilities.makeCompactGrid(speedControlPanel,5, 2,5, 5,5, 5);			


		///////////////////////////////////
		//word map port
		PanelDrawRoute mapOfPortPanel = new PanelDrawRoute(port, world, 600); 
		

		/////////////////////////
		//accepted contract table
		JPanel acceptedContractPanel = new JPanel(new BorderLayout());
		acceptedContractPanel.setBorder(BorderFactory.createTitledBorder("accepted contract"));			
		String[] propertyNames = new String[] { "productName","destinationPortName", "distance","daysToDestination", "totalTeu","totalDwt","pricePerTeu","totalPrice"};
		String[] columnLabels = new String[] { "productName","destinationPortName", "distance", "daysToDestination","totalTeu","totalDwt","pricePerTeu","totalPrice"};
		TableFormat<TransportContractTableRow> acceptedContractTableTf = GlazedLists.tableFormat(TransportContractTableRow.class, propertyNames, columnLabels);
		sailContractTable = new JTable(new EventTableModel<TransportContractTableRow>(listAcceptedContractData, acceptedContractTableTf));	
		sailContractTable.setAutoCreateRowSorter(true);	
		sailContractTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);			

		sailContractTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent event) {
				try{
					int selected = sailContractTable.getSelectedRow();
					List<Route> routes = new ArrayList<Route>();

					if(selected!=-1){
						TransportContractTableRow contract = listAcceptedContractData.get(sailContractTable.convertRowIndexToModel(selected));
						routes.add(port.getRouteTo(contract.getDestinationPort()));

						double newMaxFule = ship.getFuel();
						newMaxFule -= ship.getFuelConsumptionPerDistance(sliderNavigationSpeed.getValue(), contract.getDistance());
						sailControlFuel.setValue(newMaxFule);
					}

					//add the path to the map
					mapOfPortPanel.setRoutes(routes);

				}catch (Exception e) {
					log.error("error chosing contract", e);
				}
			}
		});			

		acceptedContractPanel.add(new JScrollPane(sailContractTable), BorderLayout.CENTER);

		//join the panels
		JSplitPane newContractAndMapRoutePanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,mapOfPortPanel,acceptedContractPanel);
		newContractAndMapRoutePanel.setDividerLocation(600);		

		JPanel selectSpeedPanel = new JPanel(new BorderLayout());		
		selectSpeedPanel.add(speedControlPanel, BorderLayout.NORTH);		


		//put all togheter	
		panel.add(newContractAndMapRoutePanel, BorderLayout.NORTH);
		panel.add(selectSpeedPanel, BorderLayout.CENTER);			

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
					contract = port.getMarket().removeContractById(data.getId());
					ship.addContract(contract);	
					listAcceptedContractData.add(data);
				}

				for (TransportContractTableRow transportContractTableRow : selectedContract) {
					listNewContractData.remove(transportContractTableRow);
				}

				int newMaxTeu = ship.getAcceptedTeu();
				int newMaxDwt = ship.getAcceptedDwt();
				double newMaxFuel = ship.getFuel();				
				controlTeu.setValue(newMaxTeu); 
				controlDwt.setValue(newMaxDwt);			
				controlFuel.setValue(newMaxFuel);  					

			}else {
				parentDesktopPane.showErrorMessageDialog("the contract cannot be signed, not enought space or fuel");
			}

		}else if (ACTION_SAIL.equals(command)){
			
			int selectedRoute = sailContractTable.getSelectedRow();

			if(sailControlFuel.getValue()>=0 && selectedRoute!=-1) {
				int selectedSpeed = sliderNavigationSpeed.getValue();
				TransportContractTableRow contract = listAcceptedContractData.get(sailContractTable.convertRowIndexToModel(selectedRoute));
	
				ship.loadShipAndPrepareToNavigate(selectedSpeed, port.getRouteTo(contract.getDestinationPort()));
				
				this.doDefaultCloseAction();	//close the frame ship no more in the port				
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
				amountToRefuelSlider.setMaximum((int)Math.ceil(source.getMaxFuel()-source.getFuel()));

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
