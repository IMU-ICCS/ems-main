package eu.melodic.dlms.utility;

import io.github.cloudiator.rest.model.NodeCandidate;
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
 */
public class DlmsControllerClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(DlmsControllerClient.class);

	// TODO should move to some config file --> UG's application.properties?
	private static final String DATASOURCE_SERVER_URL = "http://localhost:8094/dlmsController/utilityValue";

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
	 */
	public UtilityMetrics getUtilityValues(Collection<DlmsConfigurationElement> deployed, Collection<DlmsConfigurationElement> proposed) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			URI uri = new URI(DATASOURCE_SERVER_URL);
			HttpHeaders headers = createHeaders();

			DlmsDiffBundle diffBundle = runDiff(deployed, proposed);

			if(diffBundle.isEmpty()) {
				LOGGER.info("no diffs found");
				return new UtilityMetrics(Collections.emptyMap());
			}

			HttpEntity<DlmsDiffBundle> entity = new HttpEntity<>(diffBundle, headers);
			ResponseEntity<UtilityMetrics> response = restTemplate.exchange(uri, HttpMethod.POST, entity, UtilityMetrics.class);

			UtilityMetrics result = response.getBody();
			return result;
		}
		catch(URISyntaxException | RestClientException e) {
			LOGGER.error(e.getMessage(), e);
		}

		return new UtilityMetrics(Collections.emptyMap());
	}

	private DlmsDiffBundle runDiff(Collection<DlmsConfigurationElement> deployed, Collection<DlmsConfigurationElement> proposed) {
		DlmsDiffBundle diffBundle = new DlmsDiffBundle();

		for(DlmsConfigurationElement deployedElement : deployed) {
			LOGGER.info("handling deployed element: {}", deployedElement.getId());

			for(DlmsConfigurationElement proposedElement : proposed) {
				LOGGER.info("comparing proposed element: {}", proposedElement.getId());

				if(hasSameId(deployedElement, proposedElement)) {
					LOGGER.info("match found for {}", proposedElement.getId());
					checkElementsForDiff(diffBundle, deployedElement, proposedElement);
				}
			}
		}
		return diffBundle;
	}

	private void checkElementsForDiff(DlmsDiffBundle diffBundle, DlmsConfigurationElement deployedElement, DlmsConfigurationElement proposedElement) {
		if(hasCardinalityDiff(deployedElement, proposedElement)) {
			LOGGER.info("diff found for {}", proposedElement.getId());
			registerDiff(diffBundle, deployedElement, proposedElement);
		}
		else if(hasValidNodeCandidates(deployedElement, proposedElement)) {
			NodeCandidate deployedCandidate = deployedElement.getNodeCandidate();
			NodeCandidate proposedCandidate = proposedElement.getNodeCandidate();

			if(hasLocationDiff(deployedCandidate, proposedCandidate)) {
				LOGGER.info("diff found for {}", proposedElement.getId());
				registerDiff(diffBundle, deployedElement, proposedElement);
			}
			else if(hasHardwareDiff(deployedCandidate, proposedCandidate)) {
				LOGGER.info("diff found for {}", proposedElement.getId());
				registerDiff(diffBundle, deployedElement, proposedElement);
			}
		}
		else {
			LOGGER.info("node candidate(s) null for {}", proposedElement.getId());
		}
	}

	private boolean hasSameId(DlmsConfigurationElement deployedElement, DlmsConfigurationElement proposedElement) {
		return deployedElement.getId() != null && deployedElement.getId().equals(proposedElement.getId());
	}

	private boolean hasCardinalityDiff(DlmsConfigurationElement deployedElement, DlmsConfigurationElement proposedElement) {
		return deployedElement.getCardinality() != proposedElement.getCardinality();
	}

	private boolean hasValidNodeCandidates(DlmsConfigurationElement deployedElement, DlmsConfigurationElement proposedElement) {
		return deployedElement.getNodeCandidate() != null && proposedElement.getNodeCandidate() != null;
	}

	protected boolean hasLocationDiff(NodeCandidate deployedCandidate, NodeCandidate proposedCandidate) {
		return deployedCandidate.getLocation() != null && !deployedCandidate.getLocation().equals(proposedCandidate.getLocation());
	}

	protected boolean hasHardwareDiff(NodeCandidate deployedCandidate, NodeCandidate proposedCandidate) {
		return deployedCandidate.getHardware() != null && !deployedCandidate.getHardware().equals(proposedCandidate.getHardware());
	}

	private void registerDiff(DlmsDiffBundle diffBundle, DlmsConfigurationElement deployedElement, DlmsConfigurationElement proposedElement) {
		DlmsConfigurationDiff diff = new DlmsConfigurationDiff(deployedElement, proposedElement);
		diffBundle.addConfigurationDiff(diff);
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
