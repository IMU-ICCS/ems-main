/**
 */
package eu.paasage.upperware.metamodel.application;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage
 * @generated
 */
public interface ApplicationFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ApplicationFactory eINSTANCE = eu.paasage.upperware.metamodel.application.impl.ApplicationFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Paasage Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Paasage Configuration</em>'.
	 * @generated
	 */
	PaasageConfiguration createPaasageConfiguration();

	/**
	 * Returns a new object of class '<em>Virtual Machine</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Virtual Machine</em>'.
	 * @generated
	 */
	VirtualMachine createVirtualMachine();

	/**
	 * Returns a new object of class '<em>Virtual Machine Profile</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Virtual Machine Profile</em>'.
	 * @generated
	 */
	VirtualMachineProfile createVirtualMachineProfile();

	/**
	 * Returns a new object of class '<em>Memory</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Memory</em>'.
	 * @generated
	 */
	Memory createMemory();

	/**
	 * Returns a new object of class '<em>Storage</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Storage</em>'.
	 * @generated
	 */
	Storage createStorage();

	/**
	 * Returns a new object of class '<em>CPU</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>CPU</em>'.
	 * @generated
	 */
	CPU createCPU();

	/**
	 * Returns a new object of class '<em>Provider</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Provider</em>'.
	 * @generated
	 */
	Provider createProvider();

	/**
	 * Returns a new object of class '<em>Component</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Component</em>'.
	 * @generated
	 */
	ApplicationComponent createApplicationComponent();

	/**
	 * Returns a new object of class '<em>Elasticity Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Elasticity Rule</em>'.
	 * @generated
	 */
	ElasticityRule createElasticityRule();

	/**
	 * Returns a new object of class '<em>Action Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Action Upperware</em>'.
	 * @generated
	 */
	ActionUpperware createActionUpperware();

	/**
	 * Returns a new object of class '<em>Condition Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Condition Upperware</em>'.
	 * @generated
	 */
	ConditionUpperware createConditionUpperware();

	/**
	 * Returns a new object of class '<em>Paa Sage Variable</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Paa Sage Variable</em>'.
	 * @generated
	 */
	PaaSageVariable createPaaSageVariable();

	/**
	 * Returns a new object of class '<em>Paa Sage Goal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Paa Sage Goal</em>'.
	 * @generated
	 */
	PaaSageGoal createPaaSageGoal();

	/**
	 * Returns a new object of class '<em>Required Feature</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Required Feature</em>'.
	 * @generated
	 */
	RequiredFeature createRequiredFeature();

	/**
	 * Returns a new object of class '<em>Dimension</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Dimension</em>'.
	 * @generated
	 */
	Dimension createDimension();

	/**
	 * Returns a new object of class '<em>Provider Dimension</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Provider Dimension</em>'.
	 * @generated
	 */
	ProviderDimension createProviderDimension();

	/**
	 * Returns a new object of class '<em>Image Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Image Upperware</em>'.
	 * @generated
	 */
	ImageUpperware createImageUpperware();

	/**
	 * Returns a new object of class '<em>Component Metric Relationship</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Component Metric Relationship</em>'.
	 * @generated
	 */
	ComponentMetricRelationship createComponentMetricRelationship();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ApplicationPackage getApplicationPackage();

} //ApplicationFactory
