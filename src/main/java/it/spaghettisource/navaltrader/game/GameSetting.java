package it.spaghettisource.navaltrader.game;


/**
 * 
 * keep the configuration of the game settings
 * 
 * @author id837836
 *
 */
public class GameSetting {

	public final static String DIFF_EASY = "easy";
	public final static String DIFF_EASY_TXT = "This is for the player that don't like the challeng. The price of the contracts are high and a big amount of contract are generated, the expiration date of the contract are long and the game company strat wiht a big budget";	
	public final static String DIFF_MEDIUM = "medium";	
	public final static String DIFF_MEDIUM_TXT = "Balanced game. The price are in the average and the expiration date are standard, the startign budget is accetable";	
	public final static String DIFF_HARD = "hard";	
	public final static String DIFF_HARD_TXT = "This is a real challeng, only for expert players. The price of the contract are low and less contract are generated moreover the expiration date are short, probably a loan will be required to start";
	
	private boolean tutorialMode;	
	private String difficulty;

	public GameSetting(boolean tutorialMode, String difficulty) {
		super();
		this.tutorialMode = tutorialMode;
		this.difficulty = difficulty;
	}

	public boolean isTutorialMode() {
		return tutorialMode;
	}

	public String getDifficulty() {
		return difficulty;
	}

	
	public static String[] getDifficulties(){
		return new String[]{DIFF_EASY,DIFF_MEDIUM,DIFF_HARD};
	}
	
	
	
	
	
	
}
