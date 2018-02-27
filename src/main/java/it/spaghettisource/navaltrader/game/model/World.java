package it.spaghettisource.navaltrader.game.model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import it.spaghettisource.navaltrader.game.loop.Entity;

public class World implements Entity {

	private int gridSize;				//this is the actual world size
	private int gridScale;				//this is the actual world scale, if you multiply gridSize * gridScale you have the real size
	private BufferedImage worldMap;		//this is the actual world map
		
	private List<Port> ports;

	public World() {
		ports = new ArrayList<Port>();
	}
	
	public int getGridSize() {
		return gridSize;
	}

	public void setGridSize(int gridSize) {
		this.gridSize = gridSize;
	}

	public int getGridScale() {
		return gridScale;
	}

	public void setGridScale(int gridScale) {
		this.gridScale = gridScale;
	}

	public int getWorldSize(){
		return gridSize * gridScale;
	}
	
	public BufferedImage getWorldMap() {
		return worldMap;
	}

	public void setWorldMap(BufferedImage worldMap) {
		this.worldMap = worldMap;
	}
	
	public void setPorts(List<Port> ports) {
		this.ports = ports;
	}
	
	public List<Port> getPorts() {
		return ports;
	}

	public Port getPortByName(String name) {
		for (Port port : ports) {
			if(port.getName().equals(name)) {
				return port;
			}
		}
		
		return null;
	}
	
	public List<Port> getConnectedPorts(Port sourcePort) {
		
		List<Port> connected = new ArrayList<Port>();
		
		for (Port port : ports) {
			if(!port.equals(sourcePort)){
				connected.add(port);
			}	
		}
		
		return connected;
	}

	public void update(int minutsPassed, boolean isNewDay, boolean isNewWeek, boolean isNewMonth) {
			
		for (Port port : ports) {
			port.update(minutsPassed, isNewDay, isNewWeek, isNewMonth);
		}
		
	}
	
}
