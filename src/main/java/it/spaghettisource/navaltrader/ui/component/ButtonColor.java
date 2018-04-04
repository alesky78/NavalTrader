package it.spaghettisource.navaltrader.ui.component;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;

import it.spaghettisource.navaltrader.ui.FontUtil;

public class ButtonColor extends JButton {

	Color bkgColor;
	String text;
	
	public ButtonColor(Color color) {
		bkgColor = color;
	}

	
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	
        final Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(bkgColor);
        g2.fillRect(0, 0, getWidth(), getHeight());
        
        //make the text
        if((text = getText())!=null){
            g2.setFont(FontUtil.getDrawFont());
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);        	
        	FontMetrics fontMetrics = g2.getFontMetrics();
    		int stringHeight = fontMetrics.getAscent();		        	
    		int stringWidth = fontMetrics.stringWidth(text);
    		g2.drawString(text,getWidth()/2 - stringWidth/2 ,getHeight()-stringHeight/2);        	
        }
        
        
        
		g2.dispose();
        
        
    }
	
	
	
}
