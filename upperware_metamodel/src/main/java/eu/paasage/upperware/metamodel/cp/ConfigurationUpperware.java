/**
 */
package eu.paasage.upperware.metamodel.cp;

import eu.paasage.upperware.metamodel.types.NumericValueUpperware;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Configuration Upperware</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.ConfigurationUpperware#getSolution <em>Solution</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.ConfigurationUpperware#getValue <em>Value</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.ConfigurationUpperware#getGoal <em>Goal</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getConfigurationUpperware()
 * @model
 * @generated
 */
public interface ConfigurationUpperware extends NumericExpression {
	/**
	 * Returns the value of the '<em><b>Solution</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Solution</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Solution</em>' reference.
	 * @see #setSolution(Parameter)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getConfigurationUpperware_Solution()
	 * @model
	 * @generated
	 */
	Parameter getSolution();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.ConfigurationUpperware#getSolution <em>Solution</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Solution</em>' reference.
	 * @see #getSolution()
	 * @generated
	 */
	void setSolution(Parameter value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' containment reference.
	 * @see #setValue(NumericValueUpperware)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getConfigurationUpperware_Value()
	 * @model containment="true"
	 * @generated
	 */
	NumericValueUpperware getValue();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.ConfigurationUpperware#getValue <em>Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' containment reference.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(NumericValueUpperware value);

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
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getConfigurationUpperware_Goal()
	 * @model required="true"
	 * @generated
	 */
	Goal getGoal();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.ConfigurationUpperware#getGoal <em>Goal</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Goal</em>' reference.
	 * @see #getGoal()
	 * @generated
	 */
	void setGoal(Goal value);

} // ConfigurationUpperware
