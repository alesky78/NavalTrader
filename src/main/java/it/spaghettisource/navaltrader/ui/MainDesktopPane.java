package it.spaghettisource.navaltrader.ui;

import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;

public class MainDesktopPane extends JDesktopPane {

	
	public void showInfoMessageDialog(String message) {
		//TODO add the icon here
		JOptionPane.showInternalMessageDialog(this, message,"info",JOptionPane.INFORMATION_MESSAGE,  null);
	}

	public void showErrorMessageDialog(String message) {
		//TODO add the icon here		
		JOptionPane.showInternalMessageDialog(this, message,"error",JOptionPane.ERROR_MESSAGE,  null);
	}	
	
	
	
}
