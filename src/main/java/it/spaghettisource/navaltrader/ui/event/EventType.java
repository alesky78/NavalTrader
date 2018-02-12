package it.spaghettisource.navaltrader.ui.event;


/**
 * The list of possible events
 */
public class EventType {

	//pre builds event
	public static final EventType FINANCIAL_EVENT = new EventType("FINANCIAL_EVENT");					//company or ship finance situation change
	public static final EventType BUDGET_EVENT = new EventType("BUDGET_EVENT");							//company budget change
	public static final EventType RATING_EVENT = new EventType("RATING_EVENT");							//company rating change
	public static final EventType BANK_CHANGE_EVENT = new EventType("BANK_CHANGE_EVENT");				//interest or max accepted amount for load change
	public static final EventType LOAN_EVENT = new EventType("LOAN_EVENT");								//loan change: added, removed, repair
	public static final EventType BUY_SHIP_EVENT = new EventType("BUY_SHIP_EVENT");						//list of ship change
	public static final EventType SELL_SHIP_EVENT = new EventType("SELL_SHIP_EVENT");					//list of ship change
	public static final EventType SHIP_STATUS_CHANGE_EVENT = new EventType("SHIP_STATUS_CHANGE_EVENT");	//list of ship change
	public static final EventType SHIP_FUEL_CHANGE_EVENT = new EventType("SHIP_FUEL_CHANGE_EVENT");		//list of ship change		
	public static final EventType SHIP_HULL_CHANGE_EVENT = new EventType("SHIP_HULL_CHANGE_EVENT");		//list of ship change
	public static final EventType CONTRACT_COMPLETED_EVENT = new EventType("CONTRACT_COMPLETED_EVENT");	//contract completed event	
	
	

	private String id;
	
	private EventType(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	/**
	 * Checks ids for equality
	 * @param other the other id to check
	 * @return true if the ids are equal
	 */
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}

		if (other instanceof EventType) {
			return ((EventType) other).id.equals(id);
		}

		return false;
	}


	public String toString(){
		return id;
	}





}
