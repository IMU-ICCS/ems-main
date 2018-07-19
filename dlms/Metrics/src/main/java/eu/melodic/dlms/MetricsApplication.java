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
 * Application class for metrics collection.
 */
@SpringBootApplication
public class MetricsApplication {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MetricsApplication.class);

	private static final int DELAY_AFTER_STARTUP = 1000;
	private static final int CALL_INTERVAL = 60000;

	@Autowired
	private MetricsController metricsController;

	/**
	 * Main method for starting. No arguments needed for normal use.
	 */
	public static void main(String[] args) {
		SpringApplication.run(MetricsApplication.class, args);
	}

	/**
	 * CommandLineRunner to start a timer directly after startup, which then collects metrics and publishes them via JMS.
	 */
	@Bean
	public CommandLineRunner run() {
		return args -> {
			String url = System.getProperties().getProperty("mode");
			LOGGER.info("Application started and URL {} identified", url);

			TimerTask timerTask = new TimerTask() {
				@Override
				public void run() {
					LOGGER.info("Running metrics collection for {}", url);

					metricsController.collectMetrics(url);
					metricsController.sendMetrics();
				}
			};

			Timer timer = new Timer();
			timer.schedule(timerTask, DELAY_AFTER_STARTUP, CALL_INTERVAL);
			LOGGER.info("Started timer with delay=" + DELAY_AFTER_STARTUP / 1000 + " sec. and interval=" + CALL_INTERVAL / 1000 + " sec.");
		};
	}
}
