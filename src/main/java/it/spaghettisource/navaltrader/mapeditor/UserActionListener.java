package it.spaghettisource.navaltrader.mapeditor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UserActionListener  implements MouseListener, MouseMotionListener,KeyListener {

	static Log log = LogFactory.getLog(UserActionListener.class.getName());

	private boolean keyShiftDown;
	private boolean keyControlDown;	
	private MainPanel panel;



	public UserActionListener(MainPanel panel) {
		super();
		this.panel = panel;
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		int mouseX = event.getX();
		int mouseY = event.getY();

		if (event.getButton() == MouseEvent.BUTTON1) {
			log.info("BUTTON1 x:"+mouseX+" y:"+mouseY);
			if (keyShiftDown) {
				panel.setStartPoint(mouseX,mouseY);
			}else if (keyControlDown) {
				panel.addWall(mouseX,mouseY);
			}
		}else if(event.getButton() == MouseEvent.BUTTON3) {
			log.info("BUTTON3 x:"+mouseX+" y:"+mouseY);			
			if (keyShiftDown) {
				panel.setEndPoint(mouseX,mouseY);				
			}else if (keyControlDown) {
				panel.removeWall(mouseX,mouseY);
			}
		}	
	}	

	@Override
	public void mouseDragged(MouseEvent event) {
		int mouseX = event.getX();
		int mouseY = event.getY();				
		
		if (keyControlDown) {
			panel.addWall(mouseX,mouseY);
		}else if (!keyControlDown) {
			panel.removeWall(mouseX,mouseY);
		}
	}


	@Override
	public void mouseMoved(MouseEvent event) {
		int mouseX = event.getX();
		int mouseY = event.getY();	
		
		panel.setGridPointCoordinate(mouseX, mouseY);
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
			keyShiftDown = true;	
		}else if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
			keyControlDown = true;	
		} 	

	}

	@Override
	public void keyReleased(KeyEvent e) {	
		if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
			keyShiftDown = false;		
		}else if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
			keyControlDown = false;	
		} 		
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}
