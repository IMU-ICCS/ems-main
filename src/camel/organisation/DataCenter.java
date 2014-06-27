/**
 */
package camel.organisation;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Data Center</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.organisation.DataCenter#getName <em>Name</em>}</li>
 *   <li>{@link camel.organisation.DataCenter#getCodeName <em>Code Name</em>}</li>
 *   <li>{@link camel.organisation.DataCenter#getHasLocation <em>Has Location</em>}</li>
 *   <li>{@link camel.organisation.DataCenter#getOfCloudProvider <em>Of Cloud Provider</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.organisation.OrganisationPackage#getDataCenter()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='Unique_DataCenter_Per_Provider No_DataCenter_SameLocation_SameProvider'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot Unique_DataCenter_Per_Provider='\n\t\t\tDataCenter.allInstances()->forAll(p1, p2 | p1 <> p2 and p1.ofCloudProvider=p2.ofCloudProvider implies p1.name <> p2.name and p1.codeName <> p2.codeName)' No_DataCenter_SameLocation_SameProvider='\n\t\t\tDataCenter.allInstances()->forAll(p1, p2 | p1 <> p2 and p1.name <> p2.name and p1.ofCloudProvider = p2.ofCloudProvider implies p1.hasLocation <> p2.hasLocation)'"
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
	 * @see camel.organisation.OrganisationPackage#getDataCenter_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link camel.organisation.DataCenter#getName <em>Name</em>}' attribute.
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
	 * @see camel.organisation.OrganisationPackage#getDataCenter_CodeName()
	 * @model required="true"
	 * @generated
	 */
	String getCodeName();

	/**
	 * Sets the value of the '{@link camel.organisation.DataCenter#getCodeName <em>Code Name</em>}' attribute.
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
	 * @see camel.organisation.OrganisationPackage#getDataCenter_HasLocation()
	 * @model required="true"
	 * @generated
	 */
	Location getHasLocation();

	/**
	 * Sets the value of the '{@link camel.organisation.DataCenter#getHasLocation <em>Has Location</em>}' reference.
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
	 * @see camel.organisation.OrganisationPackage#getDataCenter_OfCloudProvider()
	 * @model required="true"
	 * @generated
	 */
	CloudProvider getOfCloudProvider();

	/**
	 * Sets the value of the '{@link camel.organisation.DataCenter#getOfCloudProvider <em>Of Cloud Provider</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Of Cloud Provider</em>' reference.
	 * @see #getOfCloudProvider()
	 * @generated
	 */
	void setOfCloudProvider(CloudProvider value);

} // DataCenter
