package eu.melodic.dlms.utility;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import camel.deployment.SoftwareComponent;
import eu.melodic.dlms.utility.camel.ModelAnalyzer;
import io.github.cloudiator.rest.model.NodeCandidate;
import lombok.extern.slf4j.Slf4j;

/**
 * Client interface to call DlmsController from the UtilityGenerator.
 */
@Slf4j
public class DlmsControllerClient {
	// below url is just for testing, will be changed later once actual utility is
	// available
	private static final String REST_URL_FOR_TESTING = "http://localhost:8094/dlmsController/utilityValue";

	private final String datasourceServerUrl;
	private final String camelModelId;
	
	/**
	 * Constructor to get datasource server url and stored camel model id
	 */

	public DlmsControllerClient(String datasource_server_url, String camelModelId) {
		this.datasourceServerUrl = datasource_server_url;
		this.camelModelId = camelModelId;
	}

	/**
	 * Constructor for unit tests etc.
	 */
	protected DlmsControllerClient() {
		this("", "");
	}

	/**
	 * Main method just for stand-alone testing.
	 *
	 * <p>
	 * <b>TODO: May be removed on integration?</b>
	 */
	public static void main(String[] args) {
		UtilityMetrics result = new DlmsControllerClient(REST_URL_FOR_TESTING, "")
				.getUtilityValues(Collections.emptyList(), Collections.emptyList());
		for (String key : result.getResults().keySet()) {
			log.info("{} --> {}", key, result.getResults().get(key));
		}
	}

	/**
	 * Obtain the deployed application topology from the camel model
	 *
	 */
	public Map<SoftwareComponent, List<SoftwareComponent>> readCamelModel(String camelId) {
		ModelAnalyzer modelAnalyzer = new ModelAnalyzer();
		modelAnalyzer.readModel(camelId);
		
		return modelAnalyzer.getCompConMap();
	}

	/**
	 * Returns utility values from every algorithm running in the DlmsController.
	 * The parameters are passed to the algorithms if a difference between actual
	 * and proposed value is noted.
	 */
	public UtilityMetrics getUtilityValues(Collection<DlmsConfigurationElement> deployed,
			Collection<DlmsConfigurationElement> proposed) {
		// get the connections between the application component and datasource
		Map<SoftwareComponent, List<SoftwareComponent>> compConMap= readCamelModel(this.camelModelId);
		// get the connections
		
		try {
			RestTemplate restTemplate = new RestTemplate();
			URI uri = new URI(datasourceServerUrl);
			HttpHeaders headers = createHeaders();

			DlmsDiffBundle diffBundle = runDiff(deployed, proposed);

			if (diffBundle.isEmpty()) {
				log.info("no diffs found");
				return new UtilityMetrics();
			}
			DlmsConfigurationConnection dlmsConfigCon = new DlmsConfigurationConnection(proposed, compConMap);
			HttpEntity<DlmsConfigurationConnection> entity = new HttpEntity<>(dlmsConfigCon, headers);
			ResponseEntity<UtilityMetrics> response = restTemplate.exchange(uri, HttpMethod.POST, entity,
					UtilityMetrics.class);

			return response.getBody();
		} catch (URISyntaxException | RestClientException e) {
			log.error(e.getMessage(), e);
		}

		return new UtilityMetrics();
	}

	private DlmsDiffBundle runDiff(Collection<DlmsConfigurationElement> deployed,
			Collection<DlmsConfigurationElement> proposed) {
		DlmsDiffBundle diffBundle = new DlmsDiffBundle();

		for (DlmsConfigurationElement deployedElement : deployed) {
			log.info("handling deployed element: {}", deployedElement.getId());

			for (DlmsConfigurationElement proposedElement : proposed) {
				log.info("comparing proposed element: {}", proposedElement.getId());

				if (hasSameId(deployedElement, proposedElement)) {
					log.info("match found for {}", proposedElement.getId());
					checkElementsForDiff(diffBundle, deployedElement, proposedElement);
				}
			}
		}
		return diffBundle;
	}

	private void checkElementsForDiff(DlmsDiffBundle diffBundle, DlmsConfigurationElement deployedElement,
			DlmsConfigurationElement proposedElement) {
		if (hasCardinalityDiff(deployedElement, proposedElement)) {
			log.info("diff found for {} in cardinality", proposedElement.getId());
			registerDiff(diffBundle, deployedElement, proposedElement);
		} else if (hasValidNodeCandidates(deployedElement, proposedElement)) {
			NodeCandidate deployedCandidate = deployedElement.getNodeCandidate();
			NodeCandidate proposedCandidate = proposedElement.getNodeCandidate();

			if (hasLocationDiff(deployedCandidate, proposedCandidate)) {
				log.info("diff found for {} in location", proposedElement.getId());
				registerDiff(diffBundle, deployedElement, proposedElement);
			} else if (hasHardwareDiff(deployedCandidate, proposedCandidate)) {
				log.info("diff found for {} in hardware", proposedElement.getId());
				registerDiff(diffBundle, deployedElement, proposedElement);
			}
		} else {
			log.info("node candidate(s) null for {}", proposedElement.getId());
		}
	}

	protected boolean hasSameId(DlmsConfigurationElement deployedElement, DlmsConfigurationElement proposedElement) {
		return deployedElement.getId() != null && deployedElement.getId().equals(proposedElement.getId());
	}

	protected boolean hasCardinalityDiff(DlmsConfigurationElement deployedElement,
			DlmsConfigurationElement proposedElement) {
		return deployedElement.getCardinality() != proposedElement.getCardinality();
	}

	protected boolean hasValidNodeCandidates(DlmsConfigurationElement deployedElement,
			DlmsConfigurationElement proposedElement) {
		return deployedElement.getNodeCandidate() != null && proposedElement.getNodeCandidate() != null;
	}

	protected boolean hasLocationDiff(NodeCandidate deployedCandidate, NodeCandidate proposedCandidate) {
		return deployedCandidate.getLocation() != null
				&& !deployedCandidate.getLocation().equals(proposedCandidate.getLocation());
	}

	protected boolean hasHardwareDiff(NodeCandidate deployedCandidate, NodeCandidate proposedCandidate) {
		return deployedCandidate.getHardware() != null
				&& !deployedCandidate.getHardware().equals(proposedCandidate.getHardware());
	}

	protected void registerDiff(DlmsDiffBundle diffBundle, DlmsConfigurationElement deployedElement,
			DlmsConfigurationElement proposedElement) {
		DlmsConfigurationDiff diff = new DlmsConfigurationDiff(deployedElement, proposedElement);
		diffBundle.addConfigurationDiff(diff);
	}

	/**
	 * Creates the HTTPHeaders with the security information. This will be changed
	 * in production After we get actual utility
	 */
	private HttpHeaders createHeaders() {
		return new HttpHeaders() {
			{
				String auth = "user" + ":" + "9687831d-a0bd-4d7a-993d-5d31e740749b";
				byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);
				set("Authorization", authHeader);
			}
		};
	}

}
