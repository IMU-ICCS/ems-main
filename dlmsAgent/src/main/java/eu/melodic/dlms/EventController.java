package eu.melodic.dlms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import eu.melodic.dlms.exception.InvalidEventStatusException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Webservice controller for the event service.
 */
@RestController
@Slf4j
@AllArgsConstructor
public class EventController {

	private final EventService eventService;

	/**
	 * Registers a new event and makes it ready to be called.
	 * The event name must be unique among the other registered events.
	 */
	@PostMapping(value = "/event")
	public ResponseEntity<Event> registerEvent(@RequestBody Event event) {
		URI location = eventService.addEvent(event);
		return ResponseEntity.created(location).build();
	}

	/**
	 * Unregisters the event with the given name.
	 * Throws an EventNotFoundException if no event with this name could be found. Note that the search for event names is NOT case sensitive.
	 */
	@DeleteMapping(value = "/event")
	public void unregisterEvent(String eventName) {
		Event event = eventService.getEventByName(eventName);
		eventService.deleteEventById(event.getId());
	}

	/**
	 * Removes all events from the database.
	 */
	@DeleteMapping(value = "/event/all")
	public void unregisterAllEvents() {
		eventService.deleteAllEvents();
	}

	/**
	 * Returns all events in the database.
	 */
	@GetMapping(value = "/event/all")
	public List<Event> getAllEvents() {
		return eventService.getAllEvents();
	}

	/**
	 * Returns the event with the given name.
	 * Throws an EventNotFoundException if no event with this name could be found. Note that the search for event names is NOT case sensitive.
	 */
	@GetMapping(value = "/event")
	public Event getEvent(String eventName) {
		return eventService.getEventByName(eventName);
	}

	/**
	 * Returns the current status of the event with the given name.
	 * Throws an EventNotFoundException if no event with this name could be found. Note that the search for event names is NOT case sensitive.
	 */
	@GetMapping(value = "/event/status")
	public EventStatus getEventStatus(String eventName) {
		Event event = eventService.getEventByName(eventName);
		return event.getStatus();
	}

	/**
	 * Triggers the event with the given name and executes the event's command.
	 * Returns a String with the command's resulting output or an empty String if there was no output.
	 * <p>
	 * Success can be determined by querying the event's status afterwards. A failed execution will have status ERROR, a successful run will have status FINISHED.
	 * Note that "successful" in this case means that the command could be called without the operating system complaining about anything and not that the command completely did what it was intended to do.<p>
	 * <p>
	 * Throws an EventNotFoundException if no event with this name could be found. Note that the search for event names is NOT case sensitive.<p>
	 * Throws an InvalidEventStatusException if the event's current status is RUNNING, as an event can only be running once at a time.
	 */
	@GetMapping(value = "/event/send")
	public String triggerEvent(String eventName) {
		Event event = eventService.getEventByName(eventName);

		ensureEventStatusIsNotRunning(event);
		log.info("Executing event {} with command {}", eventName, event.getCommand());

		String result = "";
		try {
			ProcessBuilder processBuilder = configureProcessBuilder(event);

			updateEventStatus(event, EventStatus.RUNNING);
			log.info("Event {} is starting now", eventName);
			Process process = processBuilder.start();

			StringBuilder output = new StringBuilder(100);
			int exitCode = process.waitFor();
			log.info("Event {} finished with code {}", eventName, exitCode);

			if(exitCode == 0) {
				updateEventStatus(event, EventStatus.FINISHED);
				extractProcessOutput(output, process.getInputStream());
			}
			else {
				updateEventStatus(event, EventStatus.ERROR);
				extractProcessOutput(output, process.getErrorStream());
			}

			result = output.toString();
			log.info(result);
		}
		catch(IOException | InterruptedException e) {
			log.error(e.getMessage(), e);
		}

		return result;
	}

	private void ensureEventStatusIsNotRunning(Event event) {
		if(EventStatus.RUNNING == event.getStatus()) {
			log.error("Event {} is currently running", event.getName());
			throw new InvalidEventStatusException(event);
		}
	}

	/**
	 * Configures a ProcessBuilder with the given Event's command.
	 * Note that this method currently is for Unix shells only.
	 */
	private ProcessBuilder configureProcessBuilder(Event event) {
		ProcessBuilder processBuilder = new ProcessBuilder();
		// this is Unix-only!
		processBuilder.command("sh", "-c", event.getCommand());
		return processBuilder;
	}

	private void updateEventStatus(Event event, EventStatus newStatus) {
		event.setStatus(newStatus);
		eventService.updateEvent(event, event.getId());
	}

	/**
	 * Extracts all the contents of the given InputStream and fills the StringBuilder with it.
	 */
	private void extractProcessOutput(StringBuilder output, InputStream stream) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		String line;
		while((line = reader.readLine()) != null) {
			output.append(line).append('\n');
		}
	}

}
