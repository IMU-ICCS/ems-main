/**
 */
package eu.paasage.upperware.metamodel.types.typesPaasage.impl;

import eu.paasage.upperware.metamodel.types.typesPaasage.OS;
import eu.paasage.upperware.metamodel.types.typesPaasage.OperatingSystems;
import eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Operating Systems</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.types.typesPaasage.impl.OperatingSystemsImpl#getOss <em>Oss</em>}</li>
 * </ul>
 *
 * @generated
 */
public class OperatingSystemsImpl extends CDOObjectImpl implements OperatingSystems {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OperatingSystemsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPaasagePackage.Literals.OPERATING_SYSTEMS;
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
	public EList<OS> getOss() {
		return (EList<OS>)eGet(TypesPaasagePackage.Literals.OPERATING_SYSTEMS__OSS, true);
	}

} //OperatingSystemsImpl
