package it.spaghettisource.navaltrader.game.loop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.GameData;
import it.spaghettisource.navaltrader.game.model.Company;
import it.spaghettisource.navaltrader.game.model.GameTime;
import it.spaghettisource.navaltrader.game.model.World;

public class LoopManager implements  Runnable {

	static Log log = LogFactory.getLog(LoopManager.class.getName());

	private GameData gameData;

	private boolean pauseByUser;
	private boolean pauseByGame;	
	private boolean shutdown;	
	private int timeSleep;
	private double timeSleepMultiplicator;	
	private int timePassInMinuts;		

	private Thread loopThread;

	public LoopManager(GameData gameData) {
		super();
		this.gameData = gameData;
		pauseByUser = false;
		pauseByGame	= false;
		shutdown = false;
		timeSleepMultiplicator = 1;
		timeSleep = 10;		
		timePassInMinuts = 1;
	}

	public void startLoopManagerThread() {
		loopThread = new Thread(this);
		loopThread.start();
	}


	public void goFast() {
		timePassInMinuts = timePassInMinuts+1;
	}
	
	public void goFast(int timePassedPerTick) {
		timePassInMinuts = timePassedPerTick;
	}

	public void goSlow() {
		timePassInMinuts = timePassInMinuts-1;
		if(timePassInMinuts<1){
			timePassInMinuts = 1;	
		}
	}


	public void setPauseByUser(boolean toSet){
		log.debug("user set pause: "+toSet);
		pauseByUser = toSet;
	}
	

	public void setPauseByGame(boolean pauseByGame) {
		log.debug("game set pause: "+pauseByGame);		
		this.pauseByGame = pauseByGame;
	}

	public void shutdown(){
		shutdown = true;
	}

	public void run() {

		GameTime gameTime = gameData.getGameTime(); 	
		Company company = gameData.getCompany();
		World world = gameData.getWorld();
		boolean isNewDate;
		boolean isNewWeek;		
		boolean isNewMonth;		
		
		while(!shutdown){

			isNewDate = gameTime.isDayChanged();
			isNewWeek = gameTime.isWeekChanged();
			isNewMonth = gameTime.isMonthChanged();
			
			try {
				Thread.currentThread();
				Thread.sleep((long)(timeSleep*timeSleepMultiplicator));

				if(!pauseByUser && !pauseByGame){
 
					gameTime.addMinuts(timePassInMinuts);				
					
					world.update(timePassInMinuts, isNewDate, isNewWeek, isNewMonth);
					company.update(timePassInMinuts,isNewDate,isNewWeek,isNewMonth);
					
				}

			} catch (InterruptedException e) {
				log.error("error sleeping the game timer thread",e);
				e.printStackTrace();
			}

		}

	}	
	
}
