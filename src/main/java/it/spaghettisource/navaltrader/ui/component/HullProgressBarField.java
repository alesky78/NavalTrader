package it.spaghettisource.navaltrader.ui.component;

import java.text.NumberFormat;

import javax.swing.JProgressBar;

import it.spaghettisource.navaltrader.ui.ColorUtils;

public class HullProgressBarField extends JProgressBar {

	private NumberFormat formatter = NumberFormat.getPercentInstance(); 
	
	public HullProgressBarField(Double hull){
		setStringPainted(true);		
		setValue(hull);
	}
	
	public void setValue(Double hull){
		super.setValue(Double.valueOf((hull*100)).intValue());
		setString(formatter.format(hull));
		setForeground(ColorUtils.getTransitionRedToGreen(hull));		
	}
	
	
	
}
