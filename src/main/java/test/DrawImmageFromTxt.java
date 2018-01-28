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
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//SplitPaneDemo itself is not a visible component.
public class DrawImmageFromTxt {

	static Log log = LogFactory.getLog(DrawImmageFromTxt.class.getName());

	BufferedImage image;

	public DrawImmageFromTxt(){

		InputStream in = null;

		try {
			Rectangle rect = calculateDimension();
			log.info(rect.toString());
			image = new BufferedImage((int)rect.getWidth(), (int)rect.getHeight(), BufferedImage.TYPE_INT_ARGB); 
			
			in = DrawImmageFromTxt.class.getResourceAsStream("/map/test.txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));

			String line;
			int x = 0;
			int y = 0;			
			int data = 0;
			while((line = reader.readLine())!=null ){
				for (int i = 0; i < line.length(); i++) {
					if(line.substring(i, i+1).equals("X")) {
						image.setRGB(x, y, Color.RED.getRGB());										
					}else if(line.substring(i, i+1).equals(" ")) {
						image.setRGB(x, y, Color.WHITE.getRGB());				
					}else if(line.substring(i, i+1).equals("A")) {
						image.setRGB(x, y, Color.GREEN.getRGB());				
					}

					x++;	
				}
				//next line reset index
				y++;
				x = 0;
			}

		} catch (IOException e) {
			log.error(e,e);
			e.printStackTrace();
		}finally {
			try {
				in.close();
			} catch (IOException e) {}
		}


	}

	public Rectangle calculateDimension() throws IOException {

		InputStream in = DrawImmageFromTxt.class.getResourceAsStream("/map/test.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));

		String line;
		int width = 0;
		int height = 0;	
		while((line = reader.readLine())!=null ){
			log.info(line);
			width = line.length();
			height++; 
		}
		in.close();

		return new Rectangle(width, height);
	}


	public PaintJPanel createPanel() {
		return new PaintJPanel(image);
	}


	/**
	 * Create the GUI and show it.  For thread safety,
	 * this method should be invoked from the
	 * event-dispatching thread.
	 */
	private static void createAndShowGUI() {

		//Create and set up the window.
		JFrame frame = new JFrame("draw immage by txt demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		DrawImmageFromTxt splitPaneDemo = new DrawImmageFromTxt();


		frame.getContentPane().add(splitPaneDemo.createPanel());

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


	public class PaintJPanel extends JPanel{

		private BufferedImage image;

		public PaintJPanel(BufferedImage image) {
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
		}

		private void modifyImmageAndRender(Graphics g) {
			Dimension dim = getSize();			
			g.drawImage(image,0,0,(int)dim.getWidth(),(int)dim.getHeight(),0,0,image.getWidth(),image.getHeight(),null);
		}



	}

}