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

	private boolean pause;
	private boolean shutdown;	
	private int timeSleep;
	private double timeSleepMultiplicator;	
	private int timePassInMinuts;		

	private Thread loopThread;
	
	private InternalFrameTimeSimulation clockUI;

	public LoopManager(GameData gameData) {
		super();
		this.gameData = gameData;
		pause = false;
		shutdown = false;
		timeSleep = 2000;	//TODO review a valid amount for the time 2000 is to much i think 1000
		timeSleepMultiplicator = 1;
		timePassInMinuts = 20;		
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


	public void pause(boolean toSet){
		pause = toSet;
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

				if(!pause){
 
					gameTime.addMinuts(timePassInMinuts);				
					
					//log.debug("time:"+gameTime.getFullDate()+" new date:"+isNewDate+" new week:"+isNewWeek+" new month:"+isNewMonth );
					//TODO remove log for test

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
