package it.spaghettisource.navaltrader.ui.component;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import it.spaghettisource.navaltrader.ui.ImageIconFactory;

public class PanelDrawBackground extends JPanel {

	BufferedImage background;
	
	
	public PanelDrawBackground(String immageName) {
		super();
		
		background = ImageIconFactory.getImageBy(immageName);
		
	}

	
	public void paintComponent(Graphics graphicsPanel) {
		super.paintComponent(graphicsPanel);
		
		graphicsPanel.drawImage(background,0,0,getWidth(),getHeight(),0,0,background.getWidth(),background.getHeight(),null);			
		
	}
	
	
	
	
}
