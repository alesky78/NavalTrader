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
	private int newDay;	
	private boolean dayChanged;
	private int actualMonth;
	private int newMonth;	
	private boolean monthChanged;


	public GameTime() {
		super();
		fullDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");	
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");	
		actualDate = Calendar.getInstance();
		try {
			actualDate.setTime(fullDateFormat.parse("01/01/2000 00:00"));
			dayChanged = false;
			monthChanged = false;			
			actualDay = actualDate.get(Calendar.DAY_OF_MONTH);
			actualMonth = actualDate.get(Calendar.MONTH);			

		} catch (ParseException e) {
			log.error(e);
		}		
	}	


	public void addMinuts(int toAdd){

		actualDate.add(Calendar.MINUTE, toAdd);

		newDay = actualDate.get(Calendar.DAY_OF_MONTH);
		newMonth = actualDate.get(Calendar.MONTH);		

		//check day
		if(actualDay != newDay){
			actualDay = newDay;
			dayChanged = true;
		}else{
			dayChanged = false;
		}

		//check month
		if(actualMonth != newMonth){
			actualMonth = newMonth;
			monthChanged = true;
		}else{
			monthChanged = false;
		}				
	}


	public boolean isDayChanged(){
		return dayChanged;
	}	
	
	public boolean isMonthChanged(){
		return monthChanged;
	}		


	public String getFullDate(){
		return fullDateFormat.format(actualDate.getTime());
	}

	public String getDate(){
		return dateFormat.format(actualDate.getTime());
	}	




}
