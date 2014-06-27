/**
 */
package camel.scalability;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Sensor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.scalability.Sensor#getConfiguration <em>Configuration</em>}</li>
 *   <li>{@link camel.scalability.Sensor#isIsPush <em>Is Push</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.scalability.ScalabilityPackage#getSensor()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface Sensor extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Configuration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Configuration</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Configuration</em>' attribute.
	 * @see #setConfiguration(String)
	 * @see camel.scalability.ScalabilityPackage#getSensor_Configuration()
	 * @model
	 * @generated
	 */
	String getConfiguration();

	/**
	 * Sets the value of the '{@link camel.scalability.Sensor#getConfiguration <em>Configuration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Configuration</em>' attribute.
	 * @see #getConfiguration()
	 * @generated
	 */
	void setConfiguration(String value);

	/**
	 * Returns the value of the '<em><b>Is Push</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Push</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Push</em>' attribute.
	 * @see #setIsPush(boolean)
	 * @see camel.scalability.ScalabilityPackage#getSensor_IsPush()
	 * @model
	 * @generated
	 */
	boolean isIsPush();

	/**
	 * Sets the value of the '{@link camel.scalability.Sensor#isIsPush <em>Is Push</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Push</em>' attribute.
	 * @see #isIsPush()
	 * @generated
	 */
	void setIsPush(boolean value);

} // Sensor
