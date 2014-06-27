/**
 */
package camel;

import camel.organisation.Resource;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Data Object</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.DataObject#getReplication <em>Replication</em>}</li>
 *   <li>{@link camel.DataObject#getPartitioning <em>Partitioning</em>}</li>
 *   <li>{@link camel.DataObject#getConsistency <em>Consistency</em>}</li>
 *   <li>{@link camel.DataObject#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.CamelPackage#getDataObject()
 * @model
 * @generated
 */
public interface DataObject extends Resource {
	/**
	 * Returns the value of the '<em><b>Replication</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Replication</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Replication</em>' attribute.
	 * @see #setReplication(String)
	 * @see camel.CamelPackage#getDataObject_Replication()
	 * @model
	 * @generated
	 */
	String getReplication();

	/**
	 * Sets the value of the '{@link camel.DataObject#getReplication <em>Replication</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Replication</em>' attribute.
	 * @see #getReplication()
	 * @generated
	 */
	void setReplication(String value);

	/**
	 * Returns the value of the '<em><b>Partitioning</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Partitioning</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Partitioning</em>' attribute.
	 * @see #setPartitioning(String)
	 * @see camel.CamelPackage#getDataObject_Partitioning()
	 * @model
	 * @generated
	 */
	String getPartitioning();

	/**
	 * Sets the value of the '{@link camel.DataObject#getPartitioning <em>Partitioning</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Partitioning</em>' attribute.
	 * @see #getPartitioning()
	 * @generated
	 */
	void setPartitioning(String value);

	/**
	 * Returns the value of the '<em><b>Consistency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Consistency</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Consistency</em>' attribute.
	 * @see #setConsistency(String)
	 * @see camel.CamelPackage#getDataObject_Consistency()
	 * @model
	 * @generated
	 */
	String getConsistency();

	/**
	 * Sets the value of the '{@link camel.DataObject#getConsistency <em>Consistency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Consistency</em>' attribute.
	 * @see #getConsistency()
	 * @generated
	 */
	void setConsistency(String value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see camel.CamelPackage#getDataObject_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link camel.DataObject#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // DataObject
