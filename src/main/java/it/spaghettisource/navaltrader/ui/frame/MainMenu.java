package it.spaghettisource.navaltrader.ui.frame;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import it.spaghettisource.navaltrader.game.GameManager;
import it.spaghettisource.navaltrader.ui.component.PanelDrawBackground;
import it.spaghettisource.navaltrader.ui.event.InboundEventQueue;
import java.awt.Color;

public class MainMenu extends JFrame implements ActionListener {

	private static final String ACTION_NEW_GAME = "new game";
	private static final String ACTION_LOAD_GAME = "load game";	
	
		public MainMenu() {
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setUndecorated(true);
		
		JPanel panel = new PanelDrawBackground("/images/startBakground.png");
		getContentPane().add(panel);
		
		panel.setLayout(null);
		
		JButton newGameButton = new JButton("New game");
		newGameButton.setForeground(Color.WHITE);
		newGameButton.setActionCommand(ACTION_NEW_GAME);		
		newGameButton.addActionListener(this);
		newGameButton.setFont(new Font("SansSerif", Font.BOLD, 30));
		newGameButton.setBounds(1389, 291, 200, 36);		
		newGameButton.setBorder(BorderFactory.createEmptyBorder());
		newGameButton.setContentAreaFilled(false);				
		newGameButton.setFocusPainted(false);		
		panel.add(newGameButton);
		
		JButton loadGameButton = new JButton("Load game");
		loadGameButton.setForeground(Color.WHITE);
		loadGameButton.setActionCommand(ACTION_LOAD_GAME);		
		loadGameButton.addActionListener(this);		
		loadGameButton.setFont(new Font("SansSerif", Font.BOLD, 30));
		loadGameButton.setBounds(1389, 401, 200, 36);
		loadGameButton.setBorder(BorderFactory.createEmptyBorder());
		loadGameButton.setContentAreaFilled(false);			
		loadGameButton.setFocusPainted(false);			
		panel.add(loadGameButton);
		
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();
		
		if(ACTION_NEW_GAME.equals(action)) {
			
			//close the main menu frame
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
			
			//Schedule a job for the event-dispatching thread:
			//creating and showing this application's GUI.
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					
					//initialize game structure
					GameManager gameManager = new GameManager();	
					gameManager.startNewGame("test");			
	
					new MainFrame(gameManager);
				}
			});
			
			
		}

		
	}
	
	
	
	
}
