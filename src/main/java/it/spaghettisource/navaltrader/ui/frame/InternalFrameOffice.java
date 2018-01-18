package it.spaghettisource.navaltrader.ui.frame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
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
import it.spaghettisource.navaltrader.game.model.Bank;
import it.spaghettisource.navaltrader.game.model.Company;
import it.spaghettisource.navaltrader.game.model.Finance;
import it.spaghettisource.navaltrader.ui.ImageIconFactory;
import it.spaghettisource.navaltrader.ui.SpringLayoutUtilities;
import it.spaghettisource.navaltrader.ui.component.CurrencyTextField;
import it.spaghettisource.navaltrader.ui.component.PercentageTextField;
import it.spaghettisource.navaltrader.ui.component.TableCellCurrentyRenderer;
import it.spaghettisource.navaltrader.ui.component.TableCellPercentageRenderer;
import it.spaghettisource.navaltrader.ui.event.Event;
import it.spaghettisource.navaltrader.ui.event.EventType;
import it.spaghettisource.navaltrader.ui.model.FinancialTableRow;
import it.spaghettisource.navaltrader.ui.model.LoanTableRow;

public class InternalFrameOffice extends InternalFrameAbstract  implements ActionListener {

	static Log log = LogFactory.getLog(InternalFrameOffice.class.getName());

	private final static String TAB_FINANCIAL_STATUS = "financial status";
	private final static String TAB_BANK = "bank";		

	private final static String ACTION_NEW_LOAN = "new loan";
	private final static String ACTION_REPAIR_LOAN = "repair loan";	


	//UI components
	private JTabbedPane tabbedPane;	

	//financial data tab
	private EventList<FinancialTableRow> listFinancialData;
	private CurrencyTextField netProfit;
	private JTextField companyRating;
	private CurrencyTextField budget;		

	
	//bank event tab
	private JTable loanTable;
	private EventList<LoanTableRow> listBankLoan;
	private PercentageTextField interest;		
	private CurrencyTextField maxLoanAmount;	
	private JSlider sliderNewLoanAmount;	
	private JSlider sliderAmountToRepair;		

	 

	public InternalFrameOffice(GameManager gameManager) {
		super(gameManager,"Office");
		setSize(500,450);   
		setFrameIcon(ImageIconFactory.getForFrame("/icon/desk.png"));

		initValuesFromModel();

		tabbedPane = new JTabbedPane();		

		tabbedPane.addTab(TAB_FINANCIAL_STATUS, ImageIconFactory.getForTab("/icon/pie-chart.png"),createFinancialStatusPanel());
		tabbedPane.addTab(TAB_BANK, ImageIconFactory.getForTab("/icon/bank.png"),createBankPanel());				

		getContentPane().add(tabbedPane);

	}

	private void initValuesFromModel() {

		Company company = gameData.getCompany();
		Finance finance = company.getCompanyFinance();
		Bank bank = gameData.getBank();		

		//financial tab

		listFinancialData = GlazedLists.threadSafeList(new BasicEventList<FinancialTableRow>());	
		listFinancialData.addAll(FinancialTableRow.mapData(finance)); 
		netProfit = new CurrencyTextField(finance.getNetProfit());
		companyRating = new JTextField(company.getRating());	
	
		budget = new CurrencyTextField(company.getBudget());

		//bank tab
		listBankLoan = GlazedLists.threadSafeList(new BasicEventList<LoanTableRow>());	
		listBankLoan.addAll(LoanTableRow.mapData(bank.getLoanList()));
		interest = new PercentageTextField(bank.getActualInterest(company));		
		maxLoanAmount = new CurrencyTextField(bank.getMaxAcceptedAmount(company));
		
		sliderNewLoanAmount = new JSlider(JSlider.HORIZONTAL,0, bank.getMaxAcceptedAmount(company).intValue(), 0);	

	}

	private Component createShipBrokerPanel() {
		JPanel panel = new JPanel();
		return panel;
	}


