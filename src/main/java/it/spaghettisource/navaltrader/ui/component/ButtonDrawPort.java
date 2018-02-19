package it.spaghettisource.navaltrader.ui.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.model.Port;
import it.spaghettisource.navaltrader.geometry.Point;
import it.spaghettisource.navaltrader.ui.ImageIconFactory;

/**
 * this button is 
 * 
 * @author id837836
 *
 */
public class ButtonDrawPort extends JButton  implements ActionListener{

	static Log log = LogFactory.getLog(ButtonDrawPort.class.getName());

	private Port port;
	private int buttonSize;

	private int x,y; //buttonLocation

	private int spirteSize = 120;	
	private ImageIcon portImmage; 		



	public ButtonDrawPort( Port port,int buttonSize,String actionCommand) {
		super();
		this.port = port;
		this.buttonSize = buttonSize;
		setBorder(BorderFactory.createEmptyBorder());
		setContentAreaFilled(false);
		setSize(buttonSize, buttonSize);

		portImmage = ImageIconFactory.getForButton("/icon/harbor.png");		
		setIcon(portImmage);

		addActionListener(this);
	}



	public void resetLocation(JPanel panel,int worldSize){
		Point point = port.getCooridnate();		
		x =(int)( (double)point.getX() * ((double)panel.getWidth()/(double)worldSize)  -buttonSize/2);
		y =(int)( (double)point.getY() * ((double)panel.getHeight()/(double)worldSize) -buttonSize/2);
		setLocation(x,y);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("cliccato!!!");

	}


}
