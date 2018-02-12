package it.spaghettisource.navaltrader.game.loop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.GameData;
import it.spaghettisource.navaltrader.game.model.Company;
import it.spaghettisource.navaltrader.game.model.GameTime;
import it.spaghettisource.navaltrader.ui.frame.InternalFrameTimeSimulation;

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
	
	private InternalFrameTimeSimulation clockUI;

	public LoopManager(GameData gameData) {
		super();
		this.gameData = gameData;
		pauseByUser = false;
		pauseByGame	= false;
		shutdown = false;
		timeSleepMultiplicator = 1;
		timeSleep = 2000;	//TODO review a valid amount for the time 2000 is to much i think 1000		
		timePassInMinuts = 20;		//TODO still is jumping, reduce this time
	}

	public void startLoopManagerThread() {
		loopThread = new Thread(this);
		loopThread.start();
	}


	public void goFast() {
		if(timeSleepMultiplicator>0.015625) {
			timeSleepMultiplicator = timeSleepMultiplicator / 2;
			loopThread.interrupt();
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

	public void setClockUI(InternalFrameTimeSimulation clockUI) {
		this.clockUI = clockUI;
	}

	public void run() {

		GameTime gameTime = gameData.getGameTime(); 	
		Company company = gameData.getCompany();
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
					
					company.update(timePassInMinuts,isNewDate,isNewWeek,isNewMonth);
					
					if(clockUI!=null){
						try{
							clockUI.updateTime(gameTime.getDate());				//here clockUI could be null for multhythread							
						}catch (Exception e) {}
	
					}


				}

			} catch (InterruptedException e) {
				log.error("error sleeping the game timer thread",e);
				e.printStackTrace();
			}

		}

	}	
	
}
