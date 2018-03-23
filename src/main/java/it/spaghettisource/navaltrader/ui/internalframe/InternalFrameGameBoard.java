package it.spaghettisource.navaltrader.ui.internalframe;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;

import javax.swing.event.InternalFrameEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.GameManager;
import it.spaghettisource.navaltrader.ui.ImageIconFactory;
import it.spaghettisource.navaltrader.ui.component.PanelGameBoard;
import it.spaghettisource.navaltrader.ui.event.Event;
import it.spaghettisource.navaltrader.ui.event.EventType;
import it.spaghettisource.navaltrader.ui.frame.GameBoardDesktopPane;

public class InternalFrameGameBoard extends InternalFrameAbstract {

	static Log log = LogFactory.getLog(InternalFrameGameBoard.class.getName());

	private PanelGameBoard panel;


	public InternalFrameGameBoard(GameBoardDesktopPane parentDesktopPane, GameManager gameManager) {
		super(parentDesktopPane, gameManager, "navigation", false, false, false, false);

		try {
			setSize(850,850);   
			setFrameIcon(ImageIconFactory.getForFrame("/icon/globe.png"));

			panel = new PanelGameBoard(parentDesktopPane,gameManager, 600);
			panel.setBackground(Color.BLACK);
			panel.setBorder(null);

			getContentPane().add(panel);
			panel.start();

		} catch (Exception e) {
			log.error("Erro creating InternalFrameMapNavigation",e);
		}

	}

	public void internalFrameClosed(InternalFrameEvent arg0) {
		panel.stop();		
		super.internalFrameClosed(arg0);
	}	

	public void internalFrameActivated(InternalFrameEvent event) {
		moveToBack();
	}
	
	public void eventReceived(Event event) {

	}

	public EventType[] getEventsOfInterest() {
		return null;
	}

}
