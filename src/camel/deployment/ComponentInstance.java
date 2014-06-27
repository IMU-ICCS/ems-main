/**
 */
package camel.deployment;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Component Instance</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.deployment.ComponentInstance#getProvidedCommunicationInstances <em>Provided Communication Instances</em>}</li>
 *   <li>{@link camel.deployment.ComponentInstance#getType <em>Type</em>}</li>
 *   <li>{@link camel.deployment.ComponentInstance#getProvidedHostInstances <em>Provided Host Instances</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.deployment.DeploymentPackage#getComponentInstance()
 * @model abstract="true"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore constraints='component_instance_ports_belong_to_instance component_port_instances_of_correct_type'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot component_instance_ports_belong_to_instance='\n\t\t\t\t\t\t\t\tprovidedCommunicationInstances->forAll(p | p.componentInstance = self)\n\t\t\t\t\t\t\t\tand\n\t\t\t\t\t\t\t\tprovidedHostInstances->forAll(p | p.componentInstance = self)' component_port_instances_of_correct_type='\n\t\t\t\t\t\tprovidedCommunicationInstances->forAll(p | type.providedCommunications->includes(p.type))\n\t\t\t\t\t\tand\n\t\t\t\t\t\tprovidedHostInstances->forAll(p | type.providedHosts->includes(p.type))'"
 * @generated
 */
public interface ComponentInstance extends CloudMLElement {
	/**
	 * Returns the value of the '<em><b>Provided Communication Instances</b></em>' containment reference list.
	 * The list contents are of type {@link camel.deployment.ProvidedCommunicationInstance}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provided Communication Instances</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provided Communication Instances</em>' containment reference list.
	 * @see camel.deployment.DeploymentPackage#getComponentInstance_ProvidedCommunicationInstances()
	 * @model containment="true"
	 * @generated
	 */
	EList<ProvidedCommunicationInstance> getProvidedCommunicationInstances();

	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(Component)
	 * @see camel.deployment.DeploymentPackage#getComponentInstance_Type()
	 * @model required="true"
	 * @generated
	 */
	Component getType();

	/**
	 * Sets the value of the '{@link camel.deployment.ComponentInstance#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(Component value);

	/**
	 * Returns the value of the '<em><b>Provided Host Instances</b></em>' containment reference list.
	 * The list contents are of type {@link camel.deployment.ProvidedHostInstance}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provided Host Instances</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provided Host Instances</em>' containment reference list.
	 * @see camel.deployment.DeploymentPackage#getComponentInstance_ProvidedHostInstances()
	 * @model containment="true"
	 * @generated
	 */
	EList<ProvidedHostInstance> getProvidedHostInstances();

} // ComponentInstance
