/**
 */
package types.typesPaasage.impl;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

import types.typesPaasage.ApplicationComponentType;
import types.typesPaasage.TypesPaasagePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Application Component Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link types.typesPaasage.impl.ApplicationComponentTypeImpl#getId <em>Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ApplicationComponentTypeImpl extends CDOObjectImpl implements ApplicationComponentType {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ApplicationComponentTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPaasagePackage.Literals.APPLICATION_COMPONENT_TYPE;
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
		return (String)eGet(TypesPaasagePackage.Literals.APPLICATION_COMPONENT_TYPE__ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		eSet(TypesPaasagePackage.Literals.APPLICATION_COMPONENT_TYPE__ID, newId);
	}

} //ApplicationComponentTypeImpl
