/**
 */
package camel.sla;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Business Value List Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.sla.BusinessValueListType#getImportance <em>Importance</em>}</li>
 *   <li>{@link camel.sla.BusinessValueListType#getPenalty <em>Penalty</em>}</li>
 *   <li>{@link camel.sla.BusinessValueListType#getReward <em>Reward</em>}</li>
 *   <li>{@link camel.sla.BusinessValueListType#getPreference <em>Preference</em>}</li>
 *   <li>{@link camel.sla.BusinessValueListType#getCustomBusinessValue <em>Custom Business Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.sla.SlaPackage#getBusinessValueListType()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface BusinessValueListType extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Importance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Importance</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Importance</em>' attribute.
	 * @see #setImportance(int)
	 * @see camel.sla.SlaPackage#getBusinessValueListType_Importance()
	 * @model
	 * @generated
	 */
	int getImportance();

	/**
	 * Sets the value of the '{@link camel.sla.BusinessValueListType#getImportance <em>Importance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Importance</em>' attribute.
	 * @see #getImportance()
	 * @generated
	 */
	void setImportance(int value);

	/**
	 * Returns the value of the '<em><b>Penalty</b></em>' containment reference list.
	 * The list contents are of type {@link camel.sla.CompensationType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Penalty</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Penalty</em>' containment reference list.
	 * @see camel.sla.SlaPackage#getBusinessValueListType_Penalty()
	 * @model containment="true"
	 * @generated
	 */
	EList<CompensationType> getPenalty();

	/**
	 * Returns the value of the '<em><b>Reward</b></em>' containment reference list.
	 * The list contents are of type {@link camel.sla.CompensationType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reward</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reward</em>' containment reference list.
	 * @see camel.sla.SlaPackage#getBusinessValueListType_Reward()
	 * @model containment="true"
	 * @generated
	 */
	EList<CompensationType> getReward();

	/**
	 * Returns the value of the '<em><b>Preference</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Preference</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Preference</em>' containment reference.
	 * @see #setPreference(PreferenceType)
	 * @see camel.sla.SlaPackage#getBusinessValueListType_Preference()
	 * @model containment="true"
	 * @generated
	 */
	PreferenceType getPreference();

	/**
	 * Sets the value of the '{@link camel.sla.BusinessValueListType#getPreference <em>Preference</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Preference</em>' containment reference.
	 * @see #getPreference()
	 * @generated
	 */
	void setPreference(PreferenceType value);

	/**
	 * Returns the value of the '<em><b>Custom Business Value</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EObject}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Custom Business Value</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Custom Business Value</em>' containment reference list.
	 * @see camel.sla.SlaPackage#getBusinessValueListType_CustomBusinessValue()
	 * @model containment="true"
	 * @generated
	 */
	EList<EObject> getCustomBusinessValue();

} // BusinessValueListType
