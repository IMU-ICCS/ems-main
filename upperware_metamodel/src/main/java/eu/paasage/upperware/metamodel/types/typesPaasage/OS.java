/**
 */
package eu.paasage.upperware.metamodel.types.typesPaasage;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>OS</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.types.typesPaasage.OS#getName <em>Name</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.types.typesPaasage.OS#getVers <em>Vers</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.types.typesPaasage.OS#getArchitecture <em>Architecture</em>}</li>
 * </ul>
 *
 * @see eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage#getOS()
 * @model
 * @generated
 */
public interface OS extends PaaSageCPElement {
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
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage#getOS_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.OS#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Vers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Vers</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Vers</em>' attribute.
	 * @see #setVers(String)
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage#getOS_Vers()
	 * @model
	 * @generated
	 */
	String getVers();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.OS#getVers <em>Vers</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Vers</em>' attribute.
	 * @see #getVers()
	 * @generated
	 */
	void setVers(String value);

	/**
	 * Returns the value of the '<em><b>Architecture</b></em>' attribute.
	 * The literals are from the enumeration {@link eu.paasage.upperware.metamodel.types.typesPaasage.OSArchitectureEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Architecture</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Architecture</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.OSArchitectureEnum
	 * @see #setArchitecture(OSArchitectureEnum)
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage#getOS_Architecture()
	 * @model required="true"
	 * @generated
	 */
	OSArchitectureEnum getArchitecture();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.OS#getArchitecture <em>Architecture</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Architecture</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.OSArchitectureEnum
	 * @see #getArchitecture()
	 * @generated
	 */
	void setArchitecture(OSArchitectureEnum value);

} // OS
