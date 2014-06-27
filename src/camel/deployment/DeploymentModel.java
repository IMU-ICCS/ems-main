/**
 */
package camel.deployment;

import camel.organisation.CloudProvider;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.deployment.DeploymentModel#getComponents <em>Components</em>}</li>
 *   <li>{@link camel.deployment.DeploymentModel#getCommunications <em>Communications</em>}</li>
 *   <li>{@link camel.deployment.DeploymentModel#getHostings <em>Hostings</em>}</li>
 *   <li>{@link camel.deployment.DeploymentModel#getImages <em>Images</em>}</li>
 *   <li>{@link camel.deployment.DeploymentModel#getProviders <em>Providers</em>}</li>
 *   <li>{@link camel.deployment.DeploymentModel#getComponentInstances <em>Component Instances</em>}</li>
 *   <li>{@link camel.deployment.DeploymentModel#getCommunicationInstances <em>Communication Instances</em>}</li>
 *   <li>{@link camel.deployment.DeploymentModel#getHostingInstances <em>Hosting Instances</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.deployment.DeploymentPackage#getDeploymentModel()
 * @model
 * @generated
 */
public interface DeploymentModel extends CloudMLElement {
	/**
	 * Returns the value of the '<em><b>Components</b></em>' containment reference list.
	 * The list contents are of type {@link camel.deployment.Component}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Components</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Components</em>' containment reference list.
	 * @see camel.deployment.DeploymentPackage#getDeploymentModel_Components()
	 * @model containment="true"
	 * @generated
	 */
	EList<Component> getComponents();

	/**
	 * Returns the value of the '<em><b>Communications</b></em>' containment reference list.
	 * The list contents are of type {@link camel.deployment.Communication}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Communications</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Communications</em>' containment reference list.
	 * @see camel.deployment.DeploymentPackage#getDeploymentModel_Communications()
	 * @model containment="true"
	 * @generated
	 */
	EList<Communication> getCommunications();

	/**
	 * Returns the value of the '<em><b>Hostings</b></em>' containment reference list.
	 * The list contents are of type {@link camel.deployment.Hosting}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hostings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hostings</em>' containment reference list.
	 * @see camel.deployment.DeploymentPackage#getDeploymentModel_Hostings()
	 * @model containment="true"
	 * @generated
	 */
	EList<Hosting> getHostings();

	/**
	 * Returns the value of the '<em><b>Images</b></em>' containment reference list.
	 * The list contents are of type {@link camel.deployment.Image}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Images</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Images</em>' containment reference list.
	 * @see camel.deployment.DeploymentPackage#getDeploymentModel_Images()
	 * @model containment="true"
	 * @generated
	 */
	EList<Image> getImages();

	/**
	 * Returns the value of the '<em><b>Providers</b></em>' reference list.
	 * The list contents are of type {@link camel.organisation.CloudProvider}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Providers</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Providers</em>' reference list.
	 * @see camel.deployment.DeploymentPackage#getDeploymentModel_Providers()
	 * @model
	 * @generated
	 */
	EList<CloudProvider> getProviders();

	/**
	 * Returns the value of the '<em><b>Component Instances</b></em>' containment reference list.
	 * The list contents are of type {@link camel.deployment.ComponentInstance}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component Instances</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component Instances</em>' containment reference list.
	 * @see camel.deployment.DeploymentPackage#getDeploymentModel_ComponentInstances()
	 * @model containment="true"
	 * @generated
	 */
	EList<ComponentInstance> getComponentInstances();

	/**
	 * Returns the value of the '<em><b>Communication Instances</b></em>' containment reference list.
	 * The list contents are of type {@link camel.deployment.CommunicationInstance}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Communication Instances</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Communication Instances</em>' containment reference list.
	 * @see camel.deployment.DeploymentPackage#getDeploymentModel_CommunicationInstances()
	 * @model containment="true"
	 * @generated
	 */
	EList<CommunicationInstance> getCommunicationInstances();

	/**
	 * Returns the value of the '<em><b>Hosting Instances</b></em>' containment reference list.
	 * The list contents are of type {@link camel.deployment.HostingInstance}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hosting Instances</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hosting Instances</em>' containment reference list.
	 * @see camel.deployment.DeploymentPackage#getDeploymentModel_HostingInstances()
	 * @model containment="true"
	 * @generated
	 */
	EList<HostingInstance> getHostingInstances();

} // DeploymentModel
