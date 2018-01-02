/**
 */
package eu.paasage.upperware.metamodel.application;

import eu.paasage.upperware.metamodel.types.typesPaasage.FrequencyEnum;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>CPU</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.CPU#getFrequency <em>Frequency</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.CPU#getCores <em>Cores</em>}</li>
 * </ul>
 *
 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getCPU()
 * @model
 * @generated
 */
public interface CPU extends ResourceUpperware {
	/**
	 * Returns the value of the '<em><b>Frequency</b></em>' attribute.
	 * The literals are from the enumeration {@link eu.paasage.upperware.metamodel.types.typesPaasage.FrequencyEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Frequency</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Frequency</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.FrequencyEnum
	 * @see #setFrequency(FrequencyEnum)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getCPU_Frequency()
	 * @model
	 * @generated
	 */
	FrequencyEnum getFrequency();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.CPU#getFrequency <em>Frequency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Frequency</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.FrequencyEnum
	 * @see #getFrequency()
	 * @generated
	 */
	void setFrequency(FrequencyEnum value);

	/**
	 * Returns the value of the '<em><b>Cores</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cores</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cores</em>' attribute.
	 * @see #setCores(int)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getCPU_Cores()
	 * @model required="true"
	 * @generated
	 */
	int getCores();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.CPU#getCores <em>Cores</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cores</em>' attribute.
	 * @see #getCores()
	 * @generated
	 */
	void setCores(int value);

} // CPU
