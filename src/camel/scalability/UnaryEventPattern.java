/**
 */
package camel.scalability;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Unary Event Pattern</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.scalability.UnaryEventPattern#getEvent <em>Event</em>}</li>
 *   <li>{@link camel.scalability.UnaryEventPattern#getOccurrenceNum <em>Occurrence Num</em>}</li>
 *   <li>{@link camel.scalability.UnaryEventPattern#getOperator <em>Operator</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.scalability.ScalabilityPackage#getUnaryEventPattern()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='UnaryEventPattern_correct_values_per_operator'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot UnaryEventPattern_correct_values_per_operator='\n\t\t\t(self.operator = UnaryPatternOperatorType::REPEAT implies occurrenceNum > 0) and (self.operator <> UnaryPatternOperatorType::REPEAT implies occurrenceNum = 0) and (self.operator = UnaryPatternOperatorType::WHERE implies self.oclAsType(EventPattern).timer <> null) and (self.operator <> UnaryPatternOperatorType::WHERE implies self.oclAsType(EventPattern).timer = null)'"
 * @generated
 */
public interface UnaryEventPattern extends EventPattern {
	/**
	 * Returns the value of the '<em><b>Event</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Event</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Event</em>' reference.
	 * @see #setEvent(Event)
	 * @see camel.scalability.ScalabilityPackage#getUnaryEventPattern_Event()
	 * @model required="true"
	 * @generated
	 */
	Event getEvent();

	/**
	 * Sets the value of the '{@link camel.scalability.UnaryEventPattern#getEvent <em>Event</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Event</em>' reference.
	 * @see #getEvent()
	 * @generated
	 */
	void setEvent(Event value);

	/**
	 * Returns the value of the '<em><b>Occurrence Num</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Occurrence Num</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Occurrence Num</em>' attribute.
	 * @see #setOccurrenceNum(int)
	 * @see camel.scalability.ScalabilityPackage#getUnaryEventPattern_OccurrenceNum()
	 * @model
	 * @generated
	 */
	int getOccurrenceNum();

	/**
	 * Sets the value of the '{@link camel.scalability.UnaryEventPattern#getOccurrenceNum <em>Occurrence Num</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Occurrence Num</em>' attribute.
	 * @see #getOccurrenceNum()
	 * @generated
	 */
	void setOccurrenceNum(int value);

	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * The literals are from the enumeration {@link camel.scalability.UnaryPatternOperatorType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see camel.scalability.UnaryPatternOperatorType
	 * @see #setOperator(UnaryPatternOperatorType)
	 * @see camel.scalability.ScalabilityPackage#getUnaryEventPattern_Operator()
	 * @model required="true"
	 * @generated
	 */
	UnaryPatternOperatorType getOperator();

	/**
	 * Sets the value of the '{@link camel.scalability.UnaryEventPattern#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see camel.scalability.UnaryPatternOperatorType
	 * @see #getOperator()
	 * @generated
	 */
	void setOperator(UnaryPatternOperatorType value);

} // UnaryEventPattern
