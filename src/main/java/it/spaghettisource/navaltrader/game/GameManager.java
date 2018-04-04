package it.spaghettisource.navaltrader.game;

import it.spaghettisource.navaltrader.game.factory.GameFactory;
import it.spaghettisource.navaltrader.game.loop.LoopManager;
import it.spaghettisource.navaltrader.game.model.Company;
import it.spaghettisource.navaltrader.game.model.GameTime;
import it.spaghettisource.navaltrader.game.model.Port;
import it.spaghettisource.navaltrader.game.model.World;
import it.spaghettisource.navaltrader.ui.event.EventPublisher;
import it.spaghettisource.navaltrader.ui.event.InboundEventQueue;

public class GameManager {

	private GameData gameData; 
	private LoopManager loopManager;


	public void startNewGame(GameSetting config, String companyName, String portName) {
		
		GameTime gameTime = new GameTime();
		
		GameFactory factory = new GameFactory();
		World world = 	factory.createWorld();
		
		Port startPort = world.getPortByName(portName);
		Company company = new Company(companyName, startPort, 40000000,world);

		gameData = new GameData(config,company,gameTime,world);
		loopManager = new LoopManager(gameData);
		
		loopManager.startLoopManagerThread();
		InboundEventQueue.getInstance().startQueuePublisher();			
		
	}
	
	public GameData getGameData() {
		return gameData;
	}

	public LoopManager getLoopManager() {
		return loopManager;
	}

	
	public void quitGame() {
		if(loopManager!=null) {
			loopManager.shutdown();			
		}
		
		InboundEventQueue.getInstance().stopQueuePublisher();		
		EventPublisher.getInstance().clearAllListeners();		

	}	

}
