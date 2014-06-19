/**
 */
package camel.impl;

import camel.Application;
import camel.CamelPackage;
import camel.Requirement;
import camel.RequirementGroup;
import camel.RequirementOperatorType;

import camel.cerif.User;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Requirement Group</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.impl.RequirementGroupImpl#getPosedBy <em>Posed By</em>}</li>
 *   <li>{@link camel.impl.RequirementGroupImpl#getRequirement <em>Requirement</em>}</li>
 *   <li>{@link camel.impl.RequirementGroupImpl#getOnApplication <em>On Application</em>}</li>
 *   <li>{@link camel.impl.RequirementGroupImpl#getRequirementOperator <em>Requirement Operator</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RequirementGroupImpl extends RequirementImpl implements RequirementGroup {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RequirementGroupImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CamelPackage.Literals.REQUIREMENT_GROUP;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public User getPosedBy() {
		return (User)eGet(CamelPackage.Literals.REQUIREMENT_GROUP__POSED_BY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPosedBy(User newPosedBy) {
		eSet(CamelPackage.Literals.REQUIREMENT_GROUP__POSED_BY, newPosedBy);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Requirement> getRequirement() {
		return (EList<Requirement>)eGet(CamelPackage.Literals.REQUIREMENT_GROUP__REQUIREMENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Application> getOnApplication() {
		return (EList<Application>)eGet(CamelPackage.Literals.REQUIREMENT_GROUP__ON_APPLICATION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RequirementOperatorType getRequirementOperator() {
		return (RequirementOperatorType)eGet(CamelPackage.Literals.REQUIREMENT_GROUP__REQUIREMENT_OPERATOR, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRequirementOperator(RequirementOperatorType newRequirementOperator) {
		eSet(CamelPackage.Literals.REQUIREMENT_GROUP__REQUIREMENT_OPERATOR, newRequirementOperator);
	}

} //RequirementGroupImpl
