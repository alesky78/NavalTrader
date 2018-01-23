package it.spaghettisource.navaltrader.ui;

import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;

public class MainDesktopPane extends JDesktopPane {

	
	public void showInfoMessageDialog(String message) {
		JOptionPane.showInternalMessageDialog(this, message,"info",JOptionPane.INFORMATION_MESSAGE,  ImageIconFactory.getForTab("/icon/info.png"));
	}

	public void showErrorMessageDialog(String message) {
		JOptionPane.showInternalMessageDialog(this, message,"error",JOptionPane.ERROR_MESSAGE,  ImageIconFactory.getForTab("/icon/error.png"));
	}	
	
	public void showWarningMessageDialog(String message) {	
		JOptionPane.showInternalMessageDialog(this, message,"warning",JOptionPane.WARNING_MESSAGE,  ImageIconFactory.getForTab("/icon/warning.png"));
	}		
	
	public void showOKMessageDialog(String message) {	
		JOptionPane.showInternalMessageDialog(this, message,"ok",JOptionPane.INFORMATION_MESSAGE, ImageIconFactory.getForTab("/icon/success.png"));
	}		
	
	
}
