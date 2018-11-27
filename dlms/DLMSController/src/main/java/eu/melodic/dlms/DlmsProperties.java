package eu.melodic.dlms;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapping class for the algorithm properties in the application.properties file. The mapping is done automatically by Spring.
 */
@Component
@ConfigurationProperties(prefix = "dlms")
@Getter
@Setter
public class DlmsProperties {

	private List<Algorithm> algorithms = new ArrayList<>();

}
