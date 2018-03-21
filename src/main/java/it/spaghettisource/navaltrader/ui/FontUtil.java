package it.spaghettisource.navaltrader.ui;

import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

public class FontUtil {

	
	private static Font drawFont;
	
	/**
	 * return the standard fond used to draw in the game
	 * 
	 * @return
	 */
	public static Font getDrawFont(){
		
		if(drawFont==null){
			Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
			attributes.put(TextAttribute.TRACKING, 0.01);
			drawFont = new Font("arial", Font.BOLD, 40).deriveFont(attributes);			
		}

		return drawFont; 
		
	}
	
}
