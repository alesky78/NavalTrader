package it.spaghettisource.navaltrader.game;

import java.io.Serializable;

import it.spaghettisource.navaltrader.game.model.Bank;
import it.spaghettisource.navaltrader.game.model.Company;
import it.spaghettisource.navaltrader.game.model.GameTime;
import it.spaghettisource.navaltrader.game.model.ShipBroker;

public class GameData implements Serializable {

	private Company company;
	private GameTime time;
	private Bank bank;
	private ShipBroker shipBroker;
	
	public GameData(Company company,GameTime time,Bank bank,ShipBroker shipBroker) {
		super();
		this.company = company;
		this.time = time;
		this.bank = bank;
		this.shipBroker = shipBroker;
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

	public ShipBroker getShipBroker() {
		return shipBroker;
	}
		
}
