/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

package test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

//SplitPaneDemo itself is not a visible component.
public class SplitPaneDemoRenderingWihtGraphic extends JPanel
implements ListSelectionListener {
	private PaintJPanel picture;
	private JList list;
	private JSplitPane splitPane;
	private String[] imageNames = { "agenda", "bank", "cart-buy", "cart-sell", "clipboard"};


	public SplitPaneDemoRenderingWihtGraphic() {

		//Create the list of images and put it in a scroll pane.

		list = new JList(imageNames);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		list.addListSelectionListener(this);


		JScrollPane listScrollPane = new JScrollPane(list);

		picture = new PaintJPanel();

		JScrollPane pictureScrollPane = new JScrollPane(picture);

		//Create a split pane with the two scroll panes in it.
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				listScrollPane, pictureScrollPane);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(150);

		//Provide minimum sizes for the two components in the split pane.
		Dimension minimumSize = new Dimension(100, 50);
		listScrollPane.setMinimumSize(minimumSize);
		pictureScrollPane.setMinimumSize(minimumSize);

		//Provide a preferred size for the split pane.
		splitPane.setPreferredSize(new Dimension(400, 200));
		updateLabel(imageNames[list.getSelectedIndex()]);
	}

	//Listens to the list
	public void valueChanged(ListSelectionEvent e) {
		JList list = (JList)e.getSource();
		updateLabel(imageNames[list.getSelectedIndex()]);
	}

	//Renders the selected image
	protected void updateLabel (String name) {
		BufferedImage immage = createBufferedImage("/icon/" + name + ".png");
		picture.setImage(immage);
	}

	//Used by SplitPaneDemo2
	public JList getImageList() {
		return list;
	}

	public JSplitPane getSplitPane() {
		return splitPane;
	}


	
	private static BufferedImage createBufferedImage(String path) {

		try {
			BufferedImage bufferedImage;
			bufferedImage = ImageIO.read(SplitPaneDemoRenderingWihtGraphic.class.getResourceAsStream(path));
			return bufferedImage;
		} catch (IOException e) {
			System.err.println("Couldn't find file: " + path);
			return null;
		}

	}	

	/**
	 * Create the GUI and show it.  For thread safety,
	 * this method should be invoked from the
	 * event-dispatching thread.
	 */
	private static void createAndShowGUI() {

		//Create and set up the window.
		JFrame frame = new JFrame("SplitPaneDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SplitPaneDemoRenderingWihtGraphic splitPaneDemo = new SplitPaneDemoRenderingWihtGraphic();
		frame.getContentPane().add(splitPaneDemo.getSplitPane());

		//Display the window.
		frame.pack();
		frame.setVisible(true);
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


	class PaintJPanel extends JPanel{

		private BufferedImage image;
		
		public PaintJPanel() {
			super();
			this.image = image;
		}

		public void setImage(BufferedImage image) {
			this.image = image;
			repaint();
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			
			modifyImmageAndRender(g);
			//renderImmage(g);
		}

		private void modifyImmageAndRender(Graphics g) {
			Dimension dim = getSize();			
			Graphics2D dbg = (Graphics2D) image.getGraphics();
			Color originalColor = dbg.getColor();
			dbg.setColor(Color.RED); //blue
			dbg.drawLine(8, 8, 38, 38);						
			dbg.setColor(originalColor);		
			
			//g.drawImage(image, 0, 0, this); // this make a draw wiht original size of immage		
			g.drawImage(image,0,0,(int)dim.getWidth(),(int)dim.getHeight(),0,0,image.getWidth(),image.getHeight(),null);
		}
		
		
		
		private void renderImmage(Graphics g) {
			
			//create an immage large like the panel
			Dimension dim = getSize();
			Image dbImage = this.createImage((int)dim.getWidth(), (int)dim.getHeight());
			Graphics2D dbg = (Graphics2D) dbImage.getGraphics();
			dbg.drawImage(image,0,0,(int)dim.getWidth(),(int)dim.getHeight(),0,0,image.getWidth(),image.getHeight(),null);
			
			
			//draw a random point
			Color originalColor = dbg.getColor();
			dbg.setColor(Color.RED); //blue
			dbg.drawLine(8, 8, 38, 38);						
			dbg.setColor(originalColor);
			
			//draw on the panel the immage
			g.drawImage(dbImage, 0, 0,(int)dim.getWidth(),(int)dim.getHeight(), null);
			Toolkit.getDefaultToolkit().sync();
			g.dispose();
			
		}

	}

}