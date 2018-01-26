package it.spaghettisource.navaltrader.game;

import it.spaghettisource.navaltrader.game.loop.LoopManager;
import it.spaghettisource.navaltrader.game.model.Company;
import it.spaghettisource.navaltrader.game.model.GameTime;
import it.spaghettisource.navaltrader.game.model.World;

public class GameManager {

	private GameData gameData; 
	private LoopManager loopManager;


	public void newGame(String companyName) {

		GameTime gameTime = new GameTime();		
		Company company = new Company(companyName, "porto A", 4000000);

		World world = new World();
		
		
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
