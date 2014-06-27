/**
 */
package camel.execution;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see camel.execution.ExecutionPackage
 * @generated
 */
public interface ExecutionFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ExecutionFactory eINSTANCE = camel.execution.impl.ExecutionFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Model</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Model</em>'.
	 * @generated
	 */
	ExecutionModel createExecutionModel();

	/**
	 * Returns a new object of class '<em>Action Realization</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Action Realization</em>'.
	 * @generated
	 */
	ActionRealization createActionRealization();

	/**
	 * Returns a new object of class '<em>Context</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Context</em>'.
	 * @generated
	 */
	ExecutionContext createExecutionContext();

	/**
	 * Returns a new object of class '<em>Measurement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Measurement</em>'.
	 * @generated
	 */
	Measurement createMeasurement();

	/**
	 * Returns a new object of class '<em>Application Measurement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Application Measurement</em>'.
	 * @generated
	 */
	ApplicationMeasurement createApplicationMeasurement();

	/**
	 * Returns a new object of class '<em>Internal Component Measurement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Internal Component Measurement</em>'.
	 * @generated
	 */
	InternalComponentMeasurement createInternalComponentMeasurement();

	/**
	 * Returns a new object of class '<em>Resource Coupling Measurement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Resource Coupling Measurement</em>'.
	 * @generated
	 */
	ResourceCouplingMeasurement createResourceCouplingMeasurement();

	/**
	 * Returns a new object of class '<em>Resource Measurement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Resource Measurement</em>'.
	 * @generated
	 */
	ResourceMeasurement createResourceMeasurement();

	/**
	 * Returns a new object of class '<em>Rule Trigger</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Rule Trigger</em>'.
	 * @generated
	 */
	RuleTrigger createRuleTrigger();

	/**
	 * Returns a new object of class '<em>SLO Assessment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>SLO Assessment</em>'.
	 * @generated
	 */
	SLOAssessment createSLOAssessment();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ExecutionPackage getExecutionPackage();

} //ExecutionFactory
