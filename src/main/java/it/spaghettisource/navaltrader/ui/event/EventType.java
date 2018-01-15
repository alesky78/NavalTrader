package it.spaghettisource.navaltrader.ui.event;


/**
 * The list of possible events
 */
public class EventType {

	//pre builds event
	public static final EventType EXAMPLE_EVENT = new EventType("EXAMPLE_EVENT");	

	private String id = "UNDEFINED";
	
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
