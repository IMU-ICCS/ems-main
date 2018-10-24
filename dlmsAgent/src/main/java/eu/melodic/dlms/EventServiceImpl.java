package eu.melodic.dlms;

import eu.melodic.dlms.exception.EventNotFoundException;
import eu.melodic.dlms.exception.IdNotFoundException;
import eu.melodic.dlms.exception.InvalidEventStatusException;
import eu.melodic.dlms.exception.InvalidParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of EventService.
 */
@Service("eventService")
public class EventServiceImpl implements EventService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EventServiceImpl.class);

	@Autowired
	EventRepository eventRepository;

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
		Optional<Event> result = eventRepository.findOne(Example.of(findMe));
		if(result != null && result.isPresent()) {
			return result.get();
		}

		throw new EventNotFoundException(eventName);
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
			LOGGER.error("Event {} has running status", event.getName());
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
