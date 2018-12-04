package eu.melodic.dlms;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import eu.melodic.dlms.exception.EventNotFoundException;
import eu.melodic.dlms.exception.IdNotFoundException;
import eu.melodic.dlms.exception.InvalidEventStatusException;
import eu.melodic.dlms.exception.InvalidParameterException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of EventService.
 */
@Service("eventService")
@AllArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {

	private final EventRepository eventRepository;

	@Override
	public URI addEvent(Event event) {
		Event result = eventRepository.save(event);

		return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId()).toUri();
	}

	@Override
	public void deleteEventById(long id) {
		ensureIdParameterNotNegative(id);

		if(eventRepository.existsById(id)) {
			Event event = eventRepository.getOne(id);
			ensureEventStatusNotRunning(event);

			eventRepository.deleteById(id);
		}
		else {
			throw new IdNotFoundException(id);
		}
	}

	@Override
	public void deleteAllEvents() {
		eventRepository.deleteAll();
	}

	@Override
	public Event getEventByName(String eventName) {
		ensureStringParameterNotEmpty(eventName);

		Event findMe = new Event(eventName);
		findMe.setStatus(null); // EventStatus is set to IDLE on default, so we have to reset it to find any status...
		
		return eventRepository.findOne(Example.of(findMe))
				.orElseThrow(() -> new EventNotFoundException(eventName));
	}

	@Override
	public void updateEvent(Event event, long id) {
		if(eventRepository.existsById(id)) {
			event.setId(id);
			eventRepository.save(event);
		}
		else {
			throw new IdNotFoundException(id);
		}
	}

	@Override
	public List<Event> getAllEvents() {
		return eventRepository.findAll();
	}

	private void ensureEventStatusNotRunning(Event event) {
		if(EventStatus.RUNNING == event.getStatus()) {
			log.error("Event {} has running status", event.getName());
			throw new InvalidEventStatusException(event);
		}
	}

	private void ensureIdParameterNotNegative(long id) {
		if(id < 0) {
			throw new InvalidParameterException("Parameter must be >= 0");
		}
	}

	private void ensureStringParameterNotEmpty(String name) {
		if(name == null || name.trim().isEmpty()) {
			throw new InvalidParameterException("Parameter must not be empty");
		}
	}

}
