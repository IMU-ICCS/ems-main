/**
 */
package camel.scalability;

import camel.TimeIntervalUnit;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Timer</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.scalability.Timer#getType <em>Type</em>}</li>
 *   <li>{@link camel.scalability.Timer#getTimeValue <em>Time Value</em>}</li>
 *   <li>{@link camel.scalability.Timer#getMaxOccurrenceNum <em>Max Occurrence Num</em>}</li>
 *   <li>{@link camel.scalability.Timer#getUnit <em>Unit</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.scalability.ScalabilityPackage#getTimer()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='Timer_correctValues'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot Timer_correctValues='\n\t\t\t\t\t\ttimeValue > 0 and (self.type = TimerType::WITHIN_MAX implies self.maxOccurrenceNum > 0) and (self.type <> TimerType::WITHIN_MAX implies self.maxOccurrenceNum = 0)'"
 * @extends CDOObject
 * @generated
 */
public interface Timer extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link camel.scalability.TimerType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see camel.scalability.TimerType
	 * @see #setType(TimerType)
	 * @see camel.scalability.ScalabilityPackage#getTimer_Type()
	 * @model required="true"
	 * @generated
	 */
	TimerType getType();

	/**
	 * Sets the value of the '{@link camel.scalability.Timer#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see camel.scalability.TimerType
	 * @see #getType()
	 * @generated
	 */
	void setType(TimerType value);

	/**
	 * Returns the value of the '<em><b>Time Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Time Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Time Value</em>' attribute.
	 * @see #setTimeValue(int)
	 * @see camel.scalability.ScalabilityPackage#getTimer_TimeValue()
	 * @model required="true"
	 * @generated
	 */
	int getTimeValue();

	/**
	 * Sets the value of the '{@link camel.scalability.Timer#getTimeValue <em>Time Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Time Value</em>' attribute.
	 * @see #getTimeValue()
	 * @generated
	 */
	void setTimeValue(int value);

	/**
	 * Returns the value of the '<em><b>Max Occurrence Num</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max Occurrence Num</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Occurrence Num</em>' attribute.
	 * @see #setMaxOccurrenceNum(int)
	 * @see camel.scalability.ScalabilityPackage#getTimer_MaxOccurrenceNum()
	 * @model
	 * @generated
	 */
	int getMaxOccurrenceNum();

	/**
	 * Sets the value of the '{@link camel.scalability.Timer#getMaxOccurrenceNum <em>Max Occurrence Num</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Occurrence Num</em>' attribute.
	 * @see #getMaxOccurrenceNum()
	 * @generated
	 */
	void setMaxOccurrenceNum(int value);

	/**
	 * Returns the value of the '<em><b>Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Unit</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unit</em>' reference.
	 * @see #setUnit(TimeIntervalUnit)
	 * @see camel.scalability.ScalabilityPackage#getTimer_Unit()
	 * @model required="true"
	 * @generated
	 */
	TimeIntervalUnit getUnit();

	/**
	 * Sets the value of the '{@link camel.scalability.Timer#getUnit <em>Unit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unit</em>' reference.
	 * @see #getUnit()
	 * @generated
	 */
	void setUnit(TimeIntervalUnit value);

} // Timer
