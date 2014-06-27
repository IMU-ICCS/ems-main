/**
 */
package camel.scalability;

import camel.execution.ExecutionContext;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Event Pattern</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.scalability.EventPattern#getTimer <em>Timer</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.scalability.ScalabilityPackage#getEventPattern()
 * @model abstract="true"
 * @generated
 */
public interface EventPattern extends Event {
	/**
	 * Returns the value of the '<em><b>Timer</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Timer</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Timer</em>' reference.
	 * @see #setTimer(Timer)
	 * @see camel.scalability.ScalabilityPackage#getEventPattern_Timer()
	 * @model
	 * @generated
	 */
	Timer getTimer();

	/**
	 * Sets the value of the '{@link camel.scalability.EventPattern#getTimer <em>Timer</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Timer</em>' reference.
	 * @see #getTimer()
	 * @generated
	 */
	void setTimer(Timer value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model eRequired="true"
	 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot body='\n\t\t\t\t\t\tif (self.oclIsTypeOf(UnaryEventPattern)) then self.oclAsType(UnaryEventPattern).event = e \n\t\t\t\t\t\telse (includesLeftEvent(e) or includesRightEvent(e)) endif'"
	 * @generated
	 */
	boolean includesEvent(SimpleEvent e);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model eRequired="true"
	 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot body='\n\t\t\t\t\t\t\tif (self.oclAsType(BinaryEventPattern).left.oclIsKindOf(EventPattern)) then self.oclAsType(BinaryEventPattern).left.oclAsType(EventPattern).includesEvent(e)\n\t\t\t\t\t\t\telse(\n\t\t\t\t\t\t\t\tif (self.oclAsType(BinaryEventPattern).left.oclIsKindOf(SimpleEvent)) then self.oclAsType(BinaryEventPattern).left.oclAsType(SimpleEvent) = e\n\t\t\t\t\t\t\t\telse false\n\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t)\n\t\t\t\t\t\t\tendif'"
	 * @generated
	 */
	boolean includesLeftEvent(SimpleEvent e);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model eRequired="true"
	 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot body='\n\t\t\t\t\t\t\tif (self.oclAsType(BinaryEventPattern).right.oclIsKindOf(EventPattern)) then self.oclAsType(BinaryEventPattern).right.oclAsType(EventPattern).includesEvent(e)\n\t\t\t\t\t\t\telse (\n\t\t\t\t\t\t\t\tif (self.oclAsType(BinaryEventPattern).right.oclIsKindOf(SimpleEvent)) then self.oclAsType(BinaryEventPattern).right.oclAsType(SimpleEvent) = e else false endif\n\t\t\t\t\t\t\t\t )\n\t\t\t\t\t\t\tendif'"
	 * @generated
	 */
	boolean includesRightEvent(SimpleEvent e);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model ecRequired="true"
	 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot body='\n\t\t\t\t\t\t\tif (self.oclIsTypeOf(UnaryEventPattern)) then \n\t\t\t\t\t\t\t\tif (self.oclAsType(UnaryEventPattern).event.oclIsTypeOf(NonFunctionalEvent)) then self.oclAsType(UnaryEventPattern).event.oclAsType(NonFunctionalEvent).condition.metric.objectBinding.executionContext = ec\n\t\t\t\t\t\t\t\telse true endif  \n\t\t\t\t\t\t\telse (\n\t\t\t\t\t\t\t\tleftRelatedToExecutionContext(ec) and rightRelatedToExecutionContext(ec)\n\t\t\t\t\t\t\t) \n\t\t\t\t\t\t\tendif'"
	 * @generated
	 */
	boolean relatedToExecutionContext(ExecutionContext ec);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model ecRequired="true"
	 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot body='\n\t\t\t\t\t\t\tif (self.oclAsType(BinaryEventPattern).left.oclIsKindOf(EventPattern)) then self.oclAsType(BinaryEventPattern).left.oclAsType(EventPattern).relatedToExecutionContext(ec)\n\t\t\t\t\t\t\telse(\n\t\t\t\t\t\t\t\tif (self.oclAsType(BinaryEventPattern).left.oclIsTypeOf(NonFunctionalEvent)) then self.oclAsType(BinaryEventPattern).left.oclAsType(NonFunctionalEvent).condition.metric.objectBinding.executionContext = ec\n\t\t\t\t\t\t\t\telse true endif\n\t\t\t\t\t\t\t)\n\t\t\t\t\t\t\tendif'"
	 * @generated
	 */
	boolean leftRelatedToExecutionContext(ExecutionContext ec);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model ecRequired="true"
	 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot body='\n\t\t\t\t\t\t\tif (self.oclAsType(BinaryEventPattern).right.oclIsTypeOf(EventPattern)) then self.oclAsType(BinaryEventPattern).right.oclAsType(EventPattern).relatedToExecutionContext(ec)\n\t\t\t\t\t\t\telse (\n\t\t\t\t\t\t\t\tif (self.oclAsType(BinaryEventPattern).right.oclIsTypeOf(NonFunctionalEvent)) then self.oclAsType(BinaryEventPattern).right.oclAsType(NonFunctionalEvent).condition.metric.objectBinding.executionContext = ec \n\t\t\t\t\t\t\t\telse true endif\n\t\t\t\t\t\t\t)\n\t\t\t\t\t\t\tendif'"
	 * @generated
	 */
	boolean rightRelatedToExecutionContext(ExecutionContext ec);

} // EventPattern
