
package it.spaghettisource.navaltrader.ui.event;

/**
 * An event class for transmitting events
 */
public class Event {

  /**
   * The event id
   */
  private final EventType eventType;

  
  /**
   * Constructs a new event with the requested event id
   * @param eventId the event id for this event
   */
  public Event(EventType type) {
    this.eventType = type;
  }


  /**
   * Fetches the event id
   * @return the eventId for this event
   */
  public EventType getEventType() {
    return eventType;
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
