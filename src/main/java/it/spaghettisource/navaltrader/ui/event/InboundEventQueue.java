package it.spaghettisource.navaltrader.ui.event;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import EDU.oswego.cs.dl.util.concurrent.LinkedQueue;


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
	
	private LinkedQueue linkedQueue = new LinkedQueue();
	private InboundEventQueuePublisher processor = null;

	private long millisBetweenUpdates = 1000;
	
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
		linkedQueue = new LinkedQueue();		
		log.info("stop inboud event queue");		
	}	

	public void put(Event event) {
		try {
			log.debug("add new event in the queue:"+event.getEventType());			
			linkedQueue.put(event);
		}
		catch (InterruptedException e) {
			log.error("error putting event in the queue",e);
		}
	}

	private Event take() {
		Event event = null;

		try {
			event = (Event) linkedQueue.take();
		}
		catch (InterruptedException e) {
			log.error("error taking event in the queue",e);
		}

		return event;
	}

	private Event poll(long millis) {
		Event event = null;

		try {
			event = (Event) linkedQueue.poll(millis);
		}
		catch (InterruptedException e) {
			log.error("error polling event in the queue",e);
		}

		return event;
	}

	public boolean isEmpty() {
		return linkedQueue.isEmpty();
	}




	/**
	 * processor from the inbounds event queue
	 *
	 * this class is responsible to push the event
	 *
	 *
	 */
	private class InboundEventQueuePublisher implements Runnable {

		private boolean shutdown;

		public InboundEventQueuePublisher() {
			shutdown = false;
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
		 * Fires the event
		 * @param loggingEvent the event to fire
		 */
		protected void fireEvent(Event event) {
			EventPublisher.getInstance().fireEvent(event);
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
			
			Event event = null;

			while (!shutdown) {

				log.debug("check for a new event");
				
				lastPass = System.currentTimeMillis();				

				event = poll(millisBetweenUpdates);

				if(event!=null){
					fireEvent(event);					
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
