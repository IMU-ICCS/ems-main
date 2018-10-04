package eu.melodic.dlms;

import eu.melodic.dlms.utilitygenerator.Algorithm;
import eu.melodic.dlms.utilitygenerator.AlgorithmRunner;
import eu.melodic.dlms.utilitygenerator.UtilityMetrics;
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
	 * Returns a map of all active algorithms with the value their runner class produced from the call of queryResults().
	 *
	 * <p><b>TODO: Signature will change as soon as Diff-class is created (needs to be passed in here; see DlmsControllerClient.getUtilityValues())</b>
	 */
	@RequestMapping(value = "/dlmsController/utilityValue", method = RequestMethod.GET)
	public UtilityMetrics getUtilityValue() {
		Map<String, Double> utilityValueMap = new HashMap<>(algorithms.size());

		algorithms.forEach((Algorithm key, AlgorithmRunner runner) -> {
			double algorithmResult = runner.queryResults();
			LOGGER.info("result for algorithm {}: {}", key.getName(), algorithmResult);
			utilityValueMap.put(key.getCamelId(), algorithmResult);
		});

		return new UtilityMetrics(utilityValueMap);
	}

}
