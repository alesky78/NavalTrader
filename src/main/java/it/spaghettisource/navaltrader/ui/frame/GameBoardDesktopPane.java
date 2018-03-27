package it.spaghettisource.navaltrader.ui.frame;

import java.awt.Component;
import java.awt.Dimension;
import java.beans.PropertyVetoException;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.GameManager;
import it.spaghettisource.navaltrader.game.model.Port;
import it.spaghettisource.navaltrader.game.model.ProfitabilityRoute;
import it.spaghettisource.navaltrader.game.model.Ship;
import it.spaghettisource.navaltrader.ui.ImageIconFactory;
import it.spaghettisource.navaltrader.ui.event.Event;
import it.spaghettisource.navaltrader.ui.event.EventListener;
import it.spaghettisource.navaltrader.ui.event.EventPublisher;
import it.spaghettisource.navaltrader.ui.event.EventType;
import it.spaghettisource.navaltrader.ui.internalframe.InternalFrameContractDelivered;
import it.spaghettisource.navaltrader.ui.internalframe.InternalFrameGameBoard;
import it.spaghettisource.navaltrader.ui.internalframe.InternalFramePort;

public class GameBoardDesktopPane extends JDesktopPane implements EventListener     {

	static Log log = LogFactory.getLog(GameBoardDesktopPane.class.getName());	

	private GameManager gameManager;
	private InternalFrameGameBoard gameBoardFrame;

	public GameBoardDesktopPane(GameManager gameManager) {
		super();
		this.gameManager = gameManager;
		EventPublisher.getInstance().register(this);		

		//Make dragging a little faster but perhaps uglier.
		//setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);	 
		
		createInternalFrameGameBoard();
	}


	public void createInternalFrameGameBoard() {
		gameBoardFrame = new InternalFrameGameBoard(this, gameManager);
		add(gameBoardFrame, 100);			
		try {
			gameBoardFrame.setMaximum(true);
			gameBoardFrame.setSelected(true);					
		} catch (PropertyVetoException e1) {
			e1.printStackTrace();
		}														
		gameBoardFrame.setVisible(true);
		gameBoardFrame.moveToBack();

		try {
			gameBoardFrame.setSelected(true);
		} catch (java.beans.PropertyVetoException e) {}
	}	 


	//TODO ship should not be pass ship variable there are multiple ships also how to manage from the board class?
	//from the list is ok it works because i select a specific ship
	public void createInternalFramePort(GameManager gameManager,Port port,Ship ship){
			JInternalFrame frame =  getInternalFrameByName(port.getName());
			try {
				if(frame!=null) {
					if(frame.isIcon()) {
						frame.setIcon(false);
					}
					frame.moveToFront(); 
					frame.setSelected(true);			
				}else  {
					//open frame for specific ship
					frame = new InternalFramePort(this,gameManager,port,ship);
					add(frame);
					frame.setVisible(true);
					frame.setMaximum(true);														
					frame.setSelected(true);
				}
			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}			
	}


	public Component add(Component comp) {
		Component newC =  super.add(comp);
		gameBoardFrame.moveToBack();
		return newC;
	}


	public JInternalFrame getInternalFrameByName(String name){
		JInternalFrame[] frames =  getAllFrames();
		for (JInternalFrame frame : frames) {
			if(frame.getTitle().equals(name)) {
				return frame;
			}
		}

		return null;
	}

	public void centerFrameInTheDesktopPane(JInternalFrame frame){
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
			log.debug("contracts completed open new panel for contract delivered");
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
