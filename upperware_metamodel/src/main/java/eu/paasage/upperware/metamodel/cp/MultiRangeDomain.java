/**
 */
package eu.paasage.upperware.metamodel.cp;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Multi Range Domain</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.MultiRangeDomain#getRanges <em>Ranges</em>}</li>
 * </ul>
 *
 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getMultiRangeDomain()
 * @model
 * @generated
 */
public interface MultiRangeDomain extends NumericDomain {
	/**
	 * Returns the value of the '<em><b>Ranges</b></em>' containment reference list.
	 * The list contents are of type {@link eu.paasage.upperware.metamodel.cp.RangeDomain}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ranges</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ranges</em>' containment reference list.
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getMultiRangeDomain_Ranges()
	 * @model containment="true" lower="2"
	 * @generated
	 */
	EList<RangeDomain> getRanges();

} // MultiRangeDomain
