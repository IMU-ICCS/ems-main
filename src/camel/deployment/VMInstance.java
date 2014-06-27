/**
 */
package camel.deployment;

import camel.VMInfo;

import java.util.Date;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>VM Instance</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.deployment.VMInstance#getPublicAddress <em>Public Address</em>}</li>
 *   <li>{@link camel.deployment.VMInstance#getCreatedOn <em>Created On</em>}</li>
 *   <li>{@link camel.deployment.VMInstance#getDestroyedOn <em>Destroyed On</em>}</li>
 *   <li>{@link camel.deployment.VMInstance#getHasConfig <em>Has Config</em>}</li>
 *   <li>{@link camel.deployment.VMInstance#getHasInfo <em>Has Info</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.deployment.DeploymentPackage#getVMInstance()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='correct_type_for_vm_instance vm_instance_type_map_to_VMInfo'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot correct_type_for_vm_instance='\n\t\t\t\t\t\t\t\ttype.oclIsTypeOf(VM)' vm_instance_type_map_to_VMInfo='\n\t\t\t\t\t\t\t\t(type.oclIsTypeOf(VM) and type.oclAsType(VM).vmType <> null) implies hasInfo.ofVM = type.oclAsType(VM).vmType'"
 * @generated
 */
public interface VMInstance extends ExternalComponentInstance {
	/**
	 * Returns the value of the '<em><b>Public Address</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Public Address</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Public Address</em>' attribute.
	 * @see #setPublicAddress(String)
	 * @see camel.deployment.DeploymentPackage#getVMInstance_PublicAddress()
	 * @model
	 * @generated
	 */
	String getPublicAddress();

	/**
	 * Sets the value of the '{@link camel.deployment.VMInstance#getPublicAddress <em>Public Address</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Public Address</em>' attribute.
	 * @see #getPublicAddress()
	 * @generated
	 */
	void setPublicAddress(String value);

	/**
	 * Returns the value of the '<em><b>Created On</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Created On</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Created On</em>' attribute.
	 * @see #setCreatedOn(Date)
	 * @see camel.deployment.DeploymentPackage#getVMInstance_CreatedOn()
	 * @model
	 * @generated
	 */
	Date getCreatedOn();

	/**
	 * Sets the value of the '{@link camel.deployment.VMInstance#getCreatedOn <em>Created On</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Created On</em>' attribute.
	 * @see #getCreatedOn()
	 * @generated
	 */
	void setCreatedOn(Date value);

	/**
	 * Returns the value of the '<em><b>Destroyed On</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Destroyed On</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Destroyed On</em>' attribute.
	 * @see #setDestroyedOn(Date)
	 * @see camel.deployment.DeploymentPackage#getVMInstance_DestroyedOn()
	 * @model
	 * @generated
	 */
	Date getDestroyedOn();

	/**
	 * Sets the value of the '{@link camel.deployment.VMInstance#getDestroyedOn <em>Destroyed On</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Destroyed On</em>' attribute.
	 * @see #getDestroyedOn()
	 * @generated
	 */
	void setDestroyedOn(Date value);

	/**
	 * Returns the value of the '<em><b>Has Config</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Has Config</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Has Config</em>' reference.
	 * @see #setHasConfig(Image)
	 * @see camel.deployment.DeploymentPackage#getVMInstance_HasConfig()
	 * @model required="true"
	 * @generated
	 */
	Image getHasConfig();

	/**
	 * Sets the value of the '{@link camel.deployment.VMInstance#getHasConfig <em>Has Config</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Has Config</em>' reference.
	 * @see #getHasConfig()
	 * @generated
	 */
	void setHasConfig(Image value);

	/**
	 * Returns the value of the '<em><b>Has Info</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Has Info</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Has Info</em>' reference.
	 * @see #setHasInfo(VMInfo)
	 * @see camel.deployment.DeploymentPackage#getVMInstance_HasInfo()
	 * @model required="true"
	 * @generated
	 */
	VMInfo getHasInfo();

	/**
	 * Sets the value of the '{@link camel.deployment.VMInstance#getHasInfo <em>Has Info</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Has Info</em>' reference.
	 * @see #getHasInfo()
	 * @generated
	 */
	void setHasInfo(VMInfo value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='System.out.println(\"Checking dates for VMInstance: \" + vm);\n\t\tDate createdOn = vm.getCreatedOn();\n\t\tDate destroyedOn = vm.getDestroyedOn();\n\t\tif (createdOn != null && destroyedOn != null && destroyedOn.before(createdOn)) return Boolean.FALSE;\n\t\treturn Boolean.TRUE;'"
	 * @generated
	 */
	boolean checkDates(VMInstance vm);

} // VMInstance
