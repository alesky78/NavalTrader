package it.spaghettisource.navaltrader.game;

import java.io.Serializable;

import it.spaghettisource.navaltrader.game.model.Bank;
import it.spaghettisource.navaltrader.game.model.Company;
import it.spaghettisource.navaltrader.game.model.GameTime;
import it.spaghettisource.navaltrader.game.model.World;


public class GameData implements Serializable {

	private Company company;
	private World world;
	private GameTime time;
	
	public GameData(Company company,GameTime time,World world) {
		super();
		this.company = company;
		this.time = time;
		this.world = world;
	}

	public Company getCompany() {
		return company;
	}

	public GameTime getGameTime() {
		return time;
	}

	public World getWorld() {
		return world;
	}
		
}
