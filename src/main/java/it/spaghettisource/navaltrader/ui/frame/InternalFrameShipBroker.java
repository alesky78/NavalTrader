package it.spaghettisource.navaltrader.ui.frame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
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
import it.spaghettisource.navaltrader.ui.MainDesktopPane;
import it.spaghettisource.navaltrader.ui.SpringLayoutUtilities;
import it.spaghettisource.navaltrader.ui.component.CurrencyTextField;
import it.spaghettisource.navaltrader.ui.component.HullTableCellProgressBarPercentageRenderer;
import it.spaghettisource.navaltrader.ui.event.Event;
import it.spaghettisource.navaltrader.ui.event.EventType;
import it.spaghettisource.navaltrader.ui.model.BuyShipTableRow;
import it.spaghettisource.navaltrader.ui.model.SellShipTableRow;

public class InternalFrameShipBroker extends InternalFrameAbstract  implements ActionListener  {

	static Log log = LogFactory.getLog(InternalFrameShipBroker.class.getName());

	private final static String TAB_BUY_SHIP = "buy ship";
	private final static String TAB_SELL_SHIP = "sell ship";	

	private final static String ACTION_BUY_SHIP = "buy ship";
	private final static String ACTION_SELL_SHIP = "sell ship";		

	//UI components
	private JTabbedPane tabbedPane;	

	//buy ship tab
	private EventList<BuyShipTableRow> listBuyShipData;
	private JTextField newShipName;
	private JTextField newShipModel;		
	private CurrencyTextField newShipPrice;		
	private CurrencyTextField newShipNetBudget;	

	//sell ship tab
	private EventList<SellShipTableRow> listSellShipData;
	private JTextField sellShipType;			
	private JTextField sellShipName;		
	private CurrencyTextField sellShipPrice;
	private CurrencyTextField sellShipNetBudget;		


	public InternalFrameShipBroker(MainDesktopPane parentDesktopPane,GameManager gameManager) {
		super(parentDesktopPane,gameManager, "ship broker");
		setSize(850,450);   
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
		newShipModel = new JTextField("");
		newShipModel.setEditable(false);
		newShipPrice = new CurrencyTextField(0.0);		
		newShipNetBudget = new CurrencyTextField(company.getBudget());

		//sell ship tab
		listSellShipData = GlazedLists.threadSafeList(new BasicEventList<SellShipTableRow>());
		listSellShipData.addAll(SellShipTableRow.mapData(company.getShips())); 		
		
		sellShipType = new JTextField("");		
		sellShipType.setEditable(false);		
		sellShipName = new JTextField("");	
		sellShipName.setEditable(false);			
		sellShipPrice = new CurrencyTextField(0.0);			
		sellShipNetBudget = new CurrencyTextField(company.getBudget());
	}



