/**
 */
package camel.execution;

import camel.Application;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Application Measurement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.execution.ApplicationMeasurement#getForApplication <em>For Application</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.execution.ExecutionPackage#getApplicationMeasurement()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='AM_Same_App'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot AM_Same_App='\n\t\t\t\t\t\t\t\tself.inExecutionContext.ofApplication = self.forApplication'"
 * @generated
 */
public interface ApplicationMeasurement extends Measurement {
	/**
	 * Returns the value of the '<em><b>For Application</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>For Application</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>For Application</em>' reference.
	 * @see #setForApplication(Application)
	 * @see camel.execution.ExecutionPackage#getApplicationMeasurement_ForApplication()
	 * @model required="true"
	 * @generated
	 */
	Application getForApplication();

	/**
	 * Sets the value of the '{@link camel.execution.ApplicationMeasurement#getForApplication <em>For Application</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>For Application</em>' reference.
	 * @see #getForApplication()
	 * @generated
	 */
	void setForApplication(Application value);

} // ApplicationMeasurement
