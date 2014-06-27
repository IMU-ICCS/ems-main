/**
 */
package camel.scalability;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Binary Event Pattern</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.scalability.BinaryEventPattern#getLeft <em>Left</em>}</li>
 *   <li>{@link camel.scalability.BinaryEventPattern#getRight <em>Right</em>}</li>
 *   <li>{@link camel.scalability.BinaryEventPattern#getLowerOccurrenceBound <em>Lower Occurrence Bound</em>}</li>
 *   <li>{@link camel.scalability.BinaryEventPattern#getUpperOccurrenceBound <em>Upper Occurrence Bound</em>}</li>
 *   <li>{@link camel.scalability.BinaryEventPattern#getOperator <em>Operator</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.scalability.ScalabilityPackage#getBinaryEventPattern()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='BinaryEventPattern_At_Least_Left_Right BinaryEventPattern_Time_One_Event BinaryEventPattern_Occur'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot BinaryEventPattern_At_Least_Left_Right='\n\t\t\tself.left <> null or self.right <> null' BinaryEventPattern_Time_One_Event='\n\t\t\tself.oclAsType(EventPattern).timer <> null implies (self.left = null or self.right = null)' BinaryEventPattern_Occur='\n\t\t\t(self.operator <> BinaryPatternOperatorType::REPEAT_UNTIL implies (self.lowerOccurrenceBound = 0 and self.upperOccurrenceBound = 0)) and (self.operator = BinaryPatternOperatorType::REPEAT_UNTIL and self.lowerOccurrenceBound >= 0 and self.upperOccurrenceBound > 0 implies self.lowerOccurrenceBound <= upperOccurrenceBound)'"
 * @generated
 */
public interface BinaryEventPattern extends EventPattern {
	/**
	 * Returns the value of the '<em><b>Left</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Left</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Left</em>' reference.
	 * @see #setLeft(Event)
	 * @see camel.scalability.ScalabilityPackage#getBinaryEventPattern_Left()
	 * @model
	 * @generated
	 */
	Event getLeft();

	/**
	 * Sets the value of the '{@link camel.scalability.BinaryEventPattern#getLeft <em>Left</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Left</em>' reference.
	 * @see #getLeft()
	 * @generated
	 */
	void setLeft(Event value);

	/**
	 * Returns the value of the '<em><b>Right</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Right</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Right</em>' reference.
	 * @see #setRight(Event)
	 * @see camel.scalability.ScalabilityPackage#getBinaryEventPattern_Right()
	 * @model
	 * @generated
	 */
	Event getRight();

	/**
	 * Sets the value of the '{@link camel.scalability.BinaryEventPattern#getRight <em>Right</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Right</em>' reference.
	 * @see #getRight()
	 * @generated
	 */
	void setRight(Event value);

	/**
	 * Returns the value of the '<em><b>Lower Occurrence Bound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lower Occurrence Bound</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lower Occurrence Bound</em>' attribute.
	 * @see #setLowerOccurrenceBound(int)
	 * @see camel.scalability.ScalabilityPackage#getBinaryEventPattern_LowerOccurrenceBound()
	 * @model
	 * @generated
	 */
	int getLowerOccurrenceBound();

	/**
	 * Sets the value of the '{@link camel.scalability.BinaryEventPattern#getLowerOccurrenceBound <em>Lower Occurrence Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lower Occurrence Bound</em>' attribute.
	 * @see #getLowerOccurrenceBound()
	 * @generated
	 */
	void setLowerOccurrenceBound(int value);

	/**
	 * Returns the value of the '<em><b>Upper Occurrence Bound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Upper Occurrence Bound</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Upper Occurrence Bound</em>' attribute.
	 * @see #setUpperOccurrenceBound(int)
	 * @see camel.scalability.ScalabilityPackage#getBinaryEventPattern_UpperOccurrenceBound()
	 * @model
	 * @generated
	 */
	int getUpperOccurrenceBound();

	/**
	 * Sets the value of the '{@link camel.scalability.BinaryEventPattern#getUpperOccurrenceBound <em>Upper Occurrence Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Upper Occurrence Bound</em>' attribute.
	 * @see #getUpperOccurrenceBound()
	 * @generated
	 */
	void setUpperOccurrenceBound(int value);

	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * The literals are from the enumeration {@link camel.scalability.BinaryPatternOperatorType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see camel.scalability.BinaryPatternOperatorType
	 * @see #setOperator(BinaryPatternOperatorType)
	 * @see camel.scalability.ScalabilityPackage#getBinaryEventPattern_Operator()
	 * @model required="true"
	 * @generated
	 */
	BinaryPatternOperatorType getOperator();

	/**
	 * Sets the value of the '{@link camel.scalability.BinaryEventPattern#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see camel.scalability.BinaryPatternOperatorType
	 * @see #getOperator()
	 * @generated
	 */
	void setOperator(BinaryPatternOperatorType value);

} // BinaryEventPattern
