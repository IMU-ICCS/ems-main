/**
 */
package camel.security.impl;

import camel.security.SecurityControl;
import camel.security.SecurityPackage;
import camel.security.SecurityProperty;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Control</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.security.impl.SecurityControlImpl#getId <em>Id</em>}</li>
 *   <li>{@link camel.security.impl.SecurityControlImpl#getDomain <em>Domain</em>}</li>
 *   <li>{@link camel.security.impl.SecurityControlImpl#getSpecification <em>Specification</em>}</li>
 *   <li>{@link camel.security.impl.SecurityControlImpl#getLinkToSecProp <em>Link To Sec Prop</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SecurityControlImpl extends CDOObjectImpl implements SecurityControl {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SecurityControlImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SecurityPackage.Literals.SECURITY_CONTROL;
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
	public String getId() {
		return (String)eGet(SecurityPackage.Literals.SECURITY_CONTROL__ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		eSet(SecurityPackage.Literals.SECURITY_CONTROL__ID, newId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDomain() {
		return (String)eGet(SecurityPackage.Literals.SECURITY_CONTROL__DOMAIN, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDomain(String newDomain) {
		eSet(SecurityPackage.Literals.SECURITY_CONTROL__DOMAIN, newDomain);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSpecification() {
		return (String)eGet(SecurityPackage.Literals.SECURITY_CONTROL__SPECIFICATION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSpecification(String newSpecification) {
		eSet(SecurityPackage.Literals.SECURITY_CONTROL__SPECIFICATION, newSpecification);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<SecurityProperty> getLinkToSecProp() {
		return (EList<SecurityProperty>)eGet(SecurityPackage.Literals.SECURITY_CONTROL__LINK_TO_SEC_PROP, true);
	}

} //SecurityControlImpl
