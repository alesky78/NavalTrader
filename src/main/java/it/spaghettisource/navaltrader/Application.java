package it.spaghettisource.navaltrader;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.GameManager;
import it.spaghettisource.navaltrader.ui.frame.MainMenu;

public class Application {

	static Log log = LogFactory.getLog(Application.class.getName());

	public static boolean nimbus = true;

	public static void main(String[] args){

		if(nimbus) {
			activateNimbus();
		}else {
			JFrame.setDefaultLookAndFeelDecorated(true);   			
		}    
		
		//Schedule a job for the event-dispatching thread:
		//creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainMenu();
			}
		});

	}

	private static void activateNimbus() {
		//NIMBUS
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
	}




}
