/**
 */
package camel.impl;

import camel.CamelPackage;
import camel.ScalabilityPolicy;
import camel.ScalabilityType;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Scalability Policy</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.impl.ScalabilityPolicyImpl#getMinInstances <em>Min Instances</em>}</li>
 *   <li>{@link camel.impl.ScalabilityPolicyImpl#getMaxInstances <em>Max Instances</em>}</li>
 *   <li>{@link camel.impl.ScalabilityPolicyImpl#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ScalabilityPolicyImpl extends RequirementImpl implements ScalabilityPolicy {
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
		return CamelPackage.Literals.SCALABILITY_POLICY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getMinInstances() {
		return (Integer)eGet(CamelPackage.Literals.SCALABILITY_POLICY__MIN_INSTANCES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMinInstances(int newMinInstances) {
		eSet(CamelPackage.Literals.SCALABILITY_POLICY__MIN_INSTANCES, newMinInstances);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getMaxInstances() {
		return (Integer)eGet(CamelPackage.Literals.SCALABILITY_POLICY__MAX_INSTANCES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxInstances(int newMaxInstances) {
		eSet(CamelPackage.Literals.SCALABILITY_POLICY__MAX_INSTANCES, newMaxInstances);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScalabilityType getType() {
		return (ScalabilityType)eGet(CamelPackage.Literals.SCALABILITY_POLICY__TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(ScalabilityType newType) {
		eSet(CamelPackage.Literals.SCALABILITY_POLICY__TYPE, newType);
	}

} //ScalabilityPolicyImpl
