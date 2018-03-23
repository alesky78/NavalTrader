package it.spaghettisource.navaltrader.ui.component;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.draw.ScreeCoordinteUtil;
import it.spaghettisource.navaltrader.game.model.Port;
import it.spaghettisource.navaltrader.game.model.Ship;
import it.spaghettisource.navaltrader.ui.ImageIconFactory;

/**
 * this button is 
 * 
 * @author id837836
 *
 */
public class ButtonDrawPort extends JButton{

	static Log log = LogFactory.getLog(ButtonDrawPort.class.getName());
	static BufferedImage dockImmage = ImageIconFactory.getBufferImageByName("/images/dock.png");

	private Port port;
	private Ship ship;
	private int buttonBordersize;
	private boolean isValidDockedPort;	

	public ButtonDrawPort( Port port, String actionCommand, ActionListener listner) {
		super();
		this.port = port;
		this.buttonBordersize = buttonBordersize;
		setBorder(BorderFactory.createEmptyBorder());
		setContentAreaFilled(false);
		setSize(dockImmage.getWidth(), dockImmage.getHeight());
		isValidDockedPort = true;		
		
		//add listener and command only if there is a listener and if the ship can go to this port
		if(listner!=null && actionCommand !=null){
			addActionListener(listner);
			setActionCommand(actionCommand);			
		}
	}
	
	public ButtonDrawPort( Port port, Ship ship, String actionCommand, ActionListener listner) {
		super();
		this.port = port;
		this.ship = ship;
		this.buttonBordersize = buttonBordersize;
		setBorder(BorderFactory.createEmptyBorder());
		setContentAreaFilled(false);
		setSize(dockImmage.getWidth(), dockImmage.getHeight());
		
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
		if(isValidDockedPort){
			graphicsBuffer.setColor(Color.GREEN);			
		}else{
			graphicsBuffer.setColor(Color.RED);			
		}
	
		graphicsBuffer.drawImage(dockImmage, 0, 0, dockImmage.getWidth(), dockImmage.getHeight(), null);
		
	}


	public void resetLocation(JPanel panel,int worldWidth, int worldHeight){
		ScreeCoordinteUtil.setLocationFromRealWorldToScreenCoordinate(panel, this, port.getCooridnate(), worldWidth,worldHeight);
	}


}
