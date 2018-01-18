package it.spaghettisource.navaltrader.game;

import it.spaghettisource.navaltrader.game.loop.LoopManager;
import it.spaghettisource.navaltrader.game.model.Bank;
import it.spaghettisource.navaltrader.game.model.Company;
import it.spaghettisource.navaltrader.game.model.GameTime;
import it.spaghettisource.navaltrader.game.model.ShipBroker;

public class GameManager {

	private GameData gameData; 
	private LoopManager loopManager;


	public void newGame(String companyName) {

		GameTime gameTime = new GameTime();
		ShipBroker shipBroker = new ShipBroker();		
		Company company = new Company(companyName, 4000000);
		Bank bank = new Bank(0.08);

		gameData = new GameData(company,gameTime,bank,shipBroker);
		loopManager = new LoopManager(gameData);
		
	}
	
	public GameData getGameData() {
		return gameData;
	}

	public void startGame() {
		(new Thread(loopManager)).start();
	}
	
	public void quitGame() {
		if(loopManager!=null) {
			loopManager.shutdown();			
		}

	}	

	
	public static void main(String[] args){
		GameManager manager = new GameManager();
		manager.newGame("test");
		manager.startGame();
		
	}
}
