package it.spaghettisource.navaltrader.ui.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingWorker;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Manages events for update the UI event and ensures that all events
 * are dispatched in an independent thread
 *
 */
public class EventPublisher {

	static Log log = LogFactory.getLog(EventPublisher.class.getName());
	
	private static EventPublisher instance;
	
	/**
	 * A map of listeners
	 */
	private Map<EventType, List<EventListener>> listeners = new HashMap<EventType, List<EventListener>>();
	
	
	private EventPublisher(){
	}
	
	public static EventPublisher getInstance(){
		if(instance==null){
			instance = new EventPublisher();
		}
		return instance;
	}
	

	/**
	 * Removes all listeners
	 */
	public void clearAllListeners() {
		listeners.clear();
	}

	/**
	 * Registers an event listener to its events of interest
	 * @param listener the event listener to register
	 */
	public void register(EventListener listener) {
		
		EventType [] eventsType = listener.getEventsOfInterest();

		if (eventsType == null) {
			return;
		}

		for (int i = 0; i < eventsType.length; i++) {
			List<EventListener> list = getListeners(eventsType[i]);
			list.add(listener);
		}
	}
	
	/**
	 * un-Registers an event listener to its events of interest
	 * @param listener the event listener to register
	 */
	public void unRegister(EventListener listener) {
		EventType [] eventsType = listener.getEventsOfInterest();

		if (eventsType == null) {
			return;
		}
		
		for (int i = 0; i < eventsType.length; i++) {
			List<EventListener> list = getListeners(eventsType[i]);
			list.remove(listener);
		}		
	}

	/**
	 * Fires an event to the registered listeners,
	 * @param event the event to fire
	 */
	public void fireEvent(Event event) {

		if(hasListner(event)){
			EventDispatcher dispatcher = new EventDispatcher(event);
			dispatcher.execute();			
		}
	}
	
	/**
	 * return true if there is a listner for this event
	 * @param event the event to fire
	 */
	public boolean hasListner(Event event) {
		if(!getListeners(event.getEventType()).isEmpty()){
			return true;
		}else {
			log.debug("there are not listener for "+event.getEventType());			
			return false;
		}
	}


	/**
	 * Get the listeners for the requested event id
	 * @param id the id to fetch listeners for
	 * @return the list of listeners
	 */
	private List<EventListener> getListeners(EventType id) {
		if (listeners.containsKey(id)) {
			return (List<EventListener>) listeners.get(id);
		}

		List<EventListener> listenerList = new ArrayList<EventListener>();

		listeners.put(id, listenerList);

		return listenerList;
	}
	

	/**
	 * fire the event for each register listener related to the specific event type
	 *
	 * @param event the event to dispatch
	 */
	protected void doFireEventForEachListner(Event event) {
		List<EventListener> listenersList = getListeners(event.getEventType());

		for (int i = 0; i < listenersList.size(); i++) {
			EventListener listener = (EventListener) listenersList.get(i);
			listener.eventReceived(event);
		}
	}	

	/**
	 * implementation of the dispatching thread
	 * isolated from the thread
	 *
	 */
	class EventDispatcher extends SwingWorker<Void, Void>{


		/**
		 * The event to dispatch
		 */
		protected final Event event;

		/**
		 * Creates the dispatcher witht eh event to dispatch
		 * @param event the event to dispatch
		 */
		public EventDispatcher(Event event) {
			super();
			this.event = event;
		}

		
		protected Void doInBackground() throws Exception {
			return null;
		}
		
		
		/**
		 * Executed by the Swing event dispatcher thread
		 */		
	    protected void done() {
	    	log.debug("process event: "+event.getEventType());	    	
			doFireEventForEachListner(event);
	    }

	}
}
