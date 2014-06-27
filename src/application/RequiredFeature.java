/**
 */
package application;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Required Feature</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link application.RequiredFeature#getFeature <em>Feature</em>}</li>
 *   <li>{@link application.RequiredFeature#getProvidedBy <em>Provided By</em>}</li>
 *   <li>{@link application.RequiredFeature#isRemote <em>Remote</em>}</li>
 *   <li>{@link application.RequiredFeature#isOptional <em>Optional</em>}</li>
 * </ul>
 * </p>
 *
 * @see application.ApplicationPackage#getRequiredFeature()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface RequiredFeature extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Feature</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Feature</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feature</em>' attribute.
	 * @see #setFeature(String)
	 * @see application.ApplicationPackage#getRequiredFeature_Feature()
	 * @model required="true"
	 * @generated
	 */
	String getFeature();

	/**
	 * Sets the value of the '{@link application.RequiredFeature#getFeature <em>Feature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Feature</em>' attribute.
	 * @see #getFeature()
	 * @generated
	 */
	void setFeature(String value);

	/**
	 * Returns the value of the '<em><b>Provided By</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provided By</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provided By</em>' reference.
	 * @see #setProvidedBy(CloudML_Element)
	 * @see application.ApplicationPackage#getRequiredFeature_ProvidedBy()
	 * @model required="true"
	 * @generated
	 */
	CloudML_Element getProvidedBy();

	/**
	 * Sets the value of the '{@link application.RequiredFeature#getProvidedBy <em>Provided By</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Provided By</em>' reference.
	 * @see #getProvidedBy()
	 * @generated
	 */
	void setProvidedBy(CloudML_Element value);

	/**
	 * Returns the value of the '<em><b>Remote</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Remote</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Remote</em>' attribute.
	 * @see #setRemote(boolean)
	 * @see application.ApplicationPackage#getRequiredFeature_Remote()
	 * @model required="true"
	 * @generated
	 */
	boolean isRemote();

	/**
	 * Sets the value of the '{@link application.RequiredFeature#isRemote <em>Remote</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Remote</em>' attribute.
	 * @see #isRemote()
	 * @generated
	 */
	void setRemote(boolean value);

	/**
	 * Returns the value of the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Optional</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Optional</em>' attribute.
	 * @see #setOptional(boolean)
	 * @see application.ApplicationPackage#getRequiredFeature_Optional()
	 * @model required="true"
	 * @generated
	 */
	boolean isOptional();

	/**
	 * Sets the value of the '{@link application.RequiredFeature#isOptional <em>Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Optional</em>' attribute.
	 * @see #isOptional()
	 * @generated
	 */
	void setOptional(boolean value);

} // RequiredFeature
