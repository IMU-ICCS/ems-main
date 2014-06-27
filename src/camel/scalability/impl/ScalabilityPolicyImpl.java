/**
 */
package camel.scalability.impl;

import camel.impl.RequirementImpl;

import camel.scalability.ScalabilityPackage;
import camel.scalability.ScalabilityPolicy;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Policy</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public abstract class ScalabilityPolicyImpl extends RequirementImpl implements ScalabilityPolicy {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ScalabilityPolicyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ScalabilityPackage.Literals.SCALABILITY_POLICY;
	}

} //ScalabilityPolicyImpl
