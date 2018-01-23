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
	private double timeSleepMultiplicator;	
	private int timePass;		

	private Thread owner;

	public LoopManager(GameData gameData) {
		super();
		this.gameData = gameData;
		pause = false;
		shutdown = false;
		timeSleep = 2000;
		timeSleepMultiplicator = 1;
		timePass = 20;		
	}

	public void startLoopManagerThread() {
		owner = new Thread(this);
		owner.start();
	}


	public void goFast() {
		if(timeSleepMultiplicator>0.125) {
			timeSleepMultiplicator = timeSleepMultiplicator / 2;
			owner.interrupt();
		}
	}

	public void goSlow() {
		if(timeSleepMultiplicator<8) {
			timeSleepMultiplicator = timeSleepMultiplicator * 2;			
		}
	}

	public String getMultiplicator() {
		return  ""+1/timeSleepMultiplicator;
	}


	public void pause(boolean toSet){
		pause = toSet;
	}

	public void shutdown(){
		shutdown = true;
	}


	public void run() {

		while(!shutdown){

			try {
				Thread.currentThread();
				Thread.sleep((long)(timeSleep*timeSleepMultiplicator));

				if(!pause){

					gameData.getTime().addMinuts(timePass);
					log.debug("time:"+gameData.getTime().getFullDate());			

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
	
}
