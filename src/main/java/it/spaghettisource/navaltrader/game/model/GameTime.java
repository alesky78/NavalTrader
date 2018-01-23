package it.spaghettisource.navaltrader.game.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//TODO pass to new java.time api
public class GameTime {
	
	static Log log = LogFactory.getLog(GameTime.class.getName());
	
	private DateFormat fullDateFormat;	
	private DateFormat dateFormat;	
	private Calendar calendar;
	
	public GameTime() {
		super();
		fullDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");	
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");	
		calendar = Calendar.getInstance();
		try {
			calendar.setTime(fullDateFormat.parse("01/01/2000 00:00"));
		} catch (ParseException e) {
			log.error(e);
		}		
	}	
	
	
	public void addMinuts(int toAdd){
		calendar.add(Calendar.MINUTE, toAdd);
	}


	public String getFullDate(){
		return fullDateFormat.format(calendar.getTime());
	}
	
	public String getDate(){
		return dateFormat.format(calendar.getTime());
	}	
	
	
	
	
}
