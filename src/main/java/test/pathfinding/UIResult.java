

package test.pathfinding;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import test.pathfinding.algorithm.PathFinding;

public class UIResult  extends JPanel {

	
	
	static Log log = LogFactory.getLog(UIResult.class.getName());
	
	int size;
	BufferedImage image;

	public UIResult(int size){
		this.size = size;

		image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB); 

		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				image.setRGB(x, y, Color.WHITE.getRGB());
			}
		}
	}

	
	public void createPath(List<Cell> path) {
		
		image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB); 

		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				image.setRGB(x, y, Color.WHITE.getRGB());
			}
		}
		
		for (Cell cell : path) {
			image.setRGB(cell.getX(), cell.getY(), Color.RED.getRGB());
		}
		
		setImage(image);
		
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
		repaint();
	}
	
	

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		modifyImmageAndRender(g);
	}

	private void modifyImmageAndRender(Graphics g) {
		Dimension dim = getSize();			
		g.drawImage(image,0,0,(int)dim.getWidth(),(int)dim.getHeight(),0,0,image.getWidth(),image.getHeight(),null);
	}

	
	
	public static List<Cell> runTest() {
		
		int gridSize = 100;
		Grid grid = new Grid(gridSize);
		PathFinding finder = new PathFinding();
		double start  = System.currentTimeMillis();
		List<Cell> path = finder.search(grid, new Cell(0, 0), new Cell(gridSize-1, gridSize-1));
		double end  = System.currentTimeMillis();
		for (Cell cell : path) {
			log.info("step "+cell);
		}
			
		log.info("total time seconds:"+(end-start)/1000);
		
		return path;
	
	}
	
	
	
	public static void main(String[] args) {
		//Schedule a job for the event-dispatching thread:
		//creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}


	private static void createAndShowGUI() {

		int size = 500;
		
		//Create and set up the window.
		JFrame frame = new JFrame("path finding");
		frame.setSize(size, size);		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(new JLabel("dimension:"+size),BorderLayout.NORTH);
		
		UIResult result = new UIResult(size);
		result.createPath( runTest());		
		panel.add(result,BorderLayout.CENTER);

		
		
		frame.add(panel);

		//Display the window.
		frame.setVisible(true);
	}


}