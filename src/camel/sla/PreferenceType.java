/**
 */
package camel.sla;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Preference Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.sla.PreferenceType#getServiceTermReference <em>Service Term Reference</em>}</li>
 *   <li>{@link camel.sla.PreferenceType#getUtility <em>Utility</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.sla.SlaPackage#getPreferenceType()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='Utilities_util_non_negative Utilities_sum_to_One Preference_Type_Same_Size'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot Utilities_util_non_negative='\n\t\t\t\t\t\tself.utility->forAll(p | p >= 0.0)' Utilities_sum_to_One='\n\t\t\t\t\t\tself.utility->sum() = 1.0' Preference_Type_Same_Size='\n\t\t\t\t\t\tself.serviceTermReference->size() = self.utility->size()'"
 * @extends CDOObject
 * @generated
 */
public interface PreferenceType extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Service Term Reference</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Service Term Reference</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Service Term Reference</em>' attribute list.
	 * @see camel.sla.SlaPackage#getPreferenceType_ServiceTermReference()
	 * @model required="true"
	 * @generated
	 */
	EList<String> getServiceTermReference();

	/**
	 * Returns the value of the '<em><b>Utility</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.Float}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Utility</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Utility</em>' attribute list.
	 * @see camel.sla.SlaPackage#getPreferenceType_Utility()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	EList<Float> getUtility();

} // PreferenceType
