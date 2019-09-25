package eu.melodic.dlms.utility.common;

import java.util.Collections;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Bean used as data interface between UtilityGenerator and DlmsController to
 * exchange algorithm results.
 */

@NoArgsConstructor // Exclusive constructor for mapping by the Spring RestTemplate.
@AllArgsConstructor // Constructor taking a map of Strings (CAMEL IDs) and double values (returned
					// by the algorithms).
@Getter
@Setter
public class UtilityMetrics {

	private Map<String, Double> results = Collections.emptyMap();

}
