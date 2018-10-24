package eu.melodic.dlms;

import java.net.URI;
import java.util.List;

/**
 * Event service providing various functionality regarding events.
 */
public interface EventService {

	/**
	 * Registers (adds) a new event and makes it ready to be called.
	 * New events start in status IDLE.
	 * The event name must be unique among the other registered events.
	 */
	URI addEvent(Event event);

	/**
	 * Unregisters (removes) the event with the given ID.
	 * Throws an IdNotFoundException if no event exists with this ID.
	 */
	void deleteEventById(long id);

	/**
	 * Removes all events from the database.
	 */
	void deleteAllEvents();

	/**
	 * Returns the event with the given name (event names are unique).
	 * Throws an EventNotFoundException if no event with this name could be found. Note that the search for event names is NOT case sensitive.
	 */
	Event getEventByName(String eventName);

	/**
	 * Updates the event with the given ID with new values.
	 * Throws an IdNotFoundException if no event exists with this ID.
	 */
	void updateEvent(Event event, long id);

	/**
	 * Returns all events in the database.
	 */
	List<Event> getAllEvents();

}