	private JPanel createFinancialStatusPanel(){
		JPanel panel = new JPanel(new BorderLayout());

		///////////////////////////		
		//create the table of finance
		JTable table;		
		String[] propertyNames = new String[] { "entry", "profit", "loss"};
		String[] columnLabels = new String[] { "entry", "profit", "loss"};
		TableFormat<FinancialTableRow> tf = GlazedLists.tableFormat(FinancialTableRow.class, propertyNames, columnLabels);
		table = new JTable(new EventTableModel<FinancialTableRow>(listFinancialData, tf));	

		table.getColumnModel().getColumn(1).setCellRenderer(TableCellCurrentyRenderer.getRenderer());
		table.getColumnModel().getColumn(2).setCellRenderer(TableCellCurrentyRenderer.getRenderer());		
		
		///////////////////////////		
		//create financial info
		JPanel financialPanel = new JPanel(new SpringLayout());		
		financialPanel.add(new Label("Net profit"));
		financialPanel.add(netProfit);
		financialPanel.add(new Label("company rating"));
		financialPanel.add(companyRating);
		financialPanel.add(new Label("budget"));
		financialPanel.add(budget);		

		SpringLayoutUtilities.makeGrid(financialPanel,3, 2,5, 5,5, 5);		

		//add all together
		panel.add(new JScrollPane(table), BorderLayout.CENTER);
		panel.add(financialPanel, BorderLayout.SOUTH);			

		return panel;
	}


