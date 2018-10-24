package eu.melodic.dlms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Entity class representing events.
 */
@Entity
public class Event {

	@Id
	@GeneratedValue
	private Long id;

	/**
	 * Unique name of the event
	 */
	@Column(nullable = false, length = 250, unique = true)
	private String name;

	/**
	 * Command to be run when this event is called.
	 */
	@Column(nullable = false)
	private String command;

	/**
	 * Current status of this event.
	 */
	private EventStatus status = EventStatus.IDLE;

	public Event() {
	}

	public Event(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public EventStatus getStatus() {
		return status;
	}

	public void setStatus(EventStatus status) {
		this.status = status;
	}
}
