package test.pathfinding.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UserActionListener  implements MouseListener, KeyListener {

	static Log log = LogFactory.getLog(UserActionListener.class.getName());

	private boolean keyShiftDown;
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
			if (keyShiftDown) {
				panel.setStartPoint(mouseX,mouseY);
				log.info("BUTTON1 x:"+mouseX+" y:"+mouseY);
			}
		}else if(event.getButton() == MouseEvent.BUTTON3) {
			if (keyShiftDown) {
				panel.setEndPoint(mouseX,mouseY);				
				log.info("BUTTON3 x:"+mouseX+" y:"+mouseY);
			}
		}	

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
		}	

	}

	@Override
	public void keyReleased(KeyEvent e) {	
		if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
			keyShiftDown = false;		
		}	
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}
