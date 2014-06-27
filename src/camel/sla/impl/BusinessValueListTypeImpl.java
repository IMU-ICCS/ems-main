/**
 */
package camel.sla.impl;

import camel.sla.BusinessValueListType;
import camel.sla.CompensationType;
import camel.sla.PreferenceType;
import camel.sla.SlaPackage;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Business Value List Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.sla.impl.BusinessValueListTypeImpl#getImportance <em>Importance</em>}</li>
 *   <li>{@link camel.sla.impl.BusinessValueListTypeImpl#getPenalty <em>Penalty</em>}</li>
 *   <li>{@link camel.sla.impl.BusinessValueListTypeImpl#getReward <em>Reward</em>}</li>
 *   <li>{@link camel.sla.impl.BusinessValueListTypeImpl#getPreference <em>Preference</em>}</li>
 *   <li>{@link camel.sla.impl.BusinessValueListTypeImpl#getCustomBusinessValue <em>Custom Business Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BusinessValueListTypeImpl extends CDOObjectImpl implements BusinessValueListType {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BusinessValueListTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SlaPackage.Literals.BUSINESS_VALUE_LIST_TYPE;
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
	public int getImportance() {
		return (Integer)eGet(SlaPackage.Literals.BUSINESS_VALUE_LIST_TYPE__IMPORTANCE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImportance(int newImportance) {
		eSet(SlaPackage.Literals.BUSINESS_VALUE_LIST_TYPE__IMPORTANCE, newImportance);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<CompensationType> getPenalty() {
		return (EList<CompensationType>)eGet(SlaPackage.Literals.BUSINESS_VALUE_LIST_TYPE__PENALTY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<CompensationType> getReward() {
		return (EList<CompensationType>)eGet(SlaPackage.Literals.BUSINESS_VALUE_LIST_TYPE__REWARD, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PreferenceType getPreference() {
		return (PreferenceType)eGet(SlaPackage.Literals.BUSINESS_VALUE_LIST_TYPE__PREFERENCE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPreference(PreferenceType newPreference) {
		eSet(SlaPackage.Literals.BUSINESS_VALUE_LIST_TYPE__PREFERENCE, newPreference);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<EObject> getCustomBusinessValue() {
		return (EList<EObject>)eGet(SlaPackage.Literals.BUSINESS_VALUE_LIST_TYPE__CUSTOM_BUSINESS_VALUE, true);
	}

} //BusinessValueListTypeImpl
