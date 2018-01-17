package it.spaghettisource.navaltrader.ui.office;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Label;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
import it.spaghettisource.navaltrader.ui.InternalFrameAbstract;
import it.spaghettisource.navaltrader.ui.SpringLayoutUtilities;
import it.spaghettisource.navaltrader.ui.event.Event;
import it.spaghettisource.navaltrader.ui.event.EventPublisher;
import it.spaghettisource.navaltrader.ui.event.EventType;
import it.spaghettisource.navaltrader.ui.model.FinancialTableRow;
import it.spaghettisource.navaltrader.ui.model.LoanTableRow;
import test.ButtonColumn;

public class InternalFrameOffice extends InternalFrameAbstract  implements ChangeListener {

	static Log log = LogFactory.getLog(InternalFrameOffice.class.getName());

	private final static String TAB_FINANCIAL_STATUS = "financial status";
	private final static String TAB_BANK = "bank";
	private final static String TAB_SHIP_BROKER = "ship broker";		

	//UI components
	private JTabbedPane tabbedPane;

	//financial data tab
	private EventList<FinancialTableRow> tableFinancialData;
	private JTextField netProfit;
	private JTextField companyRating;
	private JTextField budget;	

	//bank event tab
	private EventList<LoanTableRow> tableBankLoan;
	private JTextField interest;		
	private JTextField maxLoanAmount;	


	public InternalFrameOffice(GameManager gameManager) {
		super(gameManager,"Office");
		setSize(500,350);   
		setFrameIcon(ImageIconFactory.getForFrame("/icon/desk.png"));

		initValuesFromModel();

		tabbedPane = new JTabbedPane();		
		tabbedPane.addChangeListener(this);

		tabbedPane.addTab(TAB_FINANCIAL_STATUS, ImageIconFactory.getForTab("/icon/coins.png"),createFinancialStatusPanel());
		tabbedPane.addTab(TAB_BANK, ImageIconFactory.getForTab("/icon/bank.png"),createBankPanel());
		tabbedPane.addTab(TAB_SHIP_BROKER, ImageIconFactory.getForTab("/icon/justice.png"),createShipBrokerPanel());				

		getContentPane().add(tabbedPane);

	}

	private void initValuesFromModel() {

		Company company = gameData.getCompany();
		Finance finance = company.getCompanyFinance();
		Bank bank = gameData.getBank();		

		//financial tab

		tableFinancialData = GlazedLists.threadSafeList(new BasicEventList<FinancialTableRow>());	
		tableFinancialData.addAll(FinancialTableRow.mapData(finance)); 
		netProfit = new JTextField(Integer.toString(finance.getNetProfit()));
		companyRating = new JTextField(company.getRating());	
		budget = new JTextField(Integer.toString(company.getBudget()));

		//bank tab
		tableBankLoan = GlazedLists.threadSafeList(new BasicEventList<LoanTableRow>());	
		tableBankLoan.addAll(LoanTableRow.mapData(bank.getLoanList()));
		interest = new JTextField(Double.toString(bank.getActualInterest(company)));
		maxLoanAmount = new JTextField(Integer.toString(bank.getMaxAcceptedAmount(company)));

	}

	private Component createShipBrokerPanel() {
		JPanel panel = new JPanel();
		return panel;
	}


	private JPanel createFinancialStatusPanel(){
		JPanel panel = new JPanel(new BorderLayout());

		//create the table of finance
		JTable table;		
		String[] propertyNames = new String[] { "entry", "profit", "loss"};
		String[] columnLabels = new String[] { "entry", "profit", "loss"};
		TableFormat<FinancialTableRow> tf = GlazedLists.tableFormat(FinancialTableRow.class, propertyNames, columnLabels);
		table = new JTable(new EventTableModel<FinancialTableRow>(tableFinancialData, tf));	
		//ButtonColumn test = new ButtonColumn(table, null, 3);

		//create financial info
		JPanel financialPanel = new JPanel(new SpringLayout());		
		financialPanel.add(new Label("Net profit"));
		financialPanel.add(netProfit);
		financialPanel.add(new Label("company rating"));
		financialPanel.add(companyRating);
		financialPanel.add(new Label("budget"));
		financialPanel.add(budget);		

		SpringLayoutUtilities.makeGrid(financialPanel,
				3, 2, //rows, cols
				5, 5, //initialX, initialY
				5, 5);//xPad, yPad		

		//add all together
		panel.add(new JScrollPane(table), BorderLayout.CENTER);
		panel.add(financialPanel, BorderLayout.SOUTH);			

		return panel;
	}


	private Component createBankPanel() {
		JPanel panel = new JPanel(new BorderLayout());

		JPanel loanPanel = new JPanel(new BorderLayout());
		loanPanel.setBorder(BorderFactory.createTitledBorder("loans open"));

		//create the table of loans		
		JTable table;		
		String[] propertyNames = new String[] { "amount", "interest"};
		String[] columnLabels = new String[] { "amount", "interest"};
		TableFormat<LoanTableRow> tf = GlazedLists.tableFormat(LoanTableRow.class, propertyNames, columnLabels);
		table = new JTable(new EventTableModel<LoanTableRow>(tableBankLoan, tf));	
		loanPanel.add(new JScrollPane(table),BorderLayout.CENTER);

		//create the interest data
		JPanel interestPanel = new JPanel(new SpringLayout());
		interestPanel.add(new Label("Proposed interest"));
		interestPanel.add(interest);
		interestPanel.add(new Label("Max loan amount"));
		interestPanel.add(maxLoanAmount);				
		SpringLayoutUtilities.makeGrid(interestPanel,2, 2,5, 5,5, 5);	
		loanPanel.add(interestPanel,BorderLayout.SOUTH);

		//add all together
		panel.add(loanPanel, BorderLayout.CENTER);

		return panel;
	}	


	public void stateChanged(ChangeEvent arg0) {
		// TODO what if tab change?

	}


	public void eventReceived(Event event) {

		if(event.getEventType().equals(EventType.FINANCIAL_EVENT)){
			Finance finance = gameData.getCompany().getCompanyFinance();
			netProfit.setText(Integer.toString(finance.getNetProfit()));
			tableFinancialData.clear();	
			tableFinancialData.addAll(FinancialTableRow.mapData(finance)); 
		}else if(event.getEventType().equals(EventType.RATING_EVENT)){
			companyRating.setText(gameData.getCompany().getRating());
		}else if(event.getEventType().equals(EventType.BUDGET_EVENT)){
			budget.setText(Integer.toString(gameData.getCompany().getBudget()));
		}else if(event.getEventType().equals(EventType.LOAN_EVENT)){
			tableBankLoan.clear();
			tableBankLoan.addAll(LoanTableRow.mapData(gameData.getBank().getLoanList()));
			maxLoanAmount.setText(Integer.toString(gameData.getBank().getMaxAcceptedAmount(gameData.getCompany())));
		}else if(event.getEventType().equals(EventType.BANK_CHANGE_EVENT)){
			maxLoanAmount.setText(Integer.toString(gameData.getBank().getMaxAcceptedAmount(gameData.getCompany())));
		}				


	}

	public EventType[] getEventsOfInterest() {
		return new EventType[]{EventType.FINANCIAL_EVENT,EventType.RATING_EVENT,EventType.BUDGET_EVENT,EventType.LOAN_EVENT,EventType.BANK_CHANGE_EVENT};
	}



}
