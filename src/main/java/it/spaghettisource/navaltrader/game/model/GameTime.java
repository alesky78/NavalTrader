package it.spaghettisource.navaltrader.game.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

//TODO pass to new java.time api
public class GameTime {
	
	

	private DateFormat df;	
	private Calendar calendar;
	
	public GameTime() {
		super();
		df = new SimpleDateFormat("HH:mm:ss");	
		calendar = Calendar.getInstance();
	}	
	
	
	public void addMinuts(int toAdd){
		calendar.add(Calendar.MINUTE, toAdd);
	}


	
	public String getTime(){
		return df.format(calendar.getTime());
	}
	
	
	
	
}
