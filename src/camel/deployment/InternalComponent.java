/**
 */
package camel.deployment;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Internal Component</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.deployment.InternalComponent#getRequiredCommunications <em>Required Communications</em>}</li>
 *   <li>{@link camel.deployment.InternalComponent#getCompositeInternalComponents <em>Composite Internal Components</em>}</li>
 *   <li>{@link camel.deployment.InternalComponent#getRequiredHost <em>Required Host</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.deployment.DeploymentPackage#getInternalComponent()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='recursion_in_parts_of_component requiredHost_owner_is_self requiredCommunications_owner_is_self'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot recursion_in_parts_of_component='\n\t\t\t\t\t\t\t\tnot(self.contains(self,self))' requiredHost_owner_is_self='\n\t\t\t\t\t\t\t\trequiredHost.component = self' requiredCommunications_owner_is_self='\n\t\t\t\t\t\t\t\trequiredCommunications->forAll(p | p.component = self)'"
 * @generated
 */
public interface InternalComponent extends Component {
	/**
	 * Returns the value of the '<em><b>Required Communications</b></em>' containment reference list.
	 * The list contents are of type {@link camel.deployment.RequiredCommunication}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Required Communications</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Required Communications</em>' containment reference list.
	 * @see camel.deployment.DeploymentPackage#getInternalComponent_RequiredCommunications()
	 * @model containment="true"
	 * @generated
	 */
	EList<RequiredCommunication> getRequiredCommunications();

	/**
	 * Returns the value of the '<em><b>Composite Internal Components</b></em>' containment reference list.
	 * The list contents are of type {@link camel.deployment.InternalComponent}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Composite Internal Components</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Composite Internal Components</em>' containment reference list.
	 * @see camel.deployment.DeploymentPackage#getInternalComponent_CompositeInternalComponents()
	 * @model containment="true"
	 * @generated
	 */
	EList<InternalComponent> getCompositeInternalComponents();

	/**
	 * Returns the value of the '<em><b>Required Host</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Required Host</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Required Host</em>' containment reference.
	 * @see #setRequiredHost(RequiredHost)
	 * @see camel.deployment.DeploymentPackage#getInternalComponent_RequiredHost()
	 * @model containment="true"
	 * @generated
	 */
	RequiredHost getRequiredHost();

	/**
	 * Sets the value of the '{@link camel.deployment.InternalComponent#getRequiredHost <em>Required Host</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Required Host</em>' containment reference.
	 * @see #getRequiredHost()
	 * @generated
	 */
	void setRequiredHost(RequiredHost value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model icRequired="true" rcRequired="true"
	 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot body='\n\t\t\t\t\t\t\t\t\tic.compositeInternalComponents->exists(p | p.name = rc.name or p.contains(p,rc))'"
	 * @generated
	 */
	boolean contains(InternalComponent ic, InternalComponent rc);

} // InternalComponent
