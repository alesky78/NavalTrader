package it.spaghettisource.navaltrader.ui.event;

/**
 * The interface that listeners must implement to receive events
 */
public interface EventListener {

  /**
   * Fired when an event is received
   * @param event the event
   */
  public void eventReceived(Event event);

  /**
   * Invoked by the Event manager
   * @return the events of interest to this class
   */
  public EventType [] getEventsOfInterest();
}
