package it.spaghettisource.navaltrader.ui.frame;

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
import it.spaghettisource.navaltrader.ui.MainDesktopPane;
import it.spaghettisource.navaltrader.ui.component.TableCellProgressBarPercentageRenderer;
import it.spaghettisource.navaltrader.ui.event.Event;
import it.spaghettisource.navaltrader.ui.event.EventType;
import it.spaghettisource.navaltrader.ui.model.SellShipTableRow;
import it.spaghettisource.navaltrader.ui.model.ShipListTableRow;

public class InternalFrameShipList extends InternalFrameAbstract  implements ActionListener  {

	static Log log = LogFactory.getLog(InternalFrameShipList.class.getName());

	private final static String TAB_SHIP_LIST = "ship list";	

	//UI components
	private JTabbedPane tabbedPane;	

	//ship list tab
	private EventList<ShipListTableRow> listShipData;


	public InternalFrameShipList(MainDesktopPane parentDesktopPane,GameManager gameManager) {
		super(parentDesktopPane,gameManager, "ship list");
		setSize(600,300);   
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
		String[] propertyNames = new String[] { "name","type", "status", "price", "hull", "cargoSpace", "actualFuel"};
		String[] columnLabels = new String[] { "name","type", "status", "price", "hull", "cargoSpace", "actualFuel"};
		TableFormat<ShipListTableRow> tf = GlazedLists.tableFormat(ShipListTableRow.class, propertyNames, columnLabels);
		table = new JTable(new EventTableModel<ShipListTableRow>(listShipData, tf));
		table.getColumnModel().getColumn(4).setCellRenderer(TableCellProgressBarPercentageRenderer.getRenderer());

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent event) {
				try{
					ShipListTableRow data = listShipData.get(table.convertRowIndexToModel(table.getSelectedRow()));
					table.clearSelection();					

					JInternalFrame[] frames =  parentDesktopPane.getAllFrames();
					boolean exist = false;
					for (JInternalFrame frame : frames) {
						if(frame.getTitle().equals(data.getName())) {
							exist = true;
							frame.moveToFront(); 
							frame.setSelected(true);							
						};
					}
					
					if(!exist) {
						//open frame for specific ship
						InternalFrameShipDetail newFrame = new InternalFrameShipDetail(parentDesktopPane,gameManager,data.getName());
						newFrame.setVisible(true);
						parentDesktopPane.add(newFrame);
						newFrame.setSelected(true);
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
		String command = event.getActionCommand();

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
		}			

	}


	public EventType[] getEventsOfInterest() {
		return new EventType[]{EventType.BUY_SHIP_EVENT,EventType.SELL_SHIP_EVENT};
	}




}
