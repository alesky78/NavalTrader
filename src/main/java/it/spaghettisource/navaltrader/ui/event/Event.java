
package it.spaghettisource.navaltrader.ui.event;

/**
 * An event class for transmitting events
 */
public class Event {

	/**
	 * The event id
	 */
	private final EventType eventType;
	private final Object source;

	public Event(EventType type,Object source) {
		this.eventType = type;
		this.source = source;
	}


	/**
	 * Fetches the event id
	 * @return the eventId for this event
	 */
	public EventType getEventType() {
		return eventType;
	}

	public Object getSource() {
		return source;
	}

	/**
	 * Returns this as a String
	 * @return this as a String
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[Event id=").append(getEventType().getId()).append("]");
		return buffer.toString();
	}
}
