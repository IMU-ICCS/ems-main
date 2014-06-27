/**
 */
package camel.provider;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Alternative</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.provider.Alternative#getGroupCardinality <em>Group Cardinality</em>}</li>
 *   <li>{@link camel.provider.Alternative#getVariants <em>Variants</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.provider.ProviderPackage#getAlternative()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='Alternative_variants_diff_from_subFeatures'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot Alternative_variants_diff_from_subFeatures='\n\t\t\t\t\t\tsubFeatures->forAll(p | not(variants->exists(q | q.name = p.name)))'"
 * @generated
 */
public interface Alternative extends Feature {
	/**
	 * Returns the value of the '<em><b>Group Cardinality</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Group Cardinality</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Group Cardinality</em>' containment reference.
	 * @see #setGroupCardinality(GroupCardinality)
	 * @see camel.provider.ProviderPackage#getAlternative_GroupCardinality()
	 * @model containment="true"
	 * @generated
	 */
	GroupCardinality getGroupCardinality();

	/**
	 * Sets the value of the '{@link camel.provider.Alternative#getGroupCardinality <em>Group Cardinality</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Group Cardinality</em>' containment reference.
	 * @see #getGroupCardinality()
	 * @generated
	 */
	void setGroupCardinality(GroupCardinality value);

	/**
	 * Returns the value of the '<em><b>Variants</b></em>' containment reference list.
	 * The list contents are of type {@link camel.provider.Feature}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Variants</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variants</em>' containment reference list.
	 * @see camel.provider.ProviderPackage#getAlternative_Variants()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<Feature> getVariants();

} // Alternative
