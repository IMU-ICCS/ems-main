package eu.melodic.dlms;

/**
 * Interface for algorithm runner classes.
 */
public interface AlgorithmRunner {

	/**
	 * Returns an average value of all the collected results. It is the runner's responsibility to make sure that the results are cleared afterwards (if necessary).
	 */
	double queryResults();

	/**
	 * Method to run an algorithm. It is the runner's job to keep the result(s).
	 * Takes an individual number of parameters.
	 */
	int update(Object... parameters);

}
