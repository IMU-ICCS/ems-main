package eu.melodic.dlms;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
	private String username;
	private String password;

}
