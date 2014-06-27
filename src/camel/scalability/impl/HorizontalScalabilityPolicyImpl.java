/**
 */
package camel.scalability.impl;

import camel.deployment.InternalComponent;

import camel.scalability.HorizontalScalabilityPolicy;
import camel.scalability.ScalabilityPackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Horizontal Scalability Policy</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.scalability.impl.HorizontalScalabilityPolicyImpl#getMinInstances <em>Min Instances</em>}</li>
 *   <li>{@link camel.scalability.impl.HorizontalScalabilityPolicyImpl#getMaxInstances <em>Max Instances</em>}</li>
 *   <li>{@link camel.scalability.impl.HorizontalScalabilityPolicyImpl#getComponent <em>Component</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class HorizontalScalabilityPolicyImpl extends ScalabilityPolicyImpl implements HorizontalScalabilityPolicy {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected HorizontalScalabilityPolicyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ScalabilityPackage.Literals.HORIZONTAL_SCALABILITY_POLICY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getMinInstances() {
		return (Integer)eGet(ScalabilityPackage.Literals.HORIZONTAL_SCALABILITY_POLICY__MIN_INSTANCES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMinInstances(int newMinInstances) {
		eSet(ScalabilityPackage.Literals.HORIZONTAL_SCALABILITY_POLICY__MIN_INSTANCES, newMinInstances);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getMaxInstances() {
		return (Integer)eGet(ScalabilityPackage.Literals.HORIZONTAL_SCALABILITY_POLICY__MAX_INSTANCES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxInstances(int newMaxInstances) {
		eSet(ScalabilityPackage.Literals.HORIZONTAL_SCALABILITY_POLICY__MAX_INSTANCES, newMaxInstances);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InternalComponent getComponent() {
		return (InternalComponent)eGet(ScalabilityPackage.Literals.HORIZONTAL_SCALABILITY_POLICY__COMPONENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComponent(InternalComponent newComponent) {
		eSet(ScalabilityPackage.Literals.HORIZONTAL_SCALABILITY_POLICY__COMPONENT, newComponent);
	}

} //HorizontalScalabilityPolicyImpl
