package eu.melodic.dlms;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import eu.melodic.dlms.utility.DlmsDiffBundle;
import eu.melodic.dlms.utility.UtilityMetrics;

/**
 * Webservice controller for algorithms.
 */
@RestController
public class DlmsRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DlmsRestController.class);

	private Map<Algorithm, AlgorithmRunner> algorithms = new ConcurrentHashMap<>();

	/**
	 * Registers an algorithm/runner combination in the controller to be accessible
	 * via the webservice.
	 */
	public void registerAlgorithm(Algorithm algorithm, AlgorithmRunner runner) {
		algorithms.put(algorithm, runner);
	}

	/**
	 * Returns a map of all active algorithms with the value their runner class
	 * produced from the call of queryResults().
	 */
	@GetMapping(value = "/dlmsController/utilityValue")
	public UtilityMetrics getUtilityValue(@RequestBody DlmsDiffBundle diffs) {
		Map<String, Double> utilityValueMap = new HashMap<>(algorithms.size());

		algorithms.forEach((Algorithm key, AlgorithmRunner runner) -> {
			double algorithmResult = runner.queryResults(diffs);
			LOGGER.info("result for algorithm {}: {}", key.getName(), algorithmResult);
			utilityValueMap.put(key.getCamelId(), algorithmResult);
		});

		return new UtilityMetrics(utilityValueMap);
	}

}
