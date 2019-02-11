package eu.melodic.dlms;

import java.util.Collection;

import eu.melodic.dlms.utility.DlmsConfigurationConnection;
import eu.melodic.dlms.utility.DlmsConfigurationElement;

/**
 * Interface for algorithm runner classes.
 */
public abstract class AlgorithmRunner {

	/**
	 * Initializes the runner instance with a reference to the application to make the use of Spring injected repositories etc. possible.
	 */
	public abstract void initialize(DlmsControllerApplication application);

	/**
	 * Returns an utility value of all the collected results. 
	 * It is the runner's responsibility to make sure that the results are cleared afterwards (if necessary).
	 */
	public abstract double queryResults(DlmsConfigurationConnection diff);

	/**
	 * Method to run an algorithm. 
	 * It is the runner's job to keep the result(s).
	 * Takes an individual number of parameters.
	 */
	public abstract int update(Object... parameters);
	
	/**
	 * Get DlmsConfigurationElement matching the connection component name
	 */
	public DlmsConfigurationElement getComp(Collection<DlmsConfigurationElement> deployed, String toComp) {
		return deployed.stream()
				.filter(dlmsConfigurationElement -> dlmsConfigurationElement.getId().equals(toComp))
				.findFirst()
				.orElse(new DlmsConfigurationElement());
	}
	
	/**
	 * Check DlmsConfigurationElement is not empty
	 */
	public boolean isEmpty(DlmsConfigurationElement element) {
		return element.getId()==null;
	}

}
