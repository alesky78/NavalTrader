package it.spaghettisource.navaltrader.ui;

import java.awt.Dimension;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import it.spaghettisource.navaltrader.game.GameManager;
import it.spaghettisource.navaltrader.game.model.ProfitabilityRoute;
import it.spaghettisource.navaltrader.ui.event.Event;
import it.spaghettisource.navaltrader.ui.event.EventListener;
import it.spaghettisource.navaltrader.ui.event.EventPublisher;
import it.spaghettisource.navaltrader.ui.event.EventType;
import it.spaghettisource.navaltrader.ui.frame.InternalFrameContractDelivered;

public class MainDesktopPane extends JDesktopPane implements EventListener  {

	
	private GameManager gameManager;
	
	public MainDesktopPane(GameManager gameManager) {
		super();
		this.gameManager = gameManager;
		EventPublisher.getInstance().register(this);		
	}


	public void centerInTheDesktopPane(JInternalFrame frame){
		Dimension desktopSize = getSize();
	    Dimension jInternalFrameSize = frame.getSize();
	    int width = (desktopSize.width - jInternalFrameSize.width) / 2;
	    int height = (desktopSize.height - jInternalFrameSize.height) / 2;
	    frame.setLocation(width, height);				
	}
	
	
	public void showInfoMessageDialog(String message) {
		JOptionPane.showInternalMessageDialog(this, message,"info",JOptionPane.INFORMATION_MESSAGE,  ImageIconFactory.getForTab("/icon/info.png"));
	}

	public void showErrorMessageDialog(String message) {
		JOptionPane.showInternalMessageDialog(this, message,"error",JOptionPane.ERROR_MESSAGE,  ImageIconFactory.getForTab("/icon/error.png"));
	}	
	
	public void showWarningMessageDialog(String message) {	
		JOptionPane.showInternalMessageDialog(this, message,"warning",JOptionPane.WARNING_MESSAGE,  ImageIconFactory.getForTab("/icon/warning.png"));
	}		
	
	public void showOKMessageDialog(String message) {	
		JOptionPane.showInternalMessageDialog(this, message,"ok",JOptionPane.INFORMATION_MESSAGE, ImageIconFactory.getForTab("/icon/success.png"));
	}

	
	@Override
	public void eventReceived(Event event) {
		EventType eventType = event.getEventType(); 
		if(eventType.equals(EventType.CONTRACT_COMPLETED_EVENT)){
			InternalFrameContractDelivered frame = new InternalFrameContractDelivered(this, gameManager,(ProfitabilityRoute) event.getSource());
			add(frame);
			frame.moveToFront(); 	
			frame.setVisible(true);

		}
	}

	@Override
	public EventType[] getEventsOfInterest() {
		return new EventType[]{EventType.CONTRACT_COMPLETED_EVENT};
	}	
	
	
}
