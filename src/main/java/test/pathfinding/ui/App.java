package test.pathfinding.ui;

import javax.swing.JFrame;

import test.ui.DrawImmageFromTxt;

public class App {

	static int INITIAL_SIZE = 800;
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
	
	
	private static void createAndShowGUI() {

		//Create and set up the window.
		JFrame frame = new JFrame("pathFinding");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		frame.setContentPane(new MainPanel(INITIAL_SIZE));
		frame.setSize(INITIAL_SIZE, INITIAL_SIZE);
		
		frame.setLocationByPlatform(true);
		//Display the window.
		frame.setVisible(true);
	}
	
	
}
