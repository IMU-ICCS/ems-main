/**
 */
package types.typesPaasage.impl;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

import types.typesPaasage.PaaSageCPElement;
import types.typesPaasage.TypesPaasagePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Paa Sage CP Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link types.typesPaasage.impl.PaaSageCPElementImpl#getTypeId <em>Type Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class PaaSageCPElementImpl extends CDOObjectImpl implements PaaSageCPElement {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PaaSageCPElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPaasagePackage.Literals.PAA_SAGE_CP_ELEMENT;
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
	public int getTypeId() {
		return (Integer)eGet(TypesPaasagePackage.Literals.PAA_SAGE_CP_ELEMENT__TYPE_ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTypeId(int newTypeId) {
		eSet(TypesPaasagePackage.Literals.PAA_SAGE_CP_ELEMENT__TYPE_ID, newTypeId);
	}

} //PaaSageCPElementImpl
