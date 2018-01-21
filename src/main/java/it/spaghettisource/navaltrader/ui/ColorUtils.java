package it.spaghettisource.navaltrader.ui;

import java.awt.Color;

public class ColorUtils {

	/**
	 * 
	 * @param value must be between 0 and 1
	 * @return
	 */
	public static Color getTransitionRedToGreen(double value){
	    return new Color(Color.HSBtoRGB((float)value/3f, 1f, 1f));
	}
	

	
	
}
