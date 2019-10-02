/*
 * Melodic EU Project
 * DLMS WebService
 * DMS Service Application
 * @author: ferox
 */

package eu.melodic.dlms;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import eu.melodic.dlms.component.SendToDlmsAgent;
import lombok.extern.slf4j.Slf4j;

/**
 * Application class for the DLMS agent.
 */
@SpringBootApplication
@Slf4j
public class DlmsAgentApplication {

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
	 * CommandLineRunner to start a timer directly after startup, which then
	 * collects metrics and publishes them via JMS.
	 */
	@Bean
	public CommandLineRunner run() {
		return args -> {
			String metricsRangeProperty = System.getProperties().getProperty("metricsRange");
			MetricsRange metricsRange = MetricsRange.valueOf(metricsRangeProperty);
			log.info("Application started with metricsRange {} set", metricsRangeProperty);

			String url = System.getProperties().getProperty("mode");
			log.info("Application started and URL {} identified", url);

			String publicIp = System.getProperties().getProperty("ip.public");
			String webServiceUrl = System.getProperties().getProperty("webServiceUrl")+"/getAlluxioCmd/"+publicIp;

			SendToDlmsAgent sendToDlmsAgent = getSendToDlmsAgent(webServiceUrl);

			// to stop program from going to a loop
			// this needs to be modified to get information from melodic when it is ready
			int counter = 1;
			boolean isNull = isNull(sendToDlmsAgent);
			while (isNull) { // repeat it until it is not null
				// sleep needs to be changed later on
				Thread.sleep(20000);
				counter++;

				if (counter > 10) {
					log.error("Did not find the component id. Exiting now ...");
					break;
				}
				log.debug("Did not find the component id. Waiting ....");
				// obtain the object again and check
				sendToDlmsAgent = getSendToDlmsAgent(webServiceUrl);
				isNull = isNull(sendToDlmsAgent);
			}
			String appComp = runCommands(sendToDlmsAgent);

			TimerTask timerTask = new TimerTask() {
				@Override
				public void run() {
					log.info("Running metrics collection for {}", url);

					switch (metricsRange) {
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
					metricsController.sendMetrics(appComp);
				}
			};

			Timer timer = new Timer();
			timer.schedule(timerTask, DELAY_AFTER_STARTUP, CALL_INTERVAL);
			log.info("Started timer with delay=" + DELAY_AFTER_STARTUP / 1000 + " sec. and interval="
					+ CALL_INTERVAL / 1000 + " sec.");
		};
	}

	/**
	 * Get contents form the web service url
	 */
	public SendToDlmsAgent getSendToDlmsAgent(String webServiceUrl) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			URI uri = new URI(webServiceUrl);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<SendToDlmsAgent> entity = new HttpEntity<>(headers);
			ResponseEntity<SendToDlmsAgent> response = restTemplate.exchange(uri, HttpMethod.GET, entity,
					SendToDlmsAgent.class);
			return response.getBody();
		} catch (URISyntaxException e) {
			log.error("Problem getting the contents from dlms web service url");
		}
		return null;
	}

	/**
	 * Check that both command to mount and compnent id is present
	 */
	public boolean isNull(SendToDlmsAgent sendToDlmsAgent) {
		if (sendToDlmsAgent != null) { // safety check first
			return sendToDlmsAgent.getCommand() == null && sendToDlmsAgent.getComponentId() == null;
		}
		return true;
	}

	/**
	 * Execute command and send back component id
	 */
	public String runCommands(SendToDlmsAgent sendToDlmsAgent) {
		String cmd = sendToDlmsAgent.getCommand();
		Process p;

		try {
			p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
		} catch (Exception e) {
			log.error("There was a problem while executing the command from dlms web service api");
		}
		return sendToDlmsAgent.getComponentId();
	}

}
