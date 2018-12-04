package eu.melodic.dlms;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an algorithm from the application.properties file.
 */
@Getter
@Setter
@EqualsAndHashCode
public class Algorithm {

	/**
	 * Name of the algorithm. 
	 * To be included in the properties file.
	 */
	private String name;

	/**
	 * Classname of the algorithm's runner class. 
	 * To be included in the properties file.
	 */
	private String className;

	/**
	 * Interval the algorithm should be run in seconds. 
	 * To be included in the properties file.
	 */
	private int interval;

	/**
	 * Arguments to the algorithm when called. 
	 * To be included in the properties file.
	 */
	private Object[] arguments;

	/**
	 * Class of the algorithm's runner class. 
	 * Set when algorithm is first run. 
	 * Not to be included in the properties file.
	 */
	private Class<? extends AlgorithmRunner> runnerClass;

	/**
	 * Time of the algorithm's most recent execution. 
	 * Set on every execution of the runner instance. 
	 * Not to be included in the properties file.
	 */
	private Date lastRun;
	private double weight;

	/**
	 * Identifier for use with CAMEL.
	 */
	private String camelId;

	public Class<? extends AlgorithmRunner> getRunnerClass() {
		return runnerClass;
	}

	public void setRunnerClass(Class<? extends AlgorithmRunner> runnerClass) {
		this.runnerClass = runnerClass;
	}

	@Override
	public String toString() {
		return "Algorithm{" + "name='" + name + '\'' + '}';
	}
}
