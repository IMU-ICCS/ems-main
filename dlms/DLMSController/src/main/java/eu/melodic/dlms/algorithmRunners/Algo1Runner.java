package eu.melodic.dlms.algorithmRunners;

import eu.melodic.dlms.utilitygenerator.AlgorithmRunner;

/**
 * Demo implementation of a simple algorithm.
 */
public class Algo1Runner implements AlgorithmRunner {

	@Override
	public double queryResults() {
		return -5;
	}

	@Override
	public int update(Object... parameters) {
		return 0;
	}

}
