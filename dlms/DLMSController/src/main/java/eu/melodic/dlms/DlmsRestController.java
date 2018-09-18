package eu.melodic.dlms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	 * Returns the accumulated results of all registered algorithms as a weighted utility value.
	 */
	@RequestMapping(value = "/dlmsController/utilityValue", method = RequestMethod.GET)
	public double getUtilityValue() {
		double utilityValue = 0;
		for(Map.Entry<Algorithm, AlgorithmRunner> algorithmEntry : algorithms.entrySet()) {
			AlgorithmRunner runner = algorithmEntry.getValue();
			double algorithmResult = runner.queryResults();
			LOGGER.info("result for algorithm {}: {}", algorithmEntry.getKey().getName(), algorithmResult);

			double weightedResult = algorithmResult * algorithmEntry.getKey().getWeight();
			LOGGER.info("result {} weighted with {} = {}", algorithmResult, algorithmEntry.getKey().getWeight(), weightedResult);

			utilityValue += weightedResult;
			LOGGER.info("utility value changed to {}", utilityValue);
		}

		return utilityValue;
	}

}
