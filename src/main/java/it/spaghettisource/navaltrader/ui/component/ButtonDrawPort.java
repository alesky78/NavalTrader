package it.spaghettisource.navaltrader.ui.component;

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
	static BufferedImage dockGreen = ImageIconFactory.getBufferImageByName("/images/dock-green.png");
	static BufferedImage dockRed = ImageIconFactory.getBufferImageByName("/images/dock-red.png");
	static BufferedImage dockStandard = ImageIconFactory.getBufferImageByName("/images/dock.png");		
	static BufferedImage shipDocked = ImageIconFactory.getBufferImageByName("/images/dock-ship.png");		

	private Port port;
	private Ship ship;
	private int buttonBordersize;
	private boolean isValidDockedPort;		//verify if the related ship can go in this port

	private boolean isForBoardGame;	// this flag identify if the button will be used for the navigation Panel of for the boardGame Panel	 

	public ButtonDrawPort( Port port, String actionCommand, ActionListener listner) {
		super();
		this.port = port;
		this.buttonBordersize = buttonBordersize;
		setBorder(BorderFactory.createEmptyBorder());
		setContentAreaFilled(false);
		setSize(dockGreen.getWidth(), dockGreen.getHeight());
		isForBoardGame = true;
			
		//add listener and command
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
		setSize(dockGreen.getWidth(), dockGreen.getHeight());
		isForBoardGame = false;		
		
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

		//draw the ports in the board
		if(isForBoardGame) {
			graphicsBuffer.drawImage(dockStandard, 0, 0, dockStandard.getWidth(), dockStandard.getHeight(), null);
		}else if(isValidDockedPort){
			graphicsBuffer.drawImage(dockGreen, 0, 0, dockGreen.getWidth(), dockGreen.getHeight(), null);			
		}else{
			graphicsBuffer.drawImage(dockRed, 0, 0, dockRed.getWidth(), dockRed.getHeight(), null);
		}
		
	}


	public void resetLocation(JPanel panel,int worldWidth, int worldHeight){
		ScreeCoordinteUtil.setLocationFromRealWorldToScreenCoordinate(panel, this, port.getCooridnate(), worldWidth,worldHeight);
	}


}
