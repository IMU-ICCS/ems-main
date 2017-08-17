/**
 */
package eu.paasage.upperware.metamodel.cp;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Variable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.Variable#getDomain <em>Domain</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.Variable#getLocationId <em>Location Id</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.Variable#getProviderId <em>Provider Id</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.Variable#getVmId <em>Vm Id</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.Variable#getOsImageId <em>Os Image Id</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.Variable#getHardwareId <em>Hardware Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getVariable()
 * @model
 * @generated
 */
public interface Variable extends NumericExpression {
	/**
	 * Returns the value of the '<em><b>Domain</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Domain</em>' containment reference.
	 * @see #setDomain(Domain)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getVariable_Domain()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Domain getDomain();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.Variable#getDomain <em>Domain</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Domain</em>' containment reference.
	 * @see #getDomain()
	 * @generated
	 */
	void setDomain(Domain value);

	/**
	 * Returns the value of the '<em><b>Location Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Location Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Location Id</em>' attribute.
	 * @see #setLocationId(String)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getVariable_LocationId()
	 * @model
	 * @generated
	 */
	String getLocationId();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.Variable#getLocationId <em>Location Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Location Id</em>' attribute.
	 * @see #getLocationId()
	 * @generated
	 */
	void setLocationId(String value);

	/**
	 * Returns the value of the '<em><b>Provider Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provider Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provider Id</em>' attribute.
	 * @see #setProviderId(String)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getVariable_ProviderId()
	 * @model
	 * @generated
	 */
	String getProviderId();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.Variable#getProviderId <em>Provider Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Provider Id</em>' attribute.
	 * @see #getProviderId()
	 * @generated
	 */
	void setProviderId(String value);

	/**
	 * Returns the value of the '<em><b>Vm Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Vm Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Vm Id</em>' attribute.
	 * @see #setVmId(String)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getVariable_VmId()
	 * @model
	 * @generated
	 */
	String getVmId();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.Variable#getVmId <em>Vm Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Vm Id</em>' attribute.
	 * @see #getVmId()
	 * @generated
	 */
	void setVmId(String value);

	/**
	 * Returns the value of the '<em><b>Os Image Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Os Image Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Os Image Id</em>' attribute.
	 * @see #setOsImageId(String)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getVariable_OsImageId()
	 * @model
	 * @generated
	 */
	String getOsImageId();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.Variable#getOsImageId <em>Os Image Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Os Image Id</em>' attribute.
	 * @see #getOsImageId()
	 * @generated
	 */
	void setOsImageId(String value);

	/**
	 * Returns the value of the '<em><b>Hardware Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hardware Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hardware Id</em>' attribute.
	 * @see #setHardwareId(String)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getVariable_HardwareId()
	 * @model
	 * @generated
	 */
	String getHardwareId();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.Variable#getHardwareId <em>Hardware Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hardware Id</em>' attribute.
	 * @see #getHardwareId()
	 * @generated
	 */
	void setHardwareId(String value);


	void setComponentName(String newComponentName) ;

	String getComponentName();


	void setFlavourName(String newFlavourName) ;

	String getFlavourName();


} // Variable
