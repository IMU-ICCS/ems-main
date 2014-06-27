/**
 */
package camel.security.impl;

import camel.security.SecurityControl;
import camel.security.SecurityMetric;
import camel.security.SecurityModel;
import camel.security.SecurityPackage;
import camel.security.SecurityProperty;
import camel.security.SecurityRequirement;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.security.impl.SecurityModelImpl#getSecControls <em>Sec Controls</em>}</li>
 *   <li>{@link camel.security.impl.SecurityModelImpl#getSecReq <em>Sec Req</em>}</li>
 *   <li>{@link camel.security.impl.SecurityModelImpl#getSecProps <em>Sec Props</em>}</li>
 *   <li>{@link camel.security.impl.SecurityModelImpl#getSecMetric <em>Sec Metric</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SecurityModelImpl extends CDOObjectImpl implements SecurityModel {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SecurityModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SecurityPackage.Literals.SECURITY_MODEL;
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
	public EList<SecurityControl> getSecControls() {
		return (EList<SecurityControl>)eGet(SecurityPackage.Literals.SECURITY_MODEL__SEC_CONTROLS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<SecurityRequirement> getSecReq() {
		return (EList<SecurityRequirement>)eGet(SecurityPackage.Literals.SECURITY_MODEL__SEC_REQ, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<SecurityProperty> getSecProps() {
		return (EList<SecurityProperty>)eGet(SecurityPackage.Literals.SECURITY_MODEL__SEC_PROPS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<SecurityMetric> getSecMetric() {
		return (EList<SecurityMetric>)eGet(SecurityPackage.Literals.SECURITY_MODEL__SEC_METRIC, true);
	}

} //SecurityModelImpl
