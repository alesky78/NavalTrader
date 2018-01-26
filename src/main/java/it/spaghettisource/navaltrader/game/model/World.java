package it.spaghettisource.navaltrader.game.model;

import java.util.ArrayList;
import java.util.List;

import it.spaghettisource.navaltrader.game.loop.Entity;

public class World implements Entity {

	private List<Port> ports;

	public World() {
		ports = new ArrayList<Port>();
		
		ports.add(new Port("porto A", 4, 1));
		ports.add(new Port("porto B", 5, 2));		
		
	}

	public void update(int minutsPassed, boolean isNewDay, boolean isNewWeek, boolean isNewMonth) {
			
	}
	
}
