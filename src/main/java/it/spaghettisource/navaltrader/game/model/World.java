package it.spaghettisource.navaltrader.game.model;

import java.util.ArrayList;
import java.util.List;

import it.spaghettisource.navaltrader.game.loop.Entity;

public class World implements Entity {

	private List<Port> ports;

	public World() {
		
		//TODO implement configuration of the ports and the word
		ports = new ArrayList<Port>();
		
		ports.add(new Port("porto A", 3000.0, 4, 1));
		ports.add(new Port("porto B", 4000.0, 5, 2));		
		
	}

	public Port getPortByName(String name) {
		for (Port port : ports) {
			if(port.getName().equals(name)) {
				return port;
			}
		}
		
		return null;
	}
	
	public void setPorts(List<Port> ports) {
		this.ports = ports;
	}

	public void update(int minutsPassed, boolean isNewDay, boolean isNewWeek, boolean isNewMonth) {
			
		for (Port port : ports) {
			port.update(minutsPassed, isNewDay, isNewWeek, isNewMonth);
		}
		
	}
	
}
