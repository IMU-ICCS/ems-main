/**
 */
package camel.scalability;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Non Functional Event</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.scalability.NonFunctionalEvent#getCondition <em>Condition</em>}</li>
 *   <li>{@link camel.scalability.NonFunctionalEvent#isIsViolation <em>Is Violation</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.scalability.ScalabilityPackage#getNonFunctionalEvent()
 * @model
 * @generated
 */
public interface NonFunctionalEvent extends SimpleEvent {
	/**
	 * Returns the value of the '<em><b>Condition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Condition</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Condition</em>' reference.
	 * @see #setCondition(MetricCondition)
	 * @see camel.scalability.ScalabilityPackage#getNonFunctionalEvent_Condition()
	 * @model required="true"
	 * @generated
	 */
	MetricCondition getCondition();

	/**
	 * Sets the value of the '{@link camel.scalability.NonFunctionalEvent#getCondition <em>Condition</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Condition</em>' reference.
	 * @see #getCondition()
	 * @generated
	 */
	void setCondition(MetricCondition value);

	/**
	 * Returns the value of the '<em><b>Is Violation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Violation</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Violation</em>' attribute.
	 * @see #setIsViolation(boolean)
	 * @see camel.scalability.ScalabilityPackage#getNonFunctionalEvent_IsViolation()
	 * @model required="true"
	 * @generated
	 */
	boolean isIsViolation();

	/**
	 * Sets the value of the '{@link camel.scalability.NonFunctionalEvent#isIsViolation <em>Is Violation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Violation</em>' attribute.
	 * @see #isIsViolation()
	 * @generated
	 */
	void setIsViolation(boolean value);

} // NonFunctionalEvent
