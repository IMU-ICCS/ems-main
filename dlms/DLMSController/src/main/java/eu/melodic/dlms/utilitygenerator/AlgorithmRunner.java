package eu.melodic.dlms.utilitygenerator;

import eu.melodic.dlms.DlmsControllerApplication;

/**
 * Interface for algorithm runner classes.
 */
public interface AlgorithmRunner {

	/**
	 * Initializes the runner instance with a reference to the application to make the use of Spring injected repositories etc. possible.
	 */
	void initialize(DlmsControllerApplication application);

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
