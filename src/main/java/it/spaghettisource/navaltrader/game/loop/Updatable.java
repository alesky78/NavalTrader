package it.spaghettisource.navaltrader.game.loop;

public interface Updatable {
	
	
	public void update(int minutsPassed, boolean isNewDay, boolean isNewWeek, boolean isNewMonth);

}
