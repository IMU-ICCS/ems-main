/**
 */
package camel.organisation;

import camel.Action;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Resource</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see camel.organisation.OrganisationPackage#getResource()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface Resource extends CDOObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model xRequired="true"
	 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot body='self.oclType().name = x.name'"
	 * @generated
	 */
	boolean ofClass(EClass x);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model actsMany="true"
	 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot body='acts->forAll(p | AllowedActions.allInstances()->exists(actions->select(type = p.type)->size() = 1 and self.ofClass(resourceClass)))'"
	 * @generated
	 */
	boolean allowsActions(EList<Action> acts);

} // Resource
