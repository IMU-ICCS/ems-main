/**
 */
package eu.paasage.upperware.metamodel.cp;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Solution</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.Solution#getTimestamp <em>Timestamp</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.Solution#getVariableValue <em>Variable Value</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.Solution#getMetricVariableValue <em>Metric Variable Value</em>}</li>
 * </ul>
 *
 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getSolution()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface Solution extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Timestamp</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Timestamp</em>' attribute.
	 * @see #setTimestamp(long)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getSolution_Timestamp()
	 * @model required="true"
	 * @generated
	 */
	long getTimestamp();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.Solution#getTimestamp <em>Timestamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Timestamp</em>' attribute.
	 * @see #getTimestamp()
	 * @generated
	 */
	void setTimestamp(long value);

	/**
	 * Returns the value of the '<em><b>Variable Value</b></em>' containment reference list.
	 * The list contents are of type {@link eu.paasage.upperware.metamodel.cp.VariableValue}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Variable Value</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variable Value</em>' containment reference list.
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getSolution_VariableValue()
	 * @model containment="true"
	 * @generated
	 */
	EList<VariableValue> getVariableValue();

	/**
	 * Returns the value of the '<em><b>Metric Variable Value</b></em>' containment reference list.
	 * The list contents are of type {@link eu.paasage.upperware.metamodel.cp.MetricVariableValue}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Metric Variable Value</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Metric Variable Value</em>' containment reference list.
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getSolution_MetricVariableValue()
	 * @model containment="true"
	 * @generated
	 */
	EList<MetricVariableValue> getMetricVariableValue();

} // Solution
