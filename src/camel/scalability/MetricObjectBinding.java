/**
 */
package camel.scalability;

import camel.Application;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Metric Object Binding</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.scalability.MetricObjectBinding#getApplication <em>Application</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.scalability.ScalabilityPackage#getMetricObjectBinding()
 * @model abstract="true"
 * @extends CDOObject
 * @generated
 */
public interface MetricObjectBinding extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Application</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Application</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Application</em>' reference.
	 * @see #setApplication(Application)
	 * @see camel.scalability.ScalabilityPackage#getMetricObjectBinding_Application()
	 * @model required="true"
	 * @generated
	 */
	Application getApplication();

	/**
	 * Sets the value of the '{@link camel.scalability.MetricObjectBinding#getApplication <em>Application</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Application</em>' reference.
	 * @see #getApplication()
	 * @generated
	 */
	void setApplication(Application value);

} // MetricObjectBinding
