package it.spaghettisource.navaltrader.ui.component;

import javax.swing.JProgressBar;

import it.spaghettisource.navaltrader.ui.ColorUtils;

public class HullProgressBarField extends JProgressBar {

	public HullProgressBarField(Integer hull){
		setStringPainted(true);		
		setValue(hull);
	}
	
	@Override
	public void setValue(int hull){
		super.setValue(hull);
		setString(hull+"%");
		setForeground(ColorUtils.getTransitionRedToGreen(hull));		
	}	
	
	
}
