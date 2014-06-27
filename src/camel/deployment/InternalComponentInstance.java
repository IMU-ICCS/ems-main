/**
 */
package camel.deployment;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Internal Component Instance</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.deployment.InternalComponentInstance#getRequiredCommunicationInstances <em>Required Communication Instances</em>}</li>
 *   <li>{@link camel.deployment.InternalComponentInstance#getRequiredHostInstance <em>Required Host Instance</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.deployment.DeploymentPackage#getInternalComponentInstance()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='internal_component_instance_ports_belong_to_instance internal_component_port_instances_of_correct_type'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot internal_component_instance_ports_belong_to_instance='\n\t\t\t\t\t\t\t\trequiredCommunicationInstances->forAll(p | p.componentInstance = self)\n\t\t\t\t\t\t\t\tand\n\t\t\t\t\t\t\t\trequiredHostInstance.componentInstance = self' internal_component_port_instances_of_correct_type='\n\t\t\t\t\t\ttype.oclIsKindOf(InternalComponent)\n\t\t\t\t\t\tand \n\t\t\t\t\t\trequiredCommunicationInstances->forAll(p | type.oclAsType(InternalComponent).requiredCommunications->includes(p.type))\n\t\t\t\t\t\tand\n\t\t\t\t\t\trequiredHostInstance.type = type.oclAsType(InternalComponent).requiredHost'"
 * @generated
 */
public interface InternalComponentInstance extends ComponentInstance {
	/**
	 * Returns the value of the '<em><b>Required Communication Instances</b></em>' containment reference list.
	 * The list contents are of type {@link camel.deployment.RequiredCommunicationInstance}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Required Communication Instances</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Required Communication Instances</em>' containment reference list.
	 * @see camel.deployment.DeploymentPackage#getInternalComponentInstance_RequiredCommunicationInstances()
	 * @model containment="true"
	 * @generated
	 */
	EList<RequiredCommunicationInstance> getRequiredCommunicationInstances();

	/**
	 * Returns the value of the '<em><b>Required Host Instance</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Required Host Instance</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Required Host Instance</em>' containment reference.
	 * @see #setRequiredHostInstance(RequiredHostInstance)
	 * @see camel.deployment.DeploymentPackage#getInternalComponentInstance_RequiredHostInstance()
	 * @model containment="true"
	 * @generated
	 */
	RequiredHostInstance getRequiredHostInstance();

	/**
	 * Sets the value of the '{@link camel.deployment.InternalComponentInstance#getRequiredHostInstance <em>Required Host Instance</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Required Host Instance</em>' containment reference.
	 * @see #getRequiredHostInstance()
	 * @generated
	 */
	void setRequiredHostInstance(RequiredHostInstance value);

} // InternalComponentInstance
