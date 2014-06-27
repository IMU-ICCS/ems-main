/**
 */
package camel.deployment;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Component</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.deployment.Component#getProvidedCommunications <em>Provided Communications</em>}</li>
 *   <li>{@link camel.deployment.Component#getProvidedHosts <em>Provided Hosts</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.deployment.DeploymentPackage#getComponent()
 * @model abstract="true"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore constraints='provided_component_ports_should_point_to_component'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot provided_component_ports_should_point_to_component='\n\t\t\t\t\t\t\t\tprovidedCommunications->forAll(p | p.component = self) \n\t\t\t\t\t\t\t\tand\n\t\t\t\t\t\t\t\tprovidedHosts->forAll(p | p.component = self)'"
 * @generated
 */
public interface Component extends CloudMLElement {
	/**
	 * Returns the value of the '<em><b>Provided Communications</b></em>' containment reference list.
	 * The list contents are of type {@link camel.deployment.ProvidedCommunication}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provided Communications</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provided Communications</em>' containment reference list.
	 * @see camel.deployment.DeploymentPackage#getComponent_ProvidedCommunications()
	 * @model containment="true"
	 * @generated
	 */
	EList<ProvidedCommunication> getProvidedCommunications();

	/**
	 * Returns the value of the '<em><b>Provided Hosts</b></em>' containment reference list.
	 * The list contents are of type {@link camel.deployment.ProvidedHost}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provided Hosts</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provided Hosts</em>' containment reference list.
	 * @see camel.deployment.DeploymentPackage#getComponent_ProvidedHosts()
	 * @model containment="true"
	 * @generated
	 */
	EList<ProvidedHost> getProvidedHosts();

} // Component
