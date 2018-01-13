package it.spaghettisource.navaltrader.game.model;

public class FinancialEntry {

	private int amount;
	private FinancialEntryType type;

	public FinancialEntry(int amount, FinancialEntryType type) {
		super();
		this.amount = amount;
		this.type = type;
	}

	public int getAmount() {
		return amount;
	}
	
	public FinancialEntryType getType() {
		return type;
	}
	
}
