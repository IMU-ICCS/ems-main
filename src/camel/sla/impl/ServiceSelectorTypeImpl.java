/**
 */
package camel.sla.impl;

import camel.sla.ServiceSelectorType;
import camel.sla.SlaPackage;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Service Selector Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.sla.impl.ServiceSelectorTypeImpl#getServiceName <em>Service Name</em>}</li>
 *   <li>{@link camel.sla.impl.ServiceSelectorTypeImpl#getServiceReference <em>Service Reference</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ServiceSelectorTypeImpl extends CDOObjectImpl implements ServiceSelectorType {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ServiceSelectorTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SlaPackage.Literals.SERVICE_SELECTOR_TYPE;
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
	public String getServiceName() {
		return (String)eGet(SlaPackage.Literals.SERVICE_SELECTOR_TYPE__SERVICE_NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setServiceName(String newServiceName) {
		eSet(SlaPackage.Literals.SERVICE_SELECTOR_TYPE__SERVICE_NAME, newServiceName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getServiceReference() {
		return (EObject)eGet(SlaPackage.Literals.SERVICE_SELECTOR_TYPE__SERVICE_REFERENCE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setServiceReference(EObject newServiceReference) {
		eSet(SlaPackage.Literals.SERVICE_SELECTOR_TYPE__SERVICE_REFERENCE, newServiceReference);
	}

} //ServiceSelectorTypeImpl
