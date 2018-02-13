package it.spaghettisource.navaltrader.ui.event;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * A queue that restricts the number of inbound log events that get sent to the event manager to
 * prevent slower machines from being swamped by log events.
 * <p>
 * The configuration file parameter: <code>logview4j.events.per.second</code> determines how many
 * log events are removed from this queue per second
 */
public class InboundEventQueue {

	static Log log = LogFactory.getLog(InboundEventQueue.class.getName());

	private static InboundEventQueue instance;

	private ConcurrentLinkedQueue<Event> linkedQueue = new ConcurrentLinkedQueue<Event>();
	private InboundEventQueuePublisher processor = null;

	private long millisBetweenUpdates = 50;

	public static InboundEventQueue getInstance(){
		if(instance==null){
			instance = new InboundEventQueue();
		}
		return instance;
	}

	private InboundEventQueue() {
		processor = new InboundEventQueuePublisher();	
	}

	public void startQueuePublisher() {
		processor.startThread();
		log.info("start inboud event queue");	
	}	

	public void stopQueuePublisher(){
		processor.stopThread();
		linkedQueue = new ConcurrentLinkedQueue<Event>();		
		log.info("stop inboud event queue");		
	}	

	public void put(Event event) {
		log.debug("add new event in the queue:"+event.getEventType());			
		linkedQueue.add(event);
	}

	private Event poll() {
		return linkedQueue.poll();
	}
	

	public boolean isEmpty() {
		return linkedQueue.isEmpty();
	}




	/**
	 * processor from the inbounds event
	 * this class is responsible to trigger the execution of the events
	 *
	 */
	private class InboundEventQueuePublisher implements Runnable {

		private boolean processMultypleEventInThisThread;	//TODO this variable is a test, im not sure that work in this way, in case set false to return previous implementation

		private boolean shutdown;
		Event event = null;
		private List<Event> eventsToProcess;

		public InboundEventQueuePublisher() {
			shutdown = false;
			processMultypleEventInThisThread = true;			
			eventsToProcess = new ArrayList<Event>();
		}


		public void stopThread(){
			shutdown = true;
		}

		public void startThread(){
			shutdown = false;
			Thread thread = new Thread(processor);
			thread.setDaemon(true);
			thread.start();			
		}		

		/**
		 * Fires this event in a new thread
		 * @param loggingEvent the event to fire
		 */
		private void processEvent() {
			event = null;
			event = poll();
			if(event!=null){
				EventPublisher.getInstance().fireEvent(event);					
			}
		}

		/**
		 * Fires all the events directly by this thread wihtout create a new one
		 * @param loggingEvent the event to fire
		 */
		private void processEvents() {
			while(!isEmpty()) {
				eventsToProcess.add(poll());
			}

			if(!eventsToProcess.isEmpty()){
				for (Event event : eventsToProcess) {
					if(EventPublisher.getInstance().hasListner(event)) {
						EventPublisher.getInstance().doFireEventForEachListner(event);						
					}

				}
				eventsToProcess.clear();				
			}
		}


		/**
		 * Grab events from the queue not processing events faster than this
		 * storing events when paused
		 *
		 * */
		public void run() {

			long lastPass = 0;
			long now = 0;
			long elapsed = 0;

			while (!shutdown) {


				lastPass = System.currentTimeMillis();				

				if(processMultypleEventInThisThread) {
					processEvents();
				}else {
					processEvent();					
				}


				now = System.currentTimeMillis();

				elapsed = now - lastPass;

				try {
					if (elapsed < millisBetweenUpdates) {
						// Sleep for the remainder of the wait time
						Thread.sleep(millisBetweenUpdates - elapsed);
					}
				}
				catch (InterruptedException e) {
					log.error("error sleeping queue thread ",e);
				}

			}
		}




	}

}
