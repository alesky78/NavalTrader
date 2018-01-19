package it.spaghettisource.navaltrader.game;

import java.io.Serializable;

import it.spaghettisource.navaltrader.game.model.Bank;
import it.spaghettisource.navaltrader.game.model.Company;
import it.spaghettisource.navaltrader.game.model.GameTime;


public class GameData implements Serializable {

	private Company company;
	private GameTime time;
	private Bank bank;
	
	public GameData(Company company,GameTime time,Bank bank) {
		super();
		this.company = company;
		this.time = time;
		this.bank = bank;
	}

	public Company getCompany() {
		return company;
	}

	public GameTime getTime() {
		return time;
	}

	public Bank getBank() {
		return bank;
	}

		
}
