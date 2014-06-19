/**
 */
package camel;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Cloud Independent VM Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.CloudIndependentVMInfo#getCpuClass <em>Cpu Class</em>}</li>
 *   <li>{@link camel.CloudIndependentVMInfo#getMemoryClass <em>Memory Class</em>}</li>
 *   <li>{@link camel.CloudIndependentVMInfo#getIoClass <em>Io Class</em>}</li>
 *   <li>{@link camel.CloudIndependentVMInfo#getNetworkClass <em>Network Class</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.CamelPackage#getCloudIndependentVMInfo()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface CloudIndependentVMInfo extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Cpu Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cpu Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cpu Class</em>' attribute.
	 * @see #setCpuClass(String)
	 * @see camel.CamelPackage#getCloudIndependentVMInfo_CpuClass()
	 * @model
	 * @generated
	 */
	String getCpuClass();

	/**
	 * Sets the value of the '{@link camel.CloudIndependentVMInfo#getCpuClass <em>Cpu Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cpu Class</em>' attribute.
	 * @see #getCpuClass()
	 * @generated
	 */
	void setCpuClass(String value);

	/**
	 * Returns the value of the '<em><b>Memory Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Memory Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Memory Class</em>' attribute.
	 * @see #setMemoryClass(String)
	 * @see camel.CamelPackage#getCloudIndependentVMInfo_MemoryClass()
	 * @model
	 * @generated
	 */
	String getMemoryClass();

	/**
	 * Sets the value of the '{@link camel.CloudIndependentVMInfo#getMemoryClass <em>Memory Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Memory Class</em>' attribute.
	 * @see #getMemoryClass()
	 * @generated
	 */
	void setMemoryClass(String value);

	/**
	 * Returns the value of the '<em><b>Io Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Io Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Io Class</em>' attribute.
	 * @see #setIoClass(String)
	 * @see camel.CamelPackage#getCloudIndependentVMInfo_IoClass()
	 * @model
	 * @generated
	 */
	String getIoClass();

	/**
	 * Sets the value of the '{@link camel.CloudIndependentVMInfo#getIoClass <em>Io Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Io Class</em>' attribute.
	 * @see #getIoClass()
	 * @generated
	 */
	void setIoClass(String value);

	/**
	 * Returns the value of the '<em><b>Network Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Network Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Network Class</em>' attribute.
	 * @see #setNetworkClass(String)
	 * @see camel.CamelPackage#getCloudIndependentVMInfo_NetworkClass()
	 * @model
	 * @generated
	 */
	String getNetworkClass();

	/**
	 * Sets the value of the '{@link camel.CloudIndependentVMInfo#getNetworkClass <em>Network Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Network Class</em>' attribute.
	 * @see #getNetworkClass()
	 * @generated
	 */
	void setNetworkClass(String value);

} // CloudIndependentVMInfo
