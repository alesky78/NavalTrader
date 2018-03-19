package it.spaghettisource.navaltrader.mapeditor;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrade.pathfinding.AStar;
import it.spaghettisource.navaltrade.pathfinding.Cell;
import it.spaghettisource.navaltrade.pathfinding.Dijkstra;
import it.spaghettisource.navaltrade.pathfinding.Grid;
import it.spaghettisource.navaltrade.pathfinding.GridUtils;
import it.spaghettisource.navaltrade.pathfinding.PathFinding;
import it.spaghettisource.navaltrader.geometry.Point;

public class MainPanel extends JPanel  implements ActionListener{

	static Log log = LogFactory.getLog(MainPanel.class.getName());

	private JComboBox<String> algorithmsList;
	private JComboBox<String> drawGridList;
	private JComboBox<Boolean> allowDiagonalList;		
	private JSlider alphaColorSlider;
	private JTextField coordinatePointOnGrid;	
	
	private JTextField gridHeight;
	private JTextField gridWidth;
	
	private JTextField mouseSize;

	private GridPanel gridPanel;
	private Grid grid;
	private int gridSize;

	private String ACTION_RESET = "ACTION_RESET";
	private String ACTION_START = "ACTION_START";
	private String ACTION_GRID_SIZE = "ACTION_GRID_SIZE";
	private String ACTION_DRAW_GRID = "ACTION_DRAW_GRID";	
	private String ACTION_SAVE_GRID = "ACTION_SAVE_GRID";	
	private String ACTION_LOAD_GRID = "ACTION_LOAD_GRID";	


	private String[] algorithmsValues = { "AStar", "Dijkstra"};
	private String[] drawGridValues = { "draw grid", "remove grid"};		
	private Boolean[] allowDiagonalValues = { Boolean.TRUE, Boolean.FALSE};

	private Cell startCell;
	private Cell endCell;	

