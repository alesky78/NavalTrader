package it.spaghettisource.navaltrader.game;

import it.spaghettisource.navaltrader.game.loop.LoopManager;
import it.spaghettisource.navaltrader.game.model.Company;
import it.spaghettisource.navaltrader.game.model.GameTime;

public class GameManager {

	private GameData gameData; 
	private LoopManager loopManager;


	public void newGame(String companyName) {

		GameTime gameTime = new GameTime();
		Company company = new Company(companyName, 4000000);

		gameData = new GameData(company,gameTime);
		loopManager = new LoopManager(gameData);
		
	}


	public void startGame() {
		(new Thread(loopManager)).start();
	}

	public static void main(String[] args){
		GameManager manager = new GameManager();
		manager.newGame("test");
		manager.startGame();
		
	}
}
