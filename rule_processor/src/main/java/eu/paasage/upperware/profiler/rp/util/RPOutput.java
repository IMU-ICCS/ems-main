/**
 * 
 */
package eu.paasage.upperware.profiler.rp.util;

/**
 * @author hopped
 *
 */
public class RPOutput {

	private int errorCode;
	private String cpModelId;

	/**
	 * 
	 * @param errorCode
	 *            Rule Processor error code
	 * @param cpModelId
	 *            the current CP model ID (either the given one or 'v2')
	 */
	public RPOutput(int errorCode, String cpModelId) {
		this.errorCode = errorCode;
		this.cpModelId = cpModelId;
	}

	/**
	 * @return error code
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode
	 *            code of error
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the current CP model ID
	 */
	public String getCpModelId() {
		return cpModelId;
	}

	/**
	 * @param cpModelId
	 *            sets the CP model ID
	 */
	public void setCpModelId(String cpModelId) {
		this.cpModelId = cpModelId;
	}

}
