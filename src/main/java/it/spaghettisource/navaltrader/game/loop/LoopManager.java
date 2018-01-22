package it.spaghettisource.navaltrader.game.loop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.GameData;
import it.spaghettisource.navaltrader.game.model.Ship;

public class LoopManager implements  Runnable {

	static Log log = LogFactory.getLog(LoopManager.class.getName());
	
	private GameData gameData;
	
	private boolean pause;
	private boolean shutdown;	
	private int timeSleep;
	private int timePass;		

	public LoopManager(GameData gameData) {
		super();
		this.gameData = gameData;
		pause = false;
		shutdown = false;
		timeSleep = 2000;
		timePass = 20;		
	}

	public void run() {

		while(!shutdown){
			
			try {
				Thread.currentThread();
				Thread.sleep(timeSleep);
				
				if(!pause){
					
					gameData.getTime().addMinuts(timePass);
					log.debug("time:"+gameData.getTime().getTime());			

					for (Ship ship : gameData.getCompany().getShips()) {
						ship.update(timePass);
					}
					
					gameData.getCompany().update(timePass);
					
					gameData.getBank().update(timePass);
					
				}

			} catch (InterruptedException e) {
				log.error("error sleeping the game timer thread",e);
				e.printStackTrace();
			}
	
		}
	
	}
	
	public void pause(boolean toSet){
		pause = toSet;
	}

	public void shutdown(){
		shutdown = true;
	}

	public void setTimeSleep(int toSet){
		timeSleep = toSet;
	}

	public void setTimePass(int toSet){
		timePass = toSet;
	}

	
}
