/*
 * Melodic EU Project
 * DLMS WebService
 * Data Source Repository
 * @author: ferox
 */


package eu.melodic.dlms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository provided and managed by Spring.
 */
@Repository("eventRepository")
public interface EventRepository extends JpaRepository<Event, Long> {

}
