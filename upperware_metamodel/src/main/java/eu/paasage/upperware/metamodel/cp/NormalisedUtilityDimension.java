/**
 */
package eu.paasage.upperware.metamodel.cp;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Normalised Utility Dimension</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.NormalisedUtilityDimension#getSolutions <em>Solutions</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.NormalisedUtilityDimension#getGoal <em>Goal</em>}</li>
 * </ul>
 *
 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getNormalisedUtilityDimension()
 * @model
 * @generated
 */
public interface NormalisedUtilityDimension extends Function {
	/**
	 * Returns the value of the '<em><b>Solutions</b></em>' reference list.
	 * The list contents are of type {@link eu.paasage.upperware.metamodel.cp.Parameter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Solutions</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Solutions</em>' reference list.
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getNormalisedUtilityDimension_Solutions()
	 * @model upper="2"
	 * @generated
	 */
	EList<Parameter> getSolutions();

	/**
	 * Returns the value of the '<em><b>Goal</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Goal</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Goal</em>' reference.
	 * @see #setGoal(Goal)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getNormalisedUtilityDimension_Goal()
	 * @model required="true"
	 * @generated
	 */
	Goal getGoal();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.NormalisedUtilityDimension#getGoal <em>Goal</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Goal</em>' reference.
	 * @see #getGoal()
	 * @generated
	 */
	void setGoal(Goal value);

} // NormalisedUtilityDimension
