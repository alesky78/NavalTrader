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
	
	private Calendar actualDate;
	private int actualDay;	
	private boolean dayChanged;	
	
	
	public GameTime() {
		super();
		fullDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");	
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");	
		actualDate = Calendar.getInstance();
		try {
			actualDate.setTime(fullDateFormat.parse("01/01/2000 00:00"));
			dayChanged = false;
			actualDay = actualDate.get(Calendar.DAY_OF_MONTH);
		} catch (ParseException e) {
			log.error(e);
		}		
	}	
	
	
	public void addMinuts(int toAdd){

		actualDate.add(Calendar.MINUTE, toAdd);
		
		int newDate = actualDate.get(Calendar.DAY_OF_MONTH);
		if(actualDay != newDate){
			actualDay = newDate;
			dayChanged = true;
		}else{
			dayChanged = false;
		}		
	}
	

	public boolean isDayChanged(){
		return dayChanged;
	}	


	
	public String getFullDate(){
		return fullDateFormat.format(actualDate.getTime());
	}
	
	public String getDate(){
		return dateFormat.format(actualDate.getTime());
	}	
	
	
	
	
}
