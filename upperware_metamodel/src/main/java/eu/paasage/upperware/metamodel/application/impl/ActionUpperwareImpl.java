/**
 */
package eu.paasage.upperware.metamodel.application.impl;

import eu.paasage.upperware.metamodel.application.ActionUpperware;
import eu.paasage.upperware.metamodel.application.ApplicationPackage;

import eu.paasage.upperware.metamodel.types.typesPaasage.ActionType;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Action Upperware</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.ActionUpperwareImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.ActionUpperwareImpl#getType <em>Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ActionUpperwareImpl extends CDOObjectImpl implements ActionUpperware {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ActionUpperwareImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ApplicationPackage.Literals.ACTION_UPPERWARE;
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
	@SuppressWarnings("unchecked")
	public EList<String> getParameters() {
		return (EList<String>)eGet(ApplicationPackage.Literals.ACTION_UPPERWARE__PARAMETERS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActionType getType() {
		return (ActionType)eGet(ApplicationPackage.Literals.ACTION_UPPERWARE__TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(ActionType newType) {
		eSet(ApplicationPackage.Literals.ACTION_UPPERWARE__TYPE, newType);
	}

} //ActionUpperwareImpl
