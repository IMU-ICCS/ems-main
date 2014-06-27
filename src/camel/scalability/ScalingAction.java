/**
 */
package camel.scalability;

import camel.Action;

import camel.deployment.ComponentInstance;
import camel.deployment.HostingInstance;
import camel.deployment.VMInstance;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Scaling Action</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.scalability.ScalingAction#getCount <em>Count</em>}</li>
 *   <li>{@link camel.scalability.ScalingAction#getMemoryUpdate <em>Memory Update</em>}</li>
 *   <li>{@link camel.scalability.ScalingAction#getCPUUpdate <em>CPU Update</em>}</li>
 *   <li>{@link camel.scalability.ScalingAction#getCoreUpdate <em>Core Update</em>}</li>
 *   <li>{@link camel.scalability.ScalingAction#getStorageUpdate <em>Storage Update</em>}</li>
 *   <li>{@link camel.scalability.ScalingAction#getIoUpdate <em>Io Update</em>}</li>
 *   <li>{@link camel.scalability.ScalingAction#getNetworkUpdate <em>Network Update</em>}</li>
 *   <li>{@link camel.scalability.ScalingAction#getVmInstance <em>Vm Instance</em>}</li>
 *   <li>{@link camel.scalability.ScalingAction#getComponentInstance <em>Component Instance</em>}</li>
 *   <li>{@link camel.scalability.ScalingAction#getContainmentInstance <em>Containment Instance</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.scalability.ScalabilityPackage#getScalingAction()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='scale_action_correct_count scale_action_check_type_wrt_properties'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot scale_action_correct_count='\n\t\t\t\t\t\tcount >= 0' scale_action_check_type_wrt_properties='\n\t\t\t\t\t\tif (self.type = camel::ActionType::SCALE_IN or self.type = camel::ActionType::SCALE_OUT) then (componentInstance <> null and count <> 0 and (containmentInstance <> null implies (containmentInstance.providedHostInstance.componentInstance=vmInstance and containmentInstance.requiredHostInstance.componentInstance = componentInstance)))\n\t\t\t\t\t\telse (componentInstance = null and containmentInstance = null and count = 0 and (memoryUpdate <> 0 or CPUUpdate <> 0.0 or coreUpdate <> 0 or storageUpdate <> 0))\n\t\t\t\t\t\tendif'"
 * @generated
 */
public interface ScalingAction extends Action {
	/**
	 * Returns the value of the '<em><b>Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Count</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Count</em>' attribute.
	 * @see #setCount(int)
	 * @see camel.scalability.ScalabilityPackage#getScalingAction_Count()
	 * @model
	 * @generated
	 */
	int getCount();

	/**
	 * Sets the value of the '{@link camel.scalability.ScalingAction#getCount <em>Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Count</em>' attribute.
	 * @see #getCount()
	 * @generated
	 */
	void setCount(int value);

	/**
	 * Returns the value of the '<em><b>Memory Update</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Memory Update</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Memory Update</em>' attribute.
	 * @see #setMemoryUpdate(int)
	 * @see camel.scalability.ScalabilityPackage#getScalingAction_MemoryUpdate()
	 * @model
	 * @generated
	 */
	int getMemoryUpdate();

	/**
	 * Sets the value of the '{@link camel.scalability.ScalingAction#getMemoryUpdate <em>Memory Update</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Memory Update</em>' attribute.
	 * @see #getMemoryUpdate()
	 * @generated
	 */
	void setMemoryUpdate(int value);

	/**
	 * Returns the value of the '<em><b>CPU Update</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>CPU Update</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>CPU Update</em>' attribute.
	 * @see #setCPUUpdate(double)
	 * @see camel.scalability.ScalabilityPackage#getScalingAction_CPUUpdate()
	 * @model
	 * @generated
	 */
	double getCPUUpdate();

	/**
	 * Sets the value of the '{@link camel.scalability.ScalingAction#getCPUUpdate <em>CPU Update</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>CPU Update</em>' attribute.
	 * @see #getCPUUpdate()
	 * @generated
	 */
	void setCPUUpdate(double value);

	/**
	 * Returns the value of the '<em><b>Core Update</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Core Update</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Core Update</em>' attribute.
	 * @see #setCoreUpdate(int)
	 * @see camel.scalability.ScalabilityPackage#getScalingAction_CoreUpdate()
	 * @model
	 * @generated
	 */
	int getCoreUpdate();

	/**
	 * Sets the value of the '{@link camel.scalability.ScalingAction#getCoreUpdate <em>Core Update</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Core Update</em>' attribute.
	 * @see #getCoreUpdate()
	 * @generated
	 */
	void setCoreUpdate(int value);

	/**
	 * Returns the value of the '<em><b>Storage Update</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Storage Update</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Storage Update</em>' attribute.
	 * @see #setStorageUpdate(int)
	 * @see camel.scalability.ScalabilityPackage#getScalingAction_StorageUpdate()
	 * @model
	 * @generated
	 */
	int getStorageUpdate();

	/**
	 * Sets the value of the '{@link camel.scalability.ScalingAction#getStorageUpdate <em>Storage Update</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Storage Update</em>' attribute.
	 * @see #getStorageUpdate()
	 * @generated
	 */
	void setStorageUpdate(int value);

	/**
	 * Returns the value of the '<em><b>Io Update</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Io Update</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Io Update</em>' attribute.
	 * @see #setIoUpdate(int)
	 * @see camel.scalability.ScalabilityPackage#getScalingAction_IoUpdate()
	 * @model
	 * @generated
	 */
	int getIoUpdate();

	/**
	 * Sets the value of the '{@link camel.scalability.ScalingAction#getIoUpdate <em>Io Update</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Io Update</em>' attribute.
	 * @see #getIoUpdate()
	 * @generated
	 */
	void setIoUpdate(int value);

	/**
	 * Returns the value of the '<em><b>Network Update</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Network Update</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Network Update</em>' attribute.
	 * @see #setNetworkUpdate(int)
	 * @see camel.scalability.ScalabilityPackage#getScalingAction_NetworkUpdate()
	 * @model
	 * @generated
	 */
	int getNetworkUpdate();

	/**
	 * Sets the value of the '{@link camel.scalability.ScalingAction#getNetworkUpdate <em>Network Update</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Network Update</em>' attribute.
	 * @see #getNetworkUpdate()
	 * @generated
	 */
	void setNetworkUpdate(int value);

	/**
	 * Returns the value of the '<em><b>Vm Instance</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Vm Instance</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Vm Instance</em>' reference.
	 * @see #setVmInstance(VMInstance)
	 * @see camel.scalability.ScalabilityPackage#getScalingAction_VmInstance()
	 * @model required="true"
	 * @generated
	 */
	VMInstance getVmInstance();

	/**
	 * Sets the value of the '{@link camel.scalability.ScalingAction#getVmInstance <em>Vm Instance</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Vm Instance</em>' reference.
	 * @see #getVmInstance()
	 * @generated
	 */
	void setVmInstance(VMInstance value);

	/**
	 * Returns the value of the '<em><b>Component Instance</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component Instance</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component Instance</em>' reference.
	 * @see #setComponentInstance(ComponentInstance)
	 * @see camel.scalability.ScalabilityPackage#getScalingAction_ComponentInstance()
	 * @model
	 * @generated
	 */
	ComponentInstance getComponentInstance();

	/**
	 * Sets the value of the '{@link camel.scalability.ScalingAction#getComponentInstance <em>Component Instance</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component Instance</em>' reference.
	 * @see #getComponentInstance()
	 * @generated
	 */
	void setComponentInstance(ComponentInstance value);

	/**
	 * Returns the value of the '<em><b>Containment Instance</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Containment Instance</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Containment Instance</em>' reference.
	 * @see #setContainmentInstance(HostingInstance)
	 * @see camel.scalability.ScalabilityPackage#getScalingAction_ContainmentInstance()
	 * @model
	 * @generated
	 */
	HostingInstance getContainmentInstance();

	/**
	 * Sets the value of the '{@link camel.scalability.ScalingAction#getContainmentInstance <em>Containment Instance</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Containment Instance</em>' reference.
	 * @see #getContainmentInstance()
	 * @generated
	 */
	void setContainmentInstance(HostingInstance value);

} // ScalingAction
