package eu.melodic.dlms;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * Mapping class for the MySQL metrics properties in the application.properties
 * file. The mapping is done automatically by Spring.
 */
@Component
@ConfigurationProperties(prefix = "metrics")
@Getter
@Setter
public class MetricsProperties {

	private List<String> mysql = new ArrayList<>();

}
