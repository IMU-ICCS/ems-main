/**
 */
package camel.deployment;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see camel.deployment.DeploymentPackage
 * @generated
 */
public interface DeploymentFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DeploymentFactory eINSTANCE = camel.deployment.impl.DeploymentFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Model</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Model</em>'.
	 * @generated
	 */
	DeploymentModel createDeploymentModel();

	/**
	 * Returns a new object of class '<em>Internal Component</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Internal Component</em>'.
	 * @generated
	 */
	InternalComponent createInternalComponent();

	/**
	 * Returns a new object of class '<em>External Component</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>External Component</em>'.
	 * @generated
	 */
	ExternalComponent createExternalComponent();

	/**
	 * Returns a new object of class '<em>VM</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>VM</em>'.
	 * @generated
	 */
	VM createVM();

	/**
	 * Returns a new object of class '<em>Component Group</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Component Group</em>'.
	 * @generated
	 */
	ComponentGroup createComponentGroup();

	/**
	 * Returns a new object of class '<em>Computational Resource</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Computational Resource</em>'.
	 * @generated
	 */
	ComputationalResource createComputationalResource();

	/**
	 * Returns a new object of class '<em>Communication</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Communication</em>'.
	 * @generated
	 */
	Communication createCommunication();

	/**
	 * Returns a new object of class '<em>Provided Communication</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Provided Communication</em>'.
	 * @generated
	 */
	ProvidedCommunication createProvidedCommunication();

	/**
	 * Returns a new object of class '<em>Required Communication</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Required Communication</em>'.
	 * @generated
	 */
	RequiredCommunication createRequiredCommunication();

	/**
	 * Returns a new object of class '<em>Hosting</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Hosting</em>'.
	 * @generated
	 */
	Hosting createHosting();

	/**
	 * Returns a new object of class '<em>Provided Host</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Provided Host</em>'.
	 * @generated
	 */
	ProvidedHost createProvidedHost();

	/**
	 * Returns a new object of class '<em>Required Host</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Required Host</em>'.
	 * @generated
	 */
	RequiredHost createRequiredHost();

	/**
	 * Returns a new object of class '<em>Image</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Image</em>'.
	 * @generated
	 */
	Image createImage();

	/**
	 * Returns a new object of class '<em>Internal Component Instance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Internal Component Instance</em>'.
	 * @generated
	 */
	InternalComponentInstance createInternalComponentInstance();

	/**
	 * Returns a new object of class '<em>External Component Instance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>External Component Instance</em>'.
	 * @generated
	 */
	ExternalComponentInstance createExternalComponentInstance();

	/**
	 * Returns a new object of class '<em>VM Instance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>VM Instance</em>'.
	 * @generated
	 */
	VMInstance createVMInstance();

	/**
	 * Returns a new object of class '<em>Communication Instance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Communication Instance</em>'.
	 * @generated
	 */
	CommunicationInstance createCommunicationInstance();

	/**
	 * Returns a new object of class '<em>Communication Port Instance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Communication Port Instance</em>'.
	 * @generated
	 */
	CommunicationPortInstance createCommunicationPortInstance();

	/**
	 * Returns a new object of class '<em>Provided Communication Instance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Provided Communication Instance</em>'.
	 * @generated
	 */
	ProvidedCommunicationInstance createProvidedCommunicationInstance();

	/**
	 * Returns a new object of class '<em>Required Communication Instance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Required Communication Instance</em>'.
	 * @generated
	 */
	RequiredCommunicationInstance createRequiredCommunicationInstance();

	/**
	 * Returns a new object of class '<em>Hosting Instance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Hosting Instance</em>'.
	 * @generated
	 */
	HostingInstance createHostingInstance();

	/**
	 * Returns a new object of class '<em>Provided Host Instance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Provided Host Instance</em>'.
	 * @generated
	 */
	ProvidedHostInstance createProvidedHostInstance();

	/**
	 * Returns a new object of class '<em>Required Host Instance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Required Host Instance</em>'.
	 * @generated
	 */
	RequiredHostInstance createRequiredHostInstance();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	DeploymentPackage getDeploymentPackage();

} //DeploymentFactory
