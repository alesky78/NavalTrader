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
	private int timePass;		

	private Thread owner;
	
	private InternalFrameTimeSimulation clockUI;

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
 
					gameTime.addMinuts(timePass);				
					
					//log.debug("time:"+gameTime.getFullDate()+" new date:"+isNewDate+" new week:"+isNewWeek+" new month:"+isNewMonth );
					//TODO remove log for test

					company.update(timePass,isNewDate,isNewWeek,isNewMonth);
					
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
