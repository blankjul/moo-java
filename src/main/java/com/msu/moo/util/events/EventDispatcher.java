package com.msu.moo.util.events;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.Queue;

/**
 * The core class of the event handling system.
 * <p/>
 * The EventDispatcher is stores a mapping of Events to IListeners. Arriving
 * Events are then forwarded to the fitting IListeners.
 */
public final class EventDispatcher {

	// singleton object
	private static final EventDispatcher INSTANCE = new EventDispatcher();
    private EventDispatcher() {}
    public static EventDispatcher getInstance() {
        return INSTANCE;
    }
    
	
	/**
	 * Logger to trigger the events or errors.
	 */
	private static final Logger logger = Logger.getLogger(EventDispatcher.class);
	private final Multimap<Class<?>, IListener<? extends IEvent>> events = LinkedListMultimap.create();

	/**
	 * Register an IListener for a specific Event class.
	 *
	 * @param event
	 *            The event class the Handler is registered for.
	 * @param IListener
	 *            The Handler to be registered.
	 * @param <T>
	 *            The type of the event.
	 */
	public <T extends IEvent> void register(final Class<T> event, final IListener<T> listener) {
		this.events.put(event, listener);
		logger.debug(String.format("Registered %s for event class %s.", listener, event.getClass()));
	}

	/**
	 * Invoke the processing of the given Event.
	 *
	 * @param event
	 *            The event to be processed.
	 * @param <T>
	 *            The event's type.
	 */
	public <T extends IEvent> void notify(final T event) {
		logger.trace(String.format("Received event of type %s.", event.getClass()));
		final Queue<Class<?>> eventObjects = inspectEventType(event);
		if (logger.isTraceEnabled()) {
			logger.trace(String.format("There are %d event types associated with the event of type %s: %s", eventObjects.size(), event.getClass().getName(),
					eventObjects.toString()));
		}
		for (Class<?> clazz : eventObjects) {
			handleEventInternal(clazz, event);
		}
	}

	/**
	 * Get all interfaces extending Event which are implemented by the given
	 * Event, plus the Event's class.
	 *
	 * @param event
	 *            The event to be examined.
	 * @param <T>
	 *            The event's type.
	 *
	 * @return A queue of Event classes.
	 */
	private <T extends IEvent> Queue<Class<?>> inspectEventType(final T event) {
		final Queue<Class<?>> queue = new LinkedList<>();
		queue.add(event.getClass());
		for (Class<?> c : event.getClass().getInterfaces()) {
			if (IEvent.class.isAssignableFrom(c)) {
				queue.add(c);
			}
		}
		return queue;
	}

	/**
	 * Internal event handling. This method retrieves the registered IListeners
	 * for the given event class and passes them the given event.
	 *
	 * @param clazz
	 *            The event class for which handlers shall be retrieved.
	 * @param event
	 *            The event to be passed to the handlers.
	 * @param <T>
	 *            The type of the event class
	 */
	private <T extends IEvent> void handleEventInternal(final Class<?> clazz, final T event) {
		for (IListener<? extends IEvent> listener : this.events.get(clazz)) {
			@SuppressWarnings("unchecked")
			final IListener<IEvent> eL = (IListener<IEvent>) listener;
			if (logger.isTraceEnabled()) {
				logger.trace(String.format("Delegating event of type %s to registered handler %s.", clazz, eL.getClass()));
			}
			eL.handle(event);
		}
	}

	/**
	 * Unregister all known event handlers.
	 */
	public void unregisterAll() {
		events.clear();
	}

}
