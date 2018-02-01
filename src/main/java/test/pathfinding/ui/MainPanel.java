package test.pathfinding.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import test.pathfinding.Cell;
import test.pathfinding.Grid;
import test.pathfinding.algorithm.AStar;
import test.pathfinding.algorithm.BreadthFirstSearch;
import test.pathfinding.algorithm.Dijkstra;
import test.pathfinding.algorithm.PathFinding;

public class MainPanel extends JPanel  implements ActionListener{

	static Log log = LogFactory.getLog(MainPanel.class.getName());

	private JComboBox<String> algorithmsList;
	private JComboBox<Integer> gridSizeList;
	private JComboBox<String> drawGridList;		
	private JSlider alphaColorSlider;

	private GridPanel gridPanel;
	private Grid grid;
	private int gridSize;

	private String ACTION_RESET = "ACTION_RESET";
	private String ACTION_START = "ACTION_START";
	private String ACTION_GRID_SIZE = "ACTION_GRID_SIZE";
	private String ACTION_DRAW_GRID = "ACTION_DRAW_GRID";	
	

	private String[] algorithmsValues = { "AStar", "BreadthFirstSearch", "Dijkstra"};
	private Integer[] gridSizeValues = { 10, 50, 100, 250, 300, 400, 500};
	private String[] drawGridValues = { "draw grid", "remove grid"};		

	private Cell startCell;
	private Cell endCell;	

	public MainPanel(int size) {
		super();
		setSize(size, size);

		setLayout(new BorderLayout());
		setFocusable(true);

		UserActionListener listener = new UserActionListener(this); 

		addMouseListener(listener);
		addMouseMotionListener(listener);		
		addKeyListener(listener);

		//initialize the grid
		gridSize = gridSizeValues[0];
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

		algorithmsList = new JComboBox<String>(algorithmsValues);
		algorithmsList.addActionListener(this);

		gridSizeList = new JComboBox<Integer>(gridSizeValues);
		gridSizeList.setActionCommand(ACTION_GRID_SIZE);		
		gridSizeList.addActionListener(this);		

		drawGridList = new JComboBox<String>(drawGridValues);
		drawGridList.setActionCommand(ACTION_DRAW_GRID);		
		drawGridList.addActionListener(this);		
		
		alphaColorSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 255);
		alphaColorSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				if (!source.getValueIsAdjusting()) {
					gridPanel.setAlpha(source.getValue());
				}
				requestFocus();				
			}
		});		
		
		controlPanel.add(buttonResetGrid);
		controlPanel.add(buttonStart);		
		controlPanel.add(algorithmsList);			
		controlPanel.add(gridSizeList);		
		controlPanel.add(drawGridList);		
		controlPanel.add(alphaColorSlider);		
		

		//put all togheter
		add(controlPanel, BorderLayout.SOUTH);		
		add(gridPanel, BorderLayout.CENTER);
	}


	public void setStartPoint(int x,int y){
		startCell = gridPanel.setStartCellByScreenCoordinate(x, y);
	}

	public void setEndPoint(int x,int y){
		endCell = gridPanel.setEndCellByScreenCoordinate(x, y);	
	}

	public void addWall(int x, int y) {
		gridPanel.addWallByScreenCoordinate(x, y);
	}


	public void removeWall(int x, int y) {
		gridPanel.removeWallByScreenCoordinate(x, y);
	}


	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		if(ACTION_RESET.equals(command)){
			grid.resetCells();
			gridPanel.setPath(new ArrayList<Cell>());
		}else if(ACTION_START.equals(command)){

			if( startCell != null && endCell != null){

				String algorithm = (String)algorithmsList.getSelectedItem();
				PathFinding finder = null;

				if(algorithm.equals("AStar")){
					finder = new AStar();
				}else if(algorithm.equals("BreadthFirstSearch")){
					finder = new BreadthFirstSearch();
				}else if(algorithm.equals("Dijkstra")){
					finder = new Dijkstra();
				}

				List<Cell> path = finder.search(grid, startCell, endCell);
				gridPanel.setPath(path);
			}
		}else if (ACTION_GRID_SIZE.equals(command)){
			startCell = null;
			endCell = null;

			gridSize  = (int)gridSizeList.getSelectedItem();
			grid = new Grid(gridSize);
			gridPanel.setGrid(grid);

		}else if (ACTION_DRAW_GRID.equals(command)){
			String chose  = (String)drawGridList.getSelectedItem();
			if(chose.equals("draw grid")){
				gridPanel.setDrawGrid(true);
			}else if(chose.equals("remove grid")){
				gridPanel.setDrawGrid(false);				
				
			}
		}

		requestFocus();

	}












}
