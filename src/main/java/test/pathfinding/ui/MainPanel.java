package test.pathfinding.ui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import test.pathfinding.Grid;

public class MainPanel extends JPanel {

	private GridPanel gridPanel;
	private Grid grid;
	
	private String ACTION_RESET = "ACTION_RESET";
	private String ACTION_START = "ACTION_START";	
	
	
	public MainPanel(int size) {
		super();
		setSize(size, size);
		setLayout(new BorderLayout());
		
		//initialize the grid
		grid = new Grid(size/10);
		
		//intialize the panel to drow the grid
		gridPanel = new GridPanel(grid,size);
		
		
		//control and actions
		JPanel controlPanel = new JPanel();
		
		JButton buttonResetGrid = new JButton("reset grid");
		buttonResetGrid.setActionCommand(ACTION_RESET);
		JButton buttonStart = new JButton("start");		
		buttonStart.setActionCommand(ACTION_START);		
		
		controlPanel.add(buttonResetGrid);
		controlPanel.add(buttonStart);		
		
		
		
		//put all togheter
		add(controlPanel, BorderLayout.SOUTH);		
		add(gridPanel, BorderLayout.CENTER);
	}

	
	
	
	
}
