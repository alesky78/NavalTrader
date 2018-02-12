package it.spaghettisource.navaltrader.ui.frame;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.InternalFrameEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.GameManager;
import it.spaghettisource.navaltrader.game.loop.LoopManager;
import it.spaghettisource.navaltrader.game.model.ProfitabilityRoute;
import it.spaghettisource.navaltrader.ui.ImageIconFactory;
import it.spaghettisource.navaltrader.ui.MainDesktopPane;
import it.spaghettisource.navaltrader.ui.SpringLayoutUtilities;
import it.spaghettisource.navaltrader.ui.component.TextFieldCurrency;
import it.spaghettisource.navaltrader.ui.component.TextFieldDouble;
import it.spaghettisource.navaltrader.ui.component.TextFieldInteger;
import it.spaghettisource.navaltrader.ui.event.Event;
import it.spaghettisource.navaltrader.ui.event.EventType;

public class InternalFrameContractDelivered extends InternalFrameAbstract {

	static Log log = LogFactory.getLog(InternalFrameContractDelivered.class.getName());
	
	private LoopManager loopManager;
	private ProfitabilityRoute profitabilityRoute;
	
	
	public InternalFrameContractDelivered(MainDesktopPane parentDesktopPane, GameManager gameManager, ProfitabilityRoute profitabilityRoute) {
		super(parentDesktopPane, gameManager, "contract delivered", true, true, false, false);
		
		this.profitabilityRoute = profitabilityRoute;
		loopManager = gameManager.getLoopManager();
		loopManager.setPauseByGame(true);
		
		setSize(600,300);
		setFrameIcon(ImageIconFactory.getForFrame("/icon/container.png"));		
		parentDesktopPane.centerInTheDesktopPane(this);
			
		getContentPane().add(createContentPanel());
		
	}

	
	private JPanel createContentPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		
		
		//////////////////////////////////////////////		
		//create the panel of the economical results
		JPanel economicalResults = new JPanel(new SpringLayout());
		economicalResults.setBorder(BorderFactory.createTitledBorder("economical results"));

		economicalResults.add(new JLabel("ship"));
		economicalResults.add(new JTextField(profitabilityRoute.getShip().getName()));
		economicalResults.add(new JLabel("payement received"));
		economicalResults.add(new TextFieldCurrency(profitabilityRoute.getIncomeObtained()));
		
		economicalResults.add(new JLabel("tug charges"));
		economicalResults.add(new TextFieldCurrency(-profitabilityRoute.getTugCharges()));
		economicalResults.add(new JLabel("journey duration"));
		economicalResults.add(new TextFieldInteger(profitabilityRoute.getDaysOfNavigation()));
		
		economicalResults.add(new JLabel("fuel consumption"));
		economicalResults.add(new TextFieldDouble(profitabilityRoute.getFuelConsumed()));
		economicalResults.add(new JLabel("correspond to fuel price"));		
		economicalResults.add(new TextFieldCurrency(-profitabilityRoute.getFuelConsumedPrice()));
		
		economicalResults.add(new JLabel("hull damage"));
		economicalResults.add(new TextFieldInteger(profitabilityRoute.getHullDamaged()));
		economicalResults.add(new JLabel("correspond to reparation price"));		
		economicalResults.add(new TextFieldCurrency(-profitabilityRoute.getHullDamagedPrice()));		
		
		economicalResults.add(new JLabel(""));		
		economicalResults.add(new JLabel(""));
		economicalResults.add(new JLabel("final balance of the journey"));		
		economicalResults.add(new TextFieldCurrency(profitabilityRoute.getFinalBalance()));		
		
		SpringLayoutUtilities.makeCompactGrid(economicalResults,5, 4, 5, 5, 5, 5);	

		//add all together
		panel.add(economicalResults, BorderLayout.NORTH);
		
		return panel;
	}


	public void internalFrameClosed(InternalFrameEvent event) {
		super.internalFrameClosed(event);
		loopManager.setPauseByGame(false);
		log.info("internalFrameClosed");		
	}		
	
	public void internalFrameDeactivated(InternalFrameEvent event) {
		super.internalFrameDeactivated(event);
		this.doDefaultCloseAction();			
		log.info("internalFrameDeactivated");
	}	
	
	
	@Override
	public void eventReceived(Event event) {
	}

	@Override
	public EventType[] getEventsOfInterest() {
		return null;
	}

}