	private Component createBuyShipPanel() {
		JPanel panel = new JPanel(new BorderLayout());

		///////////////////////////		
		//create the table of new ships
		JPanel buyShipTablePanel = new JPanel(new BorderLayout());
		buyShipTablePanel.setBorder(BorderFactory.createTitledBorder("available ship"));			
		JTable table;		
		String[] propertyNames = new String[] { "shipClass", "model", "price","operatingCost", "hull", "maxDwt", "maxTeu", "maxFuel", "maxSpeed"};
		String[] columnLabels = new String[] { "class", "model", "price","operating Cost", "hull", "max Dwt",  "max Teu", "max Fuel", "max Speed"};
		TableFormat<BuyShipTableRow> tf = GlazedLists.tableFormat(BuyShipTableRow.class, propertyNames, columnLabels);
		table = new JTable(new EventTableModel<BuyShipTableRow>(listBuyShipData, tf));	
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent event) {
				try{
					BuyShipTableRow data = listBuyShipData.get(table.convertRowIndexToModel(table.getSelectedRow()));
					table.clearSelection();					
					newShipModel.setText(data.getModel());
					newShipName.setText("");					
					newShipPrice.setValue(data.getPrice());
					newShipNetBudget.setValue(gameData.getCompany().getBudget()-data.getPrice());

				}catch (Exception e) {}
			}
		});	
		table.getColumnModel().getColumn(4).setCellRenderer(HullTableCellProgressBarPercentageRenderer.getRenderer());
		
		buyShipTablePanel.add(new JScrollPane(table), BorderLayout.CENTER);		

		///////////////////////////		
		//create the table of new ships
		JPanel chooseShipPanel = new JPanel(new SpringLayout());
		chooseShipPanel.setBorder(BorderFactory.createTitledBorder("selected ship"));

		JButton buyShipButton = new JButton(ImageIconFactory.getForTab("/icon/investment.png"));
		buyShipButton.setActionCommand(ACTION_BUY_SHIP);
		buyShipButton.addActionListener(this);

		chooseShipPanel.add(new JLabel("type"));
		chooseShipPanel.add(newShipModel);
		chooseShipPanel.add(new JLabel("name"));
		chooseShipPanel.add(newShipName);
		chooseShipPanel.add(new JLabel("price"));
		chooseShipPanel.add(newShipPrice);
		chooseShipPanel.add(new JLabel("new budget"));
		chooseShipPanel.add(newShipNetBudget);
		chooseShipPanel.add(new JLabel("buy"));
		chooseShipPanel.add(buyShipButton);				
		SpringLayoutUtilities.makeCompactGrid(chooseShipPanel,5, 2,5, 5,5, 5);	

		//add all together
		panel.add(buyShipTablePanel, BorderLayout.CENTER);
		panel.add(chooseShipPanel, BorderLayout.SOUTH);		

		return panel;		
	}

	private Component createSellShipPanel() {
		JPanel panel = new JPanel(new BorderLayout());

		///////////////////////////		
		//create the table of new ships
		JPanel sellShipTablePanel = new JPanel(new BorderLayout());
		sellShipTablePanel.setBorder(BorderFactory.createTitledBorder("owned ship"));			
		JTable table;		
		String[] propertyNames = new String[] { "name","shipClass","model", "status", "price", "operatingCost", "hull", "actualDwt",  "actualTeu", "actualFuel"};
		String[] columnLabels = new String[] {  "name","class","model", "status", "price", "operatingCost", "hull", "actualDwt",   "actualTeu", "actualFuel"};
		TableFormat<SellShipTableRow> tf = GlazedLists.tableFormat(SellShipTableRow.class, propertyNames, columnLabels);
		table = new JTable(new EventTableModel<SellShipTableRow>(listSellShipData, tf));	
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent event) {
				try{
					SellShipTableRow data = listSellShipData.get(table.convertRowIndexToModel(table.getSelectedRow()));
					table.clearSelection();					
					sellShipType.setText(data.getModel());
					sellShipName.setText(data.getName());					
					sellShipPrice.setValue(data.getPrice());
					sellShipNetBudget.setValue(gameData.getCompany().getBudget()+data.getPrice());					

				}catch (Exception e) {}
			}
		});	
		table.getColumnModel().getColumn(6).setCellRenderer(HullTableCellProgressBarPercentageRenderer.getRenderer());		
		
		sellShipTablePanel.add(new JScrollPane(table), BorderLayout.CENTER);		


		///////////////////////////		
		//create the table of new ships
		JPanel chooseShipPanel = new JPanel(new SpringLayout());
		chooseShipPanel.setBorder(BorderFactory.createTitledBorder("selected ship"));

		JButton selShipButton = new JButton(ImageIconFactory.getForTab("/icon/investment.png"));
		selShipButton.setActionCommand(ACTION_SELL_SHIP);
		selShipButton.addActionListener(this);

		
		chooseShipPanel.add(new JLabel("type"));
		chooseShipPanel.add(sellShipType);
		chooseShipPanel.add(new JLabel("name"));
		chooseShipPanel.add(sellShipName);
		chooseShipPanel.add(new JLabel("price"));
		chooseShipPanel.add(sellShipPrice);
		chooseShipPanel.add(new JLabel("new budget"));
		chooseShipPanel.add(sellShipNetBudget);		
		chooseShipPanel.add(new JLabel("sell"));
		chooseShipPanel.add(selShipButton);				
		SpringLayoutUtilities.makeCompactGrid(chooseShipPanel,5, 2,5, 5,5, 5);			

		//add all together
		panel.add(sellShipTablePanel, BorderLayout.CENTER);		
		panel.add(chooseShipPanel, BorderLayout.SOUTH);			

		return panel;		
	}	


	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		if(ACTION_BUY_SHIP.equals(command)){
			if(newShipName.getText().trim().equals("")) {
				parentDesktopPane.showErrorMessageDialog("set a name to the ship to buy it");
			}else if(gameData.getCompany().getShipByName(newShipName.getText())!=null) {
				parentDesktopPane.showErrorMessageDialog("ship wiht this name alrady exsist");
			}else if(newShipNetBudget.getValue()>0  && !newShipModel.getText().trim().equals("")){	//buy if money and valid name
				gameData.getCompany().buyShip(newShipModel.getText(), newShipName.getText(), newShipPrice.getValue());

				newShipPrice.setValue(0.0);
				newShipModel.setText("");
				newShipName.setText("");				
				newShipNetBudget.setValue(gameData.getCompany().getBudget());				
			}
		}else if(ACTION_SELL_SHIP.equals(command)){
			if(!sellShipName.getText().trim().equals("")){	//if ship is choose
				gameData.getCompany().sellShip(sellShipName.getText(),sellShipPrice.getValue());

				sellShipPrice.setValue(0.0);
				sellShipType.setText("");
				sellShipName.setText("");				
				sellShipNetBudget.setValue(gameData.getCompany().getBudget());				
			}
		}
	}


	public void eventReceived(Event event) {

		EventType eventType = event.getEventType(); 

		if(eventType.equals(EventType.BUDGET_EVENT)){
			newShipNetBudget.setValue(gameData.getCompany().getBudget()-newShipPrice.getValue());
			sellShipNetBudget.setValue(gameData.getCompany().getBudget()+sellShipPrice.getValue());			
		}else if(eventType.equals(EventType.BUY_SHIP_EVENT)){
			listSellShipData.add(SellShipTableRow.mapData((Ship)event.getSource()));
		}else if(eventType.equals(EventType.SELL_SHIP_EVENT)){
			int index = listSellShipData.indexOf(SellShipTableRow.mapData((Ship)event.getSource()));
			if(index>-1){
				listSellShipData.remove(index);				
			}
		}else if(eventType.equals(EventType.SHIP_FUEL_CHANGE_EVENT) || eventType.equals(EventType.SHIP_HULL_CHANGE_EVENT) || eventType.equals(EventType.SHIP_STATUS_CHANGE_EVENT)) {
			SellShipTableRow replaceElement = SellShipTableRow.mapData((Ship)event.getSource());
			int index = listSellShipData.indexOf( replaceElement);
			listSellShipData.set(index, replaceElement);
		}				

	}


	public EventType[] getEventsOfInterest() {
		return new EventType[]{EventType.BUY_SHIP_EVENT,EventType.SELL_SHIP_EVENT,EventType.BUDGET_EVENT,EventType.SHIP_FUEL_CHANGE_EVENT,EventType.SHIP_HULL_CHANGE_EVENT,EventType.SHIP_STATUS_CHANGE_EVENT};
	}




}