	private Component createBankPanel() {
		JPanel panel = new JPanel(new BorderLayout());

		///////////////////////////
		//repair loan	
		JPanel repairLoanPanel = new JPanel(new SpringLayout());
		repairLoanPanel.setBorder(BorderFactory.createTitledBorder("repair loan"));		
		sliderAmountToRepair = new JSlider(JSlider.HORIZONTAL,0, 0, 0);			
		JFormattedTextField amountToRepair = new CurrencyTextField(0.0);

		sliderAmountToRepair.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				if (!source.getValueIsAdjusting()) {
					amountToRepair.setValue(source.getValue());
				}
			}
		});

		JButton amountToRepairButton = new JButton(ImageIconFactory.getForTab("/icon/money.png"));
		amountToRepairButton.setActionCommand(ACTION_REPAIR_LOAN);
		amountToRepairButton.addActionListener(this);

		repairLoanPanel.add(sliderAmountToRepair);		
		repairLoanPanel.add(amountToRepairButton);
		repairLoanPanel.add(new Label("repair loan"));		
		repairLoanPanel.add(amountToRepair);		
		SpringLayoutUtilities.makeCompactGrid(repairLoanPanel,2, 2,5, 5,5, 5);


		///////////////////////////
		//create the table of loans		
		JPanel loanPanel = new JPanel(new BorderLayout());
		loanPanel.setBorder(BorderFactory.createTitledBorder("loans open"));			
		String[] propertyNames = new String[] { "amount", "interest","dailyPayment"};
		String[] columnLabels = new String[] { "amount", "interest","daily instalment"};
		TableFormat<LoanTableRow> tf = GlazedLists.tableFormat(LoanTableRow.class, propertyNames, columnLabels);
		loanTable = new JTable(new EventTableModel<LoanTableRow>(listBankLoan, tf));	
		loanTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent event) {
				try{
					LoanTableRow data = listBankLoan.get(loanTable.convertRowIndexToModel(loanTable.getSelectedRow()));
					if(gameData.getCompany().getBudget() > data.getAmount()){
						sliderAmountToRepair.setMaximum( data.getAmount().intValue());						
					}else{
						sliderAmountToRepair.setMaximum(gameData.getCompany().getBudget().intValue());
					}
				}catch (Exception e) {}
			}
		});		
		loanTable.getColumnModel().getColumn(0).setCellRenderer(TableCellCurrentyRenderer.getRenderer());
		loanTable.getColumnModel().getColumn(1).setCellRenderer(TableCellPercentageRenderer.getRenderer());
		loanTable.getColumnModel().getColumn(2).setCellRenderer(TableCellCurrentyRenderer.getRenderer());		
			
		loanPanel.add(new JScrollPane(loanTable),BorderLayout.CENTER);

		//create the interest data
		JPanel interestPanel = new JPanel(new SpringLayout());
		interestPanel.add(new Label("Proposed interest"));
		interestPanel.add(interest);
		interestPanel.add(new Label("Max loan amount"));
		interestPanel.add(maxLoanAmount);				
		SpringLayoutUtilities.makeGrid(interestPanel,2, 2,5, 5,5, 5);	
		loanPanel.add(interestPanel,BorderLayout.SOUTH);

		///////////////////////////
		//prepare new load panel	
		JPanel newLoanPanel = new JPanel(new SpringLayout());
		newLoanPanel.setBorder(BorderFactory.createTitledBorder("request new loan"));
		JFormattedTextField newLoanAmount = new CurrencyTextField(0.0);

		sliderNewLoanAmount.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				if (!source.getValueIsAdjusting()) {
					newLoanAmount.setValue( source.getValue());
				}
			}
		});

		JButton newLoanButton = new JButton(ImageIconFactory.getForTab("/icon/money.png"));
		newLoanButton.setActionCommand(ACTION_NEW_LOAN);
		newLoanButton.addActionListener(this);

		newLoanPanel.add(sliderNewLoanAmount);		
		newLoanPanel.add(newLoanButton);
		newLoanPanel.add(new Label("requested amount"));		
		newLoanPanel.add(newLoanAmount);		
		SpringLayoutUtilities.makeCompactGrid(newLoanPanel,2, 2,5, 5,5, 5);


		//add all together		
		panel.add(loanPanel, BorderLayout.CENTER);
		panel.add(newLoanPanel, BorderLayout.NORTH);		
		panel.add(repairLoanPanel, BorderLayout.SOUTH);			


		return panel;
	}	


	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		if(ACTION_NEW_LOAN.equals(command)){
			if(sliderNewLoanAmount.getValue()>0 ){
				gameData.getBank().createNewLoad(sliderNewLoanAmount.getValue() , gameData.getCompany());				
			}
		}else if(ACTION_REPAIR_LOAN.equals(command)){
			if(sliderAmountToRepair.getValue()>0 ){
				try{
					LoanTableRow data = listBankLoan.get(loanTable.convertRowIndexToModel(loanTable.getSelectedRow()));
					gameData.getBank().repairLoad(data.getId(), sliderAmountToRepair.getValue(), gameData.getCompany());	
					sliderAmountToRepair.setMaximum(0);					
				}catch (Exception e) {}
			}
		}
		

	}


	public void eventReceived(Event event) {

		if(event.getEventType().equals(EventType.FINANCIAL_EVENT)){
			Finance finance = gameData.getCompany().getCompanyFinance();
			netProfit.setValue(finance.getNetProfit());
			listFinancialData.clear();	
			listFinancialData.addAll(FinancialTableRow.mapData(finance)); 
		}else if(event.getEventType().equals(EventType.RATING_EVENT)){
			companyRating.setText(gameData.getCompany().getRating());
		}else if(event.getEventType().equals(EventType.BUDGET_EVENT)){
			budget.setValue(gameData.getCompany().getBudget());
		}else if(event.getEventType().equals(EventType.LOAN_EVENT)){
			listBankLoan.clear();
			listBankLoan.addAll(LoanTableRow.mapData(gameData.getBank().getLoanList()));
			maxLoanAmount.setValue(gameData.getBank().getMaxAcceptedAmount(gameData.getCompany()));
			sliderNewLoanAmount.setMaximum(gameData.getBank().getMaxAcceptedAmount(gameData.getCompany()).intValue());
		}else if(event.getEventType().equals(EventType.BANK_CHANGE_EVENT)){
			maxLoanAmount.setValue(gameData.getBank().getMaxAcceptedAmount(gameData.getCompany()));
			sliderNewLoanAmount.setMaximum(gameData.getBank().getMaxAcceptedAmount(gameData.getCompany()).intValue());			
		}				


	}

	public EventType[] getEventsOfInterest() {
		return new EventType[]{EventType.FINANCIAL_EVENT,EventType.RATING_EVENT,EventType.BUDGET_EVENT,EventType.LOAN_EVENT,EventType.BANK_CHANGE_EVENT};
	}





}
