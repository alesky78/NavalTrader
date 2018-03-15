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
import it.spaghettisource.navaltrader.game.model.Ship;

/**
 * this button is 
 * 
 * @author id837836
 *
 */
public class ButtonDrawPort extends JButton{

	static Log log = LogFactory.getLog(ButtonDrawPort.class.getName());

	private Port port;
	private Ship ship;
	private int buttonBordersize;
	private boolean isValidDockedPort;	

	public ButtonDrawPort( Port port, int buttonSize,int buttonBordersize,String actionCommand, ActionListener listner) {
		super();
		this.port = port;
		this.buttonBordersize = buttonBordersize;
		setBorder(BorderFactory.createEmptyBorder());
		setContentAreaFilled(false);
		setSize(buttonSize, buttonSize);
		isValidDockedPort = true;		
		
		//add listener and command only if there is a listener and if the ship can go to this port
		if(listner!=null){
			addActionListener(listner);
			setActionCommand(actionCommand);			
		}
	}
	
	public ButtonDrawPort( Port port, Ship ship, int buttonSize,int buttonBordersize,String actionCommand, ActionListener listner) {
		super();
		this.port = port;
		this.ship = ship;
		this.buttonBordersize = buttonBordersize;
		setBorder(BorderFactory.createEmptyBorder());
		setContentAreaFilled(false);
		setSize(buttonSize, buttonSize);
		
		if(port.getShipSizeAccepted() >= ship.getShipSize()){
			isValidDockedPort = true;
		}
		
		//add listener and command only if there is a listener and if the ship can go to this port
		if(listner!=null && isValidDockedPort){
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
		if(isValidDockedPort){
			graphicsBuffer.setColor(Color.GREEN);			
		}else{
			graphicsBuffer.setColor(Color.RED);			
		}
	
		graphicsBuffer.fillOval(buttonBordersize, buttonBordersize, getWidth()-buttonBordersize, getHeight()-buttonBordersize);			
		graphicsBuffer.setColor(Color.BLACK);				
		graphicsBuffer.drawOval(buttonBordersize, buttonBordersize, getWidth()-buttonBordersize, getHeight()-buttonBordersize);		
	}


	public void resetLocation(JPanel panel,int worldSize){
		ScreeCoordinteUtil.setLocationFromRealWorldToScreenCoordinate(panel, this, port.getCooridnate(), worldSize);
	}


}
