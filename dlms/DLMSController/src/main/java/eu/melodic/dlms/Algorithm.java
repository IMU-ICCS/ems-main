package eu.melodic.dlms;

import java.util.Date;
import java.util.Objects;

/**
 * Represents an algorithm from the application.properties file.
 */
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
	 * Class of the algorithm's runner class. Set when algorithm is first run.
	 * Not to be included in the properties file.
	 */
	private Class<? extends AlgorithmRunner> runnerClass;

	/**
	 * Time of the algorithm's most recent execution. Set on every execution of the runner instance.
	 * Not to be included in the properties file.
	 */
	private Date lastRun;

	private double weight;

	/**
	 * Identifier for use with CAMEL.
	 */
	private String camelId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public Object[] getArguments() {
		return arguments;
	}

	public void setArguments(Object[] arguments) {
		this.arguments = arguments;
	}

	public Class<? extends AlgorithmRunner> getRunnerClass() {
		return runnerClass;
	}

	public void setRunnerClass(Class<? extends AlgorithmRunner> runnerClass) {
		this.runnerClass = runnerClass;
	}

	public Date getLastRun() {
		return lastRun;
	}

	public void setLastRun(Date lastRun) {
		this.lastRun = lastRun;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getCamelId() {
		return camelId;
	}

	public void setCamelId(String camelId) {
		this.camelId = camelId;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) {
			return true;
		}
		if(o == null || getClass() != o.getClass()) {
			return false;
		}
		Algorithm algorithm = (Algorithm) o;
		return Objects.equals(name, algorithm.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public String toString() {
		return "Algorithm{" +
				"name='" + name + '\'' +
				'}';
	}
}
