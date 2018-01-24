package it.spaghettisource.navaltrader.ui.frame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.GameManager;
import it.spaghettisource.navaltrader.game.loop.LoopManager;
import it.spaghettisource.navaltrader.game.model.GameTime;
import it.spaghettisource.navaltrader.ui.ImageIconFactory;
import it.spaghettisource.navaltrader.ui.MainDesktopPane;
import it.spaghettisource.navaltrader.ui.event.Event;
import it.spaghettisource.navaltrader.ui.event.EventType;

public class InternalFrameTimeSimulation extends InternalFrameAbstract  implements ActionListener {

	static Log log = LogFactory.getLog(InternalFrameTimeSimulation.class.getName());

	private int refreshTime = 500;
	
	private GameTime gameTime;
	private LoopManager loopManager;
	
	private TimeRefreshThread refreshThread;
	private JLabel date;	
	private JLabel simulationSpeed;		
	
	
	
	private final static String ACTION_PAUSE = "pause";
	private final static String ACTION_PLAY = "play";	
	private final static String ACTION_FORWARD = "forward";	
	private final static String ACTION_REWIND = "rewind";		
	



	public InternalFrameTimeSimulation(MainDesktopPane parentDesktopPane, GameManager gameManager) {
		super(parentDesktopPane, gameManager, "simulation time");
		setSize(270,120);   
		this.gameTime = gameManager.getGameData().getTime();
		this.loopManager = gameManager.getLoopManager();
		
		setFrameIcon(ImageIconFactory.getForFrame("/icon/clock.png"));
		getContentPane().add(panelTimerController());
		
		refreshThread = new TimeRefreshThread();
		Thread thread = new Thread(refreshThread);
		thread.start();			
	}

	public void internalFrameClosed(InternalFrameEvent arg0) {
		refreshThread.stopThread();
	}
	

	private Component panelTimerController() {
		JPanel mainPanel = new JPanel(new BorderLayout()); 
		
		
		//////////////////////
		// clock control
		JPanel clockControlPanel = new JPanel(new FlowLayout());
		
		JButton play = new JButton(ImageIconFactory.getForTab("/icon/play-button.png"));
		play.setActionCommand(ACTION_PLAY);
		play.addActionListener(this);
		
		JButton pause = new JButton(ImageIconFactory.getForTab("/icon/pause.png"));
		pause.setActionCommand(ACTION_PAUSE);
		pause.addActionListener(this);		
		
		JButton forward = new JButton(ImageIconFactory.getForTab("/icon/fast-forward.png"));
		forward.setActionCommand(ACTION_FORWARD);
		forward.addActionListener(this);
		
		JButton rewind = new JButton(ImageIconFactory.getForTab("/icon/rewind.png"));
		rewind.setActionCommand(ACTION_REWIND);
		rewind.addActionListener(this);		
	
		clockControlPanel.add(rewind);
		clockControlPanel.add(pause);		
		clockControlPanel.add(play);
		clockControlPanel.add(forward);

		//////////////////////
		// date info 
		JPanel clockTimePanel = new JPanel(new FlowLayout());		
		date = new JLabel("date: "+gameTime.getDate());
		simulationSpeed = new JLabel("simulation speed: "+loopManager.getMultiplicator());		
		clockTimePanel.add(date);
		clockTimePanel.add(simulationSpeed);		

	
		
		//all togheter
		mainPanel.add(clockControlPanel,BorderLayout.NORTH);
		mainPanel.add(clockTimePanel,BorderLayout.SOUTH);		
		
		return mainPanel;	
	}

	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		if(ACTION_PAUSE.equals(command)) {
			loopManager.pause(true);
		}else if(ACTION_PLAY.equals(command)) {
			loopManager.pause(false);			
		}else if(ACTION_FORWARD.equals(command)) {
			loopManager.goFast();
		}else if(ACTION_REWIND.equals(command)) {
			loopManager.goSlow();			
		}
	}

	public void updateTime(String time) {
		date.setText(time);
		simulationSpeed.setText("simulation speed: x"+loopManager.getMultiplicator());
	}
	
	@Override
	public void eventReceived(Event event) {	
	}

	@Override
	public EventType[] getEventsOfInterest() {
		return new EventType[]{};
	}
	
	
	private class TimeRefreshThread implements Runnable {

		private boolean shutdown;

		public TimeRefreshThread() {
			shutdown = false;
		}

		public void stopThread(){
			shutdown = true;
		}
		
		@Override
		public void run() {
			while (!shutdown) {

				updateTime("date: "+gameTime.getDate());
				try {
					Thread.sleep(refreshTime);
				} catch (InterruptedException e) {
					log.error(e);
				}
			}
			log.debug("TimeRefreshThread thread shutdown");
		}
		
	}





}