	public MainPanel(int size) {
		super();
		setSize(size, size);
		setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

		setLayout(new BorderLayout());
		setFocusable(true);

		UserActionListener listener = new UserActionListener(this); 

		addMouseListener(listener);
		addMouseMotionListener(listener);
		addMouseWheelListener(listener);
		addKeyListener(listener);

		//initialize the grid
		gridHeight = new JTextField(10);
		gridWidth = new JTextField(10);
		grid = new Grid(10);

		//intialize the panel to drow the grid
		gridPanel = new GridPanel(grid,10);

		//initialize the mouse size
		mouseSize = new JTextField("1");
		mouseSize.setColumns(3);

		//control and actions
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.PAGE_AXIS));

		JPanel controlPanel1 = new JPanel();
		JPanel controlPanel2 = new JPanel();	
		JPanel controlPanel3 = new JPanel();		
		JPanel controlPanel4 = new JPanel();				

		JButton buttonResetGrid = new JButton("reset result");
		buttonResetGrid.setActionCommand(ACTION_RESET);
		buttonResetGrid.addActionListener(this);

		JButton buttonStart = new JButton("start");		
		buttonStart.setActionCommand(ACTION_START);		
		buttonStart.addActionListener(this);

		algorithmsList = new JComboBox<String>(algorithmsValues);
		algorithmsList.addActionListener(this);

		JButton gridChangeButton = new JButton("chang grid size");
		gridChangeButton.setActionCommand(ACTION_GRID_SIZE);		
		gridChangeButton.addActionListener(this);		

		drawGridList = new JComboBox<String>(drawGridValues);
		drawGridList.setActionCommand(ACTION_DRAW_GRID);		
		drawGridList.addActionListener(this);		
		
		allowDiagonalList  = new JComboBox<Boolean>(allowDiagonalValues);
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

		JButton loadGrid = new JButton("load grid");
		loadGrid.setActionCommand(ACTION_LOAD_GRID);
		loadGrid.addActionListener(this);		

		JButton saveGrid = new JButton("save grid");
		saveGrid.setActionCommand(ACTION_SAVE_GRID);
		saveGrid.addActionListener(this);

		coordinatePointOnGrid = new JTextField("", 10);
		coordinatePointOnGrid.setEditable(false);


		//line 1
		controlPanel1.add(buttonResetGrid);
		controlPanel1.add(buttonStart);		
		controlPanel1.add(algorithmsList);		
		controlPanel1.add(allowDiagonalList);			

		//line 2
		controlPanel2.add(drawGridList);		
		controlPanel2.add(alphaColorSlider);	
		controlPanel2.add(coordinatePointOnGrid);
		controlPanel2.add(loadGrid);		
		controlPanel2.add(saveGrid);				

		//line 3
		controlPanel3.add(gridHeight);
		controlPanel3.add(gridWidth);
		controlPanel3.add(gridChangeButton);		
		
		//line 4
		controlPanel4.add(new JLabel("mouse"));		
		controlPanel4.add(mouseSize);
		
		controlPanel.add(controlPanel1);
		controlPanel.add(controlPanel2);	
		controlPanel.add(controlPanel3);	
		controlPanel.add(controlPanel4);		
		

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
		gridPanel.addWallByScreenCoordinate(x, y,Integer.parseInt(mouseSize.getText()));
	}

	public void removeWall(int x, int y) {
		gridPanel.removeWallByScreenCoordinate(x, y,Integer.parseInt(mouseSize.getText()));
	}

	public void changeMouseSize(int value) {
		log.info("mouse change value:"+value);
		int newValue = Integer.parseInt(mouseSize.getText())+value;
		if(newValue < 1 ){
			newValue = 1;
		}else if(newValue > 10){
			newValue = 10;
		}
		mouseSize.setText( Integer.toString(newValue)  );			
	}
	
	public void setGridPointCoordinate(int x, int y) {

		Cell selected = gridPanel.getCellByScreenCoordinate(x, y);
		if(selected!=null) {
			coordinatePointOnGrid.setText("Point x:"+selected.getX()+" y:"+selected.getY());			
		}
	}


	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		if(ACTION_RESET.equals(command)){
			grid.resetCells();
			gridPanel.setPath(null);
		}else if(ACTION_START.equals(command)){

			if( startCell != null && endCell != null){

				String algorithm = (String)algorithmsList.getSelectedItem();
				Boolean diagonal = (Boolean)allowDiagonalList.getSelectedItem();
				(new Thread(new SearchThread(algorithm,diagonal))).start();

			}
		}else if (ACTION_GRID_SIZE.equals(command)){
			startCell = null;
			endCell = null;
			
			grid = new Grid(Integer.parseInt(gridWidth.getText()), Integer.parseInt(gridHeight.getText()));
			gridPanel.setGrid(grid);

		}else if (ACTION_DRAW_GRID.equals(command)){
			String chose  = (String)drawGridList.getSelectedItem();
			if(chose.equals("draw grid")){
				gridPanel.setDrawGrid(true);
			}else if(chose.equals("remove grid")){
				gridPanel.setDrawGrid(false);				

			}
		}else if (ACTION_SAVE_GRID.equals(command)){
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Specify the file to save");   

			int userSelection = fileChooser.showSaveDialog(getParent());

			if (userSelection == JFileChooser.APPROVE_OPTION) {
				File fileToSave = fileChooser.getSelectedFile();
				GridUtils.convertToFile(fileToSave, grid);
			}

		}else if (ACTION_LOAD_GRID.equals(command)){
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Specify the file to load");   
			int userSelection = fileChooser.showOpenDialog(getParent());

			if (userSelection == JFileChooser.APPROVE_OPTION) {
				File fileToLoad= fileChooser.getSelectedFile();
				grid = GridUtils.loadFromFile(fileToLoad);

				//set the correct grid value, but disabel the event

				gridWidth.setText(Integer.toString(grid.getWidth()));
				gridHeight.setText(Integer.toString(grid.getHeight()));

				gridPanel.setGrid(grid);


			}

		}

		requestFocus();

	}



	private class SearchThread implements Runnable {

		String algorithm;
		Boolean diagonal;		

		public SearchThread(String algorithm,Boolean diagonal){
			this.algorithm = algorithm;
			this.diagonal = diagonal;
		}

		@Override
		public void run() {

			PathFinding finder = null;

			if(algorithm.equals("AStar")){
				finder = new AStar();
			}else if(algorithm.equals("Dijkstra")){
				finder = new Dijkstra();
			}

			Point[] path = finder.search(grid, startCell.getCoordinate(), endCell.getCoordinate(),diagonal);
			gridPanel.setPath(path);

		}

	}








}
