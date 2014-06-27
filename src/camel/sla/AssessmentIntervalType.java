/**
 */
package camel.sla;

import camel.TimeIntervalUnit;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Assessment Interval Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.sla.AssessmentIntervalType#getTimeInterval <em>Time Interval</em>}</li>
 *   <li>{@link camel.sla.AssessmentIntervalType#getCount <em>Count</em>}</li>
 *   <li>{@link camel.sla.AssessmentIntervalType#getTimeUnit <em>Time Unit</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.sla.SlaPackage#getAssessmentIntervalType()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='AssessmentInterval_attribute_value_enforcement'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot AssessmentInterval_attribute_value_enforcement='\n\t\t\t\t\t\ttimeInterval > 0 and count >= 0'"
 * @extends CDOObject
 * @generated
 */
public interface AssessmentIntervalType extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Time Interval</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Time Interval</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Time Interval</em>' attribute.
	 * @see #setTimeInterval(long)
	 * @see camel.sla.SlaPackage#getAssessmentIntervalType_TimeInterval()
	 * @model
	 * @generated
	 */
	long getTimeInterval();

	/**
	 * Sets the value of the '{@link camel.sla.AssessmentIntervalType#getTimeInterval <em>Time Interval</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Time Interval</em>' attribute.
	 * @see #getTimeInterval()
	 * @generated
	 */
	void setTimeInterval(long value);

	/**
	 * Returns the value of the '<em><b>Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Count</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Count</em>' attribute.
	 * @see #setCount(int)
	 * @see camel.sla.SlaPackage#getAssessmentIntervalType_Count()
	 * @model
	 * @generated
	 */
	int getCount();

	/**
	 * Sets the value of the '{@link camel.sla.AssessmentIntervalType#getCount <em>Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Count</em>' attribute.
	 * @see #getCount()
	 * @generated
	 */
	void setCount(int value);

	/**
	 * Returns the value of the '<em><b>Time Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Time Unit</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Time Unit</em>' reference.
	 * @see #setTimeUnit(TimeIntervalUnit)
	 * @see camel.sla.SlaPackage#getAssessmentIntervalType_TimeUnit()
	 * @model required="true"
	 * @generated
	 */
	TimeIntervalUnit getTimeUnit();

	/**
	 * Sets the value of the '{@link camel.sla.AssessmentIntervalType#getTimeUnit <em>Time Unit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Time Unit</em>' reference.
	 * @see #getTimeUnit()
	 * @generated
	 */
	void setTimeUnit(TimeIntervalUnit value);

} // AssessmentIntervalType
