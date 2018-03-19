package it.spaghettisource.navaltrader.draw;

import java.awt.Container;

import javax.swing.JComponent;

import it.spaghettisource.navaltrader.geometry.Point;

public class ScreeCoordinteUtil {

	private ScreeCoordinteUtil(){
	}

	
	/**
	 * covert a point from real coordinate to the coordinate of a component
	 * 
	 * @param component where to draw over
	 * @param realworldCoordinate
	 * @param wolrdSize
	 * @return
	 */
	public static Point convertFromRealWorldToScreenCoordinate(Container component, Point realworldCoordinate, int wolrdSize){
		return new Point((int)( (double)realworldCoordinate.getX() * ((double)component.getWidth()  / (double)wolrdSize) ),
						 (int)( (double)realworldCoordinate.getY() * ((double)component.getHeight() / (double)wolrdSize) ));
	}

	
	/**
	 * covert a point from real coordinate to the coordinate of a component
	 * 
	 * @param container
	 * @param componentToDraw
	 * @param realworldCoordinate
	 * @param wolrdSize
	 * @return
	 */
	public static void setLocationFromRealWorldToScreenCoordinate(Container container,JComponent componentToDraw, Point realworldCoordinate, int wolrdWidth, int wolrdHeight){
		int x = (int)( (double)realworldCoordinate.getX() * ((double)container.getWidth()  / (double)wolrdWidth) - (componentToDraw.getWidth() /2) );
		int y = (int)( (double)realworldCoordinate.getY() * ((double)container.getHeight() / (double)wolrdHeight) - (componentToDraw.getHeight()/2) );
		
		
		componentToDraw.setLocation((int)( (double)realworldCoordinate.getX() * ((double)container.getWidth()  / (double)wolrdWidth) - (componentToDraw.getWidth() /2) ),
									(int)( (double)realworldCoordinate.getY() * ((double)container.getHeight() / (double)wolrdHeight) - (componentToDraw.getHeight()/2) ));

	}
	
	
}
