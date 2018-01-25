package it.spaghettisource.navaltrader.game;

import java.io.Serializable;

import it.spaghettisource.navaltrader.game.model.Bank;
import it.spaghettisource.navaltrader.game.model.Company;
import it.spaghettisource.navaltrader.game.model.GameTime;


public class GameData implements Serializable {

	private Company company;
	private GameTime time;

	
	public GameData(Company company,GameTime time) {
		super();
		this.company = company;
		this.time = time;
	}

	public Company getCompany() {
		return company;
	}

	public GameTime getGameTime() {
		return time;
	}
		
}
