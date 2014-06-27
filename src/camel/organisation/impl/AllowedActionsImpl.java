/**
 */
package camel.organisation.impl;

import camel.Action;

import camel.organisation.AllowedActions;
import camel.organisation.OrganisationPackage;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Allowed Actions</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.organisation.impl.AllowedActionsImpl#getResourceClass <em>Resource Class</em>}</li>
 *   <li>{@link camel.organisation.impl.AllowedActionsImpl#getActions <em>Actions</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AllowedActionsImpl extends CDOObjectImpl implements AllowedActions {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AllowedActionsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrganisationPackage.Literals.ALLOWED_ACTIONS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getResourceClass() {
		return (EClass)eGet(OrganisationPackage.Literals.ALLOWED_ACTIONS__RESOURCE_CLASS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setResourceClass(EClass newResourceClass) {
		eSet(OrganisationPackage.Literals.ALLOWED_ACTIONS__RESOURCE_CLASS, newResourceClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Action> getActions() {
		return (EList<Action>)eGet(OrganisationPackage.Literals.ALLOWED_ACTIONS__ACTIONS, true);
	}

} //AllowedActionsImpl
