/*
 * Melodic EU Project
 * DLMS WebService
 * DMS Service Application
 * @author: ferox
 */

package eu.melodic.dlms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Application class for the DLMS agent.
 */
@SpringBootApplication
public class DlmsAgentApplication {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DlmsAgentApplication.class);

	private static final int DELAY_AFTER_STARTUP = 1000;
	private static final int CALL_INTERVAL = 60000;

	@Autowired
	private MetricsController metricsController;

	/**
	 * Main method for starting. No arguments needed for normal use.
	 */
	public static void main(String[] args) {
		SpringApplication.run(DlmsAgentApplication.class, args);
	}

	/**
	 * CommandLineRunner to start a timer directly after startup, which then collects metrics and publishes them via JMS.
	 */
	@Bean
	public CommandLineRunner run() {
		return args -> {
			String metricsRangeProperty = System.getProperties().getProperty("metricsRange");
			MetricsRange metricsRange = MetricsRange.valueOf(metricsRangeProperty);
			LOGGER.info("Application started with metricsRange {} set", metricsRangeProperty);

			String url = System.getProperties().getProperty("mode");
			LOGGER.info("Application started and URL {} identified", url);

			TimerTask timerTask = new TimerTask() {
				@Override
				public void run() {
					LOGGER.info("Running metrics collection for {}", url);

					switch(metricsRange) {
						case ALLUXIO: {
							metricsController.collectAlluxioMetrics(url);
							break;
						}
						case MY_SQL: {
							metricsController.collectMySqlMetrics();
							break;
						}
						case ALL: {
							metricsController.collectAlluxioMetrics(url);
							metricsController.collectMySqlMetrics();
							break;
						}
					}

					metricsController.sendMetrics();
				}
			};

			Timer timer = new Timer();
			timer.schedule(timerTask, DELAY_AFTER_STARTUP, CALL_INTERVAL);
			LOGGER.info("Started timer with delay=" + DELAY_AFTER_STARTUP / 1000 + " sec. and interval=" + CALL_INTERVAL / 1000 + " sec.");
		};
	}
}
