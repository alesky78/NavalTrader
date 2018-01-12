package it.spaghettisource.navaltrader.game.loop;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.spaghettisource.navaltrader.game.model.GameTime;

public class LoopManager implements  Runnable {

	static Log log = LogFactory.getLog(LoopManager.class.getName());
	
	private GameTime timeData;
	private List<Updatable> entities;
	
	private boolean pause;
	private boolean shutdown;	
	private int timeSleep;
	private int timePass;		

	public LoopManager() {
		super();
		timeData = new GameTime();
		pause = false;
		shutdown = false;
		timeSleep = 10;
		timePass = 20;		
		entities = new ArrayList<Updatable>();
	}

	public void run() {

		while(!shutdown){
			
			try {
				Thread.currentThread();
				Thread.sleep(timeSleep);
				
				if(!pause){
					timeData.addMinuts(timePass);
					log.debug("time:"+timeData.getTime());			
					for (Updatable updatable : entities) {
						updatable.update(timePass);
					}
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
	
	public void addUpdatable(List<Updatable> toAdd){
		entities.addAll(toAdd);
	}
	
	public static void main(String[] args){
		LoopManager manager = new LoopManager();
		
		(new Thread(manager)).start();
		
	}
	
}
