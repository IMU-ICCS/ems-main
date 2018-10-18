package eu.melodic.dlms.algorithmRunners;

import eu.melodic.dlms.algorithms.Algo_AverageWeight;
import eu.melodic.dlms.utilitygenerator.AlgorithmRunner;

/**
 * Demo implementation of a simple algorithm.
 */
public class Algo1Runner implements AlgorithmRunner {

	private Algo_AverageWeight algo1 = new Algo_AverageWeight();

	@Override
	public double queryResults() {
		return -5;
	}

	@Override
	public int update(Object... parameters) {
		return (algo1.computeAvgAndStore());
	}

}
