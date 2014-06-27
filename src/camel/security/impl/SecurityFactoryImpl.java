/**
 */
package camel.security.impl;

import camel.security.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SecurityFactoryImpl extends EFactoryImpl implements SecurityFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SecurityFactory init() {
		try {
			SecurityFactory theSecurityFactory = (SecurityFactory)EPackage.Registry.INSTANCE.getEFactory(SecurityPackage.eNS_URI);
			if (theSecurityFactory != null) {
				return theSecurityFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new SecurityFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SecurityFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case SecurityPackage.SECURITY_MODEL: return (EObject)createSecurityModel();
			case SecurityPackage.SECURITY_CONTROL: return (EObject)createSecurityControl();
			case SecurityPackage.SECURITY_METRIC: return (EObject)createSecurityMetric();
			case SecurityPackage.SECURITY_PROPERTY: return (EObject)createSecurityProperty();
			case SecurityPackage.CERTIFIABLE: return (EObject)createCertifiable();
			case SecurityPackage.SECURITY_REQUIREMENT: return (EObject)createSecurityRequirement();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SecurityModel createSecurityModel() {
		SecurityModelImpl securityModel = new SecurityModelImpl();
		return securityModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SecurityControl createSecurityControl() {
		SecurityControlImpl securityControl = new SecurityControlImpl();
		return securityControl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SecurityMetric createSecurityMetric() {
		SecurityMetricImpl securityMetric = new SecurityMetricImpl();
		return securityMetric;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SecurityProperty createSecurityProperty() {
		SecurityPropertyImpl securityProperty = new SecurityPropertyImpl();
		return securityProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Certifiable createCertifiable() {
		CertifiableImpl certifiable = new CertifiableImpl();
		return certifiable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SecurityRequirement createSecurityRequirement() {
		SecurityRequirementImpl securityRequirement = new SecurityRequirementImpl();
		return securityRequirement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SecurityPackage getSecurityPackage() {
		return (SecurityPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static SecurityPackage getPackage() {
		return SecurityPackage.eINSTANCE;
	}

} //SecurityFactoryImpl
