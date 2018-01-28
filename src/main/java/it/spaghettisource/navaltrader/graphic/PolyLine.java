package it.spaghettisource.navaltrader.graphic;

import java.awt.Graphics;
import java.util.*;


/*
 * The PolyLine class model a line made up of many points
 */
public class PolyLine {

	private int points;
	private int[] xPoints;
	private int[] yPoints;  	

	public PolyLine() {
		points = 0;
		xPoints = new int[0];
		yPoints = new int[0];		
	}

	public void addPoint(int x, int y) {
		//extend the array
		points++;
		xPoints = Arrays.copyOf(xPoints, points);
		yPoints = Arrays.copyOf(yPoints, points);
		
		//add the new point
		xPoints[points-1] = x;
		yPoints[points-1] = x;		
		
	}

	public void draw(Graphics g) { // draw itself
		g.drawPolyline(xPoints,yPoints, points);
	}
}