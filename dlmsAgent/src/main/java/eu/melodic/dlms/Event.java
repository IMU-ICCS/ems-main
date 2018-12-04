package eu.melodic.dlms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing events.
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
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

	public Event(String name) {
		this.name = name;
	}

}
