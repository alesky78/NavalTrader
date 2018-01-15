package it.spaghettisource.navaltrader;

import javax.swing.JFrame;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.GameManager;
import it.spaghettisource.navaltrader.ui.MainFrame;
import it.spaghettisource.navaltrader.ui.event.EventManager;
import it.spaghettisource.navaltrader.ui.event.InboundEventQueue;

public class Application {

	static Log log = LogFactory.getLog(Application.class.getName());


	public static void main(String[] args){

		//initialize game structure
		EventManager eventManager = new EventManager();
		InboundEventQueue eventQueue = new InboundEventQueue(eventManager);		
		GameManager gameManager = new GameManager();

		//Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);   
		
		//Schedule a job for the event-dispatching thread:
		//creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainFrame(gameManager,eventQueue,eventManager);
			}
		});

	}




}
