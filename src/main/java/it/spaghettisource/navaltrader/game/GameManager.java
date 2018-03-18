package it.spaghettisource.navaltrader.game;

import it.spaghettisource.navaltrader.game.factory.GameFactory;
import it.spaghettisource.navaltrader.game.loop.LoopManager;
import it.spaghettisource.navaltrader.game.model.Company;
import it.spaghettisource.navaltrader.game.model.GameTime;
import it.spaghettisource.navaltrader.game.model.Port;
import it.spaghettisource.navaltrader.game.model.World;
import it.spaghettisource.navaltrader.ui.event.InboundEventQueue;

public class GameManager {

	private GameData gameData; 
	private LoopManager loopManager;


	public void startNewGame(String companyName) {

		//TODO initialization of the game, selection of scenario and port and make a progress bar wiht thread
		
		GameTime gameTime = new GameTime();
		
		GameFactory factory = new GameFactory();
		World world = 	factory.createWorld();
		
		
		Port startPort = world.getPortByName("Gioia Tauro");
		Company company = new Company(companyName, startPort, 40000000,world);

		gameData = new GameData(company,gameTime,world);
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

	}	

}
