/*
 * Melodic EU Project
 * DLMS WebService
 * DMS Service Application
 * @author: ferox
 */

package eu.melodic.dlms;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.*;
import java.util.function.Supplier;

import eu.melodic.models.interfaces.dlms.Configuration;
import eu.melodic.models.interfaces.dlms.ConfigurationResponse;
import io.github.resilience4j.retry.IntervalFunction;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import eu.melodic.dlms.component.AlluxioParam;

/**
 * Application class for the DLMS agent.
 */
@SpringBootApplication
public class DlmsAgentApplication {
	private static final int DELAY_AFTER_STARTUP = 1000;
	private static final int CALL_INTERVAL = 60000;

	private static final Logger log = LoggerFactory.getLogger(DlmsAgentApplication.class);

	@Autowired
	private MetricsController metricsController;

	private static ApplicationContext context;

	/**
	 * Main method for starting. No arguments needed for normal use.
	 */
	public static void main(String[] args) {
		context = SpringApplication.run(DlmsAgentApplication.class, args);
	}

	/**
	 * CommandLineRunner to start a timer directly after startup, which then
	 * collects metrics and publishes them via JMS.
	 */
	@Bean
	public CommandLineRunner run() {
		return args -> {
			String url = System.getProperties().getProperty("mode"); //TODO "mode" --> "url"?? in the unixInstaller at cloudiator
			log.info("Application started and URL {} identified", url);

			String publicIp = System.getProperties().getProperty("ip.public");
			String webServiceUrl = System.getProperties().getProperty("webServiceUrl") + "/getConfiguration/" + publicIp;
			Map<List<String>, String> latencyConfigMap = new HashMap<>();

			final Optional<ConfigurationResponse> configurationResponse = Optional.ofNullable(getConfigurationResponse(webServiceUrl));

			if(configurationResponse.map(ConfigurationResponse::getConfigurations).map(List::size).orElse(0) > 0)
			{
				for(Configuration config: configurationResponse.get().getConfigurations()) {
					if(config.isLatencyConfiguration()) {
						log.info("Received Latency Configuration");
						log.info("Component Name: {}", config.getLatencyConfiguration().getComponentName());
						log.info("Component IP: {}", config.getLatencyConfiguration().getComponentIP());
						log.info("Component Cloud: {}", config.getLatencyConfiguration().getComponentCloud());
						log.info("Component Region: {}", config.getLatencyConfiguration().getComponentRegion());
						log.info("Agent Region: {}", config.getLatencyConfiguration().getAgentRegion());
						log.info("Agent Cloud: {}", config.getLatencyConfiguration().getAgentCloud());

						latencyConfigMap.put(
								Collections.unmodifiableList(Arrays.asList(
									config.getLatencyConfiguration().getComponentName(),
									config.getLatencyConfiguration().getComponentCloud()+"::"+config.getLatencyConfiguration().getComponentRegion()))
								,config.getLatencyConfiguration().getComponentIP());
						if(metricsController.getDlmsAgentRegion() == null) {
							metricsController.setDlmsAgentRegion(config.getLatencyConfiguration().getAgentRegion());
							metricsController.setDlmsAgentCSP(config.getLatencyConfiguration().getAgentCloud());
							log.info("agentRegion: {}; agentCSP: {}", metricsController.getDlmsAgentRegion(), metricsController.getDlmsAgentCSP());
						}
					}
				}
			} else {
				log.info("ConfigurationResponse was null or empty - exiting...");
				int exitCode = SpringApplication.exit(context, () -> -1);
				System.exit(exitCode);
			}

			log.debug("latencyConfigMap: {}", latencyConfigMap);

			TimerTask timerTask = new TimerTask() {
				@Override
				public void run() {
					log.info("Starting collecting network latencies");
					metricsController.collectNetworkLatency(latencyConfigMap);
					log.info("Finished collecting network latencies");
					metricsController.sendMetrics(null);
				}
			};

			Timer timer = new Timer();
			timer.schedule(timerTask, DELAY_AFTER_STARTUP, CALL_INTERVAL);
			log.info("Started timer with delay=" + DELAY_AFTER_STARTUP / 1000 + " sec. and interval="
					+ CALL_INTERVAL / 1000 + " sec.");
		};
	}

	private ConfigurationResponse getConfigurationResponse(String webServiceUrl) {
		log.debug("Using webServiceUrl: {}", webServiceUrl);

		IntervalFunction intervalFn = IntervalFunction.ofExponentialBackoff(800L, 1.5D);
		RetryConfig retryConfig = RetryConfig.custom()
				.maxAttempts(6)
				.retryExceptions(java.lang.Throwable.class)
				.intervalFunction(intervalFn)
				.build();
		Retry retry = Retry.of("call_dlms_ws_for_config", retryConfig);
		retry.getEventPublisher().onEvent(event -> log.error("Retry mechanism with Interval Function of type Exponential Backoff - EventType: {}; RetryName: {}; NumberOfRetryAttempts: {}; LastThrowable: {}"
				,event.getEventType().toString()
				,event.getName()
				,event.getNumberOfRetryAttempts()
				,event.getLastThrowable().getMessage()));

		Supplier<ConfigurationResponse> supplier = Retry.decorateSupplier(retry, () -> {
			try {
				return callDLMSWSforConfiguration(webServiceUrl);
			} catch (URISyntaxException e) {
				log.error("URI syntax is not correct, error: {}", e.getMessage());
				return null;
			}
		});

		try {
			return supplier.get();
		} catch (Exception e) {
			log.error("Didn't manage to call DLMS-WS for configuration, error: {}", e.getMessage());
			return null;
		}
	}

	private ConfigurationResponse callDLMSWSforConfiguration(String webServiceUrl) throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplateBuilder()
				.setConnectTimeout(Duration.ofMillis(3000))
				.build();
		URI uri = new URI(webServiceUrl);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ConfigurationResponse> entity = new HttpEntity<>(headers);
		ResponseEntity<ConfigurationResponse> response = restTemplate.exchange(uri, HttpMethod.GET, entity,
				ConfigurationResponse.class);
		return response.getBody();
	}

}
