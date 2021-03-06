package it.spaghettisource.navaltrader.ui.internalframe;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
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
import it.spaghettisource.navaltrader.game.model.Company;
import it.spaghettisource.navaltrader.game.model.Ship;
import it.spaghettisource.navaltrader.ui.ImageIconFactory;
import it.spaghettisource.navaltrader.ui.component.TableCellHullProgressBarPercentageRenderer;
import it.spaghettisource.navaltrader.ui.event.Event;
import it.spaghettisource.navaltrader.ui.event.EventType;
import it.spaghettisource.navaltrader.ui.frame.GameBoardDesktopPane;
import it.spaghettisource.navaltrader.ui.model.ShipListTableRow;

public class InternalFrameShipList extends InternalFrameAbstract  implements ActionListener  {

	static Log log = LogFactory.getLog(InternalFrameShipList.class.getName());

	private final static String TAB_SHIP_LIST = "ship list";	

	//UI components
	private JTabbedPane tabbedPane;	

	//ship list tab
	private EventList<ShipListTableRow> listShipData;


	public InternalFrameShipList(GameBoardDesktopPane parentDesktopPane,GameManager gameManager) {
		super(parentDesktopPane,gameManager, "ship list");
		setSize(850,300);   
		setFrameIcon(ImageIconFactory.getForFrame("/icon/agenda.png"));

		initValuesFromModel();

		tabbedPane = new JTabbedPane();		

		tabbedPane.addTab(TAB_SHIP_LIST, ImageIconFactory.getForTab("/icon/ship list.png"),createShipListPanel());


		getContentPane().add(tabbedPane);


	}

	private void initValuesFromModel() {

		Company company = gameData.getCompany();

		//sell ship tab
		listShipData = GlazedLists.threadSafeList(new BasicEventList<ShipListTableRow>());
		listShipData.addAll(ShipListTableRow.mapData(company.getShips())); 		

	}



	private Component createShipListPanel() {
		JPanel panel = new JPanel(new BorderLayout());

		///////////////////////////		
		//create the table of new ships
		JPanel shipListTablePanel = new JPanel(new BorderLayout());
		shipListTablePanel.setBorder(BorderFactory.createTitledBorder("owned ship"));			
		JTable table;		
		String[] propertyNames = new String[] { "name", "shipClassName", "model","dockedPortName", "status", "operatingCost", "hull", "actualDwt", "actualTeu", "actualFuel"};
		String[] columnLabels = new String[] { "name", "class", "model", "docked port", "status", "operating cost", "hull", "actual Dwt",  "actual Teu","actual Fuel"};
		TableFormat<ShipListTableRow> tf = GlazedLists.tableFormat(ShipListTableRow.class, propertyNames, columnLabels);
		table = new JTable(new EventTableModel<ShipListTableRow>(listShipData, tf));
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);			
		table.setAutoCreateRowSorter(true);			
		table.getColumnModel().getColumn(6).setCellRenderer(TableCellHullProgressBarPercentageRenderer.getRenderer());	

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent event) {
				try{
					ShipListTableRow data = listShipData.get(table.convertRowIndexToModel(table.getSelectedRow()));
					table.clearSelection();					

					//enter in port only if the ship is docked
					if(data.getStatus().equals(Ship.SHIP_STATUS_DOCKED)){
						parentDesktopPane.createInternalFramePort(gameManager,data.getDockedPort(),data.getShip());
					}


				}catch (Exception e) {}
			}
		});		

		shipListTablePanel.add(new JScrollPane(table), BorderLayout.CENTER);		

		//add all together
		panel.add(shipListTablePanel, BorderLayout.CENTER);		


		return panel;		
	}	


	public void actionPerformed(ActionEvent event) {

	}


	public void eventReceived(Event event) {

		EventType eventType = event.getEventType(); 

		if(eventType.equals(EventType.BUY_SHIP_EVENT)){
			listShipData.add(ShipListTableRow.mapData((Ship)event.getSource()));
		}else if(eventType.equals(EventType.SELL_SHIP_EVENT)){
			int index = listShipData.indexOf(ShipListTableRow.mapData((Ship)event.getSource()));
			if(index>-1){
				listShipData.remove(index);				
			}			
		}else if(eventType.equals(EventType.SHIP_FUEL_CHANGE_EVENT) || eventType.equals(EventType.SHIP_HULL_CHANGE_EVENT) || eventType.equals(EventType.SHIP_STATUS_CHANGE_EVENT)) {
			ShipListTableRow replaceElement = ShipListTableRow.mapData((Ship)event.getSource());
			int index = listShipData.indexOf( replaceElement);
			listShipData.set(index, replaceElement);
		}		
	}


	public EventType[] getEventsOfInterest() {
		return new EventType[]{EventType.BUY_SHIP_EVENT,EventType.SELL_SHIP_EVENT,EventType.SHIP_FUEL_CHANGE_EVENT,EventType.SHIP_HULL_CHANGE_EVENT,EventType.SHIP_STATUS_CHANGE_EVENT};
	}




}
