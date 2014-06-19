/**
 */
package camel;

import camel.cerif.DataCenter;
import camel.cerif.Resource;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Physical Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.PhysicalNode#getIP <em>IP</em>}</li>
 *   <li>{@link camel.PhysicalNode#getHardware <em>Hardware</em>}</li>
 *   <li>{@link camel.PhysicalNode#getInDataCenter <em>In Data Center</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.CamelPackage#getPhysicalNode()
 * @model
 * @generated
 */
public interface PhysicalNode extends Resource {
	/**
	 * Returns the value of the '<em><b>IP</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>IP</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>IP</em>' attribute.
	 * @see #setIP(String)
	 * @see camel.CamelPackage#getPhysicalNode_IP()
	 * @model
	 * @generated
	 */
	String getIP();

	/**
	 * Sets the value of the '{@link camel.PhysicalNode#getIP <em>IP</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>IP</em>' attribute.
	 * @see #getIP()
	 * @generated
	 */
	void setIP(String value);

	/**
	 * Returns the value of the '<em><b>Hardware</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hardware</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hardware</em>' attribute.
	 * @see #setHardware(String)
	 * @see camel.CamelPackage#getPhysicalNode_Hardware()
	 * @model
	 * @generated
	 */
	String getHardware();

	/**
	 * Sets the value of the '{@link camel.PhysicalNode#getHardware <em>Hardware</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hardware</em>' attribute.
	 * @see #getHardware()
	 * @generated
	 */
	void setHardware(String value);

	/**
	 * Returns the value of the '<em><b>In Data Center</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>In Data Center</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>In Data Center</em>' reference.
	 * @see #setInDataCenter(DataCenter)
	 * @see camel.CamelPackage#getPhysicalNode_InDataCenter()
	 * @model required="true"
	 * @generated
	 */
	DataCenter getInDataCenter();

	/**
	 * Sets the value of the '{@link camel.PhysicalNode#getInDataCenter <em>In Data Center</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>In Data Center</em>' reference.
	 * @see #getInDataCenter()
	 * @generated
	 */
	void setInDataCenter(DataCenter value);

} // PhysicalNode
