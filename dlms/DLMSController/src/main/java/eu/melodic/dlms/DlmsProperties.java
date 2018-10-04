package eu.melodic.dlms;

import eu.melodic.dlms.utilitygenerator.Algorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapping class for the algorithm properties in the application.properties file. The mapping is done automatically by Spring.
 */
@Component
@ConfigurationProperties(prefix = "dlms")
public class DlmsProperties {

	private List<Algorithm> algorithms = new ArrayList<>();

	public List<Algorithm> getAlgorithms() {
		return algorithms;
	}

	public void setAlgorithms(List<Algorithm> algorithms) {
		this.algorithms = algorithms;
	}
}
