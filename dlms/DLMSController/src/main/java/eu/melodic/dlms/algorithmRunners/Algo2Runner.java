package eu.melodic.dlms.algorithmRunners;

import eu.melodic.dlms.utilitygenerator.AlgorithmRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Demo implementation of an algorithm that collects results.
 */
public class Algo2Runner implements AlgorithmRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(Algo2Runner.class);

	private Map<Date, Double> results = new HashMap<>();
	private int counter = 0;

	@Override
	public double queryResults() {
		double value = 0;
		for(Double result : results.values()) {
			value += result;
			LOGGER.info("{}: adding {} to sum", getClass().getSimpleName(), result);
		}

		double average = value / results.values().size();
		LOGGER.info("{}: sum {} for {} averaging to {}", getClass().getSimpleName(), value, results.values().size(), average);

		return average;
	}

	@Override
	public int update(Object... parameters) {
		int para1 = Integer.parseInt(parameters[0].toString());
		String para2 = parameters[1].toString();
		double result = para1 + para2.length() + counter;
		counter++;

		results.put(new Date(), result);
		LOGGER.info("{} adding result {}", getClass().getSimpleName(), result);

		return 0;
	}

}
