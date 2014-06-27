/**
 */
package camel.sla.impl;

import camel.sla.PreferenceType;
import camel.sla.SlaPackage;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Preference Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.sla.impl.PreferenceTypeImpl#getServiceTermReference <em>Service Term Reference</em>}</li>
 *   <li>{@link camel.sla.impl.PreferenceTypeImpl#getUtility <em>Utility</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PreferenceTypeImpl extends CDOObjectImpl implements PreferenceType {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PreferenceTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SlaPackage.Literals.PREFERENCE_TYPE;
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
	public EList<String> getServiceTermReference() {
		return (EList<String>)eGet(SlaPackage.Literals.PREFERENCE_TYPE__SERVICE_TERM_REFERENCE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Float> getUtility() {
		return (EList<Float>)eGet(SlaPackage.Literals.PREFERENCE_TYPE__UTILITY, true);
	}

} //PreferenceTypeImpl
