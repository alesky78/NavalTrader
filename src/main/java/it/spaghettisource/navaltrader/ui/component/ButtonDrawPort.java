package it.spaghettisource.navaltrader.ui.component;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.draw.ScreeCoordinteUtil;
import it.spaghettisource.navaltrader.game.model.Port;

/**
 * this button is 
 * 
 * @author id837836
 *
 */
public class ButtonDrawPort extends JButton{

	static Log log = LogFactory.getLog(ButtonDrawPort.class.getName());

	private Port port;
	private int buttonBordersize;

	public ButtonDrawPort( Port port,int buttonSize,int buttonBordersize,String actionCommand, ActionListener listner) {
		super();
		this.port = port;
		this.buttonBordersize = buttonBordersize;
		setBorder(BorderFactory.createEmptyBorder());
		setContentAreaFilled(false);
		setSize(buttonSize, buttonSize);
		
		//add listner and command
		if(listner!=null){
			addActionListener(listner);
			setActionCommand(actionCommand);			
		}
		
	}

	public Port getManagedPort(){
		return port;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g); 
		Graphics2D graphicsBuffer = (Graphics2D) g;

		//draw the ports
		graphicsBuffer.setStroke(new BasicStroke(buttonBordersize));		
		graphicsBuffer.setColor(Color.YELLOW);	
		graphicsBuffer.fillOval(buttonBordersize, buttonBordersize, getWidth()-buttonBordersize, getHeight()-buttonBordersize);			
		graphicsBuffer.setColor(Color.BLACK);				
		graphicsBuffer.drawOval(buttonBordersize, buttonBordersize, getWidth()-buttonBordersize, getHeight()-buttonBordersize);		
	}


	public void resetLocation(JPanel panel,int worldSize){
		ScreeCoordinteUtil.setLocationFromRealWorldToScreenCoordinate(panel, this, port.getCooridnate(), worldSize);
	}


}
