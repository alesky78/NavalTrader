package it.spaghettisource.navaltrader.game.factory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrade.pathfinding.Cell;
import it.spaghettisource.navaltrade.pathfinding.Grid;
import it.spaghettisource.navaltrade.pathfinding.GridUtils;
import it.spaghettisource.navaltrade.pathfinding.algorithm.AStar;
import it.spaghettisource.navaltrade.pathfinding.algorithm.PathFinding;
import it.spaghettisource.navaltrader.game.model.Port;
import it.spaghettisource.navaltrader.game.model.World;
import it.spaghettisource.navaltrader.graphic.Point;

public class WorldFactory {

	private static Log log = LogFactory.getLog(WorldFactory.class.getName());


	public World createWorld() {

		try {

			File tempFile = generateTempFile("port", "/scenario/port.properties");

			Configurations configs = new Configurations();
			Configuration config = configs.properties(tempFile);

			World world = new World();

			//create all the ports
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
			


			//add to the world
			world.setPorts(ports);

			//generate the contracts
			for (Port port : ports) {
				port.generateContracts();
			}

			//prepare the route
			tempFile = generateTempFile("grid", "/scenario/grid.map");
			Grid grid = GridUtils.loadFromFile(tempFile);
			PathFinding finder =  new AStar();
			Cell source;
			Cell destination;			
			
			//generate the routes for all the ports
			for (Port port : ports) {
				source = new Cell(port.getCooridnate().getX(), port.getCooridnate().getY());
				List<Port> connectedPorts = world.getConnectedPorts(port);
				for (Port connected : connectedPorts) {
					destination = new Cell(connected.getCooridnate().getX(), connected.getCooridnate().getY());					
					finder.search(grid, source, destination);
					grid.resetCells();
				}
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
