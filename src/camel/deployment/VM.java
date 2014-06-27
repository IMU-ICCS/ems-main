/**
 */
package camel.deployment;

import camel.StorageUnit;
import camel.VMType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>VM</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.deployment.VM#getMinRam <em>Min Ram</em>}</li>
 *   <li>{@link camel.deployment.VM#getMaxRam <em>Max Ram</em>}</li>
 *   <li>{@link camel.deployment.VM#getRamUnit <em>Ram Unit</em>}</li>
 *   <li>{@link camel.deployment.VM#getMinCores <em>Min Cores</em>}</li>
 *   <li>{@link camel.deployment.VM#getMaxCores <em>Max Cores</em>}</li>
 *   <li>{@link camel.deployment.VM#getMinStorage <em>Min Storage</em>}</li>
 *   <li>{@link camel.deployment.VM#getMaxStorage <em>Max Storage</em>}</li>
 *   <li>{@link camel.deployment.VM#getStorageUnit <em>Storage Unit</em>}</li>
 *   <li>{@link camel.deployment.VM#getMinCPU <em>Min CPU</em>}</li>
 *   <li>{@link camel.deployment.VM#getMaxCPU <em>Max CPU</em>}</li>
 *   <li>{@link camel.deployment.VM#getOs <em>Os</em>}</li>
 *   <li>{@link camel.deployment.VM#isIs64os <em>Is64os</em>}</li>
 *   <li>{@link camel.deployment.VM#getVmType <em>Vm Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.deployment.DeploymentPackage#getVM()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='one_alternative_provided correct_input'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot one_alternative_provided='\n\t\t\t\t\t\t\t\t(vmType <> null and (minRam = 0 and maxRam = 0 and ramUnit = null and minStorage = 0 and maxStorage = 0 and storageUnit = null and minCPU = 0 and maxCPU = 0 and minCores = 0 and maxCores = 0)) or (vmType = null and (((minRam > 0 or maxRam > 0) and ramUnit <> null) or ((minStorage > 0 or maxStorage > 0) and storageUnit <> null) or (minCPU > 0 or maxCPU > 0) or (minCores > 0 or maxCores > 0)))' correct_input='\n\t\t\t\t\t\tminRam >= 0 and maxRam >= 0 and minCores >= 0 and maxCores >= 0 and minStorage >= 0 and maxStorage >= 0 and minCPU >= 0 and maxCPU >= 0'"
 * @generated
 */
public interface VM extends ExternalComponent {
	/**
	 * Returns the value of the '<em><b>Min Ram</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Min Ram</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Min Ram</em>' attribute.
	 * @see #setMinRam(int)
	 * @see camel.deployment.DeploymentPackage#getVM_MinRam()
	 * @model
	 * @generated
	 */
	int getMinRam();

	/**
	 * Sets the value of the '{@link camel.deployment.VM#getMinRam <em>Min Ram</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Min Ram</em>' attribute.
	 * @see #getMinRam()
	 * @generated
	 */
	void setMinRam(int value);

	/**
	 * Returns the value of the '<em><b>Max Ram</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max Ram</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Ram</em>' attribute.
	 * @see #setMaxRam(int)
	 * @see camel.deployment.DeploymentPackage#getVM_MaxRam()
	 * @model
	 * @generated
	 */
	int getMaxRam();

	/**
	 * Sets the value of the '{@link camel.deployment.VM#getMaxRam <em>Max Ram</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Ram</em>' attribute.
	 * @see #getMaxRam()
	 * @generated
	 */
	void setMaxRam(int value);

	/**
	 * Returns the value of the '<em><b>Ram Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ram Unit</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ram Unit</em>' reference.
	 * @see #setRamUnit(StorageUnit)
	 * @see camel.deployment.DeploymentPackage#getVM_RamUnit()
	 * @model
	 * @generated
	 */
	StorageUnit getRamUnit();

	/**
	 * Sets the value of the '{@link camel.deployment.VM#getRamUnit <em>Ram Unit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ram Unit</em>' reference.
	 * @see #getRamUnit()
	 * @generated
	 */
	void setRamUnit(StorageUnit value);

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
	 * @see camel.deployment.DeploymentPackage#getVM_MinCores()
	 * @model
	 * @generated
	 */
	int getMinCores();

	/**
	 * Sets the value of the '{@link camel.deployment.VM#getMinCores <em>Min Cores</em>}' attribute.
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
	 * @see camel.deployment.DeploymentPackage#getVM_MaxCores()
	 * @model
	 * @generated
	 */
	int getMaxCores();

	/**
	 * Sets the value of the '{@link camel.deployment.VM#getMaxCores <em>Max Cores</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Cores</em>' attribute.
	 * @see #getMaxCores()
	 * @generated
	 */
	void setMaxCores(int value);

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
	 * @see camel.deployment.DeploymentPackage#getVM_MinStorage()
	 * @model
	 * @generated
	 */
	int getMinStorage();

	/**
	 * Sets the value of the '{@link camel.deployment.VM#getMinStorage <em>Min Storage</em>}' attribute.
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
	 * @see camel.deployment.DeploymentPackage#getVM_MaxStorage()
	 * @model
	 * @generated
	 */
	int getMaxStorage();

	/**
	 * Sets the value of the '{@link camel.deployment.VM#getMaxStorage <em>Max Storage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Storage</em>' attribute.
	 * @see #getMaxStorage()
	 * @generated
	 */
	void setMaxStorage(int value);

	/**
	 * Returns the value of the '<em><b>Storage Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Storage Unit</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Storage Unit</em>' reference.
	 * @see #setStorageUnit(StorageUnit)
	 * @see camel.deployment.DeploymentPackage#getVM_StorageUnit()
	 * @model
	 * @generated
	 */
	StorageUnit getStorageUnit();

	/**
	 * Sets the value of the '{@link camel.deployment.VM#getStorageUnit <em>Storage Unit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Storage Unit</em>' reference.
	 * @see #getStorageUnit()
	 * @generated
	 */
	void setStorageUnit(StorageUnit value);

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
	 * @see camel.deployment.DeploymentPackage#getVM_MinCPU()
	 * @model
	 * @generated
	 */
	double getMinCPU();

	/**
	 * Sets the value of the '{@link camel.deployment.VM#getMinCPU <em>Min CPU</em>}' attribute.
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
	 * @see camel.deployment.DeploymentPackage#getVM_MaxCPU()
	 * @model
	 * @generated
	 */
	double getMaxCPU();

	/**
	 * Sets the value of the '{@link camel.deployment.VM#getMaxCPU <em>Max CPU</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max CPU</em>' attribute.
	 * @see #getMaxCPU()
	 * @generated
	 */
	void setMaxCPU(double value);

	/**
	 * Returns the value of the '<em><b>Os</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Os</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Os</em>' attribute.
	 * @see #setOs(String)
	 * @see camel.deployment.DeploymentPackage#getVM_Os()
	 * @model
	 * @generated
	 */
	String getOs();

	/**
	 * Sets the value of the '{@link camel.deployment.VM#getOs <em>Os</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Os</em>' attribute.
	 * @see #getOs()
	 * @generated
	 */
	void setOs(String value);

	/**
	 * Returns the value of the '<em><b>Is64os</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is64os</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is64os</em>' attribute.
	 * @see #setIs64os(boolean)
	 * @see camel.deployment.DeploymentPackage#getVM_Is64os()
	 * @model default="true"
	 * @generated
	 */
	boolean isIs64os();

	/**
	 * Sets the value of the '{@link camel.deployment.VM#isIs64os <em>Is64os</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is64os</em>' attribute.
	 * @see #isIs64os()
	 * @generated
	 */
	void setIs64os(boolean value);

	/**
	 * Returns the value of the '<em><b>Vm Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Vm Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Vm Type</em>' reference.
	 * @see #setVmType(VMType)
	 * @see camel.deployment.DeploymentPackage#getVM_VmType()
	 * @model
	 * @generated
	 */
	VMType getVmType();

	/**
	 * Sets the value of the '{@link camel.deployment.VM#getVmType <em>Vm Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Vm Type</em>' reference.
	 * @see #getVmType()
	 * @generated
	 */
	void setVmType(VMType value);

} // VM
