/**
 */
package eu.paasage.upperware.metamodel.cp;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Delta Utility</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.DeltaUtility#getSolutions <em>Solutions</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.DeltaUtility#getSelectedSolution <em>Selected Solution</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getDeltaUtility()
 * @model
 * @generated
 */
public interface DeltaUtility extends Function {
	/**
	 * Returns the value of the '<em><b>Solutions</b></em>' containment reference list.
	 * The list contents are of type {@link eu.paasage.upperware.metamodel.cp.Parameter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Solutions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Solutions</em>' containment reference list.
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getDeltaUtility_Solutions()
	 * @model containment="true" upper="2"
	 * @generated
	 */
	EList<Parameter> getSolutions();

	/**
	 * Returns the value of the '<em><b>Selected Solution</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Selected Solution</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Selected Solution</em>' reference.
	 * @see #setSelectedSolution(Parameter)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getDeltaUtility_SelectedSolution()
	 * @model
	 * @generated
	 */
	Parameter getSelectedSolution();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.DeltaUtility#getSelectedSolution <em>Selected Solution</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Selected Solution</em>' reference.
	 * @see #getSelectedSolution()
	 * @generated
	 */
	void setSelectedSolution(Parameter value);

} // DeltaUtility
