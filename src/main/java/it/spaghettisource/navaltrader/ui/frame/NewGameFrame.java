package it.spaghettisource.navaltrader.ui.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import it.spaghettisource.navaltrader.game.GameManager;
import it.spaghettisource.navaltrader.game.GameSetting;
import it.spaghettisource.navaltrader.ui.FrameUtils;
import it.spaghettisource.navaltrader.ui.component.ButtonColor;

public class NewGameFrame extends JFrame implements ActionListener {

	private static final String ACTION_NEW_GAME = "new game";
	private static final String ACTION_CLOSE = "close";	
	
	private JPanel contentPane;
	private JComboBox difficultyCombo;
	private JComboBox tutorialCombo;
	private JTextPane difficultyDesc;


	/**
	 * Create the frame.
	 */
	public NewGameFrame() {
		
		setBounds(100, 100, 577, 610);
		setUndecorated(true);
		setAlwaysOnTop(true);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel header = new JPanel();
		header.setBackground(new Color(255, 200, 0));
		header.setBounds(12, 13, 553, 34);
		contentPane.add(header);
		
		JLabel lblNewGame = new JLabel("NEW GAME");
		lblNewGame.setForeground(Color.WHITE);
		lblNewGame.setFont(new Font("Arial", Font.BOLD, 20));
		header.add(lblNewGame);
		
		ButtonColor startButton = new ButtonColor(Color.ORANGE);
		startButton.setActionCommand(ACTION_NEW_GAME);
		startButton.addActionListener(this);
		startButton.setText("Start");
		startButton.setFocusPainted(false);
		startButton.setFont(new Font("Arial", Font.BOLD, 20));
		startButton.setForeground(Color.WHITE);
		startButton.setBackground(Color.WHITE);
		startButton.setBounds(350, 563, 215, 34);
		contentPane.add(startButton);
		
		ButtonColor closeButton = new ButtonColor(Color.ORANGE);
		closeButton.setActionCommand(ACTION_CLOSE);		
		closeButton.addActionListener(this);		
		closeButton.setText("Close");
		closeButton.setForeground(Color.WHITE);
		closeButton.setFont(new Font("Arial", Font.BOLD, 20));
		closeButton.setFocusPainted(false);
		closeButton.setBackground(Color.WHITE);
		closeButton.setBounds(9, 563, 215, 34);
		contentPane.add(closeButton);
		
		JLabel lblStartPort = new JLabel("start port");
		lblStartPort.setFont(new Font("Arial", Font.BOLD, 20));
		lblStartPort.setBounds(40, 100, 184, 34);
		contentPane.add(lblStartPort);
		
		JLabel lblDifficulties = new JLabel("activate tutorial");
		lblDifficulties.setFont(new Font("Arial", Font.BOLD, 20));
		lblDifficulties.setBounds(40, 180, 184, 34);
		contentPane.add(lblDifficulties);
		
		JLabel lblActivateTutorial = new JLabel("select difficulty");
		lblActivateTutorial.setFont(new Font("Arial", Font.BOLD, 20));
		lblActivateTutorial.setBounds(40, 270, 184, 34);
		contentPane.add(lblActivateTutorial);
		
		JComboBox startPortCombo = new JComboBox();
		startPortCombo.setRequestFocusEnabled(false);
		startPortCombo.setFont(new Font("Arial", Font.BOLD, 20));
		startPortCombo.setBounds(332, 100, 184, 34);
		contentPane.add(startPortCombo);
		
		tutorialCombo = new JComboBox(new Boolean[]{Boolean.TRUE,Boolean.FALSE});
		tutorialCombo.setRequestFocusEnabled(false);
		tutorialCombo.setFont(new Font("Arial", Font.BOLD, 20));
		tutorialCombo.setBounds(332, 180, 184, 34);
		contentPane.add(tutorialCombo);
		
		difficultyCombo = new JComboBox(GameSetting.getDifficulties());
		difficultyCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String choose  = (String)difficultyCombo.getSelectedItem();
				if(choose.equals(GameSetting.DIFF_EASY)){
					difficultyDesc.setText(GameSetting.DIFF_EASY_TXT);
				}else if(choose.equals(GameSetting.DIFF_MEDIUM)){
					difficultyDesc.setText(GameSetting.DIFF_MEDIUM_TXT);					
				}else if(choose.equals(GameSetting.DIFF_HARD)){
					difficultyDesc.setText(GameSetting.DIFF_HARD_TXT);					
				}
			}
		});
		difficultyCombo.setRequestFocusEnabled(false);
		difficultyCombo.setFont(new Font("Arial", Font.BOLD, 20));
		difficultyCombo.setBounds(332, 270, 184, 34);
		contentPane.add(difficultyCombo);

		
		difficultyDesc = new JTextPane();
		difficultyDesc.setText(GameSetting.DIFF_EASY_TXT);
		difficultyDesc.setEditable(false);
		difficultyDesc.setFont(new Font("Arial", Font.BOLD, 20));
		difficultyDesc.setBounds(40, 324, 476, 173);
		contentPane.add(difficultyDesc);
		
		FrameUtils.centreWindow(this);
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
					GameSetting setting = new GameSetting((boolean) tutorialCombo.getSelectedItem(), (String) difficultyCombo.getSelectedItem());
					gameManager.startNewGame(setting,"company name","Gioia Tauro");			
	
					new GameBoardFrame(gameManager);
				}
			});
			
		}else if(ACTION_CLOSE.equals(action)) {
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
		
	}

}
