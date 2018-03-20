package it.spaghettisource.navaltrader.game.factory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrade.pathfinding.AStar;
import it.spaghettisource.navaltrade.pathfinding.Grid;
import it.spaghettisource.navaltrade.pathfinding.GridUtils;
import it.spaghettisource.navaltrade.pathfinding.PathFinding;
import it.spaghettisource.navaltrader.game.model.Market;
import it.spaghettisource.navaltrader.game.model.Port;
import it.spaghettisource.navaltrader.game.model.Product;
import it.spaghettisource.navaltrader.game.model.Route;
import it.spaghettisource.navaltrader.game.model.World;
import it.spaghettisource.navaltrader.geometry.Mathematic;
import it.spaghettisource.navaltrader.geometry.Point;
import it.spaghettisource.navaltrader.ui.ImageIconFactory;
import it.spaghettisource.navaltrader.ui.component.PanelDrawRoute;

public class GameFactory {

	private static Log log = LogFactory.getLog(GameFactory.class.getName());


	//TODO rewrite completely is a uniqe bunch of code
	public World createWorld() {

		try {

			Configuration config;
			
			//create the world
			File tempFile = generateTempFile("world", "/scenario/world.properties");
			config = createConfig(tempFile);			
			World world = new World();
			world.setGridScale(config.getInt("gridscale"));
			

			//create the ports
			tempFile = generateTempFile("port", "/scenario/port.properties");
			config = createConfig(tempFile);			
			List<Port> ports = new ArrayList<Port>();
			Port actual;

			Point cooridnate = null;
			for (int i = 0; i <config.getInt("ports"); i++) {
				cooridnate = new Point(config.getInt("port"+i+".xpos"), config.getInt("port"+i+".ypos"));
				actual = new Port(world,
								 cooridnate,
								 config.getString("port"+i+".name"), 
								 config.getDouble("port"+i+".dailyFeeCost"),
								 config.getDouble("port"+i+".castOffCost"),
								 config.getInt("port"+i+".shipSizeAccepted"),
								 config.getDouble("port"+i+".loadTeuPerHour"),
								 config.getDouble("port"+i+".fuelPrice")); 
				ports.add(actual);				
			}
			
			//add the port to the world
			world.setPorts(ports);

			//prepare the route
			tempFile = generateTempFile("grid", "/scenario/grid.map");
			Grid grid = GridUtils.loadFromFile(tempFile);
			
			world.setGridSize(grid.getWidth(), grid.getHeight());
			world.setWorldMap(ImageIconFactory.getImageBy("/scenario/world.png"));
			
			PathFinding finder =  new AStar();	
			Point[] path;
			Route route;
			List<Port> connectedPorts;
			
			
			//generate the routes for all the ports
			for (Port port : ports) {
				connectedPorts = world.getConnectedPorts(port);
				for (Port destination : connectedPorts) {
					path = finder.search(grid, port.getCooridnate(), destination.getCooridnate(),true);
					route = new Route(destination, world.getGridScale(), path);
					port.addRoute(route);
					grid.resetCells();
				}
			}

			//now transform the port coordinate in scale coordinate
			for (Port port : ports) {
				 port.setCooridnate(Mathematic.scale(port.getCooridnate(), world.getGridScale())); 
			}
			
			
			//load all the product
			tempFile = generateTempFile("market", "/scenario/market.properties");
			config = createConfig(tempFile);			
			List<Product> products = new ArrayList<Product>();
			Product product;			
			for (int i = 0; i <config.getInt("products"); i++) {
				
				product = new Product( config.getInt("product"+i+".id"),  
										config.getString("product"+i+".name"), 
										config.getDouble("product"+i+".price"), 
										config.getInt("product"+i+".dwt")); 
				products.add(product);				
			}			
			
			
			//create the market for each port
			String[] demandString;
			int[] demand;			
			String[] supplyString;
			int[] supply;
			
			int dayContractRegeneration = 0;
			Market market;
			Port portOwnerOfMarket;			
			for (int i = 0; i <config.getInt("ports"); i++) {
				portOwnerOfMarket = world.getPortByName(  config.getString("market"+i+".port") );
				log.debug("create market for:"+portOwnerOfMarket.getName());
				
				demandString = config.getStringArray("market"+i+".demand");
				supplyString = config.getStringArray("market"+i+".supply");
				dayContractRegeneration  = config.getInt("market"+i+".dayContractRegeneration"); 
				demand = Arrays.asList(demandString).stream().mapToInt(Integer::parseInt).toArray();
				supply= Arrays.asList(supplyString).stream().mapToInt(Integer::parseInt).toArray();				
				market = new Market(portOwnerOfMarket,products, demand, supply,dayContractRegeneration);

				portOwnerOfMarket.setMarket(market);
				
			}
			
			//generate the contracts
			for (Port port : ports) {
				port.getMarket().generateContracts();
			}
			
			return world;

		}catch (Exception e) {
			log.error("error creating the world", e);
			return null;
		}

	}


	private Configuration createConfig(File tempFile) throws ConfigurationException {
		FileBasedConfigurationBuilder<PropertiesConfiguration> builder =
			    new FileBasedConfigurationBuilder<PropertiesConfiguration>(PropertiesConfiguration.class)
			    	.configure(new Parameters().properties()
			    				.setFile(tempFile)
			    				.setThrowExceptionOnMissing(true)
			    				.setListDelimiterHandler(new DefaultListDelimiterHandler(';')));
			PropertiesConfiguration config = builder.getConfiguration();
		return config;
	}


	private File generateTempFile(String tempFileName, String sourceFile) throws IOException, FileNotFoundException {
		File tempFile = File.createTempFile(tempFileName, null);
		tempFile.deleteOnExit();
		FileOutputStream out = new FileOutputStream(tempFile);
		IOUtils.copy(GameFactory.class.getResourceAsStream(sourceFile), out);
		out.close();
		return tempFile;
	}


}
