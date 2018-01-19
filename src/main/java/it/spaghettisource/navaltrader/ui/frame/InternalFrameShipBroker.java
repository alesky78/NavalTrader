package it.spaghettisource.navaltrader.ui.frame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
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
import it.spaghettisource.navaltrader.ui.SpringLayoutUtilities;
import it.spaghettisource.navaltrader.ui.component.CurrencyTextField;
import it.spaghettisource.navaltrader.ui.event.Event;
import it.spaghettisource.navaltrader.ui.event.EventType;
import it.spaghettisource.navaltrader.ui.model.BuyShipTableRow;
import it.spaghettisource.navaltrader.ui.model.LoanTableRow;

public class InternalFrameShipBroker extends InternalFrameAbstract  implements ActionListener  {

	static Log log = LogFactory.getLog(InternalFrameShipBroker.class.getName());

	private final static String TAB_BUY_SHIP = "buy ship";
	private final static String TAB_SELL_SHIP = "sell ship";	

	//UI components
	private JTabbedPane tabbedPane;	

	//buy ship tab
	private EventList<BuyShipTableRow> listBuyShipData;
	private JTextField newShipName;
	private JTextField newShipType;		
	private CurrencyTextField newShipPrice;		
	private CurrencyTextField newNetBudget;	


	public InternalFrameShipBroker(GameManager gameManager) {
		super(gameManager, "ship broker");
		setSize(600,400);   
		setFrameIcon(ImageIconFactory.getForFrame("/icon/justice.png"));

		initValuesFromModel();

		tabbedPane = new JTabbedPane();		

		tabbedPane.addTab(TAB_BUY_SHIP, ImageIconFactory.getForTab("/icon/cart-buy.png"),createBuyShipPanel());
		tabbedPane.addTab(TAB_SELL_SHIP, ImageIconFactory.getForTab("/icon/cart-sell.png"),createSellShipPanel());		


		getContentPane().add(tabbedPane);


	}

	private void initValuesFromModel() {

		Company company = gameData.getCompany();

		//buy ship tab
		listBuyShipData = GlazedLists.threadSafeList(new BasicEventList<BuyShipTableRow>());	
		listBuyShipData.addAll(BuyShipTableRow.mapData(Ship.getListSellShip())); 
		newShipName = new JTextField("");
		newShipType = new JTextField("");
		newShipType.setEditable(false);
		newShipPrice = new CurrencyTextField(0.0);		
		newNetBudget = new CurrencyTextField(company.getBudget());
	}



	private Component createBuyShipPanel() {
		JPanel panel = new JPanel(new BorderLayout());

		///////////////////////////		
		//create the table of new ships
		JPanel buyShipTablePanel = new JPanel(new BorderLayout());
		buyShipTablePanel.setBorder(BorderFactory.createTitledBorder("available ship"));			
		JTable table;		
		String[] propertyNames = new String[] { "type", "price", "hull", "cargoSpace", "maxFuel", "maxSpeed"};
		String[] columnLabels = new String[] { "type", "price", "hull", "cargoSpace", "maxFuel", "maxSpeed"};
		TableFormat<BuyShipTableRow> tf = GlazedLists.tableFormat(BuyShipTableRow.class, propertyNames, columnLabels);
		table = new JTable(new EventTableModel<BuyShipTableRow>(listBuyShipData, tf));	
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent event) {
				try{
					BuyShipTableRow data = listBuyShipData.get(table.convertRowIndexToModel(table.getSelectedRow()));
					newShipType.setText(data.getType());
					newShipName.setText("");					
					newShipPrice.setValue(data.getPrice());
					newNetBudget.setValue(gameData.getCompany().getBudget()-data.getPrice());

				}catch (Exception e) {}
			}
		});	
		buyShipTablePanel.add(new JScrollPane(table), BorderLayout.CENTER);		

		///////////////////////////		
		//create the table of new ships
		JPanel chooseShipPanel = new JPanel(new SpringLayout());
		chooseShipPanel.setBorder(BorderFactory.createTitledBorder("selected ship"));						
		chooseShipPanel.add(new Label("type"));
		chooseShipPanel.add(newShipType);
		chooseShipPanel.add(new Label("name"));
		chooseShipPanel.add(newShipName);
		chooseShipPanel.add(new Label("price"));
		chooseShipPanel.add(newShipPrice);
		chooseShipPanel.add(new Label("new budget"));
		chooseShipPanel.add(newNetBudget);
		SpringLayoutUtilities.makeGrid(chooseShipPanel,4, 2,5, 5,5, 5);	

		//add all together
		panel.add(buyShipTablePanel, BorderLayout.CENTER);
		panel.add(chooseShipPanel, BorderLayout.SOUTH);		

		return panel;		
	}

	private Component createSellShipPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		return panel;
	}	




	public void eventReceived(Event event) {

		EventType eventType = event.getEventType(); 

		if(eventType.equals(EventType.BUDGET_EVENT)){
			newNetBudget.setValue(gameData.getCompany().getBudget()-newShipPrice.getValue());
		}		

	}


	public EventType[] getEventsOfInterest() {
		return new EventType[]{EventType.BUDGET_EVENT};
	}


	public void actionPerformed(ActionEvent e) {

	}

}
