package eu.melodic.dlms.utility;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Collections;

/**
 * Client interface to call DlmsController from the UtilityGenerator.
 *
 * <p><b>TODO: The redundant version in the current DlmsController project is to be removed on integration!</b>
 */
public class DlmsControllerClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(DlmsControllerClient.class);

	// TODO should move to some config file --> UG's application.properties?
	private static final String DATASOURCE_SERVER_URL = "http://localhost:8080/dlmsController/utilityValue";

	/**
	 * Main method just for stand-alone testing.
	 *
	 * <p><b>TODO: May be removed on integration.</b>
	 */
	public static void main(String args[]) {
		UtilityMetrics result = new DlmsControllerClient().getUtilityValues(Collections.emptyList(), Collections.emptyList());
		for(String key : result.getResults().keySet()) {
			LOGGER.info("{} --> {}", key, result.getResults().get(key));
		}
	}

	/**
	 * Returns utility values from every algorithm running in the DlmsController.
	 * The parameters are passed to the algorithms if a difference between actual and proposed value is noted.
	 *
	 * <p><b>TODO: Parameters are not yet passed on, as we need to define what a "difference" is exactly.</b>
	 * <p><b>TODO: The Diff-class (technically) should be part of this client. It is still missing as depending on above it is not clear how it will look.</b>
	 * <p><b>TODO: Diff needs to be performed. Only if there is a difference, the DlmsController should be called.</b>
	 */
	public UtilityMetrics getUtilityValues(Collection<ConfigurationElement> actual, Collection<ConfigurationElement> proposed) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			URI uri = new URI(DATASOURCE_SERVER_URL);
			HttpHeaders headers = createHeaders();
			HttpEntity<UtilityMetrics> entity = new HttpEntity<>(null, headers);
			ResponseEntity<UtilityMetrics> response = restTemplate.exchange(uri, HttpMethod.GET, entity, UtilityMetrics.class);

			UtilityMetrics result = response.getBody();
			return result;
		}
		catch(URISyntaxException | RestClientException e) {
			LOGGER.error(e.getMessage(), e);
		}

		return new UtilityMetrics(Collections.emptyMap());
	}

	/**
	 * Creates the HTTPHeaders with the security information.
	 */
	private HttpHeaders createHeaders() {
		return new HttpHeaders() {{
			String auth = "user" + ":" + "9687831d-a0bd-4d7a-993d-5d31e740749b";
			byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
			String authHeader = "Basic " + new String(encodedAuth);
			set("Authorization", authHeader);
		}};
	}

}
