package it.spaghettisource.navaltrader.game;

import it.spaghettisource.navaltrader.game.factory.WorldFactory;
import it.spaghettisource.navaltrader.game.loop.LoopManager;
import it.spaghettisource.navaltrader.game.model.Company;
import it.spaghettisource.navaltrader.game.model.GameTime;
import it.spaghettisource.navaltrader.game.model.World;

public class GameManager {

	private GameData gameData; 
	private LoopManager loopManager;


	//TODO user should chose company and start port
	public void newGame(String companyName) {

		//TODO initialization of the game and selection of scenario
		
		GameTime gameTime = new GameTime();
		
		WorldFactory factory = new WorldFactory();
		World world = 	factory.createWorld();
		
		Company company = new Company(companyName, "porto A", 4000000,world);

		gameData = new GameData(company,gameTime,world);
		loopManager = new LoopManager(gameData);
		
	}
	
	public GameData getGameData() {
		return gameData;
	}

	public LoopManager getLoopManager() {
		return loopManager;
	}

	public void startGame() {
		loopManager.startLoopManagerThread();
	}
	
	public void quitGame() {
		if(loopManager!=null) {
			loopManager.shutdown();			
		}

	}	

}
