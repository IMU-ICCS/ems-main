package eu.melodic.dlms;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapping class for the MySQL metrics properties in the application.properties file. The mapping is done automatically by Spring.
 */
@Component
@ConfigurationProperties(prefix = "metrics")
public class MetricsProperties {

	private List<String> mysql = new ArrayList<>();

	public List<String> getMysql() {
		return mysql;
	}

	public void setMysql(List<String> mysql) {
		this.mysql = mysql;
	}
}
