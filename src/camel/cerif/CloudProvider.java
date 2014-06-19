/**
 */
package camel.cerif;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Cloud Provider</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.cerif.CloudProvider#isPublic <em>Public</em>}</li>
 *   <li>{@link camel.cerif.CloudProvider#isSaaS <em>Saa S</em>}</li>
 *   <li>{@link camel.cerif.CloudProvider#isPaaS <em>Paa S</em>}</li>
 *   <li>{@link camel.cerif.CloudProvider#isIaaS <em>Iaa S</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.cerif.CerifPackage#getCloudProvider()
 * @model
 * @generated
 */
public interface CloudProvider extends Organization {
	/**
	 * Returns the value of the '<em><b>Public</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Public</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Public</em>' attribute.
	 * @see #setPublic(boolean)
	 * @see camel.cerif.CerifPackage#getCloudProvider_Public()
	 * @model
	 * @generated
	 */
	boolean isPublic();

	/**
	 * Sets the value of the '{@link camel.cerif.CloudProvider#isPublic <em>Public</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Public</em>' attribute.
	 * @see #isPublic()
	 * @generated
	 */
	void setPublic(boolean value);

	/**
	 * Returns the value of the '<em><b>Saa S</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Saa S</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Saa S</em>' attribute.
	 * @see #setSaaS(boolean)
	 * @see camel.cerif.CerifPackage#getCloudProvider_SaaS()
	 * @model
	 * @generated
	 */
	boolean isSaaS();

	/**
	 * Sets the value of the '{@link camel.cerif.CloudProvider#isSaaS <em>Saa S</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Saa S</em>' attribute.
	 * @see #isSaaS()
	 * @generated
	 */
	void setSaaS(boolean value);

	/**
	 * Returns the value of the '<em><b>Paa S</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Paa S</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Paa S</em>' attribute.
	 * @see #setPaaS(boolean)
	 * @see camel.cerif.CerifPackage#getCloudProvider_PaaS()
	 * @model
	 * @generated
	 */
	boolean isPaaS();

	/**
	 * Sets the value of the '{@link camel.cerif.CloudProvider#isPaaS <em>Paa S</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Paa S</em>' attribute.
	 * @see #isPaaS()
	 * @generated
	 */
	void setPaaS(boolean value);

	/**
	 * Returns the value of the '<em><b>Iaa S</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Iaa S</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Iaa S</em>' attribute.
	 * @see #setIaaS(boolean)
	 * @see camel.cerif.CerifPackage#getCloudProvider_IaaS()
	 * @model
	 * @generated
	 */
	boolean isIaaS();

	/**
	 * Sets the value of the '{@link camel.cerif.CloudProvider#isIaaS <em>Iaa S</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Iaa S</em>' attribute.
	 * @see #isIaaS()
	 * @generated
	 */
	void setIaaS(boolean value);

} // CloudProvider
