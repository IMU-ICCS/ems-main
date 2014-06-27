/**
 */
package application;

import types.typesPaasage.OS;
import types.typesPaasage.VMSizeEnum;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Virtual Machine Profile</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link application.VirtualMachineProfile#getSize <em>Size</em>}</li>
 *   <li>{@link application.VirtualMachineProfile#getMemory <em>Memory</em>}</li>
 *   <li>{@link application.VirtualMachineProfile#getStorage <em>Storage</em>}</li>
 *   <li>{@link application.VirtualMachineProfile#getCpu <em>Cpu</em>}</li>
 *   <li>{@link application.VirtualMachineProfile#getOs <em>Os</em>}</li>
 *   <li>{@link application.VirtualMachineProfile#getProvider <em>Provider</em>}</li>
 * </ul>
 * </p>
 *
 * @see application.ApplicationPackage#getVirtualMachineProfile()
 * @model
 * @generated
 */
public interface VirtualMachineProfile extends CloudML_Element {
	/**
	 * Returns the value of the '<em><b>Size</b></em>' attribute.
	 * The literals are from the enumeration {@link types.typesPaasage.VMSizeEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Size</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Size</em>' attribute.
	 * @see types.typesPaasage.VMSizeEnum
	 * @see #setSize(VMSizeEnum)
	 * @see application.ApplicationPackage#getVirtualMachineProfile_Size()
	 * @model required="true"
	 * @generated
	 */
	VMSizeEnum getSize();

	/**
	 * Sets the value of the '{@link application.VirtualMachineProfile#getSize <em>Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Size</em>' attribute.
	 * @see types.typesPaasage.VMSizeEnum
	 * @see #getSize()
	 * @generated
	 */
	void setSize(VMSizeEnum value);

	/**
	 * Returns the value of the '<em><b>Memory</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Memory</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Memory</em>' containment reference.
	 * @see #setMemory(Memory)
	 * @see application.ApplicationPackage#getVirtualMachineProfile_Memory()
	 * @model containment="true"
	 * @generated
	 */
	Memory getMemory();

	/**
	 * Sets the value of the '{@link application.VirtualMachineProfile#getMemory <em>Memory</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Memory</em>' containment reference.
	 * @see #getMemory()
	 * @generated
	 */
	void setMemory(Memory value);

	/**
	 * Returns the value of the '<em><b>Storage</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Storage</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Storage</em>' containment reference.
	 * @see #setStorage(Storage)
	 * @see application.ApplicationPackage#getVirtualMachineProfile_Storage()
	 * @model containment="true"
	 * @generated
	 */
	Storage getStorage();

	/**
	 * Sets the value of the '{@link application.VirtualMachineProfile#getStorage <em>Storage</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Storage</em>' containment reference.
	 * @see #getStorage()
	 * @generated
	 */
	void setStorage(Storage value);

	/**
	 * Returns the value of the '<em><b>Cpu</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cpu</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cpu</em>' containment reference.
	 * @see #setCpu(CPU)
	 * @see application.ApplicationPackage#getVirtualMachineProfile_Cpu()
	 * @model containment="true"
	 * @generated
	 */
	CPU getCpu();

	/**
	 * Sets the value of the '{@link application.VirtualMachineProfile#getCpu <em>Cpu</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cpu</em>' containment reference.
	 * @see #getCpu()
	 * @generated
	 */
	void setCpu(CPU value);

	/**
	 * Returns the value of the '<em><b>Os</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Os</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Os</em>' reference.
	 * @see #setOs(OS)
	 * @see application.ApplicationPackage#getVirtualMachineProfile_Os()
	 * @model required="true"
	 * @generated
	 */
	OS getOs();

	/**
	 * Sets the value of the '{@link application.VirtualMachineProfile#getOs <em>Os</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Os</em>' reference.
	 * @see #getOs()
	 * @generated
	 */
	void setOs(OS value);

	/**
	 * Returns the value of the '<em><b>Provider</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provider</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provider</em>' containment reference.
	 * @see #setProvider(ProviderCost)
	 * @see application.ApplicationPackage#getVirtualMachineProfile_Provider()
	 * @model containment="true"
	 * @generated
	 */
	ProviderCost getProvider();

	/**
	 * Sets the value of the '{@link application.VirtualMachineProfile#getProvider <em>Provider</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Provider</em>' containment reference.
	 * @see #getProvider()
	 * @generated
	 */
	void setProvider(ProviderCost value);

} // VirtualMachineProfile
