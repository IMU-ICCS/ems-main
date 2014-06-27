/**
 */
package camel.scalability;

import camel.deployment.VM;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Vertical Scalability Policy</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.scalability.VerticalScalabilityPolicy#getMinCores <em>Min Cores</em>}</li>
 *   <li>{@link camel.scalability.VerticalScalabilityPolicy#getMaxCores <em>Max Cores</em>}</li>
 *   <li>{@link camel.scalability.VerticalScalabilityPolicy#getMinMemory <em>Min Memory</em>}</li>
 *   <li>{@link camel.scalability.VerticalScalabilityPolicy#getMaxMemory <em>Max Memory</em>}</li>
 *   <li>{@link camel.scalability.VerticalScalabilityPolicy#getMinCPU <em>Min CPU</em>}</li>
 *   <li>{@link camel.scalability.VerticalScalabilityPolicy#getMaxCPU <em>Max CPU</em>}</li>
 *   <li>{@link camel.scalability.VerticalScalabilityPolicy#getMinStorage <em>Min Storage</em>}</li>
 *   <li>{@link camel.scalability.VerticalScalabilityPolicy#getMaxStorage <em>Max Storage</em>}</li>
 *   <li>{@link camel.scalability.VerticalScalabilityPolicy#getVm <em>Vm</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.scalability.ScalabilityPackage#getVerticalScalabilityPolicy()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='VertScalPol_correct_param_vals VertScalPol_activ_one_alt'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot VertScalPol_correct_param_vals='\n\t\t\t\t\t\t\tminCores >= 0 and maxCores >= 0 and minCores <= maxCores and minMemory >= 0 and maxMemory >= 0 and minMemory <= maxMemory and minCPU >= 0 and maxCPU >= 0 and minCPU <= maxCPU and minStorage >=0 and maxStorage >= 0 and minStorage <= maxStorage' VertScalPol_activ_one_alt='\n\t\t\t\t\t\t\tmaxCores > 0 or maxCPU > 0 or maxMemory > 0 or maxStorage > 0'"
 * @generated
 */
public interface VerticalScalabilityPolicy extends ScalabilityPolicy {
	/**
	 * Returns the value of the '<em><b>Min Cores</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Min Cores</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Min Cores</em>' attribute.
	 * @see #setMinCores(int)
	 * @see camel.scalability.ScalabilityPackage#getVerticalScalabilityPolicy_MinCores()
	 * @model
	 * @generated
	 */
	int getMinCores();

	/**
	 * Sets the value of the '{@link camel.scalability.VerticalScalabilityPolicy#getMinCores <em>Min Cores</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Min Cores</em>' attribute.
	 * @see #getMinCores()
	 * @generated
	 */
	void setMinCores(int value);

	/**
	 * Returns the value of the '<em><b>Max Cores</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max Cores</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Cores</em>' attribute.
	 * @see #setMaxCores(int)
	 * @see camel.scalability.ScalabilityPackage#getVerticalScalabilityPolicy_MaxCores()
	 * @model
	 * @generated
	 */
	int getMaxCores();

	/**
	 * Sets the value of the '{@link camel.scalability.VerticalScalabilityPolicy#getMaxCores <em>Max Cores</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Cores</em>' attribute.
	 * @see #getMaxCores()
	 * @generated
	 */
	void setMaxCores(int value);

	/**
	 * Returns the value of the '<em><b>Min Memory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Min Memory</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Min Memory</em>' attribute.
	 * @see #setMinMemory(int)
	 * @see camel.scalability.ScalabilityPackage#getVerticalScalabilityPolicy_MinMemory()
	 * @model
	 * @generated
	 */
	int getMinMemory();

	/**
	 * Sets the value of the '{@link camel.scalability.VerticalScalabilityPolicy#getMinMemory <em>Min Memory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Min Memory</em>' attribute.
	 * @see #getMinMemory()
	 * @generated
	 */
	void setMinMemory(int value);

	/**
	 * Returns the value of the '<em><b>Max Memory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max Memory</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Memory</em>' attribute.
	 * @see #setMaxMemory(int)
	 * @see camel.scalability.ScalabilityPackage#getVerticalScalabilityPolicy_MaxMemory()
	 * @model
	 * @generated
	 */
	int getMaxMemory();

	/**
	 * Sets the value of the '{@link camel.scalability.VerticalScalabilityPolicy#getMaxMemory <em>Max Memory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Memory</em>' attribute.
	 * @see #getMaxMemory()
	 * @generated
	 */
	void setMaxMemory(int value);

	/**
	 * Returns the value of the '<em><b>Min CPU</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Min CPU</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Min CPU</em>' attribute.
	 * @see #setMinCPU(double)
	 * @see camel.scalability.ScalabilityPackage#getVerticalScalabilityPolicy_MinCPU()
	 * @model
	 * @generated
	 */
	double getMinCPU();

	/**
	 * Sets the value of the '{@link camel.scalability.VerticalScalabilityPolicy#getMinCPU <em>Min CPU</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Min CPU</em>' attribute.
	 * @see #getMinCPU()
	 * @generated
	 */
	void setMinCPU(double value);

	/**
	 * Returns the value of the '<em><b>Max CPU</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max CPU</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max CPU</em>' attribute.
	 * @see #setMaxCPU(double)
	 * @see camel.scalability.ScalabilityPackage#getVerticalScalabilityPolicy_MaxCPU()
	 * @model
	 * @generated
	 */
	double getMaxCPU();

	/**
	 * Sets the value of the '{@link camel.scalability.VerticalScalabilityPolicy#getMaxCPU <em>Max CPU</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max CPU</em>' attribute.
	 * @see #getMaxCPU()
	 * @generated
	 */
	void setMaxCPU(double value);

	/**
	 * Returns the value of the '<em><b>Min Storage</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Min Storage</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Min Storage</em>' attribute.
	 * @see #setMinStorage(int)
	 * @see camel.scalability.ScalabilityPackage#getVerticalScalabilityPolicy_MinStorage()
	 * @model
	 * @generated
	 */
	int getMinStorage();

	/**
	 * Sets the value of the '{@link camel.scalability.VerticalScalabilityPolicy#getMinStorage <em>Min Storage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Min Storage</em>' attribute.
	 * @see #getMinStorage()
	 * @generated
	 */
	void setMinStorage(int value);

	/**
	 * Returns the value of the '<em><b>Max Storage</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max Storage</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Storage</em>' attribute.
	 * @see #setMaxStorage(int)
	 * @see camel.scalability.ScalabilityPackage#getVerticalScalabilityPolicy_MaxStorage()
	 * @model
	 * @generated
	 */
	int getMaxStorage();

	/**
	 * Sets the value of the '{@link camel.scalability.VerticalScalabilityPolicy#getMaxStorage <em>Max Storage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Storage</em>' attribute.
	 * @see #getMaxStorage()
	 * @generated
	 */
	void setMaxStorage(int value);

	/**
	 * Returns the value of the '<em><b>Vm</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Vm</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Vm</em>' reference.
	 * @see #setVm(VM)
	 * @see camel.scalability.ScalabilityPackage#getVerticalScalabilityPolicy_Vm()
	 * @model
	 * @generated
	 */
	VM getVm();

	/**
	 * Sets the value of the '{@link camel.scalability.VerticalScalabilityPolicy#getVm <em>Vm</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Vm</em>' reference.
	 * @see #getVm()
	 * @generated
	 */
	void setVm(VM value);

} // VerticalScalabilityPolicy
