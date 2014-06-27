/**
 */
package camel.sla;

import camel.MonetaryUnit;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Compensation Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.sla.CompensationType#getAssessmentInterval <em>Assessment Interval</em>}</li>
 *   <li>{@link camel.sla.CompensationType#getValueUnit <em>Value Unit</em>}</li>
 *   <li>{@link camel.sla.CompensationType#getValueExpression <em>Value Expression</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.sla.SlaPackage#getCompensationType()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface CompensationType extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Assessment Interval</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assessment Interval</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assessment Interval</em>' containment reference.
	 * @see #setAssessmentInterval(AssessmentIntervalType)
	 * @see camel.sla.SlaPackage#getCompensationType_AssessmentInterval()
	 * @model containment="true" required="true"
	 * @generated
	 */
	AssessmentIntervalType getAssessmentInterval();

	/**
	 * Sets the value of the '{@link camel.sla.CompensationType#getAssessmentInterval <em>Assessment Interval</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Assessment Interval</em>' containment reference.
	 * @see #getAssessmentInterval()
	 * @generated
	 */
	void setAssessmentInterval(AssessmentIntervalType value);

	/**
	 * Returns the value of the '<em><b>Value Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value Unit</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value Unit</em>' reference.
	 * @see #setValueUnit(MonetaryUnit)
	 * @see camel.sla.SlaPackage#getCompensationType_ValueUnit()
	 * @model required="true"
	 * @generated
	 */
	MonetaryUnit getValueUnit();

	/**
	 * Sets the value of the '{@link camel.sla.CompensationType#getValueUnit <em>Value Unit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value Unit</em>' reference.
	 * @see #getValueUnit()
	 * @generated
	 */
	void setValueUnit(MonetaryUnit value);

	/**
	 * Returns the value of the '<em><b>Value Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value Expression</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value Expression</em>' attribute.
	 * @see #setValueExpression(String)
	 * @see camel.sla.SlaPackage#getCompensationType_ValueExpression()
	 * @model required="true"
	 * @generated
	 */
	String getValueExpression();

	/**
	 * Sets the value of the '{@link camel.sla.CompensationType#getValueExpression <em>Value Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value Expression</em>' attribute.
	 * @see #getValueExpression()
	 * @generated
	 */
	void setValueExpression(String value);

} // CompensationType
