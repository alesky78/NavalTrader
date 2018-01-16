package it.spaghettisource.navaltrader.ui.event;


/**
 * The list of possible events
 */
public class EventType {

	//pre builds event
	public static final EventType FINANCIAL_EVENT = new EventType("FINANCIAL_EVENT");	
	public static final EventType RATING_EVENT = new EventType("RATING_EVENT");	

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







}
