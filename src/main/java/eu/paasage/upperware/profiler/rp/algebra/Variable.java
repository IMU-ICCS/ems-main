package eu.paasage.upperware.profiler.rp.algebra;

public class Variable {

	private String variable;
	private int from;
	private int to;

	public Variable(String variable, int from, int to) {
		this.variable = variable;
		this.from = from;
		this.to = to;
	}

	/**
	 * @return the variable
	 */
	public String getVariable() {
		return variable;
	}

	/**
	 * @return the from
	 */
	public int getFrom() {
		return from;
	}

	/**
	 * @return the to
	 */
	public int getTo() {
		return to;
	}

	/**
	 * @param variable
	 *            name of variable within boolean expression
	 */
	public void setVariable(String variable) {
		this.variable = variable;
	}

	/**
	 * @param from
	 *            lower bound of variable
	 */
	public void setFrom(int from) {
		this.from = from;
	}

	/**
	 * @param to
	 *            upper bound of variable
	 */
	public void setTo(int to) {
		this.to = to;
	}

}
