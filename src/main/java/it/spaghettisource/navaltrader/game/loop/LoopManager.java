package it.spaghettisource.navaltrader.game.loop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.GameData;
import it.spaghettisource.navaltrader.game.model.GameTime;
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
		timePass = 60;		
	}

	public void startLoopManagerThread() {
		owner = new Thread(this);
		owner.start();
	}


	public void goFast() {
		if(timeSleepMultiplicator>0.0625) {
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

		GameTime gameTime;
		boolean isNewDate;
		boolean isNewMonth;		
		
		while(!shutdown){

			gameTime = gameData.getTime(); 
			isNewDate = gameTime.isDayChanged();
			isNewMonth = gameTime.isMonthChanged();
			
			try {
				Thread.currentThread();
				Thread.sleep((long)(timeSleep*timeSleepMultiplicator));

				if(!pause){
 
					gameTime.addMinuts(timePass);
					isNewDate = gameTime.isDayChanged();					
					
					log.debug("time:"+gameData.getTime().getFullDate()+" new date:"+isNewDate );			

					gameData.getCompany().update(timePass,isNewDate,isNewMonth);

					gameData.getBank().update(timePass,isNewDate,isNewMonth);

				}

			} catch (InterruptedException e) {
				log.error("error sleeping the game timer thread",e);
				e.printStackTrace();
			}

		}

	}	
	
}
