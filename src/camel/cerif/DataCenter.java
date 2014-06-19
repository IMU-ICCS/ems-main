/**
 */
package camel.cerif;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Data Center</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.cerif.DataCenter#getName <em>Name</em>}</li>
 *   <li>{@link camel.cerif.DataCenter#getCodeName <em>Code Name</em>}</li>
 *   <li>{@link camel.cerif.DataCenter#getHasLocation <em>Has Location</em>}</li>
 *   <li>{@link camel.cerif.DataCenter#getOfCloudProvider <em>Of Cloud Provider</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.cerif.CerifPackage#getDataCenter()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface DataCenter extends CDOObject {
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
	 * @see camel.cerif.CerifPackage#getDataCenter_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link camel.cerif.DataCenter#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Code Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Code Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Code Name</em>' attribute.
	 * @see #setCodeName(String)
	 * @see camel.cerif.CerifPackage#getDataCenter_CodeName()
	 * @model required="true"
	 * @generated
	 */
	String getCodeName();

	/**
	 * Sets the value of the '{@link camel.cerif.DataCenter#getCodeName <em>Code Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Code Name</em>' attribute.
	 * @see #getCodeName()
	 * @generated
	 */
	void setCodeName(String value);

	/**
	 * Returns the value of the '<em><b>Has Location</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Has Location</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Has Location</em>' reference.
	 * @see #setHasLocation(Location)
	 * @see camel.cerif.CerifPackage#getDataCenter_HasLocation()
	 * @model required="true"
	 * @generated
	 */
	Location getHasLocation();

	/**
	 * Sets the value of the '{@link camel.cerif.DataCenter#getHasLocation <em>Has Location</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Has Location</em>' reference.
	 * @see #getHasLocation()
	 * @generated
	 */
	void setHasLocation(Location value);

	/**
	 * Returns the value of the '<em><b>Of Cloud Provider</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Of Cloud Provider</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Of Cloud Provider</em>' reference.
	 * @see #setOfCloudProvider(CloudProvider)
	 * @see camel.cerif.CerifPackage#getDataCenter_OfCloudProvider()
	 * @model required="true"
	 * @generated
	 */
	CloudProvider getOfCloudProvider();

	/**
	 * Sets the value of the '{@link camel.cerif.DataCenter#getOfCloudProvider <em>Of Cloud Provider</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Of Cloud Provider</em>' reference.
	 * @see #getOfCloudProvider()
	 * @generated
	 */
	void setOfCloudProvider(CloudProvider value);

} // DataCenter
