/**
 */
package eu.paasage.upperware.metamodel.types.typesPaasage.impl;

import eu.paasage.upperware.metamodel.types.typesPaasage.PaaSageCPElement;
import eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Paa Sage CP Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.PaaSageCPElementImpl#getTypeId <em>Type Id</em>}</li>
 * </ul>
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
	public long getTypeId() {
		return (Long)eGet(TypesPaasagePackage.Literals.PAA_SAGE_CP_ELEMENT__TYPE_ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTypeId(long newTypeId) {
		eSet(TypesPaasagePackage.Literals.PAA_SAGE_CP_ELEMENT__TYPE_ID, newTypeId);
	}

} //PaaSageCPElementImpl
