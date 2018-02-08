package it.spaghettisource.navaltrader.game.factory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrade.pathfinding.AStar;
import it.spaghettisource.navaltrade.pathfinding.Grid;
import it.spaghettisource.navaltrade.pathfinding.GridUtils;
import it.spaghettisource.navaltrade.pathfinding.PathFinding;
import it.spaghettisource.navaltrader.game.model.Port;
import it.spaghettisource.navaltrader.game.model.Route;
import it.spaghettisource.navaltrader.game.model.World;
import it.spaghettisource.navaltrader.geometry.Mathematic;
import it.spaghettisource.navaltrader.geometry.Point;
import it.spaghettisource.navaltrader.ui.component.PanelDrawRoute;

public class WorldFactory {

	private static Log log = LogFactory.getLog(WorldFactory.class.getName());


	public World createWorld() {

		try {

			Configurations configs = new Configurations();
			Configuration config;
			
			//create the world
			File tempFile = generateTempFile("world", "/scenario/world.properties");
			config = configs.properties(tempFile);			
			World world = new World();
			world.setGridScale(config.getInt("gridscale"));
			

			//create the ports
			tempFile = generateTempFile("port", "/scenario/port.properties");
			config = configs.properties(tempFile);			
			List<Port> ports = new ArrayList<Port>();
			Port actual;

			Point cooridnate = null;
			for (int i = 0; i <config.getInt("ports"); i++) {
				cooridnate = new Point(config.getInt("port"+i+".xpos"), config.getInt("port"+i+".ypos"));
				actual = new Port(world,
								 cooridnate,
								 config.getString("port"+i+".name"), 
								 config.getDouble("port"+i+".dailyFeeCost"),
								 config.getInt("port"+i+".classAccepted"), 
								 config.getInt("port"+i+".dayContractRegeneration")); 
				ports.add(actual);				
			}
			
			//add the port to the world
			world.setPorts(ports);

			//prepare the route
			tempFile = generateTempFile("grid", "/scenario/grid.map");
			Grid grid = GridUtils.loadFromFile(tempFile);
			
			world.setGridSize(grid.getSize());
			world.setWorldMap(ImageIO.read(PanelDrawRoute.class.getResourceAsStream("/scenario/world.png")));
			
			PathFinding finder =  new AStar();	
			Point[] path;
			Route route;
			List<Port> connectedPorts;
			
			
			//generate the routes for all the ports
			for (Port port : ports) {
				connectedPorts = world.getConnectedPorts(port);
				for (Port destination : connectedPorts) {
					path = finder.search(grid, port.getCooridnate(), destination.getCooridnate());
					route = new Route(destination, world.getGridScale(), path);
					port.addRoute(route);
					grid.resetCells();
				}
			}

			//now transform the port coordinate in scale coordinate
			for (Port port : ports) {
				 port.setCooridnate(Mathematic.scale(port.getCooridnate(), world.getGridScale())); 
			}
			
			//generate the contracts
			for (Port port : ports) {
				port.generateContracts();
			}
			
			return world;

		}catch (Exception e) {
			log.error("error creating the world", e);
			return null;
		}

	}


	private File generateTempFile(String tempFileName, String sourceFile) throws IOException, FileNotFoundException {
		File tempFile = File.createTempFile(tempFileName, null);
		tempFile.deleteOnExit();
		FileOutputStream out = new FileOutputStream(tempFile);
		IOUtils.copy(WorldFactory.class.getResourceAsStream(sourceFile), out);
		out.close();
		return tempFile;
	}


}
