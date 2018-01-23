package it.spaghettisource.navaltrader.ui.frame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.GameManager;
import it.spaghettisource.navaltrader.game.model.GameTime;
import it.spaghettisource.navaltrader.ui.ImageIconFactory;
import it.spaghettisource.navaltrader.ui.MainDesktopPane;

public class InternalFrameTimeSimulation extends JInternalFrame  implements ActionListener {

	static Log log = LogFactory.getLog(InternalFrameTimeSimulation.class.getName());

	private MainDesktopPane parentDesktopPane;
	private GameManager gameManager;
	
	
	private final static String ACTION_PAUSE = "pause";
	private final static String ACTION_PLAY = "play";	
	private final static String ACTION_FORWARD = "forward";	
	private final static String ACTION_REWIND = "rewind";		
	


	public InternalFrameTimeSimulation(MainDesktopPane parentDesktopPane, GameManager gameManager) {
		super("simulation time", true, true, true, true);
		setSize(270,120);   
		this.parentDesktopPane = parentDesktopPane;
		this.gameManager = gameManager;
		
		setFrameIcon(ImageIconFactory.getForFrame("/icon/clock.png"));
		getContentPane().add(panelTimerController());
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
		GameTime time = gameManager.getGameData().getTime();
		JPanel clockTimePanel = new JPanel(new FlowLayout());		
		Label date = new Label(time.getDate());
		
		clockTimePanel.add(date);
		
		//all togheter
		mainPanel.add(clockControlPanel,BorderLayout.NORTH);
		mainPanel.add(clockTimePanel,BorderLayout.SOUTH);		
		
		return mainPanel;	
	}



	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		

	}



}
