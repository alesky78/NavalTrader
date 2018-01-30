package test.pathfinding.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import test.pathfinding.Cell;
import test.pathfinding.Grid;
import test.pathfinding.algorithm.AStar;
import test.pathfinding.algorithm.BreadthFirstSearch;
import test.pathfinding.algorithm.PathFinding;

public class MainPanel extends JPanel  implements ActionListener{

	static Log log = LogFactory.getLog(MainPanel.class.getName());

	private JComboBox algorithmsList;

	private GridPanel gridPanel;
	private Grid grid;
	private int gridSize;

	private String ACTION_RESET = "ACTION_RESET";
	private String ACTION_START = "ACTION_START";



	private String[] algorithms = { "AStar", "BreadthFirstSearch"};

	public MainPanel(int size) {
		super();
		setSize(size, size);
		setLayout(new BorderLayout());

		//initialize the grid
		gridSize = size/10;
		grid = new Grid(gridSize);

		//intialize the panel to drow the grid
		gridPanel = new GridPanel(grid,size);


		//control and actions
		JPanel controlPanel = new JPanel();

		JButton buttonResetGrid = new JButton("reset grid");
		buttonResetGrid.setActionCommand(ACTION_RESET);
		buttonResetGrid.addActionListener(this);

		JButton buttonStart = new JButton("start");		
		buttonStart.setActionCommand(ACTION_START);		
		buttonStart.addActionListener(this);

		algorithmsList = new JComboBox(algorithms);

		controlPanel.add(buttonResetGrid);
		controlPanel.add(buttonStart);		
		controlPanel.add(algorithmsList);			

		//put all togheter
		add(controlPanel, BorderLayout.SOUTH);		
		add(gridPanel, BorderLayout.CENTER);
	}


	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		if(ACTION_RESET.equals(command)){
			grid.resetCells();
			gridPanel.setPath(new ArrayList<Cell>());
		}else if(ACTION_START.equals(command)){
	
			String algorithm = (String)algorithmsList.getSelectedItem();
			PathFinding finder = null;
			if(algorithm.equals("AStar")){
				finder = new AStar();
			}else if(algorithm.equals("BreadthFirstSearch")){
				finder = new BreadthFirstSearch();
			}

			List<Cell> path = finder.search(grid, new Cell(gridSize/2, gridSize/2), new Cell(gridSize-1, gridSize-1));
			gridPanel.setPath(path);
		}

	}





}
