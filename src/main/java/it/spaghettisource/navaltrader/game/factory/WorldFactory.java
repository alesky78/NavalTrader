package it.spaghettisource.navaltrader.game.factory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.model.Port;
import it.spaghettisource.navaltrader.game.model.World;

public class WorldFactory {

	private static Log log = LogFactory.getLog(WorldFactory.class.getName());


	public World createWorld() {

		try {
			
			Configurations configs = new Configurations();

			File tempFile = File.createTempFile("portlist", null);
			tempFile.deleteOnExit();
			FileOutputStream out = new FileOutputStream(tempFile);
			IOUtils.copy(WorldFactory.class.getResourceAsStream("/scenario/port.properties"), out);

			Configuration config = configs.properties(tempFile);

			//strat to create all the ports
			List<Port> ports = new ArrayList<Port>();
			Port actual;

			for (int i = 0; i <config.getInt("ports"); i++) {
				actual = new Port(config.getString("port"+i+".name"), 
						config.getDouble("port"+i+".dailyFeeCost"),
						config.getInt("port"+i+".classAccepted"), 
						config.getInt("port"+i+".dayContractRegeneration")); 
			}

			World world = new World();
			world.setPorts(ports);

			return world;

		}catch (Exception e) {
			log.error("error reading the configruation", e);
			return null;
		}

	}


}
