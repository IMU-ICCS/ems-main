package eu.melodic.dlms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Webservice controller for algorithms.
 */
@RestController
public class DlmsRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DlmsRestController.class);

	private Map<Algorithm, AlgorithmRunner> algorithms = new ConcurrentHashMap<>();

	/**
	 * Registers an algorithm/runner combination in the controller to be accessible via the webservice.
	 */
	public void registerAlgorithm(Algorithm algorithm, AlgorithmRunner runner) {
		algorithms.put(algorithm, runner);
	}

	/**
	 * Returns the accumulated results of all registered algorithms.
	 */
	@RequestMapping(value = "/dlmsController", method = RequestMethod.GET)
	public Map<String, Double> getResults() {
		Map<String, Double> results = new HashMap<>(algorithms.entrySet().size());

		for(Map.Entry<Algorithm, AlgorithmRunner> algorithmEntry : algorithms.entrySet()) {
			AlgorithmRunner runner = algorithmEntry.getValue();
			final double algorithmResults = runner.queryResults();
			LOGGER.info("result for algorithm {}: {}", algorithmEntry.getKey().getName(), algorithmResults);

			results.put(algorithmEntry.getKey().getName(), algorithmResults);
		}

		return results;
	}

}
